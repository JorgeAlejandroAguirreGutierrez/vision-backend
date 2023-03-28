package com.proyecto.sicecuador.servicios.impl.entrega;

import ayungan.com.signature.ConvertFile;
import ayungan.com.signature.SignatureXAdESBES;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.exception.EstadoInvalidoException;
import com.proyecto.sicecuador.exception.FacturaElectronicaInvalidaException;
import com.proyecto.sicecuador.modelos.comprobante.FacturaLinea;
import com.proyecto.sicecuador.modelos.comprobante.electronico.guiaremision.*;
import com.proyecto.sicecuador.modelos.entrega.GuiaRemision;
import com.proyecto.sicecuador.repositorios.entrega.IGuiaRemisionRepository;
import com.proyecto.sicecuador.servicios.interf.comprobante.IGuiaRemisionElectronicaService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.mail.Message;
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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.CertificateException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

@Service
public class GuiaRemisionElectronicaService implements IGuiaRemisionElectronicaService {
    @Autowired
    private IGuiaRemisionRepository rep;
    
    @Value("${prefijo.url.imagenes}")
    private String imagenes;
    
    @Value("${correo.usuario}")
    private String correoUsuario;
    
    @Value("${correo.contrasena}")
    private String correoContrasena;

    
    private GuiaRemisionElectronica crear(GuiaRemision guiaRemision) {
    	//MAPEO A FACTURA ELECTRONICA
		GuiaRemisionElectronica guiaRemisionElectronica = new GuiaRemisionElectronica();
    	InfoTributaria infoTributaria = new InfoTributaria();
    	  	
    	infoTributaria.setAmbiente(Constantes.pruebas_sri);
    	infoTributaria.setTipoEmision(Constantes.emision_normal_sri);
    	infoTributaria.setRazonSocial(guiaRemision.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial());
    	infoTributaria.setNombreComercial(guiaRemision.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getNombreComercial());
    	infoTributaria.setRuc(guiaRemision.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getIdentificacion());
    	infoTributaria.setClaveAcceso(guiaRemision.getClaveAcceso());
    	infoTributaria.setCodDoc(Constantes.guia_de_remision_sri);
    	infoTributaria.setEstab(guiaRemision.getSesion().getUsuario().getEstacion().getEstablecimiento().getCodigoSRI());
    	infoTributaria.setPtoEmi(guiaRemision.getSesion().getUsuario().getEstacion().getCodigoSRI());
    	infoTributaria.setSecuencial(guiaRemision.getSecuencia());
    	infoTributaria.setDirMatriz(guiaRemision.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getDireccion());

		InfoGuiaRemision infoGuiaRemision = new InfoGuiaRemision();
		infoGuiaRemision.setDirEstablecimiento(guiaRemision.getSesion().getUsuario().getEstacion().getEstablecimiento().getDireccion());
		infoGuiaRemision.setDirPartida(guiaRemision.getSesion().getUsuario().getEstacion().getEstablecimiento().getDireccion());
		infoGuiaRemision.setRazonSocialTransportista(guiaRemision.getTransportista().getNombre());
		infoGuiaRemision.setTipoIdentificacionTransportista(guiaRemision.getTransportista().getTipoIdentificacion().getCodigoSRI());
		infoGuiaRemision.setRucTransportista(guiaRemision.getTransportista().getIdentificacion());
		infoGuiaRemision.setObligadoContabilidad(guiaRemision.getFactura().getCliente().getObligadoContabilidad());
		infoGuiaRemision.setContribuyenteEspecial(null);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String fechaInicioTransporte = dateFormat.format(guiaRemision.getFechaInicioTransporte());
		infoGuiaRemision.setFechaIniTransporte(fechaInicioTransporte);
		String fechaFinTransporte = dateFormat.format(guiaRemision.getFechaFinTransporte());
		infoGuiaRemision.setFechaFinTransporte(fechaFinTransporte);
		infoGuiaRemision.setPlaca(guiaRemision.getTransportista().getVehiculoTransporte().getPlaca());
		Destinatarios destinatarios = crearDestinatarios(guiaRemision);
		InfoAdicional infoAdicional = crearInfoAdicional(guiaRemision);
		guiaRemisionElectronica.setInfoTributaria(infoTributaria);
		guiaRemisionElectronica.setInfoGuiaRemision(infoGuiaRemision);
		guiaRemisionElectronica.setDestinatarios(destinatarios);
		guiaRemisionElectronica.setInfoAdicional(infoAdicional);
    	return guiaRemisionElectronica;
    }
	private Destinatarios crearDestinatarios(GuiaRemision guiaRemision) {
		Destinatarios destinatarios = new Destinatarios();
		List<Destinatario> destinatariosLista = new ArrayList<>();
		Destinatario destinatario = new Destinatario();
		if(guiaRemision.getOpcionGuia().equals(Constantes.cliente_direccion)){
			destinatario.setIdentificacionDestinatario(guiaRemision.getFactura().getCliente().getIdentificacion());
			destinatario.setRazonSocialDestinatario(guiaRemision.getFactura().getCliente().getRazonSocial());
			destinatario.setDirDestinatario(guiaRemision.getFactura().getCliente().getDireccion());
		}
		if(guiaRemision.getOpcionGuia().equals(Constantes.nueva_direccion)){
			destinatario.setIdentificacionDestinatario(guiaRemision.getIdentificacionDestinatario());
			destinatario.setRazonSocialDestinatario(guiaRemision.getRazonSocialDestinatario());
			destinatario.setDirDestinatario(guiaRemision.getDireccionDestinatario());
		}
		destinatario.setMotivoTraslado(guiaRemision.getMotivoTraslado());
		destinatario.setRuta(guiaRemision.getRuta());
		destinatario.setCodDocSustento(Constantes.factura_sri);
		String numDocSustento = guiaRemision.getFactura().getSesion().getUsuario().getEstacion().getEstablecimiento().getCodigoSRI()
				+ Constantes.guion + guiaRemision.getFactura().getSesion().getUsuario().getEstacion().getCodigoSRI() + Constantes.guion
				+ guiaRemision.getFactura().getSecuencia();
		destinatario.setNumDocSustento(numDocSustento);
		destinatario.setNumAutDocSustento(guiaRemision.getFactura().getClaveAcceso());
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		destinatario.setFechaEmisionDocSustento(dateFormat.format(guiaRemision.getFactura().getFecha()));
		destinatariosLista.add(destinatario);
		destinatarios.setDestinatario(destinatariosLista);
		return destinatarios;
	}
	private InfoAdicional crearInfoAdicional(GuiaRemision guiaRemision) {
		InfoAdicional infoAdicional = new InfoAdicional();
		List<CampoAdicional> camposAdicionales = new ArrayList<>();
		CampoAdicional campoAdicional1 = new CampoAdicional();
		campoAdicional1.setNombre(Constantes.telefono);
		campoAdicional1.setValor(guiaRemision.getFactura().getCliente().getTelefonos().get(0).getNumero());
		CampoAdicional campoAdicional2 = new CampoAdicional();
		campoAdicional2.setNombre(Constantes.celular);
		campoAdicional2.setValor(guiaRemision.getFactura().getCliente().getCelulares().get(0).getNumero());
		CampoAdicional campoAdicional3 = new CampoAdicional();
		campoAdicional3.setNombre(Constantes.correo);
		campoAdicional3.setValor(guiaRemision.getFactura().getCliente().getCorreos().get(0).getEmail());
		CampoAdicional campoAdicional4 = new CampoAdicional();
		campoAdicional4.setNombre(Constantes.direccion);
		campoAdicional4.setValor(guiaRemision.getFactura().getCliente().getDireccion());
		CampoAdicional campoAdicional5 = new CampoAdicional();
		campoAdicional5.setNombre(Constantes.valor);
		campoAdicional5.setValor(guiaRemision.getFactura().getTotalConDescuento() + Constantes.vacio);
		camposAdicionales.add(campoAdicional1);
		camposAdicionales.add(campoAdicional2);
		camposAdicionales.add(campoAdicional3);
		camposAdicionales.add(campoAdicional4);
		camposAdicionales.add(campoAdicional5);
		infoAdicional.setCampoAdicional(camposAdicionales);
		return infoAdicional;
	}

