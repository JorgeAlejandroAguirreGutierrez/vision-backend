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
import com.proyecto.vision.modelos.venta.NotaCredito;
import com.proyecto.vision.modelos.venta.NotaCreditoLinea;
import com.proyecto.vision.modelos.venta.electronico.notacredito.*;
import com.proyecto.vision.repositorios.venta.INotaCreditoRepository;
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
	private INotaCreditoRepository rep;

	@Autowired
	private IEmpresaService empresaService;

	@Value("${prefijo.url.imagenes}")
	private String imagenes;

	@Value("${correo.usuario}")
	private String correoUsuario;

	@Value("${correo.contrasena}")
	private String correoContrasena;

	@Value("${facturacion.produccion}")
	private String facturacionProduccion;

	private NotaCreditoElectronica crear(NotaCredito notaCredito) {
		//MAPEO A FACTURA ELECTRONICA
		NotaCreditoElectronica notaCreditoElectronica = new NotaCreditoElectronica();
		InfoTributaria infoTributaria = new InfoTributaria();
		String ambiente = Constantes.vacio;
		if(facturacionProduccion.equals(Constantes.si)){
			ambiente = Constantes.produccion_sri;
		}
		if(facturacionProduccion.equals(Constantes.no)){
			ambiente = Constantes.pruebas_sri;
		}
		infoTributaria.setAmbiente(ambiente);
		infoTributaria.setTipoEmision(Constantes.emision_normal_sri);
		infoTributaria.setRazonSocial(notaCredito.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial());
		infoTributaria.setNombreComercial(notaCredito.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getNombreComercial());
		infoTributaria.setRuc(notaCredito.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getIdentificacion());
		infoTributaria.setClaveAcceso(notaCredito.getClaveAcceso());
		infoTributaria.setCodDoc(Constantes.nota_de_credito_sri);
		infoTributaria.setEstab(notaCredito.getSesion().getUsuario().getEstacion().getEstablecimiento().getCodigoSRI());
		infoTributaria.setPtoEmi(notaCredito.getSesion().getUsuario().getEstacion().getCodigoSRI());
		infoTributaria.setSecuencial(notaCredito.getSecuencial());
		infoTributaria.setDirMatriz(notaCredito.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getDireccion());

		DateFormat dateFormat = new SimpleDateFormat(Constantes.fechaCortaSri);
		String fechaEmision = dateFormat.format(notaCredito.getFecha());
		InfoNotaCredito infoNotaCredito = new InfoNotaCredito();
		infoNotaCredito.setFechaEmision(fechaEmision);
		infoNotaCredito.setDirEstablecimiento(notaCredito.getSesion().getUsuario().getEstacion().getEstablecimiento().getDireccion());
		infoNotaCredito.setTipoIdentificacionComprador(notaCredito.getFactura().getCliente().getTipoIdentificacion().getCodigoSRI());
		infoNotaCredito.setRazonSocialComprador(notaCredito.getFactura().getCliente().getRazonSocial());
		infoNotaCredito.setIdentificacionComprador(notaCredito.getFactura().getCliente().getIdentificacion());
		infoNotaCredito.setObligadoContabilidad(notaCredito.getFactura().getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getObligadoContabilidad());
		infoNotaCredito.setCodDocModificado(Constantes.factura_sri);
		String numero = notaCredito.getFactura().getSesion().getUsuario().getEstacion().getEstablecimiento().getCodigoSRI()+Constantes.guion+ notaCredito.getFactura().getSesion().getUsuario().getEstacion().getCodigoSRI()+Constantes.guion+ notaCredito.getFactura().getSecuencial();
		infoNotaCredito.setNumDocModificado(numero);
		String fechaEmisionFactura = dateFormat.format(notaCredito.getFactura().getFecha());
		infoNotaCredito.setFechaEmisionDocSustento(fechaEmisionFactura);
		infoNotaCredito.setTotalSinImpuestos(notaCredito.getSubtotal());
		infoNotaCredito.setValorModificacion(notaCredito.getTotal());
		infoNotaCredito.setMoneda(Constantes.moneda);
		infoNotaCredito.setTotalConImpuestos(crearTotalConImpuestos(notaCredito));
		infoNotaCredito.setMotivo(notaCredito.getOperacion());
		Detalles detalles=crearDetalles(notaCredito);
		InfoAdicional infoAdicional = crearInfoAdicional(notaCredito);
		notaCreditoElectronica.setInfoTributaria(infoTributaria);
		notaCreditoElectronica.setInfoNotaCredito(infoNotaCredito);
		notaCreditoElectronica.setDetalles(detalles);
		notaCreditoElectronica.setInfoAdicional(infoAdicional);
		return notaCreditoElectronica;
	}

	private TotalConImpuestos crearTotalConImpuestos(NotaCredito notaCredito){
		TotalConImpuestos totalConImpuestos = new TotalConImpuestos();
		List<TotalImpuesto> totalImpuestos = new ArrayList<>();
		for(NotaCreditoLinea notaCreditoLinea : notaCredito.getNotaCreditoLineas()) {
			TotalImpuesto totalImpuesto = new TotalImpuesto();
			totalImpuesto.setCodigo(Constantes.iva_sri);
			totalImpuesto.setCodigoPorcentaje(notaCreditoLinea.getImpuesto().getCodigoSRI());
			totalImpuesto.setBaseImponible(notaCreditoLinea.getTotalLinea());
			totalImpuesto.setValor(notaCreditoLinea.getImporteIvaLinea());
			totalImpuestos.add(totalImpuesto);
		}
		totalConImpuestos.setTotalImpuesto(totalImpuestos);
		return totalConImpuestos;
	}

	private Detalles crearDetalles(NotaCredito notaCredito) {
		Detalles detalles=new Detalles();
		List<Detalle> detalleLista = new ArrayList<>();
		for(NotaCreditoLinea notaCreditoLinea : notaCredito.getNotaCreditoLineas()) {
			Detalle detalle = new Detalle();
			detalle.setCodigoInterno(notaCreditoLinea.getProducto().getCodigo());
			detalle.setDescripcion(notaCreditoLinea.getProducto().getNombre());
			detalle.setCantidad(notaCreditoLinea.getCantidad());
			detalle.setPrecioUnitario(Math.round(notaCreditoLinea.getCostoUnitario()*100.0)/100.0);
			detalle.setDescuento(notaCreditoLinea.getCostoUnitario());
			detalle.setPrecioTotalSinImpuesto(notaCreditoLinea.getTotalLinea());
			detalle.setImpuestos(crearImpuestos(notaCreditoLinea));
			detalleLista.add(detalle);
		}
		detalles.setDetalle(detalleLista);
		return detalles;
	}

	private Impuestos crearImpuestos(NotaCreditoLinea notaCreditoLinea) {
		Impuestos impuestos=new Impuestos();
		List<Impuesto> impuestoLista = new ArrayList<>();
		Impuesto impuesto=new Impuesto();
		impuesto.setCodigo(Constantes.iva_sri);
		impuesto.setCodigoPorcentaje(notaCreditoLinea.getImpuesto().getCodigoSRI());
		impuesto.setTarifa(notaCreditoLinea.getImpuesto().getPorcentaje());
		impuesto.setBaseImponible(notaCreditoLinea.getSubtotalLinea());
		impuesto.setValor(notaCreditoLinea.getImporteIvaLinea());
		impuestoLista.add(impuesto);
		impuestos.setImpuesto(impuestoLista);
		return impuestos;
	}

	private InfoAdicional crearInfoAdicional(NotaCredito notaCredito) {
		List<CampoAdicional> camposAdicionales = new ArrayList<>();
		if(!notaCredito.getFactura().getCliente().getTelefonos().isEmpty()) {
			String telefono = notaCredito.getFactura().getCliente().getTelefonos().get(0).getNumero();
			CampoAdicional campoAdicional = new CampoAdicional();
			campoAdicional.setNombre(Constantes.telefono);
			campoAdicional.setValor(telefono);
			camposAdicionales.add(campoAdicional);
		}
		if(!notaCredito.getFactura().getCliente().getCelulares().isEmpty()){
			String celular = notaCredito.getFactura().getCliente().getCelulares().get(0).getNumero();
			CampoAdicional campoAdicional = new CampoAdicional();
			campoAdicional.setNombre(Constantes.celular);
			campoAdicional.setValor(celular);
			camposAdicionales.add(campoAdicional);
		}
		if(!notaCredito.getFactura().getCliente().getCorreos().isEmpty()) {
			String correo = notaCredito.getFactura().getCliente().getCorreos().get(0).getEmail();
			CampoAdicional campoAdicional = new CampoAdicional();
			campoAdicional.setNombre(Constantes.correo);
			campoAdicional.setValor(correo);
			camposAdicionales.add(campoAdicional);
		}
		if(notaCredito.getFactura().getCliente().getDireccion().length() > Constantes.cero) {
			String direccion = notaCredito.getFactura().getCliente().getDireccion();
			CampoAdicional campoAdicional = new CampoAdicional();
			campoAdicional.setNombre(Constantes.direccion);
			campoAdicional.setValor(direccion);
			camposAdicionales.add(campoAdicional);
		}
		CampoAdicional campoAdicional = new CampoAdicional();
		campoAdicional.setNombre(Constantes.valor);
		campoAdicional.setValor(notaCredito.getFactura().getTotal() + Constantes.vacio);
		camposAdicionales.add(campoAdicional);
		InfoAdicional infoAdicional = new InfoAdicional();
		infoAdicional.setCampoAdicional(camposAdicionales);
		return infoAdicional;
	}

	@Override
	public NotaCredito enviar(long notaCreditoVentaId) throws MalformedURLException {
		Optional<NotaCredito> opcional= rep.findById(notaCreditoVentaId);
		if(opcional.isEmpty()) {
			throw new EntidadNoExistenteException(Constantes.nota_credito_venta);
		}
		NotaCredito notaCredito = opcional.get();
		Resource certificado = empresaService.bajarCertificado(notaCredito.getEmpresa().getId());
		if(certificado == null){
			throw new CertificadoNoExistenteException();
		}
		if(notaCredito.getEmpresa().getContrasena().equals(Constantes.vacio)){
			throw new FacturaElectronicaInvalidaException(Constantes.contrasena);
		}
		if(notaCredito.getProcesoSRI().equals(Constantes.procesoSRIAutorizada)){
			throw new EstadoInvalidoException(Constantes.procesoSRIAutorizada);
		}
		if(notaCredito.getProcesoSRI().equals(Constantes.procesoSRIAnulada)){
			throw new EstadoInvalidoException(Constantes.procesoSRIAnulada);
		}
		NotaCreditoElectronica notaCreditoElectronica = crear(notaCredito);
		List<String> estadoRecepcion = recepcion(notaCreditoElectronica, notaCredito.getEmpresa().getCertificado(), notaCredito.getEmpresa().getContrasena());
		if(estadoRecepcion.get(0).equals(Constantes.devueltaSri)) {
			throw new FacturaElectronicaInvalidaException("ESTADO DEL SRI:" + Constantes.espacio + estadoRecepcion.get(0) + Constantes.espacio + Constantes.guion + Constantes.espacio + "INFORMACION ADICIONAL: " + estadoRecepcion.get(1));
		}
		List<String> estadoAutorizacion = autorizacion(notaCreditoElectronica);
		if(estadoAutorizacion.get(0).equals(Constantes.devueltaSri)) {
			throw new FacturaElectronicaInvalidaException("ESTADO DEL SRI:" + Constantes.espacio + estadoRecepcion.get(0) + Constantes.espacio + Constantes.guion + Constantes.espacio + "INFORMACION ADICIONAL: " + estadoRecepcion.get(1));
		}
		if(estadoAutorizacion.get(0).equals(Constantes.autorizadoSri)){
			notaCredito.setProcesoSRI(Constantes.procesoSRIAutorizada);
			notaCredito.setFechaAutorizacion(new Date());
			enviarCorreo(notaCredito, notaCreditoElectronica);
		}
		NotaCredito facturada = rep.save(notaCredito);
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

	public ByteArrayInputStream crearPDF(NotaCredito notaCredito) {
		try {
			DateFormat formatoFecha = new SimpleDateFormat(Constantes.fechaYHora);
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
			if(notaCredito.getSesion().getUsuario().getEstacion().getEstablecimiento().getRegimen() != null) {
				regimen = notaCredito.getSesion().getUsuario().getEstacion().getRegimen().getDescripcion();
			}
			if(notaCredito.getSesion().getUsuario().getEstacion().getRegimen() != null) {
				regimen = notaCredito.getSesion().getUsuario().getEstacion().getRegimen().getDescripcion();
			}
			float [] columnas = {320F, 280F};
			Table tabla = new Table(columnas);
			tabla.addCell(getCellEmpresa(notaCredito.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial() +"\n" + "\n" +
					"DIRECCIÓN MATRIZ: " + notaCredito.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getDireccion() +"\n" + "\n" +
					"DIRECCIÓN SUCURSAL: " + notaCredito.getSesion().getUsuario().getEstacion().getEstablecimiento().getDireccion() +"\n" + "\n" +
					regimen + "\n" + "\n" +
					"CONTIRUYENTE ESPECIAL: " + notaCredito.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getResolucionEspecial() + "\n" + "\n" +
					"OBLIGADO A LLEVAR CONTABILIDAD: " + notaCredito.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getObligadoContabilidad() + "\n" + "\n" +
					"AGENTE RETENCION RESOLUCIÓN: " + notaCredito.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getResolucionAgente(), TextAlignment.LEFT));
			String numeroAutorizacion = Constantes.vacio;
			String fechaAutorizacion = Constantes.vacio;
			Image imagenCodigoBarras = null;
			if(notaCredito.getProcesoSRI().equals(Constantes.procesoSRIAutorizada)){
				numeroAutorizacion = notaCredito.getClaveAcceso();
				fechaAutorizacion = formatoFecha.format(notaCredito.getFechaAutorizacion());
				Barcode128 codigoBarras = new Barcode128(pdf);
				codigoBarras.setCodeType(Barcode128.CODE128);
				codigoBarras.setCode(notaCredito.getClaveAcceso());
				PdfFormXObject objetoCodigoBarras = codigoBarras.createFormXObject(null, null, pdf);
				imagenCodigoBarras = new Image(objetoCodigoBarras);
			}
			tabla.addCell(getCellFactura("RUC: "+ notaCredito.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getIdentificacion()+"\n"+
					"NOTA CREDITO"+"\n"+
					"No. " + notaCredito.getNumeroComprobante() + "\n" +
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
			tablaCliente.addCell(getCellCliente("RAZÓN SOCIAL: "+ notaCredito.getFactura().getCliente().getRazonSocial()+"\n" + "FECHA EMISIÓN: " + formatoFecha.format(notaCredito.getFecha()) + "\n" +
					"DIRECCION: " + notaCredito.getFactura().getCliente().getDireccion() + "\n", TextAlignment.LEFT));
			tablaCliente.addCell(getCellCliente("IDENTIFICACIÓN: " + notaCredito.getFactura().getCliente().getIdentificacion() + "\n"+ "GUIA: " + "\t" + "\t"+ "\t" + "\t"+ "\t"+ "\t"+ "\t"+ "\t", TextAlignment.RIGHT));
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
			for (int i = 0; i < notaCredito.getNotaCreditoLineas().size(); i++)
			{
				String precioSinIva = String.format("%.2f", notaCredito.getNotaCreditoLineas().get(i).getPrecio().getPrecioSinIva());
				String valorDescuentoLinea = String.format("%.2f", notaCredito.getNotaCreditoLineas().get(i).getCostoUnitario());
				String subtotalConDescuentoLinea = String.format("%.2f", notaCredito.getNotaCreditoLineas().get(i).getSubtotalLinea());

				tablaFacturaDetalle.addCell(getCellFilaFactura(notaCredito.getNotaCreditoLineas().get(i).getProducto().getCodigo()));
				tablaFacturaDetalle.addCell(getCellFilaFactura(notaCredito.getNotaCreditoLineas().get(i).getCantidad() + Constantes.vacio));
				tablaFacturaDetalle.addCell(getCellFilaFactura(notaCredito.getNotaCreditoLineas().get(i).getProducto().getNombre()));
				tablaFacturaDetalle.addCell(getCellFilaFactura("$"+precioSinIva));
				tablaFacturaDetalle.addCell(getCellFilaFactura("$"+valorDescuentoLinea));
				tablaFacturaDetalle.addCell(getCellFilaFactura("$"+subtotalConDescuentoLinea));
			}
			documento.add(tablaFacturaDetalle);
			documento.add( new Paragraph("\n"));
			String subtotal = String.format("%.2f", notaCredito.getSubtotal());
			String descuento = String.format("%.2f", notaCredito.getDescuento());
			String subtotalGravado = String.format("%.2f", notaCredito.getSubtotalGravado());
			String subtotalNoGravado = String.format("%.2f", notaCredito.getSubtotalNoGravado());
			String iva = String.format("%.2f", notaCredito.getImporteIva());
			String totalConDescuento = String.format("%.2f", notaCredito.getTotal());
			float [] columnasTablaFactura = {300F, 300F};
			Table tablaFactura = new Table(columnasTablaFactura);
			tablaFactura.addCell(getCellFilaFactura("SUBTOTAL"));
			tablaFactura.addCell(getCellFilaFactura("$" + subtotal));
			tablaFactura.addCell(getCellFilaFactura("DESCUENTO"));
			tablaFactura.addCell(getCellFilaFactura("$" + descuento));
			tablaFactura.addCell(getCellFilaFactura("SUBTOTAL GRAVADO"));
			tablaFactura.addCell(getCellFilaFactura("$" + subtotalGravado));
			tablaFactura.addCell(getCellFilaFactura("SUBTOTAL NO GRAVADO"));
			tablaFactura.addCell(getCellFilaFactura("$" + subtotalNoGravado));
			tablaFactura.addCell(getCellFilaFactura("IVA"));
			tablaFactura.addCell(getCellFilaFactura("$" + iva));
			tablaFactura.addCell(getCellFilaFactura("TOTAL"));
			tablaFactura.addCell(getCellFilaFactura("$" + totalConDescuento));
			tablaFactura.setTextAlignment(TextAlignment.RIGHT);
			String telefonoCliente = Constantes.vacio;
			String celularCliente = Constantes.vacio;
			String correoCliente = Constantes.vacio;
			if (!notaCredito.getFactura().getCliente().getTelefonos().isEmpty()){
				telefonoCliente = notaCredito.getFactura().getCliente().getTelefonos().get(0).getNumero();
			}
			if (!notaCredito.getFactura().getCliente().getCelulares().isEmpty()){
				celularCliente = notaCredito.getFactura().getCliente().getCelulares().get(0).getNumero();
			}
			if (!notaCredito.getFactura().getCliente().getCorreos().isEmpty()){
				correoCliente = notaCredito.getFactura().getCliente().getCorreos().get(0).getEmail();
			}
			String comentario = notaCredito.getComentario();
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

	private void enviarCorreo(NotaCredito notaCredito, NotaCreditoElectronica notaCreditoElectronica) {
		try {
			ByteArrayInputStream pdf = crearPDF(notaCredito);
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
			parte1.setFileName(Constantes.nota_credito + notaCredito.getSecuencial()+Constantes.extensionPdf);
			MimeBodyPart parte2 = new MimeBodyPart();
			parte2.setDataHandler(new DataHandler(xmlData));
			parte2.setFileName(Constantes.nota_credito + notaCredito.getSecuencial()+Constantes.extensionXml);

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(parte1);
			multipart.addBodyPart(parte2);

			message.setFrom(new InternetAddress(correoUsuario));
			message.addRecipients(Message.RecipientType.TO, notaCredito.getFactura().getCliente().getCorreos().get(0).getEmail());   //Se podrían añadir varios de la misma manera
			message.setSubject(notaCredito.getFactura().getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial()+ Constantes.mensajeCorreo + notaCredito.getCodigo());
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
		Optional<NotaCredito> opcional= rep.findById(notaCreditoVentaId);
		if(opcional.isEmpty()) {
			throw new EntidadNoExistenteException(Constantes.nota_credito_venta);
		}
		NotaCredito notaCredito = opcional.get();
		ByteArrayInputStream pdf = crearPDF(notaCredito);
		return pdf;
	}

	@Override
	public void enviarPDFYXML(long notaCreditoVentaId){
		Optional<NotaCredito> opcional= rep.findById(notaCreditoVentaId);
		if(opcional.isEmpty()) {
			throw new EntidadNoExistenteException(Constantes.nota_credito_venta);
		}
		NotaCredito notaCredito = opcional.get();
		NotaCreditoElectronica notaCreditoElectronica = crear(notaCredito);
		enviarCorreo(notaCredito, notaCreditoElectronica);
	}
}