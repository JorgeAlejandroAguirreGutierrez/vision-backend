package com.proyecto.sicecuador.servicios.impl.comprobante;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.modelos.comprobante.FacturaDetalle;
import com.proyecto.sicecuador.modelos.comprobante.facturacionelectronica.factura.Detalle;
import com.proyecto.sicecuador.modelos.comprobante.facturacionelectronica.factura.Detalles;
import com.proyecto.sicecuador.modelos.comprobante.facturacionelectronica.factura.FacturaElectronica;
import com.proyecto.sicecuador.modelos.comprobante.facturacionelectronica.factura.Impuesto;
import com.proyecto.sicecuador.modelos.comprobante.facturacionelectronica.factura.Impuestos;
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
import com.proyecto.sicecuador.repositorios.comprobante.IFacturaRepository;
import com.proyecto.sicecuador.repositorios.recaudacion.IRecaudacionRepository;
import com.proyecto.sicecuador.servicios.interf.comprobante.IFacturaElectronicaService;

import ayungan.com.signature.ConvertFile;
import ayungan.com.signature.SignatureXAdESBES;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.net.http.HttpResponse;
import java.security.cert.CertificateException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;


@Service
public class FacturaElectronicaService implements IFacturaElectronicaService{
    @Autowired
    private IRecaudacionRepository rep;
    
    @Autowired
    private IFacturaRepository repFactura;

    
    public Optional<String> crear(Factura _factura) {
    	Optional<Recaudacion> recaudacion= rep.obtenerPorFactura(_factura.getId());
    	if(recaudacion.isEmpty()) {
    		throw new EntidadNoExistenteException(Constantes.recaudacion);
    	}
    	Factura factura = recaudacion.get().getFactura();
    	if(factura.getEstado().equals(Constantes.estadoEmitida) || factura.getEstado().equals(Constantes.estadoNoFacturada)) {
    		//MAPEO A FACTURA ELECTRONICA
        	FacturaElectronica facturaElectronica=new FacturaElectronica();
        	InfoTributaria infoTributaria = new InfoTributaria();
        	InfoFactura infoFactura = new InfoFactura();	 	
        	  	
        	infoTributaria.setAmbiente(Constantes.pruebas_sri);
        	infoTributaria.setTipoEmision(Constantes.emision_normal_sri);
        	infoTributaria.setRazonSocial(factura.getSesion().getUsuario().getEmpresa().getRazonSocial());
        	infoTributaria.setNombreComercial(factura.getSesion().getUsuario().getEmpresa().getNombreComercial());
        	infoTributaria.setRuc(factura.getSesion().getUsuario().getEmpresa().getIdentificacion());
        	infoTributaria.setClaveAcceso(factura.getClaveAcceso());
        	infoTributaria.setCodDoc(Constantes.factura_sri);
        	infoTributaria.setEstab(factura.getSesion().getUsuario().getPuntoVenta().getEstablecimiento().getCodigoSri());
        	infoTributaria.setPtoEmi(factura.getSesion().getUsuario().getPuntoVenta().getCodigoSri());
        	infoTributaria.setSecuencial(factura.getSecuencia());
        	infoTributaria.setDirMatriz(factura.getSesion().getUsuario().getEmpresa().getDireccion().getDireccion());
        	
        	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
        	String fechaEmision = dateFormat.format(factura.getFecha());
        	infoFactura.setFechaEmision(fechaEmision);
        	infoFactura.setObligadoContabilidad(factura.getSesion().getUsuario().getEmpresa().isObligadoContabilidad()? Constantes.si : Constantes.no);
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
        	
        	facturaElectronica.setInfoTributaria(infoTributaria);
        	facturaElectronica.setInfoFactura(infoFactura);
        	facturaElectronica.setDetalles(detalles);
       	
        	String estado= enviar(facturaElectronica);
        	if(estado.equals(Constantes.recibidaSri)) {
        		factura.setEstado(Constantes.estadoFacturada);
        		repFactura.save(factura);
        	} else {
        		factura.setEstado(Constantes.estadoNoFacturada);
        		repFactura.save(factura);
        	}
        	String respuesta=Constantes.mensajeSri+estado+Constantes.mensajeClaveAccesoSri+factura.getClaveAcceso();
        	return Optional.of(respuesta);
    	} else {
    		String respuesta=Constantes.mensajeNoSri+factura.getClaveAcceso();
        	return Optional.of(respuesta);
    	}
    	
    }