	@Override
	public GuiaRemision enviar(long guiaRemisionId) {
		Optional<GuiaRemision> opcional= rep.findById(guiaRemisionId);
		if(opcional.isEmpty()) {
			throw new EntidadNoExistenteException(Constantes.guia_remision);
		}
		GuiaRemision guiaRemision = opcional.get();
		if(!guiaRemision.getEstado().equals(Constantes.estadoEmitida)){
			throw new EstadoInvalidoException(Constantes.estadoFacturada);
		}
		GuiaRemisionElectronica guiaRemisionElectronica = crear(guiaRemision);
		if(guiaRemision.getEstado().equals(Constantes.estadoEmitida)) {
			String estadoRecepcion = recepcion(guiaRemisionElectronica);
			if(estadoRecepcion.equals(Constantes.recibidaSri)) {
				String estadoAutorizacion = autorizacion(guiaRemisionElectronica);
				if(estadoAutorizacion.equals(Constantes.autorizadoSri)){
					guiaRemision.setEstado(Constantes.estadoFacturada);
					enviarCorreo(guiaRemision, guiaRemisionElectronica);
					GuiaRemision facturada = rep.save(guiaRemision);
					facturada.normalizar();
					return facturada;
				}
				throw new FacturaElectronicaInvalidaException(estadoAutorizacion);
			}
			throw new FacturaElectronicaInvalidaException(estadoRecepcion);
		}
		if(guiaRemision.getEstado().equals(Constantes.estadoFacturada)){
			enviarCorreo(guiaRemision, guiaRemisionElectronica);
			guiaRemision.normalizar();
			return guiaRemision;
		}
		throw new FacturaElectronicaInvalidaException(Constantes.estadoNoFacturada);
	}
    
