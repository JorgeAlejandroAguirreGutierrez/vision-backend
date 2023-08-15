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
import com.proyecto.vision.modelos.venta.NotaCreditoVenta;
import com.proyecto.vision.modelos.venta.NotaDebitoVenta;
import com.proyecto.vision.modelos.venta.NotaDebitoVentaLinea;
import com.proyecto.vision.modelos.venta.electronico.notacredito.NotaCreditoElectronica;
import com.proyecto.vision.modelos.venta.electronico.notadebito.*;
import com.proyecto.vision.modelos.recaudacion.*;
import com.proyecto.vision.repositorios.venta.INotaDebitoVentaRepository;
import com.proyecto.vision.servicios.interf.usuario.IEmpresaService;
import com.proyecto.vision.servicios.interf.venta.INotaDebitoElectronicaService;
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
public class NotaDebitoElectronicaService implements INotaDebitoElectronicaService {
	@Autowired
	private INotaDebitoVentaRepository rep;

	@Autowired
	private IEmpresaService empresaService;

	@Value("${correo.usuario}")
	private String correoUsuario;

	@Value("${correo.contrasena}")
	private String correoContrasena;


	private NotaDebitoElectronica crear(NotaDebitoVenta notaDebitoVenta) {
		//MAPEO A FACTURA ELECTRONICA
		NotaDebitoElectronica notaDebitoElectronica = new NotaDebitoElectronica();
		InfoTributaria infoTributaria = new InfoTributaria();

		infoTributaria.setAmbiente(Constantes.pruebas_sri);
		infoTributaria.setTipoEmision(Constantes.emision_normal_sri);
		infoTributaria.setRazonSocial(notaDebitoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial());
		infoTributaria.setNombreComercial(notaDebitoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getNombreComercial());
		infoTributaria.setRuc(notaDebitoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getIdentificacion());
		infoTributaria.setClaveAcceso(notaDebitoVenta.getClaveAcceso());
		infoTributaria.setCodDoc(Constantes.nota_de_debito_sri);
		infoTributaria.setEstab(notaDebitoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getCodigoSRI());
		infoTributaria.setPtoEmi(notaDebitoVenta.getSesion().getUsuario().getEstacion().getCodigoSRI());
		infoTributaria.setSecuencial(notaDebitoVenta.getSecuencial());
		infoTributaria.setDirMatriz(notaDebitoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getDireccion());

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String fechaEmision = dateFormat.format(notaDebitoVenta.getFecha());
		InfoNotaDebito infoNotaDebito = new InfoNotaDebito();
		infoNotaDebito.setFechaEmision(fechaEmision);
		infoNotaDebito.setDirEstablecimiento(notaDebitoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getDireccion());
		infoNotaDebito.setTipoIdentificacionComprador(notaDebitoVenta.getFactura().getCliente().getTipoIdentificacion().getCodigoSRI());
		infoNotaDebito.setRazonSocialComprador(notaDebitoVenta.getFactura().getCliente().getRazonSocial());
		infoNotaDebito.setIdentificacionComprador(notaDebitoVenta.getFactura().getCliente().getIdentificacion());
		infoNotaDebito.setObligadoContabilidad(notaDebitoVenta.getFactura().getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getObligadoContabilidad());
		infoNotaDebito.setCodDocModificado(Constantes.factura_sri);
		String numero = notaDebitoVenta.getFactura().getSesion().getUsuario().getEstacion().getEstablecimiento().getCodigoSRI() + "-" + notaDebitoVenta.getFactura().getSesion().getUsuario().getEstacion().getCodigoSRI() + "-" + notaDebitoVenta.getFactura().getSecuencial();
		infoNotaDebito.setNumDocModificado(numero);
		String fechaEmisionFactura = dateFormat.format(notaDebitoVenta.getFactura().getFecha());
		infoNotaDebito.setFechaEmisionDocSustento(fechaEmisionFactura);
		infoNotaDebito.setTotalSinImpuestos(notaDebitoVenta.getSubtotal());
		Impuestos impuestos = crearImpuestos(notaDebitoVenta);
		infoNotaDebito.setValorTotal(notaDebitoVenta.getTotal());
		Pagos pagos = crearPagos(notaDebitoVenta);
		Motivos motivos = crearMotivos(notaDebitoVenta);

		InfoAdicional infoAdicional = crearInfoAdicional(notaDebitoVenta);

		notaDebitoElectronica.setInfoTributaria(infoTributaria);
		notaDebitoElectronica.setInfoNotaDebito(infoNotaDebito);
		notaDebitoElectronica.getInfoNotaDebito().setImpuestos(impuestos);
		notaDebitoElectronica.getInfoNotaDebito().setPagos(pagos);
		notaDebitoElectronica.setMotivos(motivos);
		notaDebitoElectronica.setInfoAdicional(infoAdicional);
		return notaDebitoElectronica;
	}

