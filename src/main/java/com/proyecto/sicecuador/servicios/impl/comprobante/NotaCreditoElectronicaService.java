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
import com.proyecto.sicecuador.modelos.comprobante.electronico.notacredito.*;
import com.proyecto.sicecuador.repositorios.comprobante.INotaCreditoVentaRepository;
import com.proyecto.sicecuador.servicios.interf.comprobante.INotaCreditoElectronicaService;
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
public class NotaCreditoElectronicaService implements INotaCreditoElectronicaService {
    @Autowired
    private INotaCreditoVentaRepository rep;
    
    @Value("${prefijo.url.imagenes}")
    private String imagenes;
    
    @Value("${correo.usuario}")
    private String correoUsuario;
    
    @Value("${correo.contrasena}")
    private String correoContrasena;

    
    private NotaCreditoElectronica crear(NotaCreditoVenta notaCreditoVenta) {
    	//MAPEO A FACTURA ELECTRONICA
		NotaCreditoElectronica notaCreditoElectronica = new NotaCreditoElectronica();
    	InfoTributaria infoTributaria = new InfoTributaria();
    	  	
    	infoTributaria.setAmbiente(Constantes.pruebas_sri);
    	infoTributaria.setTipoEmision(Constantes.emision_normal_sri);
    	infoTributaria.setRazonSocial(notaCreditoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial());
    	infoTributaria.setNombreComercial(notaCreditoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getNombreComercial());
    	infoTributaria.setRuc(notaCreditoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getIdentificacion());
    	infoTributaria.setClaveAcceso(notaCreditoVenta.getClaveAcceso());
    	infoTributaria.setCodDoc(Constantes.nota_de_credito_sri);
    	infoTributaria.setEstab(notaCreditoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getCodigoSRI());
    	infoTributaria.setPtoEmi(notaCreditoVenta.getSesion().getUsuario().getEstacion().getCodigoSRI());
    	infoTributaria.setSecuencial(notaCreditoVenta.getSecuencia());
    	infoTributaria.setDirMatriz(notaCreditoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getDireccion());
    	
    	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    	String fechaEmision = dateFormat.format(notaCreditoVenta.getFecha());
		InfoNotaCredito infoNotaCredito = new InfoNotaCredito();
		infoNotaCredito.setFechaEmision(fechaEmision);
		infoNotaCredito.setDirEstablecimiento(notaCreditoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getDireccion());
		infoNotaCredito.setTipoIdentificacionComprador(notaCreditoVenta.getFactura().getCliente().getTipoIdentificacion().getCodigoSRI());
		infoNotaCredito.setRazonSocialComprador(notaCreditoVenta.getFactura().getCliente().getRazonSocial());
		infoNotaCredito.setIdentificacionComprador(notaCreditoVenta.getFactura().getCliente().getIdentificacion());
		infoNotaCredito.setObligadoContabilidad(notaCreditoVenta.getFactura().getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getObligadoContabilidad());
		infoNotaCredito.setCodDocModificado(Constantes.factura_sri);
		String numero = notaCreditoVenta.getFactura().getSesion().getUsuario().getEstacion().getEstablecimiento().getCodigoSRI()+"-"+notaCreditoVenta.getFactura().getSesion().getUsuario().getEstacion().getCodigoSRI()+"-"+notaCreditoVenta.getFactura().getSecuencia();
		infoNotaCredito.setNumDocModificado(numero);
		String fechaEmisionFactura = dateFormat.format(notaCreditoVenta.getFactura().getFecha());
		infoNotaCredito.setFechaEmisionDocSustento(fechaEmisionFactura);
		infoNotaCredito.setTotalSinImpuestos(notaCreditoVenta.getTotalSinDescuento());
		infoNotaCredito.setValorModificacion(notaCreditoVenta.getTotalConDescuento());
		infoNotaCredito.setMoneda(Constantes.moneda);
		infoNotaCredito.setTotalConImpuestos(crearTotalConImpuestos(notaCreditoVenta));
		infoNotaCredito.setMotivo(notaCreditoVenta.getOperacion());
    	Detalles detalles=crearDetalles(notaCreditoVenta);

		List<CampoAdicional> infoAdicional = crearInfoAdicional(notaCreditoVenta);

		notaCreditoElectronica.setInfoTributaria(infoTributaria);
		notaCreditoElectronica.setInfoNotaCredito(infoNotaCredito);
		notaCreditoElectronica.setDetalles(detalles);
		notaCreditoElectronica.setInfoAdicional(infoAdicional);
    	return notaCreditoElectronica;
    }