    private TotalConImpuestos crearTotalConImpuestos(Factura factura){
    	TotalConImpuestos totalConImpuestos = new TotalConImpuestos();
    	List<TotalImpuesto> totalImpuestos = new ArrayList<>();
    	for(int i=0; i<factura.getFacturaDetalles().size(); i++) {
        	TotalImpuesto totalImpuesto = new TotalImpuesto();
    		totalImpuesto.setCodigo(Constantes.iva_sri);
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
    		detalle.setImpuestos(crearImpuestos(factura.getFacturaDetalles().get(i)));
    		detalleLista.add(detalle);
    	}
    	detalles.setDetalle(detalleLista);
    	return detalles;
    }
    
    private Impuestos crearImpuestos(FacturaDetalle facturaDetalle) {
    	Impuestos impuestos=new Impuestos();
    	List<Impuesto> impuestoLista = new ArrayList<>();
    	Impuesto impuesto=new Impuesto();
    	impuesto.setCodigo(Constantes.iva_sri);
    	impuesto.setCodigoPorcentaje(facturaDetalle.getImpuesto().getCodigoImpuestoSri());
    	impuesto.setTarifa(facturaDetalle.getImpuesto().getPorcentaje());
    	impuesto.setBaseImponible(facturaDetalle.getSubtotalConDescuentoLinea());
    	impuesto.setValor(facturaDetalle.getIvaConDescuentoLinea());
    	impuestoLista.add(impuesto);
    	impuestos.setImpuesto(impuestoLista);
    	return impuestos;
    }
    
    public String enviar(FacturaElectronica facturaElectronica) {
    	try {
    		JAXBContext jaxbContext = JAXBContext.newInstance(FacturaElectronica.class);            
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, Constantes.utf8);
            jaxbMarshaller.marshal(facturaElectronica, System.out);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(facturaElectronica, sw);
            String xml=sw.toString();
			byte[] cert = ConvertFile.readBytesFromFile(Constantes.certificadoSri);
            byte[] firmado=SignatureXAdESBES.firmarByteData(xml.getBytes(), cert, Constantes.contrasenaCertificadoSri);
            String encode=Base64.getEncoder().encodeToString(firmado);
            String body=Util.soapFacturacionEletronica(encode).get();
            System.out.println(body);
            HttpClient httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(BodyPublishers.ofString(body))
                    .uri(URI.create(Constantes.urlFacturacionEletronicaSri))
                    .setHeader(Constantes.contentType, Constantes.contenTypeValor)
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            // print response headers
            HttpHeaders headers = response.headers();
            headers.map().forEach((k, v) -> System.out.println(k + ":" + v));
            // print status code
            System.out.println(response.statusCode());
            // print response body
            System.out.println(response.body());
            JSONObject json=Util.convertirXmlJson(response.body());
            String estado=json.getJSONObject("soap:Envelope").getJSONObject("soap:Body").getJSONObject("ns2:validarComprobanteResponse").getJSONObject("RespuestaRecepcionComprobante").getString("estado");
            return estado;
        } catch (JAXBException ex) {
            System.err.println(ex.getMessage());                        
        } catch (IOException ex) {
			// TODO Auto-generated catch block
        	System.err.println(ex.getMessage());   
		} catch (InterruptedException ex) {
			// TODO Auto-generated catch block
			System.err.println(ex.getMessage());
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new EntidadNoExistenteException(Constantes.factura_electronica);
    }
}