	private Impuestos crearImpuestos(NotaDebitoVenta notaDebitoVenta) {
		Impuestos impuestos=new Impuestos();
		List<Impuesto> impuestoLista = new ArrayList<>();
		for(NotaDebitoVentaLinea notaDebitoVentaLinea: notaDebitoVenta.getNotaDebitoVentaLineas()) {
			Impuesto impuesto=new Impuesto();
			impuesto.setCodigo(Constantes.iva_sri);
			impuesto.setCodigoPorcentaje(notaDebitoVentaLinea.getImpuesto().getCodigoSRI());
			impuesto.setTarifa(notaDebitoVentaLinea.getImpuesto().getPorcentaje());
			impuesto.setBaseImponible(notaDebitoVentaLinea.getTotalLinea());
			impuesto.setValor(notaDebitoVentaLinea.getImporteIvaLinea());
			impuestoLista.add(impuesto);
		}
		impuestos.setImpuesto(impuestoLista);
		return impuestos;
	}

	private Pagos crearPagos(NotaDebitoVenta notaDebitoVenta) {
		Pagos pagos = new Pagos();
		List<Pago> pagosLista = new ArrayList<>();
		if(notaDebitoVenta.getEfectivo() > 0) {
			Pago pago = new Pago();
			pago.setFormaPago(Constantes.sin_utilizacion_del_sistema_financiero);
			pago.setTotal(notaDebitoVenta.getEfectivo());
			pagosLista.add(pago);
		}

		for(NotaDebitoVentaCheque cheque: notaDebitoVenta.getCheques()) {
			Pago pago = new Pago();
			pago.setFormaPago(Constantes.otros_con_utilizacion_sistema_financiero);
			pago.setTotal(cheque.getValor());
			pagosLista.add(pago);
		}

		for(NotaDebitoVentaDeposito deposito: notaDebitoVenta.getDepositos()) {
			Pago pago = new Pago();
			pago.setFormaPago(Constantes.otros_con_utilizacion_sistema_financiero);
			pago.setTotal(deposito.getValor());
			pagosLista.add(pago);
		}

		for(NotaDebitoVentaTransferencia transferencia: notaDebitoVenta.getTransferencias()) {
			Pago pago = new Pago();
			pago.setFormaPago(Constantes.otros_con_utilizacion_sistema_financiero);
			pago.setTotal(transferencia.getValor());
			pagosLista.add(pago);
		}

		for(NotaDebitoVentaTarjetaDebito tarjetaDebito: notaDebitoVenta.getTarjetasDebitos()) {
			Pago pago = new Pago();
			pago.setFormaPago(Constantes.tarjeta_de_debito);
			pago.setTotal(tarjetaDebito.getValor());
			pagosLista.add(pago);
		}

		for(NotaDebitoVentaTarjetaCredito tarjetaCredito: notaDebitoVenta.getTarjetasCreditos()) {
			Pago pago = new Pago();
			pago.setFormaPago(Constantes.tarjeta_de_credito);
			pago.setTotal(tarjetaCredito.getValor());
			pagosLista.add(pago);
		}
		if(notaDebitoVenta.getCredito()!= null && notaDebitoVenta.getCredito().getSaldo() > Constantes.cero) {
			Pago pago = new Pago();
			pago.setFormaPago(Constantes.otros_con_utilizacion_sistema_financiero);
			pago.setTotal(notaDebitoVenta.getCredito().getSaldo());
			pago.setUnidadTiempo(notaDebitoVenta.getCredito().getUnidadTiempo());
			pago.setPlazo(notaDebitoVenta.getCredito().getPlazo());
			pagosLista.add(pago);
		}
		pagos.setPago(pagosLista);
		return pagos;
	}
	private Motivos crearMotivos(NotaDebitoVenta notaDebitoVenta) {
		Motivos motivos=new Motivos();
		List<Motivo> motivoLista = new ArrayList<>();
		for(NotaDebitoVentaLinea notaDebitoVentaLinea: notaDebitoVenta.getNotaDebitoVentaLineas()) {
			Motivo motivo = new Motivo();
			motivo.setRazon(notaDebitoVentaLinea.getProducto().getNombre());
			motivo.setValor(notaDebitoVentaLinea.getTotalLinea());
			motivoLista.add(motivo);
		}
		motivos.setMotivo(motivoLista);
		return motivos;
	}

