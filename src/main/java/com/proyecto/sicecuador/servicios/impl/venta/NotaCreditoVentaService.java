package com.proyecto.sicecuador.servicios.impl.venta;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.modelos.configuracion.TipoComprobante;
import com.proyecto.sicecuador.exception.*;
import com.proyecto.sicecuador.modelos.configuracion.Secuencial;
import com.proyecto.sicecuador.modelos.inventario.TipoOperacion;
import com.proyecto.sicecuador.modelos.venta.*;
import com.proyecto.sicecuador.modelos.inventario.Kardex;
import com.proyecto.sicecuador.repositorios.venta.INotaCreditoVentaRepository;
import com.proyecto.sicecuador.servicios.interf.configuracion.ISecuencialService;
import com.proyecto.sicecuador.servicios.interf.venta.IFacturaService;
import com.proyecto.sicecuador.servicios.interf.venta.INotaCreditoVentaService;
import com.proyecto.sicecuador.servicios.interf.configuracion.ITipoComprobanteService;
import com.proyecto.sicecuador.servicios.interf.inventario.IKardexService;
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
        if(notaCreditoVenta.getEstado().equals(Constantes.estadoFacturada)) throw new DatoInvalidoException(Constantes.estado);
        if(notaCreditoVenta.getFecha() == null) throw new DatoInvalidoException(Constantes.fecha);
        if(notaCreditoVenta.getSesion().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.sesion);
        if(notaCreditoVenta.getNotaCreditoVentaLineas().isEmpty()) throw new DatoInvalidoException(Constantes.nota_credito_venta_linea);
    }

    private void facturar(NotaCreditoVenta notaCreditoVenta) {
        if (notaCreditoVenta.getEstado().equals(Constantes.estadoFacturada))
            throw new DatoInvalidoException(Constantes.estado);
        if (notaCreditoVenta.getEstado().equals(Constantes.estadoAnulada))
            throw new DatoInvalidoException(Constantes.estado);
        List<NotaCreditoVenta> notasCreditoVentaAnt = rep.consultarPorFactura(notaCreditoVenta.getFactura().getId(), Constantes.estadoEmitida, Constantes.estadoFacturada);
        List<Long> cantidadesDevueltas = new ArrayList();
        for(int i = 0; i < notaCreditoVenta.getNotaCreditoVentaLineas().size(); i++ ) {
            cantidadesDevueltas.add(Constantes.ceroId);
        }
        for(int i = 0; i < notasCreditoVentaAnt.size(); i++ ){
            for(int j = 0; j < notasCreditoVentaAnt.get(i).getNotaCreditoVentaLineas().size(); j++){
                cantidadesDevueltas.set(j, cantidadesDevueltas.get(j) + notasCreditoVentaAnt.get(i).getNotaCreditoVentaLineas().get(j).getDevolucion());
            }
        }
        for(int i = 0; i<notaCreditoVenta.getNotaCreditoVentaLineas().size(); i++){
            if(notaCreditoVenta.getNotaCreditoVentaLineas().get(i).getDevolucion() + cantidadesDevueltas.get(i) > notaCreditoVenta.getNotaCreditoVentaLineas().get(i).getCantidad()){
                throw new DatoInvalidoException(Constantes.devolucion);
            }
        }
        //kardexService.eliminar(4, Constantes.operacion_conjunta, notaCreditoVenta.getSecuencial());
        //kardexService.eliminar(4, Constantes.operacion_devolucion, notaCreditoVenta.getSecuencial());
        kardexService.eliminar(4, 7, notaCreditoVenta.getSecuencial());
        if(notaCreditoVenta.getOperacion().equals(Constantes.operacion_conjunta)){
            for(NotaCreditoVentaLinea notaCreditoVentaLinea : notaCreditoVenta.getNotaCreditoVentaLineas()) {
                Kardex ultimoKardex = kardexService.obtenerUltimoPorBodega(notaCreditoVentaLinea.getBodega().getId(), notaCreditoVentaLinea.getProducto().getId());
                if (ultimoKardex != null) {
                    double saldo = ultimoKardex.getSaldo() + notaCreditoVentaLinea.getDevolucion();
                    Kardex kardex = new Kardex(null, new Date(), notaCreditoVenta.getSecuencial(),
                            notaCreditoVentaLinea.getDevolucion(), Constantes.cero, saldo,
                            notaCreditoVentaLinea.getTotalSinDescuentoLinea(), Constantes.cero,
                            notaCreditoVentaLinea.getCostoUnitario(), notaCreditoVentaLinea.getTotalSinDescuentoLinea(),
                            new TipoComprobante(4), new TipoOperacion(7), ultimoKardex.getBodega(), ultimoKardex.getProducto());
                    kardexService.crear(kardex);
                }
            }
        }
        if(notaCreditoVenta.getOperacion().equals(Constantes.operacion_devolucion)){
            for(NotaCreditoVentaLinea notaCreditoVentaLinea : notaCreditoVenta.getNotaCreditoVentaLineas()) {
                Kardex ultimoKardex = kardexService.obtenerUltimoPorBodega(notaCreditoVentaLinea.getBodega().getId(), notaCreditoVentaLinea.getProducto().getId());
                if (ultimoKardex != null) {
                    double saldo = ultimoKardex.getSaldo() + notaCreditoVentaLinea.getDevolucion();
                    Kardex kardex = new Kardex(null, new Date(),
                            notaCreditoVenta.getSecuencial(), notaCreditoVentaLinea.getDevolucion(), Constantes.cero, saldo,
                            notaCreditoVentaLinea.getTotalSinDescuentoLinea(), Constantes.cero,
                            notaCreditoVentaLinea.getCostoUnitario(), notaCreditoVentaLinea.getTotalSinDescuentoLinea(),
                            new TipoComprobante(4), new TipoOperacion(7), ultimoKardex.getBodega(), ultimoKardex.getProducto());
                    kardexService.crear(kardex);
                }
            }
        }
        if(notaCreditoVenta.getOperacion().equals(Constantes.operacion_descuento)){
            kardexService.eliminar(4, 7, notaCreditoVenta.getSecuencial());
            for(NotaCreditoVentaLinea notaCreditoCompraLinea : notaCreditoVenta.getNotaCreditoVentaLineas()) {
                Kardex ultimoKardex = kardexService.obtenerUltimoPorBodega(notaCreditoCompraLinea.getBodega().getId(), notaCreditoCompraLinea.getProducto().getId());
                if(ultimoKardex != null){
                    Kardex kardex = new Kardex(null, new Date(), notaCreditoVenta.getSecuencial(), Constantes.cero,
                            ultimoKardex.getSalida(), ultimoKardex.getSaldo(), Constantes.cero, notaCreditoCompraLinea.getTotalSinDescuentoLinea(),
                            notaCreditoCompraLinea.getCostoUnitario(), notaCreditoCompraLinea.getTotalSinDescuentoLinea(),
                            new TipoComprobante(4), new TipoOperacion(7), ultimoKardex.getBodega(), ultimoKardex.getProducto());
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
        Optional<String>codigo=Util.generarCodigoPorEmpresa(Constantes.tabla_nota_credito_venta, notaCreditoVenta.getEmpresa().getId());
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
        notaCreditoVenta.setCodigo(codigo.get());
        notaCreditoVenta.setSerie(notaCreditoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getCodigoSRI() + notaCreditoVenta.getSesion().getUsuario().getEstacion().getCodigoSRI());
        Secuencial secuencial = secuencialService.obtenerPorTipoComprobanteYEstacion(notaCreditoVenta.getTipoComprobante().getId(), notaCreditoVenta.getSesion().getUsuario().getEstacion().getId());
        notaCreditoVenta.setSecuencial(Util.generarSecuencial(secuencial.getNumeroSiguiente()));
        notaCreditoVenta.setCodigoNumerico(Util.generarCodigoNumerico(secuencial.getNumeroSiguiente()));
        Optional<String> claveAcceso = crearClaveAcceso(notaCreditoVenta);
        if (claveAcceso.isEmpty()) {
            throw new ClaveAccesoNoExistenteException();
        }
        notaCreditoVenta.setClaveAcceso(claveAcceso.get());
        notaCreditoVenta.setEstado(Constantes.estadoEmitida);
        calcular(notaCreditoVenta);
        facturar(notaCreditoVenta);
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

    @Override
    public NotaCreditoVenta actualizar(NotaCreditoVenta notaCreditoVenta) {
        validar(notaCreditoVenta);
        calcular(notaCreditoVenta);
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
    public NotaCreditoVenta calcular(NotaCreditoVenta notaCreditoVenta) {
        this.calcularTotalSinDescuentoLinea(notaCreditoVenta);
        this.calcularIvaSinDescuentoLinea(notaCreditoVenta);
        this.calcularSubtotalSinDescuento(notaCreditoVenta);
        this.calcularSubtotalBase12SinDescuento(notaCreditoVenta);
        this.calcularSubtotalBase0SinDescuento(notaCreditoVenta);
        this.calcularIvaSinDescuento(notaCreditoVenta);
        this.calcularDescuentoTotal(notaCreditoVenta);
        this.calcularTotalSinDescuento(notaCreditoVenta);
        this.calcularTotalConDescuento(notaCreditoVenta);
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
    private void calcularIvaSinDescuentoLinea(NotaCreditoVenta notaCreditoVenta) {
        for(NotaCreditoVentaLinea notaCreditoVentaLinea: notaCreditoVenta.getNotaCreditoVentaLineas()) {
            validarLinea(notaCreditoVentaLinea);
            double ivaSinDescuentoLinea = notaCreditoVentaLinea.getTotalSinDescuentoLinea() * notaCreditoVentaLinea.getImpuesto().getPorcentaje()/100;
            ivaSinDescuentoLinea = Math.round(ivaSinDescuentoLinea*100.0)/100.0;
            notaCreditoVentaLinea.setIvaSinDescuentoLinea(ivaSinDescuentoLinea);
        }
    }
    /*
     * FIN CALCULO NOTA CREDITO VENTA LINEA
     */
    
    /*
     * CALCULAR DESCUENTOS
     */
    private void calcularDescuentoTotal(NotaCreditoVenta notaCreditoVenta) {
        double totalDescuento = Constantes.cero;
        for(NotaCreditoVentaLinea notaCreditoVentaLinea: notaCreditoVenta.getNotaCreditoVentaLineas()) {
            double valorDescuentoPorcentajeLinea = (notaCreditoVentaLinea.getTotalSinDescuentoLinea() * notaCreditoVentaLinea.getPorcentajeDescuentoLinea()) / 100;
            totalDescuento = totalDescuento + notaCreditoVentaLinea.getValorDescuentoLinea() + valorDescuentoPorcentajeLinea;
        }
        totalDescuento = Math.round(totalDescuento*100.0)/100.0;
        notaCreditoVenta.setDescuentoTotal(totalDescuento);
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
        double totalSinDescuento = notaCreditoVenta.getSubtotalBase0SinDescuento() + notaCreditoVenta.getSubtotalBase12SinDescuento();
        totalSinDescuento=Math.round(totalSinDescuento*100.0)/100.0;
        notaCreditoVenta.setTotalSinDescuento(totalSinDescuento);
    }
    private void calcularTotalConDescuento(NotaCreditoVenta notaCreditoVenta){
        double totalConDescuento = notaCreditoVenta.getSubtotalBase0SinDescuento() + notaCreditoVenta.getSubtotalBase12SinDescuento() + notaCreditoVenta.getIvaSinDescuento() - notaCreditoVenta.getDescuentoTotal();
        totalConDescuento = Math.round(totalConDescuento*100.0)/100.0;
        notaCreditoVenta.setTotalConDescuento(totalConDescuento);
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
            notaCreditoVentaLinea.setCostoUnitario(facturaLinea.getPrecioUnitario());
            notaCreditoVentaLinea.setCantidad(facturaLinea.getCantidad());
            notaCreditoVenta.getNotaCreditoVentaLineas().add(notaCreditoVentaLinea);
        }
        return notaCreditoVenta;
    }
}
