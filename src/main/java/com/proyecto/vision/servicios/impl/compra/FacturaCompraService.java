package com.proyecto.vision.servicios.impl.compra;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.*;
import com.proyecto.vision.modelos.compra.FacturaCompra;
import com.proyecto.vision.modelos.compra.FacturaCompraLinea;
import com.proyecto.vision.modelos.compra.NotaCreditoCompra;
import com.proyecto.vision.modelos.configuracion.TipoComprobante;
import com.proyecto.vision.modelos.inventario.Precio;
import com.proyecto.vision.modelos.venta.NotaCredito;
import com.proyecto.vision.servicios.interf.compra.INotaCreditoCompraService;
import com.proyecto.vision.servicios.interf.inventario.IPrecioService;
import com.proyecto.vision.modelos.inventario.Kardex;
import com.proyecto.vision.servicios.interf.inventario.IKardexService;
import com.proyecto.vision.modelos.inventario.TipoOperacion;
import com.proyecto.vision.servicios.interf.inventario.ITipoOperacionService;
import com.proyecto.vision.repositorios.compra.IFacturaCompraRepository;
import com.proyecto.vision.servicios.interf.compra.IFacturaCompraService;
import com.proyecto.vision.servicios.interf.configuracion.ISecuencialService;
import com.proyecto.vision.servicios.interf.configuracion.ITipoComprobanteService;
import com.proyecto.vision.servicios.interf.venta.INotaCreditoService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FacturaCompraService implements IFacturaCompraService {
    @Autowired
    private IFacturaCompraRepository rep;
    @Autowired
    private ITipoComprobanteService tipoComprobanteService;
    @Autowired
    private ITipoOperacionService tipoOperacionService;
    @Autowired
    private IKardexService kardexService;
    @Autowired
    private IPrecioService precioService;
    @Autowired
    private INotaCreditoCompraService notaCreditoCompraService;

    @Override
    public void validar(FacturaCompra facturaCompra) {
        if (facturaCompra.getFecha() == null) throw new DatoInvalidoException(Constantes.fecha);
        if (facturaCompra.getProveedor().getId() == Constantes.ceroId)
            throw new DatoInvalidoException(Constantes.proveedor);
        if (facturaCompra.getSesion().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.sesion);
        if (facturaCompra.getFacturaCompraLineas().isEmpty())
            throw new DatoInvalidoException(Constantes.factura_compra_linea);
        for (FacturaCompraLinea facturaCompraLinea : facturaCompra.getFacturaCompraLineas()) {
            validarLinea(facturaCompraLinea);
        }
    }

    @Transactional
    @Override
    public FacturaCompra crear(FacturaCompra facturaCompra) {
        validar(facturaCompra);
        Optional<FacturaCompra> facturaCompraExiste = rep.obtenerPorEstableciminetoYEstacionYSecuencialYProveedor(facturaCompra.getEstablecimiento(), facturaCompra.getPuntoVenta(), facturaCompra.getSecuencial(), facturaCompra.getProveedor().getId());
        if(facturaCompraExiste.isPresent()){
            throw new DatoInvalidoException(Constantes.secuencial);
        }
        for (FacturaCompraLinea facturaCompraLinea : facturaCompra.getFacturaCompraLineas()) {
            Kardex registroInicial = kardexService.obtenerSaldoInicialPorProductoYBodega(facturaCompraLinea.getProducto().getId(), facturaCompraLinea.getBodega().getId());
            Date fechaCompra = DateUtils.truncate(facturaCompra.getFecha(), Calendar.DAY_OF_MONTH);
            Date fechaInicio = DateUtils.truncate(registroInicial.getFecha(), Calendar.DAY_OF_MONTH);
            if (fechaCompra.before(fechaInicio)) {
                throw new DatoInvalidoException(Constantes.fecha);
            }
        }
        TipoComprobante tipoComprobante = tipoComprobanteService.obtenerPorNombreTabla(Constantes.tabla_factura_compra);
        facturaCompra.setTipoComprobante(tipoComprobante);
        Optional<String> codigo = Util.generarCodigoPorEmpresa(Constantes.tabla_factura_compra, facturaCompra.getEmpresa().getId());
        if (codigo.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        facturaCompra.setCodigo(codigo.get());
        facturaCompra.setEstado(Constantes.estadoPorPagar);
        FacturaCompra res = rep.save(facturaCompra);
        crearKardex(facturaCompra);
        actualizarPrecios(facturaCompra);
        res.normalizar();
        return res;
    }

    private void crearKardex(FacturaCompra facturaCompra) {
        for (FacturaCompraLinea facturaCompraLinea : facturaCompra.getFacturaCompraLineas()) {
            Kardex ultimoKardex = kardexService.obtenerUltimoPorProductoYBodegaYFecha(facturaCompraLinea.getProducto().getId(), facturaCompraLinea.getBodega().getId(), facturaCompra.getFecha());
            double saldo, costoTotal, costoUnitario, costoPromedio;
            if (ultimoKardex != null) {
                saldo = ultimoKardex.getSaldo() + facturaCompraLinea.getCantidad();
                costoTotal = ultimoKardex.getCostoTotal() + facturaCompraLinea.getSubtotalLinea();
                costoUnitario = facturaCompraLinea.getSubtotalLinea() / facturaCompraLinea.getCantidad();
                costoUnitario = Math.round(costoUnitario * 10000.0) / 10000.0;
                costoPromedio = costoTotal / saldo;
                costoPromedio = Math.round(costoPromedio * 10000.0) / 10000.0;
            } else{
                throw new DatoInvalidoException(Constantes.kardex);
            }
            TipoComprobante tipoComprobante = tipoComprobanteService.obtenerPorNombreTabla(Constantes.tabla_factura_compra);
            TipoOperacion tipoOperacion = tipoOperacionService.obtenerPorAbreviaturaYEstado(Constantes.compra, Constantes.estadoActivo);
            Kardex kardex = new Kardex(null, facturaCompra.getFecha(), facturaCompra.getNumeroComprobante(),
                    facturaCompraLinea.getId(), facturaCompraLinea.getCantidad(), Constantes.cero, saldo,
                    costoUnitario, Constantes.cero, costoPromedio, costoTotal, tipoComprobante,
                    tipoOperacion, facturaCompraLinea.getBodega(), facturaCompraLinea.getProducto());

            kardexService.crear(kardex);
            kardexService.recalcularPorProductoYBodegaYFecha(facturaCompraLinea.getProducto().getId(), facturaCompraLinea.getBodega().getId(),facturaCompra.getFecha());
        }
    }

    @Override
    public FacturaCompra actualizar(FacturaCompra facturaCompra) {
        validar(facturaCompra);
        List<NotaCreditoCompra> notasCreditosCompras = notaCreditoCompraService.consultarPorFacturaCompraYEmpresaYEstadoDiferente(facturaCompra.getId(), facturaCompra.getEmpresa().getId(), Constantes.estadoAnulada);
        if(!notasCreditosCompras.isEmpty()){
            throw new ErrorInternoException(Constantes.mensaje_error_nota_credito_existente);
        }
        for (FacturaCompraLinea facturaCompraLinea : facturaCompra.getFacturaCompraLineas()) {
            validarLinea(facturaCompraLinea);
            Kardex registroInicial = kardexService.obtenerSaldoInicialPorProductoYBodega(facturaCompraLinea.getProducto().getId(), facturaCompraLinea.getBodega().getId());
            Date fechaCompra = DateUtils.truncate(facturaCompra.getFecha(), Calendar.DAY_OF_MONTH);
            Date fechaInicio = DateUtils.truncate(registroInicial.getFecha(), Calendar.DAY_OF_MONTH);
            if (fechaCompra.before(fechaInicio)) {
                throw new DatoInvalidoException(Constantes.fecha);
            }
            calcularLinea(facturaCompraLinea);
        }
        calcular(facturaCompra);
        FacturaCompra res = rep.save(facturaCompra);
        facturaCompra = obtener(facturaCompra.getId());
        actualizarKardex(facturaCompra);
        eliminarKardex(facturaCompra);
        actualizarPrecios(facturaCompra);
        res.normalizar();
        return res;
    }

    private void actualizarKardex(FacturaCompra facturaCompra) {
        for (FacturaCompraLinea facturaCompraLinea : facturaCompra.getFacturaCompraLineas()) {
            TipoComprobante tipoComprobante = tipoComprobanteService.obtenerPorNombreTabla(Constantes.tabla_factura_compra);
            Kardex ultimoKardex = kardexService.obtenerPorProductoYBodegaYTipoComprobanteYComprobanteYIdLinea(facturaCompraLinea.getProducto().getId(), facturaCompraLinea.getBodega().getId(), tipoComprobante.getId(), facturaCompra.getNumeroComprobante(), facturaCompraLinea.getId());
            if (ultimoKardex == null){
                ultimoKardex = kardexService.obtenerUltimoPorProductoYBodegaYFecha(facturaCompraLinea.getProducto().getId(), facturaCompraLinea.getBodega().getId(), facturaCompra.getFecha());
                double saldo, costoTotal, costoUnitario, costoPromedio;
                if (ultimoKardex != null) {
                    saldo = ultimoKardex.getSaldo() + facturaCompraLinea.getCantidad();
                    costoTotal = ultimoKardex.getCostoTotal() + facturaCompraLinea.getSubtotalLinea();
                    costoUnitario = facturaCompraLinea.getSubtotalLinea() / facturaCompraLinea.getCantidad();
                    costoUnitario = Math.round(costoUnitario * 10000.0) / 10000.0;
                    costoPromedio = costoTotal / saldo;
                    costoPromedio = Math.round(costoPromedio * 10000.0) / 10000.0;
                } else{
                    throw new DatoInvalidoException(Constantes.kardex);
                }
                TipoOperacion tipoOperacion = tipoOperacionService.obtenerPorAbreviaturaYEstado(Constantes.compra, Constantes.estadoActivo);
                Kardex kardex = new Kardex(null, facturaCompra.getFecha(), facturaCompra.getNumeroComprobante(),
                        facturaCompraLinea.getId(), facturaCompraLinea.getCantidad(), Constantes.cero, saldo,
                        costoUnitario, Constantes.cero, costoPromedio, costoTotal, tipoComprobante,
                        tipoOperacion, facturaCompraLinea.getBodega(), facturaCompraLinea.getProducto());
                kardexService.crear(kardex);
            } else {
                Kardex penultimoKardex = kardexService.obtenerPenultimoPorProductoYBodegaYMismaFechaYId(facturaCompraLinea.getProducto().getId(), facturaCompraLinea.getBodega().getId(), facturaCompra.getFecha(), ultimoKardex.getId());
                if (penultimoKardex == null) {
                    penultimoKardex = kardexService.obtenerPenultimoPorProductoYBodegaYMenorFecha(facturaCompraLinea.getProducto().getId(), facturaCompraLinea.getBodega().getId(), facturaCompra.getFecha());
                }
                double saldo, costoTotal, costoUnitario, costoPromedio;

                saldo = penultimoKardex.getSaldo() + facturaCompraLinea.getCantidad();
                costoTotal = penultimoKardex.getCostoTotal() + facturaCompraLinea.getSubtotalLinea();
                costoUnitario = facturaCompraLinea.getSubtotalLinea() / facturaCompraLinea.getCantidad();
                costoUnitario = Math.round(costoUnitario * 10000.0) / 10000.0;
                costoPromedio = costoTotal / saldo;
                costoPromedio = Math.round(costoPromedio * 10000.0) / 10000.0;

                ultimoKardex.setFecha(facturaCompra.getFecha());
                ultimoKardex.setEntrada(facturaCompraLinea.getCantidad());
                ultimoKardex.setSaldo(saldo);
                ultimoKardex.setDebe(costoUnitario);
                ultimoKardex.setCostoPromedio(costoPromedio);
                ultimoKardex.setCostoTotal(costoTotal);
                kardexService.actualizar(ultimoKardex);
            }
            Calendar c = Calendar.getInstance();
            c.setTime(facturaCompra.getFecha());
            c.add(c.DAY_OF_YEAR, -1);
            kardexService.recalcularPorProductoYBodegaYFecha(facturaCompraLinea.getProducto().getId(), facturaCompraLinea.getBodega().getId(), c.getTime());
        }
    }

    private void eliminarKardex(FacturaCompra facturaCompra) {
        TipoComprobante tipoComprobante = tipoComprobanteService.obtenerPorNombreTabla(Constantes.tabla_factura_compra);
        List<Kardex> kardexs = kardexService.consultarPorTipoComprobanteYReferencia(tipoComprobante.getId(), facturaCompra.getNumeroComprobante());
        if (kardexs.isEmpty()) {
            return;
        }
        for (Kardex kardex : kardexs) {
            Boolean lineaEncontrada = false;
            for (FacturaCompraLinea facturaCompraLinea : facturaCompra.getFacturaCompraLineas()) {
                if (kardex.getIdLinea() == facturaCompraLinea.getId()) {
                    lineaEncontrada = true;
                }
            }
            if (!lineaEncontrada) {
                long productoEliminadoId = kardex.getProducto().getId();
                kardex.setProducto(null);
                kardexService.actualizar(kardex);
                Calendar c = Calendar.getInstance();
                c.setTime(facturaCompra.getFecha());
                c.add(c.DAY_OF_YEAR, -1);
                kardexService.recalcularPorProductoYBodegaYFecha(productoEliminadoId, kardex.getBodega().getId(), c.getTime());
            }
        }
    }

    private void actualizarPrecios(FacturaCompra facturaCompra) {
        for (FacturaCompraLinea facturaCompraLinea : facturaCompra.getFacturaCompraLineas()) {
            Kardex ultimoKardex = kardexService.obtenerUltimoPorProductoYBodega(facturaCompraLinea.getProducto().getId(), facturaCompraLinea.getBodega().getId());
            for (Precio precio : facturaCompraLinea.getProducto().getPrecios()) {
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
    public FacturaCompra anular(FacturaCompra facturaCompra) {
        validar(facturaCompra);
        facturaCompra.setEstado(Constantes.estadoAnulada);
        FacturaCompra res = rep.save(facturaCompra);
        res.normalizar();
        return res;
    }

    @Override
    public FacturaCompra obtener(long id) {
        Optional<FacturaCompra> facturaCompra = rep.findById(id);
        if (facturaCompra.isPresent()) {
            FacturaCompra res = facturaCompra.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.factura_compra);
    }

    @Override
    public List<FacturaCompra> consultar() {
        return rep.consultar();
    }

    @Override
    public List<FacturaCompra> consultarPorEstado(String estado) {
        return rep.consultarPorEstado(estado);
    }

    @Override
    public Page<FacturaCompra> consultarPagina(Pageable pageable) {
        return rep.findAll(pageable);
    }

    @Override
    public List<FacturaCompra> consultarPorEmpresa(long empresaId) {
        return rep.consultarPorEmpresa(empresaId);
    }

    @Override
    public List<FacturaCompra> consultarPorProveedorYEmpresaYEstado(long proveedorId, long empresaId, String estado){
        return rep.consultarPorProveedorYEmpresaYEstado(proveedorId, empresaId, estado);
    }

    @Override
    public List<FacturaCompra> consultarPorProveedorYEmpresaYEstadoDiferente(long proveedorId, long empresaId, String estado){
        return rep.consultarPorProveedorYEmpresaYEstadoDiferente(proveedorId, empresaId, estado);
    }

    @Override
    public List<FacturaCompra> consultarPorEmpresaYEstado(long empresaId, String estado) {
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }

    /*
     * CALCULOS CON FACTURA COMPRA LINEAS
     */
    @Override
    public void validarLinea(FacturaCompraLinea facturaCompraLinea) {
        if (facturaCompraLinea.getCantidad() <= Constantes.cero)
            throw new DatoInvalidoException(Constantes.cantidad);
        if (facturaCompraLinea.getCostoUnitario() <= Constantes.cero)
            throw new DatoInvalidoException(Constantes.costoUnitario);
        if (facturaCompraLinea.getBodega().getId() == Constantes.ceroId)
            throw new DatoInvalidoException(Constantes.bodega);
        if (facturaCompraLinea.getProducto().getId() == Constantes.ceroId)
            throw new DatoInvalidoException(Constantes.producto);
        if (facturaCompraLinea.getImpuesto().getId() == Constantes.ceroId)
            throw new DatoInvalidoException(Constantes.impuesto);
    }

    @Override
    public FacturaCompraLinea calcularLinea(FacturaCompraLinea facturaCompraLinea) {
        this.validarLinea(facturaCompraLinea);
        this.distribuirCostoLinea(facturaCompraLinea);

        double subtotalLineaSinDescuento = facturaCompraLinea.getCantidad() * facturaCompraLinea.getCostoUnitario();
        subtotalLineaSinDescuento = Math.round(subtotalLineaSinDescuento * 10000.0) / 10000.0;
        facturaCompraLinea.setSubtotalLineaSinDescuento(subtotalLineaSinDescuento);

        double valorPorcentajeDescuentoLinea = (subtotalLineaSinDescuento * facturaCompraLinea.getPorcentajeDescuentoLinea() / 100);
        valorPorcentajeDescuentoLinea = Math.round(valorPorcentajeDescuentoLinea * 10000.0) / 10000.0;
        facturaCompraLinea.setValorPorcentajeDescuentoLinea(valorPorcentajeDescuentoLinea);

        double subtotalLinea = subtotalLineaSinDescuento - facturaCompraLinea.getValorDescuentoLinea() - facturaCompraLinea.getValorPorcentajeDescuentoLinea() -
                facturaCompraLinea.getValorDescuentoTotalLinea() - facturaCompraLinea.getValorPorcentajeDescuentoTotalLinea();
        subtotalLinea = Math.round(subtotalLinea * 10000.0) / 10000.0;
        facturaCompraLinea.setSubtotalLinea(subtotalLinea);

        double importeIvaLinea = (subtotalLinea * facturaCompraLinea.getImpuesto().getPorcentaje()) / 100;
        importeIvaLinea = Math.round(importeIvaLinea * 10000.0) / 10000.0;
        facturaCompraLinea.setImporteIvaLinea(importeIvaLinea);

        double totalLinea = subtotalLinea + importeIvaLinea;
        totalLinea = Math.round(totalLinea * 10000.0) / 10000.0;
        facturaCompraLinea.setTotalLinea(totalLinea);

        return facturaCompraLinea;
    }

    private void distribuirCostoLinea(FacturaCompraLinea facturaCompraLinea) {
        double costoPromedioLinea = facturaCompraLinea.getCostoUnitario() + facturaCompraLinea.getCostoDistribuido();
        facturaCompraLinea.setCostoPromedio(costoPromedioLinea);
    }

    /*
     * CALCULAR TOTALES FACTURA COMPRA
     */
    @Override
    public FacturaCompra calcular(FacturaCompra facturaCompra) {
        this.calcularSubtotal(facturaCompra);
        if (facturaCompra.getValorDescuentoTotal() != Constantes.cero) {
            this.calcularValorDescuentoTotal(facturaCompra);
        }
        for(FacturaCompraLinea facturaCompraLinea: facturaCompra.getFacturaCompraLineas()){
            calcularLinea(facturaCompraLinea);
        }
        this.calcularTotales(facturaCompra);
        if (facturaCompra.getPorcentajeDescuentoTotal() != Constantes.cero) {
            this.calcularPorcentajeDescuentoTotal(facturaCompra);
            for(FacturaCompraLinea facturaCompraLinea: facturaCompra.getFacturaCompraLineas()){
                calcularLinea(facturaCompraLinea);
            }
            this.calcularTotales(facturaCompra);
        }
        return facturaCompra;
    }

    private void calcularSubtotal(FacturaCompra facturaCompra) {
        double subtotal = Constantes.cero;
        for (FacturaCompraLinea facturaCompraLinea : facturaCompra.getFacturaCompraLineas()) {
            subtotal += facturaCompraLinea.getSubtotalLineaSinDescuento();
        }
        subtotal = Math.round(subtotal * 100.0) / 100.0;
        facturaCompra.setSubtotal(subtotal);
    }

    private void calcularValorDescuentoTotal(FacturaCompra facturaCompra) {
        for (FacturaCompraLinea facturaCompraLinea : facturaCompra.getFacturaCompraLineas()) {
            double ponderacion = facturaCompraLinea.getSubtotalLineaSinDescuento() / facturaCompra.getSubtotal();

            double valorDescuentoTotalLinea = (facturaCompra.getValorDescuentoTotal() * ponderacion) * 100 / (100 + facturaCompraLinea.getImpuesto().getPorcentaje());
            valorDescuentoTotalLinea = Math.round(valorDescuentoTotalLinea * 100.0) / 100.0;
            facturaCompraLinea.setValorDescuentoTotalLinea(valorDescuentoTotalLinea);
        }
    }

    private void calcularPorcentajeDescuentoTotal(FacturaCompra facturaCompra) {

        double valorTotalPorcentajeDescuentoTotal = (facturaCompra.getTotal() * (facturaCompra.getPorcentajeDescuentoTotal() / 100));
        valorTotalPorcentajeDescuentoTotal = Math.round(valorTotalPorcentajeDescuentoTotal * 100.0) / 100.0;
        facturaCompra.setValorPorcentajeDescuentoTotal(valorTotalPorcentajeDescuentoTotal);

        for (FacturaCompraLinea facturaCompraLinea : facturaCompra.getFacturaCompraLineas()) {
            double ponderacion = facturaCompraLinea.getSubtotalLineaSinDescuento() / facturaCompra.getSubtotal();

            double valorPorcentajeDescuentoTotalLinea = (valorTotalPorcentajeDescuentoTotal * ponderacion) * 100 / (100 + facturaCompraLinea.getImpuesto().getPorcentaje());
            valorPorcentajeDescuentoTotalLinea = Math.round(valorPorcentajeDescuentoTotalLinea * 100.0) / 100.0;
            facturaCompraLinea.setValorPorcentajeDescuentoTotalLinea(valorPorcentajeDescuentoTotalLinea);
        }
    }
    private void calcularTotales(FacturaCompra facturaCompra) {
        double subtotalGravadoConDescuento = Constantes.cero, subtotalNoGravadoConDescuento = Constantes.cero;
        double importeIvaTotal = Constantes.cero, descuentoTotal = Constantes.cero;
        for (FacturaCompraLinea facturaCompraLinea : facturaCompra.getFacturaCompraLineas()) {
            descuentoTotal += facturaCompraLinea.getValorDescuentoLinea() + facturaCompraLinea.getValorPorcentajeDescuentoLinea() +
                    facturaCompraLinea.getValorDescuentoTotalLinea() + facturaCompraLinea.getValorPorcentajeDescuentoTotalLinea();
            if (facturaCompraLinea.getImpuesto().getPorcentaje() != Constantes.cero) {
                subtotalGravadoConDescuento += facturaCompraLinea.getSubtotalLinea();
            } else {
                subtotalNoGravadoConDescuento += facturaCompraLinea.getSubtotalLinea();
            }
            importeIvaTotal += facturaCompraLinea.getImporteIvaLinea();
        }
        descuentoTotal = Math.round(descuentoTotal * 100.0) / 100.0;
        facturaCompra.setDescuento(descuentoTotal);

        subtotalGravadoConDescuento = Math.round(subtotalGravadoConDescuento * 100.0) / 100.0;
        facturaCompra.setSubtotalGravadoConDescuento(subtotalGravadoConDescuento);

        subtotalNoGravadoConDescuento = Math.round(subtotalNoGravadoConDescuento * 100.0) / 100.0;
        facturaCompra.setSubtotalNoGravadoConDescuento(subtotalNoGravadoConDescuento);

        importeIvaTotal = Math.round(importeIvaTotal * 100.0) / 100.0;
        facturaCompra.setImporteIvaTotal(importeIvaTotal);

        double total = subtotalGravadoConDescuento + subtotalNoGravadoConDescuento + importeIvaTotal;
        total = Math.round(total * 100.0) / 100.0;
        facturaCompra.setTotal(total);
    }

    /*
     * FIN CALCULOS TOTALES FACTURA COMPRA
     */

    public FacturaCompra pagar(long facturaCompraId){
        Optional<FacturaCompra> optional = rep.findById(facturaCompraId);
        if(optional.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.factura_compra);
        }
        FacturaCompra facturaCompra = optional.get();
        validar(facturaCompra);
        if(facturaCompra.getEstado().equals(Constantes.estadoAnulada))
            throw new EstadoInvalidoException(Constantes.estadoAnulada);
        if(facturaCompra.getEstado().equals(Constantes.estadoPagada))
            throw new EstadoInvalidoException(Constantes.estadoPagada);
        if(facturaCompra.getEstado().equals(Constantes.estadoPorPagar)){
            facturaCompra.setEstado(Constantes.estadoPagada);
            FacturaCompra facturada = rep.save(facturaCompra);
            facturada.normalizar();
            return facturada;
        }
        throw new ErrorInternoException(Constantes.vacio);
    }
}