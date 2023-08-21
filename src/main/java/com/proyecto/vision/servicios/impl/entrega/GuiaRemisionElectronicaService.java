package com.proyecto.vision.servicios.impl.entrega;

import ayungan.com.signature.ConvertFile;
import ayungan.com.signature.SignatureXAdESBES;
import com.itextpdf.barcodes.Barcode128;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.*;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CertificadoNoExistenteException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.exception.EstadoInvalidoException;
import com.proyecto.vision.exception.FacturaElectronicaInvalidaException;
import com.proyecto.vision.modelos.recaudacion.*;
import com.proyecto.vision.modelos.venta.Factura;
import com.proyecto.vision.modelos.venta.FacturaLinea;
import com.proyecto.vision.modelos.venta.electronico.guiaremision.*;
import com.proyecto.vision.modelos.entrega.GuiaRemision;
import com.proyecto.vision.repositorios.entrega.IGuiaRemisionRepository;
import com.proyecto.vision.servicios.interf.usuario.IEmpresaService;
import com.proyecto.vision.servicios.interf.venta.IGuiaRemisionElectronicaService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
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
import java.net.MalformedURLException;
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

	@Autowired
	private IEmpresaService empresaService;
    
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
    	infoTributaria.setSecuencial(guiaRemision.getSecuencial());
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
		infoGuiaRemision.setPlaca(guiaRemision.getVehiculo().getPlaca());
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
				+ guiaRemision.getFactura().getSecuencial();
		destinatario.setNumDocSustento(numDocSustento);
		destinatario.setNumAutDocSustento(guiaRemision.getFactura().getClaveAcceso());
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		destinatario.setFechaEmisionDocSustento(dateFormat.format(guiaRemision.getFactura().getFecha()));
		destinatariosLista.add(destinatario);
		destinatarios.setDestinatario(destinatariosLista);
		return destinatarios;
	}
	private InfoAdicional crearInfoAdicional(GuiaRemision guiaRemision) {
		List<CampoAdicional> camposAdicionales = new ArrayList<>();
		if(!guiaRemision.getFactura().getCliente().getTelefonos().isEmpty()) {
			String telefono = guiaRemision.getFactura().getCliente().getTelefonos().get(0).getNumero();
			CampoAdicional campoAdicional = new CampoAdicional();
			campoAdicional.setNombre(Constantes.telefono);
			campoAdicional.setValor(telefono);
			camposAdicionales.add(campoAdicional);
		}
		if(!guiaRemision.getFactura().getCliente().getCelulares().isEmpty()){
			String celular = guiaRemision.getFactura().getCliente().getCelulares().get(0).getNumero();
			CampoAdicional campoAdicional = new CampoAdicional();
			campoAdicional.setNombre(Constantes.celular);
			campoAdicional.setValor(celular);
			camposAdicionales.add(campoAdicional);
		}
		if(!guiaRemision.getFactura().getCliente().getCorreos().isEmpty()) {
			String correo = guiaRemision.getFactura().getCliente().getCorreos().get(0).getEmail();
			CampoAdicional campoAdicional = new CampoAdicional();
			campoAdicional.setNombre(Constantes.correo);
			campoAdicional.setValor(correo);
			camposAdicionales.add(campoAdicional);
		}
		if(guiaRemision.getFactura().getCliente().getDireccion().length() > Constantes.cero) {
			String direccion = guiaRemision.getFactura().getCliente().getDireccion();
			CampoAdicional campoAdicional = new CampoAdicional();
			campoAdicional.setNombre(Constantes.direccion);
			campoAdicional.setValor(direccion);
			camposAdicionales.add(campoAdicional);
		}
		CampoAdicional campoAdicional = new CampoAdicional();
		campoAdicional.setNombre(Constantes.valor);
		campoAdicional.setValor(guiaRemision.getFactura().getTotal() + Constantes.vacio);
		camposAdicionales.add(campoAdicional);
		InfoAdicional infoAdicional = new InfoAdicional();
		infoAdicional.setCampoAdicional(camposAdicionales);
		return infoAdicional;
	}

	@Override
	public GuiaRemision enviar(long guiaRemisionId) throws MalformedURLException {
		Optional<GuiaRemision> opcional= rep.findById(guiaRemisionId);
		if(opcional.isEmpty()) {
			throw new EntidadNoExistenteException(Constantes.factura);
		}
		GuiaRemision guiaRemision = opcional.get();
		Resource certificado = empresaService.bajarCertificado(guiaRemision.getEmpresa().getId());
		if(certificado == null){
			throw new CertificadoNoExistenteException();
		}
		if(guiaRemision.getEstadoInterno().equals(Constantes.estadoInternoEmitida)){
			throw new EstadoInvalidoException(Constantes.estadoInternoEmitida);
		}
		if(guiaRemision.getEstadoInterno().equals(Constantes.estadoInternoAnulada)){
			throw new EstadoInvalidoException(Constantes.estadoInternoAnulada);
		}
		if(guiaRemision.getEstadoSri().equals(Constantes.estadoSriAutorizada)){
			throw new EstadoInvalidoException(Constantes.estadoSriAutorizada);
		}
		if(guiaRemision.getEstadoSri().equals(Constantes.estadoSriAnulada)){
			throw new EstadoInvalidoException(Constantes.estadoSriAnulada);
		}
		GuiaRemisionElectronica guiaRemisionElectronica = crear(guiaRemision);
		List<String> estadoRecepcion = recepcion(guiaRemisionElectronica, guiaRemision.getEmpresa().getCertificado(), guiaRemision.getEmpresa().getContrasena());
		if(estadoRecepcion.get(0).equals(Constantes.devueltaSri)) {
			throw new FacturaElectronicaInvalidaException("ESTADO DEL SRI:" + Constantes.espacio + estadoRecepcion.get(0) + Constantes.espacio + Constantes.guion + Constantes.espacio + "INFORMACION ADICIONAL: " + estadoRecepcion.get(1));
		}
		List<String> estadoAutorizacion = autorizacion(guiaRemisionElectronica);
		if(estadoAutorizacion.get(0).equals(Constantes.devueltaSri)) {
			throw new FacturaElectronicaInvalidaException("ESTADO DEL SRI:" + Constantes.espacio + estadoRecepcion.get(0) + Constantes.espacio + Constantes.guion + Constantes.espacio + "INFORMACION ADICIONAL: " + estadoRecepcion.get(1));
		}
		guiaRemision.setEstadoSri(Constantes.estadoSriAutorizada);
		guiaRemision.setFechaAutorizacion(new Date());
		enviarCorreo(guiaRemision, guiaRemisionElectronica);
		GuiaRemision facturada = rep.save(guiaRemision);
		facturada.normalizar();
		return facturada;
	}
    
    private List<String> recepcion(GuiaRemisionElectronica guiaRemisionElectronica, String certificado, String contrasena) {
    	try {
    		JAXBContext jaxbContext = JAXBContext.newInstance(GuiaRemisionElectronica.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, Constantes.utf8);
            jaxbMarshaller.marshal(guiaRemisionElectronica, System.out);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(guiaRemisionElectronica, sw);
            String xml=sw.toString();
			Path path = Paths.get(Constantes.pathCertificados + Constantes.slash + certificado);
			String ruta = path.toAbsolutePath().toString();
			byte[] cert = ConvertFile.readBytesFromFile(ruta);
            byte[] firmado=SignatureXAdESBES.firmarByteData(xml.getBytes(), cert, contrasena);
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
			List<String> resultado = new ArrayList<>();
			String estado = json.getJSONObject("soap:Envelope").getJSONObject("soap:Body").getJSONObject("ns2:validarComprobanteResponse").getJSONObject("RespuestaRecepcionComprobante").getString("estado");
			resultado.add(estado);
			if(estado.equals(Constantes.devueltaSri)){
				String informacionAdicional = Constantes.vacio;
				if(json.getJSONObject("soap:Envelope").getJSONObject("soap:Body").getJSONObject("ns2:validarComprobanteResponse").getJSONObject("RespuestaRecepcionComprobante")
						.getJSONObject("comprobantes").getJSONObject("comprobante").getJSONObject("mensajes").getJSONObject("mensaje").has("mensaje")){
					informacionAdicional = json.getJSONObject("soap:Envelope").getJSONObject("soap:Body").getJSONObject("ns2:validarComprobanteResponse").getJSONObject("RespuestaRecepcionComprobante")
							.getJSONObject("comprobantes").getJSONObject("comprobante").getJSONObject("mensajes").getJSONObject("mensaje").getString("mensaje");
				}
				if(json.getJSONObject("soap:Envelope").getJSONObject("soap:Body").getJSONObject("ns2:validarComprobanteResponse").getJSONObject("RespuestaRecepcionComprobante")
						.getJSONObject("comprobantes").getJSONObject("comprobante").getJSONObject("mensajes").getJSONObject("mensaje").has("informacionAdicional")) {
					informacionAdicional = json.getJSONObject("soap:Envelope").getJSONObject("soap:Body").getJSONObject("ns2:validarComprobanteResponse").getJSONObject("RespuestaRecepcionComprobante")
							.getJSONObject("comprobantes").getJSONObject("comprobante").getJSONObject("mensajes").getJSONObject("mensaje").getString("informacionAdicional");
				}
				resultado.add(informacionAdicional);
			}
			return resultado;
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

	public List<String> autorizacion(GuiaRemisionElectronica guiaRemisionElectronica){
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
			List<String> resultado = new ArrayList<>();
			String estado = json.getJSONObject("soap:Envelope").getJSONObject("soap:Body").getJSONObject("ns2:autorizacionComprobanteResponse").getJSONObject("RespuestaAutorizacionComprobante")
					.getJSONObject("autorizaciones").getJSONObject("autorizacion").getString("estado");
			resultado.add(estado);
			if(estado.equals(Constantes.noAutorizadoSri)){
				String informacionAdicional = json.getJSONObject("soap:Envelope").getJSONObject("soap:Body").getJSONObject("ns2:autorizacionComprobanteResponse").getJSONObject("RespuestaAutorizacionComprobante")
						.getJSONObject("autorizaciones").getJSONObject("autorizacion").getJSONObject("mensajes").getJSONObject("mensaje").getString("informacionAdicional");
				resultado.add(informacionAdicional);
			}
			if(estado.equals(Constantes.devueltaSri)){
				String informacionAdicional = Constantes.vacio;
				if(json.getJSONObject("soap:Envelope").getJSONObject("soap:Body").getJSONObject("ns2:autorizacionComprobanteResponse").getJSONObject("RespuestaAutorizacionComprobante")
						.getJSONObject("autorizaciones").getJSONObject("autorizacion").getJSONObject("mensajes").getJSONObject("mensaje").has("mensaje")){
					informacionAdicional = json.getJSONObject("soap:Envelope").getJSONObject("soap:Body").getJSONObject("ns2:autorizacionComprobanteResponse").getJSONObject("RespuestaAutorizacionComprobante")
							.getJSONObject("autorizaciones").getJSONObject("autorizacion").getJSONObject("mensajes").getJSONObject("mensaje").getString("mensaje");
				}
				if(json.getJSONObject("soap:Envelope").getJSONObject("soap:Body").getJSONObject("ns2:autorizacionComprobanteResponse").getJSONObject("RespuestaAutorizacionComprobante")
						.getJSONObject("autorizaciones").getJSONObject("autorizacion").getJSONObject("mensajes").getJSONObject("mensaje").has("informacionAdicional")) {
					informacionAdicional = json.getJSONObject("soap:Envelope").getJSONObject("soap:Body").getJSONObject("ns2:autorizacionComprobanteResponse").getJSONObject("RespuestaAutorizacionComprobante")
							.getJSONObject("autorizaciones").getJSONObject("autorizacion").getJSONObject("mensajes").getJSONObject("mensaje").getString("informacionAdicional");
				}
				resultado.add(informacionAdicional);
			}
			return resultado;
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
			documento.setMargins(0,0,0,0);
			// 4. Add content
			PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
			documento.setFont(font);
			documento.add(new Paragraph("LOGO").setFontSize(50).setTextAlignment(TextAlignment.CENTER));
			String regimen = Constantes.vacio;
			if(guiaRemision.getSesion().getUsuario().getEstacion().getEstablecimiento().getRegimen() != null) {
				regimen = guiaRemision.getSesion().getUsuario().getEstacion().getEstablecimiento().getRegimen().getDescripcion();
			}
			if(guiaRemision.getSesion().getUsuario().getEstacion().getRegimen() != null) {
				regimen = guiaRemision.getSesion().getUsuario().getEstacion().getRegimen().getDescripcion();
			}
			float [] columnas = {320F, 280F};
			Table tabla = new Table(columnas);
			tabla.addCell(getCellEmpresa(guiaRemision.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial() +"\n" + "\n" +
					"DIRECCIÓN MATRIZ: " + guiaRemision.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getDireccion() +"\n" + "\n" +
					"DIRECCIÓN SUCURSAL: " + guiaRemision.getSesion().getUsuario().getEstacion().getEstablecimiento().getDireccion() +"\n" + "\n" +
					regimen + "\n" + "\n" +
					"CONTIRUYENTE ESPECIAL: " + guiaRemision.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getResolucionEspecial() + "\n" + "\n" +
					"OBLIGADO A LLEVAR CONTABILIDAD: " + guiaRemision.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getObligadoContabilidad() + "\n" + "\n" +
					"AGENTE RETENCION RESOLUCIÓN: " + guiaRemision.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getResolucionAgente(), TextAlignment.LEFT));
			String numeroAutorizacion = Constantes.vacio;
			String fechaAutorizacion = Constantes.vacio;
			Image imagenCodigoBarras = null;
			if(guiaRemision.getEstadoSri().equals(Constantes.estadoSriAutorizada)){
				numeroAutorizacion = guiaRemision.getClaveAcceso();
				fechaAutorizacion = guiaRemision.getFechaAutorizacion().toString();
				Barcode128 codigoBarras = new Barcode128(pdf);
				codigoBarras.setCodeType(Barcode128.CODE128);
				codigoBarras.setCode(guiaRemision.getClaveAcceso());
				PdfFormXObject objetoCodigoBarras = codigoBarras.createFormXObject(null, null, pdf);
				imagenCodigoBarras = new Image(objetoCodigoBarras);
			}
			tabla.addCell(getCellFactura("RUC: "+ guiaRemision.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getIdentificacion()+"\n"+
					"FACTURA"+"\n"+
					"No. " + guiaRemision.getNumeroComprobante() + "\n" +
					"NÚMERO DE AUTORIZACIÓN: " + numeroAutorizacion + "\n" +
					"FECHA DE AUTORIZACIÓN: " + fechaAutorizacion + "\n" +
					"AMBIENTE: " + Constantes.facturaFisicaAmbienteValor + "\n" +
					"EMISIÓN: " + Constantes.facturaFisicaEmisionValor + "\n" + "\n" +
					"CLAVE DE ACCESO:", TextAlignment.LEFT, imagenCodigoBarras));
			tabla.setBorderCollapse(BorderCollapsePropertyValue.SEPARATE);
			tabla.setHorizontalBorderSpacing(3);
			documento.add(tabla);
			documento.add(new Paragraph("\n"));
			float [] columnasCliente = {300F, 300F};
			Table tablaCliente = new Table(columnasCliente);
			tablaCliente.addCell(getCellCliente("IDENTIFICACIÓN: " + guiaRemision.getFactura().getCliente().getIdentificacion(), TextAlignment.LEFT));
			tablaCliente.addCell(getCellCliente("RAZÓN SOCIAL: "+ guiaRemision.getFactura().getCliente().getRazonSocial(), TextAlignment.LEFT));
			tablaCliente.addCell(getCellCliente("DIRECCION: "+ guiaRemision.getFactura().getCliente().getDireccion(), TextAlignment.LEFT));
			tablaCliente.addCell(getCellCliente("FECHA EMISIÓN: " + guiaRemision.getFecha().toString(), TextAlignment.LEFT));
			documento.add(tablaCliente);
			documento.add( new Paragraph("\n"));
			String identificacionDestinatario = Constantes.vacio;
			String razonSocialDestinatario = Constantes.vacio;
			String direccionDestinatario = Constantes.vacio;
			if(guiaRemision.getOpcionGuia().equals(Constantes.cliente_direccion)){
				identificacionDestinatario = guiaRemision.getFactura().getCliente().getIdentificacion();
				razonSocialDestinatario = guiaRemision.getFactura().getCliente().getRazonSocial();
				direccionDestinatario = guiaRemision.getFactura().getCliente().getDireccion();
			}
			if(guiaRemision.getOpcionGuia().equals(Constantes.cliente_direccion)){
				identificacionDestinatario = guiaRemision.getIdentificacionDestinatario();
				razonSocialDestinatario = guiaRemision.getRazonSocialDestinatario();
				direccionDestinatario = guiaRemision.getDireccionDestinatario();
			}
			float [] columnasDestinatario = {300F, 300F};
			Table tablaDestinatario = new Table(columnasDestinatario);
			tablaDestinatario.addCell(getCellDestinatario("IDENTIFICACION DESTINATARIO:", TextAlignment.LEFT));
			tablaDestinatario.addCell(getCellDestinatario(identificacionDestinatario, TextAlignment.RIGHT));
			tablaDestinatario.addCell(getCellDestinatario("RAZON SOCIAL DESTINATARIO:", TextAlignment.LEFT));
			tablaDestinatario.addCell(getCellDestinatario(razonSocialDestinatario, TextAlignment.RIGHT));
			tablaDestinatario.addCell(getCellDestinatario("DIRECCION DESTINATARIO:", TextAlignment.LEFT));
			tablaDestinatario.addCell(getCellDestinatario(direccionDestinatario, TextAlignment.RIGHT));
			documento.add(tablaDestinatario);
			documento.add( new Paragraph("\n"));
			float [] columnasGuiaRemision = {300F, 300F};
			Table tablaGuiaRemision = new Table(columnasGuiaRemision);
			tablaGuiaRemision.addCell(getCellGuiaRemision("IDENTIFICACION TRANSPORTISTA:", TextAlignment.LEFT));
			tablaGuiaRemision.addCell(getCellGuiaRemision(guiaRemision.getTransportista().getIdentificacion(), TextAlignment.RIGHT));
			tablaGuiaRemision.addCell(getCellGuiaRemision("RAZON SOCIAL TRANSPORTISTA:", TextAlignment.LEFT));
			tablaGuiaRemision.addCell(getCellGuiaRemision(guiaRemision.getTransportista().getNombre(), TextAlignment.RIGHT));
			tablaGuiaRemision.addCell(getCellGuiaRemision("PLACA:", TextAlignment.LEFT));
			tablaGuiaRemision.addCell(getCellGuiaRemision(guiaRemision.getVehiculo().getPlaca(), TextAlignment.RIGHT));
			tablaGuiaRemision.addCell(getCellGuiaRemision("MOTIVO TRASLADO:", TextAlignment.LEFT));
			tablaGuiaRemision.addCell(getCellGuiaRemision(guiaRemision.getMotivoTraslado(), TextAlignment.RIGHT));
			tablaGuiaRemision.addCell(getCellGuiaRemision("RUTA:", TextAlignment.LEFT));
			tablaGuiaRemision.addCell(getCellGuiaRemision(guiaRemision.getRuta(), TextAlignment.RIGHT));
			tablaGuiaRemision.addCell(getCellGuiaRemision("FECHA INICIO DE TRTANSPORTE:", TextAlignment.LEFT));
			tablaGuiaRemision.addCell(getCellGuiaRemision(guiaRemision.getFechaInicioTransporte().toString(), TextAlignment.RIGHT));
			tablaGuiaRemision.addCell(getCellGuiaRemision("FECHA FIN DE TRANSPORTE:", TextAlignment.RIGHT));
			tablaGuiaRemision.addCell(getCellGuiaRemision(guiaRemision.getFechaFinTransporte().toString(), TextAlignment.RIGHT));
			documento.add(tablaGuiaRemision);
			documento.add( new Paragraph("\n"));
			float [] columnasTablaFacturaDetalle = {200F, 200F, 200F};
			Table tablaFacturaDetalle = new Table(columnasTablaFacturaDetalle);
			tablaFacturaDetalle.addCell(getCellColumnaFactura("CÓDIGO"));
			tablaFacturaDetalle.addCell(getCellColumnaFactura("CANT"));
			tablaFacturaDetalle.addCell(getCellColumnaFactura("DESCRIPCION"));
			for (int i = 0; i < guiaRemision.getFactura().getFacturaLineas().size(); i++)
			{
				tablaFacturaDetalle.addCell(getCellFilaFactura(guiaRemision.getFactura().getFacturaLineas().get(i).getProducto().getCodigo()));
				tablaFacturaDetalle.addCell(getCellFilaFactura(guiaRemision.getFactura().getFacturaLineas().get(i).getCantidad() + Constantes.vacio));
				tablaFacturaDetalle.addCell(getCellFilaFactura(guiaRemision.getFactura().getFacturaLineas().get(i).getProducto().getNombre()));
			}
			documento.add(tablaFacturaDetalle);
			// 5. Close document
			documento.close();
			return new ByteArrayInputStream(salida.toByteArray());
		} catch(Exception e){
			return null;
		}
	}

	private Cell getCellEmpresa(String text, TextAlignment alignment) {
		Cell cell = new Cell().add(new Paragraph(text));
		cell.setTextAlignment(alignment);
		cell.setBorder(new SolidBorder(ColorConstants.BLUE, 2));
		cell.setBorderTopLeftRadius(new BorderRadius(5));
		cell.setBorderTopRightRadius(new BorderRadius(5));
		cell.setBorderBottomLeftRadius(new BorderRadius(5));
		cell.setBorderBottomRightRadius(new BorderRadius(5));
		cell.setFontSize(Constantes.fontSize10);
		return cell;
	}
	private Cell getCellFactura(String text, TextAlignment alignment, Image imagenCodigoBarras) {
		Paragraph parrafo = new Paragraph(text);
		Cell cell = new Cell();
		cell.add(parrafo);
		if(imagenCodigoBarras != null){
			cell.add(imagenCodigoBarras);
		}
		cell.setTextAlignment(alignment);
		cell.setFontSize(Constantes.fontSize10);
		cell.setBorder(new SolidBorder(ColorConstants.BLUE, 2));
		cell.setBorderTopLeftRadius(new BorderRadius(5));
		cell.setBorderTopRightRadius(new BorderRadius(5));
		cell.setBorderBottomLeftRadius(new BorderRadius(5));
		cell.setBorderBottomRightRadius(new BorderRadius(5));
		return cell;
	}
	private Cell getCellCliente(String text, TextAlignment alignment) {
		Cell cell = new Cell().add(new Paragraph(text));
		cell.setTextAlignment(alignment);
		cell.setBorder(Border.NO_BORDER);
		cell.setFontSize(Constantes.fontSize10);
		cell.setBorderBottom(new SolidBorder(ColorConstants.BLUE,1));
		cell.setBorderTop(new SolidBorder(ColorConstants.BLUE, 1));
		return cell;
	}
	private Cell getCellColumnaFactura(String text) {
		Paragraph parrafo = new Paragraph(text);
		Cell cell = new Cell();
		cell.add(parrafo);
		cell.setFontSize(Constantes.fontSize10);
		cell.setBackgroundColor(ColorConstants.BLUE).setFontColor(ColorConstants.WHITE);
		cell.setBorder(new SolidBorder(ColorConstants.BLUE,1));
		return cell;
	}
	private Cell getCellFilaFactura(String text) {
		Paragraph parrafo = new Paragraph(text);
		Cell cell = new Cell();
		cell.add(parrafo);
		cell.setFontSize(Constantes.fontSize10);
		cell.setBorder(new SolidBorder(ColorConstants.BLUE,1));
		return cell;
	}
	private Cell getCellGuiaRemision(String text, TextAlignment alignment) {
		Paragraph parrafo = new Paragraph(text);
		Cell cell = new Cell();
		cell.add(parrafo);
		cell.setTextAlignment(alignment);
		cell.setFontSize(Constantes.fontSize10);
		cell.setBorder(new SolidBorder(ColorConstants.BLUE,1));
		return cell;
	}
	private Cell getCellDestinatario(String text, TextAlignment alignment) {
		Paragraph parrafo = new Paragraph(text);
		Cell cell = new Cell();
		cell.add(parrafo);
		cell.setTextAlignment(alignment);
		cell.setFontSize(Constantes.fontSize10);
		cell.setBorder(new SolidBorder(ColorConstants.BLUE,1));
		return cell;
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
	        parte1.setFileName(Constantes.nota_credito + guiaRemision.getSecuencial()+Constantes.extensionPdf);
	        MimeBodyPart parte2 = new MimeBodyPart();
	        parte2.setDataHandler(new DataHandler(xmlData));
	        parte2.setFileName(Constantes.nota_credito + guiaRemision.getSecuencial()+Constantes.extensionXml);
	        
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

	@Override
	public ByteArrayInputStream obtenerPDF(long guiaRemisionId){
		Optional<GuiaRemision> opcional= rep.findById(guiaRemisionId);
		if(opcional.isEmpty()) {
			throw new EntidadNoExistenteException(Constantes.guia_remision);
		}
		GuiaRemision guiaRemision = opcional.get();
		ByteArrayInputStream pdf = crearPDF(guiaRemision);
		return pdf;
	}

	@Override
	public void enviarPDFYXML(long guiaRemisionId){
		Optional<GuiaRemision> opcional= rep.findById(guiaRemisionId);
		if(opcional.isEmpty()) {
			throw new EntidadNoExistenteException(Constantes.factura);
		}
		GuiaRemision guiaRemision = opcional.get();
		GuiaRemisionElectronica guiaRemisionElectronica = crear(guiaRemision);
		enviarCorreo(guiaRemision, guiaRemisionElectronica);
	}
}
