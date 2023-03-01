package com.proyecto.sicecuador.servicios.impl.comprobante;

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
import com.proyecto.sicecuador.exception.FacturaElectronicaInvalidaException;
import com.proyecto.sicecuador.modelos.comprobante.NotaCreditoVenta;
import com.proyecto.sicecuador.modelos.comprobante.NotaCreditoVentaLinea;
import com.proyecto.sicecuador.modelos.comprobante.NotaDebitoVenta;
import com.proyecto.sicecuador.modelos.comprobante.NotaDebitoVentaLinea;
import com.proyecto.sicecuador.modelos.comprobante.electronico.notadebitoventa.*;
import com.proyecto.sicecuador.repositorios.comprobante.INotaDebitoVentaRepository;
import com.proyecto.sicecuador.servicios.interf.comprobante.INotaDebitoVentaElectronicaService;
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
public class NotaDebitoVentaElectronicaService implements INotaDebitoVentaElectronicaService {
    @Autowired
    private INotaDebitoVentaRepository rep;
    
    @Value("${prefijo.url.imagenes}")
    private String imagenes;
    
    @Value("${correo.usuario}")
    private String correoUsuario;
    
    @Value("${correo.contrasena}")
    private String correoContrasena;

    
    private NotaDebitoVentaElectronica crear(NotaDebitoVenta notaDebitoVenta) {
    	//MAPEO A FACTURA ELECTRONICA
		NotaDebitoVentaElectronica notaDebitoVentaElectronica = new NotaDebitoVentaElectronica();
    	InfoTributaria infoTributaria = new InfoTributaria();
    	  	
    	infoTributaria.setAmbiente(Constantes.pruebas_sri);
    	infoTributaria.setTipoEmision(Constantes.emision_normal_sri);
    	infoTributaria.setRazonSocial(notaDebitoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial());
    	infoTributaria.setNombreComercial(notaDebitoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getNombreComercial());
    	infoTributaria.setRuc(notaDebitoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getIdentificacion());
    	infoTributaria.setClaveAcceso(notaDebitoVenta.getClaveAcceso());
    	infoTributaria.setCodDoc(Constantes.factura_sri);
    	infoTributaria.setEstab(notaDebitoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getCodigoSRI());
    	infoTributaria.setPtoEmi(notaDebitoVenta.getSesion().getUsuario().getEstacion().getCodigoSRI());
    	infoTributaria.setSecuencial(notaDebitoVenta.getSecuencia());
    	infoTributaria.setDirMatriz(notaDebitoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getDireccion());
    	
    	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
    	String fechaEmision = dateFormat.format(notaDebitoVenta.getFecha());
		InfoNotaDebito infoNotaDebito = new InfoNotaDebito();
		infoNotaDebito.setFechaEmision(fechaEmision);
		infoNotaDebito.setTipoIdentificacionComprador(notaDebitoVenta.getFactura().getCliente().getTipoIdentificacion().getCodigoSRI());
		infoNotaDebito.setRazonSocialComprador(notaDebitoVenta.getFactura().getCliente().getRazonSocial());
		infoNotaDebito.setIdentificacionComprador(notaDebitoVenta.getFactura().getCliente().getIdentificacion());
		infoNotaDebito.setObligadoContabilidad(notaDebitoVenta.getFactura().getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getObligadoContabilidad());
		infoNotaDebito.setCodDocModificado(Constantes.factura_sri);
		String numero = notaDebitoVenta.getFactura().getSesion().getUsuario().getEstacion().getEstablecimiento().getCodigoSRI() + "-" + notaDebitoVenta.getFactura().getSesion().getUsuario().getEstacion().getCodigoSRI() + "-" + notaDebitoVenta.getFactura().getSecuencia();
		infoNotaDebito.setNumDocModificado(numero);
		String fechaEmisionFactura = dateFormat.format(notaDebitoVenta.getFactura().getFecha());
		infoNotaDebito.setFechaEmisionDocSustento(fechaEmisionFactura);
		infoNotaDebito.setTotalSinImpuestos(notaDebitoVenta.getTotalConDescuento());
		Impuestos impuestos = crearImpuestos(notaDebitoVenta);
		Pagos pagos = crearPagos(notaDebitoVenta);
		Motivos motivos = crearMotivos(notaDebitoVenta);

		notaDebitoVentaElectronica.setInfoTributaria(infoTributaria);
		notaDebitoVentaElectronica.setInfoNotaDebito(infoNotaDebito);
		notaDebitoVentaElectronica.getInfoNotaDebito().setImpuestos(impuestos);
		notaDebitoVentaElectronica.getInfoNotaDebito().setPagos(pagos);
		notaDebitoVentaElectronica.setMotivos(motivos);
    	return notaDebitoVentaElectronica;
    }