    private TotalConImpuestos crearTotalConImpuestos(NotaCreditoVenta notaCreditoVenta){
    	TotalConImpuestos totalConImpuestos = new TotalConImpuestos();
    	List<TotalImpuesto> totalImpuestos = new ArrayList<>();
    	for(NotaCreditoVentaLinea notaCreditoVentaLinea: notaCreditoVenta.getNotaCreditoVentaLineas()) {
        	TotalImpuesto totalImpuesto = new TotalImpuesto();
    		totalImpuesto.setCodigo(Constantes.iva_sri);
        	totalImpuesto.setCodigoPorcentaje(notaCreditoVentaLinea.getImpuesto().getCodigoSRI());
        	totalImpuesto.setBaseImponible(notaCreditoVentaLinea.getTotalSinDescuentoLinea());
        	totalImpuesto.setValor(notaCreditoVentaLinea.getIvaSinDescuentoLinea());
        	totalImpuestos.add(totalImpuesto);
    	}
    	totalConImpuestos.setTotalImpuesto(totalImpuestos);
    	return totalConImpuestos;
    }

    private Detalles crearDetalles(NotaCreditoVenta notaCreditoVenta) {
    	Detalles detalles=new Detalles();
    	List<Detalle> detalleLista = new ArrayList<>();
    	for(NotaCreditoVentaLinea notaCreditoVentaLinea: notaCreditoVenta.getNotaCreditoVentaLineas()) {
    		Detalle detalle = new Detalle();
    		detalle.setCodigoInterno(notaCreditoVentaLinea.getProducto().getCodigo());
    		detalle.setDescripcion(notaCreditoVentaLinea.getProducto().getNombre());
    		detalle.setCantidad(notaCreditoVentaLinea.getDevolucion());
    		detalle.setPrecioUnitario(notaCreditoVentaLinea.getPrecio().getPrecioVentaPublicoManual());
    		detalle.setDescuento(notaCreditoVentaLinea.getValorDescuentoLinea());
    		detalle.setPrecioTotalSinImpuesto(notaCreditoVentaLinea.getTotalSinDescuentoLinea());
    		detalle.setImpuestos(crearImpuestos(notaCreditoVentaLinea));
    		detalleLista.add(detalle);
    	}
    	detalles.setDetalle(detalleLista);
    	return detalles;
    }
    
    private Impuestos crearImpuestos(NotaCreditoVentaLinea notaCreditoVentaLinea) {
    	Impuestos impuestos=new Impuestos();
    	List<Impuesto> impuestoLista = new ArrayList<>();
    	Impuesto impuesto=new Impuesto();
    	impuesto.setCodigo(Constantes.iva_sri);
    	impuesto.setCodigoPorcentaje(notaCreditoVentaLinea.getImpuesto().getCodigoSRI());
    	impuesto.setTarifa(notaCreditoVentaLinea.getImpuesto().getPorcentaje());
    	impuesto.setBaseImponible(notaCreditoVentaLinea.getTotalSinDescuentoLinea());
    	impuesto.setValor(notaCreditoVentaLinea.getIvaSinDescuentoLinea());
    	impuestoLista.add(impuesto);
    	impuestos.setImpuesto(impuestoLista);
    	return impuestos;
    }