	private InfoAdicional crearInfoAdicional(NotaDebitoVenta notaDebitoVenta) {
		List<CampoAdicional> camposAdicionales = new ArrayList<>();
		if(!notaDebitoVenta.getFactura().getCliente().getTelefonos().isEmpty()) {
			String telefono = notaDebitoVenta.getFactura().getCliente().getTelefonos().get(0).getNumero();
			CampoAdicional campoAdicional = new CampoAdicional();
			campoAdicional.setNombre(Constantes.telefono);
			campoAdicional.setValor(telefono);
			camposAdicionales.add(campoAdicional);
		}
		if(!notaDebitoVenta.getFactura().getCliente().getTelefonos().isEmpty()){
			String celular = notaDebitoVenta.getFactura().getCliente().getTelefonos().get(0).getNumero();
			CampoAdicional campoAdicional = new CampoAdicional();
			campoAdicional.setNombre(Constantes.celular);
			campoAdicional.setValor(celular);
			camposAdicionales.add(campoAdicional);
		}
		if(!notaDebitoVenta.getFactura().getCliente().getCorreos().isEmpty()) {
			String correo = notaDebitoVenta.getFactura().getCliente().getCorreos().get(0).getEmail();
			CampoAdicional campoAdicional = new CampoAdicional();
			campoAdicional.setNombre(Constantes.correo);
			campoAdicional.setValor(correo);
			camposAdicionales.add(campoAdicional);
		}
		if(notaDebitoVenta.getFactura().getCliente().getDireccion().length() > Constantes.cero) {
			String direccion = notaDebitoVenta.getFactura().getCliente().getDireccion();
			CampoAdicional campoAdicional = new CampoAdicional();
			campoAdicional.setNombre(Constantes.direccion);
			campoAdicional.setValor(direccion);
			camposAdicionales.add(campoAdicional);
		}
		CampoAdicional campoAdicional = new CampoAdicional();
		campoAdicional.setNombre(Constantes.valor);
		campoAdicional.setValor(notaDebitoVenta.getFactura().getTotal() + Constantes.vacio);
		camposAdicionales.add(campoAdicional);
		InfoAdicional infoAdicional = new InfoAdicional();
		infoAdicional.setCampoAdicional(camposAdicionales);
		return infoAdicional;
	}