	private Impuestos crearImpuestos(NotaDebitoVenta notaDebitoVenta) {
		Impuestos impuestos=new Impuestos();
		List<Impuesto> impuestoLista = new ArrayList<>();
		for(NotaDebitoVentaLinea notaDebitoVentaLinea: notaDebitoVenta.getNotaDebitoVentaLineas()) {
			Impuesto impuesto=new Impuesto();
			impuesto.setCodigo(Constantes.iva_sri);
			impuesto.setCodigoPorcentaje(notaDebitoVentaLinea.getImpuesto().getCodigoSRI());
			impuesto.setTarifa(notaDebitoVentaLinea.getImpuesto().getPorcentaje());
			impuesto.setBaseImponible(notaDebitoVentaLinea.getTotalSinDescuentoLinea());
			impuesto.setValor(notaDebitoVentaLinea.getIvaSinDescuentoLinea());
			impuestoLista.add(impuesto);
		}
		impuestos.setImpuesto(impuestoLista);
		return impuestos;
	}

	private Pagos crearPagos(NotaDebitoVenta notaDebitoVenta) {
		Pagos pagos = new Pagos();
		List<Pago> pagoLista = new ArrayList<>();
		for(NotaDebitoVentaLinea notaDebitoVentaLinea: notaDebitoVenta.getNotaDebitoVentaLineas()) {
			Pago pago=new Pago();
			pagoLista.add(pago);
		}
		pagos.setPago(pagoLista);
		return pagos;
	}
	private Motivos crearMotivos(NotaDebitoVenta notaDebitoVenta) {
    	Motivos motivos=new Motivos();
    	List<Motivo> motivoLista = new ArrayList<>();
    	for(NotaDebitoVentaLinea notaDebitoVentaLinea: notaDebitoVenta.getNotaDebitoVentaLineas()) {
    		Motivo motivo = new Motivo();
    		motivo.setRazon(notaDebitoVentaLinea.getProducto().getNombre());
    		motivo.setValor(notaDebitoVentaLinea.getTotalSinDescuentoLinea());
    		motivoLista.add(motivo);
    	}
    	motivos.setMotivo(motivoLista);
    	return motivos;
    }

	@Override
	public NotaDebitoVenta enviar(long notaDebitoVentaId) {
		Optional<NotaDebitoVenta> opcional= rep.findById(notaDebitoVentaId);
		if(opcional.isEmpty()) {
			throw new EntidadNoExistenteException(Constantes.nota_credito_venta);
		}
		NotaDebitoVenta notaDebitoVenta = opcional.get();
		NotaDebitoVentaElectronica notaDebitoVentaElectronica = crear(notaDebitoVenta);
		if(notaDebitoVenta.getEstado().equals(Constantes.estadoEmitida)) {
			String estadoRecepcion = recepcion(notaDebitoVentaElectronica);
			String estadoAutorizacion = autorizacion(notaDebitoVentaElectronica);
			if(estadoRecepcion.equals(Constantes.recibidaSri) && estadoAutorizacion.equals(Constantes.autorizadoSri)) {
				notaDebitoVenta.setEstado(Constantes.estadoFacturada);
				enviarCorreo(notaDebitoVenta, notaDebitoVentaElectronica);
				NotaDebitoVenta facturada = rep.save(notaDebitoVenta);
				facturada.normalizar();
				return facturada;
			}
			throw new FacturaElectronicaInvalidaException(estadoAutorizacion);
		} else if(notaDebitoVenta.getEstado().equals(Constantes.estadoFacturada)){
			enviarCorreo(notaDebitoVenta, notaDebitoVentaElectronica);
			notaDebitoVenta.normalizar();
			return notaDebitoVenta;
		}
		throw new FacturaElectronicaInvalidaException(Constantes.estado);
	}
    
