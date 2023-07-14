package com.proyecto.vision.servicios.impl.compra;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.modelos.configuracion.TipoComprobante;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.compra.FacturaCompra;
import com.proyecto.vision.modelos.compra.FacturaCompraLinea;
import com.proyecto.vision.modelos.compra.NotaCreditoCompra;
import com.proyecto.vision.modelos.compra.NotaCreditoCompraLinea;
import com.proyecto.vision.modelos.configuracion.Secuencial;
import com.proyecto.vision.modelos.inventario.Kardex;
import com.proyecto.vision.modelos.inventario.TipoOperacion;
import com.proyecto.vision.repositorios.compra.INotaCreditoCompraRepository;
import com.proyecto.vision.servicios.interf.compra.IFacturaCompraService;
import com.proyecto.vision.servicios.interf.compra.INotaCreditoCompraService;
import com.proyecto.vision.servicios.interf.configuracion.ISecuencialService;
import com.proyecto.vision.servicios.interf.configuracion.ITipoComprobanteService;
import com.proyecto.vision.servicios.interf.inventario.IKardexService;
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
public class NotaCreditoCompraService implements INotaCreditoCompraService {
    @Autowired
    private INotaCreditoCompraRepository rep;
    @Autowired
    private ITipoComprobanteService tipoComprobanteService;
    @Autowired
    private IKardexService kardexService;
    @Autowired
    private IFacturaCompraService facturaCompraService;
    @Autowired
    private ISecuencialService secuencialService;

    @Override
    public void validar(NotaCreditoCompra notaCreditoCompra) {
        if(notaCreditoCompra.getFecha() == null) throw new DatoInvalidoException(Constantes.fecha);
        if(notaCreditoCompra.getSesion().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.sesion);
        if(notaCreditoCompra.getNotaCreditoCompraLineas().isEmpty()) throw new DatoInvalidoException(Constantes.nota_credito_compra_linea);
    }