	private List<CampoAdicional> crearInfoAdicional(NotaCreditoVenta notaCreditoVenta) {
		List<CampoAdicional> infoAdicional = new ArrayList<>();
		CampoAdicional campoAdicional1 = new CampoAdicional();
		campoAdicional1.setNombre(Constantes.telefono);
		campoAdicional1.setValor(notaCreditoVenta.getFactura().getCliente().getTelefonos().get(0).getNumero());
		CampoAdicional campoAdicional2 = new CampoAdicional();
		campoAdicional2.setNombre(Constantes.celular);
		campoAdicional2.setValor(notaCreditoVenta.getFactura().getCliente().getCelulares().get(0).getNumero());
		CampoAdicional campoAdicional3 = new CampoAdicional();
		campoAdicional3.setNombre(Constantes.correo);
		campoAdicional3.setValor(notaCreditoVenta.getFactura().getCliente().getCorreos().get(0).getEmail());
		CampoAdicional campoAdicional4 = new CampoAdicional();
		campoAdicional4.setNombre(Constantes.direccion);
		campoAdicional4.setValor(notaCreditoVenta.getFactura().getCliente().getDireccion());
		CampoAdicional campoAdicional5 = new CampoAdicional();
		campoAdicional5.setNombre(Constantes.valor);
		campoAdicional5.setValor(notaCreditoVenta.getFactura().getTotalConDescuento() + Constantes.vacio);
		infoAdicional.add(campoAdicional1);
		infoAdicional.add(campoAdicional2);
		infoAdicional.add(campoAdicional3);
		infoAdicional.add(campoAdicional4);
		infoAdicional.add(campoAdicional5);
		return infoAdicional;
	}

	@Override
	public NotaCreditoVenta enviar(long notaCreditoVentaId) {
		Optional<NotaCreditoVenta> opcional= rep.findById(notaCreditoVentaId);
		if(opcional.isEmpty()) {
			throw new EntidadNoExistenteException(Constantes.nota_credito_venta);
		}
		NotaCreditoVenta notaCreditoVenta = opcional.get();
		NotaCreditoElectronica notaCreditoElectronica = crear(notaCreditoVenta);
		if(notaCreditoVenta.getEstado().equals(Constantes.estadoEmitida)) {
			String estadoRecepcion = recepcion(notaCreditoElectronica);
			if(estadoRecepcion.equals(Constantes.recibidaSri)) {
				String estadoAutorizacion = autorizacion(notaCreditoElectronica);
				if(estadoAutorizacion.equals(Constantes.autorizadoSri)){
					notaCreditoVenta.setEstado(Constantes.estadoFacturada);
					enviarCorreo(notaCreditoVenta, notaCreditoElectronica);
					NotaCreditoVenta facturada = rep.save(notaCreditoVenta);
					facturada.normalizar();
					return facturada;
				}
				throw new FacturaElectronicaInvalidaException(estadoAutorizacion);
			}
			throw new FacturaElectronicaInvalidaException(estadoRecepcion);
		} else if(notaCreditoVenta.getEstado().equals(Constantes.estadoFacturada)){
			enviarCorreo(notaCreditoVenta, notaCreditoElectronica);
			notaCreditoVenta.normalizar();
			return notaCreditoVenta;
		}
		throw new FacturaElectronicaInvalidaException(Constantes.estado);
	}
    
