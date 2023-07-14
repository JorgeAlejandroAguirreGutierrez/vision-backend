package com.proyecto.vision.servicios.impl.venta;

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
import com.proyecto.vision.modelos.venta.Factura;
import com.proyecto.vision.modelos.venta.NotaCreditoVenta;
import com.proyecto.vision.modelos.venta.NotaCreditoVentaLinea;
import com.proyecto.vision.modelos.venta.electronico.factura.FacturaElectronica;
import com.proyecto.vision.modelos.venta.electronico.notacredito.*;
import com.proyecto.vision.repositorios.venta.INotaCreditoVentaRepository;
import com.proyecto.vision.servicios.interf.usuario.IEmpresaService;
import com.proyecto.vision.servicios.interf.venta.INotaCreditoElectronicaService;
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
public class NotaCreditoElectronicaService implements INotaCreditoElectronicaService {
    @Autowired
    private INotaCreditoVentaRepository rep;

	@Autowired
	private IEmpresaService empresaService;
    
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
    	infoTributaria.setSecuencial(notaCreditoVenta.getSecuencial());
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
		String numero = notaCreditoVenta.getFactura().getSesion().getUsuario().getEstacion().getEstablecimiento().getCodigoSRI()+Constantes.guion+notaCreditoVenta.getFactura().getSesion().getUsuario().getEstacion().getCodigoSRI()+Constantes.guion+notaCreditoVenta.getFactura().getSecuencial();
		infoNotaCredito.setNumDocModificado(numero);
		String fechaEmisionFactura = dateFormat.format(notaCreditoVenta.getFactura().getFecha());
		infoNotaCredito.setFechaEmisionDocSustento(fechaEmisionFactura);
		infoNotaCredito.setTotalSinImpuestos(notaCreditoVenta.getTotalSinDescuento());
		infoNotaCredito.setValorModificacion(notaCreditoVenta.getTotalConDescuento());
		infoNotaCredito.setMoneda(Constantes.moneda);
		infoNotaCredito.setTotalConImpuestos(crearTotalConImpuestos(notaCreditoVenta));
		infoNotaCredito.setMotivo(notaCreditoVenta.getOperacion());
    	Detalles detalles=crearDetalles(notaCreditoVenta);
		InfoAdicional infoAdicional = crearInfoAdicional(notaCreditoVenta);
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
    		detalle.setPrecioUnitario(Math.round(notaCreditoVentaLinea.getCostoUnitario()*100.0)/100.0);
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

	private InfoAdicional crearInfoAdicional(NotaCreditoVenta notaCreditoVenta) {
		List<CampoAdicional> camposAdicionales = new ArrayList<>();
		if(!notaCreditoVenta.getFactura().getCliente().getTelefonos().isEmpty()) {
			String telefono = notaCreditoVenta.getFactura().getCliente().getTelefonos().get(0).getNumero();
			CampoAdicional campoAdicional = new CampoAdicional();
			campoAdicional.setNombre(Constantes.telefono);
			campoAdicional.setValor(telefono);
			camposAdicionales.add(campoAdicional);
		}
		if(!notaCreditoVenta.getFactura().getCliente().getCelulares().isEmpty()){
			String celular = notaCreditoVenta.getFactura().getCliente().getCelulares().get(0).getNumero();
			CampoAdicional campoAdicional = new CampoAdicional();
			campoAdicional.setNombre(Constantes.celular);
			campoAdicional.setValor(celular);
			camposAdicionales.add(campoAdicional);
		}
		if(!notaCreditoVenta.getFactura().getCliente().getCorreos().isEmpty()) {
			String correo = notaCreditoVenta.getFactura().getCliente().getCorreos().get(0).getEmail();
			CampoAdicional campoAdicional = new CampoAdicional();
			campoAdicional.setNombre(Constantes.correo);
			campoAdicional.setValor(correo);
			camposAdicionales.add(campoAdicional);
		}
		if(notaCreditoVenta.getFactura().getCliente().getDireccion().length() > Constantes.cero) {
			String direccion = notaCreditoVenta.getFactura().getCliente().getDireccion();
			CampoAdicional campoAdicional = new CampoAdicional();
			campoAdicional.setNombre(Constantes.direccion);
			campoAdicional.setValor(direccion);
			camposAdicionales.add(campoAdicional);
		}
		CampoAdicional campoAdicional = new CampoAdicional();
		campoAdicional.setNombre(Constantes.valor);
		campoAdicional.setValor(notaCreditoVenta.getFactura().getValorTotal() + Constantes.vacio);
		camposAdicionales.add(campoAdicional);
		InfoAdicional infoAdicional = new InfoAdicional();
		infoAdicional.setCampoAdicional(camposAdicionales);
		return infoAdicional;
	}

