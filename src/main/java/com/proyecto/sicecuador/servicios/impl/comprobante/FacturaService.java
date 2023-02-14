package com.proyecto.sicecuador.servicios.impl.comprobante;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.*;
import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.modelos.comprobante.FacturaDetalle;
import com.proyecto.sicecuador.modelos.comprobante.TipoComprobante;
import com.proyecto.sicecuador.modelos.inventario.Kardex;
import com.proyecto.sicecuador.modelos.recaudacion.Recaudacion;
import com.proyecto.sicecuador.repositorios.comprobante.IFacturaRepository;
import com.proyecto.sicecuador.servicios.interf.comprobante.IFacturaService;
import com.proyecto.sicecuador.servicios.interf.comprobante.ITipoComprobanteService;
import com.proyecto.sicecuador.servicios.interf.inventario.IKardexService;
import com.proyecto.sicecuador.servicios.interf.recaudacion.IRecaudacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FacturaService implements IFacturaService {
    @Autowired
    private IFacturaRepository rep;
    @Autowired
    private IKardexService kardexService;
    @Autowired
    private ITipoComprobanteService tipoComprobanteService;

    @Autowired
    private IRecaudacionService recaudacionService;

    @Override
    public void validar(Factura factura) {
        if(factura.getFecha() == null) throw new DatoInvalidoException(Constantes.fecha);
        if(factura.getCliente().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.cliente);
        if(factura.getSesion().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.sesion);
        if(factura.getFacturaDetalles().isEmpty()) throw new DatoInvalidoException(Constantes.factura_detalle);
    }

    private void facturar(Factura factura) {
        if(factura.getEstado().equals(Constantes.estadoFacturada)) throw new DatoInvalidoException(Constantes.estado);
        if(factura.getEstado().equals(Constantes.estadoAnulada)) throw new DatoInvalidoException(Constantes.estado);
        kardexService.eliminar(Constantes.factura, Constantes.operacion_venta, factura.getSecuencia());
        for(FacturaDetalle facturaDetalle: factura.getFacturaDetalles()){
            Kardex ultimoKardex = kardexService.obtenerUltimoPorFecha(facturaDetalle.getBodega().getId(), facturaDetalle.getProducto().getId());
            if(ultimoKardex == null){
                throw new DatoInvalidoException(Constantes.kardex);
            }
            if(ultimoKardex.getSaldo() < facturaDetalle.getCantidad()){
                throw new DatoInvalidoException(Constantes.kardex);
            }
            double saldo = ultimoKardex.getSaldo() - facturaDetalle.getCantidad();
            Kardex kardex = new Kardex(null, new Date(), Constantes.factura, Constantes.operacion_venta,
                    factura.getSecuencia(), Constantes.cero, facturaDetalle.getCantidad(), saldo,
                    Constantes.cero, facturaDetalle.getSubtotalSinDescuentoLinea(),
                    facturaDetalle.getCantidad(), facturaDetalle.getPrecio().getPrecioVentaPublicoManual(), facturaDetalle.getSubtotalSinDescuentoLinea(),
                    facturaDetalle.getBodega(), facturaDetalle.getProducto());
            kardexService.crear(kardex);
        }
    }

    @Override
    public Factura crear(Factura factura) {
        validar(factura);
        TipoComprobante tipoComprobante = tipoComprobanteService.obtenerPorNombreTabla(Constantes.tabla_factura);
        factura.setTipoComprobante(tipoComprobante);
        Optional<String>codigo=Util.generarCodigo(Constantes.tabla_factura);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	factura.setCodigo(codigo.get());
    	Optional<String>secuencia=Util.generarSecuencia(Constantes.tabla_factura);
    	if (secuencia.isEmpty()) {
    		throw new SecuenciaNoExistenteException();
    	}
    	factura.setSecuencia(secuencia.get());
    	Optional<String> codigoNumerico = Util.generarCodigoNumerico(Constantes.tabla_factura);
    	if (codigoNumerico.isEmpty()) {
    		throw new CodigoNumericoNoExistenteException();
    	}
    	factura.setCodigoNumerico(codigoNumerico.get());
    	Optional<String> claveAcceso= crearClaveAcceso(factura);
    	if (claveAcceso.isEmpty()) {
    		throw new ClaveAccesoNoExistenteException();
    	}
    	factura.setClaveAcceso(claveAcceso.get());
    	factura.setEstado(Constantes.estadoEmitida);
        facturar(factura);
        Factura res = rep.save(factura);
        res.normalizar();
        return res;
    }
    
    private Optional<String> crearClaveAcceso(Factura factura) {
    	DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");  
    	String fechaEmision = dateFormat.format(factura.getFecha());
    	String tipoComprobante=Constantes.factura_sri;
    	String numeroRuc=factura.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getIdentificacion();
    	String tipoAmbiente=Constantes.pruebas_sri;
    	String serie=factura.getSesion().getUsuario().getEstacion().getEstablecimiento().getCodigoSRI() + factura.getSesion().getUsuario().getEstacion().getCodigoSRI();
    	String numeroComprobante=factura.getSecuencia();
    	String codigoNumerico=factura.getCodigoNumerico();
    	String tipoEmision=Constantes.emision_normal_sri;
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
    public Factura actualizar(Factura factura) {
        validar(factura);
        Recaudacion recaudacion = recaudacionService.obtenerPorFactura(factura.getId());
        if(recaudacion != null && recaudacion.getEstado().equals(Constantes.recaudado)) {
            throw new EstadoInvalidoException(Constantes.recaudacion);
        }
        facturar(factura);
        Factura res = rep.save(factura);
        res.normalizar();
        return res;
    }

    @Override
    public Factura activar(Factura factura) {
        validar(factura);
        factura.setEstado(Constantes.activo);
        Factura res = rep.save(factura);
        res.normalizar();
        return res;
    }

    @Override
    public Factura inactivar(Factura factura) {
        validar(factura);
        factura.setEstado(Constantes.inactivo);
        Factura res = rep.save(factura);
        res.normalizar();
        return res;
    }

    @Override
    public Factura obtener(long id) {
        Optional<Factura> factura = rep.findById(id);
        if(factura.isPresent()) {
        	Factura res = factura.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.factura);
    }

    @Override
    public List<Factura> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<Factura> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<Factura> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public List<Factura> buscar(Factura factura) {
        return  rep.findAll((root, criteriaQuery, criteriaBuilder) -> {
		    List<Predicate> predicates = new ArrayList<>();
		    if (!factura.getSecuencia().equals(Constantes.vacio)) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("codigo"), "%"+factura.getSecuencia()+"%")));
		    }
		    if (!factura.getCliente().getRazonSocial().equals(Constantes.vacio)) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("cliente").get("razonSocial"), "%"+factura.getCliente().getRazonSocial()+"%")));
		    }
		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		});
    }
    
    public Factura calcular(Factura factura) {
    	this.recalcular(factura);
    	if(factura.getValorDescuentoSubtotal()==0 && factura.getPorcentajeDescuentoSubtotal()==0 && factura.getValorDescuentoTotal()==0 && factura.getPorcentajeDescuentoTotal()==0) {
    		this.calcularSubtotalSinDescuentoLinea(factura);
            this.calcularValorPorcentajeDescuentoLinea(factura);
            this.calcularTotalDescuentoLinea(factura);
            this.calcularIvaSinDescuentoLinea(factura);
            this.calcularSubtotalConDescuentoLinea(factura);
            this.calcularIvaConDescuentoLinea(factura);
            this.calcularTotalConDescuentoLinea(factura);
                
    		this.calcularSubtotalSinDescuento(factura);
            this.calcularSubtotalConDescuento(factura);
            this.calcularDescuentoTotal(factura);
            this.calcularSubtotalBase12SinDescuento(factura);
            this.calcularSubtotalBase0SinDescuento(factura);
            this.calcularSubtotalBase12ConDescuento(factura);
            this.calcularSubtotalBase0ConDescuento(factura);
            this.calcularIvaSinDescuento(factura);
            this.calcularIvaConDescuento(factura);
            this.calcularTotalSinDescuento(factura);
            this.calcularTotalConDescuento(factura);
    	} else {
    		
    		this.calcularSubtotalSinDescuentoLinea(factura);
    		this.calcularSubtotalSinDescuento(factura);
            
    		this.calcularValorDescuentoLineaPorDescuentosGenerales(factura);
    		this.calcularPorcentajeDescuentoLineaPorDescuentosGenerales(factura);
    		this.calcularValorPorcentajeDescuentoLineaPorDescuentosGenerales(factura);
    		this.calcularValorDescuentoTotalLineaPorDescuentosGenerales(factura);
    		this.calcularValorPorcentajeDescuentoTotalLineaPorDescuentosGenerales(factura);
    		this.calcularPorcentajeDescuentoTotalLineaPorDescuentosGenerales(factura);
    		this.calcularTotalDescuentoLinea(factura);
    		this.calcularIvaSinDescuentoLinea(factura);
            this.calcularSubtotalConDescuentoLinea(factura);
            this.calcularIvaConDescuentoLinea(factura);
            this.calcularTotalConDescuentoLinea(factura);
            
            this.calcularSubtotalConDescuento(factura);
            this.calcularDescuentoTotal(factura);
            this.calcularSubtotalBase12SinDescuento(factura);
            this.calcularSubtotalBase0SinDescuento(factura);
            this.calcularSubtotalBase12ConDescuento(factura);
            this.calcularSubtotalBase0ConDescuento(factura);
            this.calcularIvaSinDescuento(factura);
            this.calcularIvaConDescuento(factura);
            this.calcularTotalSinDescuento(factura);
            this.calcularTotalConDescuento(factura);
            
            //AQUI
            this.calcularValorPorcentajeDescuentoTotal(factura);
            // CIERRO AQUI
            
            this.calcularSubtotalSinDescuentoLinea(factura);
    		this.calcularSubtotalSinDescuento(factura);
            
    		this.calcularValorDescuentoLineaPorDescuentosGenerales(factura);
    		this.calcularPorcentajeDescuentoLineaPorDescuentosGenerales(factura);
    		this.calcularValorPorcentajeDescuentoLineaPorDescuentosGenerales(factura);
    		this.calcularValorDescuentoTotalLineaPorDescuentosGenerales(factura);
    		this.calcularValorPorcentajeDescuentoTotalLineaPorDescuentosGenerales(factura);
    		this.calcularPorcentajeDescuentoTotalLineaPorDescuentosGenerales(factura);
    		this.calcularTotalDescuentoLinea(factura);
    		this.calcularIvaSinDescuentoLinea(factura);
            this.calcularSubtotalConDescuentoLinea(factura);
            this.calcularIvaConDescuentoLinea(factura);
            this.calcularTotalConDescuentoLinea(factura);
            
            this.calcularSubtotalConDescuento(factura);
            this.calcularDescuentoTotal(factura);
            this.calcularSubtotalBase12SinDescuento(factura);
            this.calcularSubtotalBase0SinDescuento(factura);
            this.calcularSubtotalBase12ConDescuento(factura);
            this.calcularSubtotalBase0ConDescuento(factura);
            this.calcularIvaSinDescuento(factura);
            this.calcularIvaConDescuento(factura);
            this.calcularTotalSinDescuento(factura);
            this.calcularTotalConDescuento(factura);
    	}
        return factura;
    }
    
    private void recalcular(Factura factura){
    	for(FacturaDetalle facturaDetalle: factura.getFacturaDetalles()) {
    		facturaDetalle.setPorcentajeDescuentoTotalLinea(0);
    		facturaDetalle.setValorPorcentajeDescuentoTotalLinea(0);
    	}
    	factura.setValorPorcentajeDescuentoTotal(0);
    }
    
    /*
     * CALCULOS CON FACTURA DETALLES
     */
    private void calcularSubtotalSinDescuentoLinea(Factura factura) {
    	for(FacturaDetalle facturaDetalle: factura.getFacturaDetalles()) {
    		double subtotalSinDescuentoLinea=facturaDetalle.getCantidad()*facturaDetalle.getPrecio().getPrecioVentaPublicoManual();
        	subtotalSinDescuentoLinea=Math.round(subtotalSinDescuentoLinea*100.0)/100.0;
        	facturaDetalle.setSubtotalSinDescuentoLinea(subtotalSinDescuentoLinea);
    	}
    }
    
    private void calcularValorPorcentajeDescuentoLinea(Factura factura) {
    	for(FacturaDetalle facturaDetalle: factura.getFacturaDetalles()) {
    		double valorPorcentajeDescuentoLinea=(facturaDetalle.getSubtotalSinDescuentoLinea()*facturaDetalle.getPorcentajeDescuentoLinea())/100;
        	valorPorcentajeDescuentoLinea= Math.round(valorPorcentajeDescuentoLinea*100.0)/100.0;
            facturaDetalle.setValorPorcentajeDescuentoLinea(valorPorcentajeDescuentoLinea);
    	}
    	
    }
    
    private void calcularTotalDescuentoLinea(Factura factura) {
    	for(FacturaDetalle facturaDetalle: factura.getFacturaDetalles()) {
    		double totalDescuentoLinea=facturaDetalle.getValorDescuentoTotalLinea()+facturaDetalle.getValorPorcentajeDescuentoLinea()+facturaDetalle.getValorDescuentoLinea()+facturaDetalle.getValorPorcentajeDescuentoTotalLinea();  
        	totalDescuentoLinea= Math.round(totalDescuentoLinea*100.0)/100.0;
            facturaDetalle.setTotalDescuentoLinea(totalDescuentoLinea);
    	}
    	
    }
    
    private void calcularIvaSinDescuentoLinea(Factura factura) {
    	for(FacturaDetalle facturaDetalle: factura.getFacturaDetalles()) {
    		double ivaSinDescuentoLinea=facturaDetalle.getSubtotalSinDescuentoLinea()*facturaDetalle.getImpuesto().getPorcentaje()/100;
            ivaSinDescuentoLinea= Math.round(ivaSinDescuentoLinea*100.0)/100.0;
            facturaDetalle.setIvaSinDescuentoLinea(ivaSinDescuentoLinea);
    	}
        
    }
    
    private void calcularSubtotalConDescuentoLinea(Factura factura) {
    	for(FacturaDetalle facturaDetalle: factura.getFacturaDetalles()) {
    		double subtotalConDescuentoLinea=facturaDetalle.getSubtotalSinDescuentoLinea()-facturaDetalle.getTotalDescuentoLinea();
        	subtotalConDescuentoLinea= Math.round(subtotalConDescuentoLinea*100.0)/100.0;
            facturaDetalle.setSubtotalConDescuentoLinea(subtotalConDescuentoLinea);
    	}
    	
    }
    
    private void calcularIvaConDescuentoLinea(Factura factura) {
    	for(FacturaDetalle facturaDetalle: factura.getFacturaDetalles()) {
    		double ivaConDescuentoLinea=facturaDetalle.getSubtotalConDescuentoLinea()*facturaDetalle.getImpuesto().getPorcentaje()/100;
        	ivaConDescuentoLinea = Math.round(ivaConDescuentoLinea*100.0)/100.0;
            facturaDetalle.setIvaConDescuentoLinea(ivaConDescuentoLinea);
    	}
    	
    }
    
    private void calcularTotalConDescuentoLinea(Factura factura) {
    	for(FacturaDetalle facturaDetalle: factura.getFacturaDetalles()) {
    		double totalConDescuentoLinea=facturaDetalle.getSubtotalConDescuentoLinea()+facturaDetalle.getIvaConDescuentoLinea();
        	totalConDescuentoLinea = Math.round(totalConDescuentoLinea*100.0)/100.0;
            facturaDetalle.setTotalConDescuentoLinea(totalConDescuentoLinea);
    	}
    	
    }
    /*
     * FIN CALCULO FACTURA DETALLES
     */
    
    /*
     * CALCULAR DESCUENTOS GENERALES
     */
    private void calcularValorDescuentoLineaPorDescuentosGenerales(Factura factura) {
    	for(FacturaDetalle facturaDetalle: factura.getFacturaDetalles()) {
    		double valorDescuentoLinea=factura.getValorDescuentoSubtotal()*facturaDetalle.getSubtotalSinDescuentoLinea()/factura.getSubtotalSinDescuento();
    		valorDescuentoLinea = Math.round(valorDescuentoLinea*100.0)/100.0;
    		facturaDetalle.setValorDescuentoLinea(valorDescuentoLinea);
    	}
    }
    private void calcularPorcentajeDescuentoLineaPorDescuentosGenerales(Factura factura) {
    	for(FacturaDetalle facturaDetalle: factura.getFacturaDetalles()) {
    		double porcentajeDescuentoLinea=factura.getPorcentajeDescuentoSubtotal();
    		facturaDetalle.setPorcentajeDescuentoLinea(porcentajeDescuentoLinea);
    	}
    }
    
    private void calcularValorPorcentajeDescuentoLineaPorDescuentosGenerales(Factura factura){
    	for(FacturaDetalle facturaDetalle: factura.getFacturaDetalles()) {
    		double valorPorcentajeDescuentoLinea=(facturaDetalle.getSubtotalSinDescuentoLinea()*facturaDetalle.getPorcentajeDescuentoLinea())/100;
        	valorPorcentajeDescuentoLinea= Math.round(valorPorcentajeDescuentoLinea*100.0)/100.0;
            facturaDetalle.setValorPorcentajeDescuentoLinea(valorPorcentajeDescuentoLinea);
    	}
    }
    
    private void calcularValorDescuentoTotalLineaPorDescuentosGenerales(Factura factura) {
    	for(FacturaDetalle facturaDetalle: factura.getFacturaDetalles()) {
    		double valorDescuentoTotalLinea=0;
        	if(facturaDetalle.getImpuesto().getPorcentaje()>0) {
        		valorDescuentoTotalLinea=((factura.getValorDescuentoTotal()*facturaDetalle.getSubtotalSinDescuentoLinea())/factura.getSubtotalSinDescuento()/((100+facturaDetalle.getImpuesto().getPorcentaje())/100));
        	} else {
        		valorDescuentoTotalLinea=((factura.getValorDescuentoTotal()*facturaDetalle.getSubtotalSinDescuentoLinea())/factura.getSubtotalSinDescuento());
        	}
        	valorDescuentoTotalLinea= Math.round(valorDescuentoTotalLinea*100.0)/100.0;
        	facturaDetalle.setValorDescuentoTotalLinea(valorDescuentoTotalLinea);
    	}
    }
    
    private void calcularValorPorcentajeDescuentoTotal(Factura factura){
    	double valorPorcentajeDescuentoTotal=factura.getTotalConDescuento()*(factura.getPorcentajeDescuentoTotal()/100);
    	valorPorcentajeDescuentoTotal= Math.round(valorPorcentajeDescuentoTotal*100.0)/100.0;
    	factura.setValorPorcentajeDescuentoTotal(valorPorcentajeDescuentoTotal);
    }
    
    private void calcularValorPorcentajeDescuentoTotalLineaPorDescuentosGenerales(Factura factura) {
    	for(FacturaDetalle facturaDetalle: factura.getFacturaDetalles()) {
    		double valorPorcentajeDescuentoTotalLinea=0;
        	if(facturaDetalle.getImpuesto().getPorcentaje()>0) {
        		valorPorcentajeDescuentoTotalLinea=((factura.getValorPorcentajeDescuentoTotal()*facturaDetalle.getSubtotalSinDescuentoLinea())/factura.getSubtotalSinDescuento()/((100+facturaDetalle.getImpuesto().getPorcentaje())/100));
        	} else {
        		valorPorcentajeDescuentoTotalLinea=((factura.getValorPorcentajeDescuentoTotal()*facturaDetalle.getSubtotalSinDescuentoLinea())/factura.getSubtotalSinDescuento());
        	}
        	valorPorcentajeDescuentoTotalLinea= Math.round(valorPorcentajeDescuentoTotalLinea*100.0)/100.0;
        	facturaDetalle.setValorPorcentajeDescuentoTotalLinea(valorPorcentajeDescuentoTotalLinea);
    	}
    }
    
    private void calcularPorcentajeDescuentoTotalLineaPorDescuentosGenerales(Factura factura) {
    	for(FacturaDetalle facturaDetalle: factura.getFacturaDetalles()) {
    		double porcentajeDescuentoTotalLinea=(facturaDetalle.getValorPorcentajeDescuentoTotalLinea()/facturaDetalle.getSubtotalSinDescuentoLinea())*100;
    		porcentajeDescuentoTotalLinea= Math.round(porcentajeDescuentoTotalLinea*100.0)/100.0;
        	facturaDetalle.setPorcentajeDescuentoTotalLinea(porcentajeDescuentoTotalLinea);
    	}
    	
    }
    /*
     * FIN CALCULAR DESCUENTOS GENERALES
     */
    
    /*
     * CALCULOS CON FACTURA
     */
    private void calcularSubtotalSinDescuento(Factura factura) {
    	double subtotalSinDescuento=0;
        for(FacturaDetalle facturaDetalle: factura.getFacturaDetalles()){
          subtotalSinDescuento+=facturaDetalle.getSubtotalSinDescuentoLinea();
        }
        subtotalSinDescuento=Math.round(subtotalSinDescuento*100.0)/100.0;
        factura.setSubtotalSinDescuento(subtotalSinDescuento);
    }
    
    private void calcularSubtotalConDescuento(Factura factura){
        double subtotalConDescuento=0;
        for(FacturaDetalle facturaDetalle: factura.getFacturaDetalles()){
          subtotalConDescuento+=facturaDetalle.getSubtotalConDescuentoLinea();
        }
        subtotalConDescuento=Math.round(subtotalConDescuento*100.0)/100.0;
        factura.setSubtotalConDescuento(subtotalConDescuento);
    }
    
    private void calcularDescuentoTotal(Factura factura){
	    double descuentoTotal=0;
	    for(FacturaDetalle facturaDetalle: factura.getFacturaDetalles()){
	      descuentoTotal= descuentoTotal+facturaDetalle.getTotalDescuentoLinea();
	    }
	    descuentoTotal= Math.round(descuentoTotal*100.0)/100.0;
	    factura.setDescuentoTotal(descuentoTotal);
    }
    
    private void calcularSubtotalBase12SinDescuento(Factura factura) {
    	double subtotalBase12SinDescuento=0;
    	for(FacturaDetalle facturaDetalle: factura.getFacturaDetalles()){
          if (facturaDetalle.getImpuesto().getPorcentaje()==12){
            subtotalBase12SinDescuento+=facturaDetalle.getSubtotalSinDescuentoLinea();
          }
    	}
        subtotalBase12SinDescuento= Math.round(subtotalBase12SinDescuento*100.0)/100.0;
        factura.setSubtotalBase12SinDescuento(subtotalBase12SinDescuento);
    }
    
    private void calcularSubtotalBase0SinDescuento(Factura factura) {
    	double subtotalBase0SinDescuento=0;
    	for(FacturaDetalle facturaDetalle: factura.getFacturaDetalles()){
          if (facturaDetalle.getImpuesto().getPorcentaje()==0){
            subtotalBase0SinDescuento+=facturaDetalle.getSubtotalSinDescuentoLinea();
          }
        }
        subtotalBase0SinDescuento=Math.round(subtotalBase0SinDescuento*100.0)/100.0;
        factura.setSubtotalBase0SinDescuento(subtotalBase0SinDescuento);
    }
    
    private void calcularSubtotalBase12ConDescuento(Factura factura) {
    	double subtotalBase12ConDescuento=0;
    	for(FacturaDetalle facturaDetalle: factura.getFacturaDetalles()){
          if (facturaDetalle.getImpuesto().getPorcentaje()==12){
            subtotalBase12ConDescuento+=facturaDetalle.getSubtotalConDescuentoLinea();
          }
        }
        subtotalBase12ConDescuento= Math.round(subtotalBase12ConDescuento*100.0)/100.0;
        factura.setSubtotalBase12ConDescuento(subtotalBase12ConDescuento);
    }
    
    
    private void calcularSubtotalBase0ConDescuento(Factura factura) {
    	double subtotalBase0ConDescuento=0;
    	for(FacturaDetalle facturaDetalle: factura.getFacturaDetalles()){
          if (facturaDetalle.getImpuesto().getPorcentaje()==0){
            subtotalBase0ConDescuento+=facturaDetalle.getSubtotalConDescuentoLinea();
          }
        }
        subtotalBase0ConDescuento=Math.round(subtotalBase0ConDescuento*100.0)/100.0;
        factura.setSubtotalBase0ConDescuento(subtotalBase0ConDescuento);
    }
    
    private void calcularIvaSinDescuento(Factura factura) {
        double ivaSinDescuento=factura.getSubtotalBase12SinDescuento()*12/100;
        ivaSinDescuento=Math.round(ivaSinDescuento*100.0)/100.0;
        factura.setIvaSinDescuento(ivaSinDescuento);
    }
    
    private void calcularIvaConDescuento(Factura factura) {
        double ivaConDescuento=factura.getSubtotalBase12ConDescuento()*12/100;
        ivaConDescuento= Math.round(ivaConDescuento*100.0)/100.0;
        factura.setIvaConDescuento(ivaConDescuento);
    }
    
    private void calcularTotalSinDescuento(Factura factura) {
        double totalSinDescuento=factura.getSubtotalBase0SinDescuento()+factura.getSubtotalBase12SinDescuento()+factura.getIvaSinDescuento();
        totalSinDescuento= Math.round(totalSinDescuento*100.0)/100.0;
        factura.setTotalSinDescuento(totalSinDescuento);
    }
    
    private void calcularTotalConDescuento(Factura factura) {
        double totalConDescuento=factura.getSubtotalBase0ConDescuento()+factura.getSubtotalBase12ConDescuento()+factura.getIvaConDescuento();
        totalConDescuento=Math.round(totalConDescuento*100.0)/100.0;
        factura.setTotalConDescuento(totalConDescuento);
    }
}
