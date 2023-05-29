package com.proyecto.sicecuador.servicios.impl.venta;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.*;
import com.proyecto.sicecuador.modelos.configuracion.Secuencial;
import com.proyecto.sicecuador.modelos.inventario.TipoOperacion;
import com.proyecto.sicecuador.modelos.venta.Factura;
import com.proyecto.sicecuador.modelos.venta.FacturaLinea;
import com.proyecto.sicecuador.modelos.configuracion.TipoComprobante;
import com.proyecto.sicecuador.modelos.inventario.Kardex;
import com.proyecto.sicecuador.modelos.recaudacion.*;
import com.proyecto.sicecuador.repositorios.venta.IFacturaRepository;
import com.proyecto.sicecuador.servicios.interf.configuracion.ISecuencialService;
import com.proyecto.sicecuador.servicios.interf.venta.IFacturaService;
import com.proyecto.sicecuador.servicios.interf.configuracion.ITipoComprobanteService;
import com.proyecto.sicecuador.servicios.interf.inventario.IKardexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.persistence.criteria.Predicate;
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
    private ISecuencialService secuencialService;

    @Override
    public void validar(Factura factura) {
        if(factura.getEstado().equals(Constantes.estadoRecaudada)) throw new DatoInvalidoException(Constantes.estado);
        if(factura.getEstado().equals(Constantes.estadoFacturada)) throw new DatoInvalidoException(Constantes.estado);
        if(factura.getFecha() == null) throw new DatoInvalidoException(Constantes.fecha);
        if(factura.getCliente().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.cliente);
        if(factura.getSesion().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.sesion);
        if(factura.getFacturaLineas().isEmpty()) throw new DatoInvalidoException(Constantes.factura_detalle);

        for(FacturaLinea facturaLinea: factura.getFacturaLineas()){
            validarLinea(facturaLinea);
        }

        for(Cheque cheque : factura.getCheques()){
            if(cheque.getNumero().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.numero);
            if(cheque.getTipo().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.dato_tipo);
            if(cheque.getValor() <= 0) throw new DatoInvalidoException(Constantes.valor);
            if(cheque.getBanco().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.banco);
        }
        for(Deposito deposito : factura.getDepositos()){
            if(deposito.getComprobante().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.comprobante);
            if(deposito.getValor() <= 0) throw new DatoInvalidoException(Constantes.valor);
            if(deposito.getCuentaPropia().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.cuenta_propia);
        }
        for(Transferencia transferencia : factura.getTransferencias()){
            if(transferencia.getTipo().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.tipo_transaccion);
            if(transferencia.getComprobante().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.numero_transaccion);
            if(transferencia.getValor() <= 0) throw new DatoInvalidoException(Constantes.valor);
            if(transferencia.getCuentaPropia().getBanco().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.banco);
        }
        for(TarjetaDebito tarjetaDebito : factura.getTarjetasDebitos()){
            if(tarjetaDebito.getIdentificacion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.identificacion);
            if(tarjetaDebito.getNombre().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.nombre_titular);
            if(tarjetaDebito.getLote().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.lote);
            if(tarjetaDebito.getValor() <= 0) throw new DatoInvalidoException(Constantes.valor);
            if(tarjetaDebito.getBanco().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.banco);
            if(tarjetaDebito.getOperadorTarjeta().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.operador_tarjeta);
            if(tarjetaDebito.getFranquiciaTarjeta().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.franquicia_tarjeta);
        }
        for(TarjetaCredito tarjetaCredito : factura.getTarjetasCreditos()){
            if(tarjetaCredito.getDiferido().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.diferido);
            if(tarjetaCredito.getTitular().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.titular);
            if(tarjetaCredito.getIdentificacion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.identificacion);
            if(tarjetaCredito.getNombre().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.nombre_titular);
            if(tarjetaCredito.getLote().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.lote);
            if(tarjetaCredito.getValor() <= 0) throw new DatoInvalidoException(Constantes.valor);
            if(tarjetaCredito.getBanco().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.banco);
            if(tarjetaCredito.getOperadorTarjeta().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.operador_tarjeta);
            if(tarjetaCredito.getFranquiciaTarjeta().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.franquicia_tarjeta);
        }
    }

    private void crearKardex(Factura factura) {
        if(factura.getEstado().equals(Constantes.estadoFacturada)) throw new DatoInvalidoException(Constantes.estado);
        if(factura.getEstado().equals(Constantes.estadoAnulada)) throw new DatoInvalidoException(Constantes.estado);
        kardexService.eliminar(2, 2, factura.getSecuencial());
        for(FacturaLinea facturaLinea : factura.getFacturaLineas()){
            if(facturaLinea.getProducto().getCategoriaProducto().getDescripcion().equals(Constantes.bien)) {
                Kardex ultimoKardex = kardexService.obtenerUltimoPorBodega(facturaLinea.getBodega().getId(), facturaLinea.getProducto().getId());
                if (ultimoKardex == null) {
                    throw new DatoInvalidoException(Constantes.kardex);
                }
                if (ultimoKardex.getSaldo() < facturaLinea.getCantidad()) {
                    throw new DatoInvalidoException(Constantes.kardex);
                }
                double saldo = ultimoKardex.getSaldo() - facturaLinea.getCantidad();
                double costoTotal = ultimoKardex.getCostoTotal() - (facturaLinea.getCantidad() * ultimoKardex.getCostoPromedio());
                costoTotal = Math.round(costoTotal * 100.0) / 100.0;
                Kardex kardex = new Kardex(null, new Date(),
                        factura.getSecuencial(), Constantes.cero, facturaLinea.getCantidad(), saldo,
                        Constantes.cero, ultimoKardex.getCostoPromedio(),
                        ultimoKardex.getCostoPromedio(), costoTotal,
                        new TipoComprobante(2), new TipoOperacion(2), facturaLinea.getBodega(), facturaLinea.getProducto());
                kardexService.crear(kardex);
            }
        }
    }

    private Optional<String> crearClaveAcceso(Factura factura) {
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String fechaEmision = dateFormat.format(factura.getFecha());
        String tipoComprobante = Constantes.factura_sri;
        String numeroRuc = factura.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getIdentificacion();
        String tipoAmbiente = Constantes.pruebas_sri;
        String serie = factura.getSerie();
        String numeroComprobante = factura.getSecuencial();
        String codigoNumerico = factura.getCodigoNumerico();
        String tipoEmision = Constantes.emision_normal_sri;
        String cadenaVerificacion = fechaEmision + tipoComprobante+numeroRuc + tipoAmbiente + serie + numeroComprobante + codigoNumerico + tipoEmision;
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
    public Factura crear(Factura factura) {
        validar(factura);
        TipoComprobante tipoComprobante = tipoComprobanteService.obtenerPorNombreTabla(Constantes.tabla_factura);
        factura.setTipoComprobante(tipoComprobante);
        Optional<String>codigo=Util.generarCodigo(Constantes.tabla_factura);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	factura.setCodigo(codigo.get());
    	factura.setSerie(factura.getSesion().getUsuario().getEstacion().getEstablecimiento().getCodigoSRI() + factura.getSesion().getUsuario().getEstacion().getCodigoSRI());
        Secuencial secuencial = secuencialService.obtenerPorTipoComprobanteYEstacion(factura.getTipoComprobante().getId(), factura.getSesion().getUsuario().getEstacion().getId());
    	factura.setSecuencial(Util.generarSecuencial(secuencial.getNumeroSiguiente()));
    	factura.setCodigoNumerico(Util.generarCodigoNumerico(secuencial.getNumeroSiguiente()));
    	Optional<String> claveAcceso = crearClaveAcceso(factura);
    	if (claveAcceso.isEmpty()) {
    		throw new ClaveAccesoNoExistenteException();
    	}
    	factura.setClaveAcceso(claveAcceso.get());
    	factura.setEstado(Constantes.estadoEmitida);
        calcular(factura);
        calcularRecaudacion(factura);
        crearKardex(factura);
        Factura res = rep.save(factura);
        res.normalizar();
        secuencial.setNumeroSiguiente(secuencial.getNumeroSiguiente()+1);
        secuencialService.actualizar(secuencial);
        return res;
    }

    @Override
    public Factura actualizar(Factura factura) {
        validar(factura);
        calcular(factura);
        calcularRecaudacion(factura);
        crearKardex(factura);
        Factura res = rep.save(factura);
        res.normalizar();
        return res;
    }

    @Override
    public Factura recaudar(Factura factura) {
        validar(factura);
        calcular(factura);
        calcularRecaudacion(factura);
        if(factura.getPorPagar() > Constantes.cero){
            factura.setEstado(Constantes.estadoNoRecaudada);
        } else{
            factura.setEstado(Constantes.estadoRecaudada);
        }
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
        return rep.consultar();
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
		    if (!factura.getSecuencial().equals(Constantes.vacio)) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("codigo"), "%"+factura.getSecuencial()+"%")));
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
    	for(FacturaLinea facturaLinea : factura.getFacturaLineas()) {
    		facturaLinea.setPorcentajeDescuentoTotalLinea(0);
    		facturaLinea.setValorPorcentajeDescuentoTotalLinea(0);
    	}
    	factura.setValorPorcentajeDescuentoTotal(0);
    }

    @Override
    public Factura calcularRecaudacion(Factura factura){
        double total = Constantes.cero;
        total = total + factura.getEfectivo();
        double totalCheques = Constantes.cero;
        for(Cheque cheque: factura.getCheques()) {
            totalCheques = totalCheques+cheque.getValor();
            total = total + totalCheques;
        }
        double totalDepositos = Constantes.cero;
        for(Deposito deposito: factura.getDepositos()) {
            totalDepositos = totalDepositos + deposito.getValor();
            total = total + totalDepositos;
        }
        double totalTransferencias = Constantes.cero;
        for(Transferencia transferencia: factura.getTransferencias()) {
            totalTransferencias = totalTransferencias+transferencia.getValor();
            total = total + totalTransferencias;
        }
        double totalTarjetasDebitos = Constantes.cero;
        for(TarjetaDebito tarjetaDebito: factura.getTarjetasDebitos()) {
            totalTarjetasDebitos = totalTarjetasDebitos + tarjetaDebito.getValor();
            total = total + totalTarjetasDebitos;
        }
        double totalTarjetasCreditos = Constantes.cero;
        for(TarjetaCredito tarjetaCredito: factura.getTarjetasCreditos()) {
            totalTarjetasCreditos = totalTarjetasCreditos+tarjetaCredito.getValor();
            total = total + totalTarjetasCreditos;
        }

        total = total + factura.getCredito().getSaldo();
        factura.setTotalCredito(factura.getCredito().getSaldo());
        factura.setTotalCheques(totalCheques);
        factura.setTotalDepositos(totalDepositos);
        factura.setTotalTransferencias(totalTransferencias);
        factura.setTotalTarjetasDebitos(totalTarjetasDebitos);
        factura.setTotalTarjetasCreditos(totalTarjetasCreditos);
        if(total >= factura.getTotalConDescuento()){
            double cambio = total - factura.getTotalConDescuento();
            cambio = Math.round(cambio*100.0)/100.0;
            factura.setCambio(cambio);
        } else {
            factura.setCambio(Constantes.cero);
        }
        if(total >= factura.getTotalConDescuento()){
            total = factura.getTotalConDescuento();
        }
        double porPagar = factura.getTotalConDescuento() - total;
        porPagar = Math.round(porPagar*100.0)/100.0;
        if(porPagar < 0) {
            porPagar = 0;
        }
        factura.setPorPagar(porPagar);
        factura.setTotalRecaudacion(total);
        return factura;
    }
    
    /*
     * CALCULOS CON FACTURA DETALLES
     */
    private void calcularSubtotalSinDescuentoLinea(Factura factura) {
    	for(FacturaLinea facturaLinea : factura.getFacturaLineas()) {
    		double subtotalSinDescuentoLinea= facturaLinea.getCantidad()* facturaLinea.getPrecioUnitario();
        	subtotalSinDescuentoLinea=Math.round(subtotalSinDescuentoLinea*100.0)/100.0;
        	facturaLinea.setSubtotalSinDescuentoLinea(subtotalSinDescuentoLinea);
    	}
    }
    
    private void calcularValorPorcentajeDescuentoLinea(Factura factura) {
    	for(FacturaLinea facturaLinea : factura.getFacturaLineas()) {
    		double valorPorcentajeDescuentoLinea=(facturaLinea.getSubtotalSinDescuentoLinea()* facturaLinea.getPorcentajeDescuentoLinea())/100;
        	valorPorcentajeDescuentoLinea= Math.round(valorPorcentajeDescuentoLinea*100.0)/100.0;
            facturaLinea.setValorPorcentajeDescuentoLinea(valorPorcentajeDescuentoLinea);
    	}
    	
    }
    
    private void calcularTotalDescuentoLinea(Factura factura) {
    	for(FacturaLinea facturaLinea : factura.getFacturaLineas()) {
    		double totalDescuentoLinea= facturaLinea.getValorDescuentoTotalLinea()+ facturaLinea.getValorPorcentajeDescuentoLinea()+ facturaLinea.getValorDescuentoLinea()+ facturaLinea.getValorPorcentajeDescuentoTotalLinea();
        	totalDescuentoLinea= Math.round(totalDescuentoLinea*100.0)/100.0;
            facturaLinea.setTotalDescuentoLinea(totalDescuentoLinea);
    	}
    	
    }
    
    private void calcularIvaSinDescuentoLinea(Factura factura) {
    	for(FacturaLinea facturaLinea : factura.getFacturaLineas()) {
    		double ivaSinDescuentoLinea= facturaLinea.getSubtotalSinDescuentoLinea()* facturaLinea.getImpuesto().getPorcentaje()/100;
            ivaSinDescuentoLinea= Math.round(ivaSinDescuentoLinea*100.0)/100.0;
            facturaLinea.setIvaSinDescuentoLinea(ivaSinDescuentoLinea);
    	}
        
    }
    
    private void calcularSubtotalConDescuentoLinea(Factura factura) {
    	for(FacturaLinea facturaLinea : factura.getFacturaLineas()) {
    		double subtotalConDescuentoLinea= facturaLinea.getSubtotalSinDescuentoLinea()- facturaLinea.getTotalDescuentoLinea();
        	subtotalConDescuentoLinea= Math.round(subtotalConDescuentoLinea*100.0)/100.0;
            facturaLinea.setSubtotalConDescuentoLinea(subtotalConDescuentoLinea);
    	}
    	
    }
    
    private void calcularIvaConDescuentoLinea(Factura factura) {
    	for(FacturaLinea facturaLinea : factura.getFacturaLineas()) {
    		double ivaConDescuentoLinea= facturaLinea.getSubtotalConDescuentoLinea()* facturaLinea.getImpuesto().getPorcentaje()/100;
        	ivaConDescuentoLinea = Math.round(ivaConDescuentoLinea*100.0)/100.0;
            facturaLinea.setIvaConDescuentoLinea(ivaConDescuentoLinea);
    	}
    	
    }
    
    private void calcularTotalConDescuentoLinea(Factura factura) {
    	for(FacturaLinea facturaLinea : factura.getFacturaLineas()) {
    		double totalConDescuentoLinea= facturaLinea.getSubtotalConDescuentoLinea()+ facturaLinea.getIvaConDescuentoLinea();
        	totalConDescuentoLinea = Math.round(totalConDescuentoLinea*100.0)/100.0;
            facturaLinea.setTotalConDescuentoLinea(totalConDescuentoLinea);
    	}
    	
    }
    /*
     * FIN CALCULO FACTURA DETALLES
     */
    
    /*
     * CALCULAR DESCUENTOS GENERALES
     */
    private void calcularValorDescuentoLineaPorDescuentosGenerales(Factura factura) {
    	for(FacturaLinea facturaLinea : factura.getFacturaLineas()) {
    		double valorDescuentoLinea=factura.getValorDescuentoSubtotal()* facturaLinea.getSubtotalSinDescuentoLinea()/factura.getSubtotalSinDescuento();
    		valorDescuentoLinea = Math.round(valorDescuentoLinea*100.0)/100.0;
    		facturaLinea.setValorDescuentoLinea(valorDescuentoLinea);
    	}
    }
    private void calcularPorcentajeDescuentoLineaPorDescuentosGenerales(Factura factura) {
    	for(FacturaLinea facturaLinea : factura.getFacturaLineas()) {
    		double porcentajeDescuentoLinea=factura.getPorcentajeDescuentoSubtotal();
    		facturaLinea.setPorcentajeDescuentoLinea(porcentajeDescuentoLinea);
    	}
    }
    
    private void calcularValorPorcentajeDescuentoLineaPorDescuentosGenerales(Factura factura){
    	for(FacturaLinea facturaLinea : factura.getFacturaLineas()) {
    		double valorPorcentajeDescuentoLinea=(facturaLinea.getSubtotalSinDescuentoLinea()* facturaLinea.getPorcentajeDescuentoLinea())/100;
        	valorPorcentajeDescuentoLinea= Math.round(valorPorcentajeDescuentoLinea*100.0)/100.0;
            facturaLinea.setValorPorcentajeDescuentoLinea(valorPorcentajeDescuentoLinea);
    	}
    }
    
    private void calcularValorDescuentoTotalLineaPorDescuentosGenerales(Factura factura) {
    	for(FacturaLinea facturaLinea : factura.getFacturaLineas()) {
    		double valorDescuentoTotalLinea=0;
        	if(facturaLinea.getImpuesto().getPorcentaje()>0) {
        		valorDescuentoTotalLinea=((factura.getValorDescuentoTotal()* facturaLinea.getSubtotalSinDescuentoLinea())/factura.getSubtotalSinDescuento()/((100+ facturaLinea.getImpuesto().getPorcentaje())/100));
        	} else {
        		valorDescuentoTotalLinea=((factura.getValorDescuentoTotal()* facturaLinea.getSubtotalSinDescuentoLinea())/factura.getSubtotalSinDescuento());
        	}
        	valorDescuentoTotalLinea= Math.round(valorDescuentoTotalLinea*100.0)/100.0;
        	facturaLinea.setValorDescuentoTotalLinea(valorDescuentoTotalLinea);
    	}
    }
    
    private void calcularValorPorcentajeDescuentoTotal(Factura factura){
    	double valorPorcentajeDescuentoTotal=factura.getTotalConDescuento()*(factura.getPorcentajeDescuentoTotal()/100);
    	valorPorcentajeDescuentoTotal= Math.round(valorPorcentajeDescuentoTotal*100.0)/100.0;
    	factura.setValorPorcentajeDescuentoTotal(valorPorcentajeDescuentoTotal);
    }
    
    private void calcularValorPorcentajeDescuentoTotalLineaPorDescuentosGenerales(Factura factura) {
    	for(FacturaLinea facturaLinea : factura.getFacturaLineas()) {
    		double valorPorcentajeDescuentoTotalLinea=0;
        	if(facturaLinea.getImpuesto().getPorcentaje()>0) {
        		valorPorcentajeDescuentoTotalLinea=((factura.getValorPorcentajeDescuentoTotal()* facturaLinea.getSubtotalSinDescuentoLinea())/factura.getSubtotalSinDescuento()/((100+ facturaLinea.getImpuesto().getPorcentaje())/100));
        	} else {
        		valorPorcentajeDescuentoTotalLinea=((factura.getValorPorcentajeDescuentoTotal()* facturaLinea.getSubtotalSinDescuentoLinea())/factura.getSubtotalSinDescuento());
        	}
        	valorPorcentajeDescuentoTotalLinea= Math.round(valorPorcentajeDescuentoTotalLinea*100.0)/100.0;
        	facturaLinea.setValorPorcentajeDescuentoTotalLinea(valorPorcentajeDescuentoTotalLinea);
    	}
    }
    
    private void calcularPorcentajeDescuentoTotalLineaPorDescuentosGenerales(Factura factura) {
    	for(FacturaLinea facturaLinea : factura.getFacturaLineas()) {
    		double porcentajeDescuentoTotalLinea=(facturaLinea.getValorPorcentajeDescuentoTotalLinea()/ facturaLinea.getSubtotalSinDescuentoLinea())*100;
    		porcentajeDescuentoTotalLinea= Math.round(porcentajeDescuentoTotalLinea*100.0)/100.0;
        	facturaLinea.setPorcentajeDescuentoTotalLinea(porcentajeDescuentoTotalLinea);
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
        for(FacturaLinea facturaLinea : factura.getFacturaLineas()){
          subtotalSinDescuento+= facturaLinea.getSubtotalSinDescuentoLinea();
        }
        subtotalSinDescuento=Math.round(subtotalSinDescuento*100.0)/100.0;
        factura.setSubtotalSinDescuento(subtotalSinDescuento);
    }
    
    private void calcularSubtotalConDescuento(Factura factura){
        double subtotalConDescuento=0;
        for(FacturaLinea facturaLinea : factura.getFacturaLineas()){
          subtotalConDescuento+= facturaLinea.getSubtotalConDescuentoLinea();
        }
        subtotalConDescuento=Math.round(subtotalConDescuento*100.0)/100.0;
        factura.setSubtotalConDescuento(subtotalConDescuento);
    }
    
    private void calcularDescuentoTotal(Factura factura){
	    double descuentoTotal=0;
	    for(FacturaLinea facturaLinea : factura.getFacturaLineas()){
	      descuentoTotal= descuentoTotal+ facturaLinea.getTotalDescuentoLinea();
	    }
	    descuentoTotal= Math.round(descuentoTotal*100.0)/100.0;
	    factura.setDescuentoTotal(descuentoTotal);
    }
    
    private void calcularSubtotalBase12SinDescuento(Factura factura) {
    	double subtotalBase12SinDescuento=0;
    	for(FacturaLinea facturaLinea : factura.getFacturaLineas()){
          if (facturaLinea.getImpuesto().getPorcentaje()!=0){
            subtotalBase12SinDescuento+= facturaLinea.getSubtotalSinDescuentoLinea();
          }
    	}
        subtotalBase12SinDescuento= Math.round(subtotalBase12SinDescuento*100.0)/100.0;
        factura.setSubtotalBase12SinDescuento(subtotalBase12SinDescuento);
    }
    
    private void calcularSubtotalBase0SinDescuento(Factura factura) {
    	double subtotalBase0SinDescuento=0;
    	for(FacturaLinea facturaLinea : factura.getFacturaLineas()){
          if (facturaLinea.getImpuesto().getPorcentaje()==0){
            subtotalBase0SinDescuento+= facturaLinea.getSubtotalSinDescuentoLinea();
          }
        }
        subtotalBase0SinDescuento=Math.round(subtotalBase0SinDescuento*100.0)/100.0;
        factura.setSubtotalBase0SinDescuento(subtotalBase0SinDescuento);
    }
    
    private void calcularSubtotalBase12ConDescuento(Factura factura) {
    	double subtotalBase12ConDescuento=0;
    	for(FacturaLinea facturaLinea : factura.getFacturaLineas()){
          if (facturaLinea.getImpuesto().getPorcentaje()!=0){
            subtotalBase12ConDescuento+= facturaLinea.getSubtotalConDescuentoLinea();
          }
        }
        subtotalBase12ConDescuento= Math.round(subtotalBase12ConDescuento*100.0)/100.0;
        factura.setSubtotalBase12ConDescuento(subtotalBase12ConDescuento);
    }
    
    
    private void calcularSubtotalBase0ConDescuento(Factura factura) {
    	double subtotalBase0ConDescuento=0;
    	for(FacturaLinea facturaLinea : factura.getFacturaLineas()){
          if (facturaLinea.getImpuesto().getPorcentaje()==0){
            subtotalBase0ConDescuento+= facturaLinea.getSubtotalConDescuentoLinea();
          }
        }
        subtotalBase0ConDescuento=Math.round(subtotalBase0ConDescuento*100.0)/100.0;
        factura.setSubtotalBase0ConDescuento(subtotalBase0ConDescuento);
    }
    
    private void calcularIvaSinDescuento(Factura factura) {
        double ivaSinDescuento=0;
        for(FacturaLinea facturaLinea : factura.getFacturaLineas()){
            if (facturaLinea.getImpuesto().getPorcentaje()!=0){
                ivaSinDescuento+= facturaLinea.getSubtotalSinDescuentoLinea()* facturaLinea.getImpuesto().getPorcentaje()/100;
            }
        }
        ivaSinDescuento=Math.round(ivaSinDescuento*100.0)/100.0;
        factura.setIvaSinDescuento(ivaSinDescuento);
    }
    
    private void calcularIvaConDescuento(Factura factura) {
        double ivaConDescuento=0;
        for(FacturaLinea facturaLinea : factura.getFacturaLineas()){
            if (facturaLinea.getImpuesto().getPorcentaje()!=0){
                ivaConDescuento+= facturaLinea.getSubtotalConDescuentoLinea()* facturaLinea.getImpuesto().getPorcentaje()/100;
            }
        }
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

    @Override
    public List<Factura> consultarPorCliente(long facturaId) {
        return rep.consultarPorCliente(facturaId, Constantes.estadoRecaudada, Constantes.estadoFacturada);
    }
    @Override
    public void validarLinea(FacturaLinea facturaLinea) {
        if(facturaLinea.getImpuesto().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.impuesto);
        if(facturaLinea.getBodega().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.bodega);
        if(facturaLinea.getProducto().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.producto);
        if(facturaLinea.getPrecio().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.precio);
        if(facturaLinea.getCantidad() < Constantes.cero) throw new DatoInvalidoException(Constantes.cantidad);
        if(facturaLinea.getPrecioUnitario() < Constantes.cero) throw new DatoInvalidoException(Constantes.precio);
        if(facturaLinea.getValorDescuentoLinea() < Constantes.cero) throw new DatoInvalidoException(Constantes.valorDescuentoLinea);
        if(facturaLinea.getValorDescuentoLinea() > (facturaLinea.getCantidad()*facturaLinea.getPrecioUnitario())) throw new DatoInvalidoException(Constantes.valorDescuentoLinea);
        if(facturaLinea.getPorcentajeDescuentoLinea() < Constantes.cero) throw new DatoInvalidoException(Constantes.porcentajeDescuentoLinea);
        if(facturaLinea.getPorcentajeDescuentoLinea() > 100) throw new DatoInvalidoException(Constantes.porcentajeDescuentoLinea);
    }
    @Override
    public FacturaLinea calcularLinea(FacturaLinea facturaLinea) {
        validarLinea(facturaLinea);
        this.calcularSubtotalSinDescuentoLinea(facturaLinea);
        this.calcularValorPorcentajeDescuentoLinea(facturaLinea);
        this.calcularTotalDescuentoLinea(facturaLinea);
        this.calcularIvaSinDescuentoLinea(facturaLinea);
        this.calcularSubtotalConDescuentoLinea(facturaLinea);
        this.calcularIvaConDescuentoLinea(facturaLinea);
        this.calcularTotalConDescuentoLinea(facturaLinea);
        return facturaLinea;
    }

    private void calcularSubtotalSinDescuentoLinea(FacturaLinea facturaLinea) {
        double subtotalSinDescuentoLinea= facturaLinea.getCantidad()* facturaLinea.getPrecioUnitario();
        subtotalSinDescuentoLinea=Math.round(subtotalSinDescuentoLinea*100.0)/100.0;
        facturaLinea.setSubtotalSinDescuentoLinea(subtotalSinDescuentoLinea);
    }

    private void calcularValorPorcentajeDescuentoLinea(FacturaLinea facturaLinea) {
        double valorPorcentajeDescuentoLinea=(facturaLinea.getSubtotalSinDescuentoLinea()* facturaLinea.getPorcentajeDescuentoLinea())/100;
        valorPorcentajeDescuentoLinea= Math.round(valorPorcentajeDescuentoLinea*100.0)/100.0;
        facturaLinea.setValorPorcentajeDescuentoLinea(valorPorcentajeDescuentoLinea);

    }

    private void calcularTotalDescuentoLinea(FacturaLinea facturaLinea) {
        double totalDescuentoLinea= facturaLinea.getValorDescuentoTotalLinea()+ facturaLinea.getValorPorcentajeDescuentoLinea()+ facturaLinea.getValorDescuentoLinea()+ facturaLinea.getValorPorcentajeDescuentoTotalLinea();
        totalDescuentoLinea= Math.round(totalDescuentoLinea*100.0)/100.0;
        facturaLinea.setTotalDescuentoLinea(totalDescuentoLinea);
    }

    private void calcularIvaSinDescuentoLinea(FacturaLinea facturaLinea) {
        double ivaSinDescuentoLinea= facturaLinea.getSubtotalSinDescuentoLinea()* facturaLinea.getImpuesto().getPorcentaje()/100;
        ivaSinDescuentoLinea= Math.round(ivaSinDescuentoLinea*100.0)/100.0;
        facturaLinea.setIvaSinDescuentoLinea(ivaSinDescuentoLinea);

    }

    private void calcularSubtotalConDescuentoLinea(FacturaLinea facturaLinea) {
        double subtotalConDescuentoLinea= facturaLinea.getSubtotalSinDescuentoLinea()- facturaLinea.getTotalDescuentoLinea();
        subtotalConDescuentoLinea= Math.round(subtotalConDescuentoLinea*100.0)/100.0;
        facturaLinea.setSubtotalConDescuentoLinea(subtotalConDescuentoLinea);
    }

    private void calcularIvaConDescuentoLinea(FacturaLinea facturaLinea) {
        double ivaConDescuentoLinea= facturaLinea.getSubtotalConDescuentoLinea()* facturaLinea.getImpuesto().getPorcentaje()/100;
        ivaConDescuentoLinea = Math.round(ivaConDescuentoLinea*100.0)/100.0;
        facturaLinea.setIvaConDescuentoLinea(ivaConDescuentoLinea);
    }

    private void calcularTotalConDescuentoLinea(FacturaLinea facturaLinea) {
        double totalConDescuentoLinea= facturaLinea.getSubtotalConDescuentoLinea()+ facturaLinea.getIvaConDescuentoLinea();
        totalConDescuentoLinea = Math.round(totalConDescuentoLinea*100.0)/100.0;
        facturaLinea.setTotalConDescuentoLinea(totalConDescuentoLinea);
    }
}