	@Override
	public NotaCreditoVenta enviar(long notaCreditoVentaId) throws MalformedURLException {
		Optional<NotaCreditoVenta> opcional= rep.findById(notaCreditoVentaId);
		if(opcional.isEmpty()) {
			throw new EntidadNoExistenteException(Constantes.factura);
		}
		NotaCreditoVenta notaCreditoVenta = opcional.get();
		Resource certificado = empresaService.bajarCertificado(notaCreditoVenta.getEmpresa().getId());
		if(certificado == null){
			throw new CertificadoNoExistenteException();
		}
		if(notaCreditoVenta.getEmpresa().getContrasena().equals(Constantes.vacio)){
			throw new FacturaElectronicaInvalidaException(Constantes.contrasena);
		}
		if(notaCreditoVenta.getEstadoInterno().equals(Constantes.estadoInternoAnulada)){
			throw new EstadoInvalidoException(Constantes.estadoInternoAnulada);
		}
		if(notaCreditoVenta.getEstadoSri().equals(Constantes.estadoSriAutorizada)){
			throw new EstadoInvalidoException(Constantes.estadoSriAutorizada);
		}
		if(notaCreditoVenta.getEstadoSri().equals(Constantes.estadoSriAnulada)){
			throw new EstadoInvalidoException(Constantes.estadoSriAnulada);
		}
		NotaCreditoElectronica notaCreditoElectronica = crear(notaCreditoVenta);
		List<String> estadoRecepcion = recepcion(notaCreditoElectronica, notaCreditoVenta.getEmpresa().getCertificado(), notaCreditoVenta.getEmpresa().getContrasena());
		if(estadoRecepcion.get(0).equals(Constantes.devueltaSri)) {
			throw new FacturaElectronicaInvalidaException("ESTADO DEL SRI:" + Constantes.espacio + estadoRecepcion.get(0) + Constantes.espacio + Constantes.guion + Constantes.espacio + "INFORMACION ADICIONAL: " + estadoRecepcion.get(1));
		}
		List<String> estadoAutorizacion = autorizacion(notaCreditoElectronica);
		if(estadoAutorizacion.get(0).equals(Constantes.devueltaSri)) {
			throw new FacturaElectronicaInvalidaException("ESTADO DEL SRI:" + Constantes.espacio + estadoRecepcion.get(0) + Constantes.espacio + Constantes.guion + Constantes.espacio + "INFORMACION ADICIONAL: " + estadoRecepcion.get(1));
		}
		notaCreditoVenta.setEstadoSri(Constantes.estadoSriAutorizada);
		notaCreditoVenta.setFechaAutorizacion(new Date());
		enviarCorreo(notaCreditoVenta, notaCreditoElectronica);
		NotaCreditoVenta facturada = rep.save(notaCreditoVenta);
		facturada.normalizar();
		return facturada;
	}
    