    private void facturar(NotaCreditoCompra notaCreditoCompra) {
        if(notaCreditoCompra.getEstado().equals(Constantes.estadoInactivo)) throw new DatoInvalidoException(Constantes.estado);
        if(notaCreditoCompra.getEstadoInterno().equals(Constantes.estadoInternoAnulada)) throw new DatoInvalidoException(Constantes.estado);
        List<NotaCreditoCompra> notasCreditoCompraAnt = rep.consultarPorFacturaCompraYEstadoInternoYEstado(notaCreditoCompra.getFacturaCompra().getId(), Constantes.estadoInternoPagada, Constantes.estadoActivo);
        List<Long> cantidadesDevueltas = new ArrayList();
        for(int i = 0; i < notaCreditoCompra.getNotaCreditoCompraLineas().size(); i++ ) {
            cantidadesDevueltas.add(Constantes.ceroId);
        }
        for(int i = 0; i < notasCreditoCompraAnt.size(); i++ ){
            for(int j = 0; j < notasCreditoCompraAnt.get(i).getNotaCreditoCompraLineas().size(); j++){
                cantidadesDevueltas.set(j, cantidadesDevueltas.get(j) + notasCreditoCompraAnt.get(i).getNotaCreditoCompraLineas().get(j).getDevolucion());
            }
        }
        for(int i = 0; i<notaCreditoCompra.getNotaCreditoCompraLineas().size(); i++){
            if(notaCreditoCompra.getNotaCreditoCompraLineas().get(i).getDevolucion() + cantidadesDevueltas.get(i) > notaCreditoCompra.getNotaCreditoCompraLineas().get(i).getCantidad()){
                throw new DatoInvalidoException(Constantes.devolucion);
            }
        }
        kardexService.eliminar(9, 6, notaCreditoCompra.getSecuencial());
        if(notaCreditoCompra.getOperacion().equals(Constantes.operacion_conjunta)){
            for(NotaCreditoCompraLinea notaCreditoCompraLinea : notaCreditoCompra.getNotaCreditoCompraLineas()) {
                Kardex ultimoKardex = kardexService.obtenerUltimoPorBodega(notaCreditoCompraLinea.getBodega().getId(), notaCreditoCompraLinea.getProducto().getId());
                if (ultimoKardex != null) {
                    double saldo = ultimoKardex.getSaldo() - notaCreditoCompraLinea.getDevolucion();
                    Kardex kardex = new Kardex(null, new Date(),
                            notaCreditoCompra.getSecuencial(), Constantes.cero, notaCreditoCompraLinea.getDevolucion(), saldo,
                            Constantes.cero, notaCreditoCompraLinea.getTotalSinDescuentoLinea(),
                            notaCreditoCompraLinea.getCostoUnitario(), notaCreditoCompraLinea.getTotalSinDescuentoLinea(),
                            new TipoComprobante(9), new TipoOperacion(7), ultimoKardex.getBodega(), ultimoKardex.getProducto());
                    kardexService.crear(kardex);
                }
            }
        }
        if(notaCreditoCompra.getOperacion().equals(Constantes.operacion_devolucion)){
            for(NotaCreditoCompraLinea notaCreditoCompraLinea : notaCreditoCompra.getNotaCreditoCompraLineas()) {
                Kardex ultimoKardex = kardexService.obtenerUltimoPorBodega(notaCreditoCompraLinea.getBodega().getId(), notaCreditoCompraLinea.getProducto().getId());
                if (ultimoKardex != null) {
                    double saldo = ultimoKardex.getSaldo() - notaCreditoCompraLinea.getDevolucion();
                    Kardex kardex = new Kardex(null, new Date(),
                            notaCreditoCompra.getSecuencial(), Constantes.cero, notaCreditoCompraLinea.getDevolucion(), saldo,
                            Constantes.cero, notaCreditoCompraLinea.getTotalSinDescuentoLinea(),
                            ultimoKardex.getCostoPromedio(),notaCreditoCompraLinea.getTotalSinDescuentoLinea(),
                            new TipoComprobante(9), new TipoOperacion(7), ultimoKardex.getBodega(), ultimoKardex.getProducto());
                    kardexService.crear(kardex);
                }
            }
        }
        if(notaCreditoCompra.getOperacion().equals(Constantes.operacion_descuento)){
            for(NotaCreditoCompraLinea notaCreditoCompraLinea : notaCreditoCompra.getNotaCreditoCompraLineas()) {
                Kardex ultimoKardex = kardexService.obtenerUltimoPorBodega(notaCreditoCompraLinea.getBodega().getId(), notaCreditoCompraLinea.getProducto().getId());
                if(ultimoKardex != null){
                    Kardex kardex = new Kardex(null, new Date(),
                            notaCreditoCompra.getSecuencial(), Constantes.cero, ultimoKardex.getSalida(), ultimoKardex.getSaldo(),
                            Constantes.cero, notaCreditoCompraLinea.getTotalSinDescuentoLinea(),
                            notaCreditoCompraLinea.getCostoUnitario(), notaCreditoCompraLinea.getTotalSinDescuentoLinea(),
                            new TipoComprobante(9), new TipoOperacion(7), ultimoKardex.getBodega(), ultimoKardex.getProducto());
                    kardexService.crear(kardex);
                }
            }
        }
    }

