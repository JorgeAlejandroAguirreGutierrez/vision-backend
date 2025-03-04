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
import com.itextpdf.layout.properties.*;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.*;
import com.proyecto.vision.modelos.venta.NotaCredito;
import com.proyecto.vision.modelos.venta.NotaCreditoLinea;
import com.proyecto.vision.modelos.venta.electronico.notacredito.*;
import com.proyecto.vision.repositorios.venta.INotaCreditoRepository;
import com.proyecto.vision.servicios.interf.acceso.IEmpresaService;
import com.proyecto.vision.servicios.interf.acceso.ISuscripcionService;
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

	@Autowired
	private ISuscripcionService suscripcionService;

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
		infoTributaria.setRazonSocial(notaCredito.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial());
		infoTributaria.setNombreComercial(notaCredito.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getNombreComercial());
		infoTributaria.setRuc(notaCredito.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getIdentificacion());
		infoTributaria.setClaveAcceso(notaCredito.getClaveAcceso());
		infoTributaria.setCodDoc(Constantes.nota_credito_sri);
		infoTributaria.setEstab(notaCredito.getUsuario().getEstacion().getEstablecimiento().getCodigoSRI());
		infoTributaria.setPtoEmi(notaCredito.getUsuario().getEstacion().getCodigoSRI());
		infoTributaria.setSecuencial(notaCredito.getSecuencial());
		infoTributaria.setDirMatriz(notaCredito.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getDireccion());

		DateFormat dateFormat = new SimpleDateFormat(Constantes.fechaCortaSri);
		String fechaEmision = dateFormat.format(notaCredito.getFecha());
		InfoNotaCredito infoNotaCredito = new InfoNotaCredito();
		infoNotaCredito.setFechaEmision(fechaEmision);
		infoNotaCredito.setDirEstablecimiento(notaCredito.getUsuario().getEstacion().getEstablecimiento().getDireccion());
		infoNotaCredito.setTipoIdentificacionComprador(notaCredito.getFactura().getCliente().getTipoIdentificacion().getCodigoSRI());
		infoNotaCredito.setRazonSocialComprador(notaCredito.getFactura().getCliente().getRazonSocial());
		infoNotaCredito.setIdentificacionComprador(notaCredito.getFactura().getCliente().getIdentificacion());
		infoNotaCredito.setObligadoContabilidad(notaCredito.getFactura().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getObligadoContabilidad());
		infoNotaCredito.setCodDocModificado(Constantes.factura_sri);
		String numero = notaCredito.getFactura().getUsuario().getEstacion().getEstablecimiento().getCodigoSRI()+Constantes.guion+ notaCredito.getFactura().getUsuario().getEstacion().getCodigoSRI()+Constantes.guion+ notaCredito.getFactura().getSecuencial();
		infoNotaCredito.setNumDocModificado(numero);
		String fechaEmisionFactura = dateFormat.format(notaCredito.getFactura().getFecha());
		infoNotaCredito.setFechaEmisionDocSustento(fechaEmisionFactura);
		infoNotaCredito.setTotalSinImpuestos(Math.round(notaCredito.getSubtotal() * 100.0)/100.0);
		infoNotaCredito.setValorModificacion(notaCredito.getTotal());
		infoNotaCredito.setMoneda(Constantes.moneda);
		infoNotaCredito.setTotalConImpuestos(crearTotalConImpuestos(notaCredito));
		infoNotaCredito.setMotivo(notaCredito.getOperacion());
		Detalles detalles = crearDetalles(notaCredito);
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
		double baseImponible0 = Constantes.cero;
		double baseImponible8 = Constantes.cero;
		double baseImponible12 = Constantes.cero;
		double baseImponible14 = Constantes.cero;
		double baseImponible15 = Constantes.cero;
		double baseImponible5 = Constantes.cero;
		double baseImponible13 = Constantes.cero;
		double iva0 = Constantes.cero;
		double iva8 = Constantes.cero;
		double iva12 = Constantes.cero;
		double iva14 = Constantes.cero;
		double iva15 = Constantes.cero;
		double iva5 = Constantes.cero;
		double iva13 = Constantes.cero;
		boolean banderaIva0 = false;
		boolean banderaIva8 = false;
		boolean banderaIva12 = false;
		boolean banderaIva14 = false;
		boolean banderaIva15 = false;
		boolean banderaIva5 = false;
		boolean banderaIva13 = false;
		for(NotaCreditoLinea notaCreditoLinea: notaCredito.getNotaCreditoLineas()){
			if(notaCreditoLinea.getImpuesto().getCodigoSRI().equals(Constantes.iva_0_sri)){
				banderaIva0 = true;
				baseImponible0 = baseImponible0 + notaCreditoLinea.getSubtotalLinea();
				iva0 = iva0 + notaCreditoLinea.getImporteIvaLinea();
			}
			if(notaCreditoLinea.getImpuesto().getCodigoSRI().equals(Constantes.iva_8_sri)){
				banderaIva8 = true;
				baseImponible8 = baseImponible8 + notaCreditoLinea.getSubtotalLinea();
				iva8 = iva8 + notaCreditoLinea.getImporteIvaLinea();
			}
			if(notaCreditoLinea.getImpuesto().getCodigoSRI().equals(Constantes.iva_12_sri)){
				banderaIva12 = true;
				baseImponible12 = baseImponible12 + notaCreditoLinea.getSubtotalLinea();
				iva12 = iva12 + notaCreditoLinea.getImporteIvaLinea();
			}
			if(notaCreditoLinea.getImpuesto().getCodigoSRI().equals(Constantes.iva_14_sri)){
				banderaIva14 = true;
				baseImponible14 = baseImponible14 + notaCreditoLinea.getSubtotalLinea();
				iva14 = iva14 + notaCreditoLinea.getImporteIvaLinea();
			}
			if(notaCreditoLinea.getImpuesto().getCodigoSRI().equals(Constantes.iva_15_sri)){
				banderaIva15 = true;
				baseImponible15 = baseImponible15 + notaCreditoLinea.getSubtotalLinea();
				iva15 = iva15 + notaCreditoLinea.getImporteIvaLinea();
			}
			if(notaCreditoLinea.getImpuesto().getCodigoSRI().equals(Constantes.iva_5_sri)){
				banderaIva5 = true;
				baseImponible5 = baseImponible5 + notaCreditoLinea.getSubtotalLinea();
				iva5 = iva5 + notaCreditoLinea.getImporteIvaLinea();
			}
			if(notaCreditoLinea.getImpuesto().getCodigoSRI().equals(Constantes.iva_13_sri)){
				banderaIva13 = true;
				baseImponible13 = baseImponible13 + notaCreditoLinea.getSubtotalLinea();
				iva13 = iva13 + notaCreditoLinea.getImporteIvaLinea();
			}
		}
		if(banderaIva0){
			TotalImpuesto totalImpuesto = new TotalImpuesto();
			totalImpuesto.setCodigo(Constantes.iva_sri);
			totalImpuesto.setCodigoPorcentaje(Constantes.iva_0_sri);
			totalImpuesto.setBaseImponible(Math.round(baseImponible0 * 100.0)/100.0);
			totalImpuesto.setValor(Math.round(iva0 * 100.0)/100.0);
			totalImpuestos.add(totalImpuesto);
		}
		if(banderaIva8){
			TotalImpuesto totalImpuesto = new TotalImpuesto();
			totalImpuesto.setCodigo(Constantes.iva_sri);
			totalImpuesto.setCodigoPorcentaje(Constantes.iva_8_sri);
			totalImpuesto.setBaseImponible(Math.round(baseImponible8 * 100.0)/100.0);
			totalImpuesto.setValor(Math.round(iva8 * 100.0)/100.0);
			totalImpuestos.add(totalImpuesto);
		}
		if(banderaIva12){
			TotalImpuesto totalImpuesto = new TotalImpuesto();
			totalImpuesto.setCodigo(Constantes.iva_sri);
			totalImpuesto.setCodigoPorcentaje(Constantes.iva_12_sri);
			totalImpuesto.setBaseImponible(Math.round(baseImponible12 * 100.0)/100.0);
			totalImpuesto.setValor(Math.round(iva12 * 100.0)/100.0);
			totalImpuestos.add(totalImpuesto);
		}
		if(banderaIva14){
			TotalImpuesto totalImpuesto = new TotalImpuesto();
			totalImpuesto.setCodigo(Constantes.iva_sri);
			totalImpuesto.setCodigoPorcentaje(Constantes.iva_14_sri);
			totalImpuesto.setBaseImponible(Math.round(baseImponible14 * 100.0)/100.0);
			totalImpuesto.setValor(Math.round(iva12 * 100.0)/100.0);
			totalImpuestos.add(totalImpuesto);
		}
		if(banderaIva15){
			TotalImpuesto totalImpuesto = new TotalImpuesto();
			totalImpuesto.setCodigo(Constantes.iva_sri);
			totalImpuesto.setCodigoPorcentaje(Constantes.iva_15_sri);
			totalImpuesto.setBaseImponible(Math.round(baseImponible15 * 100.0)/100.0);
			totalImpuesto.setValor(Math.round(iva15 * 100.0)/100.0);
			totalImpuestos.add(totalImpuesto);
		}
		if(banderaIva5){
			TotalImpuesto totalImpuesto = new TotalImpuesto();
			totalImpuesto.setCodigo(Constantes.iva_sri);
			totalImpuesto.setCodigoPorcentaje(Constantes.iva_5_sri);
			totalImpuesto.setBaseImponible(Math.round(baseImponible5 * 100.0)/100.0);
			totalImpuesto.setValor(Math.round(iva5 * 100.0)/100.0);
			totalImpuestos.add(totalImpuesto);
		}
		if(banderaIva13){
			TotalImpuesto totalImpuesto = new TotalImpuesto();
			totalImpuesto.setCodigo(Constantes.iva_sri);
			totalImpuesto.setCodigoPorcentaje(Constantes.iva_13_sri);
			totalImpuesto.setBaseImponible(Math.round(baseImponible13 * 100.0)/100.0);
			totalImpuesto.setValor(Math.round(iva13 * 100.0)/100.0);
			totalImpuestos.add(totalImpuesto);
		}
		totalConImpuestos.setTotalImpuesto(totalImpuestos);
		return totalConImpuestos;
	}

	private Detalles crearDetalles(NotaCredito notaCredito) {
		Detalles detalles = new Detalles();
		List<Detalle> detalleLista = new ArrayList<>();
		for(NotaCreditoLinea notaCreditoLinea : notaCredito.getNotaCreditoLineas()) {
			Detalle detalle = new Detalle();
			detalle.setCodigoInterno(notaCreditoLinea.getProducto().getCodigo());
			detalle.setDescripcion(notaCreditoLinea.getNombreProducto());
			detalle.setCantidad(notaCreditoLinea.getCantidad());
			detalle.setPrecioUnitario(Math.round(notaCreditoLinea.getCostoUnitario()*100.0)/100.0);
			detalle.setDescuento(notaCreditoLinea.getDescuentoLinea());
			detalle.setPrecioTotalSinImpuesto(Math.round(notaCreditoLinea.getSubtotalLinea() * 100.0) / 100.0);
			detalle.setImpuestos(crearImpuestos(notaCreditoLinea));
			detalleLista.add(detalle);
		}
		detalles.setDetalle(detalleLista);
		return detalles;
	}

	private Impuestos crearImpuestos(NotaCreditoLinea notaCreditoLinea) {
		Impuestos impuestos = new Impuestos();
		List<Impuesto> impuestoLista = new ArrayList<>();
		Impuesto impuesto = new Impuesto();
		impuesto.setCodigo(Constantes.iva_sri);
		impuesto.setCodigoPorcentaje(notaCreditoLinea.getImpuesto().getCodigoSRI());
		impuesto.setTarifa(notaCreditoLinea.getImpuesto().getPorcentaje());
		impuesto.setBaseImponible(Math.round(notaCreditoLinea.getSubtotalLinea()* 100.0)/100.0);
		impuesto.setValor(Math.round(notaCreditoLinea.getImporteIvaLinea()*100.0)/100.0);
		impuestoLista.add(impuesto);
		impuestos.setImpuesto(impuestoLista);
		return impuestos;
	}

	private InfoAdicional crearInfoAdicional(NotaCredito notaCredito) {
		List<CampoAdicional> camposAdicionales = new ArrayList<>();
		if(!notaCredito.getFactura().getCliente().getTelefonos().isEmpty() && !notaCredito.getFactura().getCliente().getTelefonos().get(0).getNumero().equals(Constantes.vacio)) {
			String telefono = notaCredito.getFactura().getCliente().getTelefonos().get(0).getNumero();
			CampoAdicional campoAdicional = new CampoAdicional();
			campoAdicional.setNombre(Constantes.telefono);
			campoAdicional.setValor(telefono);
			camposAdicionales.add(campoAdicional);
		}
		if(!notaCredito.getFactura().getCliente().getCelulares().isEmpty() && !notaCredito.getFactura().getCliente().getCelulares().get(0).getNumero().equals(Constantes.vacio)){
			String celular = notaCredito.getFactura().getCliente().getCelulares().get(0).getNumero();
			CampoAdicional campoAdicional = new CampoAdicional();
			campoAdicional.setNombre(Constantes.celular);
			campoAdicional.setValor(celular);
			camposAdicionales.add(campoAdicional);
		}
		if(!notaCredito.getFactura().getCliente().getCorreos().isEmpty() && !notaCredito.getFactura().getCliente().getCorreos().get(0).getEmail().equals(Constantes.vacio)) {
			String correo = notaCredito.getFactura().getCliente().getCorreos().get(0).getEmail();
			CampoAdicional campoAdicional = new CampoAdicional();
			campoAdicional.setNombre(Constantes.correo);
			campoAdicional.setValor(correo);
			camposAdicionales.add(campoAdicional);
		}
		if(!notaCredito.getFactura().getCliente().getDireccion().equals(Constantes.vacio)) {
			String direccion = notaCredito.getFactura().getCliente().getDireccion();
			CampoAdicional campoAdicional = new CampoAdicional();
			campoAdicional.setNombre(Constantes.direccion);
			campoAdicional.setValor(direccion);
			camposAdicionales.add(campoAdicional);
		}
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
		boolean banderaSuscripcion = suscripcionService.verificar(notaCredito.getEmpresa().getId());
		if(!banderaSuscripcion){
			throw new SuscripcionInvalidaException();
		}
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
		if(estadoAutorizacion.get(0).equals(Constantes.noAutorizadoSri)){
			throw new FacturaElectronicaInvalidaException("ESTADO DEL SRI:" + Constantes.espacio + estadoAutorizacion.get(0) + Constantes.espacio + Constantes.guion + Constantes.espacio + "INFORMACION ADICIONAL: " + estadoAutorizacion.get(1));
		}
		if(estadoAutorizacion.get(0).equals(Constantes.autorizadoSri)){
			suscripcionService.aumentarConteo(notaCredito.getEmpresa().getId());
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
			String url = Constantes.vacio;
			if(facturacionProduccion.equals(Constantes.si)){
				url = Constantes.urlProduccionFacturacionEletronicaSri;
			}
			if(facturacionProduccion.equals(Constantes.no)){
				url = Constantes.urlPruebasFacturacionEletronicaSri;
			}
			JAXBContext jaxbContext = JAXBContext.newInstance(NotaCreditoElectronica.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, Constantes.utf8);
			jaxbMarshaller.marshal(notaCreditoElectronica, System.out);
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(notaCreditoElectronica, sw);
			String xml = sw.toString();
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

	public List<String> autorizacion(NotaCreditoElectronica notaCreditoElectronica){
		try {
			String url = Constantes.vacio;
			if(facturacionProduccion.equals(Constantes.si)){
				url = Constantes.urlProduccionConsultaFacturacionEletronicaSri;
			}
			if(facturacionProduccion.equals(Constantes.no)){
				url = Constantes.urlPruebasConsultaFacturacionEletronicaSri;
			}
			String body=Util.soapConsultaFacturacionEletronica(notaCreditoElectronica.getInfoTributaria().getClaveAcceso());
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

	public ByteArrayInputStream crearPDF(NotaCredito notaCredito) {
		try {
			DateFormat formatoFechaYHora = new SimpleDateFormat(Constantes.fechaYHora);
			DateFormat formatoFecha = new SimpleDateFormat(Constantes.fechaCorta);
			ByteArrayOutputStream salida = new ByteArrayOutputStream();
			PdfWriter writer = new PdfWriter(salida);
			PdfDocument pdf = new PdfDocument(writer);
			// Initialize document
			Document documento = new Document(pdf, PageSize.A4);
			documento.setMargins(30,25,20,30);
			// 4. Add content
			PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
			documento.setFont(font);
			float [] columnas = {320F, 280F};
			Table tablaEncabezado = new Table(columnas);
			tablaEncabezado.setVerticalAlignment(VerticalAlignment.BOTTOM);
			tablaEncabezado.setBorder(Border.NO_BORDER);
			Table tablaEncabezadoIzq = new Table(1);
			tablaEncabezadoIzq.useAllAvailableWidth();
			tablaEncabezadoIzq.setBorder(Border.NO_BORDER);
			Table tablaEncabezadoDer = new Table(1);
			tablaEncabezadoDer.setBorder(Border.NO_BORDER);
			if(notaCredito.getEmpresa().getLogo().equals(Constantes.vacio)){
				documento.add(new Paragraph("LOGO").setFontSize(50).setTextAlignment(TextAlignment.CENTER));
			}
			if(!notaCredito.getEmpresa().getLogo().equals(Constantes.vacio)){
				Path path = Paths.get(Constantes.pathRecursos + Constantes.pathLogos + Constantes.slash + notaCredito.getEmpresa().getLogo());
				ImageData imageData = ImageDataFactory.create(path.toAbsolutePath().toString());
				Image image = new Image(imageData).scaleAbsolute(150, 100);
				image.setHorizontalAlignment(HorizontalAlignment.CENTER);
				image.setBorder(Border.NO_BORDER);
				Cell celda1 = new Cell();
				celda1.setBorder(Border.NO_BORDER);
				celda1.add(image);
				tablaEncabezadoIzq.addCell(celda1);
			}
			String regimen = Constantes.vacio;
			if(notaCredito.getUsuario().getEstacion().getEstablecimiento().getRegimen() != null) {
				regimen = notaCredito.getUsuario().getEstacion().getEstablecimiento().getRegimen().getDescripcion();
			}
			if(notaCredito.getUsuario().getEstacion().getRegimen() != null) {
				regimen = notaCredito.getUsuario().getEstacion().getRegimen().getDescripcion();
			}
			String contribuyenteEspecial = Constantes.vacio;
			if(notaCredito.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getEspecial().equals(Constantes.si)){
				contribuyenteEspecial = notaCredito.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getResolucionEspecial();
			}
			if(notaCredito.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getEspecial().equals(Constantes.no)){
				contribuyenteEspecial = Constantes.no;
			}
			String agenteRetencion = Constantes.vacio;
			if(notaCredito.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getAgenteRetencion().equals(Constantes.si)){
				agenteRetencion = notaCredito.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getResolucionAgente();
			}
			if(notaCredito.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getAgenteRetencion().equals(Constantes.no)){
				agenteRetencion = Constantes.no;
			}
			String granContribuyente = notaCredito.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getGranContribuyente();
			String artesadoCalificado = notaCredito.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getArtesanoCalificado();
			String linea1 = notaCredito.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial() +"\n" + "\n";
			String linea2 = "DIRECCIÓN MATRIZ: " + notaCredito.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getDireccion();
			String texto = "DIRECCIÓN SUCURSAL: " + notaCredito.getUsuario().getEstacion().getEstablecimiento().getDireccion() +"\n" +
					regimen + "\n" +
					"CONTRIBUYENTE ESPECIAL: " + contribuyenteEspecial + "\n"+
					"OBLIGADO A LLEVAR CONTABILIDAD: " + notaCredito.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getObligadoContabilidad() + "\n" +
					"AGENTE RETENCION RESOL: " + agenteRetencion;
			if(granContribuyente.equals(Constantes.si)){
				texto = texto + "\n" + "GRAN CONTRIBUYENTE: " + granContribuyente;
			}
			if(artesadoCalificado.equals(Constantes.si)){
				texto = texto + "\n" + "ARTESADO CALIFICADO: " + artesadoCalificado;
			}
			tablaEncabezadoIzq.addCell(getCellEmpresa(linea1, linea2, texto, TextAlignment.LEFT));
			String numeroAutorizacion = Constantes.vacio;
			String fechaAutorizacion = Constantes.vacio;
			Image imagenCodigoBarras = null;
			if(notaCredito.getProcesoSRI().equals(Constantes.procesoSRIAutorizada)){
				numeroAutorizacion = notaCredito.getClaveAcceso();
				fechaAutorizacion = formatoFechaYHora.format(notaCredito.getFechaAutorizacion());
				Barcode128 codigoBarras = new Barcode128(pdf);
				codigoBarras.setCodeType(Barcode128.CODE128);
				codigoBarras.setCode(notaCredito.getClaveAcceso());
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
			tablaEncabezadoDer.addCell(getCellFactura("RUC: " + notaCredito.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getIdentificacion()+"\n"+"\n"+
					"NOTA DE CREDITO"+"\n"+
					"No. " + notaCredito.getNumeroComprobante() + "\n" +"\n"+
					"NÚMERO DE AUTORIZACIÓN: " + numeroAutorizacion + "\n" +"\n"+
					"FECHA DE AUTORIZACIÓN: " + fechaAutorizacion + "\n" +
					"AMBIENTE: " + ambiente + "\n" +
					"EMISIÓN: " + Constantes.facturaFisicaEmisionValor + "\n" + "\n" +
					"CLAVE DE ACCESO:", TextAlignment.LEFT, imagenCodigoBarras));
			Cell celda2 = new Cell();
			celda2.setBorder(Border.NO_BORDER);
			celda2.add(tablaEncabezadoIzq);
			tablaEncabezado.addCell(celda2);
			Cell celda3 = new Cell();
			celda3.setBorder(Border.NO_BORDER);
			celda3.add(tablaEncabezadoDer);
			tablaEncabezado.addCell(celda3);
			documento.add(tablaEncabezado);
			documento.add(new Paragraph("\n"));
			float [] columnasCliente = {600F};
			Table tablaCliente = new Table(columnasCliente);
			tablaCliente.addCell(getCellCliente("RAZÓN SOCIAL: "+notaCredito.getFactura().getCliente().getRazonSocial()+ "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" +"IDENTIFICACIÓN: " + notaCredito.getFactura().getCliente().getIdentificacion() +
					"\n" + "FECHA EMISIÓN: " + formatoFecha.format(notaCredito.getFecha()) +
					"\n" + "COMPROBANTE QUE SE MODIFICA:" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "FACTURA" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + notaCredito.getFactura().getNumeroComprobante() +
					"\n" + "FECHA DE EMISION (COMPROBANTE A MODIFICAR): "  + formatoFecha.format(notaCredito.getFactura().getFecha()) +
					"\n" + "RAZON DE MODIFICACION: " + notaCredito.getOperacion(), TextAlignment.LEFT));
			documento.add(tablaCliente);
			documento.add( new Paragraph("\n"));
			float [] columnasTablaFacturaDetalle = {100F, 40F, 160F, 100F, 100F, 100F};
			Table tablaFacturaDetalle = new Table(columnasTablaFacturaDetalle);
			tablaFacturaDetalle.addCell(getCellColumnaFactura("CÓDIGO"));
			tablaFacturaDetalle.addCell(getCellColumnaFactura("CANT"));
			tablaFacturaDetalle.addCell(getCellColumnaFactura("DESCRIPCION"));
			tablaFacturaDetalle.addCell(getCellColumnaFactura("CANTIDAD"));
			tablaFacturaDetalle.addCell(getCellColumnaFactura("COSTO U"));
			tablaFacturaDetalle.addCell(getCellColumnaFactura("SUBTOTAL"));
			for (NotaCreditoLinea notaCreditoLinea: notaCredito.getNotaCreditoLineas())
			{
				String costoUnitario = String.format("%.2f", notaCreditoLinea.getCostoUnitario());
				String cantidad = String.format("%.2f", notaCreditoLinea.getCantidad());
				String subtotalConDescuentoLinea = String.format("%.2f", notaCreditoLinea.getSubtotalLinea());

				tablaFacturaDetalle.addCell(getCellFilaFactura(notaCreditoLinea.getProducto().getCodigo()));
				tablaFacturaDetalle.addCell(getCellFilaFactura(notaCreditoLinea.getCantidad() + Constantes.vacio));
				tablaFacturaDetalle.addCell(getCellFilaFactura(notaCreditoLinea.getNombreProducto()));
				tablaFacturaDetalle.addCell(getCellFilaFactura("$" + cantidad));
				tablaFacturaDetalle.addCell(getCellFilaFactura("$" + costoUnitario));
				tablaFacturaDetalle.addCell(getCellFilaFactura("$" + subtotalConDescuentoLinea));
			}
			documento.add(tablaFacturaDetalle);
			documento.add( new Paragraph("\n"));
			String subtotal = String.format("%.2f", notaCredito.getSubtotal());
			String descuento = String.format("%.2f", notaCredito.getTotalDescuento());
			String subtotalGravadoConDescuento = String.format("%.2f", notaCredito.getSubtotalGravado());
			String subtotalNoGravadoConDescuento = String.format("%.2f", notaCredito.getSubtotalNoGravado());
			String iva = String.format("%.2f", notaCredito.getImporteIva());
			String totalConDescuento = String.format("%.2f", notaCredito.getTotal());
			float [] columnasTablaFactura = {300F, 300F};
			Table tablaFactura = new Table(columnasTablaFactura);
			tablaFactura.addCell(getCellFilaFactura("SUBTOTAL"));
			tablaFactura.addCell(getCellFilaFactura("$" + subtotal));
			tablaFactura.addCell(getCellFilaFactura("DESCUENTO"));
			tablaFactura.addCell(getCellFilaFactura("$" + descuento));
			tablaFactura.addCell(getCellFilaFactura("SUBTOTAL GRAVADO"));
			tablaFactura.addCell(getCellFilaFactura("$" + subtotalGravadoConDescuento));
			tablaFactura.addCell(getCellFilaFactura("SUBTOTAL NO GRAVADO"));
			tablaFactura.addCell(getCellFilaFactura("$" + subtotalNoGravadoConDescuento));
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

	private Cell getCellEmpresa(String linea1, String linea2, String texto, TextAlignment alignment) {
		Paragraph parrafo1 = new Paragraph(linea1);
		parrafo1.setBold();
		Paragraph parrafo2 = new Paragraph(linea2);
		parrafo2.setFontSize(9);
		Cell cell = new Cell().add(parrafo1);
		cell.add(parrafo2);
		cell.add(new Paragraph(texto).setFontSize(9));
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
			message.setSubject(notaCredito.getFactura().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial()+ Constantes.mensajeCorreo + notaCredito.getCodigo());
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