    private String recepcion(NotaCreditoElectronica notaCreditoElectronica) {
    	try {
    		JAXBContext jaxbContext = JAXBContext.newInstance(NotaCreditoElectronica.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, Constantes.utf8);
            jaxbMarshaller.marshal(notaCreditoElectronica, System.out);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(notaCreditoElectronica, sw);
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

	public String autorizacion(NotaCreditoElectronica notaCreditoElectronica){
		try {
			String body=Util.soapConsultaFacturacionEletronica(notaCreditoElectronica.getInfoTributaria().getClaveAcceso());
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
    
    public ByteArrayInputStream crearPDF(NotaCreditoVenta notaCreditoVenta) {
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
            documento.add(new Paragraph(notaCreditoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial()+"\n"+
                    "DIRECCION MATRIZ: "+notaCreditoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getDireccion()+"\n"+
            		"OBLIGADO A LLEVAR CONTABILIDAD: "+notaCreditoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getObligadoContabilidad()).setBorder(new SolidBorder(1)));
            documento.add( new Paragraph("\n"));
            documento.add(new Paragraph("RUC: "+notaCreditoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getIdentificacion()+"\n"+
                    "NOTA DE CREDITO"+"\n"+
                    "No. " + notaCreditoVenta.getSecuencia() + "\n" +
                    "NÚMERO DE AUTORIZACIÓN: " + notaCreditoVenta.getClaveAcceso()+ "\n" +
                    "FECHA: " + notaCreditoVenta.getFecha().toString() + "\n" +
                    "AMBIENTE: " + Constantes.facturaFisicaAmbienteValor + "\n" +
                    "EMISIÓN: " + Constantes.facturaFisicaEmisionValor).setBorder(new SolidBorder(1)));
            
            documento.add( new Paragraph("\n"));
            
            String telefonoCliente="";
            String correoCliente="";
            if (!notaCreditoVenta.getFactura().getCliente().getTelefonos().isEmpty()){
                telefonoCliente=notaCreditoVenta.getFactura().getCliente().getTelefonos().get(0).getNumero();
            }
            if (!notaCreditoVenta.getFactura().getCliente().getCorreos().isEmpty()){
                correoCliente=notaCreditoVenta.getFactura().getCliente().getCorreos().get(0).getEmail();
            }
            documento.add( new Paragraph("RAZÓN SOCIAL: "+notaCreditoVenta.getFactura().getCliente().getRazonSocial()+"\n"+
                    "IDENTIFICACIÓN: " + notaCreditoVenta.getFactura().getCliente().getIdentificacion()+"\n"+
                    "FECHA EMISIÓN: " + notaCreditoVenta.getFactura().getFecha().toString()+"\n"+
                    "DIRECCIÓN: " + notaCreditoVenta.getFactura().getCliente().getDireccion() + "\n" +
                    "TELÉFONO: " + telefonoCliente + "\n" +
                    "CORREO: " + correoCliente).setBorder(new SolidBorder(1)));
            documento.add( new Paragraph("\n"));
            float [] columnasTablaFacturaDetalle = {100F, 40F, 160F, 100F, 100F, 100F};
            Table tablaFacturaDetalle = new Table(columnasTablaFacturaDetalle);
            tablaFacturaDetalle.addCell("CÓDIGO");
            tablaFacturaDetalle.addCell("DEV");
            tablaFacturaDetalle.addCell("DESCRIPCION");
            tablaFacturaDetalle.addCell("PRECIO U");
            tablaFacturaDetalle.addCell("DSCTO");
            tablaFacturaDetalle.addCell("TOTAL");
            for (NotaCreditoVentaLinea notaCreditoVentaLinea : notaCreditoVenta.getNotaCreditoVentaLineas())
            {
                tablaFacturaDetalle.addCell(notaCreditoVentaLinea.getProducto().getCodigo());
                tablaFacturaDetalle.addCell(notaCreditoVentaLinea.getDevolucion()+"");
                tablaFacturaDetalle.addCell(notaCreditoVentaLinea.getProducto().getNombre());
                tablaFacturaDetalle.addCell("$" + notaCreditoVentaLinea.getPrecio().getPrecioVentaPublicoManual());
                tablaFacturaDetalle.addCell("$" + notaCreditoVentaLinea.getValorDescuentoLinea());
                tablaFacturaDetalle.addCell("$" +notaCreditoVentaLinea.getTotalSinDescuentoLinea());
            }
            documento.add(tablaFacturaDetalle);
            documento.add( new Paragraph("\n"));
            float [] columnasTablaFactura = {130F, 100F};
            Table tablaFactura = new Table(columnasTablaFactura);
            tablaFactura.addCell("SUBTOTAL SD 12%");
            tablaFactura.addCell("$"+notaCreditoVenta.getSubtotalBase12SinDescuento());
            tablaFactura.addCell("SUBTOTAL CD 12%");
            tablaFactura.addCell("$"+notaCreditoVenta.getSubtotalBase12SinDescuento());
            tablaFactura.addCell("SUBTOTAL SD 0%");
            tablaFactura.addCell("$"+notaCreditoVenta.getSubtotalBase0SinDescuento());
            tablaFactura.addCell("SUBTOTAL CD 0%");
            tablaFactura.addCell("$"+notaCreditoVenta.getSubtotalBase0SinDescuento());
            tablaFactura.addCell("TOTAL SD");
            tablaFactura.addCell("$"+notaCreditoVenta.getTotalSinDescuento());
            tablaFactura.addCell("TOTAL CD");
            tablaFactura.addCell("$"+notaCreditoVenta.getTotalConDescuento());
            tablaFactura.setTextAlignment(TextAlignment.RIGHT);
            tablaFactura.setHorizontalAlignment(HorizontalAlignment.RIGHT);
            documento.add(tablaFactura);
            
            documento.add(new Paragraph("INFORMACION ADICIONAL"+"\n" +
					"MOTIVO: " +notaCreditoVenta.getOperacion() + "\n" +
                    "COMENTARIO: " + notaCreditoVenta.getComentario()).setBorder(new SolidBorder(1)).setWidth(300).setVerticalAlignment(VerticalAlignment.TOP).setHorizontalAlignment(HorizontalAlignment.LEFT));
            documento.add( new Paragraph("\n"));
            // 5. Close document
            documento.close();
            return new ByteArrayInputStream(salida.toByteArray());
        } catch(Exception e){
            return null;
        }
    }
    
    private ByteArrayInputStream crearXML(NotaCreditoElectronica notaCreditoElectronica) {
    	try {
			JAXBContext jaxbContext = JAXBContext.newInstance(NotaCreditoElectronica.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, Constantes.utf8);
			jaxbMarshaller.marshal(notaCreditoElectronica, System.out);
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(notaCreditoElectronica, sw);
			String xml=sw.toString();
			return new ByteArrayInputStream(xml.getBytes());
    	} catch(Exception e) {
    		return null;
    	}
    }
    
    private void enviarCorreo(NotaCreditoVenta notaCreditoVenta, NotaCreditoElectronica notaCreditoElectronica) {
    	try {
	    	ByteArrayInputStream pdf = crearPDF(notaCreditoVenta);
	    	ByteArrayInputStream xml = crearXML(notaCreditoElectronica);
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
	        parte1.setFileName(Constantes.nota_credito + notaCreditoVenta.getSecuencia()+Constantes.extensionPdf);
	        MimeBodyPart parte2 = new MimeBodyPart();
	        parte2.setDataHandler(new DataHandler(xmlData));
	        parte2.setFileName(Constantes.nota_credito + notaCreditoVenta.getSecuencia()+Constantes.extensionXml);
	        
	        Multipart multipart = new MimeMultipart();
	        multipart.addBodyPart(parte1);
	        multipart.addBodyPart(parte2);

            message.setFrom(new InternetAddress(correoUsuario));
            message.addRecipients(Message.RecipientType.TO, notaCreditoVenta.getFactura().getCliente().getCorreos().get(0).getEmail());   //Se podrían añadir varios de la misma manera
            message.setSubject(notaCreditoVenta.getFactura().getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial()+ Constantes.mensajeCorreo + notaCreditoVenta.getCodigo());
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
