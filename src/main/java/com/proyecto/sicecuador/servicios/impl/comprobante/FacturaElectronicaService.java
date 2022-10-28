package com.proyecto.sicecuador.servicios.impl.comprobante;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
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
import java.util.Properties;


@Service
public class FacturaElectronicaService implements IFacturaElectronicaService{
    @Autowired
    private IRecaudacionRepository rep;
    
    @Autowired
    private IFacturaRepository repFactura;
    
    @Value("${prefijo.url.imagenes}")
    private String imagenes;
    
    @Value("${correo.usuario}")
    private String correoUsuario;
    
    @Value("${correo.contrasena}")
    private String correoContrasena;

    
    private FacturaElectronica crear(Recaudacion recaudacion, Factura factura) {    	
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
    	infoFactura.setObligadoContabilidad(factura.getSesion().getUsuario().getEmpresa().getObligadoContabilidad());
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
    	infoFactura.setPagos(crearPagos(recaudacion));
    	
    	Detalles detalles=crearDetalles(factura);
    	
    	facturaElectronica.setInfoTributaria(infoTributaria);
    	facturaElectronica.setInfoFactura(infoFactura);
    	facturaElectronica.setDetalles(detalles);
    	return facturaElectronica;
    }
    public Optional<String> enviarSri(Factura _factura) {
    	Optional<Recaudacion> opcional= rep.obtenerPorFactura(_factura.getId());
    	if(opcional.isEmpty()) {
    		throw new EntidadNoExistenteException(Constantes.recaudacion);
    	}
    	Factura factura = opcional.get().getFactura();
    	Recaudacion recaudacion= opcional.get();
    	if(factura.getEstado().equals(Constantes.estadoEmitida) || factura.getEstado().equals(Constantes.estadoNoFacturada)) {
        	FacturaElectronica facturaElectronica = crear(recaudacion, factura);
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
    
    private String enviar(FacturaElectronica facturaElectronica) {
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
    
    public ByteArrayInputStream crearPDF(Factura factura) {
    	try {
            ByteArrayOutputStream salida = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(salida);
            PdfDocument pdf = new PdfDocument(writer);
            // Initialize document
            Document documento = new Document(pdf, PageSize.A4);
            documento.setMargins(20, 20, 20, 20);
            // 4. Add content
            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            ImageData imageData = ImageDataFactory.create(imagenes+factura.getSesion().getUsuario().getPuntoVenta().getEstablecimiento().getEmpresa().getLogo());
            Image image = new Image(imageData);
            image.setWidth(200);
            image.setHeight(150);
            documento.add(image);
            documento.setFont(font);
            
            documento.add(new Paragraph(factura.getSesion().getUsuario().getPuntoVenta().getEstablecimiento().getEmpresa().getRazonSocial()+"\n"+
                    "DIRECCION MATRIZ: "+factura.getSesion().getUsuario().getPuntoVenta().getEstablecimiento().getDireccion()+"\n"+
            		"OBLIGADO A LLEVAR CONTABILIDAD: "+factura.getSesion().getUsuario().getPuntoVenta().getEstablecimiento().getEmpresa().getObligadoContabilidad()).setBorder(new SolidBorder(1)));
            
            documento.add( new Paragraph("\n"));
            
            documento.add(new Paragraph("RUC: "+factura.getSesion().getUsuario().getPuntoVenta().getEstablecimiento().getEmpresa().getIdentificacion()+"\n"+
                    "FACTURA"+"\n"+
                    "No. " + factura.getSecuencia() + "\n" +
                    "NÚMERO DE AUTORIZACIÓN: " + factura.getClaveAcceso()+ "\n" +
                    "FECHA: " + factura.getFecha().toString() + "\n" +
                    "AMBIENTE: " + Constantes.facturaFisicaAmbienteValor + "\n" +
                    "EMISIÓN: " + Constantes.facturaFisicaEmisionValor).setBorder(new SolidBorder(1)));
            
            documento.add( new Paragraph("\n"));
            
            String telefonoCliente="";
            String correoCliente="";
            if (!factura.getCliente().getTelefonos().isEmpty()){
                telefonoCliente=factura.getCliente().getTelefonos().get(0).getNumero();
            }
            if (!factura.getCliente().getCorreos().isEmpty()){
                correoCliente=factura.getCliente().getCorreos().get(0).getEmail();
            }
            documento.add( new Paragraph("RAZÓN SOCIAL: "+factura.getCliente().getRazonSocial()+"\n"+
                    "IDENTIFICACIÓN: " + factura.getCliente().getIdentificacion()+"\n"+
                    "FECHA EMISIÓN: " + factura.getFecha().toString()+"\n"+
                    "DIRECCIÓN: " + factura.getCliente().getDireccion().getDireccion() + "\n" + 
                    "TELÉFONO: " + telefonoCliente + "\n" +
                    "CORREO: " + correoCliente).setBorder(new SolidBorder(1)));
            documento.add( new Paragraph("\n"));
            float [] columnasTablaFacturaDetalle = {100F, 40F, 160F, 100F, 100F, 100F, 100F};
            Table tablaFacturaDetalle = new Table(columnasTablaFacturaDetalle);
            tablaFacturaDetalle.addCell("CÓDIGO");
            tablaFacturaDetalle.addCell("CANT");
            tablaFacturaDetalle.addCell("DESCRIPCION");
            tablaFacturaDetalle.addCell("SERIES");
            tablaFacturaDetalle.addCell("PRECIO U");
            tablaFacturaDetalle.addCell("DSCTO");
            tablaFacturaDetalle.addCell("TOTAL");
            for (int i = 0; i <factura.getFacturaDetalles().size(); i++)
            {
                tablaFacturaDetalle.addCell(factura.getFacturaDetalles().get(i).getProducto().getCodigo());
                tablaFacturaDetalle.addCell(factura.getFacturaDetalles().get(i).getCantidad()+"");
                tablaFacturaDetalle.addCell(factura.getFacturaDetalles().get(i).getProducto().getNombre());
                String series="";
                if (!factura.getFacturaDetalles().get(i).getProducto().isSerieAutogenerado()){
                    for (int j = 0; j <factura.getFacturaDetalles().get(i).getCaracteristicas().size(); j++){
                        series=series+" "+factura.getFacturaDetalles().get(i).getCaracteristicas().get(j).getSerie();
                    }
                }
                tablaFacturaDetalle.addCell(series);
                tablaFacturaDetalle.addCell("$"+factura.getFacturaDetalles().get(i).getPrecio().getPrecioSinIva());
                tablaFacturaDetalle.addCell("$"+factura.getFacturaDetalles().get(i).getValorDescuentoLinea());
                tablaFacturaDetalle.addCell("$"+factura.getFacturaDetalles().get(i).getSubtotalConDescuentoLinea());
            }
            documento.add(tablaFacturaDetalle);
            documento.add( new Paragraph("\n"));
            float [] columnasTablaFactura = {130F, 100F};
            Table tablaFactura = new Table(columnasTablaFactura);
            tablaFactura.addCell("SUBTOTAL SD 12%");
            tablaFactura.addCell("$"+factura.getSubtotalBase12SinDescuento());
            tablaFactura.addCell("SUBTOTAL CD 12%");
            tablaFactura.addCell("$"+factura.getSubtotalBase12ConDescuento());
            tablaFactura.addCell("SUBTOTAL SD 0%");
            tablaFactura.addCell("$"+factura.getSubtotalBase0SinDescuento());
            tablaFactura.addCell("SUBTOTAL CD 0%");
            tablaFactura.addCell("$"+factura.getSubtotalBase0ConDescuento());
            tablaFactura.addCell("TOTAL SD");
            tablaFactura.addCell("$"+factura.getTotalSinDescuento());
            tablaFactura.addCell("TOTAL CD");
            tablaFactura.addCell("$"+factura.getTotalConDescuento());
            tablaFactura.setTextAlignment(TextAlignment.RIGHT);
            tablaFactura.setHorizontalAlignment(HorizontalAlignment.RIGHT);
            documento.add(tablaFactura);
            
            documento.add(new Paragraph("INFORMACION ADICIONAL"+"\n" +
                    "COMENTARIO: " + factura.getComentario()).setBorder(new SolidBorder(1)).setWidth(300).setVerticalAlignment(VerticalAlignment.TOP).setHorizontalAlignment(HorizontalAlignment.LEFT));
            documento.add( new Paragraph("\n"));
            // 5. Close document
            documento.close();
            return new ByteArrayInputStream(salida.toByteArray());
        } catch(Exception e){
            return null;
        }
    }
    
    private ByteArrayInputStream crearXML(Factura factura) {
    	try {
    		String body=Util.soapConsultaFacturacionEletronica(factura.getClaveAcceso()).get();
            HttpClient httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(BodyPublishers.ofString(body))
                    .uri(URI.create(Constantes.urlConsultaFacturacionEletronicaSri))
                    .setHeader(Constantes.contentType, Constantes.contenTypeValor)
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            // print status code
            System.out.println(response.statusCode());
            // print response body
            System.out.println(response.body());
            ByteArrayInputStream xml= new ByteArrayInputStream(response.body().getBytes());
            return xml;
    	} catch(Exception e) {
    		return null;
    	}
    }
    
    public Optional<String> enviarCorreo(Factura _factura) {
    	try {
	    	Optional<Factura> opcional= repFactura.findById(_factura.getId());
	    	if(opcional.isEmpty()) {
	    		throw new EntidadNoExistenteException(Constantes.factura);
	    	}
	    	Factura factura = opcional.get();
	    	ByteArrayInputStream pdf = crearPDF(factura);
	    	ByteArrayInputStream xml = crearXML(factura);
	    	ByteArrayDataSource pdfData= new ByteArrayDataSource(pdf, "application/pdf"); 
	    	ByteArrayDataSource xmlData = new ByteArrayDataSource(xml, "application/xml"); 
	        // Esto es lo que va delante de @gmail.com en tu cuenta de correo. Es el remitente también.
	        Properties props = System.getProperties();
	        props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
	        props.put("mail.smtp.user", correoUsuario);
	        props.put("mail.smtp.clave", correoContrasena);    //La clave de la cuenta
	        props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
	        props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
	        props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google
	
	        Session session = Session.getDefaultInstance(props);
	        MimeMessage message = new MimeMessage(session);
	        
	        MimeBodyPart parte1 = new MimeBodyPart();
	        parte1.setDataHandler(new DataHandler(pdfData));
	        parte1.setFileName(Constantes.factura+factura.getSecuencia()+".pdf");
	        MimeBodyPart parte2 = new MimeBodyPart();
	        parte2.setDataHandler(new DataHandler(xmlData));
	        parte2.setFileName(Constantes.factura+factura.getSecuencia()+".xml");
	        
	        Multipart multipart = new MimeMultipart();
	        multipart.addBodyPart(parte1);
	        multipart.addBodyPart(parte2);

            message.setFrom(new InternetAddress(correoUsuario));
            message.addRecipients(Message.RecipientType.TO, factura.getCliente().getCorreos().get(0).getEmail());   //Se podrían añadir varios de la misma manera
            message.setSubject(Constantes.mensajeCorreo + factura.getCodigo());
            message.setText(Constantes.vacio);
            message.setContent(multipart);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", correoUsuario, correoContrasena);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            return Optional.of(Constantes.mensajeCorreoExitoso);
        }
        catch (MessagingException | IOException me) {
            me.printStackTrace();   //Si se produce un error
            return Optional.of(null);
        }
    }
}
