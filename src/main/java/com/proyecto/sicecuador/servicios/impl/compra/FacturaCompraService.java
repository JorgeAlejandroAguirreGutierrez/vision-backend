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
        if(facturaCompra.getEstado().equals(Constantes.estadoFacturada)) throw new DatoInvalidoException(Constantes.estado);
        if(facturaCompra.getEstado().equals(Constantes.estadoAnulada)) throw new DatoInvalidoException(Constantes.estado);
        kardexService.eliminar(Constantes.factura_compra, Constantes.operacion_compra, facturaCompra.getSecuencial());
        for(FacturaCompraLinea facturaCompraLinea: facturaCompra.getFacturaCompraLineas()){
            Kardex ultimoKardex = kardexService.obtenerUltimoPorFecha(facturaCompraLinea.getBodega().getId(), facturaCompraLinea.getProducto().getId());
            double saldo = Constantes.cero;
            if(ultimoKardex != null){
                saldo = ultimoKardex.getSaldo() + facturaCompraLinea.getCantidad();
            }
            Kardex kardex = new Kardex(null, new Date(), Constantes.factura_compra, Constantes.operacion_compra,
                    facturaCompra.getSecuencial(), facturaCompraLinea.getCantidad(), Constantes.cero, saldo,
                    facturaCompraLinea.getTotalSinDescuentoLinea(), Constantes.cero,
                    facturaCompraLinea.getCostoUnitario(), facturaCompraLinea.getTotalSinDescuentoLinea(),
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
        Secuencial secuencial = secuencialService.obtenerPorTipoComprobanteYEstacion(facturaCompra.getTipoComprobante().getId(), facturaCompra.getSesion().getUsuario().getEstacion().getId());
        facturaCompra.setSecuencial(Util.generarSecuencial(secuencial.getNumeroSiguiente()));
        facturar(facturaCompra);
        facturaCompra.setEstado(Constantes.estadoFacturada);
        FacturaCompra res = rep.save(facturaCompra);
        res.normalizar();
        secuencial.setNumeroSiguiente(secuencial.getNumeroSiguiente()+1);
        secuencialService.actualizar(secuencial);
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
    public FacturaCompra calcular(FacturaCompra facturaCompra) {
    		this.calcularTotalSinDescuentoLinea(facturaCompra);
            this.calcularDescuentoTotal(facturaCompra);
    		this.calcularSubtotalSinDescuento(facturaCompra);
            this.calcularSubtotalBase12SinDescuento(facturaCompra);
            this.calcularSubtotalBase0SinDescuento(facturaCompra);
            this.calcularIvaSinDescuento(facturaCompra);
            this.calcularDescuentoTotal(facturaCompra);
            this.calcularTotalSinDescuento(facturaCompra);
            return facturaCompra;
    }
    /*
     * CALCULOS CON FACTURA COMPRA LINEAS
     */
    private void calcularTotalSinDescuentoLinea(FacturaCompra facturaCompra) {
    	for(FacturaCompraLinea facturaCompraLinea: facturaCompra.getFacturaCompraLineas()) {
    		double totalSinDescuentoLinea=facturaCompraLinea.getCantidad()*facturaCompraLinea.getCostoUnitario();
        	totalSinDescuentoLinea=Math.round(totalSinDescuentoLinea*100.0)/100.0;
            facturaCompraLinea.setTotalSinDescuentoLinea(totalSinDescuentoLinea);
    	}
    }
    /*
     * FIN CALCULO FACTURA LINEAS
     */
    
    /*
     * CALCULAR DESCUENTOS
     */
    private void calcularDescuentoTotal(FacturaCompra facturaCompra) {
        double totalValorDescuentoLinea = Constantes.cero;
        for(FacturaCompraLinea facturaCompraLinea: facturaCompra.getFacturaCompraLineas()) {
            double valorDescuentoPorcentajeLinea = (facturaCompraLinea.getTotalSinDescuentoLinea() * facturaCompraLinea.getPorcentajeDescuentoLinea()) / 100;
            totalValorDescuentoLinea = facturaCompraLinea.getValorDescuentoLinea() + valorDescuentoPorcentajeLinea;
        }
        double valorDescuentoTotalPorcentaje = (facturaCompra.getPorcentajeDescuentoTotal() * facturaCompra.getTotalSinDescuento()) / 100;
        double descuentoTotal = totalValorDescuentoLinea + facturaCompra.getValorDescuentoTotal() + valorDescuentoTotalPorcentaje;
        descuentoTotal = Math.round(descuentoTotal*100.0)/100.0;
        facturaCompra.setDescuentoTotal(descuentoTotal);
    }
    /*
     * FIN CALCULAR DESCUENTOS
     */
    
    /*
     * CALCULOS CON FACTURA
     */
    private void calcularSubtotalSinDescuento(FacturaCompra facturaCompra) {
    	double subtotalSinDescuento = Constantes.cero;
        for(FacturaCompraLinea facturaCompraLinea: facturaCompra.getFacturaCompraLineas()){
          subtotalSinDescuento+=facturaCompraLinea.getTotalSinDescuentoLinea();
        }
        subtotalSinDescuento=Math.round(subtotalSinDescuento*100.0)/100.0;
        facturaCompra.setSubtotalSinDescuento(subtotalSinDescuento);
    }
    
    private void calcularSubtotalBase12SinDescuento(FacturaCompra facturaCompra) {
    	double subtotalBase12SinDescuento = Constantes.cero;
    	for(FacturaCompraLinea facturaCompraLinea: facturaCompra.getFacturaCompraLineas()){
          if (facturaCompraLinea.getProducto().getImpuesto().getPorcentaje() == Constantes.iva12){
            subtotalBase12SinDescuento+=facturaCompraLinea.getTotalSinDescuentoLinea();
          }
    	}
        subtotalBase12SinDescuento= Math.round(subtotalBase12SinDescuento*100.0)/100.0;
        facturaCompra.setSubtotalBase12SinDescuento(subtotalBase12SinDescuento);
    }
    
    private void calcularSubtotalBase0SinDescuento(FacturaCompra facturaCompra) {
    	double subtotalBase0SinDescuento = Constantes.cero;
    	for(FacturaCompraLinea facturaCompraLinea: facturaCompra.getFacturaCompraLineas()){
          if (facturaCompraLinea.getProducto().getImpuesto().getPorcentaje() == Constantes.iva0){
            subtotalBase0SinDescuento += facturaCompraLinea.getTotalSinDescuentoLinea();
          }
        }
        subtotalBase0SinDescuento = Math.round(subtotalBase0SinDescuento*100.0)/100.0;
        facturaCompra.setSubtotalBase0SinDescuento(subtotalBase0SinDescuento);
    }

    private void calcularIvaSinDescuento(FacturaCompra facturaCompra){
        double ivaSinDescuento=(facturaCompra.getSubtotalBase12SinDescuento() * Constantes.iva12) / 100;
        ivaSinDescuento=Math.round(ivaSinDescuento*100.0)/100.0;
        facturaCompra.setIvaSinDescuento(ivaSinDescuento);
    }

    private void calcularTotalSinDescuento(FacturaCompra facturaCompra){
        double totalSinDescuento = facturaCompra.getSubtotalBase0SinDescuento() + facturaCompra.getSubtotalBase12SinDescuento() + facturaCompra.getIvaSinDescuento();
        totalSinDescuento=Math.round(totalSinDescuento*100.0)/100.0;
        facturaCompra.setTotalSinDescuento(totalSinDescuento);
    }

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
        validarLinea(facturaCompraLinea);
        double totalSinDescuentoLinea = facturaCompraLinea.getCantidad() * facturaCompraLinea.getCostoUnitario();
        facturaCompraLinea.setTotalSinDescuentoLinea(totalSinDescuentoLinea);
        return facturaCompraLinea;
    }
    @Override
    public List<FacturaCompra> consultarPorProveedor(long proveedorId) {
        return rep.consultarPorProveedor(proveedorId, Constantes.estadoFacturada);
    }
}