    private List<String> recepcion(NotaCreditoElectronica notaCreditoElectronica, String certificado, String contrasena) {
    	try {
    		JAXBContext jaxbContext = JAXBContext.newInstance(NotaCreditoElectronica.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, Constantes.utf8);
            jaxbMarshaller.marshal(notaCreditoElectronica, System.out);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(notaCreditoElectronica, sw);
            String xml=sw.toString();
			Path path = Paths.get(Constantes.pathCertificados + Constantes.slash + certificado);
			String ruta = path.toAbsolutePath().toString();
			byte[] cert = ConvertFile.readBytesFromFile(ruta);
            byte[] firmado=SignatureXAdESBES.firmarByteData(xml.getBytes(), cert, Constantes.contrasena);
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

	public List<String> autorizacion(NotaCreditoElectronica notaCreditoElectronica){
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

	public ByteArrayInputStream crearPDF(NotaCreditoVenta notaCreditoVenta) {
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
			if(notaCreditoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getRegimen() != null) {
				regimen = notaCreditoVenta.getSesion().getUsuario().getEstacion().getRegimen().getDescripcion();
			}
			if(notaCreditoVenta.getSesion().getUsuario().getEstacion().getRegimen() != null) {
				regimen = notaCreditoVenta.getSesion().getUsuario().getEstacion().getRegimen().getDescripcion();
			}
			float [] columnas = {320F, 280F};
			Table tabla = new Table(columnas);
			tabla.addCell(getCellEmpresa(notaCreditoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial() +"\n" + "\n" +
					"DIRECCIÓN MATRIZ: " + notaCreditoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getDireccion() +"\n" + "\n" +
					"DIRECCIÓN SUCURSAL: " + notaCreditoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getDireccion() +"\n" + "\n" +
					regimen + "\n" + "\n" +
					"CONTIRUYENTE ESPECIAL: " + notaCreditoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getResolucionEspecial() + "\n" + "\n" +
					"OBLIGADO A LLEVAR CONTABILIDAD: " + notaCreditoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getObligadoContabilidad() + "\n" + "\n" +
					"AGENTE RETENCION RESOLUCIÓN: " + notaCreditoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getResolucionAgente(), TextAlignment.LEFT));
			String numeroAutorizacion = Constantes.vacio;
			String fechaAutorizacion = Constantes.vacio;
			Image imagenCodigoBarras = null;
			if(notaCreditoVenta.getEstadoSri().equals(Constantes.estadoSriAutorizada)){
				numeroAutorizacion = notaCreditoVenta.getClaveAcceso();
				fechaAutorizacion = notaCreditoVenta.getFechaAutorizacion().toString();
				Barcode128 codigoBarras = new Barcode128(pdf);
				codigoBarras.setCodeType(Barcode128.CODE128);
				codigoBarras.setCode(notaCreditoVenta.getClaveAcceso());
				PdfFormXObject objetoCodigoBarras = codigoBarras.createFormXObject(null, null, pdf);
				imagenCodigoBarras = new Image(objetoCodigoBarras);
			}
			tabla.addCell(getCellFactura("RUC: "+ notaCreditoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getIdentificacion()+"\n"+
					"FACTURA"+"\n"+
					"No. " + notaCreditoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getCodigoSRI() + Constantes.guion + notaCreditoVenta.getSesion().getUsuario().getEstacion().getCodigoSRI() + Constantes.guion + notaCreditoVenta.getSecuencial() + "\n" +
					"NÚMERO DE AUTORIZACIÓN: " + numeroAutorizacion+ "\n" +
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
			tablaCliente.addCell(getCellCliente("RAZÓN SOCIAL: "+notaCreditoVenta.getFactura().getCliente().getRazonSocial()+"\n" + "FECHA EMISIÓN: " + notaCreditoVenta.getFecha().toString() + "\n" +
					"DIRECCION: " + notaCreditoVenta.getFactura().getCliente().getDireccion() + "\n", TextAlignment.LEFT));
			tablaCliente.addCell(getCellCliente("IDENTIFICACIÓN: " + notaCreditoVenta.getFactura().getCliente().getIdentificacion() + "\n"+ "GUIA: " + "\t" + "\t"+ "\t" + "\t"+ "\t"+ "\t"+ "\t"+ "\t", TextAlignment.RIGHT));
			documento.add(tablaCliente);
			documento.add( new Paragraph("\n"));
			float [] columnasTablaFacturaDetalle = {100F, 40F, 160F, 100F, 100F, 100F};
			Table tablaFacturaDetalle = new Table(columnasTablaFacturaDetalle);
			tablaFacturaDetalle.addCell(getCellColumnaFactura("CÓDIGO"));
			tablaFacturaDetalle.addCell(getCellColumnaFactura("CANT"));
			tablaFacturaDetalle.addCell(getCellColumnaFactura("DESCRIPCION"));
			tablaFacturaDetalle.addCell(getCellColumnaFactura("PRECIO U"));
			tablaFacturaDetalle.addCell(getCellColumnaFactura("DSCTO"));
			tablaFacturaDetalle.addCell(getCellColumnaFactura("SUBTOTAL"));
			for (int i = 0; i <notaCreditoVenta.getNotaCreditoVentaLineas().size(); i++)
			{
				String precioSinIva = String.format("%.2f", notaCreditoVenta.getNotaCreditoVentaLineas().get(i).getPrecio().getPrecioSinIva());
				String valorDescuentoLinea = String.format("%.2f", notaCreditoVenta.getNotaCreditoVentaLineas().get(i).getValorDescuentoLinea());
				String subtotalConDescuentoLinea = String.format("%.2f", notaCreditoVenta.getNotaCreditoVentaLineas().get(i).getTotalSinDescuentoLinea());

				tablaFacturaDetalle.addCell(getCellFilaFactura(notaCreditoVenta.getNotaCreditoVentaLineas().get(i).getProducto().getCodigo()));
				tablaFacturaDetalle.addCell(getCellFilaFactura(notaCreditoVenta.getNotaCreditoVentaLineas().get(i).getCantidad() + Constantes.vacio));
				tablaFacturaDetalle.addCell(getCellFilaFactura(notaCreditoVenta.getNotaCreditoVentaLineas().get(i).getProducto().getNombre()));
				tablaFacturaDetalle.addCell(getCellFilaFactura("$"+precioSinIva));
				tablaFacturaDetalle.addCell(getCellFilaFactura("$"+valorDescuentoLinea));
				tablaFacturaDetalle.addCell(getCellFilaFactura("$"+subtotalConDescuentoLinea));
			}
			documento.add(tablaFacturaDetalle);
			documento.add( new Paragraph("\n"));
			String subtotal = String.format("%.2f", notaCreditoVenta.getTotalConDescuento());
			String descuento = String.format("%.2f", notaCreditoVenta.getDescuentoTotal());
			String subtotalBase12ConDescuento = String.format("%.2f", notaCreditoVenta.getSubtotalBase12SinDescuento());
			String subtotalBase0ConDescuento = String.format("%.2f", notaCreditoVenta.getSubtotalBase0SinDescuento());
			String iva = String.format("%.2f", notaCreditoVenta.getIvaSinDescuento());
			String totalConDescuento = String.format("%.2f", notaCreditoVenta.getTotalConDescuento());
			float [] columnasTablaFactura = {300F, 300F};
			Table tablaFactura = new Table(columnasTablaFactura);
			tablaFactura.addCell(getCellFilaFactura("SUBTOTAL"));
			tablaFactura.addCell(getCellFilaFactura("$" + subtotal));
			tablaFactura.addCell(getCellFilaFactura("DESCUENTO"));
			tablaFactura.addCell(getCellFilaFactura("$" + descuento));
			tablaFactura.addCell(getCellFilaFactura("SUBTOTAL GRAVADO"));
			tablaFactura.addCell(getCellFilaFactura("$" + subtotalBase12ConDescuento));
			tablaFactura.addCell(getCellFilaFactura("SUBTOTAL NO GRAVADO"));
			tablaFactura.addCell(getCellFilaFactura("$" + subtotalBase0ConDescuento));
			tablaFactura.addCell(getCellFilaFactura("IVA"));
			tablaFactura.addCell(getCellFilaFactura("$" + iva));
			tablaFactura.addCell(getCellFilaFactura("TOTAL"));
			tablaFactura.addCell(getCellFilaFactura("$" + totalConDescuento));
			tablaFactura.setTextAlignment(TextAlignment.RIGHT);
			String telefonoCliente = Constantes.vacio;
			String celularCliente = Constantes.vacio;
			String correoCliente = Constantes.vacio;
			if (!notaCreditoVenta.getFactura().getCliente().getTelefonos().isEmpty()){
				telefonoCliente = notaCreditoVenta.getFactura().getCliente().getTelefonos().get(0).getNumero();
			}
			if (!notaCreditoVenta.getFactura().getCliente().getCelulares().isEmpty()){
				celularCliente = notaCreditoVenta.getFactura().getCliente().getCelulares().get(0).getNumero();
			}
			if (!notaCreditoVenta.getFactura().getCliente().getCorreos().isEmpty()){
				correoCliente = notaCreditoVenta.getFactura().getCliente().getCorreos().get(0).getEmail();
			}
			String comentario = notaCreditoVenta.getComentario();
			float [] columnasAdicional = {150F, 450F};
			Table tablaAdicional = new Table(columnasAdicional);
			tablaAdicional.addCell(getCellAdicional("COMENTARIO"));
			tablaAdicional.addCell(getCellAdicional(comentario));
			tablaAdicional.addCell(getCellAdicional("TELEFONO"));
			tablaAdicional.addCell(getCellAdicional(telefonoCliente));
			tablaAdicional.addCell(getCellAdicional("CELULAR"));
			tablaAdicional.addCell(getCellAdicional(celularCliente));
			tablaAdicional.addCell(getCellAdicional("CORREO"));
			tablaAdicional.addCell(getCellAdicional(correoCliente));
			float [] columnasAdicionalYFactura = {300F, 300F};
			Table tablaAdicionalYFactura = new Table(columnasAdicionalYFactura);
			tablaAdicionalYFactura.addCell(getCellAdicionalYFactura(tablaAdicional));
			tablaAdicionalYFactura.addCell(getCellAdicionalYFactura(tablaFactura));
			tablaAdicionalYFactura.setBorderCollapse(BorderCollapsePropertyValue.SEPARATE);
			tablaAdicionalYFactura.setHorizontalBorderSpacing(3);
			documento.add(tablaAdicionalYFactura);
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
	private Cell getCellAdicional(String text) {
		Paragraph parrafo = new Paragraph(text);
		Cell cell = new Cell();
		cell.add(parrafo);
		cell.setFontSize(Constantes.fontSize10);
		cell.setBorder(new SolidBorder(ColorConstants.BLUE,1));
		return cell;
	}
	private Cell getCellAdicionalYFactura(Table tabla){
		Cell cell = new Cell();
		cell.add(tabla);
		cell.setBorder(Border.NO_BORDER);
		return cell;
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
	        parte1.setFileName(Constantes.nota_credito + notaCreditoVenta.getSecuencial()+Constantes.extensionPdf);
	        MimeBodyPart parte2 = new MimeBodyPart();
	        parte2.setDataHandler(new DataHandler(xmlData));
	        parte2.setFileName(Constantes.nota_credito + notaCreditoVenta.getSecuencial()+Constantes.extensionXml);
	        
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

	@Override
	public ByteArrayInputStream obtenerPDF(long notaCreditoVentaId){
		Optional<NotaCreditoVenta> opcional= rep.findById(notaCreditoVentaId);
		if(opcional.isEmpty()) {
			throw new EntidadNoExistenteException(Constantes.nota_credito_venta);
		}
		NotaCreditoVenta notaCreditoVenta = opcional.get();
		ByteArrayInputStream pdf = crearPDF(notaCreditoVenta);
		return pdf;
	}

	@Override
	public void enviarPDFYXML(long notaCreditoVentaId){
		Optional<NotaCreditoVenta> opcional= rep.findById(notaCreditoVentaId);
		if(opcional.isEmpty()) {
			throw new EntidadNoExistenteException(Constantes.nota_credito_venta);
		}
		NotaCreditoVenta notaCreditoVenta = opcional.get();
		NotaCreditoElectronica notaCreditoElectronica = crear(notaCreditoVenta);
		enviarCorreo(notaCreditoVenta, notaCreditoElectronica);
	}
}