    @Transactional
    @Override
    public NotaCreditoCompra crear(NotaCreditoCompra notaCreditoCompra) {
        validar(notaCreditoCompra);
        TipoComprobante tipoComprobante = tipoComprobanteService.obtenerPorNombreTabla(Constantes.tabla_nota_credito_compra);
        notaCreditoCompra.setTipoComprobante(tipoComprobante);
        Optional<String>codigo=Util.generarCodigoPorEmpresa(Constantes.tabla_nota_credito_compra, notaCreditoCompra.getEmpresa().getId());
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
        notaCreditoCompra.setCodigo(codigo.get());
        notaCreditoCompra.setSerie(notaCreditoCompra.getSesion().getUsuario().getEstacion().getEstablecimiento().getCodigoSRI() + notaCreditoCompra.getSesion().getUsuario().getEstacion().getCodigoSRI());
        Secuencial secuencial = secuencialService.obtenerPorTipoComprobanteYEstacion(notaCreditoCompra.getTipoComprobante().getId(), notaCreditoCompra.getSesion().getUsuario().getEstacion().getId());
        notaCreditoCompra.setSecuencial(Util.generarSecuencial(secuencial.getNumeroSiguiente()));
        notaCreditoCompra.setEstado(Constantes.estadoActivo);
        notaCreditoCompra.setEstadoInterno(Constantes.estadoInternoPorPagar);
        calcular(notaCreditoCompra);
        facturar(notaCreditoCompra);
        NotaCreditoCompra res = rep.save(notaCreditoCompra);
        res.normalizar();
        secuencial.setNumeroSiguiente(secuencial.getNumeroSiguiente()+1);
        secuencialService.actualizar(secuencial);
        return res;
    }

    @Override
    public NotaCreditoCompra actualizar(NotaCreditoCompra notaCreditoCompra) {
        validar(notaCreditoCompra);
        calcular(notaCreditoCompra);
        facturar(notaCreditoCompra);
        NotaCreditoCompra res = rep.save(notaCreditoCompra);
        res.normalizar();
        return res;
    }

    @Override
    public NotaCreditoCompra activar(NotaCreditoCompra notaCreditoCompra) {
        validar(notaCreditoCompra);
        notaCreditoCompra.setEstado(Constantes.estadoActivo);
        NotaCreditoCompra res = rep.save(notaCreditoCompra);
        res.normalizar();
        return res;
    }

