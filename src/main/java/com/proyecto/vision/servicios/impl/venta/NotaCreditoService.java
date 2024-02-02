package com.proyecto.vision.servicios.impl.venta;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.modelos.compra.NotaCreditoCompra;
import com.proyecto.vision.modelos.compra.NotaCreditoCompraLinea;
import com.proyecto.vision.modelos.configuracion.TipoComprobante;
import com.proyecto.vision.exception.*;
import com.proyecto.vision.modelos.configuracion.Secuencial;
import com.proyecto.vision.modelos.inventario.Precio;
import com.proyecto.vision.modelos.inventario.TipoOperacion;
import com.proyecto.vision.modelos.venta.*;
import com.proyecto.vision.modelos.inventario.Kardex;
import com.proyecto.vision.repositorios.venta.INotaCreditoRepository;
import com.proyecto.vision.servicios.interf.configuracion.ISecuencialService;
import com.proyecto.vision.servicios.interf.inventario.IPrecioService;
import com.proyecto.vision.servicios.interf.inventario.ITipoOperacionService;
import com.proyecto.vision.servicios.interf.venta.IFacturaService;
import com.proyecto.vision.servicios.interf.venta.INotaCreditoService;
import com.proyecto.vision.servicios.interf.configuracion.ITipoComprobanteService;
import com.proyecto.vision.servicios.interf.inventario.IKardexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class NotaCreditoService implements INotaCreditoService {
    @Autowired
    private INotaCreditoRepository rep;
    @Autowired
    private ITipoComprobanteService tipoComprobanteService;
    @Autowired
    private ITipoOperacionService tipoOperacionService;
    @Autowired
    private IKardexService kardexService;
    @Autowired
    private IPrecioService precioService;
    @Autowired
    private IFacturaService facturaService;
    @Autowired
    private ISecuencialService secuencialService;
    @Value("${facturacion.produccion}")
    private String facturacionProduccion;

    @Override
    public void validar(NotaCredito notaCredito) {
        if(notaCredito.getEstado().equals(Constantes.estadoAnulada)) throw new EstadoInvalidoException(Constantes.estadoAnulada);
        if(notaCredito.getProcesoSRI().equals(Constantes.procesoSRIAutorizada)) throw new EstadoInvalidoException(Constantes.procesoSRIAutorizada);
        if(notaCredito.getProcesoSRI().equals(Constantes.procesoSRIAnulada)) throw new EstadoInvalidoException(Constantes.procesoSRIAnulada);
        if(notaCredito.getEmpresa().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.empresa);
        if(notaCredito.getFecha() == null) throw new DatoInvalidoException(Constantes.fecha);
        if(notaCredito.getUsuario().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.usuario);
        if(notaCredito.getNotaCreditoLineas().isEmpty()) throw new DatoInvalidoException(Constantes.nota_credito_venta_linea);
    }

    @Transactional
    @Override
    public NotaCredito crear(NotaCredito notaCredito) {
        validar(notaCredito);
        List<NotaCredito> notaCreditoExistente = rep.consultarPorFacturaYEmpresaYEstadoDiferente(notaCredito.getFactura().getId(), notaCredito.getEmpresa().getId(), Constantes.estadoAnulada);
        if(!notaCreditoExistente.isEmpty()){
            throw new EntidadExistenteException(Constantes.nota_credito);
        }
        TipoComprobante tipoComprobante = tipoComprobanteService.obtenerPorNombreTabla(Constantes.tabla_nota_credito);
        notaCredito.setTipoComprobante(tipoComprobante);
        Optional<String>codigo=Util.generarCodigoPorEmpresa(notaCredito.getFecha(), Constantes.tabla_nota_credito, notaCredito.getEmpresa().getId());
        if (codigo.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        notaCredito.setCodigo(codigo.get());
        Secuencial secuencial = secuencialService.obtenerPorTipoComprobanteYEstacionYEmpresaYEstado(notaCredito.getTipoComprobante().getId(),
                notaCredito.getUsuario().getEstacion().getId(), notaCredito.getEmpresa().getId(), Constantes.estadoActivo);
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
        NotaCredito res = rep.save(notaCredito);
        crearKardex(notaCredito);
        actualizarPrecios(notaCredito);
        res.normalizar();
        secuencial.setNumeroSiguiente(secuencial.getNumeroSiguiente()+1);
        secuencialService.actualizar(secuencial);
        return res;
    }

    private Optional<String> crearClaveAcceso(NotaCredito notaCredito) {
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String fechaEmision = dateFormat.format(notaCredito.getFecha());
        String tipoComprobante = Constantes.nota_credito_sri;
        String numeroRuc = notaCredito.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getIdentificacion();
        String tipoAmbiente = Constantes.vacio;
        if(facturacionProduccion.equals(Constantes.si)){
            tipoAmbiente = Constantes.produccion_sri;
        }
        if(facturacionProduccion.equals(Constantes.no)){
            tipoAmbiente = Constantes.pruebas_sri;
        }
        String serie = notaCredito.getUsuario().getEstacion().getEstablecimiento().getCodigoSRI() + notaCredito.getUsuario().getEstacion().getCodigoSRI();
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
            if (notaCreditoLinea.getProducto().getCategoriaProducto().getAbreviatura().equals(Constantes.abreviatura_bien)){
                Kardex ultimoKardex = kardexService.obtenerUltimoPorProductoYBodegaYFecha(notaCreditoLinea.getProducto().getId(), notaCreditoLinea.getBodega().getId(), notaCredito.getFecha());
                double entrada = Constantes.cero, saldo = Constantes.cero, costoTotal = Constantes.cero;
                double costoUnitario = Constantes.cero, costoPromedio = Constantes.cero;
                TipoOperacion tipoOperacion = null;
                if (ultimoKardex != null) {
                    entrada = notaCreditoLinea.getCantidad();
                    if(notaCredito.getOperacion().equals(Constantes.operacion_devolucion)) {
                        saldo = ultimoKardex.getSaldo() + entrada;
                        tipoOperacion = tipoOperacionService.obtenerPorAbreviaturaYEstado(Constantes.devolucionVenta, Constantes.estadoActivo);
                    }
                    if(notaCredito.getOperacion().equals(Constantes.operacion_descuento)) {
                        saldo = ultimoKardex.getSaldo();
                        tipoOperacion = tipoOperacionService.obtenerPorAbreviaturaYEstado(Constantes.descuentoVenta, Constantes.estadoActivo);
                    }
                    costoUnitario = notaCreditoLinea.getCostoUnitario();
                    costoUnitario = Math.round(costoUnitario * 10000.0) / 10000.0;

                    costoTotal = ultimoKardex.getCostoTotal() + (notaCreditoLinea.getCantidad() * costoUnitario);
                    costoTotal = Math.round(costoTotal * 10000.0) / 10000.0;

                    costoPromedio = costoTotal / saldo;
                    costoPromedio = Math.round(costoPromedio * 10000.0) / 10000.0;
                }
                Kardex kardex = new Kardex(null, notaCredito.getFecha(), notaCredito.getNumeroComprobante(),
                        notaCreditoLinea.getId(), entrada, Constantes.cero, saldo, costoUnitario, Constantes.cero,
                        costoPromedio, costoTotal, Constantes.estadoActivo, new TipoComprobante(4),
                        tipoOperacion, notaCreditoLinea.getBodega(), notaCreditoLinea.getProducto());

                kardexService.crear(kardex);
                kardexService.recalcularPorProductoYBodegaYFecha(notaCreditoLinea.getProducto().getId(), notaCreditoLinea.getBodega().getId(),notaCredito.getFecha());
            }
        }
    }

    @Override
    public NotaCredito actualizar(NotaCredito notaCredito) {
        validar(notaCredito);
        calcular(notaCredito);
        NotaCredito res = rep.save(notaCredito);
        actualizarKardex(notaCredito);
        actualizarPrecios(notaCredito);
        res.normalizar();
        return res;
    }

    private void actualizarKardex(NotaCredito notaCredito) {
        for (NotaCreditoLinea notaCreditoLinea : notaCredito.getNotaCreditoLineas()) {
            TipoComprobante tipoComprobante = tipoComprobanteService.obtenerPorNombreTabla(Constantes.tabla_nota_credito);
            Kardex ultimoKardex = kardexService.obtenerPorProductoYBodegaYTipoComprobanteYComprobanteYIdLinea(notaCreditoLinea.getProducto().getId(), notaCreditoLinea.getBodega().getId(), tipoComprobante.getId(), notaCredito.getNumeroComprobante(), notaCreditoLinea.getId());
            if (ultimoKardex == null) {
                ultimoKardex = kardexService.obtenerUltimoPorProductoYBodegaYFecha(notaCreditoLinea.getProducto().getId(), notaCreditoLinea.getBodega().getId(), notaCredito.getFecha());
                double entrada = Constantes.cero, saldo = Constantes.cero, costoTotal = Constantes.cero;
                double costoUnitario = Constantes.cero, costoPromedio = Constantes.cero;
                TipoOperacion tipoOperacion = null;
                if (ultimoKardex != null) {
                    entrada = notaCreditoLinea.getCantidad();
                    if(notaCredito.getOperacion().equals(Constantes.operacion_devolucion)) {
                        saldo = ultimoKardex.getSaldo() + entrada;
                        tipoOperacion = tipoOperacionService.obtenerPorAbreviaturaYEstado(Constantes.devolucionVenta, Constantes.estadoActivo);
                    }
                    if(notaCredito.getOperacion().equals(Constantes.operacion_descuento)) {
                        saldo = ultimoKardex.getSaldo();
                        tipoOperacion = tipoOperacionService.obtenerPorAbreviaturaYEstado(Constantes.descuentoVenta, Constantes.estadoActivo);
                    }
                    costoUnitario = notaCreditoLinea.getCostoUnitario();
                    costoUnitario = Math.round(costoUnitario * 10000.0) / 10000.0;

                    costoTotal = ultimoKardex.getCostoTotal() + (notaCreditoLinea.getCantidad() * costoUnitario);
                    costoTotal = Math.round(costoTotal * 10000.0) / 10000.0;

                    costoPromedio = costoTotal / saldo;
                    costoPromedio = Math.round(costoPromedio * 10000.0) / 10000.0;
                }
                Kardex kardex = new Kardex(null, notaCredito.getFecha(), notaCredito.getNumeroComprobante(),
                        notaCreditoLinea.getId(), entrada, Constantes.cero, saldo, costoUnitario, Constantes.cero,
                        costoPromedio, costoTotal, Constantes.estadoActivo, new TipoComprobante(4),
                        tipoOperacion, notaCreditoLinea.getBodega(), notaCreditoLinea.getProducto());

                kardexService.crear(kardex);
            } else {
                Kardex penultimoKardex = kardexService.obtenerPenultimoPorProductoYBodegaYMismaFechaYId(notaCreditoLinea.getProducto().getId(), notaCreditoLinea.getBodega().getId(), notaCredito.getFecha(), ultimoKardex.getId());
                if (penultimoKardex == null) {
                    penultimoKardex = kardexService.obtenerPenultimoPorProductoYBodegaYMenorFecha(notaCreditoLinea.getProducto().getId(), notaCreditoLinea.getBodega().getId(), notaCredito.getFecha());
                }
                double entrada, saldo = Constantes.cero, costoTotal, costoUnitario, costoPromedio;
                entrada = notaCreditoLinea.getCantidad();
                if (notaCredito.getOperacion().equals(Constantes.operacion_devolucion)) {
                    saldo = penultimoKardex.getSaldo() + entrada;
                }
                if (notaCredito.getOperacion().equals(Constantes.operacion_descuento)) {
                    saldo = penultimoKardex.getSaldo();
                }
                costoTotal = penultimoKardex.getCostoTotal() + notaCreditoLinea.getSubtotalLinea();
                costoTotal = Math.round(costoTotal * 10000.0) / 10000.0;
                costoUnitario = notaCreditoLinea.getSubtotalLinea() / notaCreditoLinea.getCantidad();
                costoUnitario = Math.round(costoUnitario * 10000.0) / 10000.0;
                costoPromedio = costoTotal / saldo;
                costoPromedio = Math.round(costoPromedio * 10000.0) / 10000.0;

                ultimoKardex.setFecha(notaCredito.getFecha());
                ultimoKardex.setEntrada(entrada);
                ultimoKardex.setSaldo(saldo);
                ultimoKardex.setDebe(costoUnitario);
                ultimoKardex.setCostoPromedio(costoPromedio);
                ultimoKardex.setCostoTotal(costoTotal);

                kardexService.actualizar(ultimoKardex);
            }
            Calendar c = Calendar.getInstance();
            c.setTime(notaCredito.getFecha());
            c.add(c.DAY_OF_YEAR, -1);
            kardexService.recalcularPorProductoYBodegaYFecha(notaCreditoLinea.getProducto().getId(), notaCreditoLinea.getBodega().getId(), c.getTime());
        }
    }

    private void actualizarPrecios(NotaCredito notaCredito) {
        for (NotaCreditoLinea notaCreditoLinea : notaCredito.getNotaCreditoLineas()) {
            if(notaCreditoLinea.getProducto().getCategoriaProducto().getAbreviatura().equals(Constantes.abreviatura_bien)){
                Kardex ultimoKardex = kardexService.obtenerUltimoPorProductoYBodegaYEstado(notaCreditoLinea.getProducto().getId(), notaCreditoLinea.getBodega().getId(), Constantes.estadoActivo);
                for (Precio precio : notaCreditoLinea.getProducto().getPrecios()) {
                    precio.setCosto(ultimoKardex.getCostoPromedio());

                    double precioSinIva = ultimoKardex.getCostoPromedio() + (ultimoKardex.getCostoPromedio() * precio.getMargenGanancia() / 100);
                    precioSinIva = Math.round(precioSinIva * 10000.0) / 10000.0;
                    precio.setPrecioSinIva(precioSinIva);

                    double pvp = precioSinIva + (precioSinIva * precio.getProducto().getImpuesto().getPorcentaje() / 100);
                    pvp = Math.round(pvp * 100.0) / 100.0;
                    precio.setPrecioVentaPublico(pvp);

                    double utilidad = precioSinIva - ultimoKardex.getCostoPromedio();
                    utilidad = Math.round(utilidad * 10000.0) / 10000.0;
                    precio.setUtilidad(utilidad);

                    precio.setUtilidadPorcentaje(precio.getMargenGanancia());

                    precioService.actualizar(precio);
                }
            }
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
            notaCreditoLinea.setNombreProducto(facturaLinea.getNombreProducto());
            notaCreditoLinea.setImpuesto(facturaLinea.getImpuesto());
            notaCreditoLinea.setProducto(facturaLinea.getProducto());
            notaCreditoLinea.setBodega(facturaLinea.getBodega());
            notaCreditoLinea.setCantidadVenta(facturaLinea.getCantidad());
            notaCreditoLinea.setCostoUnitarioVenta(facturaLinea.getPrecioUnitario());
            notaCreditoLinea.setCostoUnitario(facturaLinea.getPrecioUnitario());
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
        //if(notaCreditoLinea.getBodega().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.bodega);
        if(notaCreditoLinea.getProducto().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.producto);
        if(notaCreditoLinea.getCantidad() > notaCreditoLinea.getCantidadVenta()) throw new DatoInvalidoException(Constantes.devolucion);
    }

    @Override
    public NotaCredito calcularOperacion(NotaCredito notaCredito) {
        if(notaCredito.getOperacion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.operacion);
        if(notaCredito.getOperacion().equals(Constantes.operacion_devolucion)){
            notaCredito.setValorDescuento(Constantes.cero);
            notaCredito.setPorcentajeDescuento(Constantes.cero);
            notaCredito.setTotalDescuento(Constantes.cero);
            for(NotaCreditoLinea notaCreditoLinea: notaCredito.getNotaCreditoLineas()){
                notaCreditoLinea.setCostoUnitario(notaCreditoLinea.getCostoUnitarioVenta());
                notaCreditoLinea.setCantidad(Constantes.cero);
            }
        }
        if(notaCredito.getOperacion().equals(Constantes.operacion_descuento)){
            for(NotaCreditoLinea notaCreditoLinea: notaCredito.getNotaCreditoLineas()){
                notaCreditoLinea.setCostoUnitario(notaCreditoLinea.getCostoUnitarioVenta());
                notaCreditoLinea.setCantidad(notaCreditoLinea.getCantidadVenta());
            }
        }
        calcular(notaCredito);
        return notaCredito;
    }

    @Override
    public NotaCredito calcular(NotaCredito notaCredito) {
        if(notaCredito.getOperacion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.operacion);
        if(notaCredito.getOperacion().equals(Constantes.operacion_descuento)){
            for(NotaCreditoLinea notaCreditoLinea : notaCredito.getNotaCreditoLineas()) {
                double valorPorcentajeDescuento = notaCreditoLinea.getCantidadVenta() * notaCredito.getPorcentajeDescuento() / 100;
                valorPorcentajeDescuento = Math.round(valorPorcentajeDescuento * 100.0) / 100.0;
                notaCredito.setTotalDescuento(notaCredito.getValorDescuento() + valorPorcentajeDescuento);
            }
        }
        double subtotal = Constantes.cero;
        double subtotalGravado = Constantes.cero;
        double subtotalNoGravado = Constantes.cero;
        double importeIva = Constantes.cero;
        for(NotaCreditoLinea notaCreditoLinea : notaCredito.getNotaCreditoLineas()) {
            validarLinea(notaCreditoLinea);
            double subtotalLinea = Constantes.cero;
            if(notaCredito.getOperacion().equals(Constantes.operacion_devolucion)){
                subtotalLinea = notaCreditoLinea.getCantidad() * notaCreditoLinea.getCostoUnitario();
                subtotalLinea = Math.round(subtotalLinea * 10000.0) / 10000.0;
                notaCreditoLinea.setSubtotalLinea(subtotalLinea);
            }
            if(notaCredito.getOperacion().equals(Constantes.operacion_descuento) && (notaCredito.getValorDescuento() > Constantes.cero || notaCredito.getPorcentajeDescuento() > Constantes.cero)) {
                double costoTotal = Constantes.cero;
                for(NotaCreditoLinea notaCreditoCompraCosto : notaCredito.getNotaCreditoLineas()) {
                    costoTotal += notaCreditoCompraCosto.getCantidadVenta() * notaCreditoCompraCosto.getCostoUnitarioVenta();
                }

                double ponderacion = (notaCreditoLinea.getCantidadVenta() * notaCreditoLinea.getCostoUnitarioVenta()) / costoTotal;
                subtotalLinea = (notaCredito.getTotalDescuento() * ponderacion) * 100 / (100 + notaCreditoLinea.getImpuesto().getPorcentaje());
                subtotalLinea = Math.round(subtotalLinea * 10000.0) / 10000.0;
                notaCreditoLinea.setSubtotalLinea(subtotalLinea);

                double costoUnitario = subtotalLinea / notaCreditoLinea.getCantidad();
                costoUnitario = Math.round(costoUnitario * 10000.0) / 10000.0;
                notaCreditoLinea.setCostoUnitario(costoUnitario);
            }
            if(notaCredito.getOperacion().equals(Constantes.operacion_descuento) && notaCredito.getValorDescuento() <= Constantes.cero && notaCredito.getPorcentajeDescuento() <= Constantes.cero){
                subtotalLinea = notaCreditoLinea.getCantidad() * notaCreditoLinea.getCostoUnitario();
                subtotalLinea = Math.round(subtotalLinea * 10000.0) / 10000.0;
                notaCreditoLinea.setSubtotalLinea(subtotalLinea);
            }
            subtotal += subtotalLinea;
            if (notaCreditoLinea.getImpuesto().getPorcentaje() != Constantes.cero){
                subtotalGravado += subtotalLinea;
            } else {
                subtotalNoGravado += subtotalLinea;
            }

            double importeIvaLinea = subtotalLinea * notaCreditoLinea.getImpuesto().getPorcentaje() / 100;
            importeIvaLinea = Math.round(importeIvaLinea * 10000.0) / 10000.0;
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