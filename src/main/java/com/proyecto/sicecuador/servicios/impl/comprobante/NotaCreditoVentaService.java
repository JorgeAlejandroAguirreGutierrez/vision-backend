package com.proyecto.sicecuador.servicios.impl.comprobante;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.exception.SecuenciaNoExistenteException;
import com.proyecto.sicecuador.modelos.comprobante.*;
import com.proyecto.sicecuador.modelos.inventario.Kardex;
import com.proyecto.sicecuador.repositorios.comprobante.INotaCreditoVentaRepository;
import com.proyecto.sicecuador.servicios.interf.comprobante.IFacturaService;
import com.proyecto.sicecuador.servicios.interf.comprobante.INotaCreditoVentaService;
import com.proyecto.sicecuador.servicios.interf.comprobante.ITipoComprobanteService;
import com.proyecto.sicecuador.servicios.interf.inventario.IKardexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Override
    public void validar(NotaCreditoVenta notaCreditoVenta) {
        if(notaCreditoVenta.getFecha() == null) throw new DatoInvalidoException(Constantes.fecha);
        if(notaCreditoVenta.getSesion().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.sesion);
        if(notaCreditoVenta.getNotaCreditoVentaLineas().isEmpty()) throw new DatoInvalidoException(Constantes.nota_credito_venta_linea);
    }

    private void facturar(NotaCreditoVenta notaCreditoVenta) {
        if(notaCreditoVenta.getEstado().equals(Constantes.estadoFacturada)) throw new DatoInvalidoException(Constantes.estado);
        if(notaCreditoVenta.getEstado().equals(Constantes.estadoAnulada)) throw new DatoInvalidoException(Constantes.estado);

        if(notaCreditoVenta.getOperacion().equals(Constantes.operacion_conjunta)){
            for(NotaCreditoVentaLinea notaCreditoVentaLinea : notaCreditoVenta.getNotaCreditoVentaLineas()) {
                Kardex ultimoKardex = kardexService.obtenerUltimoPorFecha(notaCreditoVentaLinea.getBodega().getId(), notaCreditoVentaLinea.getProducto().getId());
                if (ultimoKardex != null) {
                    double saldo = ultimoKardex.getSaldo() + notaCreditoVentaLinea.getDevolucion();
                    Kardex kardex = new Kardex(null, new Date(), Constantes.nota_credito_venta, notaCreditoVenta.getOperacion(),
                            notaCreditoVenta.getSecuencia(), notaCreditoVentaLinea.getDevolucion(), Constantes.cero, saldo,
                            notaCreditoVentaLinea.getTotalSinDescuentoLinea(), Constantes.cero,
                            notaCreditoVentaLinea.getDevolucion(), notaCreditoVentaLinea.getCostoUnitario(), notaCreditoVentaLinea.getTotalSinDescuentoLinea(),
                            ultimoKardex.getBodega(), ultimoKardex.getProducto());
                    kardexService.crear(kardex);
                }
            }
        }
        if(notaCreditoVenta.getOperacion().equals(Constantes.operacion_devolucion)){
            for(NotaCreditoVentaLinea notaCreditoVentaLinea : notaCreditoVenta.getNotaCreditoVentaLineas()) {
                Kardex ultimoKardex = kardexService.obtenerUltimoPorFecha(notaCreditoVentaLinea.getBodega().getId(), notaCreditoVentaLinea.getProducto().getId());
                if (ultimoKardex != null) {
                    double saldo = ultimoKardex.getSaldo() + notaCreditoVentaLinea.getDevolucion();
                    Kardex kardex = new Kardex(null, new Date(), Constantes.nota_credito_venta, notaCreditoVenta.getOperacion(),
                            notaCreditoVenta.getSecuencia(), notaCreditoVentaLinea.getDevolucion(), Constantes.cero, saldo,
                            notaCreditoVentaLinea.getTotalSinDescuentoLinea(), Constantes.cero,
                            notaCreditoVentaLinea.getDevolucion(), notaCreditoVentaLinea.getCostoUnitario(), notaCreditoVentaLinea.getTotalSinDescuentoLinea(),
                            ultimoKardex.getBodega(), ultimoKardex.getProducto());
                    kardexService.crear(kardex);
                }
            }
        }
        if(notaCreditoVenta.getOperacion().equals(Constantes.operacion_descuento)){
            for(NotaCreditoVentaLinea notaCreditoCompraLinea : notaCreditoVenta.getNotaCreditoVentaLineas()) {
                Kardex ultimoKardex = kardexService.obtenerUltimoPorFecha(notaCreditoCompraLinea.getBodega().getId(), notaCreditoCompraLinea.getProducto().getId());
                if(ultimoKardex != null){
                    Kardex kardex = new Kardex(null, new Date(), Constantes.nota_credito_compra, notaCreditoVenta.getOperacion(),
                            notaCreditoVenta.getSecuencia(), Constantes.cero, ultimoKardex.getCantidad(), ultimoKardex.getSaldo(),
                            Constantes.cero, notaCreditoCompraLinea.getTotalSinDescuentoLinea(),
                            ultimoKardex.getCantidad(), notaCreditoCompraLinea.getCostoUnitario(), notaCreditoCompraLinea.getTotalSinDescuentoLinea(),
                            ultimoKardex.getBodega(), ultimoKardex.getProducto());
                    kardexService.crear(kardex);
                }
            }
        }
    }

    @Transactional
    @Override
    public NotaCreditoVenta crear(NotaCreditoVenta notaCreditoVenta) {
        validar(notaCreditoVenta);
        TipoComprobante tipoComprobante = tipoComprobanteService.obtenerPorNombreTabla(Constantes.tabla_nota_credito_venta);
        notaCreditoVenta.setTipoComprobante(tipoComprobante);
        Optional<String>codigo=Util.generarCodigo(Constantes.tabla_nota_credito_venta);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
        notaCreditoVenta.setCodigo(codigo.get());
    	Optional<String>secuencia=Util.generarSecuencia(Constantes.tabla_factura_compra);
    	if (secuencia.isEmpty()) {
    		throw new SecuenciaNoExistenteException();
    	}
        notaCreditoVenta.setSecuencia(secuencia.get());
        facturar(notaCreditoVenta);
        notaCreditoVenta.setEstado(Constantes.estadoEmitida);
        NotaCreditoVenta res = rep.save(notaCreditoVenta);
        res.normalizar();
        return res;
    }

    @Override
    public NotaCreditoVenta actualizar(NotaCreditoVenta notaCreditoVenta) {
        validar(notaCreditoVenta);
        facturar(notaCreditoVenta);
        NotaCreditoVenta res = rep.save(notaCreditoVenta);
        res.normalizar();
        return res;
    }

    @Override
    public NotaCreditoVenta activar(NotaCreditoVenta notaCreditoVenta) {
        validar(notaCreditoVenta);
        notaCreditoVenta.setEstado(Constantes.activo);
        NotaCreditoVenta res = rep.save(notaCreditoVenta);
        res.normalizar();
        return res;
    }

    @Override
    public NotaCreditoVenta inactivar(NotaCreditoVenta notaCreditoVenta) {
        validar(notaCreditoVenta);
        notaCreditoVenta.setEstado(Constantes.inactivo);
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
    public List<NotaCreditoVenta> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<NotaCreditoVenta> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<NotaCreditoVenta> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public NotaCreditoVenta calcular(NotaCreditoVenta notaCreditoVenta) {
        this.calcularTotalSinDescuentoLinea(notaCreditoVenta);
        this.calcularDescuentoTotal(notaCreditoVenta);
        this.calcularSubtotalSinDescuento(notaCreditoVenta);
        this.calcularSubtotalBase12SinDescuento(notaCreditoVenta);
        this.calcularSubtotalBase0SinDescuento(notaCreditoVenta);
        this.calcularIvaSinDescuento(notaCreditoVenta);
        this.calcularDescuentoTotal(notaCreditoVenta);
        this.calcularTotalSinDescuento(notaCreditoVenta);
        return notaCreditoVenta;
    }
    /*
     * CALCULOS CON NOTA CREDITO VENTA LINEA
     */
    private void calcularTotalSinDescuentoLinea(NotaCreditoVenta notaCreditoVenta) {
    	for(NotaCreditoVentaLinea notaCreditoVentaLinea: notaCreditoVenta.getNotaCreditoVentaLineas()) {
            validarLinea(notaCreditoVentaLinea);
    		double totalSinDescuentoLinea = (notaCreditoVentaLinea.getDevolucion()) * notaCreditoVentaLinea.getCostoUnitario();
        	totalSinDescuentoLinea=Math.round(totalSinDescuentoLinea*100.0)/100.0;
            notaCreditoVentaLinea.setTotalSinDescuentoLinea(totalSinDescuentoLinea);
    	}
    }
    /*
     * FIN CALCULO NOTA CREDITO VENTA LINEA
     */
    
    /*
     * CALCULAR DESCUENTOS
     */
    private void calcularDescuentoTotal(NotaCreditoVenta notaCreditoVenta) {
        double totalValorDescuentoLinea = Constantes.cero;
        for(NotaCreditoVentaLinea notaCreditoVentaLinea: notaCreditoVenta.getNotaCreditoVentaLineas()) {
            double valorDescuentoPorcentajeLinea = (notaCreditoVentaLinea.getTotalSinDescuentoLinea() * notaCreditoVentaLinea.getPorcentajeDescuentoLinea()) / 100;
            totalValorDescuentoLinea = notaCreditoVentaLinea.getValorDescuentoLinea() + valorDescuentoPorcentajeLinea;
        }
        double valorDescuentoTotalPorcentaje = (notaCreditoVenta.getPorcentajeDescuentoTotal() * notaCreditoVenta.getTotalSinDescuento()) / 100;
        double descuentoTotal = totalValorDescuentoLinea + notaCreditoVenta.getValorDescuentoTotal() + valorDescuentoTotalPorcentaje;
        descuentoTotal = Math.round(descuentoTotal*100.0)/100.0;
        notaCreditoVenta.setDescuentoTotal(descuentoTotal);
    }
    /*
     * FIN CALCULAR DESCUENTOS
     */
    
    /*
     * CALCULOS CON FACTURA
     */
    private void calcularSubtotalSinDescuento(NotaCreditoVenta notaCreditoVenta) {
    	double subtotalSinDescuento = Constantes.cero;
        for(NotaCreditoVentaLinea notaCreditoVentaLinea: notaCreditoVenta.getNotaCreditoVentaLineas()){
          subtotalSinDescuento += notaCreditoVentaLinea.getTotalSinDescuentoLinea();
        }
        subtotalSinDescuento=Math.round(subtotalSinDescuento*100.0)/100.0;
        notaCreditoVenta.setSubtotalSinDescuento(subtotalSinDescuento);
    }
    
    private void calcularSubtotalBase12SinDescuento(NotaCreditoVenta notaCreditoVenta) {
    	double subtotalBase12SinDescuento = Constantes.cero;
    	for(NotaCreditoVentaLinea notaCreditoVentaLinea: notaCreditoVenta.getNotaCreditoVentaLineas()){
          if (notaCreditoVentaLinea.getProducto().getImpuesto().getPorcentaje() == Constantes.iva12){
            subtotalBase12SinDescuento += notaCreditoVentaLinea.getTotalSinDescuentoLinea();
          }
    	}
        subtotalBase12SinDescuento= Math.round(subtotalBase12SinDescuento*100.0)/100.0;
        notaCreditoVenta.setSubtotalBase12SinDescuento(subtotalBase12SinDescuento);
    }
    
    private void calcularSubtotalBase0SinDescuento(NotaCreditoVenta notaCreditoVenta) {
    	double subtotalBase0SinDescuento = Constantes.cero;
    	for(NotaCreditoVentaLinea notaCreditoVentaLinea: notaCreditoVenta.getNotaCreditoVentaLineas()){
          if (notaCreditoVentaLinea.getProducto().getImpuesto().getPorcentaje() == Constantes.iva0){
            subtotalBase0SinDescuento += notaCreditoVentaLinea.getTotalSinDescuentoLinea();
          }
        }
        subtotalBase0SinDescuento = Math.round(subtotalBase0SinDescuento*100.0)/100.0;
        notaCreditoVenta.setSubtotalBase0SinDescuento(subtotalBase0SinDescuento);
    }

    private void calcularIvaSinDescuento(NotaCreditoVenta notaCreditoVenta){
        double ivaSinDescuento=(notaCreditoVenta.getSubtotalBase12SinDescuento() * Constantes.iva12) / 100;
        ivaSinDescuento=Math.round(ivaSinDescuento*100.0)/100.0;
        notaCreditoVenta.setIvaSinDescuento(ivaSinDescuento);
    }

    private void calcularTotalSinDescuento(NotaCreditoVenta notaCreditoVenta){
        double totalSinDescuento = notaCreditoVenta.getSubtotalBase0SinDescuento() + notaCreditoVenta.getSubtotalBase12SinDescuento() + notaCreditoVenta.getIvaSinDescuento();
        totalSinDescuento=Math.round(totalSinDescuento*100.0)/100.0;
        notaCreditoVenta.setTotalSinDescuento(totalSinDescuento);
    }

    @Override
    public void validarLinea(NotaCreditoVentaLinea notaCreditoVentaLinea) {
        if(notaCreditoVentaLinea.getCantidad() < Constantes.cero) throw new DatoInvalidoException(Constantes.cantidad);
        if(notaCreditoVentaLinea.getDevolucion() < Constantes.cero) throw new DatoInvalidoException(Constantes.devolucion);
        if(notaCreditoVentaLinea.getCostoUnitario() < Constantes.cero) throw new DatoInvalidoException(Constantes.costoUnitario);
        if(notaCreditoVentaLinea.getValorDescuentoLinea() < Constantes.cero) throw new DatoInvalidoException(Constantes.valorDescuentoLinea);
        if(notaCreditoVentaLinea.getPorcentajeDescuentoLinea() < Constantes.cero) throw new DatoInvalidoException(Constantes.porcentajeDescuentoLinea);
        if(notaCreditoVentaLinea.getBodega().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.bodega);
        if(notaCreditoVentaLinea.getProducto().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.producto);
        if(notaCreditoVentaLinea.getDevolucion() > notaCreditoVentaLinea.getCantidad()) throw new DatoInvalidoException(Constantes.devolucion);
        if(notaCreditoVentaLinea.getValorDescuentoLinea() > notaCreditoVentaLinea.getCostoUnitario()) throw new DatoInvalidoException(Constantes.valorDescuentoLinea);
        if(notaCreditoVentaLinea.getPorcentajeDescuentoLinea() > 100) throw new DatoInvalidoException(Constantes.porcentajeDescuentoLinea);
    }

    @Override
    public NotaCreditoVenta obtenerPorFactura(long facturaId){
        NotaCreditoVenta notaCreditoVenta = new NotaCreditoVenta();
        Factura factura = facturaService.obtener(facturaId);
        notaCreditoVenta.setFactura(factura);
        notaCreditoVenta.setNotaCreditoVentaLineas(new ArrayList<>());
        for(FacturaLinea facturaLinea: factura.getFacturaLineas()){
            NotaCreditoVentaLinea notaCreditoVentaLinea = new NotaCreditoVentaLinea();
            notaCreditoVentaLinea.setPrecio(facturaLinea.getPrecio());
            notaCreditoVentaLinea.setImpuesto(facturaLinea.getImpuesto());
            notaCreditoVentaLinea.setBodega(facturaLinea.getBodega());
            notaCreditoVentaLinea.setProducto(facturaLinea.getProducto());
            notaCreditoVentaLinea.setCostoUnitario(facturaLinea.getPrecio().getPrecioVentaPublicoManual());
            notaCreditoVentaLinea.setCantidad(facturaLinea.getCantidad());
            notaCreditoVenta.getNotaCreditoVentaLineas().add(notaCreditoVentaLinea);
        }
        return notaCreditoVenta;
    }
}
