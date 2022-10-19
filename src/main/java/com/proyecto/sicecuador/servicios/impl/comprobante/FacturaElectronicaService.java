package com.proyecto.sicecuador.servicios.impl.comprobante;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.modelos.comprobante.facturacionelectronica.factura.Detalle;
import com.proyecto.sicecuador.modelos.comprobante.facturacionelectronica.factura.Detalles;
import com.proyecto.sicecuador.modelos.comprobante.facturacionelectronica.factura.FacturaElectronica;
import com.proyecto.sicecuador.modelos.comprobante.facturacionelectronica.factura.InfoFactura;
import com.proyecto.sicecuador.modelos.comprobante.facturacionelectronica.factura.InfoTributaria;
import com.proyecto.sicecuador.modelos.comprobante.facturacionelectronica.factura.Pago;
import com.proyecto.sicecuador.modelos.comprobante.facturacionelectronica.factura.Pagos;
import com.proyecto.sicecuador.modelos.comprobante.facturacionelectronica.factura.TotalConImpuestos;
import com.proyecto.sicecuador.modelos.comprobante.facturacionelectronica.factura.TotalImpuesto;
import com.proyecto.sicecuador.modelos.recaudacion.Cheque;
import com.proyecto.sicecuador.modelos.recaudacion.Compensacion;
import com.proyecto.sicecuador.modelos.recaudacion.Deposito;
import com.proyecto.sicecuador.modelos.recaudacion.Recaudacion;
import com.proyecto.sicecuador.modelos.recaudacion.RetencionVenta;
import com.proyecto.sicecuador.modelos.recaudacion.TarjetaCredito;
import com.proyecto.sicecuador.modelos.recaudacion.TarjetaDebito;
import com.proyecto.sicecuador.modelos.recaudacion.Transferencia;
import com.proyecto.sicecuador.repositorios.recaudacion.IRecaudacionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class FacturaElectronicaService {
    @Autowired
    private IRecaudacionRepository rep;

    
    public FacturaElectronica crear(long facturaId) {
    	Optional<Recaudacion> recaudacion= rep.obtenerPorFactura(facturaId);
    	if(recaudacion.isEmpty()) {
    		throw new EntidadNoExistenteException(Constantes.recaudacion);
    	}
    	Factura factura = recaudacion.get().getFactura();
    	//MAPEO A FACTURA ELECTRONICA
    	FacturaElectronica facturaE=new FacturaElectronica();
    	InfoTributaria infoTributaria = new InfoTributaria();
    	InfoFactura infoFactura = new InfoFactura();	 	
    	  	
    	infoTributaria.setAmbiente(Constantes.ambienteFE);
    	infoTributaria.setTipoEmision(Constantes.tipoEmisionFE);
    	infoTributaria.setRazonSocial(factura.getSesion().getUsuario().getEmpresa().getRazonSocial());
    	infoTributaria.setNombreComercial(factura.getSesion().getUsuario().getEmpresa().getNombreComercial());
    	infoTributaria.setRuc(factura.getSesion().getUsuario().getEmpresa().getIdentificacion());
    	infoTributaria.setClaveAcceso(factura.getClaveAccesoSri());
    	infoTributaria.setCodDoc(factura.getSesion().getUsuario().getEmpresa().getTipoIdentificacion().getCodigoSri());
    	infoTributaria.setEstab(factura.getSesion().getPuntoVenta().getEstablecimiento().getCodigoSri());
    	infoTributaria.setPtoEmi(factura.getSesion().getPuntoVenta().getCodigoSri());
    	infoTributaria.setSecuencial(factura.getSecuencia());
    	infoTributaria.setDirMatriz(factura.getSesion().getUsuario().getEmpresa().getDireccion().getDireccion());
    	
    	infoFactura.setFechaEmision(factura.getFecha());
    	infoFactura.setObligadoContabilidad(factura.getCliente().getTipoContribuyente().isObligadoContabilidad()? "SI":"NO");
    	infoFactura.setTipoIdentificacionComprador(factura.getCliente().getTipoIdentificacion().getCodigoSri());
    	infoFactura.setRazonSocialComprador(factura.getCliente().getRazonSocial());
    	infoFactura.setIdentificacionComprador(factura.getCliente().getIdentificacion());
    	infoFactura.setDireccionComprador(factura.getCliente().getDireccion().getDireccion());
    	infoFactura.setTotalSinImpuestos(factura.getSubtotalSinDescuento());
    	infoFactura.setTotalDescuento(factura.getDescuentoTotal());
    	infoFactura.setTotalConImpuestos(crearTotalConImpuestos(factura));
    	infoFactura.setPropina(Constantes.cero);
    	infoFactura.setImporteTotal(factura.getTotalConDescuento());
    	infoFactura.setMoneda(factura.getMoneda());
    	infoFactura.setPagos(crearPagos(recaudacion.get()));
    	
    	Detalles detalles=crearDetalles(factura);
    	
    	facturaE.setInfoTributaria(infoTributaria);
    	facturaE.setInfoFactura(infoFactura);
    	facturaE.setDetalles(detalles);
   	
    	return facturaE;
    }

    private TotalConImpuestos crearTotalConImpuestos(Factura factura){
    	TotalConImpuestos totalConImpuestos = new TotalConImpuestos();
    	List<TotalImpuesto> totalImpuestos = new ArrayList<>();
    	for(int i=0; i<factura.getFacturaDetalles().size(); i++) {
        	TotalImpuesto totalImpuesto = new TotalImpuesto();
    		totalImpuesto.setCodigo(Constantes.iva);
        	totalImpuesto.setCodigoPorcentaje(factura.getFacturaDetalles().get(i).getImpuesto().getCodigoImpuestoSri());
        	totalImpuesto.setDescuentoAdicional(factura.getFacturaDetalles().get(i).getTotalDescuentoLinea());
        	totalImpuesto.setBaseImponible(factura.getFacturaDetalles().get(i).getSubtotalConDescuentoLinea());
        	totalImpuesto.setValor(factura.getFacturaDetalles().get(i).getIvaConDescuentoLinea());
        	totalImpuestos.add(totalImpuesto);
    	}
    	totalConImpuestos.setTotalImpuesto(totalImpuestos);
    	return totalConImpuestos;
    }
    
    private Pagos crearPagos(Recaudacion recaudacion) {
    	Pagos pagos = new Pagos();
    	List<Pago> pagosLista = new ArrayList<>();
    	
    	if(recaudacion.getEfectivo()>0) {
    		Pago pago = new Pago();
        	pago.setFormaPago(recaudacion.getEfectivoCodigoSri());
        	pago.setTotal(recaudacion.getEfectivo());
        	pagosLista.add(pago);
    	}
    	
        for(Cheque cheque: recaudacion.getCheques()) {
        	Pago pago = new Pago();
        	pago.setFormaPago(recaudacion.getChequeCodigoSri());
        	pago.setTotal(cheque.getValor());
        	pagosLista.add(pago);
        }
        
        for(Deposito deposito: recaudacion.getDepositos()) {
        	Pago pago = new Pago();
        	pago.setFormaPago(recaudacion.getDepositoCodigoSri());
        	pago.setTotal(deposito.getValor());
        	pagosLista.add(pago);
        }
        
        for(Transferencia transferencia: recaudacion.getTransferencias()) {
        	Pago pago = new Pago();
        	pago.setFormaPago(recaudacion.getTransferenciaCodigoSri());
        	pago.setTotal(transferencia.getValor());
        	pagosLista.add(pago);
        }
       
        for(TarjetaDebito tarjetaDebito: recaudacion.getTarjetasDebitos()) {
        	Pago pago = new Pago();
        	pago.setFormaPago(recaudacion.getTarjetaDebitoCodigoSri());
        	pago.setTotal(tarjetaDebito.getValor());
        	pagosLista.add(pago);
        }
        
        for(TarjetaCredito tarjetaCredito: recaudacion.getTarjetasCreditos()) {
        	Pago pago = new Pago();
        	pago.setFormaPago(recaudacion.getTarjetaCreditoCodigoSri());
        	pago.setTotal(tarjetaCredito.getValor());
        	pagosLista.add(pago);
        }
        
        for(Compensacion compensacion: recaudacion.getCompensaciones()) {
        	Pago pago = new Pago();
        	pago.setFormaPago(recaudacion.getCompensacionCodigoSri());
        	pago.setTotal(compensacion.getValorCompensado());
        	pagosLista.add(pago);
        }
        
        for(RetencionVenta retencionVenta: recaudacion.getRetencionesVentas()) {
        	Pago pago = new Pago();
        	pago.setFormaPago(recaudacion.getRetencionVentaCodigoSri());
        	pago.setTotal(retencionVenta.getValor());
        	pagosLista.add(pago);
        }
        if(recaudacion.getCredito()!= null) {
        	Pago pago = new Pago();
        	pago.setFormaPago(recaudacion.getCreditoCodigoSri());
        	pago.setTotal(recaudacion.getCredito().getSaldo());
        	pago.setUnidadTiempo(recaudacion.getCredito().getPeriodicidad());
        	pago.setPlazo(recaudacion.getCredito().getPeriodicidadNumero());
        	pagosLista.add(pago);
        }
    	pagos.setPago(pagosLista);
    	return pagos;
    }
    
    private Detalles crearDetalles(Factura factura) {
    	Detalles detalles=new Detalles();
    	List<Detalle> detalleLista = new ArrayList<>();
    	for(int i=0; i<factura.getFacturaDetalles().size(); i++) {
    		Detalle detalle = new Detalle();
    		detalle.setCodigoPrincipal(factura.getFacturaDetalles().get(i).getProducto().getCodigo());
    		detalle.setDescripcion(factura.getFacturaDetalles().get(i).getProducto().getNombre());
    		detalle.setCantidad(factura.getFacturaDetalles().get(i).getCantidad());
    		detalle.setPrecioUnitario(factura.getFacturaDetalles().get(i).getPrecio().getPrecioVentaPublico());
    		detalle.setDescuento(factura.getFacturaDetalles().get(i).getTotalDescuentoLinea());
    		detalle.setPrecioTotalSinImpuesto(factura.getFacturaDetalles().get(i).getSubtotalConDescuentoLinea());
    		detalleLista.add(detalle);
    	}
    	detalles.setDetalle(detalleLista);
    	return detalles;
    }

    
    public void enviar(FacturaElectronica facturaE) {
    	try {
            JAXBContext jaxbContext = JAXBContext.newInstance(FacturaElectronica.class);            
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI);
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
            jaxbMarshaller.marshal(facturaE, System.out);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(facturaE, sw);
            String body=sw.toString();
            HttpClient httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(BodyPublishers.ofString(body))
                    .uri(URI.create("https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline"))
                    .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            // print response headers
            HttpHeaders headers = response.headers();
            headers.map().forEach((k, v) -> System.out.println(k + ":" + v));
            // print status code
            System.out.println(response.statusCode());
            // print response body
            System.out.println(response.body());
        } catch (JAXBException ex) {
            System.err.println(ex.getMessage());                        
        } catch (IOException ex) {
			// TODO Auto-generated catch block
        	System.err.println(ex.getMessage());   
		} catch (InterruptedException ex) {
			// TODO Auto-generated catch block
			System.err.println(ex.getMessage());   
		}
    }
}