    @Override
    public NotaCreditoCompra inactivar(NotaCreditoCompra notaCreditoCompra) {
        validar(notaCreditoCompra);
        notaCreditoCompra.setEstado(Constantes.estadoInactivo);
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
    public List<NotaCreditoCompra> consultar() {
        return rep.consultar();
    }
    
    @Override
    public List<NotaCreditoCompra> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public Page<NotaCreditoCompra> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public List<NotaCreditoCompra> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    @Override
    public List<NotaCreditoCompra> consultarPorEmpresaYEstado(long empresaId, String estado){
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }

    @Override
    public NotaCreditoCompra calcular(NotaCreditoCompra notaCreditoCompra) {
        this.calcularTotalSinDescuentoLinea(notaCreditoCompra);
        this.calcularIvaSinDescuentoLinea(notaCreditoCompra);
        this.calcularSubtotalSinDescuento(notaCreditoCompra);
        this.calcularSubtotalBase12SinDescuento(notaCreditoCompra);
        this.calcularSubtotalBase0SinDescuento(notaCreditoCompra);
        this.calcularIvaSinDescuento(notaCreditoCompra);
        this.calcularDescuentoTotal(notaCreditoCompra);
        this.calcularTotalSinDescuento(notaCreditoCompra);
        this.calcularTotalConDescuento(notaCreditoCompra);
        return notaCreditoCompra;
    }
    /*
     * CALCULOS CON NOTA CREDITO COMPRA LINEAS
     */
    private void calcularTotalSinDescuentoLinea(NotaCreditoCompra notaCreditoCompra) {
    	for(NotaCreditoCompraLinea notaCreditoCompraLinea: notaCreditoCompra.getNotaCreditoCompraLineas()) {
            validarLinea(notaCreditoCompraLinea);
    		double totalSinDescuentoLinea = notaCreditoCompraLinea.getDevolucion() * notaCreditoCompraLinea.getCostoUnitario();
        	totalSinDescuentoLinea=Math.round(totalSinDescuentoLinea*100.0)/100.0;
            notaCreditoCompraLinea.setTotalSinDescuentoLinea(totalSinDescuentoLinea);
    	}
    }
    private void calcularIvaSinDescuentoLinea(NotaCreditoCompra notaCreditoCompra) {
        for(NotaCreditoCompraLinea notaCreditoCompraLinea: notaCreditoCompra.getNotaCreditoCompraLineas()) {
            validarLinea(notaCreditoCompraLinea);
            double ivaSinDescuentoLinea = notaCreditoCompraLinea.getTotalSinDescuentoLinea() * notaCreditoCompraLinea.getImpuesto().getPorcentaje()/100;
            ivaSinDescuentoLinea = Math.round(ivaSinDescuentoLinea*100.0)/100.0;
            notaCreditoCompraLinea.setIvaSinDescuentoLinea(ivaSinDescuentoLinea);
        }
    }
    /*
     * FIN CALCULO NOTA DE CREDITO COMPRA LINEAS
     */
    
    /*
     * CALCULOS CON NOTA DE CREDITO DE COMPRA
     */
    /*
     * CALCULAR DESCUENTOS
     */
    private void calcularDescuentoTotal(NotaCreditoCompra notaCreditoCompra) {
        double totalDescuento = Constantes.cero;
        for(NotaCreditoCompraLinea notaCreditoCompraLinea: notaCreditoCompra.getNotaCreditoCompraLineas()) {
            double valorDescuentoPorcentajeLinea = (notaCreditoCompraLinea.getTotalSinDescuentoLinea() * notaCreditoCompraLinea.getPorcentajeDescuentoLinea()) / 100;
            totalDescuento = totalDescuento + notaCreditoCompraLinea.getValorDescuentoLinea() + valorDescuentoPorcentajeLinea;
        }
        totalDescuento = Math.round(totalDescuento * 100.0) / 100.0;
        notaCreditoCompra.setDescuentoTotal(totalDescuento);
    }
    /*
     * FIN CALCULAR DESCUENTOS
     */
    private void calcularSubtotalSinDescuento(NotaCreditoCompra notaCreditoCompra) {
    	double subtotalSinDescuento = Constantes.cero;
        for(NotaCreditoCompraLinea notaCreditoCompraLinea: notaCreditoCompra.getNotaCreditoCompraLineas()){
          subtotalSinDescuento+=notaCreditoCompraLinea.getTotalSinDescuentoLinea();
        }
        subtotalSinDescuento=Math.round(subtotalSinDescuento*100.0)/100.0;
        notaCreditoCompra.setSubtotalSinDescuento(subtotalSinDescuento);
    }
    
    private void calcularSubtotalBase12SinDescuento(NotaCreditoCompra notaCreditoCompra) {
    	double subtotalBase12SinDescuento = Constantes.cero;
    	for(NotaCreditoCompraLinea notaCreditoCompraLinea: notaCreditoCompra.getNotaCreditoCompraLineas()){
          if (notaCreditoCompraLinea.getProducto().getImpuesto().getPorcentaje() == Constantes.iva12){
            subtotalBase12SinDescuento+=notaCreditoCompraLinea.getTotalSinDescuentoLinea();
          }
    	}
        subtotalBase12SinDescuento= Math.round(subtotalBase12SinDescuento*100.0)/100.0;
        notaCreditoCompra.setSubtotalBase12SinDescuento(subtotalBase12SinDescuento);
    }
    
    private void calcularSubtotalBase0SinDescuento(NotaCreditoCompra notaCreditoCompra) {
    	double subtotalBase0SinDescuento = Constantes.cero;
    	for(NotaCreditoCompraLinea notaCreditoCompraLinea: notaCreditoCompra.getNotaCreditoCompraLineas()){
          if (notaCreditoCompraLinea.getProducto().getImpuesto().getPorcentaje() == Constantes.iva0){
            subtotalBase0SinDescuento += notaCreditoCompraLinea.getTotalSinDescuentoLinea();
          }
        }
        subtotalBase0SinDescuento = Math.round(subtotalBase0SinDescuento*100.0)/100.0;
        notaCreditoCompra.setSubtotalBase0SinDescuento(subtotalBase0SinDescuento);
    }

    private void calcularIvaSinDescuento(NotaCreditoCompra notaCreditoCompra){
        double ivaSinDescuento=(notaCreditoCompra.getSubtotalBase12SinDescuento() * Constantes.iva12) / 100;
        ivaSinDescuento=Math.round(ivaSinDescuento*100.0)/100.0;
        notaCreditoCompra.setIvaSinDescuento(ivaSinDescuento);
    }

    private void calcularTotalSinDescuento(NotaCreditoCompra notaCreditoCompra){
        double totalSinDescuento = notaCreditoCompra.getSubtotalBase0SinDescuento() + notaCreditoCompra.getSubtotalBase12SinDescuento() + notaCreditoCompra.getIvaSinDescuento();
        totalSinDescuento=Math.round(totalSinDescuento*100.0)/100.0;
        notaCreditoCompra.setTotalSinDescuento(totalSinDescuento);
    }
    private void calcularTotalConDescuento(NotaCreditoCompra notaCreditoCompra){
        double totalConDescuento = notaCreditoCompra.getSubtotalBase0SinDescuento() + notaCreditoCompra.getSubtotalBase12SinDescuento() + notaCreditoCompra.getIvaSinDescuento() - notaCreditoCompra.getDescuentoTotal();
        totalConDescuento = Math.round(totalConDescuento*100.0)/100.0;
        notaCreditoCompra.setTotalConDescuento(totalConDescuento);
    }

    @Override
    public void validarLinea(NotaCreditoCompraLinea notaCreditoCompraLinea) {
        if(notaCreditoCompraLinea.getCantidad() < Constantes.cero) throw new DatoInvalidoException(Constantes.cantidad);
        if(notaCreditoCompraLinea.getDevolucion() < Constantes.cero) throw new DatoInvalidoException(Constantes.devolucion);
        if(notaCreditoCompraLinea.getCostoUnitario() < Constantes.cero) throw new DatoInvalidoException(Constantes.costoUnitario);
        if(notaCreditoCompraLinea.getValorDescuentoLinea() < Constantes.cero) throw new DatoInvalidoException(Constantes.valorDescuentoLinea);
        if(notaCreditoCompraLinea.getPorcentajeDescuentoLinea() < Constantes.cero) throw new DatoInvalidoException(Constantes.porcentajeDescuentoLinea);
        if(notaCreditoCompraLinea.getBodega().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.bodega);
        if(notaCreditoCompraLinea.getProducto().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.producto);
        if(notaCreditoCompraLinea.getDevolucion() > notaCreditoCompraLinea.getCantidad()) throw new DatoInvalidoException(Constantes.devolucion);
        if(notaCreditoCompraLinea.getValorDescuentoLinea() > notaCreditoCompraLinea.getCostoUnitario()) throw new DatoInvalidoException(Constantes.valorDescuentoLinea);
        if(notaCreditoCompraLinea.getPorcentajeDescuentoLinea() > 100) throw new DatoInvalidoException(Constantes.porcentajeDescuentoLinea);
    }

    @Override
    public NotaCreditoCompra obtenerPorFacturaCompra(long facturaCompraId){
        NotaCreditoCompra notaCreditoCompra = new NotaCreditoCompra();
        FacturaCompra facturaCompra = facturaCompraService.obtener(facturaCompraId);
        notaCreditoCompra.setFacturaCompra(facturaCompra);
        notaCreditoCompra.setNotaCreditoCompraLineas(new ArrayList<>());
        for(FacturaCompraLinea facturaCompraLinea: facturaCompra.getFacturaCompraLineas()){
            NotaCreditoCompraLinea notaCreditoCompraLinea = new NotaCreditoCompraLinea();
            notaCreditoCompraLinea.setImpuesto(facturaCompraLinea.getImpuesto());
            notaCreditoCompraLinea.setBodega(facturaCompraLinea.getBodega());
            notaCreditoCompraLinea.setProducto(facturaCompraLinea.getProducto());
            notaCreditoCompraLinea.setCostoUnitario(facturaCompraLinea.getCostoUnitario());
            notaCreditoCompraLinea.setCantidad(facturaCompraLinea.getCantidad());
            notaCreditoCompra.getNotaCreditoCompraLineas().add(notaCreditoCompraLinea);
        }
        return notaCreditoCompra;
    }
}
