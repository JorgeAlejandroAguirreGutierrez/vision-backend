package com.proyecto.sicecuador.servicios.impl.compra;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.modelos.configuracion.TipoComprobante;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.compra.*;
import com.proyecto.sicecuador.modelos.configuracion.Secuencial;
import com.proyecto.sicecuador.modelos.inventario.Kardex;
import com.proyecto.sicecuador.modelos.inventario.TipoOperacion;
import com.proyecto.sicecuador.repositorios.compra.INotaDebitoCompraRepository;
import com.proyecto.sicecuador.servicios.interf.compra.IFacturaCompraService;
import com.proyecto.sicecuador.servicios.interf.compra.INotaDebitoCompraService;
import com.proyecto.sicecuador.servicios.interf.configuracion.ISecuencialService;
import com.proyecto.sicecuador.servicios.interf.configuracion.ITipoComprobanteService;
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
public class NotaDebitoCompraService implements INotaDebitoCompraService {
    @Autowired
    private INotaDebitoCompraRepository rep;
    @Autowired
    private ITipoComprobanteService tipoComprobanteService;
    @Autowired
    private IKardexService kardexService;
    @Autowired
    private IFacturaCompraService facturaCompraService;
    @Autowired
    private ISecuencialService secuencialService;

    @Override
    public void validar(NotaDebitoCompra notaDebitoCompra) {
        if(notaDebitoCompra.getFecha() == null) throw new DatoInvalidoException(Constantes.fecha);
        if(notaDebitoCompra.getSesion().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.sesion);
        if(notaDebitoCompra.getNotaDebitoCompraLineas().isEmpty()) throw new DatoInvalidoException(Constantes.nota_debito_compra_linea);
    }

    private void facturar(NotaDebitoCompra notaDebitoCompra) {
        if(notaDebitoCompra.getEstado().equals(Constantes.estadoFacturada)) throw new DatoInvalidoException(Constantes.estado);
        if(notaDebitoCompra.getEstado().equals(Constantes.estadoAnulada)) throw new DatoInvalidoException(Constantes.estado);
        kardexService.eliminar(10, 7, notaDebitoCompra.getSecuencial());
        for(NotaDebitoCompraLinea notaDebitoCompraLinea : notaDebitoCompra.getNotaDebitoCompraLineas()) {
            Kardex ultimoKardex = kardexService.obtenerUltimoPorBodega(notaDebitoCompraLinea.getBodega().getId(), notaDebitoCompraLinea.getProducto().getId());
            if (ultimoKardex != null) {
                double saldo = ultimoKardex.getSaldo() - notaDebitoCompraLinea.getCantidad();
                Kardex kardex = new Kardex(null, new Date(),
                        notaDebitoCompra.getSecuencial(), notaDebitoCompraLinea.getCantidad(), Constantes.cero, saldo,
                        notaDebitoCompraLinea.getTotalSinDescuentoLinea(), Constantes.cero,
                        notaDebitoCompraLinea.getCostoUnitario(), notaDebitoCompraLinea.getTotalSinDescuentoLinea(),
                        new TipoComprobante(10), new TipoOperacion(6), ultimoKardex.getBodega(), ultimoKardex.getProducto());
                kardexService.crear(kardex);
            }
        }
    }