	@Override
	public NotaDebitoVenta enviar(long notaDebitoVentaId) throws MalformedURLException {
		Optional<NotaDebitoVenta> opcional= rep.findById(notaDebitoVentaId);
		if(opcional.isEmpty()) {
			throw new EntidadNoExistenteException(Constantes.nota_debito_venta);
		}
		NotaDebitoVenta notaDebitoVenta = opcional.get();
		Resource certificado = empresaService.bajarCertificado(notaDebitoVenta.getEmpresa().getId());
		if(certificado == null){
			throw new CertificadoNoExistenteException();
		}
		if(notaDebitoVenta.getEmpresa().getContrasena().equals(Constantes.vacio)){
			throw new FacturaElectronicaInvalidaException(Constantes.contrasena);
		}
		if(notaDebitoVenta.getEstadoInterno().equals(Constantes.estadoInternoEmitida)){
			throw new EstadoInvalidoException(Constantes.estadoInternoEmitida);
		}
		if(notaDebitoVenta.getEstadoInterno().equals(Constantes.estadoInternoAnulada)){
			throw new EstadoInvalidoException(Constantes.estadoInternoAnulada);
		}
		if(notaDebitoVenta.getEstadoSri().equals(Constantes.estadoSriAutorizada)){
			throw new EstadoInvalidoException(Constantes.estadoSriAutorizada);
		}
		if(notaDebitoVenta.getEstadoSri().equals(Constantes.estadoSriAnulada)){
			throw new EstadoInvalidoException(Constantes.estadoSriAnulada);
		}
		NotaDebitoElectronica notaDebitoElectronica = crear(notaDebitoVenta);
		List<String> estadoRecepcion = recepcion(notaDebitoElectronica, notaDebitoVenta.getEmpresa().getCertificado(), notaDebitoVenta.getEmpresa().getContrasena());
		if(estadoRecepcion.get(0).equals(Constantes.devueltaSri)) {
			throw new FacturaElectronicaInvalidaException("ESTADO DEL SRI:" + Constantes.espacio + estadoRecepcion.get(0) + Constantes.espacio + Constantes.guion + Constantes.espacio + "INFORMACION ADICIONAL: " + estadoRecepcion.get(1));
		}
		List<String> estadoAutorizacion = autorizacion(notaDebitoElectronica);
		if(estadoAutorizacion.get(0).equals(Constantes.devueltaSri)) {
			throw new FacturaElectronicaInvalidaException("ESTADO DEL SRI:" + Constantes.espacio + estadoRecepcion.get(0) + Constantes.espacio + Constantes.guion + Constantes.espacio + "INFORMACION ADICIONAL: " + estadoRecepcion.get(1));
		}
		notaDebitoVenta.setEstadoSri(Constantes.estadoSriAutorizada);
		notaDebitoVenta.setFechaAutorizacion(new Date());
		enviarCorreo(notaDebitoVenta, notaDebitoElectronica);
		NotaDebitoVenta facturada = rep.save(notaDebitoVenta);
		facturada.normalizar();
		return facturada;
	}

	private List<String> recepcion(NotaDebitoElectronica notaDebitoElectronica, String certificado, String contrasena) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(NotaDebitoElectronica.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, Constantes.utf8);
			jaxbMarshaller.marshal(notaDebitoElectronica, System.out);
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(notaDebitoElectronica, sw);
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

