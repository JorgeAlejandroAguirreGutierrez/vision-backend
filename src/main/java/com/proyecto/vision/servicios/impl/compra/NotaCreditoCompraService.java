package com.proyecto.vision.servicios.impl.compra;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.*;
import com.proyecto.vision.modelos.configuracion.TipoComprobante;
import com.proyecto.vision.modelos.compra.FacturaCompra;
import com.proyecto.vision.modelos.compra.FacturaCompraLinea;
import com.proyecto.vision.modelos.compra.NotaCreditoCompra;
import com.proyecto.vision.modelos.compra.NotaCreditoCompraLinea;
import com.proyecto.vision.modelos.inventario.Kardex;
import com.proyecto.vision.modelos.inventario.Precio;
import com.proyecto.vision.modelos.inventario.TipoOperacion;
import com.proyecto.vision.repositorios.compra.INotaCreditoCompraRepository;
import com.proyecto.vision.servicios.interf.compra.IFacturaCompraService;
import com.proyecto.vision.servicios.interf.compra.INotaCreditoCompraService;
import com.proyecto.vision.servicios.interf.configuracion.ISecuencialService;
import com.proyecto.vision.servicios.interf.configuracion.ITipoComprobanteService;
import com.proyecto.vision.servicios.interf.inventario.IKardexService;
import com.proyecto.vision.servicios.interf.inventario.IPrecioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class NotaCreditoCompraService implements INotaCreditoCompraService {
    @Autowired
    private INotaCreditoCompraRepository rep;
    @Autowired
    private ITipoComprobanteService tipoComprobanteService;
    @Autowired
    private IKardexService kardexService;
    @Autowired
    private IPrecioService precioService;
    @Autowired
    private IFacturaCompraService facturaCompraService;
    @Autowired
    private ISecuencialService secuencialService;

    @Override
    public void validar(NotaCreditoCompra notaCreditoCompra) {
        if(notaCreditoCompra.getEstablecimiento().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.establecimiento);
        if(notaCreditoCompra.getPuntoVenta().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.punto_venta);
        if(notaCreditoCompra.getSecuencial().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.secuencial);
        if(notaCreditoCompra.getEstablecimiento().length() > 3) throw new DatoInvalidoException(Constantes.establecimiento);
        if(notaCreditoCompra.getPuntoVenta().length() > 3) throw new DatoInvalidoException(Constantes.punto_venta);
        if(notaCreditoCompra.getSecuencial().length() > 9) throw new DatoInvalidoException(Constantes.secuencial);
        if(notaCreditoCompra.getFecha() == null) throw new DatoInvalidoException(Constantes.fecha);
        if(notaCreditoCompra.getUsuario().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.sesion);
        if(notaCreditoCompra.getNotaCreditoCompraLineas().isEmpty()) throw new DatoInvalidoException(Constantes.nota_credito_compra_linea);
    }

    @Transactional
    @Override
    public NotaCreditoCompra crear(NotaCreditoCompra notaCreditoCompra) {
        validar(notaCreditoCompra);
        //List<NotaCreditoCompra> notaCreditoExistente = rep.consultarPorFacturaCompraYEmpresaYEstadoDiferente(notaCreditoCompra.getFacturaCompra().getId(), notaCreditoCompra.getEmpresa().getId(), Constantes.estadoAnulada);
        //if(!notaCreditoExistente.isEmpty()){
        //    throw new EntidadExistenteException(Constantes.nota_credito_compra);
        //}
        TipoComprobante tipoComprobante = tipoComprobanteService.obtenerPorNombreTabla(Constantes.tabla_nota_credito_compra);
        notaCreditoCompra.setTipoComprobante(tipoComprobante);
        Optional<String> codigo = Util.generarCodigoPorEmpresa(notaCreditoCompra.getFecha(), Constantes.tabla_nota_credito_compra, notaCreditoCompra.getEmpresa().getId());
        if (codigo.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        notaCreditoCompra.setCodigo(codigo.get());
        notaCreditoCompra.setEstado(Constantes.estadoPorPagar);
        //calcular(notaCreditoCompra);
        NotaCreditoCompra res = rep.save(notaCreditoCompra);
        crearKardex(notaCreditoCompra);
        actualizarPrecios(notaCreditoCompra);
        res.normalizar();
        return res;
    }

    private void crearKardex(NotaCreditoCompra notaCreditoCompra) {
        if(notaCreditoCompra.getEstado().equals(Constantes.estadoAnulada)) throw new EstadoInvalidoException(Constantes.estadoAnulada);

        for (NotaCreditoCompraLinea notaCreditoCompraLinea : notaCreditoCompra.getNotaCreditoCompraLineas()) {
            Kardex ultimoKardex = kardexService.obtenerUltimoPorProductoYBodegaYFecha(notaCreditoCompraLinea.getProducto().getId(), notaCreditoCompraLinea.getBodega().getId(), notaCreditoCompra.getFecha());
            double entrada = Constantes.cero, saldo = Constantes.cero, costoTotal = Constantes.cero;
            double costoUnitario = Constantes.cero, costoPromedio = Constantes.cero;
            long tipoOperacionId = Constantes.ceroId;
            if (ultimoKardex != null) {
                entrada = notaCreditoCompraLinea.getCantidad()*(-1);
                if(notaCreditoCompra.getOperacion().equals(Constantes.operacion_devolucion)) {
                    saldo = ultimoKardex.getSaldo() + entrada;
                    tipoOperacionId = 6;
                }
                if(notaCreditoCompra.getOperacion().equals(Constantes.operacion_descuento)) {
                    saldo = ultimoKardex.getSaldo();
                    tipoOperacionId = 8;
                }
                costoUnitario = notaCreditoCompraLinea.getCostoUnitario();
                costoUnitario = Math.round(costoUnitario * 10000.0) / 10000.0;

                costoTotal = ultimoKardex.getCostoTotal() - notaCreditoCompraLinea.getSubtotalLinea();
                costoTotal = Math.round(costoTotal * 10000.0) / 10000.0;

                costoPromedio = costoTotal / saldo;
                costoPromedio = Math.round(costoPromedio * 10000.0) / 10000.0;
            }
            TipoComprobante tipoComprobante = tipoComprobanteService.obtenerPorNombreTabla(Constantes.tabla_nota_credito_compra);
            Kardex kardex = new Kardex(null, notaCreditoCompra.getFecha(), notaCreditoCompra.getNumeroComprobante(),
                    notaCreditoCompraLinea.getId(), entrada, Constantes.cero, saldo,
                    costoUnitario, Constantes.cero, costoPromedio, costoTotal, Constantes.estadoActivo, tipoComprobante,
                    new TipoOperacion(tipoOperacionId), notaCreditoCompraLinea.getBodega(), notaCreditoCompraLinea.getProducto());

            kardexService.crear(kardex);
            kardexService.recalcularPorProductoYBodegaYFecha(notaCreditoCompraLinea.getProducto().getId(), notaCreditoCompraLinea.getBodega().getId(),notaCreditoCompra.getFecha());
        }
    }

    @Override
    public NotaCreditoCompra actualizar(NotaCreditoCompra notaCreditoCompra) {
        validar(notaCreditoCompra);
        calcular(notaCreditoCompra);
        NotaCreditoCompra res = rep.save(notaCreditoCompra);
        actualizarKardex(notaCreditoCompra);
        actualizarPrecios(notaCreditoCompra);
        res.normalizar();
        return res;
    }

    private void actualizarKardex(NotaCreditoCompra notaCreditoCompra) {
        for (NotaCreditoCompraLinea notaCreditoCompraLinea : notaCreditoCompra.getNotaCreditoCompraLineas()) {
            TipoComprobante tipoComprobante = tipoComprobanteService.obtenerPorNombreTabla(Constantes.tabla_nota_credito_compra);
            Kardex ultimoKardex = kardexService.obtenerPorProductoYBodegaYTipoComprobanteYComprobanteYIdLinea(notaCreditoCompraLinea.getProducto().getId(), notaCreditoCompraLinea.getBodega().getId(), tipoComprobante.getId(), notaCreditoCompra.getNumeroComprobante(), notaCreditoCompraLinea.getId());
            if (ultimoKardex == null) {
                ultimoKardex = kardexService.obtenerUltimoPorProductoYBodegaYFecha(notaCreditoCompraLinea.getProducto().getId(), notaCreditoCompraLinea.getBodega().getId(), notaCreditoCompra.getFecha());
                double entrada, saldo = Constantes.cero, costoTotal;
                double costoUnitario, costoPromedio;
                long tipoOperacionId = Constantes.ceroId;
                if (ultimoKardex != null) {
                    entrada = notaCreditoCompraLinea.getCantidad()*(-1);
                    if(notaCreditoCompra.getOperacion().equals(Constantes.operacion_devolucion)) {
                        saldo = ultimoKardex.getSaldo() + entrada;
                        tipoOperacionId = 6;
                    }
                    if(notaCreditoCompra.getOperacion().equals(Constantes.operacion_descuento)) {
                        saldo = ultimoKardex.getSaldo();
                        tipoOperacionId = 8;
                    }
                    costoUnitario = notaCreditoCompraLinea.getCostoUnitario();
                    costoUnitario = Math.round(costoUnitario * 10000.0) / 10000.0;

                    costoTotal = ultimoKardex.getCostoTotal() - notaCreditoCompraLinea.getSubtotalLinea();
                    costoTotal = Math.round(costoTotal * 10000.0) / 10000.0;

                    costoPromedio = costoTotal / saldo;
                    costoPromedio = Math.round(costoPromedio * 10000.0) / 10000.0;
                } else{
                    throw new DatoInvalidoException(Constantes.kardex);
                }
                Kardex kardex = new Kardex(null, notaCreditoCompra.getFecha(), notaCreditoCompra.getNumeroComprobante(),
                        notaCreditoCompraLinea.getId(), entrada, Constantes.cero, saldo,
                        costoUnitario, Constantes.cero, costoPromedio, costoTotal, Constantes.estadoActivo, tipoComprobante,
                        new TipoOperacion(tipoOperacionId), notaCreditoCompraLinea.getBodega(), notaCreditoCompraLinea.getProducto());

                kardexService.crear(kardex);

            } else {
                Kardex penultimoKardex = kardexService.obtenerPenultimoPorProductoYBodegaYMismaFechaYId(notaCreditoCompraLinea.getProducto().getId(), notaCreditoCompraLinea.getBodega().getId(), notaCreditoCompra.getFecha(), ultimoKardex.getId());
                if (penultimoKardex == null) {
                    penultimoKardex = kardexService.obtenerPenultimoPorProductoYBodegaYMenorFecha(notaCreditoCompraLinea.getProducto().getId(), notaCreditoCompraLinea.getBodega().getId(), notaCreditoCompra.getFecha());
                }
                double entrada, saldo = Constantes.cero, costoTotal, costoUnitario, costoPromedio;

                entrada = notaCreditoCompraLinea.getCantidad() * (-1);
                if (notaCreditoCompra.getOperacion().equals(Constantes.operacion_devolucion)) {
                    saldo = penultimoKardex.getSaldo() + entrada;
                }
                if (notaCreditoCompra.getOperacion().equals(Constantes.operacion_descuento)) {
                    saldo = penultimoKardex.getSaldo();
                }
                costoTotal = penultimoKardex.getCostoTotal() + notaCreditoCompraLinea.getSubtotalLinea();
                costoTotal = Math.round(costoTotal * 10000.0) / 10000.0;
                costoUnitario = notaCreditoCompraLinea.getSubtotalLinea() / notaCreditoCompraLinea.getCantidad();
                costoUnitario = Math.round(costoUnitario * 10000.0) / 10000.0;
                costoPromedio = costoTotal / saldo;
                costoPromedio = Math.round(costoPromedio * 10000.0) / 10000.0;

                ultimoKardex.setFecha(notaCreditoCompra.getFecha());
                ultimoKardex.setEntrada(entrada);
                ultimoKardex.setSaldo(saldo);
                ultimoKardex.setDebe(costoUnitario);
                ultimoKardex.setCostoPromedio(costoPromedio);
                ultimoKardex.setCostoTotal(costoTotal);

                kardexService.actualizar(ultimoKardex);
            }
            Calendar c = Calendar.getInstance();
            c.setTime(notaCreditoCompra.getFecha());
            c.add(c.DAY_OF_YEAR, -1);
            kardexService.recalcularPorProductoYBodegaYFecha(notaCreditoCompraLinea.getProducto().getId(), notaCreditoCompraLinea.getBodega().getId(), c.getTime());
        }
    }

    private void actualizarPrecios(NotaCreditoCompra notaCreditoCompra) {
        for (NotaCreditoCompraLinea notaCreditoCompraLinea : notaCreditoCompra.getNotaCreditoCompraLineas()) {
            Kardex ultimoKardex = kardexService.obtenerUltimoPorProductoYBodegaYEstado(notaCreditoCompraLinea.getProducto().getId(), notaCreditoCompraLinea.getBodega().getId(), Constantes.estadoActivo);
            for (Precio precio : notaCreditoCompraLinea.getProducto().getPrecios()) {
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
    public NotaCreditoCompra anular(NotaCreditoCompra notaCreditoCompra) {
        validar(notaCreditoCompra);
        notaCreditoCompra.setEstado(Constantes.estadoAnulada);
        NotaCreditoCompra res = rep.save(notaCreditoCompra);
        res.normalizar();
        return res;
    }

    @Override
    public NotaCreditoCompra obtener(long id) {
        Optional<NotaCreditoCompra> notaCreditoCompra = rep.findById(id);
        if(notaCreditoCompra.isPresent()) {
            NotaCreditoCompra res = notaCreditoCompra.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.factura_compra);
    }

    @Override
    public NotaCreditoCompra obtenerPorFacturaCompra(long facturaCompraId){
        NotaCreditoCompra notaCreditoCompra = new NotaCreditoCompra();
        FacturaCompra facturaCompra = facturaCompraService.obtener(facturaCompraId);
        notaCreditoCompra.setFacturaCompra(facturaCompra);
        notaCreditoCompra.setOperacion(Constantes.operacion_devolucion);
        notaCreditoCompra.setNotaCreditoCompraLineas(new ArrayList<>());
        for(FacturaCompraLinea facturaCompraLinea: facturaCompra.getFacturaCompraLineas()){
            NotaCreditoCompraLinea notaCreditoCompraLinea = new NotaCreditoCompraLinea();
            notaCreditoCompraLinea.setImpuesto(facturaCompraLinea.getImpuesto());
            notaCreditoCompraLinea.setProducto(facturaCompraLinea.getProducto());
            notaCreditoCompraLinea.setBodega(facturaCompraLinea.getBodega());
            notaCreditoCompraLinea.setCantidadCompra(facturaCompraLinea.getCantidad());

            double costoUnitarioCompra = facturaCompraLinea.getSubtotalLinea() / facturaCompraLinea.getCantidad();
            costoUnitarioCompra = Math.round(costoUnitarioCompra * 100.0) / 100.0;
            notaCreditoCompraLinea.setCostoUnitarioCompra(costoUnitarioCompra);
            notaCreditoCompraLinea.setCostoUnitario(costoUnitarioCompra);

            notaCreditoCompra.getNotaCreditoCompraLineas().add(notaCreditoCompraLinea);
        }
        return notaCreditoCompra;
    }

    public List<NotaCreditoCompra> consultarPorFacturaCompraYEmpresaYEstadoDiferente(long facturaCompraId, long empresaId, String estado){
        return rep.consultarPorFacturaCompraYEmpresaYEstadoDiferente(facturaCompraId, empresaId, estado);
    }

    @Override
    public List<NotaCreditoCompra> consultar() {
        return rep.consultar();
    }

    @Override
    public List<NotaCreditoCompra> consultarPorEstado(String estado){
        return rep.consultarPorEstado(estado);
    }

    @Override
    public List<NotaCreditoCompra> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    @Override
    public List<NotaCreditoCompra> consultarPorEmpresaYEstado(long empresaId, String estado){
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }

    /*
     * CALCULOS CON NOTA CREDITO COMPRA LINEAS
     */
    @Override
    public void validarLinea(NotaCreditoCompraLinea notaCreditoCompraLinea) {
        if(notaCreditoCompraLinea.getCantidadCompra() < Constantes.cero) throw new DatoInvalidoException(Constantes.cantidad);
        if(notaCreditoCompraLinea.getCostoUnitarioCompra() < Constantes.cero) throw new DatoInvalidoException(Constantes.costoUnitario);
        if(notaCreditoCompraLinea.getCantidad() < Constantes.cero) throw new DatoInvalidoException(Constantes.devolucion);
        if(notaCreditoCompraLinea.getCostoUnitario() < Constantes.cero) throw new DatoInvalidoException(Constantes.costoUnitario);
        if(notaCreditoCompraLinea.getImpuesto().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.impuesto);
        if(notaCreditoCompraLinea.getProducto().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.producto);
        if(notaCreditoCompraLinea.getBodega().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.bodega);
        if(notaCreditoCompraLinea.getCantidad() > notaCreditoCompraLinea.getCantidadCompra()) throw new DatoInvalidoException(Constantes.devolucion);
    }

    @Override
    public NotaCreditoCompra calcular(NotaCreditoCompra notaCreditoCompra) {
        if(notaCreditoCompra.getOperacion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.operacion_devolucion);
        double subtotal = Constantes.cero, subtotalGravado = Constantes.cero;
        double subtotalNoGravado = Constantes.cero, importeIva = Constantes.cero;
        for(NotaCreditoCompraLinea notaCreditoCompraLinea : notaCreditoCompra.getNotaCreditoCompraLineas()) {
            validarLinea(notaCreditoCompraLinea);
            double subtotalLinea = Constantes.cero, importeIvaLinea = Constantes.cero;
            if(notaCreditoCompra.getOperacion().equals(Constantes.operacion_devolucion)){
                subtotalLinea = notaCreditoCompraLinea.getCantidad() * notaCreditoCompraLinea.getCostoUnitario();
                subtotalLinea = Math.round(subtotalLinea * 100.0) / 100.0;
                notaCreditoCompraLinea.setSubtotalLinea(subtotalLinea);

                if (notaCreditoCompraLinea.getImpuesto().getPorcentaje() != Constantes.cero){
                    subtotalGravado += subtotalLinea;
                } else {
                    subtotalNoGravado += subtotalLinea;
                }
                importeIvaLinea = subtotalLinea * notaCreditoCompraLinea.getImpuesto().getPorcentaje() / 100;

            }
            if(notaCreditoCompra.getOperacion().equals(Constantes.operacion_descuento)) {
                if(notaCreditoCompra.getDescuentoGravado() < Constantes.cero) throw new DatoInvalidoException(Constantes.operacion_descuento);
                if(notaCreditoCompra.getDescuentoNoGravado() < Constantes.cero) throw new DatoInvalidoException(Constantes.operacion_descuento);
                if(notaCreditoCompra.getDescuentoGravado() == Constantes.cero && notaCreditoCompra.getDescuentoNoGravado() == Constantes.cero) throw new DatoInvalidoException(Constantes.operacion_descuento);

                double costoTotal = Constantes.cero;
                for(NotaCreditoCompraLinea notaCreditoCompraCosto : notaCreditoCompra.getNotaCreditoCompraLineas()) {
                    costoTotal += notaCreditoCompraCosto.getCantidadCompra() * notaCreditoCompraCosto.getCostoUnitarioCompra();
                }

                double ponderacion = (notaCreditoCompraLinea.getCantidadCompra() * notaCreditoCompraLinea.getCostoUnitarioCompra()) / costoTotal;
                subtotalLinea = (notaCreditoCompra.getDescuentoNoGravado() + notaCreditoCompra.getDescuentoGravado()) * ponderacion;
                subtotalLinea = Math.round(subtotalLinea * 100.0) / 100.0;
                notaCreditoCompraLinea.setSubtotalLinea(subtotalLinea);

                importeIvaLinea = (notaCreditoCompra.getDescuentoGravado() * ponderacion) * (Constantes.iva12 / 100);

                double costoUnitario = subtotalLinea / notaCreditoCompraLinea.getCantidad();
                costoUnitario = Math.round(costoUnitario * 100.0) / 100.0;
                notaCreditoCompraLinea.setCostoUnitario(costoUnitario);
            }
            subtotal += subtotalLinea;

            importeIvaLinea = Math.round(importeIvaLinea * 100.0) / 100.0;
            notaCreditoCompraLinea.setImporteIvaLinea(importeIvaLinea);
            importeIva += importeIvaLinea;

            double totalLinea = subtotalLinea + importeIvaLinea;
            totalLinea = Math.round(totalLinea * 100.0) / 100.0;
            notaCreditoCompraLinea.setTotalLinea(totalLinea);
        }
        subtotal = Math.round(subtotal * 100.0) / 100.0;
        notaCreditoCompra.setSubtotal(subtotal);

        if(notaCreditoCompra.getOperacion().equals(Constantes.operacion_descuento)) {
            subtotalGravado = notaCreditoCompra.getDescuentoGravado();
            subtotalNoGravado = notaCreditoCompra.getDescuentoNoGravado();
        }

        subtotalGravado = Math.round(subtotalGravado * 100.0) / 100.0;
        notaCreditoCompra.setSubtotalGravado(subtotalGravado);

        subtotalNoGravado = Math.round(subtotalNoGravado * 100.0) / 100.0;
        notaCreditoCompra.setSubtotalNoGravado(subtotalNoGravado);

        importeIva = Math.round(importeIva * 100.0) / 100.0;
        notaCreditoCompra.setImporteIva(importeIva);

        double total = subtotalGravado + subtotalNoGravado + importeIva;
        total = Math.round(total * 100.0) / 100.0;
        notaCreditoCompra.setTotal(total);

        return notaCreditoCompra;
    }
}