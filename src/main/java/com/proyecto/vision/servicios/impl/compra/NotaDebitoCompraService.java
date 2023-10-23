package com.proyecto.vision.servicios.impl.compra;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.EstadoInvalidoException;
import com.proyecto.vision.modelos.configuracion.TipoComprobante;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.compra.*;
import com.proyecto.vision.modelos.inventario.Bodega;
import com.proyecto.vision.modelos.inventario.Kardex;
import com.proyecto.vision.modelos.inventario.Precio;
import com.proyecto.vision.modelos.inventario.TipoOperacion;
import com.proyecto.vision.repositorios.compra.INotaDebitoCompraRepository;
import com.proyecto.vision.servicios.interf.compra.IFacturaCompraService;
import com.proyecto.vision.servicios.interf.compra.INotaDebitoCompraService;
import com.proyecto.vision.servicios.interf.configuracion.ISecuencialService;
import com.proyecto.vision.servicios.interf.configuracion.ITipoComprobanteService;
import com.proyecto.vision.servicios.interf.inventario.IKardexService;
import com.proyecto.vision.servicios.interf.inventario.IPrecioService;
import com.proyecto.vision.servicios.interf.inventario.ITipoOperacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class NotaDebitoCompraService implements INotaDebitoCompraService {
    @Autowired
    private INotaDebitoCompraRepository rep;
    @Autowired
    private ITipoComprobanteService tipoComprobanteService;
    @Autowired
    private ITipoOperacionService tipoOperacionService;
    @Autowired
    private IKardexService kardexService;
    @Autowired
    private IPrecioService precioService;
    @Autowired
    private IFacturaCompraService facturaCompraService;
    @Autowired
    private ISecuencialService secuencialService;

    @Override
    public void validar(NotaDebitoCompra notaDebitoCompra) {
        if(notaDebitoCompra.getEstablecimiento().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.establecimiento);
        if(notaDebitoCompra.getPuntoVenta().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.punto_venta);
        if(notaDebitoCompra.getSecuencial().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.secuencial);
        if(notaDebitoCompra.getEstablecimiento().length() > 3) throw new DatoInvalidoException(Constantes.establecimiento);
        if(notaDebitoCompra.getPuntoVenta().length() > 3) throw new DatoInvalidoException(Constantes.punto_venta);
        if(notaDebitoCompra.getSecuencial().length() > 9) throw new DatoInvalidoException(Constantes.punto_venta);
        if(notaDebitoCompra.getFecha() == null) throw new DatoInvalidoException(Constantes.fecha);
        if(notaDebitoCompra.getUsuario().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.usuario);
        if(notaDebitoCompra.getNotaDebitoCompraLineas().isEmpty()) throw new DatoInvalidoException(Constantes.nota_debito_compra_linea);
    }

    @Transactional
    @Override
    public NotaDebitoCompra crear(NotaDebitoCompra notaDebitoCompra) {
        validar(notaDebitoCompra);
        TipoComprobante tipoComprobante = tipoComprobanteService.obtenerPorNombreTabla(Constantes.tabla_nota_debito_compra);
        notaDebitoCompra.setTipoComprobante(tipoComprobante);
        Optional<String>codigo=Util.generarCodigoPorEmpresa(notaDebitoCompra.getFecha(), Constantes.tabla_nota_debito_compra, notaDebitoCompra.getEmpresa().getId());
        if (codigo.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        notaDebitoCompra.setCodigo(codigo.get());
        notaDebitoCompra.setNumeroComprobante(notaDebitoCompra.getEstablecimiento() + Constantes.guion + notaDebitoCompra.getPuntoVenta() + Constantes.guion + notaDebitoCompra.getSecuencial());
        notaDebitoCompra.setEstado(Constantes.estadoPorPagar);
        //calcular(notaDebitoCompra);
        NotaDebitoCompra res = rep.save(notaDebitoCompra);
        crearKardex(notaDebitoCompra);
        actualizarPrecios(notaDebitoCompra);
        res.normalizar();
        return res;
    }

    private void crearKardex(NotaDebitoCompra notaDebitoCompra) {
        if(notaDebitoCompra.getEstado().equals(Constantes.estadoAnulada)) throw new EstadoInvalidoException(Constantes.estadoAnulada);

        for (NotaDebitoCompraLinea notaDebitoCompraLinea : notaDebitoCompra.getNotaDebitoCompraLineas()) {
            Kardex ultimoKardex = kardexService.obtenerUltimoPorProductoYBodegaYFecha(notaDebitoCompraLinea.getProducto().getId(), 1, notaDebitoCompra.getFecha());
            double entrada = Constantes.cero, saldo = Constantes.cero, costoTotal = Constantes.cero;
            double costoUnitario = Constantes.cero, costoPromedio = Constantes.cero;
            long tipoOperacionId = Constantes.ceroId;
            if (ultimoKardex != null) {
                entrada = notaDebitoCompraLinea.getCantidad();
                if(notaDebitoCompra.getOperacion().equals(Constantes.operacion_devolucion)) {
                    saldo = ultimoKardex.getSaldo() + entrada;
                    tipoOperacionId = 6;
                }
                if(notaDebitoCompra.getOperacion().equals(Constantes.operacion_descuento)) {
                    saldo = ultimoKardex.getSaldo();
                    tipoOperacionId = 8;
                }
                costoUnitario = notaDebitoCompraLinea.getCostoUnitario();
                costoUnitario = Math.round(costoUnitario * 10000.0) / 10000.0;

                costoTotal = ultimoKardex.getCostoTotal() - notaDebitoCompraLinea.getSubtotalLinea();
                costoTotal = Math.round(costoTotal * 10000.0) / 10000.0;

                costoPromedio = costoTotal / saldo;
                costoPromedio = Math.round(costoPromedio * 10000.0) / 10000.0;
            }
            TipoComprobante tipoComprobante = tipoComprobanteService.obtenerPorNombreTabla(Constantes.tabla_nota_debito_compra);
            Kardex kardex = new Kardex(null, notaDebitoCompra.getFecha(), notaDebitoCompra.getNumeroComprobante(),
                    notaDebitoCompraLinea.getId(), entrada, Constantes.cero, saldo,
                    costoUnitario, Constantes.cero, costoPromedio, costoTotal, tipoComprobante,
                    new TipoOperacion(tipoOperacionId), new Bodega(1), notaDebitoCompraLinea.getProducto());

            kardexService.crear(kardex);
            kardexService.recalcularPorProductoYBodegaYFecha(notaDebitoCompraLinea.getProducto().getId(), 1,notaDebitoCompra.getFecha());
        }
    }

    @Override
    public NotaDebitoCompra actualizar(NotaDebitoCompra notaDebitoCompra) {
        validar(notaDebitoCompra);
        notaDebitoCompra.setNumeroComprobante(notaDebitoCompra.getEstablecimiento() + Constantes.guion + notaDebitoCompra.getPuntoVenta() + Constantes.guion + notaDebitoCompra.getSecuencial());
        calcular(notaDebitoCompra);
        NotaDebitoCompra res = rep.save(notaDebitoCompra);
        res.normalizar();
        return res;
    }

    private void actualizarPrecios(NotaDebitoCompra notaDebitoCompra) {
        for (NotaDebitoCompraLinea notaDebitoCompraLinea : notaDebitoCompra.getNotaDebitoCompraLineas()) {
            Kardex ultimoKardex = kardexService.obtenerUltimoPorProductoYBodega(notaDebitoCompraLinea.getProducto().getId(), 1);
            for (Precio precio : notaDebitoCompraLinea.getProducto().getPrecios()) {
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

    @Override
    public NotaDebitoCompra anular(NotaDebitoCompra notaDebitoCompra) {
        validar(notaDebitoCompra);
        notaDebitoCompra.setEstado(Constantes.estadoAnulada);
        NotaDebitoCompra res = rep.save(notaDebitoCompra);
        res.normalizar();
        return res;
    }

    @Override
    public NotaDebitoCompra obtener(long id) {
        Optional<NotaDebitoCompra> notaDebitoCompra = rep.findById(id);
        if(notaDebitoCompra.isPresent()) {
            NotaDebitoCompra res = notaDebitoCompra.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.nota_debito_compra);
    }

    @Override
    public List<NotaDebitoCompra> consultar() {
        return rep.consultar();
    }

    @Override
    public List<NotaDebitoCompra> consultarPorEstado(String estado){
        return rep.consultarPorEstado(estado);
    }

    @Override
    public Page<NotaDebitoCompra> consultarPagina(Pageable pageable){
        return rep.findAll(pageable);
    }

    @Override
    public void validarLinea(NotaDebitoCompraLinea notaDebitoCompraLinea) {
        if(notaDebitoCompraLinea.getCantidad() < Constantes.cero) throw new DatoInvalidoException(Constantes.cantidad);
        if(notaDebitoCompraLinea.getCostoUnitario() < Constantes.cero) throw new DatoInvalidoException(Constantes.costoUnitario);
        if(notaDebitoCompraLinea.getValorDescuentoLinea() < Constantes.cero) throw new DatoInvalidoException(Constantes.valorDescuentoLinea);
        if(notaDebitoCompraLinea.getProducto().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.producto);
    }

    /*
     * CALCULOS CON NOTA DEBITO COMPRA LINEA
     */
    @Override
    public NotaDebitoCompraLinea calcularLinea(NotaDebitoCompraLinea notaDebitoCompraLinea) {
        validarLinea(notaDebitoCompraLinea);

        double subtotalLinea = (notaDebitoCompraLinea.getCostoUnitario() - notaDebitoCompraLinea.getValorDescuentoLinea()) * notaDebitoCompraLinea.getCantidad();
        subtotalLinea = Math.round(subtotalLinea * 10000.0) / 10000.0;
        notaDebitoCompraLinea.setSubtotalLinea(subtotalLinea);

        double importeIvaLinea = (subtotalLinea * (notaDebitoCompraLinea.getImpuesto().getPorcentaje() / 100));
        importeIvaLinea = Math.round(importeIvaLinea * 100.0) / 100.0;
        notaDebitoCompraLinea.setImporteIvaLinea(importeIvaLinea);

        double totalLinea = subtotalLinea + importeIvaLinea;
        totalLinea = Math.round(totalLinea * 100.0) / 100.0;
        notaDebitoCompraLinea.setTotalLinea(totalLinea);

        return notaDebitoCompraLinea;
    }

    @Override
    public NotaDebitoCompra calcular(NotaDebitoCompra notaDebitoCompra) {
        this.validar(notaDebitoCompra);
        for(NotaDebitoCompraLinea notaDebitoCompraLinea: notaDebitoCompra.getNotaDebitoCompraLineas()){
            calcularLinea(notaDebitoCompraLinea);
        }
        this.calcularSubtotal(notaDebitoCompra);
        this.calcularDescuento(notaDebitoCompra);
        this.calcularTotales(notaDebitoCompra);
        return notaDebitoCompra;
    }
    /*
     * CALCULOS TOTALES FACTURA DE VENTA
     */
    private void calcularSubtotal(NotaDebitoCompra notaDebitoCompra) {
        double subtotal = Constantes.cero;
        for(NotaDebitoCompraLinea notaDebitoCompraLinea : notaDebitoCompra.getNotaDebitoCompraLineas()){
            subtotal += notaDebitoCompraLinea.getSubtotalLinea();
        }
        subtotal = Math.round(subtotal * 10000.0) / 10000.0;
        notaDebitoCompra.setSubtotal(subtotal);
    }

    private void calcularDescuento(NotaDebitoCompra notaDebitoCompra) {
        double descuento = Constantes.cero;
        for (NotaDebitoCompraLinea notaDebitoCompraLinea : notaDebitoCompra.getNotaDebitoCompraLineas()) {
            descuento += notaDebitoCompraLinea.getValorDescuentoLinea();
        }
        descuento = Math.round(descuento * 100.0) / 100.0;
        notaDebitoCompra.setDescuento(descuento);
    }

    private void calcularTotales(NotaDebitoCompra notaDebitoCompra) {
        double subtotalGravado = Constantes.cero;
        double subtotalNoGravado = Constantes.cero;
        double importeIva = Constantes.cero;
        double total = Constantes.cero;
        for (NotaDebitoCompraLinea notaDebitoCompraLinea : notaDebitoCompra.getNotaDebitoCompraLineas()) {
            if (notaDebitoCompraLinea.getImpuesto().getPorcentaje() != Constantes.cero) {
                subtotalGravado += notaDebitoCompraLinea.getSubtotalLinea();
            } else {
                subtotalNoGravado += notaDebitoCompraLinea.getSubtotalLinea();
            }
            importeIva += notaDebitoCompraLinea.getImporteIvaLinea();
        }
        subtotalGravado = Math.round(subtotalGravado * 100.0) / 100.0;
        notaDebitoCompra.setSubtotalGravado(subtotalGravado);

        subtotalNoGravado = Math.round(subtotalNoGravado * 100.0) / 100.0;
        notaDebitoCompra.setSubtotalNoGravado(subtotalNoGravado);

        importeIva = Math.round(importeIva * 100.0) / 100.0;
        notaDebitoCompra.setImporteIva(importeIva);

        total = subtotalGravado + subtotalNoGravado + importeIva;
        total = Math.round(total * 100.0) / 100.0;
        notaDebitoCompra.setTotal(total);
    }

    @Override
    public NotaDebitoCompra obtenerPorFacturaCompra(long facturaCompraId){
        NotaDebitoCompra notaDebitoCompra = new NotaDebitoCompra();
        FacturaCompra facturaCompra = facturaCompraService.obtener(facturaCompraId);
        notaDebitoCompra.setFacturaCompra(facturaCompra);
        return notaDebitoCompra;
    }

    @Override
    public List<NotaDebitoCompra> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    @Override
    public List<NotaDebitoCompra> consultarPorEmpresaYEstado(long empresaId, String estado){
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }
}