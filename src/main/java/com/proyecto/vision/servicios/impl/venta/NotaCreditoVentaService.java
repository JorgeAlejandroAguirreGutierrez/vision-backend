package com.proyecto.vision.servicios.impl.venta;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.modelos.compra.FacturaCompra;
import com.proyecto.vision.modelos.compra.FacturaCompraLinea;
import com.proyecto.vision.modelos.compra.NotaCreditoCompra;
import com.proyecto.vision.modelos.compra.NotaCreditoCompraLinea;
import com.proyecto.vision.modelos.configuracion.TipoComprobante;
import com.proyecto.vision.exception.*;
import com.proyecto.vision.modelos.configuracion.Secuencial;
import com.proyecto.vision.modelos.inventario.TipoOperacion;
import com.proyecto.vision.modelos.venta.*;
import com.proyecto.vision.modelos.inventario.Kardex;
import com.proyecto.vision.repositorios.venta.INotaCreditoVentaRepository;
import com.proyecto.vision.servicios.interf.configuracion.ISecuencialService;
import com.proyecto.vision.servicios.interf.venta.IFacturaService;
import com.proyecto.vision.servicios.interf.venta.INotaCreditoVentaService;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotaCreditoVentaService implements INotaCreditoVentaService {
    @Autowired
    private INotaCreditoVentaRepository rep;
    @Autowired
    private ITipoComprobanteService tipoComprobanteService;
    @Autowired
    private IKardexService kardexService;
    @Autowired
    private IFacturaService facturaService;
    @Autowired
    private ISecuencialService secuencialService;

    @Override
    public void validar(NotaCreditoVenta notaCreditoVenta) {
        if(notaCreditoVenta.getEstado().equals(Constantes.estado)) throw new DatoInvalidoException(Constantes.estado);
        if(notaCreditoVenta.getFecha() == null) throw new DatoInvalidoException(Constantes.fecha);
        if(notaCreditoVenta.getSesion().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.sesion);
        if(notaCreditoVenta.getNotaCreditoVentaLineas().isEmpty()) throw new DatoInvalidoException(Constantes.nota_credito_venta_linea);
    }


    @Transactional
    @Override
    public NotaCreditoVenta crear(NotaCreditoVenta notaCreditoVenta) {
        validar(notaCreditoVenta);
        TipoComprobante tipoComprobante = tipoComprobanteService.obtenerPorNombreTabla(Constantes.tabla_nota_credito_venta);
        notaCreditoVenta.setTipoComprobante(tipoComprobante);
        Optional<String>codigo=Util.generarCodigoPorEmpresa(Constantes.tabla_nota_credito_venta, notaCreditoVenta.getEmpresa().getId());
        if (codigo.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        notaCreditoVenta.setCodigo(codigo.get());
        Secuencial secuencial = secuencialService.obtenerPorTipoComprobanteYEstacion(notaCreditoVenta.getTipoComprobante().getId(), notaCreditoVenta.getSesion().getUsuario().getEstacion().getId());
        notaCreditoVenta.setSecuencial(Util.generarSecuencial(secuencial.getNumeroSiguiente()));
        notaCreditoVenta.setNumeroComprobante(notaCreditoVenta.getEstablecimiento() + Constantes.guion + notaCreditoVenta.getPuntoVenta() + Constantes.guion + notaCreditoVenta.getSecuencial());
        notaCreditoVenta.setCodigoNumerico(Util.generarCodigoNumerico(secuencial.getNumeroSiguiente()));
        Optional<String> claveAcceso = crearClaveAcceso(notaCreditoVenta);
        if (claveAcceso.isEmpty()) {
            throw new ClaveAccesoNoExistenteException();
        }
        notaCreditoVenta.setClaveAcceso(claveAcceso.get());
        notaCreditoVenta.setEstadoSri(Constantes.estadoSriPendiente);
        notaCreditoVenta.setEstadoInterno(Constantes.estadoInternoEmitida);
        notaCreditoVenta.setEstado(Constantes.estadoActivo);
        calcular(notaCreditoVenta);
        crearKardex(notaCreditoVenta);
        NotaCreditoVenta res = rep.save(notaCreditoVenta);
        res.normalizar();
        secuencial.setNumeroSiguiente(secuencial.getNumeroSiguiente()+1);
        secuencialService.actualizar(secuencial);
        return res;
    }

    private Optional<String> crearClaveAcceso(NotaCreditoVenta notaCreditoVenta) {
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String fechaEmision = dateFormat.format(notaCreditoVenta.getFecha());
        String tipoComprobante = Constantes.nota_de_credito_sri;
        String numeroRuc = notaCreditoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getIdentificacion();
        String tipoAmbiente = Constantes.pruebas_sri;
        String serie = notaCreditoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getCodigoSRI() + notaCreditoVenta.getSesion().getUsuario().getEstacion().getCodigoSRI();
        String numeroComprobante = notaCreditoVenta.getSecuencial();
        String codigoNumerico = notaCreditoVenta.getCodigoNumerico();
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
    private void crearKardex(NotaCreditoVenta notaCreditoVenta) {
        if(notaCreditoVenta.getEstado().equals(Constantes.estadoInactivo)) throw new DatoInvalidoException(Constantes.estado);
        if(notaCreditoVenta.getEstadoInterno().equals(Constantes.estadoInternoAnulada)) throw new DatoInvalidoException(Constantes.estado);

        for (NotaCreditoVentaLinea notaCreditoVentaLinea : notaCreditoVenta.getNotaCreditoVentaLineas()) {
            Kardex ultimoKardex = kardexService.obtenerUltimoPorBodega(notaCreditoVentaLinea.getBodega().getId(), notaCreditoVentaLinea.getProducto().getId());
            double entrada = Constantes.cero;
            double saldo = Constantes.cero;
            double costoTotal = Constantes.cero;
            double costoUnitario = Constantes.cero;
            double costoPromedio = Constantes.cero;
            long tipoOperacionId = Constantes.ceroId;
            if (ultimoKardex != null) {
                entrada = notaCreditoVentaLinea.getCantidad();
                if(notaCreditoVenta.getOperacion().equals(Constantes.operacion_devolucion)) {
                    saldo = ultimoKardex.getSaldo() + entrada;
                    tipoOperacionId = 7;
                }
                if(notaCreditoVenta.getOperacion().equals(Constantes.operacion_descuento)) {
                    saldo = ultimoKardex.getSaldo();
                    tipoOperacionId = 9;
                }
                costoUnitario = ultimoKardex.getCostoPromedio();
                costoUnitario = Math.round(costoUnitario * 100.0) / 100.0;

                costoTotal = ultimoKardex.getCostoTotal() + (notaCreditoVentaLinea.getCantidad() * costoUnitario);
                costoTotal = Math.round(costoTotal * 100.0) / 100.0;

                costoPromedio = costoTotal / saldo;
                costoPromedio = Math.round(costoPromedio * 10000.0) / 10000.0;
            }
            Kardex kardex = new Kardex(null, notaCreditoVenta.getFecha(),
                    notaCreditoVenta.getNumeroComprobante(), entrada, Constantes.cero, saldo,
                    costoUnitario, Constantes.cero, costoPromedio, costoTotal, new TipoComprobante(4),
                    new TipoOperacion(tipoOperacionId), notaCreditoVentaLinea.getBodega(), notaCreditoVentaLinea.getProducto());

            kardexService.crear(kardex);
        }
    }

    @Override
    public NotaCreditoVenta actualizar(NotaCreditoVenta notaCreditoVenta) {
        validar(notaCreditoVenta);
        calcular(notaCreditoVenta);
        NotaCreditoVenta res = rep.save(notaCreditoVenta);
        actualizarKardex(notaCreditoVenta);
        res.normalizar();
        return res;
    }

    private void actualizarKardex(NotaCreditoVenta notaCreditoVenta) {
        for (NotaCreditoVentaLinea notaCreditoVentaLinea : notaCreditoVenta.getNotaCreditoVentaLineas()) {
            int ultimoIndiceKardex = notaCreditoVentaLinea.getProducto().getKardexs().size() - 1;
            Kardex ultimoKardex = kardexService.obtenerUltimoPorBodega(notaCreditoVentaLinea.getBodega().getId(), notaCreditoVentaLinea.getProducto().getId());
            double entrada = Constantes.cero;
            double saldo = Constantes.cero;
            double costoTotal = Constantes.cero;
            double costoUnitario = Constantes.cero;
            double costoPromedio = Constantes.cero;
            if (ultimoIndiceKardex > Constantes.cero) {
                entrada = notaCreditoVentaLinea.getCantidad();
                if(notaCreditoVenta.getOperacion().equals(Constantes.operacion_devolucion)) {
                    saldo = notaCreditoVentaLinea.getProducto().getKardexs().get(ultimoIndiceKardex - 1).getSaldo() + entrada;
                }
                if(notaCreditoVenta.getOperacion().equals(Constantes.operacion_descuento)) {
                    saldo = notaCreditoVentaLinea.getProducto().getKardexs().get(ultimoIndiceKardex - 1).getSaldo();
                }
                costoUnitario = notaCreditoVentaLinea.getProducto().getKardexs().get(ultimoIndiceKardex - 1).getCostoPromedio();
                costoUnitario = Math.round(costoUnitario * 100.0) / 100.0;

                costoTotal = notaCreditoVentaLinea.getProducto().getKardexs().get(ultimoIndiceKardex - 1).getCostoTotal() + (notaCreditoVentaLinea.getCantidad() * costoUnitario);;
                costoTotal = Math.round(costoTotal * 100.0) / 100.0;

                costoPromedio = costoTotal / saldo;
                costoPromedio = Math.round(costoPromedio * 10000.0) / 10000.0;
            }
            ultimoKardex.setFecha(notaCreditoVenta.getFecha());
            ultimoKardex.setEntrada(entrada);
            ultimoKardex.setSaldo(saldo);
            ultimoKardex.setDebe(costoUnitario);
            ultimoKardex.setCostoPromedio(costoPromedio);
            ultimoKardex.setCostoTotal(costoTotal);
            kardexService.actualizar(ultimoKardex);
        }
    }

    @Override
    public NotaCreditoVenta activar(NotaCreditoVenta notaCreditoVenta) {
        validar(notaCreditoVenta);
        notaCreditoVenta.setEstado(Constantes.estadoActivo);
        NotaCreditoVenta res = rep.save(notaCreditoVenta);
        res.normalizar();
        return res;
    }

    @Override
    public NotaCreditoVenta inactivar(NotaCreditoVenta notaCreditoVenta) {
        validar(notaCreditoVenta);
        notaCreditoVenta.setEstado(Constantes.estadoInactivo);
        NotaCreditoVenta res = rep.save(notaCreditoVenta);
        res.normalizar();
        return res;
    }

    @Override
    public NotaCreditoVenta obtener(long id) {
        Optional<NotaCreditoVenta> notaCreditoVenta = rep.findById(id);
        if(notaCreditoVenta.isPresent()) {
            NotaCreditoVenta res = notaCreditoVenta.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.factura_compra);
    }

    @Override
    public NotaCreditoVenta obtenerPorFactura(long facturaId){
        NotaCreditoVenta notaCreditoVenta = new NotaCreditoVenta();
        Factura factura = facturaService.obtener(facturaId);
        notaCreditoVenta.setFactura(factura);
        notaCreditoVenta.setOperacion(Constantes.operacion_devolucion);
        notaCreditoVenta.setNotaCreditoVentaLineas(new ArrayList<>());
        for(FacturaLinea facturaLinea: factura.getFacturaLineas()){
            NotaCreditoVentaLinea notaCreditoVentaLinea = new NotaCreditoVentaLinea();
            notaCreditoVentaLinea.setImpuesto(facturaLinea.getImpuesto());
            notaCreditoVentaLinea.setProducto(facturaLinea.getProducto());
            notaCreditoVentaLinea.setBodega(facturaLinea.getBodega());
            notaCreditoVentaLinea.setCantidadVenta(facturaLinea.getCantidad());

            double costoUnitarioCompra = facturaLinea.getSubtotalConDescuentoLinea() / facturaLinea.getCantidad();
            costoUnitarioCompra = Math.round(costoUnitarioCompra * 100.0) / 100.0;
            notaCreditoVentaLinea.setCostoUnitarioVenta(costoUnitarioCompra);
            notaCreditoVentaLinea.setCostoUnitario(costoUnitarioCompra);

            notaCreditoVenta.getNotaCreditoVentaLineas().add(notaCreditoVentaLinea);
        }
        return notaCreditoVenta;
    }

    @Override
    public List<NotaCreditoVenta> consultar() {
        return rep.consultar();
    }
    
    @Override
    public List<NotaCreditoVenta> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public List<NotaCreditoVenta> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    @Override
    public List<NotaCreditoVenta> consultarPorEmpresaYEstado(long empresaId, String estado){
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }

    @Override
    public Page<NotaCreditoVenta> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public void validarLinea(NotaCreditoVentaLinea notaCreditoVentaLinea) {
        if(notaCreditoVentaLinea.getCantidadVenta() < Constantes.cero) throw new DatoInvalidoException(Constantes.cantidad);
        if(notaCreditoVentaLinea.getCostoUnitarioVenta() < Constantes.cero) throw new DatoInvalidoException(Constantes.costoUnitario);
        if(notaCreditoVentaLinea.getCantidad() < Constantes.cero) throw new DatoInvalidoException(Constantes.devolucion);
        if(notaCreditoVentaLinea.getCostoUnitario() < Constantes.cero) throw new DatoInvalidoException(Constantes.costoUnitario);
        if(notaCreditoVentaLinea.getImpuesto().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.impuesto);
        if(notaCreditoVentaLinea.getBodega().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.bodega);
        if(notaCreditoVentaLinea.getProducto().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.producto);
        if(notaCreditoVentaLinea.getCantidad() > notaCreditoVentaLinea.getCantidadVenta()) throw new DatoInvalidoException(Constantes.devolucion);
    }

    /*
     * CALCULOS CON NOTA CREDITO VENTA LINEA
     */
    @Override
    public NotaCreditoVenta calcular(NotaCreditoVenta notaCreditoVenta) {
        if(notaCreditoVenta.getOperacion() == Constantes.vacio) throw new DatoInvalidoException(Constantes.operacion_devolucion);
        double subtotal = Constantes.cero;
        double subtotalGravado = Constantes.cero;
        double subtotalNoGravado = Constantes.cero;
        double importeIva = Constantes.cero;
        for(NotaCreditoVentaLinea notaCreditoVentaLinea : notaCreditoVenta.getNotaCreditoVentaLineas()) {
            validarLinea(notaCreditoVentaLinea);
            double subtotalLinea = Constantes.cero;
            if(notaCreditoVenta.getOperacion().equals(Constantes.operacion_devolucion)){
                subtotalLinea = notaCreditoVentaLinea.getCantidad() * notaCreditoVentaLinea.getCostoUnitario();
                subtotalLinea = Math.round(subtotalLinea * 100.0) / 100.0;
                notaCreditoVentaLinea.setSubtotalLinea(subtotalLinea);

            }
            if(notaCreditoVenta.getOperacion().equals(Constantes.operacion_descuento)) {
                if(notaCreditoVenta.getDescuento() <= Constantes.cero) throw new DatoInvalidoException(Constantes.operacion_descuento);

                double costoTotal = Constantes.cero;
                for(NotaCreditoVentaLinea notaCreditoVentaCosto : notaCreditoVenta.getNotaCreditoVentaLineas()) {
                    costoTotal += notaCreditoVentaCosto.getCantidadVenta() * notaCreditoVentaCosto.getCostoUnitarioVenta();
                }

                double ponderacion = (notaCreditoVentaLinea.getCantidadVenta() * notaCreditoVentaLinea.getCostoUnitarioVenta()) / costoTotal;
                subtotalLinea = (notaCreditoVenta.getDescuento() * ponderacion) * 100 / (100 + notaCreditoVentaLinea.getImpuesto().getPorcentaje());
                subtotalLinea = Math.round(subtotalLinea * 100.0) / 100.0;
                notaCreditoVentaLinea.setSubtotalLinea(subtotalLinea);

                double costoUnitario = subtotalLinea / notaCreditoVentaLinea.getCantidad();
                costoUnitario = Math.round(costoUnitario * 100.0) / 100.0;
                notaCreditoVentaLinea.setCostoUnitario(costoUnitario);
            }
            subtotal += subtotalLinea;
            if (notaCreditoVentaLinea.getImpuesto().getPorcentaje() != Constantes.cero){
                subtotalGravado += subtotalLinea;
            } else {
                subtotalNoGravado += subtotalLinea;
            }

            double importeIvaLinea = subtotalLinea * notaCreditoVentaLinea.getImpuesto().getPorcentaje() / 100;
            importeIvaLinea = Math.round(importeIvaLinea * 100.0) / 100.0;
            notaCreditoVentaLinea.setImporteIvaLinea(importeIvaLinea);
            importeIva += importeIvaLinea;

            double totalLinea = subtotalLinea + importeIvaLinea;
            totalLinea = Math.round(totalLinea * 100.0) / 100.0;
            notaCreditoVentaLinea.setTotalLinea(totalLinea);
        }
        subtotal = Math.round(subtotal * 100.0) / 100.0;
        notaCreditoVenta.setSubtotal(subtotal);

        subtotalGravado = Math.round(subtotalGravado * 100.0) / 100.0;
        notaCreditoVenta.setSubtotalGravado(subtotalGravado);

        subtotalNoGravado = Math.round(subtotalNoGravado * 100.0) / 100.0;
        notaCreditoVenta.setSubtotalNoGravado(subtotalNoGravado);

        importeIva = Math.round(importeIva * 100.0) / 100.0;
        notaCreditoVenta.setImporteIva(importeIva);

        double total = subtotalGravado + subtotalNoGravado + importeIva;
        total = Math.round(total * 100.0) / 100.0;
        notaCreditoVenta.setTotal(total);

        return notaCreditoVenta;
    }
}
