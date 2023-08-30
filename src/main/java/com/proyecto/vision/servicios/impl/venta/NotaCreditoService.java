package com.proyecto.vision.servicios.impl.venta;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.modelos.compra.NotaCreditoCompraLinea;
import com.proyecto.vision.modelos.configuracion.TipoComprobante;
import com.proyecto.vision.exception.*;
import com.proyecto.vision.modelos.configuracion.Secuencial;
import com.proyecto.vision.modelos.inventario.TipoOperacion;
import com.proyecto.vision.modelos.venta.*;
import com.proyecto.vision.modelos.inventario.Kardex;
import com.proyecto.vision.repositorios.venta.INotaCreditoRepository;
import com.proyecto.vision.servicios.interf.configuracion.ISecuencialService;
import com.proyecto.vision.servicios.interf.venta.IFacturaService;
import com.proyecto.vision.servicios.interf.venta.INotaCreditoService;
import com.proyecto.vision.servicios.interf.configuracion.ITipoComprobanteService;
import com.proyecto.vision.servicios.interf.inventario.IKardexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NotaCreditoService implements INotaCreditoService {
    @Autowired
    private INotaCreditoRepository rep;
    @Autowired
    private ITipoComprobanteService tipoComprobanteService;
    @Autowired
    private IKardexService kardexService;
    @Autowired
    private IFacturaService facturaService;
    @Autowired
    private ISecuencialService secuencialService;

    @Override
    public void validar(NotaCredito notaCredito) {
        if(notaCredito.getEstado().equals(Constantes.estadoAnulada)) throw new EstadoInvalidoException(Constantes.estadoAnulada);
        if(notaCredito.getProcesoSRI().equals(Constantes.procesoSRIAutorizada)) throw new EstadoInvalidoException(Constantes.procesoSRIAutorizada);
        if(notaCredito.getProcesoSRI().equals(Constantes.procesoSRIAnulada)) throw new EstadoInvalidoException(Constantes.procesoSRIAnulada);
        if(notaCredito.getEmpresa().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.empresa);
        if(notaCredito.getFecha() == null) throw new DatoInvalidoException(Constantes.fecha);
        if(notaCredito.getSesion().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.sesion);
        if(notaCredito.getNotaCreditoLineas().isEmpty()) throw new DatoInvalidoException(Constantes.nota_credito_venta_linea);
    }

    @Transactional
    @Override
    public NotaCredito crear(NotaCredito notaCredito) {
        validar(notaCredito);
        Optional<NotaCredito> notaCreditoExistente = rep.obtenerPorFacturaYEmpresaYEstadoDiferente(notaCredito.getFactura().getId(), notaCredito.getEmpresa().getId(), Constantes.estadoAnulada);
        if(notaCreditoExistente.isPresent()){
            throw new EntidadExistenteException(Constantes.nota_credito);
        }
        TipoComprobante tipoComprobante = tipoComprobanteService.obtenerPorNombreTabla(Constantes.tabla_nota_credito);
        notaCredito.setTipoComprobante(tipoComprobante);
        Optional<String>codigo=Util.generarCodigoPorEmpresa(Constantes.tabla_nota_credito, notaCredito.getEmpresa().getId());
        if (codigo.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        notaCredito.setCodigo(codigo.get());
        Secuencial secuencial = secuencialService.obtenerPorTipoComprobanteYEstacionYEmpresaYEstado(notaCredito.getTipoComprobante().getId(),
                notaCredito.getSesion().getUsuario().getEstacion().getId(), notaCredito.getSesion().getEmpresa().getId(), Constantes.estadoActivo);
        notaCredito.setSecuencial(Util.generarSecuencial(secuencial.getNumeroSiguiente()));
        notaCredito.setNumeroComprobante(notaCredito.getEstablecimiento() + Constantes.guion + notaCredito.getPuntoVenta() + Constantes.guion + notaCredito.getSecuencial());
        notaCredito.setCodigoNumerico(Util.generarCodigoNumerico(secuencial.getNumeroSiguiente()));
        Optional<String> claveAcceso = crearClaveAcceso(notaCredito);
        if (claveAcceso.isEmpty()) {
            throw new ClaveAccesoNoExistenteException();
        }
        notaCredito.setClaveAcceso(claveAcceso.get());
        notaCredito.setEstado(Constantes.estadoEmitida);
        notaCredito.setProcesoSRI(Constantes.procesoSRIPendiente);
        calcular(notaCredito);
        crearKardex(notaCredito);
        NotaCredito res = rep.save(notaCredito);
        res.normalizar();
        secuencial.setNumeroSiguiente(secuencial.getNumeroSiguiente()+1);
        secuencialService.actualizar(secuencial);
        return res;
    }

    private Optional<String> crearClaveAcceso(NotaCredito notaCredito) {
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String fechaEmision = dateFormat.format(notaCredito.getFecha());
        String tipoComprobante = Constantes.nota_de_credito_sri;
        String numeroRuc = notaCredito.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getIdentificacion();
        String tipoAmbiente = Constantes.pruebas_sri;
        String serie = notaCredito.getSesion().getUsuario().getEstacion().getEstablecimiento().getCodigoSRI() + notaCredito.getSesion().getUsuario().getEstacion().getCodigoSRI();
        String numeroComprobante = notaCredito.getSecuencial();
        String codigoNumerico = notaCredito.getCodigoNumerico();
        String tipoEmision = Constantes.emision_normal_sri;
        String cadenaVerificacion=fechaEmision+tipoComprobante+numeroRuc+tipoAmbiente+serie+numeroComprobante+codigoNumerico+tipoEmision;
        int[] arreglo=new int[cadenaVerificacion.length()];
        for(int i=0; i<cadenaVerificacion.length(); i++) {
            arreglo[i]= Integer.parseInt(cadenaVerificacion.charAt(i)+Constantes.vacio);
        }
        int factor=Constantes.dos;
        int suma=0;
        for(int i=arreglo.length-1; i>=0; i--) {
            suma=suma+arreglo[i]*factor;
            if(factor==Constantes.siete) {
                factor=Constantes.dos;
            } else {
                factor++;
            }
        }
        int digitoVerificador = Constantes.once - (suma % Constantes.once);
        if(digitoVerificador == Constantes.diez) {
            digitoVerificador = 1;
        }
        if(digitoVerificador == Constantes.once) {
            digitoVerificador = 0;
        }
        String claveAcceso=cadenaVerificacion+digitoVerificador;
        return Optional.of(claveAcceso);
    }
    private void crearKardex(NotaCredito notaCredito) {
        if(notaCredito.getEstado().equals(Constantes.estadoAnulada)) throw new EstadoInvalidoException(Constantes.estadoAnulada);
        if(notaCredito.getProcesoSRI().equals(Constantes.procesoSRIAutorizada)) throw new EstadoInvalidoException(Constantes.procesoSRIAutorizada);
        if(notaCredito.getProcesoSRI().equals(Constantes.procesoSRIAnulada)) throw new EstadoInvalidoException(Constantes.procesoSRIAnulada);

        for (NotaCreditoLinea notaCreditoLinea : notaCredito.getNotaCreditoLineas()) {
            Kardex ultimoKardex = kardexService.obtenerUltimoPorProductoYBodega(notaCreditoLinea.getProducto().getId(), notaCreditoLinea.getBodega().getId());
            double entrada = Constantes.cero;
            double saldo = Constantes.cero;
            double costoTotal = Constantes.cero;
            double costoUnitario = Constantes.cero;
            double costoPromedio = Constantes.cero;
            long tipoOperacionId = Constantes.ceroId;
            if (ultimoKardex != null) {
                entrada = notaCreditoLinea.getCantidad();
                if(notaCredito.getOperacion().equals(Constantes.operacion_devolucion)) {
                    saldo = ultimoKardex.getSaldo() + entrada;
                    tipoOperacionId = 7;
                }
                if(notaCredito.getOperacion().equals(Constantes.operacion_descuento)) {
                    saldo = ultimoKardex.getSaldo();
                    tipoOperacionId = 9;
                }
                costoUnitario = ultimoKardex.getCostoPromedio();
                costoUnitario = Math.round(costoUnitario * 100.0) / 100.0;

                costoTotal = ultimoKardex.getCostoTotal() + (notaCreditoLinea.getCantidad() * costoUnitario);
                costoTotal = Math.round(costoTotal * 100.0) / 100.0;

                costoPromedio = costoTotal / saldo;
                costoPromedio = Math.round(costoPromedio * 10000.0) / 10000.0;
            }
            Kardex kardex = new Kardex(null, notaCredito.getFecha(), notaCredito.getNumeroComprobante(),
                    notaCreditoLinea.getId(), entrada, Constantes.cero, saldo,
                    costoUnitario, Constantes.cero, costoPromedio, costoTotal, new TipoComprobante(4),
                    new TipoOperacion(tipoOperacionId), notaCreditoLinea.getBodega(), notaCreditoLinea.getProducto());

            kardexService.crear(kardex);
        }
    }

    @Override
    public NotaCredito actualizar(NotaCredito notaCredito) {
        validar(notaCredito);
        calcular(notaCredito);
        NotaCredito res = rep.save(notaCredito);
        actualizarKardex(notaCredito);
        res.normalizar();
        return res;
    }

    private void actualizarKardex(NotaCredito notaCredito) {
        for (NotaCreditoLinea notaCreditoLinea : notaCredito.getNotaCreditoLineas()) {
            int ultimoIndiceKardex = notaCreditoLinea.getProducto().getKardexs().size() - 1;
            Kardex ultimoKardex = kardexService.obtenerUltimoPorProductoYBodega(notaCreditoLinea.getProducto().getId(), notaCreditoLinea.getBodega().getId());
            double entrada = Constantes.cero;
            double saldo = Constantes.cero;
            double costoTotal = Constantes.cero;
            double costoUnitario = Constantes.cero;
            double costoPromedio = Constantes.cero;
            if (ultimoIndiceKardex > Constantes.cero) {
                entrada = notaCreditoLinea.getCantidad();
                if(notaCredito.getOperacion().equals(Constantes.operacion_devolucion)) {
                    saldo = notaCreditoLinea.getProducto().getKardexs().get(ultimoIndiceKardex - 1).getSaldo() + entrada;
                }
                if(notaCredito.getOperacion().equals(Constantes.operacion_descuento)) {
                    saldo = notaCreditoLinea.getProducto().getKardexs().get(ultimoIndiceKardex - 1).getSaldo();
                }
                costoUnitario = notaCreditoLinea.getProducto().getKardexs().get(ultimoIndiceKardex - 1).getCostoPromedio();
                costoUnitario = Math.round(costoUnitario * 100.0) / 100.0;

                costoTotal = notaCreditoLinea.getProducto().getKardexs().get(ultimoIndiceKardex - 1).getCostoTotal() + (notaCreditoLinea.getCantidad() * costoUnitario);
                costoTotal = Math.round(costoTotal * 100.0) / 100.0;

                costoPromedio = costoTotal / saldo;
                costoPromedio = Math.round(costoPromedio * 10000.0) / 10000.0;
            }
            ultimoKardex.setFecha(notaCredito.getFecha());
            ultimoKardex.setEntrada(entrada);
            ultimoKardex.setSaldo(saldo);
            ultimoKardex.setDebe(costoUnitario);
            ultimoKardex.setCostoPromedio(costoPromedio);
            ultimoKardex.setCostoTotal(costoTotal);
            kardexService.actualizar(ultimoKardex);
        }
    }

    @Override
    public NotaCredito anular(NotaCredito notaCredito) {
        validar(notaCredito);
        notaCredito.setEstado(Constantes.estadoAnulada);
        notaCredito.setProcesoSRI(Constantes.procesoSRIAnulada);
        NotaCredito res = rep.save(notaCredito);
        res.normalizar();
        return res;
    }

    @Override
    public NotaCredito obtener(long id) {
        Optional<NotaCredito> notaCreditoVenta = rep.findById(id);
        if(notaCreditoVenta.isPresent()) {
            NotaCredito res = notaCreditoVenta.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.factura_compra);
    }

    @Override
    public NotaCredito obtenerPorFactura(long facturaId){
        NotaCredito notaCredito = new NotaCredito();
        Factura factura = facturaService.obtener(facturaId);
        notaCredito.setFactura(factura);
        notaCredito.setOperacion(Constantes.operacion_devolucion);
        notaCredito.setNotaCreditoLineas(new ArrayList<>());
        for(FacturaLinea facturaLinea: factura.getFacturaLineas()){
            NotaCreditoLinea notaCreditoLinea = new NotaCreditoLinea();
            notaCreditoLinea.setImpuesto(facturaLinea.getImpuesto());
            notaCreditoLinea.setProducto(facturaLinea.getProducto());
            notaCreditoLinea.setBodega(facturaLinea.getBodega());
            notaCreditoLinea.setCantidadVenta(facturaLinea.getCantidad());
            double costoUnitarioCompra = facturaLinea.getSubtotalLinea() / facturaLinea.getCantidad();
            costoUnitarioCompra = Math.round(costoUnitarioCompra * 100.0) / 100.0;
            notaCreditoLinea.setCostoUnitarioVenta(costoUnitarioCompra);
            notaCreditoLinea.setCostoUnitario(costoUnitarioCompra);
            notaCredito.getNotaCreditoLineas().add(notaCreditoLinea);
        }
        return notaCredito;
    }

    @Override
    public List<NotaCredito> consultar() {
        return rep.consultar();
    }

    @Override
    public List<NotaCredito> consultarPorEstado(String estado){
        return rep.consultarPorEstado(estado);
    }

    @Override
    public List<NotaCredito> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    @Override
    public List<NotaCredito> consultarPorEmpresaYEstado(long empresaId, String estado){
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }

    @Override
    public List<NotaCredito> consultarPorFacturaYEmpresaYEstadoDiferente(long facturaId, long empresaId, String estado){
        return rep.consultarPorFacturaYEmpresaYEstadoDiferente(facturaId, empresaId, estado);
    }

    @Override
    public Page<NotaCredito> consultarPagina(Pageable pageable){
        return rep.findAll(pageable);
    }

    @Override
    public void validarLinea(NotaCreditoLinea notaCreditoLinea) {
        if(notaCreditoLinea.getCantidadVenta() < Constantes.cero) throw new DatoInvalidoException(Constantes.cantidad);
        if(notaCreditoLinea.getCostoUnitarioVenta() < Constantes.cero) throw new DatoInvalidoException(Constantes.costoUnitario);
        if(notaCreditoLinea.getCantidad() < Constantes.cero) throw new DatoInvalidoException(Constantes.devolucion);
        if(notaCreditoLinea.getCostoUnitario() < Constantes.cero) throw new DatoInvalidoException(Constantes.costoUnitario);
        if(notaCreditoLinea.getImpuesto().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.impuesto);
        if(notaCreditoLinea.getBodega().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.bodega);
        if(notaCreditoLinea.getProducto().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.producto);
        if(notaCreditoLinea.getCantidad() > notaCreditoLinea.getCantidadVenta()) throw new DatoInvalidoException(Constantes.devolucion);
    }

    @Override
    public NotaCredito calcular(NotaCredito notaCredito) {
        if(notaCredito.getOperacion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.operacion);
        double subtotal = Constantes.cero;
        double subtotalGravado = Constantes.cero;
        double subtotalNoGravado = Constantes.cero;
        double importeIva = Constantes.cero;
        for(NotaCreditoLinea notaCreditoLinea : notaCredito.getNotaCreditoLineas()) {
            validarLinea(notaCreditoLinea);
            double subtotalLinea = Constantes.cero;
            if(notaCredito.getOperacion().equals(Constantes.operacion_devolucion)){
                subtotalLinea = notaCreditoLinea.getCantidad() * notaCreditoLinea.getCostoUnitario();
                subtotalLinea = Math.round(subtotalLinea * 100.0) / 100.0;
                notaCreditoLinea.setSubtotalLinea(subtotalLinea);
            }
            if(notaCredito.getOperacion().equals(Constantes.operacion_descuento) && notaCredito.getDescuento() > Constantes.cero) {
                double costoTotal = Constantes.cero;
                for(NotaCreditoLinea notaCreditoCompraCosto : notaCredito.getNotaCreditoLineas()) {
                    costoTotal += notaCreditoCompraCosto.getCantidadVenta() * notaCreditoCompraCosto.getCostoUnitarioVenta();
                }

                double ponderacion = (notaCreditoLinea.getCantidadVenta() * notaCreditoLinea.getCostoUnitarioVenta()) / costoTotal;
                subtotalLinea = (notaCredito.getDescuento() * ponderacion) * 100 / (100 + notaCreditoLinea.getImpuesto().getPorcentaje());
                subtotalLinea = Math.round(subtotalLinea * 100.0) / 100.0;
                notaCreditoLinea.setSubtotalLinea(subtotalLinea);

                double costoUnitario = subtotalLinea / notaCreditoLinea.getCantidad();
                costoUnitario = Math.round(costoUnitario * 100.0) / 100.0;
                notaCreditoLinea.setCostoUnitario(costoUnitario);
            }
            if(notaCredito.getOperacion().equals(Constantes.operacion_descuento) && notaCredito.getDescuento() <= Constantes.cero){
                subtotalLinea = notaCreditoLinea.getCantidad() * notaCreditoLinea.getCostoUnitario();
                subtotalLinea = Math.round(subtotalLinea * 100.0) / 100.0;
                notaCreditoLinea.setSubtotalLinea(subtotalLinea);
            }
            subtotal += subtotalLinea;
            if (notaCreditoLinea.getImpuesto().getPorcentaje() != Constantes.cero){
                subtotalGravado += subtotalLinea;
            } else {
                subtotalNoGravado += subtotalLinea;
            }

            double importeIvaLinea = subtotalLinea * notaCreditoLinea.getImpuesto().getPorcentaje() / 100;
            importeIvaLinea = Math.round(importeIvaLinea * 100.0) / 100.0;
            notaCreditoLinea.setImporteIvaLinea(importeIvaLinea);
            importeIva += importeIvaLinea;

            double totalLinea = subtotalLinea + importeIvaLinea;
            totalLinea = Math.round(totalLinea * 100.0) / 100.0;
            notaCreditoLinea.setTotalLinea(totalLinea);
        }
        subtotal = Math.round(subtotal * 100.0) / 100.0;
        notaCredito.setSubtotal(subtotal);

        subtotalGravado = Math.round(subtotalGravado * 100.0) / 100.0;
        notaCredito.setSubtotalGravado(subtotalGravado);

        subtotalNoGravado = Math.round(subtotalNoGravado * 100.0) / 100.0;
        notaCredito.setSubtotalNoGravado(subtotalNoGravado);

        importeIva = Math.round(importeIva * 100.0) / 100.0;
        notaCredito.setImporteIva(importeIva);

        double total = subtotalGravado + subtotalNoGravado + importeIva;
        total = Math.round(total * 100.0) / 100.0;
        notaCredito.setTotal(total);

        return notaCredito;
    }
}