    @Transactional
    @Override
    public NotaDebitoCompra crear(NotaDebitoCompra notaDebitoCompra) {
        validar(notaDebitoCompra);
        TipoComprobante tipoComprobante = tipoComprobanteService.obtenerPorNombreTabla(Constantes.tabla_nota_debito_compra);
        notaDebitoCompra.setTipoComprobante(tipoComprobante);
        Optional<String>codigo=Util.generarCodigo(Constantes.tabla_nota_debito_compra);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
        notaDebitoCompra.setCodigo(codigo.get());
        notaDebitoCompra.setSerie(notaDebitoCompra.getSesion().getUsuario().getEstacion().getEstablecimiento().getCodigoSRI() + notaDebitoCompra.getSesion().getUsuario().getEstacion().getCodigoSRI());
        Secuencial secuencial = secuencialService.obtenerPorTipoComprobanteYEstacion(notaDebitoCompra.getTipoComprobante().getId(), notaDebitoCompra.getSesion().getUsuario().getEstacion().getId());
        notaDebitoCompra.setSecuencial(Util.generarSecuencial(secuencial.getNumeroSiguiente()));
        notaDebitoCompra.setEstado(Constantes.estadoEmitida);
        calcular(notaDebitoCompra);
        facturar(notaDebitoCompra);
        notaDebitoCompra.setEstado(Constantes.estadoFacturada);
        NotaDebitoCompra res = rep.save(notaDebitoCompra);
        res.normalizar();
        secuencial.setNumeroSiguiente(secuencial.getNumeroSiguiente()+1);
        secuencialService.actualizar(secuencial);
        return res;
    }

    @Override
    public NotaDebitoCompra actualizar(NotaDebitoCompra notaDebitoCompra) {
        validar(notaDebitoCompra);
        calcular(notaDebitoCompra);
        facturar(notaDebitoCompra);
        notaDebitoCompra.setEstado(Constantes.estadoFacturada);
        NotaDebitoCompra res = rep.save(notaDebitoCompra);
        res.normalizar();
        return res;
    }

    @Override
    public NotaDebitoCompra activar(NotaDebitoCompra notaDebitoCompra) {
        validar(notaDebitoCompra);
        notaDebitoCompra.setEstado(Constantes.activo);
        NotaDebitoCompra res = rep.save(notaDebitoCompra);
        res.normalizar();
        return res;
    }

