package com.proyecto.sicecuador.servicios.impl.compra;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.*;
import com.proyecto.sicecuador.modelos.compra.FacturaCompra;
import com.proyecto.sicecuador.modelos.compra.FacturaCompraLinea;
import com.proyecto.sicecuador.modelos.configuracion.Secuencial;
import com.proyecto.sicecuador.modelos.venta.TipoComprobante;
import com.proyecto.sicecuador.modelos.inventario.Kardex;
import com.proyecto.sicecuador.repositorios.compra.IFacturaCompraRepository;
import com.proyecto.sicecuador.servicios.interf.compra.IFacturaCompraService;
import com.proyecto.sicecuador.servicios.interf.configuracion.ISecuencialService;
import com.proyecto.sicecuador.servicios.interf.venta.ITipoComprobanteService;
import com.proyecto.sicecuador.servicios.interf.inventario.IKardexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    private IKardexService kardexService;
    @Autowired
    private ISecuencialService secuencialService;

    @Override
    public void validar(FacturaCompra facturaCompra) {
        if(facturaCompra.getFecha() == null) throw new DatoInvalidoException(Constantes.fecha);
        if(facturaCompra.getProveedor().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.proveedor);
        if(facturaCompra.getSesion().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.sesion);
        if(facturaCompra.getFacturaCompraLineas().isEmpty()) throw new DatoInvalidoException(Constantes.factura_compra_linea);
        for(FacturaCompraLinea facturaCompraLinea: facturaCompra.getFacturaCompraLineas()){
            validarLinea(facturaCompraLinea);
        }

    }

    private void facturar(FacturaCompra facturaCompra) {
        //if(facturaCompra.getEstado().equals(Constantes.estadoFacturada)) throw new DatoInvalidoException(Constantes.estado);
        //if(facturaCompra.getEstado().equals(Constantes.estadoAnulada)) throw new DatoInvalidoException(Constantes.estado);
        kardexService.eliminar(Constantes.factura_compra, Constantes.operacion_compra, facturaCompra.getNumeroFactura());
        for(FacturaCompraLinea facturaCompraLinea: facturaCompra.getFacturaCompraLineas()){
            Kardex ultimoKardex = kardexService.obtenerUltimoPorFecha(facturaCompraLinea.getBodega().getId(), facturaCompraLinea.getProducto().getId());
            double saldo = Constantes.cero;
            if(ultimoKardex != null){
                saldo = ultimoKardex.getSaldo() + facturaCompraLinea.getCantidad();
            }
            Kardex kardex = new Kardex(null, new Date(), Constantes.factura_compra, Constantes.operacion_compra,
                    facturaCompra.getNumeroFactura(), facturaCompraLinea.getCantidad(), Constantes.cero, saldo,
                    facturaCompraLinea.getSubtotalSinDescuentoLinea(), Constantes.cero,
                    facturaCompraLinea.getCostoUnitario(), facturaCompraLinea.getSubtotalSinDescuentoLinea(),
                    facturaCompraLinea.getBodega(), facturaCompraLinea.getProducto());
            kardexService.crear(kardex);
        }
    }

    @Transactional
    @Override
    public FacturaCompra crear(FacturaCompra facturaCompra) {
        validar(facturaCompra);
        TipoComprobante tipoComprobante = tipoComprobanteService.obtenerPorNombreTabla(Constantes.tabla_factura_compra);
        facturaCompra.setTipoComprobante(tipoComprobante);
        Optional<String>codigo=Util.generarCodigo(Constantes.tabla_factura_compra);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	facturaCompra.setCodigo(codigo.get());
        facturar(facturaCompra);
        facturaCompra.setEstado(Constantes.activo);
        FacturaCompra res = rep.save(facturaCompra);
        res.normalizar();
        return res;
    }

    @Override
    public FacturaCompra actualizar(FacturaCompra facturaCompra) {
        validar(facturaCompra);
        facturar(facturaCompra);
        FacturaCompra res = rep.save(facturaCompra);
        res.normalizar();
        return res;
    }

    @Override
    public FacturaCompra activar(FacturaCompra facturaCompra) {
        validar(facturaCompra);
        facturaCompra.setEstado(Constantes.activo);
        FacturaCompra res = rep.save(facturaCompra);
        res.normalizar();
        return res;
    }

    @Override
    public FacturaCompra inactivar(FacturaCompra facturaCompra) {
        validar(facturaCompra);
        facturaCompra.setEstado(Constantes.inactivo);
        FacturaCompra res = rep.save(facturaCompra);
        res.normalizar();
        return res;
    }

    @Override
    public FacturaCompra obtener(long id) {
        Optional<FacturaCompra> facturaCompra = rep.findById(id);
        if(facturaCompra.isPresent()) {
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
    public List<FacturaCompra> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<FacturaCompra> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
    @Override
    public List<FacturaCompra> consultarPorProveedor(long proveedorId) {
        return rep.consultarPorProveedor(proveedorId, Constantes.estadoFacturada);
    }
    /*
     * CALCULOS CON FACTURA COMPRA LINEAS
     */
    @Override
    public void validarLinea(FacturaCompraLinea facturaCompraLinea) {
        if(facturaCompraLinea.getCantidad() <= Constantes.cero) throw new DatoInvalidoException(Constantes.cantidad);
        if(facturaCompraLinea.getCostoUnitario() <= Constantes.cero) throw new DatoInvalidoException(Constantes.costoUnitario);
        if(facturaCompraLinea.getBodega().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.bodega);
        if(facturaCompraLinea.getProducto().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.producto);
        if(facturaCompraLinea.getImpuesto().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.impuesto);
    }
    @Override
    public FacturaCompraLinea calcularLinea(FacturaCompraLinea facturaCompraLinea) {
        this.validarLinea(facturaCompraLinea);
        this.distribuirCostoLinea(facturaCompraLinea);

        double subtotalSinDescuentoLinea = facturaCompraLinea.getCantidad() * facturaCompraLinea.getCostoPromedio();
        subtotalSinDescuentoLinea = Math.round(subtotalSinDescuentoLinea * 100.0) / 100.0;
        facturaCompraLinea.setSubtotalSinDescuentoLinea(subtotalSinDescuentoLinea);

        double valorPorcentajeDescuentoLinea = (subtotalSinDescuentoLinea * facturaCompraLinea.getPorcentajeDescuentoLinea() / 100);
        valorPorcentajeDescuentoLinea = Math.round(valorPorcentajeDescuentoLinea * 100.0) / 100.0;
        facturaCompraLinea.setValorPorcentajeDescuentoLinea(valorPorcentajeDescuentoLinea);

        double subtotalConDescuentoLinea = subtotalSinDescuentoLinea - facturaCompraLinea.getValorDescuentoLinea() - valorPorcentajeDescuentoLinea;
        subtotalConDescuentoLinea = Math.round(subtotalConDescuentoLinea * 100.0) / 100.0;
        facturaCompraLinea.setSubtotalConDescuentoLinea(subtotalConDescuentoLinea);

        double valorIvaLinea = (subtotalConDescuentoLinea * (facturaCompraLinea.getImpuesto().getPorcentaje() / 100));
        valorIvaLinea = Math.round(valorIvaLinea * 100.0) / 100.0;
        facturaCompraLinea.setImporteIvaLinea(valorIvaLinea);

        double totalLinea = subtotalConDescuentoLinea + valorIvaLinea;
        totalLinea = Math.round(totalLinea * 100.0) / 100.0;
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
        this.calcularSubtotalSinDescuento(facturaCompra);
        this.calcularDescuentoTotalSinPorcentajeDescuentoTotal(facturaCompra);
        this.calcularTotales(facturaCompra);
        this.calcularDescuentoTotalConPorcentajeDescuentoTotal(facturaCompra);
        this.calcularTotales(facturaCompra);
        return facturaCompra;
    }
    private void calcularSubtotalSinDescuento(FacturaCompra facturaCompra) {
    	double subtotalSinDescuento = Constantes.cero;
        for(FacturaCompraLinea facturaCompraLinea: facturaCompra.getFacturaCompraLineas()){
          subtotalSinDescuento+=facturaCompraLinea.getSubtotalSinDescuentoLinea();
        }
        subtotalSinDescuento=Math.round(subtotalSinDescuento*100.0)/100.0;
        facturaCompra.setSubtotalSinDescuento(subtotalSinDescuento);
    }

    private void calcularDescuentoTotalSinPorcentajeDescuentoTotal(FacturaCompra facturaCompra) {
        double valorTotalDescuentoLinea = Constantes.cero;
        double valorTotalDescuentoTotalLinea = Constantes.cero;
        for(FacturaCompraLinea facturaCompraLinea: facturaCompra.getFacturaCompraLineas()) {
            valorTotalDescuentoLinea += facturaCompraLinea.getValorDescuentoLinea() + facturaCompraLinea.getValorPorcentajeDescuentoLinea();

            double ponderacion =  facturaCompraLinea.getSubtotalSinDescuentoLinea() / facturaCompra.getSubtotalSinDescuento();
            double valorDescuentoTotalLinea = (facturaCompra.getValorDescuentoTotal() * ponderacion)*100/(100+facturaCompraLinea.getImpuesto().getPorcentaje());
            valorDescuentoTotalLinea = Math.round(valorDescuentoTotalLinea * 100.0) / 100.0;
            facturaCompraLinea.setValorDescuentoTotalLinea(valorDescuentoTotalLinea);
            valorTotalDescuentoTotalLinea += valorDescuentoTotalLinea;

            double subtotalConDescuentoLinea = facturaCompraLinea.getSubtotalSinDescuentoLinea() - facturaCompraLinea.getValorDescuentoLinea() -
                    facturaCompraLinea.getValorPorcentajeDescuentoLinea() - valorDescuentoTotalLinea;
            subtotalConDescuentoLinea = Math.round(subtotalConDescuentoLinea * 100.0) / 100.0;
            facturaCompraLinea.setSubtotalConDescuentoLinea(subtotalConDescuentoLinea);

            double valorIvaLinea = (subtotalConDescuentoLinea * (facturaCompraLinea.getImpuesto().getPorcentaje() / 100));
            valorIvaLinea = Math.round(valorIvaLinea * 100.0) / 100.0;
            facturaCompraLinea.setImporteIvaLinea(valorIvaLinea);

            double totalLinea = subtotalConDescuentoLinea + valorIvaLinea;
            totalLinea = Math.round(totalLinea * 100.0) / 100.0;
            facturaCompraLinea.setTotalLinea(totalLinea);
        }
        double descuentoTotal = valorTotalDescuentoLinea + valorTotalDescuentoTotalLinea;
        descuentoTotal = Math.round(descuentoTotal * 100.0) / 100.0;
        facturaCompra.setDescuentoTotal(descuentoTotal);
    }

    private void calcularTotales(FacturaCompra facturaCompra) {
    	double subtotalGrabadoConDescuento = Constantes.cero;
        double subtotalNoGrabadoConDescuento = Constantes.cero;
        double importeIvaTotal = Constantes.cero;
    	for(FacturaCompraLinea facturaCompraLinea: facturaCompra.getFacturaCompraLineas()){
          if (facturaCompraLinea.getImpuesto().getPorcentaje() != Constantes.cero){
              subtotalGrabadoConDescuento+=facturaCompraLinea.getSubtotalConDescuentoLinea();
          } else {
              subtotalNoGrabadoConDescuento+=facturaCompraLinea.getSubtotalConDescuentoLinea();
          }
          importeIvaTotal += facturaCompraLinea.getImporteIvaLinea();
    	}
        subtotalGrabadoConDescuento= Math.round(subtotalGrabadoConDescuento * 100.0) / 100.0;
        facturaCompra.setSubtotalGrabadoConDescuento(subtotalGrabadoConDescuento);

        subtotalNoGrabadoConDescuento= Math.round(subtotalNoGrabadoConDescuento * 100.0) / 100.0;
        facturaCompra.setSubtotalNoGrabadoConDescuento(subtotalNoGrabadoConDescuento);

        importeIvaTotal= Math.round(importeIvaTotal * 100.0) / 100.0;
        facturaCompra.setImporteIvaTotal(importeIvaTotal);

        double valorTotal = subtotalGrabadoConDescuento + subtotalNoGrabadoConDescuento + importeIvaTotal;
        valorTotal= Math.round(valorTotal * 100.0) / 100.0;
        facturaCompra.setValorTotal(valorTotal);
    }

    private void calcularDescuentoTotalConPorcentajeDescuentoTotal(FacturaCompra facturaCompra) {
        double valorTotalDescuentoLinea = Constantes.cero;
        double valorTotalDescuentoTotalLinea = Constantes.cero;
        double valorTotalPorcentajeDescuentoTotalLinea = Constantes.cero;
        double valorTotalPorcentajeDescuentoTotal = (facturaCompra.getValorTotal() * (facturaCompra.getPorcentajeDescuentoTotal() / 100));
        valorTotalPorcentajeDescuentoTotal = Math.round(valorTotalPorcentajeDescuentoTotal * 100.0) / 100.0;
        facturaCompra.setValorPorcentajeDescuentoTotal(valorTotalPorcentajeDescuentoTotal);

        for(FacturaCompraLinea facturaCompraLinea: facturaCompra.getFacturaCompraLineas()) {
            valorTotalDescuentoLinea += facturaCompraLinea.getValorDescuentoLinea() + facturaCompraLinea.getValorPorcentajeDescuentoLinea();
            valorTotalDescuentoTotalLinea += facturaCompraLinea.getValorDescuentoTotalLinea();

            double ponderacion =  facturaCompraLinea.getSubtotalSinDescuentoLinea() / facturaCompra.getSubtotalSinDescuento();
            double valorPorcentajeDescuentoTotalLinea = (valorTotalPorcentajeDescuentoTotal * ponderacion) * 100 / (100+facturaCompraLinea.getImpuesto().getPorcentaje());
            valorPorcentajeDescuentoTotalLinea = Math.round(valorPorcentajeDescuentoTotalLinea * 100.0) / 100.0;
            facturaCompraLinea.setValorPorcentajeDescuentoTotalLinea(valorPorcentajeDescuentoTotalLinea);
            valorTotalPorcentajeDescuentoTotalLinea += valorPorcentajeDescuentoTotalLinea;

            double subtotalConDescuentoLinea = facturaCompraLinea.getSubtotalSinDescuentoLinea() - facturaCompraLinea.getValorDescuentoLinea() - facturaCompraLinea.getValorPorcentajeDescuentoLinea() -
                    facturaCompraLinea.getValorDescuentoTotalLinea() - valorPorcentajeDescuentoTotalLinea;
            subtotalConDescuentoLinea = Math.round(subtotalConDescuentoLinea * 100.0) / 100.0;
            facturaCompraLinea.setSubtotalConDescuentoLinea(subtotalConDescuentoLinea);

            double valorIvaLinea = (subtotalConDescuentoLinea * facturaCompraLinea.getImpuesto().getPorcentaje() / 100);
            valorIvaLinea = Math.round(valorIvaLinea * 100.0) / 100.0;
            facturaCompraLinea.setImporteIvaLinea(valorIvaLinea);

            double totalLinea = subtotalConDescuentoLinea + valorIvaLinea;
            totalLinea = Math.round(totalLinea * 100.0) / 100.0;
            facturaCompraLinea.setTotalLinea(totalLinea);
        }
        double descuentoTotal = valorTotalDescuentoLinea + valorTotalDescuentoTotalLinea + valorTotalPorcentajeDescuentoTotalLinea;
        descuentoTotal = Math.round(descuentoTotal * 100.0) / 100.0;
        facturaCompra.setDescuentoTotal(descuentoTotal);
    }
    /*
     * FIN CALCULOS TOTALES FACTURA COMPRA
     */

}