    private String recepcion(NotaDebitoVentaElectronica notaDebitoVentaElectronica) {
    	try {
    		JAXBContext jaxbContext = JAXBContext.newInstance(NotaDebitoVentaElectronica.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, Constantes.utf8);
            jaxbMarshaller.marshal(notaDebitoVentaElectronica, System.out);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(notaDebitoVentaElectronica, sw);
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

	public String autorizacion(NotaDebitoVentaElectronica notaDebitoVentaElectronica){
		try {
			String body=Util.soapConsultaFacturacionEletronica(notaDebitoVentaElectronica.getInfoTributaria().getClaveAcceso());
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
    
    public ByteArrayInputStream crearPDF(NotaDebitoVenta notaDebitoVenta) {
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
            documento.add(new Paragraph(notaDebitoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial()+"\n"+
                    "DIRECCION MATRIZ: "+notaDebitoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getDireccion()+"\n"+
            		"OBLIGADO A LLEVAR CONTABILIDAD: "+notaDebitoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getObligadoContabilidad()).setBorder(new SolidBorder(1)));
            documento.add( new Paragraph("\n"));
            documento.add(new Paragraph("RUC: "+notaDebitoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getIdentificacion()+"\n"+
                    "NOTA DE DEBITO"+"\n"+
                    "No. " + notaDebitoVenta.getSecuencia() + "\n" +
                    "NÚMERO DE AUTORIZACIÓN: " + notaDebitoVenta.getClaveAcceso()+ "\n" +
                    "FECHA: " + notaDebitoVenta.getFecha().toString() + "\n" +
                    "AMBIENTE: " + Constantes.facturaFisicaAmbienteValor + "\n" +
                    "EMISIÓN: " + Constantes.facturaFisicaEmisionValor).setBorder(new SolidBorder(1)));
            
            documento.add( new Paragraph("\n"));
            
            String telefonoCliente="";
            String correoCliente="";
            if (!notaDebitoVenta.getFactura().getCliente().getTelefonos().isEmpty()){
                telefonoCliente = notaDebitoVenta.getFactura().getCliente().getTelefonos().get(0).getNumero();
            }
            if (!notaDebitoVenta.getFactura().getCliente().getCorreos().isEmpty()){
                correoCliente = notaDebitoVenta.getFactura().getCliente().getCorreos().get(0).getEmail();
            }
            documento.add( new Paragraph("RAZÓN SOCIAL: "+notaDebitoVenta.getFactura().getCliente().getRazonSocial()+"\n"+
                    "IDENTIFICACIÓN: " + notaDebitoVenta.getFactura().getCliente().getIdentificacion()+"\n"+
                    "FECHA EMISIÓN: " + notaDebitoVenta.getFactura().getFecha().toString()+"\n"+
                    "DIRECCIÓN: " + notaDebitoVenta.getFactura().getCliente().getDireccion() + "\n" +
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
            for (NotaDebitoVentaLinea notaDebitoVentaLinea : notaDebitoVenta.getNotaDebitoVentaLineas())
            {
                tablaFacturaDetalle.addCell(notaDebitoVentaLinea.getProducto().getCodigo());
                tablaFacturaDetalle.addCell(notaDebitoVentaLinea.getCantidad()+"");
                tablaFacturaDetalle.addCell(notaDebitoVentaLinea.getProducto().getNombre());
                tablaFacturaDetalle.addCell("$" + notaDebitoVentaLinea.getPrecio().getPrecioVentaPublicoManual());
                tablaFacturaDetalle.addCell("$" + notaDebitoVentaLinea.getValorDescuentoLinea());
                tablaFacturaDetalle.addCell("$" +notaDebitoVentaLinea.getTotalSinDescuentoLinea());
            }
            documento.add(tablaFacturaDetalle);
            documento.add( new Paragraph("\n"));
            float [] columnasTablaFactura = {130F, 100F};
            Table tablaFactura = new Table(columnasTablaFactura);
            tablaFactura.addCell("SUBTOTAL SD 12%");
            tablaFactura.addCell("$" + notaDebitoVenta.getSubtotalBase12SinDescuento());
            tablaFactura.addCell("SUBTOTAL CD 12%");
            tablaFactura.addCell("$" + notaDebitoVenta.getSubtotalBase12SinDescuento());
            tablaFactura.addCell("SUBTOTAL SD 0%");
            tablaFactura.addCell("$" + notaDebitoVenta.getSubtotalBase0SinDescuento());
            tablaFactura.addCell("SUBTOTAL CD 0%");
            tablaFactura.addCell("$" + notaDebitoVenta.getSubtotalBase0SinDescuento());
            tablaFactura.addCell("TOTAL SD");
            tablaFactura.addCell("$" + notaDebitoVenta.getTotalSinDescuento());
            tablaFactura.addCell("TOTAL CD");
            tablaFactura.addCell("$" + notaDebitoVenta.getTotalConDescuento());
            tablaFactura.setTextAlignment(TextAlignment.RIGHT);
            tablaFactura.setHorizontalAlignment(HorizontalAlignment.RIGHT);
            documento.add(tablaFactura);
            
            documento.add(new Paragraph("INFORMACION ADICIONAL"+"\n" +
                    "COMENTARIO: " + notaDebitoVenta.getComentario()).setBorder(new SolidBorder(1)).setWidth(300).setVerticalAlignment(VerticalAlignment.TOP).setHorizontalAlignment(HorizontalAlignment.LEFT));
            documento.add( new Paragraph("\n"));
            // 5. Close document
            documento.close();
            return new ByteArrayInputStream(salida.toByteArray());
        } catch(Exception e){
            return null;
        }
    }
    
    private ByteArrayInputStream crearXML(NotaDebitoVentaElectronica notaDebitoVentaElectronica) {
    	try {
			JAXBContext jaxbContext = JAXBContext.newInstance(NotaDebitoVentaElectronica.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, Constantes.utf8);
			jaxbMarshaller.marshal(notaDebitoVentaElectronica, System.out);
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(notaDebitoVentaElectronica, sw);
			String xml=sw.toString();
			return new ByteArrayInputStream(xml.getBytes());
    	} catch(Exception e) {
    		return null;
    	}
    }
    
    private void enviarCorreo(NotaDebitoVenta notaDebitoVenta, NotaDebitoVentaElectronica notaDebitoVentaElectronica) {
    	try {
	    	ByteArrayInputStream pdf = crearPDF(notaDebitoVenta);
	    	ByteArrayInputStream xml = crearXML(notaDebitoVentaElectronica);
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
	        parte1.setFileName(Constantes.nota_credito + notaDebitoVenta.getSecuencia()+Constantes.extensionPdf);
	        MimeBodyPart parte2 = new MimeBodyPart();
	        parte2.setDataHandler(new DataHandler(xmlData));
	        parte2.setFileName(Constantes.nota_credito + notaDebitoVenta.getSecuencia()+Constantes.extensionXml);
	        
	        Multipart multipart = new MimeMultipart();
	        multipart.addBodyPart(parte1);
	        multipart.addBodyPart(parte2);

            message.setFrom(new InternetAddress(correoUsuario));
            message.addRecipients(Message.RecipientType.TO, notaDebitoVenta.getFactura().getCliente().getCorreos().get(0).getEmail());   //Se podrían añadir varios de la misma manera
            message.setSubject(notaDebitoVenta.getFactura().getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial()+ Constantes.mensajeCorreo + notaDebitoVenta.getCodigo());
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