	public List<String> autorizacion(NotaDebitoElectronica notaDebitoElectronica){
		try {
			String body=Util.soapConsultaFacturacionEletronica(notaDebitoElectronica.getInfoTributaria().getClaveAcceso());
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

	public ByteArrayInputStream crearPDF(NotaDebitoVenta notaDebitoVenta) {
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
			if(notaDebitoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getRegimen() != null) {
				regimen = notaDebitoVenta.getSesion().getUsuario().getEstacion().getRegimen().getDescripcion();
			}
			if(notaDebitoVenta.getSesion().getUsuario().getEstacion().getRegimen() != null) {
				regimen = notaDebitoVenta.getSesion().getUsuario().getEstacion().getRegimen().getDescripcion();
			}
			float [] columnas = {320F, 280F};
			Table tabla = new Table(columnas);
			tabla.addCell(getCellEmpresa(notaDebitoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial() +"\n" + "\n" +
					"DIRECCIÓN MATRIZ: " + notaDebitoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getDireccion() +"\n" + "\n" +
					"DIRECCIÓN SUCURSAL: " + notaDebitoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getDireccion() +"\n" + "\n" +
					regimen + "\n" + "\n" +
					"CONTIRUYENTE ESPECIAL: " + notaDebitoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getResolucionEspecial() + "\n" + "\n" +
					"OBLIGADO A LLEVAR CONTABILIDAD: " + notaDebitoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getObligadoContabilidad() + "\n" + "\n" +
					"AGENTE RETENCION RESOLUCIÓN: " + notaDebitoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getResolucionAgente(), TextAlignment.LEFT));
			String numeroAutorizacion = Constantes.vacio;
			String fechaAutorizacion = Constantes.vacio;
			Image imagenCodigoBarras = null;
			if(notaDebitoVenta.getEstadoSri().equals(Constantes.estadoSriAutorizada)) {
				numeroAutorizacion = notaDebitoVenta.getClaveAcceso();
				fechaAutorizacion = notaDebitoVenta.getFechaAutorizacion().toString();
				Barcode128 codigoBarras = new Barcode128(pdf);
				codigoBarras.setCodeType(Barcode128.CODE128);
				codigoBarras.setCode(notaDebitoVenta.getClaveAcceso());
				PdfFormXObject objetoCodigoBarras = codigoBarras.createFormXObject(null, null, pdf);
				imagenCodigoBarras = new Image(objetoCodigoBarras);
			}
			tabla.addCell(getCellFactura("RUC: " + notaDebitoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getIdentificacion()+"\n"+
					"FACTURA"+"\n"+
					"No. " + notaDebitoVenta.getSesion().getUsuario().getEstacion().getEstablecimiento().getCodigoSRI() + Constantes.guion + notaDebitoVenta.getSesion().getUsuario().getEstacion().getCodigoSRI() + Constantes.guion + notaDebitoVenta.getSecuencial() + "\n" +
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
			tablaCliente.addCell(getCellCliente("RAZÓN SOCIAL: " + notaDebitoVenta.getFactura().getCliente().getRazonSocial()+"\n" + "FECHA EMISIÓN: " + notaDebitoVenta.getFecha().toString() + "\n" +
					"DIRECCION: " + notaDebitoVenta.getFactura().getCliente().getDireccion() + "\n", TextAlignment.LEFT));
			tablaCliente.addCell(getCellCliente("IDENTIFICACIÓN: " + notaDebitoVenta.getFactura().getCliente().getIdentificacion() + "\n"+ "GUIA: " + "\t" + "\t"+ "\t" + "\t"+ "\t"+ "\t"+ "\t"+ "\t", TextAlignment.RIGHT));
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
			for (int i = 0; i <notaDebitoVenta.getNotaDebitoVentaLineas().size(); i++)
			{
				String precioSinIva = String.format("%.2f", notaDebitoVenta.getNotaDebitoVentaLineas().get(i).getPrecio().getPrecioSinIva());
				String valorDescuentoLinea = String.format("%.2f", notaDebitoVenta.getNotaDebitoVentaLineas().get(i).getValorDescuentoLinea());
				String subtotalConDescuentoLinea = String.format("%.2f", notaDebitoVenta.getNotaDebitoVentaLineas().get(i).getTotalLinea());

				tablaFacturaDetalle.addCell(getCellFilaFactura(notaDebitoVenta.getNotaDebitoVentaLineas().get(i).getProducto().getCodigo()));
				tablaFacturaDetalle.addCell(getCellFilaFactura(notaDebitoVenta.getNotaDebitoVentaLineas().get(i).getCantidad() + Constantes.vacio));
				tablaFacturaDetalle.addCell(getCellFilaFactura(notaDebitoVenta.getNotaDebitoVentaLineas().get(i).getProducto().getNombre()));
				tablaFacturaDetalle.addCell(getCellFilaFactura("$"+precioSinIva));
				tablaFacturaDetalle.addCell(getCellFilaFactura("$"+valorDescuentoLinea));
				tablaFacturaDetalle.addCell(getCellFilaFactura("$"+subtotalConDescuentoLinea));
			}
			documento.add(tablaFacturaDetalle);
			documento.add( new Paragraph("\n"));
			String subtotal = String.format("%.2f", notaDebitoVenta.getSubtotal());
			String descuento = String.format("%.2f", notaDebitoVenta.getDescuento());
			String subtotalBase12ConDescuento = String.format("%.2f", notaDebitoVenta.getSubtotalGravado());
			String subtotalBase0ConDescuento = String.format("%.2f", notaDebitoVenta.getSubtotalNoGravado());
			String iva = String.format("%.2f", notaDebitoVenta.getImporteIva());
			String totalConDescuento = String.format("%.2f", notaDebitoVenta.getTotal());
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
			if (!notaDebitoVenta.getFactura().getCliente().getTelefonos().isEmpty()){
				telefonoCliente = notaDebitoVenta.getFactura().getCliente().getTelefonos().get(0).getNumero();
			}
			if (!notaDebitoVenta.getFactura().getCliente().getCelulares().isEmpty()){
				celularCliente = notaDebitoVenta.getFactura().getCliente().getCelulares().get(0).getNumero();
			}
			if (!notaDebitoVenta.getFactura().getCliente().getCorreos().isEmpty()){
				correoCliente = notaDebitoVenta.getFactura().getCliente().getCorreos().get(0).getEmail();
			}
			String comentario = notaDebitoVenta.getComentario();
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
			float [] columnasTablaFormaPago = {200F, 100F};
			Table tablaFormaPago = new Table(columnasTablaFormaPago);
			tablaFormaPago.addCell(getCellFormaPago("FORMA DE PAGO"));
			tablaFormaPago.addCell(getCellFormaPago("VALOR"));
			if(notaDebitoVenta.getEfectivo()>0) {
				tablaFormaPago.addCell(getCellFormaPago(Constantes.sin_utilizacion_del_sistema_financiero + Constantes.espacio + Constantes.guion + Constantes.espacio + Constantes.texto_sin_utilizacion_del_sistema_financiero));
				String valor = String.format("%.2f", notaDebitoVenta.getEfectivo());
				tablaFormaPago.addCell(getCellFormaPago(valor));
			}
			for(NotaDebitoVentaCheque cheque: notaDebitoVenta.getCheques()) {
				tablaFormaPago.addCell(getCellFormaPago(Constantes.otros_con_utilizacion_sistema_financiero + Constantes.espacio + Constantes.guion + Constantes.espacio + Constantes.texto_otros_con_utilizacion_sistema_financiero));
				String valor = String.format("%.2f", cheque.getValor());
				tablaFormaPago.addCell(getCellFormaPago(valor));
			}
			for(NotaDebitoVentaDeposito deposito: notaDebitoVenta.getDepositos()) {
				tablaFormaPago.addCell(getCellFormaPago(Constantes.otros_con_utilizacion_sistema_financiero + Constantes.espacio + Constantes.guion + Constantes.espacio + Constantes.texto_otros_con_utilizacion_sistema_financiero));
				String valor = String.format("%.2f", deposito.getValor());
				tablaFormaPago.addCell(getCellFormaPago(valor));
			}
			for(NotaDebitoVentaTransferencia transferencia: notaDebitoVenta.getTransferencias()) {
				tablaFormaPago.addCell(getCellFormaPago(Constantes.otros_con_utilizacion_sistema_financiero + Constantes.espacio + Constantes.guion + Constantes.espacio + Constantes.texto_otros_con_utilizacion_sistema_financiero));
				String valor = String.format("%.2f", transferencia.getValor());
				tablaFormaPago.addCell(getCellFormaPago(valor));
			}
			for(NotaDebitoVentaTarjetaDebito tarjetaDebito: notaDebitoVenta.getTarjetasDebitos()) {
				tablaFormaPago.addCell(getCellFormaPago(Constantes.tarjeta_de_debito + Constantes.espacio + Constantes.guion + Constantes.espacio + Constantes.texto_tarjeta_de_debito));
				String valor = String.format("%.2f", tarjetaDebito.getValor());
				tablaFormaPago.addCell(getCellFormaPago(valor));
			}
			for(NotaDebitoVentaTarjetaCredito tarjetaCredito: notaDebitoVenta.getTarjetasCreditos()) {
				tablaFormaPago.addCell(getCellFormaPago(Constantes.tarjeta_de_credito + Constantes.espacio + Constantes.guion + Constantes.espacio + Constantes.texto_tarjeta_de_credito));
				String valor = String.format("%.2f", tarjetaCredito.getValor());
				tablaFormaPago.addCell(getCellFormaPago(valor));
			}
			if(notaDebitoVenta.getCredito()!= null && notaDebitoVenta.getCredito().getSaldo() > Constantes.cero) {
				tablaFormaPago.addCell(getCellFormaPago(Constantes.otros_con_utilizacion_sistema_financiero + Constantes.espacio + Constantes.guion + Constantes.espacio + Constantes.texto_otros_con_utilizacion_sistema_financiero));
				String valor = String.format("%.2f", notaDebitoVenta.getCredito().getSaldo());
				tablaFormaPago.addCell(getCellFormaPago(valor));
			}
			tablaFormaPago.setHorizontalAlignment(HorizontalAlignment.LEFT);
			documento.add(tablaFormaPago);
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

	private Cell getCellFormaPago(String text) {
		Paragraph parrafo = new Paragraph(text);
		Cell cell = new Cell();
		cell.add(parrafo);
		cell.setFontSize(Constantes.fontSize10);
		cell.setBorder(new SolidBorder(ColorConstants.BLUE,1));
		return cell;
	}

	private ByteArrayInputStream crearXML(NotaDebitoElectronica notaDebitoElectronica) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(NotaDebitoElectronica.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, Constantes.utf8);
			jaxbMarshaller.marshal(notaDebitoElectronica, System.out);
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(notaDebitoElectronica, sw);
			String xml=sw.toString();
			return new ByteArrayInputStream(xml.getBytes());
		} catch(Exception e) {
			return null;
		}
	}

	private void enviarCorreo(NotaDebitoVenta notaDebitoVenta, NotaDebitoElectronica notaDebitoElectronica) {
		try {
			ByteArrayInputStream pdf = crearPDF(notaDebitoVenta);
			ByteArrayInputStream xml = crearXML(notaDebitoElectronica);
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
			parte1.setFileName(Constantes.nota_credito + notaDebitoVenta.getSecuencial()+Constantes.extensionPdf);
			MimeBodyPart parte2 = new MimeBodyPart();
			parte2.setDataHandler(new DataHandler(xmlData));
			parte2.setFileName(Constantes.nota_credito + notaDebitoVenta.getSecuencial()+Constantes.extensionXml);

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

	@Override
	public ByteArrayInputStream obtenerPDF(long notaDebitoVentaId){
		Optional<NotaDebitoVenta> opcional= rep.findById(notaDebitoVentaId);
		if(opcional.isEmpty()) {
			throw new EntidadNoExistenteException(Constantes.nota_debito_venta);
		}
		NotaDebitoVenta notaDebitoVenta = opcional.get();
		ByteArrayInputStream pdf = crearPDF(notaDebitoVenta);
		return pdf;
	}

	@Override
	public void enviarPDFYXML(long notaDebitoVentaId){
		Optional<NotaDebitoVenta> opcional= rep.findById(notaDebitoVentaId);
		if(opcional.isEmpty()) {
			throw new EntidadNoExistenteException(Constantes.nota_debito_venta);
		}
		NotaDebitoVenta notaDebitoVenta = opcional.get();
		NotaDebitoElectronica notaDebitoElectronica = crear(notaDebitoVenta);
		enviarCorreo(notaDebitoVenta, notaDebitoElectronica);
	}
}