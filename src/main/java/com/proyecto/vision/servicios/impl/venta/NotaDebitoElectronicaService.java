package com.proyecto.vision.servicios.impl.venta;

import ayungan.com.signature.ConvertFile;
import ayungan.com.signature.SignatureXAdESBES;
import com.itextpdf.barcodes.Barcode128;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
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
import com.itextpdf.layout.properties.BorderCollapsePropertyValue;
import com.itextpdf.layout.properties.BorderRadius;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.*;
import com.proyecto.vision.modelos.venta.NotaDebito;
import com.proyecto.vision.modelos.venta.NotaDebitoLinea;
import com.proyecto.vision.modelos.venta.electronico.notadebito.*;
import com.proyecto.vision.modelos.recaudacion.*;
import com.proyecto.vision.repositorios.venta.INotaDebitoRepository;
import com.proyecto.vision.servicios.interf.acceso.IEmpresaService;
import com.proyecto.vision.servicios.interf.acceso.ISuscripcionService;
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
	private INotaDebitoRepository rep;

	@Autowired
	private IEmpresaService empresaService;

	@Autowired
	private ISuscripcionService suscripcionService;

	@Value("${correo.usuario}")
	private String correoUsuario;

	@Value("${correo.contrasena}")
	private String correoContrasena;

	@Value("${facturacion.produccion}")
	private String facturacionProduccion;

	private NotaDebitoElectronica crear(NotaDebito notaDebito) {
		//MAPEO A FACTURA ELECTRONICA
		NotaDebitoElectronica notaDebitoElectronica = new NotaDebitoElectronica();
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
		infoTributaria.setRazonSocial(notaDebito.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial());
		infoTributaria.setNombreComercial(notaDebito.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getNombreComercial());
		infoTributaria.setRuc(notaDebito.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getIdentificacion());
		infoTributaria.setClaveAcceso(notaDebito.getClaveAcceso());
		infoTributaria.setCodDoc(Constantes.nota_debito_sri);
		infoTributaria.setEstab(notaDebito.getUsuario().getEstacion().getEstablecimiento().getCodigoSRI());
		infoTributaria.setPtoEmi(notaDebito.getUsuario().getEstacion().getCodigoSRI());
		infoTributaria.setSecuencial(notaDebito.getSecuencial());
		infoTributaria.setDirMatriz(notaDebito.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getDireccion());

		DateFormat dateFormat = new SimpleDateFormat(Constantes.fechaCortaSri);
		String fechaEmision = dateFormat.format(notaDebito.getFecha());
		InfoNotaDebito infoNotaDebito = new InfoNotaDebito();
		infoNotaDebito.setFechaEmision(fechaEmision);
		infoNotaDebito.setDirEstablecimiento(notaDebito.getUsuario().getEstacion().getEstablecimiento().getDireccion());
		infoNotaDebito.setTipoIdentificacionComprador(notaDebito.getFactura().getCliente().getTipoIdentificacion().getCodigoSRI());
		infoNotaDebito.setRazonSocialComprador(notaDebito.getFactura().getCliente().getRazonSocial());
		infoNotaDebito.setIdentificacionComprador(notaDebito.getFactura().getCliente().getIdentificacion());
		infoNotaDebito.setObligadoContabilidad(notaDebito.getFactura().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getObligadoContabilidad());
		infoNotaDebito.setCodDocModificado(Constantes.factura_sri);
		String numero = notaDebito.getFactura().getUsuario().getEstacion().getEstablecimiento().getCodigoSRI() + Constantes.guion + notaDebito.getFactura().getUsuario().getEstacion().getCodigoSRI() + Constantes.guion + notaDebito.getFactura().getSecuencial();
		infoNotaDebito.setNumDocModificado(numero);
		String fechaEmisionFactura = dateFormat.format(notaDebito.getFactura().getFecha());
		infoNotaDebito.setFechaEmisionDocSustento(fechaEmisionFactura);
		infoNotaDebito.setTotalSinImpuestos(Math.round(notaDebito.getSubtotal() * 100.0)/100.0);
		Impuestos impuestos = crearImpuestos(notaDebito);
		infoNotaDebito.setValorTotal(notaDebito.getTotal());
		Pagos pagos = crearPagos(notaDebito);
		Motivos motivos = crearMotivos(notaDebito);

		InfoAdicional infoAdicional = crearInfoAdicional(notaDebito);

		notaDebitoElectronica.setInfoTributaria(infoTributaria);
		notaDebitoElectronica.setInfoNotaDebito(infoNotaDebito);
		notaDebitoElectronica.getInfoNotaDebito().setImpuestos(impuestos);
		notaDebitoElectronica.getInfoNotaDebito().setPagos(pagos);
		notaDebitoElectronica.setMotivos(motivos);
		notaDebitoElectronica.setInfoAdicional(infoAdicional);
		return notaDebitoElectronica;
	}

	private Impuestos crearImpuestos(NotaDebito notaDebito) {
		Impuestos impuestos = new Impuestos();
		List<Impuesto> impuestoLista = new ArrayList<>();
		for(NotaDebitoLinea notaDebitoLinea : notaDebito.getNotaDebitoLineas()) {
			Impuesto impuesto = new Impuesto();
			impuesto.setCodigo(Constantes.iva_sri);
			impuesto.setCodigoPorcentaje(notaDebitoLinea.getImpuesto().getCodigoSRI());
			impuesto.setTarifa(notaDebitoLinea.getImpuesto().getPorcentaje());
			impuesto.setBaseImponible(Math.round(notaDebitoLinea.getSubtotalLinea()*100.0)/100.0);
			impuesto.setValor(Math.round(notaDebitoLinea.getImporteIvaLinea()*100.0)/100.0);
			impuestoLista.add(impuesto);
		}
		impuestos.setImpuesto(impuestoLista);
		return impuestos;
	}

	private Pagos crearPagos(NotaDebito notaDebito) {
		Pagos pagos = new Pagos();
		List<Pago> pagosLista = new ArrayList<>();
		if(notaDebito.getEfectivo() > 0) {
			Pago pago = new Pago();
			pago.setFormaPago(Constantes.sin_utilizacion_del_sistema_financiero);
			pago.setTotal(notaDebito.getEfectivo());
			pagosLista.add(pago);
		}

		for(NDCheque cheque: notaDebito.getCheques()) {
			Pago pago = new Pago();
			pago.setFormaPago(Constantes.otros_con_utilizacion_sistema_financiero);
			pago.setTotal(cheque.getValor());
			pagosLista.add(pago);
		}

		for(NDDeposito deposito: notaDebito.getDepositos()) {
			Pago pago = new Pago();
			pago.setFormaPago(Constantes.otros_con_utilizacion_sistema_financiero);
			pago.setTotal(deposito.getValor());
			pagosLista.add(pago);
		}

		for(NDTransferencia transferencia: notaDebito.getTransferencias()) {
			Pago pago = new Pago();
			pago.setFormaPago(Constantes.otros_con_utilizacion_sistema_financiero);
			pago.setTotal(transferencia.getValor());
			pagosLista.add(pago);
		}

		for(NDTarjetaDebito tarjetaDebito: notaDebito.getTarjetasDebitos()) {
			Pago pago = new Pago();
			pago.setFormaPago(Constantes.tarjeta_de_debito);
			pago.setTotal(tarjetaDebito.getValor());
			pagosLista.add(pago);
		}

		for(NDTarjetaCredito tarjetaCredito: notaDebito.getTarjetasCreditos()) {
			Pago pago = new Pago();
			pago.setFormaPago(Constantes.tarjeta_de_credito);
			pago.setTotal(tarjetaCredito.getValor());
			pagosLista.add(pago);
		}
		if(notaDebito.getCredito()!= null && notaDebito.getCredito().getSaldo() > Constantes.cero) {
			Pago pago = new Pago();
			pago.setFormaPago(Constantes.otros_con_utilizacion_sistema_financiero);
			pago.setTotal(notaDebito.getCredito().getSaldo());
			pago.setUnidadTiempo(notaDebito.getCredito().getUnidadTiempo());
			pago.setPlazo(notaDebito.getCredito().getPlazo());
			pagosLista.add(pago);
		}
		pagos.setPago(pagosLista);
		return pagos;
	}
	private Motivos crearMotivos(NotaDebito notaDebito) {
		Motivos motivos = new Motivos();
		List<Motivo> motivoLista = new ArrayList<>();
		for(NotaDebitoLinea notaDebitoLinea : notaDebito.getNotaDebitoLineas()) {
			Motivo motivo = new Motivo();
			motivo.setRazon(notaDebitoLinea.getNombreProducto());
			motivo.setValor(Math.round(notaDebitoLinea.getSubtotalLinea() * 100.0)/100.0);
			motivoLista.add(motivo);
		}
		motivos.setMotivo(motivoLista);
		return motivos;
	}

	private InfoAdicional crearInfoAdicional(NotaDebito notaDebito) {
		List<CampoAdicional> camposAdicionales = new ArrayList<>();
		if(!notaDebito.getFactura().getCliente().getTelefonos().isEmpty()) {
			String telefono = notaDebito.getFactura().getCliente().getTelefonos().get(0).getNumero();
			CampoAdicional campoAdicional = new CampoAdicional();
			campoAdicional.setNombre(Constantes.telefono);
			campoAdicional.setValor(telefono);
			camposAdicionales.add(campoAdicional);
		}
		if(!notaDebito.getFactura().getCliente().getTelefonos().isEmpty()){
			String celular = notaDebito.getFactura().getCliente().getTelefonos().get(0).getNumero();
			CampoAdicional campoAdicional = new CampoAdicional();
			campoAdicional.setNombre(Constantes.celular);
			campoAdicional.setValor(celular);
			camposAdicionales.add(campoAdicional);
		}
		if(!notaDebito.getFactura().getCliente().getCorreos().isEmpty()) {
			String correo = notaDebito.getFactura().getCliente().getCorreos().get(0).getEmail();
			CampoAdicional campoAdicional = new CampoAdicional();
			campoAdicional.setNombre(Constantes.correo);
			campoAdicional.setValor(correo);
			camposAdicionales.add(campoAdicional);
		}
		if(notaDebito.getFactura().getCliente().getDireccion().length() > Constantes.cero) {
			String direccion = notaDebito.getFactura().getCliente().getDireccion();
			CampoAdicional campoAdicional = new CampoAdicional();
			campoAdicional.setNombre(Constantes.direccion);
			campoAdicional.setValor(direccion);
			camposAdicionales.add(campoAdicional);
		}
		CampoAdicional campoAdicional = new CampoAdicional();
		campoAdicional.setNombre(Constantes.valor);
		campoAdicional.setValor(notaDebito.getFactura().getTotal() + Constantes.vacio);
		camposAdicionales.add(campoAdicional);
		InfoAdicional infoAdicional = new InfoAdicional();
		infoAdicional.setCampoAdicional(camposAdicionales);
		return infoAdicional;
	}

	@Override
	public NotaDebito enviar(long notaDebitoId) throws MalformedURLException {
		Optional<NotaDebito> opcional= rep.findById(notaDebitoId);
		if(opcional.isEmpty()) {
			throw new EntidadNoExistenteException(Constantes.nota_debito);
		}
		NotaDebito notaDebito = opcional.get();
		boolean banderaSuscripcion = suscripcionService.verificar(notaDebito.getEmpresa().getId());
		if(!banderaSuscripcion){
			throw new SuscripcionInvalidaException();
		}
		Resource certificado = empresaService.bajarCertificado(notaDebito.getEmpresa().getId());
		if(certificado == null){
			throw new CertificadoNoExistenteException();
		}
		if(notaDebito.getEmpresa().getContrasena().equals(Constantes.vacio)){
			throw new FacturaElectronicaInvalidaException(Constantes.contrasena);
		}
		if(notaDebito.getEstado().equals(Constantes.estadoEmitida)){
			throw new EstadoInvalidoException(Constantes.estadoEmitida);
		}
		if(notaDebito.getProcesoSRI().equals(Constantes.procesoSRIAutorizada)){
			throw new EstadoInvalidoException(Constantes.procesoSRIAutorizada);
		}
		if(notaDebito.getProcesoSRI().equals(Constantes.procesoSRIAnulada)){
			throw new EstadoInvalidoException(Constantes.procesoSRIAnulada);
		}
		NotaDebitoElectronica notaDebitoElectronica = crear(notaDebito);
		List<String> estadoRecepcion = recepcion(notaDebitoElectronica, notaDebito.getEmpresa().getCertificado(), notaDebito.getEmpresa().getContrasena());
		if(estadoRecepcion.get(0).equals(Constantes.devueltaSri)) {
			throw new FacturaElectronicaInvalidaException("ESTADO DEL SRI:" + Constantes.espacio + estadoRecepcion.get(0) + Constantes.espacio + Constantes.guion + Constantes.espacio + "INFORMACION ADICIONAL: " + estadoRecepcion.get(1));
		}
		List<String> estadoAutorizacion = autorizacion(notaDebitoElectronica);
		if(estadoAutorizacion.get(0).equals(Constantes.devueltaSri)) {
			throw new FacturaElectronicaInvalidaException("ESTADO DEL SRI:" + Constantes.espacio + estadoRecepcion.get(0) + Constantes.espacio + Constantes.guion + Constantes.espacio + "INFORMACION ADICIONAL: " + estadoRecepcion.get(1));
		}
		if(estadoAutorizacion.get(0).equals(Constantes.noAutorizadoSri)){
			throw new FacturaElectronicaInvalidaException("ESTADO DEL SRI:" + Constantes.espacio + estadoAutorizacion.get(0) + Constantes.espacio + Constantes.guion + Constantes.espacio + "INFORMACION ADICIONAL: " + estadoAutorizacion.get(1));
		}
		if(estadoAutorizacion.get(0).equals(Constantes.autorizadoSri)){
			suscripcionService.aumentarConteo(notaDebito.getEmpresa().getId());
			notaDebito.setProcesoSRI(Constantes.procesoSRIAutorizada);
			notaDebito.setFechaAutorizacion(new Date());
			enviarCorreo(notaDebito, notaDebitoElectronica);
		}
		NotaDebito facturada = rep.save(notaDebito);
		facturada.normalizar();
		return facturada;
	}

	private List<String> recepcion(NotaDebitoElectronica notaDebitoElectronica, String certificado, String contrasena) {
		try {
			String url = Constantes.vacio;
			if(facturacionProduccion.equals(Constantes.si)){
				url = Constantes.urlProduccionFacturacionEletronicaSri;
			}
			if(facturacionProduccion.equals(Constantes.no)){
				url = Constantes.urlPruebasFacturacionEletronicaSri;
			}
			JAXBContext jaxbContext = JAXBContext.newInstance(NotaDebitoElectronica.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, Constantes.utf8);
			jaxbMarshaller.marshal(notaDebitoElectronica, System.out);
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(notaDebitoElectronica, sw);
			String xml=sw.toString();
			Path path = Paths.get(Constantes.pathRecursos + Constantes.pathCertificados + Constantes.slash + certificado);
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
					.uri(URI.create(url))
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
			String url = Constantes.vacio;
			if(facturacionProduccion.equals(Constantes.si)){
				url = Constantes.urlProduccionConsultaFacturacionEletronicaSri;
			}
			if(facturacionProduccion.equals(Constantes.no)){
				url = Constantes.urlPruebasConsultaFacturacionEletronicaSri;
			}
			String body=Util.soapConsultaFacturacionEletronica(notaDebitoElectronica.getInfoTributaria().getClaveAcceso());
			HttpClient httpClient = HttpClient.newBuilder()
					.version(HttpClient.Version.HTTP_1_1)
					.connectTimeout(Duration.ofSeconds(10))
					.build();
			HttpRequest request = HttpRequest.newBuilder()
					.POST(BodyPublishers.ofString(body))
					.uri(URI.create(url))
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

	public ByteArrayInputStream crearPDF(NotaDebito notaDebito) {
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
			if(notaDebito.getEmpresa().getLogo().equals(Constantes.vacio)){
				documento.add(new Paragraph("LOGO").setFontSize(50).setTextAlignment(TextAlignment.CENTER));
			}
			if(!notaDebito.getEmpresa().getLogo().equals(Constantes.vacio)){
				Path path = Paths.get(Constantes.pathRecursos + Constantes.pathLogos + Constantes.slash + notaDebito.getEmpresa().getLogo());
				ImageData imageData = ImageDataFactory.create(path.toAbsolutePath().toString());
				Image image = new Image(imageData).scaleAbsolute(150, 100);
				documento.add(image);
			}
			String regimen = Constantes.vacio;
			if(notaDebito.getUsuario().getEstacion().getEstablecimiento().getRegimen() != null) {
				regimen = notaDebito.getUsuario().getEstacion().getRegimen().getDescripcion();
			}
			if(notaDebito.getUsuario().getEstacion().getRegimen() != null) {
				regimen = notaDebito.getUsuario().getEstacion().getRegimen().getDescripcion();
			}
			String contribuyenteEspecial = Constantes.vacio;
			if(notaDebito.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getEspecial().equals(Constantes.si)){
				contribuyenteEspecial = notaDebito.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getResolucionEspecial();
			}
			if(notaDebito.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getEspecial().equals(Constantes.no)){
				contribuyenteEspecial = Constantes.no;
			}
			String agenteRetencion = Constantes.vacio;
			if(notaDebito.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getAgenteRetencion().equals(Constantes.si)){
				agenteRetencion = notaDebito.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getResolucionAgente();
			}
			if(notaDebito.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getAgenteRetencion().equals(Constantes.no)){
				agenteRetencion = Constantes.no;
			}
			float [] columnas = {320F, 280F};
			Table tabla = new Table(columnas);
			tabla.addCell(getCellEmpresa(notaDebito.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial() +"\n" + "\n" +
					"DIRECCIÓN MATRIZ: " + notaDebito.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getDireccion() +"\n" + "\n" +
					"DIRECCIÓN SUCURSAL: " + notaDebito.getUsuario().getEstacion().getEstablecimiento().getDireccion() +"\n" + "\n" +
					regimen + "\n" + "\n" +
					"CONTRIBUYENTE ESPECIAL: " + contribuyenteEspecial + "\n" + "\n" +
					"OBLIGADO A LLEVAR CONTABILIDAD: " + notaDebito.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getObligadoContabilidad() + "\n" + "\n" +
					"AGENTE RETENCION RESOLUCIÓN: " + agenteRetencion, TextAlignment.LEFT));
			String numeroAutorizacion = Constantes.vacio;
			String fechaAutorizacion = Constantes.vacio;
			Image imagenCodigoBarras = null;
			if(notaDebito.getProcesoSRI().equals(Constantes.procesoSRIAutorizada)) {
				numeroAutorizacion = notaDebito.getClaveAcceso();
				fechaAutorizacion = formatoFecha.format(notaDebito.getFechaAutorizacion());
				Barcode128 codigoBarras = new Barcode128(pdf);
				codigoBarras.setCodeType(Barcode128.CODE128);
				codigoBarras.setCode(notaDebito.getClaveAcceso());
				PdfFormXObject objetoCodigoBarras = codigoBarras.createFormXObject(null, null, pdf);
				imagenCodigoBarras = new Image(objetoCodigoBarras);
			}
			String ambiente = Constantes.vacio;
			if(facturacionProduccion.equals(Constantes.si)){
				ambiente = Constantes.facturaFisicaAmbienteProduccionValor;
			}
			if(facturacionProduccion.equals(Constantes.no)){
				ambiente = Constantes.facturaFisicaAmbientePruebasValor;
			}
			tabla.addCell(getCellFactura("RUC: " + notaDebito.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getIdentificacion()+"\n"+
					"NOTA DEBITO"+"\n"+
					"No. " + notaDebito.getNumeroComprobante() + "\n" +
					"NÚMERO DE AUTORIZACIÓN: " + numeroAutorizacion+ "\n" +
					"FECHA DE AUTORIZACIÓN: " + fechaAutorizacion + "\n" +
					"AMBIENTE: " + ambiente + "\n" +
					"EMISIÓN: " + Constantes.facturaFisicaEmisionValor + "\n" + "\n" +
					"CLAVE DE ACCESO:", TextAlignment.LEFT, imagenCodigoBarras));
			tabla.setBorderCollapse(BorderCollapsePropertyValue.SEPARATE);
			tabla.setHorizontalBorderSpacing(3);
			documento.add(tabla);
			documento.add(new Paragraph("\n"));
			float [] columnasCliente = {300F, 300F};
			Table tablaCliente = new Table(columnasCliente);
			tablaCliente.addCell(getCellCliente("RAZÓN SOCIAL: " + notaDebito.getFactura().getCliente().getRazonSocial()+"\n" + "FECHA EMISIÓN: " + formatoFecha.format(notaDebito.getFecha()) + "\n" +
					"DIRECCION: " + notaDebito.getFactura().getCliente().getDireccion() + "\n", TextAlignment.LEFT));
			tablaCliente.addCell(getCellCliente("IDENTIFICACIÓN: " + notaDebito.getFactura().getCliente().getIdentificacion() + "\n"+ "GUIA: " + "\t" + "\t"+ "\t" + "\t"+ "\t"+ "\t"+ "\t"+ "\t", TextAlignment.RIGHT));
			documento.add(tablaCliente);
			documento.add( new Paragraph("\n"));
			float [] columnasTablaFacturaDetalle = {100F, 40F, 160F, 100F, 60F, 60F, 80F};
			Table tablaFacturaDetalle = new Table(columnasTablaFacturaDetalle);
			tablaFacturaDetalle.addCell(getCellColumnaFactura("CÓDIGO"));
			tablaFacturaDetalle.addCell(getCellColumnaFactura("CANT"));
			tablaFacturaDetalle.addCell(getCellColumnaFactura("DESCRIPCION"));
			tablaFacturaDetalle.addCell(getCellColumnaFactura("PRECIO U"));
			tablaFacturaDetalle.addCell(getCellColumnaFactura("DSCTO"));
			tablaFacturaDetalle.addCell(getCellColumnaFactura("DSCTO %"));
			tablaFacturaDetalle.addCell(getCellColumnaFactura("SUBTOTAL"));
			for (int i = 0; i < notaDebito.getNotaDebitoLineas().size(); i++)
			{
				String precioUnitario = String.format("%.2f", notaDebito.getNotaDebitoLineas().get(i).getPrecioUnitario());
				String descuentoLinea = String.format("%.2f", notaDebito.getNotaDebitoLineas().get(i).getValorDescuentoLinea());
				String porcentajeDescuentoLinea = notaDebito.getNotaDebitoLineas().get(i).getPorcentajeDescuentoLinea() + Constantes.vacio;
				String subtotalConDescuentoLinea = String.format("%.2f", notaDebito.getNotaDebitoLineas().get(i).getTotalLinea());

				tablaFacturaDetalle.addCell(getCellFilaFactura(notaDebito.getNotaDebitoLineas().get(i).getProducto().getCodigo()));
				tablaFacturaDetalle.addCell(getCellFilaFactura(notaDebito.getNotaDebitoLineas().get(i).getCantidad() + Constantes.vacio));
				tablaFacturaDetalle.addCell(getCellFilaFactura(notaDebito.getNotaDebitoLineas().get(i).getNombreProducto()));
				tablaFacturaDetalle.addCell(getCellFilaFactura("$"+precioUnitario));
				tablaFacturaDetalle.addCell(getCellFilaFactura("$"+descuentoLinea));
				tablaFacturaDetalle.addCell(getCellFilaFactura(porcentajeDescuentoLinea+"%"));
				tablaFacturaDetalle.addCell(getCellFilaFactura("$"+subtotalConDescuentoLinea));
			}
			documento.add(tablaFacturaDetalle);
			documento.add( new Paragraph("\n"));
			String subtotal = String.format("%.2f", notaDebito.getSubtotal());
			String descuento = String.format("%.2f", notaDebito.getDescuento());
			String subtotalBase12ConDescuento = String.format("%.2f", notaDebito.getSubtotalGravado());
			String subtotalBase0ConDescuento = String.format("%.2f", notaDebito.getSubtotalNoGravado());
			String iva = String.format("%.2f", notaDebito.getImporteIva());
			String totalConDescuento = String.format("%.2f", notaDebito.getTotal());
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
			if (!notaDebito.getFactura().getCliente().getTelefonos().isEmpty()){
				telefonoCliente = notaDebito.getFactura().getCliente().getTelefonos().get(0).getNumero();
			}
			if (!notaDebito.getFactura().getCliente().getCelulares().isEmpty()){
				celularCliente = notaDebito.getFactura().getCliente().getCelulares().get(0).getNumero();
			}
			if (!notaDebito.getFactura().getCliente().getCorreos().isEmpty()){
				correoCliente = notaDebito.getFactura().getCliente().getCorreos().get(0).getEmail();
			}
			String comentario = notaDebito.getComentario();
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
			if(notaDebito.getEfectivo() > Constantes.cero) {
				tablaFormaPago.addCell(getCellFormaPago(Constantes.sin_utilizacion_del_sistema_financiero + Constantes.espacio + Constantes.guion + Constantes.espacio + Constantes.texto_sin_utilizacion_del_sistema_financiero));
				String valor = String.format("%.2f", notaDebito.getEfectivo() - notaDebito.getCambio());
				tablaFormaPago.addCell(getCellFormaPago(valor));
			}
			for(NDCheque cheque: notaDebito.getCheques()) {
				tablaFormaPago.addCell(getCellFormaPago(Constantes.otros_con_utilizacion_sistema_financiero + Constantes.espacio + Constantes.guion + Constantes.espacio + Constantes.texto_otros_con_utilizacion_sistema_financiero));
				String valor = String.format("%.2f", cheque.getValor());
				tablaFormaPago.addCell(getCellFormaPago(valor));
			}
			for(NDDeposito deposito: notaDebito.getDepositos()) {
				tablaFormaPago.addCell(getCellFormaPago(Constantes.otros_con_utilizacion_sistema_financiero + Constantes.espacio + Constantes.guion + Constantes.espacio + Constantes.texto_otros_con_utilizacion_sistema_financiero));
				String valor = String.format("%.2f", deposito.getValor());
				tablaFormaPago.addCell(getCellFormaPago(valor));
			}
			for(NDTransferencia transferencia: notaDebito.getTransferencias()) {
				tablaFormaPago.addCell(getCellFormaPago(Constantes.otros_con_utilizacion_sistema_financiero + Constantes.espacio + Constantes.guion + Constantes.espacio + Constantes.texto_otros_con_utilizacion_sistema_financiero));
				String valor = String.format("%.2f", transferencia.getValor());
				tablaFormaPago.addCell(getCellFormaPago(valor));
			}
			for(NDTarjetaDebito tarjetaDebito: notaDebito.getTarjetasDebitos()) {
				tablaFormaPago.addCell(getCellFormaPago(Constantes.tarjeta_de_debito + Constantes.espacio + Constantes.guion + Constantes.espacio + Constantes.texto_tarjeta_de_debito));
				String valor = String.format("%.2f", tarjetaDebito.getValor());
				tablaFormaPago.addCell(getCellFormaPago(valor));
			}
			for(NDTarjetaCredito tarjetaCredito: notaDebito.getTarjetasCreditos()) {
				tablaFormaPago.addCell(getCellFormaPago(Constantes.tarjeta_de_credito + Constantes.espacio + Constantes.guion + Constantes.espacio + Constantes.texto_tarjeta_de_credito));
				String valor = String.format("%.2f", tarjetaCredito.getValor());
				tablaFormaPago.addCell(getCellFormaPago(valor));
			}
			if(notaDebito.getCredito()!= null && notaDebito.getCredito().getSaldo() > Constantes.cero) {
				tablaFormaPago.addCell(getCellFormaPago(Constantes.otros_con_utilizacion_sistema_financiero + Constantes.espacio + Constantes.guion + Constantes.espacio + Constantes.texto_otros_con_utilizacion_sistema_financiero));
				String valor = String.format("%.2f", notaDebito.getCredito().getSaldo());
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

	private void enviarCorreo(NotaDebito notaDebito, NotaDebitoElectronica notaDebitoElectronica) {
		try {
			ByteArrayInputStream pdf = crearPDF(notaDebito);
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
			parte1.setFileName(Constantes.nota_credito + notaDebito.getSecuencial()+Constantes.extensionPdf);
			MimeBodyPart parte2 = new MimeBodyPart();
			parte2.setDataHandler(new DataHandler(xmlData));
			parte2.setFileName(Constantes.nota_credito + notaDebito.getSecuencial()+Constantes.extensionXml);

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(parte1);
			multipart.addBodyPart(parte2);

			message.setFrom(new InternetAddress(correoUsuario));
			message.addRecipients(Message.RecipientType.TO, notaDebito.getFactura().getCliente().getCorreos().get(0).getEmail());   //Se podrían añadir varios de la misma manera
			message.setSubject(notaDebito.getFactura().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial()+ Constantes.mensajeCorreo + notaDebito.getCodigo());
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
	public ByteArrayInputStream obtenerPDF(long notaDebitoId){
		Optional<NotaDebito> opcional= rep.findById(notaDebitoId);
		if(opcional.isEmpty()) {
			throw new EntidadNoExistenteException(Constantes.nota_debito);
		}
		NotaDebito notaDebito = opcional.get();
		ByteArrayInputStream pdf = crearPDF(notaDebito);
		return pdf;
	}

	@Override
	public void enviarPDFYXML(long notaDebitoId){
		Optional<NotaDebito> opcional= rep.findById(notaDebitoId);
		if(opcional.isEmpty()) {
			throw new EntidadNoExistenteException(Constantes.nota_debito);
		}
		NotaDebito notaDebito = opcional.get();
		NotaDebitoElectronica notaDebitoElectronica = crear(notaDebito);
		enviarCorreo(notaDebito, notaDebitoElectronica);
	}
}