    private String recepcion(GuiaRemisionElectronica guiaRemisionElectronica) {
    	try {
    		JAXBContext jaxbContext = JAXBContext.newInstance(GuiaRemisionElectronica.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, Constantes.utf8);
            jaxbMarshaller.marshal(guiaRemisionElectronica, System.out);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(guiaRemisionElectronica, sw);
            String xml=sw.toString();
			Path path = Paths.get(Constantes.certificadoSri);
			String ruta = path.toAbsolutePath().toString();
			byte[] cert = ConvertFile.readBytesFromFile(ruta);
            byte[] firmado=SignatureXAdESBES.firmarByteData(xml.getBytes(), cert, Constantes.contrasenaCertificadoSri);
            String encode=Base64.getEncoder().encodeToString(firmado);
            String body=Util.soapFacturacionEletronica(encode);
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
            return json.getJSONObject("soap:Envelope").getJSONObject("soap:Body").getJSONObject("ns2:validarComprobanteResponse").getJSONObject("RespuestaRecepcionComprobante").getString("estado");
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

	public String autorizacion(GuiaRemisionElectronica guiaRemisionElectronica){
		try {
			String body=Util.soapConsultaFacturacionEletronica(guiaRemisionElectronica.getInfoTributaria().getClaveAcceso());
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
			JSONObject json=Util.convertirXmlJson(response.body());
			return json.getJSONObject("soap:Envelope").getJSONObject("soap:Body").getJSONObject("ns2:autorizacionComprobanteResponse").getJSONObject("RespuestaAutorizacionComprobante")
					.getJSONObject("autorizaciones").getJSONObject("autorizacion").getString("estado");
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
    
    public ByteArrayInputStream crearPDF(GuiaRemision guiaRemision) {
    	try {
            ByteArrayOutputStream salida = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(salida);
            PdfDocument pdf = new PdfDocument(writer);
            // Initialize document
            Document documento = new Document(pdf, PageSize.A4);
            documento.setMargins(20, 20, 20, 20);
            // 4. Add content
            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            documento.setFont(font);
            documento.add(new Paragraph(guiaRemision.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial()+"\n"+
                    "DIRECCION MATRIZ: "+ guiaRemision.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getDireccion()+"\n"+
            		"OBLIGADO A LLEVAR CONTABILIDAD: "+ guiaRemision.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getObligadoContabilidad()).setBorder(new SolidBorder(1)));
            documento.add( new Paragraph("\n"));
            documento.add(new Paragraph("RUC: "+ guiaRemision.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getIdentificacion()+"\n"+
                    "NOTA DE DEBITO"+"\n"+
                    "No. " + guiaRemision.getSecuencia() + "\n" +
                    "NÚMERO DE AUTORIZACIÓN: " + guiaRemision.getClaveAcceso()+ "\n" +
                    "FECHA: " + guiaRemision.getFecha().toString() + "\n" +
                    "AMBIENTE: " + Constantes.facturaFisicaAmbienteValor + "\n" +
                    "EMISIÓN: " + Constantes.facturaFisicaEmisionValor).setBorder(new SolidBorder(1)));
            
            documento.add( new Paragraph("\n"));
            
            String telefonoCliente="";
            String correoCliente="";
            if (!guiaRemision.getFactura().getCliente().getTelefonos().isEmpty()){
                telefonoCliente = guiaRemision.getFactura().getCliente().getTelefonos().get(0).getNumero();
            }
            if (!guiaRemision.getFactura().getCliente().getCorreos().isEmpty()){
                correoCliente = guiaRemision.getFactura().getCliente().getCorreos().get(0).getEmail();
            }
            documento.add( new Paragraph("RAZÓN SOCIAL: "+ guiaRemision.getFactura().getCliente().getRazonSocial()+"\n"+
                    "IDENTIFICACIÓN: " + guiaRemision.getFactura().getCliente().getIdentificacion()+"\n"+
                    "FECHA EMISIÓN: " + guiaRemision.getFactura().getFecha().toString()+"\n"+
                    "DIRECCIÓN: " + guiaRemision.getFactura().getCliente().getDireccion() + "\n" +
                    "TELÉFONO: " + telefonoCliente + "\n" +
                    "CORREO: " + correoCliente).setBorder(new SolidBorder(1)));
            documento.add( new Paragraph("\n"));
            float [] columnasTablaFacturaDetalle = {100F, 40F, 160F, 100F, 100F, 100F};
            Table tablaFacturaDetalle = new Table(columnasTablaFacturaDetalle);
            tablaFacturaDetalle.addCell("CÓDIGO");
            tablaFacturaDetalle.addCell("CANT");
            tablaFacturaDetalle.addCell("DESCRIPCION");
            tablaFacturaDetalle.addCell("PRECIO U");
            tablaFacturaDetalle.addCell("DSCTO");
            tablaFacturaDetalle.addCell("TOTAL");
            for (FacturaLinea facturaLinea : guiaRemision.getFactura().getFacturaLineas())
            {
                tablaFacturaDetalle.addCell(facturaLinea.getProducto().getCodigo());
                tablaFacturaDetalle.addCell(facturaLinea.getCantidad() + Constantes.vacio);
                tablaFacturaDetalle.addCell(facturaLinea.getProducto().getNombre());
                tablaFacturaDetalle.addCell("$" + facturaLinea.getPrecio().getPrecioVentaPublicoManual());
                tablaFacturaDetalle.addCell("$" + facturaLinea.getValorDescuentoLinea());
                tablaFacturaDetalle.addCell("$" +facturaLinea.getTotalConDescuentoLinea());
            }
            documento.add(tablaFacturaDetalle);
            documento.add( new Paragraph("\n"));
            float [] columnasTablaFactura = {130F, 100F};
            Table tablaFactura = new Table(columnasTablaFactura);
            tablaFactura.addCell("SUBTOTAL SD 12%");
            tablaFactura.addCell("$" + guiaRemision.getFactura().getSubtotalBase12SinDescuento());
            tablaFactura.addCell("SUBTOTAL CD 12%");
            tablaFactura.addCell("$" + guiaRemision.getFactura().getSubtotalBase12SinDescuento());
            tablaFactura.addCell("SUBTOTAL SD 0%");
            tablaFactura.addCell("$" + guiaRemision.getFactura().getSubtotalBase0SinDescuento());
            tablaFactura.addCell("SUBTOTAL CD 0%");
            tablaFactura.addCell("$" + guiaRemision.getFactura().getSubtotalBase0SinDescuento());
            tablaFactura.addCell("TOTAL SD");
            tablaFactura.addCell("$" + guiaRemision.getFactura().getTotalSinDescuento());
            tablaFactura.addCell("TOTAL CD");
            tablaFactura.addCell("$" + guiaRemision.getFactura().getTotalConDescuento());
            tablaFactura.setTextAlignment(TextAlignment.RIGHT);
            tablaFactura.setHorizontalAlignment(HorizontalAlignment.RIGHT);
            documento.add(tablaFactura);
            
            documento.add(new Paragraph("INFORMACION ADICIONAL"+"\n" +
                    "COMENTARIO: " + guiaRemision.getFactura().getComentario()).setBorder(new SolidBorder(1)).setWidth(300).setVerticalAlignment(VerticalAlignment.TOP).setHorizontalAlignment(HorizontalAlignment.LEFT));
            documento.add( new Paragraph("\n"));
            // 5. Close document
            documento.close();
            return new ByteArrayInputStream(salida.toByteArray());
        } catch(Exception e){
            return null;
        }
    }
    
    private ByteArrayInputStream crearXML(GuiaRemisionElectronica guiaRemisionElectronica) {
    	try {
			JAXBContext jaxbContext = JAXBContext.newInstance(GuiaRemisionElectronica.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, Constantes.utf8);
			jaxbMarshaller.marshal(guiaRemisionElectronica, System.out);
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(guiaRemisionElectronica, sw);
			String xml = sw.toString();
			return new ByteArrayInputStream(xml.getBytes());
    	} catch(Exception e) {
    		return null;
    	}
    }
    
    private void enviarCorreo(GuiaRemision guiaRemision, GuiaRemisionElectronica guiaRemisionElectronica) {
    	try {
	    	ByteArrayInputStream pdf = crearPDF(guiaRemision);
	    	ByteArrayInputStream xml = crearXML(guiaRemisionElectronica);
	    	ByteArrayDataSource pdfData= new ByteArrayDataSource(pdf, Constantes.applicationPdf); 
	    	ByteArrayDataSource xmlData = new ByteArrayDataSource(xml, Constantes.textXml); 
	        Properties props = System.getProperties();
	        props.put(Constantes.mailSmtpHost, Constantes.valorMailSmtpHost);
	        props.put(Constantes.mailSmtpUser, correoUsuario); 
	        props.put(Constantes.mailSmtpClave, correoContrasena);
	        props.put(Constantes.mailSmtpAuth, Constantes.valorMailtSmtpAuth);
	        props.put(Constantes.mailSmtpStarttlsEnable, Constantes.valorMailtSmtpStarttlsEnable);
	        props.put(Constantes.mailSmtpPort, Constantes.valorMailSmtpPort);
	
	        Session session = Session.getDefaultInstance(props);
	        MimeMessage message = new MimeMessage(session);
	        
	        MimeBodyPart parte1 = new MimeBodyPart();
	        parte1.setDataHandler(new DataHandler(pdfData));
	        parte1.setFileName(Constantes.nota_credito + guiaRemision.getSecuencia()+Constantes.extensionPdf);
	        MimeBodyPart parte2 = new MimeBodyPart();
	        parte2.setDataHandler(new DataHandler(xmlData));
	        parte2.setFileName(Constantes.nota_credito + guiaRemision.getSecuencia()+Constantes.extensionXml);
	        
	        Multipart multipart = new MimeMultipart();
	        multipart.addBodyPart(parte1);
	        multipart.addBodyPart(parte2);

            message.setFrom(new InternetAddress(correoUsuario));
            message.addRecipients(Message.RecipientType.TO, guiaRemision.getFactura().getCliente().getCorreos().get(0).getEmail());   //Se podrían añadir varios de la misma manera
            message.setSubject(guiaRemision.getFactura().getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial()+ Constantes.mensajeCorreo + guiaRemision.getCodigo());
            message.setText(Constantes.vacio);
            message.setContent(multipart);
            Transport transport = session.getTransport(Constantes.smtp);
            transport.connect(Constantes.smtpGmailCom, correoUsuario, correoContrasena);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (Exception e) {
            e.printStackTrace();   //Si se produce un error
        }
    }
}