    @Override
    public NotaDebitoCompra inactivar(NotaDebitoCompra notaDebitoCompra) {
        validar(notaDebitoCompra);
        notaDebitoCompra.setEstado(Constantes.inactivo);
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
    public List<NotaDebitoCompra> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<NotaDebitoCompra> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public NotaDebitoCompra calcular(NotaDebitoCompra notaDebitoCompra) {
        this.calcularTotalSinDescuentoLinea(notaDebitoCompra);
        this.calcularIvaSinDescuentoLinea(notaDebitoCompra);
        this.calcularSubtotalSinDescuento(notaDebitoCompra);
        this.calcularSubtotalBase12SinDescuento(notaDebitoCompra);
        this.calcularSubtotalBase0SinDescuento(notaDebitoCompra);
        this.calcularIvaSinDescuento(notaDebitoCompra);
        this.calcularDescuentoTotal(notaDebitoCompra);
        this.calcularTotalSinDescuento(notaDebitoCompra);
        this.calcularTotalConDescuento(notaDebitoCompra);
        return notaDebitoCompra;
    }
    /*
     * CALCULOS CON NOTA DEBITO COMPRA LINEA
     */
    @Override
    public NotaDebitoCompraLinea calcularLinea(NotaDebitoCompraLinea notaDebitoCompraLinea) {
        validarLinea(notaDebitoCompraLinea);
        double impuesto = notaDebitoCompraLinea.getCantidad() * notaDebitoCompraLinea.getCostoUnitario() * notaDebitoCompraLinea.getImpuesto().getPorcentaje() / 100;
        double totalSinDescuentoLinea = notaDebitoCompraLinea.getCantidad() * notaDebitoCompraLinea.getCostoUnitario() + impuesto;
        totalSinDescuentoLinea = Math.round(totalSinDescuentoLinea*100.0)/100.0;
        notaDebitoCompraLinea.setTotalSinDescuentoLinea(totalSinDescuentoLinea);
        return notaDebitoCompraLinea;
    }
    /*
     * CALCULOS CON NOTA DEBITO COMPRA LINEA
     */
    private void calcularTotalSinDescuentoLinea(NotaDebitoCompra notaDebitoCompra) {
    	for(NotaDebitoCompraLinea notaDebitoCompraLinea: notaDebitoCompra.getNotaDebitoCompraLineas()) {
            validarLinea(notaDebitoCompraLinea);
    		double totalSinDescuentoLinea = (notaDebitoCompraLinea.getCantidad()) * notaDebitoCompraLinea.getCostoUnitario();
        	totalSinDescuentoLinea=Math.round(totalSinDescuentoLinea*100.0)/100.0;
            notaDebitoCompraLinea.setTotalSinDescuentoLinea(totalSinDescuentoLinea);
    	}
    }
    private void calcularIvaSinDescuentoLinea(NotaDebitoCompra notaDebitoCompra) {
        for(NotaDebitoCompraLinea notaDebitoCompraLinea: notaDebitoCompra.getNotaDebitoCompraLineas()) {
            validarLinea(notaDebitoCompraLinea);
            double ivaSinDescuentoLinea = notaDebitoCompraLinea.getTotalSinDescuentoLinea() * notaDebitoCompraLinea.getImpuesto().getPorcentaje() / 100;
            ivaSinDescuentoLinea = Math.round(ivaSinDescuentoLinea*100.0)/100.0;
            notaDebitoCompraLinea.setIvaSinDescuentoLinea(ivaSinDescuentoLinea);
        }
    }
    /*
     * FIN CALCULO NOTA DEBITO COMPRA LINEAS
     */
    
    /*
     * CALCULAR DESCUENTOS
     */
    private void calcularDescuentoTotal(NotaDebitoCompra notaDebitoCompra) {
        double totalDescuento = Constantes.cero;
        for(NotaDebitoCompraLinea notaDebitoCompraLinea: notaDebitoCompra.getNotaDebitoCompraLineas()) {
            double valorDescuentoPorcentajeLinea = (notaDebitoCompraLinea.getTotalSinDescuentoLinea() * notaDebitoCompraLinea.getPorcentajeDescuentoLinea()) / 100;
            totalDescuento = totalDescuento + notaDebitoCompraLinea.getValorDescuentoLinea() + valorDescuentoPorcentajeLinea;
        }
        totalDescuento = Math.round(totalDescuento*100.0)/100.0;
        notaDebitoCompra.setDescuentoTotal(totalDescuento);
    }
    /*
     * FIN CALCULAR DESCUENTOS
     */
    
    /*
     * CALCULOS CON NOTA DEBITO COMPRA
     */
    private void calcularSubtotalSinDescuento(NotaDebitoCompra notaDebitoCompra) {
    	double subtotalSinDescuento = Constantes.cero;
        for(NotaDebitoCompraLinea notaDebitoCompraLinea: notaDebitoCompra.getNotaDebitoCompraLineas()){
          subtotalSinDescuento += notaDebitoCompraLinea.getTotalSinDescuentoLinea();
        }
        subtotalSinDescuento=Math.round(subtotalSinDescuento*100.0)/100.0;
        notaDebitoCompra.setSubtotalSinDescuento(subtotalSinDescuento);
    }
    
    private void calcularSubtotalBase12SinDescuento(NotaDebitoCompra notaDebitoCompra) {
    	double subtotalBase12SinDescuento = Constantes.cero;
    	for(NotaDebitoCompraLinea notaDebitoCompraLinea: notaDebitoCompra.getNotaDebitoCompraLineas()){
          if (notaDebitoCompraLinea.getProducto().getImpuesto().getPorcentaje() == Constantes.iva12){
            subtotalBase12SinDescuento += notaDebitoCompraLinea.getTotalSinDescuentoLinea();
          }
    	}
        subtotalBase12SinDescuento= Math.round(subtotalBase12SinDescuento*100.0)/100.0;
        notaDebitoCompra.setSubtotalBase12SinDescuento(subtotalBase12SinDescuento);
    }
    
    private void calcularSubtotalBase0SinDescuento(NotaDebitoCompra notaDebitoCompra) {
    	double subtotalBase0SinDescuento = Constantes.cero;
    	for(NotaDebitoCompraLinea notaDebitoCompraLinea: notaDebitoCompra.getNotaDebitoCompraLineas()){
          if (notaDebitoCompraLinea.getProducto().getImpuesto().getPorcentaje() == Constantes.iva0){
            subtotalBase0SinDescuento += notaDebitoCompraLinea.getTotalSinDescuentoLinea();
          }
        }
        subtotalBase0SinDescuento = Math.round(subtotalBase0SinDescuento*100.0)/100.0;
        notaDebitoCompra.setSubtotalBase0SinDescuento(subtotalBase0SinDescuento);
    }

    private void calcularIvaSinDescuento(NotaDebitoCompra notaDebitoCompra){
        double ivaSinDescuento=(notaDebitoCompra.getSubtotalBase12SinDescuento() * Constantes.iva12) / 100;
        ivaSinDescuento=Math.round(ivaSinDescuento*100.0)/100.0;
        notaDebitoCompra.setIvaSinDescuento(ivaSinDescuento);
    }

    private void calcularTotalSinDescuento(NotaDebitoCompra notaDebitoCompra){
        double totalSinDescuento = notaDebitoCompra.getSubtotalBase0SinDescuento() + notaDebitoCompra.getSubtotalBase12SinDescuento() + notaDebitoCompra.getIvaSinDescuento();
        totalSinDescuento=Math.round(totalSinDescuento*100.0)/100.0;
        notaDebitoCompra.setTotalSinDescuento(totalSinDescuento);
    }
    private void calcularTotalConDescuento(NotaDebitoCompra notaDebitoCompra){
        double totalConDescuento = notaDebitoCompra.getSubtotalBase0SinDescuento() + notaDebitoCompra.getSubtotalBase12SinDescuento() + notaDebitoCompra.getIvaSinDescuento() - notaDebitoCompra.getDescuentoTotal();
        totalConDescuento = Math.round(totalConDescuento*100.0)/100.0;
        notaDebitoCompra.setTotalConDescuento(totalConDescuento);
    }

    @Override
    public void validarLinea(NotaDebitoCompraLinea notaDebitoCompraLinea) {
        if(notaDebitoCompraLinea.getCantidad() < Constantes.cero) throw new DatoInvalidoException(Constantes.cantidad);
        if(notaDebitoCompraLinea.getCostoUnitario() < Constantes.cero) throw new DatoInvalidoException(Constantes.costoUnitario);
        if(notaDebitoCompraLinea.getValorDescuentoLinea() < Constantes.cero) throw new DatoInvalidoException(Constantes.valorDescuentoLinea);
        if(notaDebitoCompraLinea.getPorcentajeDescuentoLinea() < Constantes.cero) throw new DatoInvalidoException(Constantes.porcentajeDescuentoLinea);
        if(notaDebitoCompraLinea.getBodega().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.bodega);
        if(notaDebitoCompraLinea.getProducto().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.producto);
        if(notaDebitoCompraLinea.getValorDescuentoLinea() > notaDebitoCompraLinea.getCostoUnitario()) throw new DatoInvalidoException(Constantes.valorDescuentoLinea);
        if(notaDebitoCompraLinea.getPorcentajeDescuentoLinea() > 100) throw new DatoInvalidoException(Constantes.porcentajeDescuentoLinea);
    }

    @Override
    public NotaDebitoCompra obtenerPorFacturaCompra(long facturaCompraId){
        NotaDebitoCompra notaDebitoCompra = new NotaDebitoCompra();
        FacturaCompra facturaCompra = facturaCompraService.obtener(facturaCompraId);
        notaDebitoCompra.setFacturaCompra(facturaCompra);
        return notaDebitoCompra;
    }
}
