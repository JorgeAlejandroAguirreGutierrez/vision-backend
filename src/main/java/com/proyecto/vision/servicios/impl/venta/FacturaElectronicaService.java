package com.proyecto.vision.servicios.impl.venta;

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
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.*;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.*;
import com.proyecto.vision.modelos.venta.Factura;
import com.proyecto.vision.modelos.venta.FacturaLinea;
import com.proyecto.vision.modelos.venta.electronico.factura.*;
import com.proyecto.vision.modelos.recaudacion.Cheque;
import com.proyecto.vision.modelos.recaudacion.Deposito;
import com.proyecto.vision.modelos.recaudacion.TarjetaCredito;
import com.proyecto.vision.modelos.recaudacion.TarjetaDebito;
import com.proyecto.vision.modelos.recaudacion.Transferencia;
import com.proyecto.vision.repositorios.venta.IFacturaRepository;
import com.proyecto.vision.servicios.interf.usuario.IEmpresaService;
import com.proyecto.vision.servicios.interf.usuario.ISuscripcionService;
import com.proyecto.vision.servicios.interf.venta.IFacturaElectronicaService;

import ayungan.com.signature.ConvertFile;
import ayungan.com.signature.SignatureXAdESBES;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
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

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.net.http.HttpResponse;
import java.security.cert.CertificateException;
import java.time.Duration;
import java.util.*;
import java.util.List;

@Service
public class FacturaElectronicaService implements IFacturaElectronicaService{
	@Autowired
	private IFacturaRepository rep;

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

	@Value("${url.facturacion}")
	private String urlFacturacion;

	@Value("${url.facebook}")
	private String urlFacebook;

	//@Autowired
	//private ChromeDriver chromeDriver;

	private FacturaElectronica crear(Factura factura) {
		//MAPEO A FACTURA ELECTRONICA
		FacturaElectronica facturaElectronica = new FacturaElectronica();
		InfoTributaria infoTributaria = new InfoTributaria();
		InfoFactura infoFactura = new InfoFactura();
		String ambiente = Constantes.vacio;
		if(facturacionProduccion.equals(Constantes.si)){
			ambiente = Constantes.produccion_sri;
		}
		if(facturacionProduccion.equals(Constantes.no)){
			ambiente = Constantes.pruebas_sri;
		}
		infoTributaria.setAmbiente(ambiente);
		infoTributaria.setTipoEmision(Constantes.emision_normal_sri);
		infoTributaria.setRazonSocial(factura.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial());
		infoTributaria.setNombreComercial(factura.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getNombreComercial());
		infoTributaria.setRuc(factura.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getIdentificacion());
		infoTributaria.setClaveAcceso(factura.getClaveAcceso());
		infoTributaria.setCodDoc(Constantes.factura_sri);
		infoTributaria.setEstab(factura.getUsuario().getEstacion().getEstablecimiento().getCodigoSRI());
		infoTributaria.setPtoEmi(factura.getUsuario().getEstacion().getCodigoSRI());
		infoTributaria.setSecuencial(factura.getSecuencial());
		infoTributaria.setDirMatriz(factura.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getDireccion());

		DateFormat dateFormat = new SimpleDateFormat(Constantes.fechaCortaSri);
		String fechaEmision = dateFormat.format(factura.getFecha());
		infoFactura.setFechaEmision(fechaEmision);
		infoFactura.setDirEstablecimiento(factura.getUsuario().getEstacion().getEstablecimiento().getDireccion());
		infoFactura.setObligadoContabilidad(factura.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getObligadoContabilidad());
		infoFactura.setTipoIdentificacionComprador(factura.getCliente().getTipoIdentificacion().getCodigoSRI());
		infoFactura.setRazonSocialComprador(factura.getCliente().getRazonSocial());
		infoFactura.setIdentificacionComprador(factura.getCliente().getIdentificacion());
		infoFactura.setDireccionComprador(factura.getCliente().getDireccion());
		infoFactura.setTotalSinImpuestos(Math.round(factura.getSubtotal() * 100.0)/100.0);
		infoFactura.setTotalDescuento(factura.getDescuento());
		infoFactura.setTotalConImpuestos(crearTotalConImpuestos(factura));
		infoFactura.setPropina(Constantes.cero);
		infoFactura.setImporteTotal(factura.getTotal());
		infoFactura.setMoneda(Constantes.moneda);
		infoFactura.setPagos(crearPagos(factura));

		Detalles detalles = crearDetalles(factura);

		InfoAdicional infoAdicional = crearInfoAdicional(factura);

		facturaElectronica.setInfoTributaria(infoTributaria);
		facturaElectronica.setInfoFactura(infoFactura);
		facturaElectronica.setDetalles(detalles);
		facturaElectronica.setInfoAdicional(infoAdicional);
		return facturaElectronica;
	}

	private TotalConImpuestos crearTotalConImpuestos(Factura factura){
		TotalConImpuestos totalConImpuestos = new TotalConImpuestos();
		List<TotalImpuesto> totalImpuestos = new ArrayList<>();
		double baseImponible0 = Constantes.cero;
		double baseImponible8 = Constantes.cero;
		double baseImponible12 = Constantes.cero;
		double baseImponible14 = Constantes.cero;
		double iva0 = Constantes.cero;
		double iva8 = Constantes.cero;
		double iva12 = Constantes.cero;
		double iva14 = Constantes.cero;
		boolean banderaIva0 = false;
		boolean banderaIva8 = false;
		boolean banderaIva12 = false;
		boolean banderaIva14 = false;
		for(FacturaLinea facturaLinea: factura.getFacturaLineas()){
			if(facturaLinea.getImpuesto().getCodigoSRI().equals(Constantes.iva_0_sri)){
				banderaIva0 = true;
				baseImponible0 = baseImponible0 + facturaLinea.getSubtotalLinea();
				iva0 = iva0 + facturaLinea.getImporteIvaLinea();
			}
			if(facturaLinea.getImpuesto().getCodigoSRI().equals(Constantes.iva_8_sri)){
				banderaIva8 = true;
				baseImponible8 = baseImponible8 + facturaLinea.getSubtotalLinea();
				iva8 = iva8 + facturaLinea.getImporteIvaLinea();
			}
			if(facturaLinea.getImpuesto().getCodigoSRI().equals(Constantes.iva_12_sri)){
				banderaIva12 = true;
				baseImponible12 = baseImponible12 + facturaLinea.getSubtotalLinea();
				iva12 = iva12 + facturaLinea.getImporteIvaLinea();
			}
			if(facturaLinea.getImpuesto().getCodigoSRI().equals(Constantes.iva_14_sri)){
				banderaIva14 = true;
				baseImponible14 = baseImponible14 + facturaLinea.getSubtotalLinea();
				iva14 = iva14 + facturaLinea.getImporteIvaLinea();
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
		totalConImpuestos.setTotalImpuesto(totalImpuestos);
		return totalConImpuestos;
	}

	private Pagos crearPagos(Factura factura) {
		Pagos pagos = new Pagos();
		List<Pago> pagosLista = new ArrayList<>();
		if(factura.getEfectivo()>0) {
			Pago pago = new Pago();
			pago.setFormaPago(Constantes.sin_utilizacion_del_sistema_financiero);
			pago.setTotal(factura.getEfectivo());
			pagosLista.add(pago);
		}

		for(Cheque cheque: factura.getCheques()) {
			Pago pago = new Pago();
			pago.setFormaPago(Constantes.otros_con_utilizacion_sistema_financiero);
			pago.setTotal(cheque.getValor());
			pagosLista.add(pago);
		}

		for(Deposito deposito: factura.getDepositos()) {
			Pago pago = new Pago();
			pago.setFormaPago(Constantes.otros_con_utilizacion_sistema_financiero);
			pago.setTotal(deposito.getValor());
			pagosLista.add(pago);
		}

		for(Transferencia transferencia: factura.getTransferencias()) {
			Pago pago = new Pago();
			pago.setFormaPago(Constantes.otros_con_utilizacion_sistema_financiero);
			pago.setTotal(transferencia.getValor());
			pagosLista.add(pago);
		}

		for(TarjetaDebito tarjetaDebito: factura.getTarjetasDebitos()) {
			Pago pago = new Pago();
			pago.setFormaPago(Constantes.tarjeta_de_debito);
			pago.setTotal(tarjetaDebito.getValor());
			pagosLista.add(pago);
		}

		for(TarjetaCredito tarjetaCredito: factura.getTarjetasCreditos()) {
			Pago pago = new Pago();
			pago.setFormaPago(Constantes.tarjeta_de_credito);
			pago.setTotal(tarjetaCredito.getValor());
			pagosLista.add(pago);
		}
		if(factura.getCredito() != null && factura.getCredito().getSaldo() > Constantes.cero) {
			Pago pago = new Pago();
			pago.setFormaPago(Constantes.otros_con_utilizacion_sistema_financiero);
			pago.setTotal(factura.getCredito().getSaldo());
			pago.setUnidadTiempo(factura.getCredito().getUnidadTiempo());
			pago.setPlazo(factura.getCredito().getPlazo());
			pagosLista.add(pago);
		}
		pagos.setPago(pagosLista);
		return pagos;
	}

	private Detalles crearDetalles(Factura factura) {
		Detalles detalles = new Detalles();
		List<Detalle> detalleLista = new ArrayList<>();
		for(FacturaLinea facturaLinea: factura.getFacturaLineas()) {
			Detalle detalle = new Detalle();
			detalle.setCodigoPrincipal(facturaLinea.getProducto().getCodigo());
			detalle.setDescripcion(facturaLinea.getNombreProducto());
			detalle.setCantidad(facturaLinea.getCantidad());
			detalle.setPrecioUnitario(Math.round(facturaLinea.getPrecioUnitario() * 100.0) / 100.0);
			detalle.setDescuento(facturaLinea.getValorDescuentoLinea() + facturaLinea.getValorPorcentajeDescuentoLinea());
			detalle.setPrecioTotalSinImpuesto(Math.round(facturaLinea.getSubtotalLinea() * 100.0) / 100.0);
			detalle.setImpuestos(crearImpuestos(facturaLinea));
			detalleLista.add(detalle);
		}
		detalles.setDetalle(detalleLista);
		return detalles;
	}

	private Impuestos crearImpuestos(FacturaLinea facturaLinea) {
		Impuestos impuestos = new Impuestos();
		List<Impuesto> impuestoLista = new ArrayList<>();
		Impuesto impuesto = new Impuesto();
		impuesto.setCodigo(Constantes.iva_sri);
		impuesto.setCodigoPorcentaje(facturaLinea.getImpuesto().getCodigoSRI());
		impuesto.setTarifa(facturaLinea.getImpuesto().getPorcentaje());
		impuesto.setBaseImponible(Math.round(facturaLinea.getSubtotalLinea()*100.0)/100.0);
		impuesto.setValor(Math.round(facturaLinea.getImporteIvaLinea()*100.0)/100.0);
		impuestoLista.add(impuesto);
		impuestos.setImpuesto(impuestoLista);
		return impuestos;
	}

	private InfoAdicional crearInfoAdicional(Factura factura) {
		List<CampoAdicional> camposAdicionales = new ArrayList<>();
		if(!factura.getCliente().getTelefonos().isEmpty()) {
			String telefono = factura.getCliente().getTelefonos().get(0).getNumero();
			CampoAdicional campoAdicional = new CampoAdicional();
			campoAdicional.setNombre(Constantes.telefono);
			campoAdicional.setValor(telefono);
			camposAdicionales.add(campoAdicional);
		}
		if(!factura.getCliente().getCelulares().isEmpty()){
			String celular = factura.getCliente().getCelulares().get(0).getNumero();
			CampoAdicional campoAdicional = new CampoAdicional();
			campoAdicional.setNombre(Constantes.celular);
			campoAdicional.setValor(celular);
			camposAdicionales.add(campoAdicional);
		}
		if(!factura.getCliente().getCorreos().isEmpty()) {
			String correo = factura.getCliente().getCorreos().get(0).getEmail();
			CampoAdicional campoAdicional = new CampoAdicional();
			campoAdicional.setNombre(Constantes.correo);
			campoAdicional.setValor(correo);
			camposAdicionales.add(campoAdicional);
		}
		if(factura.getCliente().getDireccion().length() > Constantes.cero) {
			String direccion = factura.getCliente().getDireccion();
			CampoAdicional campoAdicional = new CampoAdicional();
			campoAdicional.setNombre(Constantes.direccion);
			campoAdicional.setValor(direccion);
			camposAdicionales.add(campoAdicional);
		}
		CampoAdicional campoAdicional = new CampoAdicional();
		campoAdicional.setNombre(Constantes.valor);
		campoAdicional.setValor(factura.getTotal() + Constantes.vacio);
		camposAdicionales.add(campoAdicional);
		InfoAdicional infoAdicional = new InfoAdicional();
		infoAdicional.setCampoAdicional(camposAdicionales);
		return infoAdicional;
	}

	@Override
	public Factura enviar(long facturaId) throws MalformedURLException {
		Optional<Factura> opcional = rep.findById(facturaId);
		if(opcional.isEmpty()) {
			throw new EntidadNoExistenteException(Constantes.factura);
		}
		Factura factura = opcional.get();
		boolean banderaSuscripcion = suscripcionService.verificar(factura.getEmpresa().getId());
		if(!banderaSuscripcion){
			throw new SuscripcionInvalidaException();
		}
		Resource certificado = empresaService.bajarCertificado(factura.getEmpresa().getId());
		if(certificado == null){
			throw new CertificadoNoExistenteException();
		}
		if(factura.getEmpresa().getContrasena().equals(Constantes.vacio)){
			throw new FacturaElectronicaInvalidaException(Constantes.contrasena);
		}
		if(factura.getEstado().equals(Constantes.estadoEmitida)){
			throw new EstadoInvalidoException(Constantes.estadoEmitida);
		}
		if(factura.getProcesoSRI().equals(Constantes.procesoSRIAutorizada)){
			throw new EstadoInvalidoException(Constantes.procesoSRIAutorizada);
		}
		if(factura.getProcesoSRI().equals(Constantes.procesoSRIAnulada)){
			throw new EstadoInvalidoException(Constantes.procesoSRIAnulada);
		}
		FacturaElectronica facturaElectronica = crear(factura);
		List<String> estadoRecepcion = recepcion(facturaElectronica, factura.getEmpresa().getCertificado(), factura.getEmpresa().getContrasena());
		if(estadoRecepcion.get(0).equals(Constantes.devueltaSri)) {
			throw new FacturaElectronicaInvalidaException("ESTADO DEL SRI:" + Constantes.espacio + estadoRecepcion.get(0) + Constantes.espacio + Constantes.guion + Constantes.espacio + "INFORMACION ADICIONAL: " + estadoRecepcion.get(1));
		}
		List<String> estadoAutorizacion = autorizacion(facturaElectronica);
		if(estadoAutorizacion.get(0).equals(Constantes.devueltaSri)) {
			throw new FacturaElectronicaInvalidaException("ESTADO DEL SRI:" + Constantes.espacio + estadoRecepcion.get(0) + Constantes.espacio + Constantes.guion + Constantes.espacio + "INFORMACION ADICIONAL: " + estadoRecepcion.get(1));
		}
		if(estadoAutorizacion.get(0).equals(Constantes.noAutorizadoSri)){
			throw new FacturaElectronicaInvalidaException("ESTADO DEL SRI:" + Constantes.espacio + estadoAutorizacion.get(0) + Constantes.espacio + Constantes.guion + Constantes.espacio + "INFORMACION ADICIONAL: " + estadoAutorizacion.get(1));
		}
		if(estadoAutorizacion.get(0).equals(Constantes.autorizadoSri)){
			suscripcionService.aumentarConteo(factura.getEmpresa().getId());
			factura.setProcesoSRI(Constantes.procesoSRIAutorizada);
			factura.setFechaAutorizacion(new Date());
			enviarCorreo(factura, facturaElectronica);
		}
		Factura facturada = rep.save(factura);
		facturada.normalizar();
		return facturada;
	}

	private List<String> recepcion(FacturaElectronica facturaElectronica, String certificado, String contrasena) {
		try {
			String url = Constantes.vacio;
			if(facturacionProduccion.equals(Constantes.si)){
				url = Constantes.urlProduccionFacturacionEletronicaSri;
			}
			if(facturacionProduccion.equals(Constantes.no)){
				url = Constantes.urlPruebasFacturacionEletronicaSri;
			}
			JAXBContext jaxbContext = JAXBContext.newInstance(FacturaElectronica.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, Constantes.utf8);
			jaxbMarshaller.marshal(facturaElectronica, System.out);
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(facturaElectronica, sw);
			String xml = sw.toString();
			Path path = Paths.get(Constantes.pathRecursos + Constantes.pathCertificados + Constantes.slash + certificado);
			String ruta = path.toAbsolutePath().toString();
			byte[] cert = ConvertFile.readBytesFromFile(ruta);
			byte[] firmado = SignatureXAdESBES.firmarByteData(xml.getBytes(), cert, contrasena);
			String encode = Base64.getEncoder().encodeToString(firmado);
			String body = Util.soapFacturacionEletronica(encode);
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

	private List<String> autorizacion(FacturaElectronica facturaElectronica){
		try {
			String url = Constantes.vacio;
			if(facturacionProduccion.equals(Constantes.si)){
				url = Constantes.urlProduccionConsultaFacturacionEletronicaSri;
			}
			if(facturacionProduccion.equals(Constantes.no)){
				url = Constantes.urlPruebasConsultaFacturacionEletronicaSri;
			}
			String body = Util.soapConsultaFacturacionEletronica(facturaElectronica.getInfoTributaria().getClaveAcceso());
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

	public ByteArrayInputStream crearPDF(Factura factura) {
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
			if(factura.getEmpresa().getLogo().equals(Constantes.vacio)){
				documento.add(new Paragraph("LOGO").setFontSize(50).setTextAlignment(TextAlignment.CENTER));
			}
			if(!factura.getEmpresa().getLogo().equals(Constantes.vacio)){
				Path path = Paths.get(Constantes.pathRecursos + Constantes.pathLogos + Constantes.slash + factura.getEmpresa().getLogo());
				ImageData imageData = ImageDataFactory.create(path.toAbsolutePath().toString());
				Image image = new Image(imageData).scaleAbsolute(150, 100);
				documento.add(image);
			}
			String regimen = Constantes.vacio;
			if(factura.getUsuario().getEstacion().getEstablecimiento().getRegimen() != null) {
				regimen = factura.getUsuario().getEstacion().getEstablecimiento().getRegimen().getDescripcion();
			}
			if(factura.getUsuario().getEstacion().getRegimen() != null) {
				regimen = factura.getUsuario().getEstacion().getRegimen().getDescripcion();
			}
			String contribuyenteEspecial = Constantes.vacio;
			if(factura.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getEspecial().equals(Constantes.si)){
				contribuyenteEspecial = factura.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getResolucionEspecial();
			}
			if(factura.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getEspecial().equals(Constantes.no)){
				contribuyenteEspecial = Constantes.no;
			}
			String agenteRetencion = Constantes.vacio;
			if(factura.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getAgenteRetencion().equals(Constantes.si)){
				agenteRetencion = factura.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getResolucionAgente();
			}
			if(factura.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getAgenteRetencion().equals(Constantes.no)){
				agenteRetencion = Constantes.no;
			}
			float [] columnas = {320F, 280F};
			Table tabla = new Table(columnas);
			tabla.addCell(getCellEmpresa(factura.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial() +"\n" + "\n" +
					"DIRECCIÓN MATRIZ: " + factura.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getDireccion() +"\n" + "\n" +
					"DIRECCIÓN SUCURSAL: " + factura.getUsuario().getEstacion().getEstablecimiento().getDireccion() +"\n" + "\n" +
					regimen + "\n" + "\n" +
					"CONTRIBUYENTE ESPECIAL: " + contribuyenteEspecial + "\n" + "\n" +
					"OBLIGADO A LLEVAR CONTABILIDAD: " + factura.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getObligadoContabilidad() + "\n" + "\n" +
					"AGENTE RETENCION RESOLUCIÓN: " + agenteRetencion, TextAlignment.LEFT));
			String numeroAutorizacion = Constantes.vacio;
			String fechaAutorizacion = Constantes.vacio;
			Image imagenCodigoBarras = null;
			if(factura.getProcesoSRI().equals(Constantes.procesoSRIAutorizada)){
				numeroAutorizacion = factura.getClaveAcceso();
				fechaAutorizacion = formatoFecha.format(factura.getFechaAutorizacion());
				Barcode128 codigoBarras = new Barcode128(pdf);
				codigoBarras.setCodeType(Barcode128.CODE128);
				codigoBarras.setCode(factura.getClaveAcceso());
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
			tabla.addCell(getCellFactura("RUC: "+factura.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getIdentificacion()+"\n"+
					"FACTURA"+"\n"+
					"No. " + factura.getNumeroComprobante() + "\n" +
					"NÚMERO DE AUTORIZACIÓN: " + numeroAutorizacion + "\n" +
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
			tablaCliente.addCell(getCellCliente("RAZÓN SOCIAL: "+factura.getCliente().getRazonSocial()+"\n" + "FECHA EMISIÓN: " + formatoFecha.format(factura.getFecha()) + "\n" +
					"DIRECCION: " + factura.getCliente().getDireccion() + "\n", TextAlignment.LEFT));
			tablaCliente.addCell(getCellCliente("IDENTIFICACIÓN: " + factura.getCliente().getIdentificacion() + "\n"+ "GUIA: " + "\t" + "\t"+ "\t" + "\t"+ "\t"+ "\t"+ "\t"+ "\t", TextAlignment.RIGHT));
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
			for (int i = 0; i <factura.getFacturaLineas().size(); i++)
			{
				String precioUnitario = String.format("%.2f", factura.getFacturaLineas().get(i).getPrecioUnitario());
				String descuentoLinea = String.format("%.2f", factura.getFacturaLineas().get(i).getValorDescuentoLinea());
				String porcentajeDescuentoLinea = factura.getFacturaLineas().get(i).getPorcentajeDescuentoLinea() + Constantes.vacio;
				String subtotalConDescuentoLinea = String.format("%.2f", factura.getFacturaLineas().get(i).getSubtotalLinea());

				tablaFacturaDetalle.addCell(getCellFilaFactura(factura.getFacturaLineas().get(i).getProducto().getCodigo()));
				tablaFacturaDetalle.addCell(getCellFilaFactura(factura.getFacturaLineas().get(i).getCantidad() + Constantes.vacio));
				tablaFacturaDetalle.addCell(getCellFilaFactura(factura.getFacturaLineas().get(i).getNombreProducto()));
				tablaFacturaDetalle.addCell(getCellFilaFactura("$"+precioUnitario));
				tablaFacturaDetalle.addCell(getCellFilaFactura("$"+descuentoLinea));
				tablaFacturaDetalle.addCell(getCellFilaFactura(porcentajeDescuentoLinea+"%"));
				tablaFacturaDetalle.addCell(getCellFilaFactura("$"+subtotalConDescuentoLinea));
			}
			documento.add(tablaFacturaDetalle);
			documento.add( new Paragraph("\n"));
			String subtotal = String.format("%.2f", factura.getSubtotal());
			String descuento = String.format("%.2f", factura.getDescuento());
			String subtotalGravadoConDescuento = String.format("%.2f", factura.getSubtotalGravado());
			String subtotalNoGravadoConDescuento = String.format("%.2f", factura.getSubtotalNoGravado());
			String iva = String.format("%.2f", factura.getImporteIva());
			String totalConDescuento = String.format("%.2f", factura.getTotal());
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
			if (!factura.getCliente().getTelefonos().isEmpty()){
				telefonoCliente = factura.getCliente().getTelefonos().get(0).getNumero();
			}
			if (!factura.getCliente().getCelulares().isEmpty()){
				celularCliente = factura.getCliente().getCelulares().get(0).getNumero();
			}
			if (!factura.getCliente().getCorreos().isEmpty()){
				correoCliente = factura.getCliente().getCorreos().get(0).getEmail();
			}
			String comentario = factura.getComentario();
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
			if(factura.getEfectivo() > Constantes.cero) {
				tablaFormaPago.addCell(getCellFormaPago(Constantes.sin_utilizacion_del_sistema_financiero + Constantes.espacio + Constantes.guion + Constantes.espacio + Constantes.texto_sin_utilizacion_del_sistema_financiero));
				String valor = String.format("%.2f", factura.getEfectivo() - factura.getCambio());
				tablaFormaPago.addCell(getCellFormaPago(valor));
			}
			for(Cheque cheque: factura.getCheques()) {
				tablaFormaPago.addCell(getCellFormaPago(Constantes.otros_con_utilizacion_sistema_financiero + Constantes.espacio + Constantes.guion + Constantes.espacio + Constantes.texto_otros_con_utilizacion_sistema_financiero));
				String valor = String.format("%.2f", cheque.getValor());
				tablaFormaPago.addCell(getCellFormaPago(valor));
			}
			for(Deposito deposito: factura.getDepositos()) {
				tablaFormaPago.addCell(getCellFormaPago(Constantes.otros_con_utilizacion_sistema_financiero + Constantes.espacio + Constantes.guion + Constantes.espacio + Constantes.texto_otros_con_utilizacion_sistema_financiero));
				String valor = String.format("%.2f", deposito.getValor());
				tablaFormaPago.addCell(getCellFormaPago(valor));
			}
			for(Transferencia transferencia: factura.getTransferencias()) {
				tablaFormaPago.addCell(getCellFormaPago(Constantes.otros_con_utilizacion_sistema_financiero + Constantes.espacio + Constantes.guion + Constantes.espacio + Constantes.texto_otros_con_utilizacion_sistema_financiero));
				String valor = String.format("%.2f", transferencia.getValor());
				tablaFormaPago.addCell(getCellFormaPago(valor));
			}
			for(TarjetaDebito tarjetaDebito: factura.getTarjetasDebitos()) {
				tablaFormaPago.addCell(getCellFormaPago(Constantes.tarjeta_de_debito + Constantes.espacio + Constantes.guion + Constantes.espacio + Constantes.texto_tarjeta_de_debito));
				String valor = String.format("%.2f", tarjetaDebito.getValor());
				tablaFormaPago.addCell(getCellFormaPago(valor));
			}
			for(TarjetaCredito tarjetaCredito: factura.getTarjetasCreditos()) {
				tablaFormaPago.addCell(getCellFormaPago(Constantes.tarjeta_de_credito + Constantes.espacio + Constantes.guion + Constantes.espacio + Constantes.texto_tarjeta_de_credito));
				String valor = String.format("%.2f", tarjetaCredito.getValor());
				tablaFormaPago.addCell(getCellFormaPago(valor));
			}
			if(factura.getCredito()!= null && factura.getCredito().getSaldo() > Constantes.cero) {
				tablaFormaPago.addCell(getCellFormaPago(Constantes.otros_con_utilizacion_sistema_financiero + Constantes.espacio + Constantes.guion + Constantes.espacio + Constantes.texto_otros_con_utilizacion_sistema_financiero));
				String valor = String.format("%.2f", factura.getCredito().getSaldo());
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

	private ByteArrayInputStream crearXML(FacturaElectronica facturaElectronica) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(FacturaElectronica.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, Constantes.utf8);
			jaxbMarshaller.marshal(facturaElectronica, System.out);
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(facturaElectronica, sw);
			String xml=sw.toString();
			return new ByteArrayInputStream(xml.getBytes());
		} catch(Exception e) {
			return null;
		}
	}

	private void enviarCorreo(Factura factura, FacturaElectronica facturaElectronica) {
		try {
			ByteArrayInputStream pdf = crearPDF(factura);
			ByteArrayInputStream xml = crearXML(facturaElectronica);
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
			parte1.setFileName(Constantes.factura + factura.getSecuencial() + Constantes.extensionPdf);
			MimeBodyPart parte2 = new MimeBodyPart();
			parte2.setDataHandler(new DataHandler(xmlData));
			parte2.setFileName(Constantes.factura + factura.getSecuencial() + Constantes.extensionXml);

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(parte1);
			multipart.addBodyPart(parte2);

			message.setFrom(new InternetAddress(correoUsuario));
			String correo = Constantes.vacio;
			if(factura.getCliente().getCorreos().isEmpty()){
				correo = Constantes.correoPorDefecto;
			}
			if(!factura.getCliente().getCorreos().isEmpty()){
				correo = factura.getCliente().getCorreos().get(0).getEmail();
			}
			message.addRecipients(Message.RecipientType.TO, correo);
			message.setSubject(factura.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial()+ Constantes.mensajeCorreo + factura.getNumeroComprobante());
			message.setText(Constantes.vacio);
			message.setContent(multipart);
			Transport transport = session.getTransport(Constantes.smtp);
			transport.connect(Constantes.smtpGmailCom, correoUsuario, correoContrasena);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ByteArrayInputStream crearTicket(Factura factura) {
		try {
			DateFormat formatoFecha = new SimpleDateFormat(Constantes.fechaYHora);
			ByteArrayOutputStream salida = new ByteArrayOutputStream();
			PdfWriter writer = new PdfWriter(salida);
			PdfDocument pdf = new PdfDocument(writer);
			// Initialize document
			Document documento = new Document(pdf, PageSize.A7);
			documento.setMargins(0,0,0,0);
			// 4. Add content
			PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
			documento.setFont(font);
			documento.add(new Paragraph("LOGO").setFontSize(10).setTextAlignment(TextAlignment.CENTER));
			float[] columnas = {600F};
			Table tabla = new Table(columnas);
			tabla.addCell(getCellEmpresaTicket(factura.getEmpresa().getRazonSocial() + "\n" +
					factura.getEmpresa().getNombreComercial() + "\n" +
					"RUC: " + factura.getEmpresa().getIdentificacion() + "\n", TextAlignment.CENTER));
			documento.add(tabla);
			String numeroAutorizacion = Constantes.vacio;
			String fechaAutorizacion = Constantes.vacio;
			if(factura.getProcesoSRI().equals(Constantes.procesoSRIAutorizada)){
				numeroAutorizacion = factura.getClaveAcceso();
				fechaAutorizacion = formatoFecha.format(factura.getFechaAutorizacion());
			}
			float [] columnasFactura = {600F};
			Table tablaFactura = new Table(columnasFactura);
			tablaFactura.addCell(getCellFacturaTicket(
					"FACTURA" + "\n" +
					"No. " + factura.getNumeroComprobante() + "\n" +
					"NÚMERO DE AUTORIZACIÓN: " + numeroAutorizacion + "\n" +
					"FECHA DE AUTORIZACIÓN: " + fechaAutorizacion + "\n" , TextAlignment.LEFT));
			documento.add(tablaFactura);
			String regimen = Constantes.vacio;
			if(factura.getUsuario().getEstacion().getEstablecimiento().getRegimen() != null) {
				regimen = factura.getUsuario().getEstacion().getEstablecimiento().getRegimen().getDescripcion();
			}
			if(factura.getUsuario().getEstacion().getRegimen() != null) {
				regimen = factura.getUsuario().getEstacion().getRegimen().getDescripcion();
			}
			String contribuyenteEspecial = Constantes.vacio;
			if(factura.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getEspecial().equals(Constantes.si)){
				contribuyenteEspecial = factura.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getResolucionEspecial();
			}
			if(factura.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getEspecial().equals(Constantes.no)){
				contribuyenteEspecial = Constantes.no;
			}
			String agenteRetencion = Constantes.vacio;
			if(factura.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getAgenteRetencion().equals(Constantes.si)){
				agenteRetencion = factura.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getResolucionAgente();
			}
			if(factura.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getAgenteRetencion().equals(Constantes.no)){
				agenteRetencion = Constantes.no;
			}
			float [] columnasEmpresa = {600F};
			Table tablaEmpresa = new Table(columnasEmpresa);
			tablaEmpresa.addCell(getCellEmpresaTicket(
					"DIRECCIÓN MATRIZ: " + factura.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getDireccion() +"\n" +
					"DIRECCIÓN SUCURSAL: " + factura.getUsuario().getEstacion().getEstablecimiento().getDireccion() +"\n" +
					regimen + "\n" +
					"CONTRIBUYENTE ESPECIAL: " + contribuyenteEspecial + "\n" +
					"OBLIGADO A LLEVAR CONTABILIDAD: " + factura.getUsuario().getEstacion().getEstablecimiento().getEmpresa().getObligadoContabilidad() + "\n" +
					"AGENTE RETENCION RESOLUCIÓN: " + agenteRetencion, TextAlignment.LEFT));
			documento.add(tablaEmpresa);
			float [] columnasCliente = {600F};
			Table tablaCliente = new Table(columnasCliente);
			tablaCliente.addCell(getCellClienteTicket("CLIENTE: "+factura.getCliente().getRazonSocial()+"\n" +
					"FECHA EMISIÓN: " + formatoFecha.format(factura.getFecha()) + "\n" +
					"DIRECCION: " + factura.getCliente().getDireccion() + "\n" +
					"IDENTIFICACIÓN: " + factura.getCliente().getIdentificacion(), TextAlignment.LEFT));
			documento.add(tablaCliente);
			float [] columnasTablaFacturaDetalle = {150F, 150F, 150F, 150F};
			Table tablaFacturaDetalle = new Table(columnasTablaFacturaDetalle);
			tablaFacturaDetalle.addCell(getCellColumnaFacturaTicket("CANT"));
			tablaFacturaDetalle.addCell(getCellColumnaFacturaTicket("DESCRIPCION"));
			tablaFacturaDetalle.addCell(getCellColumnaFacturaTicket("PRECIO U"));
			tablaFacturaDetalle.addCell(getCellColumnaFacturaTicket("SUBTOTAL"));
			for (int i = 0; i <factura.getFacturaLineas().size(); i++)
			{
				String precioUnitario = String.format("%.2f", factura.getFacturaLineas().get(i).getPrecioUnitario());
				String subtotalConDescuentoLinea = String.format("%.2f", factura.getFacturaLineas().get(i).getSubtotalLinea());

				tablaFacturaDetalle.addCell(getCellFilaFacturaTicket(factura.getFacturaLineas().get(i).getCantidad() + Constantes.vacio));
				tablaFacturaDetalle.addCell(getCellFilaFacturaTicket(factura.getFacturaLineas().get(i).getNombreProducto()));
				tablaFacturaDetalle.addCell(getCellFilaFacturaTicket("$"+precioUnitario));
				tablaFacturaDetalle.addCell(getCellFilaFacturaTicket("$"+subtotalConDescuentoLinea));
			}

			String subtotalGravadoConDescuento = String.format("%.2f", factura.getSubtotalGravado());
			String subtotalNoGravadoConDescuento = String.format("%.2f", factura.getSubtotalNoGravado());
			String iva = String.format("%.2f", factura.getImporteIva());
			String totalConDescuento = String.format("%.2f", factura.getTotal());
			tablaFacturaDetalle.addCell(getCellVacio(Constantes.vacio));
			tablaFacturaDetalle.addCell(getCellVacio(Constantes.vacio));
			tablaFacturaDetalle.addCell(getCellFilaFacturaTicket("SUBTOTAL 12%"));
			tablaFacturaDetalle.addCell(getCellFilaFacturaTicket("$" + subtotalGravadoConDescuento));
			tablaFacturaDetalle.addCell(getCellVacio(Constantes.vacio));
			tablaFacturaDetalle.addCell(getCellVacio(Constantes.vacio));
			tablaFacturaDetalle.addCell(getCellFilaFacturaTicket("SUBTOTAL 0%"));
			tablaFacturaDetalle.addCell(getCellFilaFacturaTicket("$" + subtotalNoGravadoConDescuento));
			tablaFacturaDetalle.addCell(getCellVacio(Constantes.vacio));
			tablaFacturaDetalle.addCell(getCellVacio(Constantes.vacio));
			tablaFacturaDetalle.addCell(getCellFilaFacturaTicket("IVA"));
			tablaFacturaDetalle.addCell(getCellFilaFacturaTicket("$" + iva));
			tablaFacturaDetalle.addCell(getCellVacio(Constantes.vacio));
			tablaFacturaDetalle.addCell(getCellVacio(Constantes.vacio));
			tablaFacturaDetalle.addCell(getCellFilaFacturaTicket("TOTAL"));
			tablaFacturaDetalle.addCell(getCellFilaFacturaTicket("$" + totalConDescuento));
			documento.add(tablaFacturaDetalle);

			float [] columnasTablaFormaPago = {200F, 100F};
			Table tablaFormaPago = new Table(columnasTablaFormaPago);
			tablaFormaPago.addCell(getCellFormaPagoTicket("FORMA DE PAGO"));
			tablaFormaPago.addCell(getCellFormaPagoTicket("VALOR"));
			if(factura.getEfectivo() > Constantes.cero) {
				tablaFormaPago.addCell(getCellFormaPagoTicket(Constantes.sin_utilizacion_del_sistema_financiero + Constantes.espacio + Constantes.guion + Constantes.espacio + Constantes.texto_sin_utilizacion_del_sistema_financiero));
				String valor = String.format("%.2f", factura.getEfectivo());
				tablaFormaPago.addCell(getCellFormaPagoTicket(valor));
			}
			for(Cheque cheque: factura.getCheques()) {
				tablaFormaPago.addCell(getCellFormaPagoTicket(Constantes.otros_con_utilizacion_sistema_financiero + Constantes.espacio + Constantes.guion + Constantes.espacio + Constantes.texto_otros_con_utilizacion_sistema_financiero));
				String valor = String.format("%.2f", cheque.getValor());
				tablaFormaPago.addCell(getCellFormaPagoTicket(valor));
			}
			for(Deposito deposito: factura.getDepositos()) {
				tablaFormaPago.addCell(getCellFormaPagoTicket(Constantes.otros_con_utilizacion_sistema_financiero + Constantes.espacio + Constantes.guion + Constantes.espacio + Constantes.texto_otros_con_utilizacion_sistema_financiero));
				String valor = String.format("%.2f", deposito.getValor());
				tablaFormaPago.addCell(getCellFormaPagoTicket(valor));
			}
			for(Transferencia transferencia: factura.getTransferencias()) {
				tablaFormaPago.addCell(getCellFormaPagoTicket(Constantes.otros_con_utilizacion_sistema_financiero + Constantes.espacio + Constantes.guion + Constantes.espacio + Constantes.texto_otros_con_utilizacion_sistema_financiero));
				String valor = String.format("%.2f", transferencia.getValor());
				tablaFormaPago.addCell(getCellFormaPagoTicket(valor));
			}
			for(TarjetaDebito tarjetaDebito: factura.getTarjetasDebitos()) {
				tablaFormaPago.addCell(getCellFormaPagoTicket(Constantes.tarjeta_de_debito + Constantes.espacio + Constantes.guion + Constantes.espacio + Constantes.texto_tarjeta_de_debito));
				String valor = String.format("%.2f", tarjetaDebito.getValor());
				tablaFormaPago.addCell(getCellFormaPagoTicket(valor));
			}
			for(TarjetaCredito tarjetaCredito: factura.getTarjetasCreditos()) {
				tablaFormaPago.addCell(getCellFormaPagoTicket(Constantes.tarjeta_de_credito + Constantes.espacio + Constantes.guion + Constantes.espacio + Constantes.texto_tarjeta_de_credito));
				String valor = String.format("%.2f", tarjetaCredito.getValor());
				tablaFormaPago.addCell(getCellFormaPagoTicket(valor));
			}
			if(factura.getCredito()!= null && factura.getCredito().getSaldo() > Constantes.cero) {
				tablaFormaPago.addCell(getCellFormaPagoTicket(Constantes.otros_con_utilizacion_sistema_financiero + Constantes.espacio + Constantes.guion + Constantes.espacio + Constantes.texto_otros_con_utilizacion_sistema_financiero));
				String valor = String.format("%.2f", factura.getCredito().getSaldo());
				tablaFormaPago.addCell(getCellFormaPagoTicket(valor));
			}
			tablaFormaPago.setHorizontalAlignment(HorizontalAlignment.LEFT);
			documento.add(tablaFormaPago);

			String telefonoCliente = Constantes.vacio;
			String celularCliente = Constantes.vacio;
			String correoCliente = Constantes.vacio;
			if (!factura.getCliente().getTelefonos().isEmpty()){
				telefonoCliente = factura.getCliente().getTelefonos().get(0).getNumero();
			}
			if (!factura.getCliente().getCelulares().isEmpty()){
				celularCliente = factura.getCliente().getCelulares().get(0).getNumero();
			}
			if (!factura.getCliente().getCorreos().isEmpty()){
				correoCliente = factura.getCliente().getCorreos().get(0).getEmail();
			}
			String comentario = factura.getComentario();
			float [] columnasAdicional = {150F, 450F};
			Table tablaAdicional = new Table(columnasAdicional);
			tablaAdicional.addCell(getCellAdicionalTicket("COMENTARIO"));
			tablaAdicional.addCell(getCellAdicionalTicket(comentario));
			tablaAdicional.addCell(getCellAdicionalTicket("TELEFONO"));
			tablaAdicional.addCell(getCellAdicionalTicket(telefonoCliente));
			tablaAdicional.addCell(getCellAdicionalTicket("CELULAR"));
			tablaAdicional.addCell(getCellAdicionalTicket(celularCliente));
			tablaAdicional.addCell(getCellAdicionalTicket("CORREO"));
			tablaAdicional.addCell(getCellAdicionalTicket(correoCliente));
			documento.add(tablaAdicional);
			// 5. Close document
			documento.close();
			return new ByteArrayInputStream(salida.toByteArray());
		} catch(Exception e){
			return null;
		}
	}

	private Cell getCellVacio(String text) {
		Paragraph parrafo = new Paragraph(text);
		Cell cell = new Cell();
		cell.add(parrafo);
		cell.setFontSize(Constantes.fontSize6);
		cell.setBorder(Border.NO_BORDER);
		return cell;
	}

	private Cell getCellEmpresaTicket(String text, TextAlignment alignment) {
		Cell cell = new Cell().add(new Paragraph(text));
		cell.setTextAlignment(alignment);
		cell.setBorder(new SolidBorder(ColorConstants.BLUE, 2));
		cell.setBorderTopLeftRadius(new BorderRadius(5));
		cell.setBorderTopRightRadius(new BorderRadius(5));
		cell.setBorderBottomLeftRadius(new BorderRadius(5));
		cell.setBorderBottomRightRadius(new BorderRadius(5));
		cell.setFontSize(Constantes.fontSize6);
		return cell;
	}
	private Cell getCellFacturaTicket(String text, TextAlignment alignment) {
		Paragraph parrafo = new Paragraph(text);
		Cell cell = new Cell();
		cell.add(parrafo);
		cell.setTextAlignment(alignment);
		cell.setBorder(new SolidBorder(ColorConstants.BLUE, 2));
		cell.setBorderTopLeftRadius(new BorderRadius(5));
		cell.setBorderTopRightRadius(new BorderRadius(5));
		cell.setBorderBottomLeftRadius(new BorderRadius(5));
		cell.setBorderBottomRightRadius(new BorderRadius(5));
		cell.setFontSize(Constantes.fontSize6);
		return cell;
	}
	private Cell getCellClienteTicket(String text, TextAlignment alignment) {
		Cell cell = new Cell().add(new Paragraph(text));
		cell.setTextAlignment(alignment);
		cell.setBorder(new SolidBorder(ColorConstants.BLUE, 2));
		cell.setBorderTopLeftRadius(new BorderRadius(5));
		cell.setBorderTopRightRadius(new BorderRadius(5));
		cell.setBorderBottomLeftRadius(new BorderRadius(5));
		cell.setBorderBottomRightRadius(new BorderRadius(5));
		cell.setFontSize(Constantes.fontSize6);
		return cell;
	}
	private Cell getCellColumnaFacturaTicket(String text) {
		Paragraph parrafo = new Paragraph(text);
		Cell cell = new Cell();
		cell.add(parrafo);
		cell.setFontSize(Constantes.fontSize6);
		cell.setBackgroundColor(ColorConstants.BLUE).setFontColor(ColorConstants.WHITE);
		cell.setBorder(new SolidBorder(ColorConstants.BLUE,1));
		return cell;
	}
	private Cell getCellFilaFacturaTicket(String text) {
		Paragraph parrafo = new Paragraph(text);
		Cell cell = new Cell();
		cell.add(parrafo);
		cell.setFontSize(Constantes.fontSize6);
		cell.setBorder(new SolidBorder(ColorConstants.BLUE,1));
		return cell;
	}
	private Cell getCellAdicionalTicket(String text) {
		Paragraph parrafo = new Paragraph(text);
		Cell cell = new Cell();
		cell.add(parrafo);
		cell.setFontSize(Constantes.fontSize6);
		cell.setBorder(new SolidBorder(ColorConstants.BLUE,1));
		return cell;
	}

	private Cell getCellFormaPagoTicket(String text) {
		Paragraph parrafo = new Paragraph(text);
		Cell cell = new Cell();
		cell.add(parrafo);
		cell.setFontSize(Constantes.fontSize6);
		cell.setBorder(new SolidBorder(ColorConstants.BLUE,1));
		return cell;
	}

	@Override
	public ByteArrayInputStream obtenerPDF(long facturaId){
		Optional<Factura> opcional= rep.findById(facturaId);
		if(opcional.isEmpty()) {
			throw new EntidadNoExistenteException(Constantes.factura);
		}
		Factura factura = opcional.get();
		ByteArrayInputStream pdf = crearPDF(factura);
		return pdf;
	}

	@Override
	public void enviarPDFYXML(long facturaId){
		Optional<Factura> opcional= rep.findById(facturaId);
		if(opcional.isEmpty()) {
			throw new EntidadNoExistenteException(Constantes.factura);
		}
		Factura factura = opcional.get();
		FacturaElectronica facturaElectronica = crear(factura);
		enviarCorreo(factura, facturaElectronica);
	}

	@Override
	public ByteArrayInputStream obtenerTicket(long facturaId){
		Optional<Factura> opcional= rep.findById(facturaId);
		if(opcional.isEmpty()) {
			throw new EntidadNoExistenteException(Constantes.factura);
		}
		Factura factura = opcional.get();
		ByteArrayInputStream pdf = crearTicket(factura);
		return pdf;
	}

	/*private void crearMensaje(){
		String body = Util.soapConsultaFacturacionEletronica(facturaElectronica.getInfoTributaria().getClaveAcceso());
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
	}*/

	/*@Override
	public void scrapSRI(){
		try {
		chromeDriver.get(Constantes.urlSri);
		Thread.sleep(10000);
		chromeDriver.findElement(By.id("usuario")).sendKeys("0603467226001");
		Thread.sleep(9000);
		chromeDriver.findElement(By.id("password")).sendKeys("DDMR2013");
		Thread.sleep(8000);
		chromeDriver.findElement(By.id("kc-login")).click();
		Thread.sleep(10000);
		chromeDriver.navigate().to("https://srienlinea.sri.gob.ec/tuportal-internet/accederAplicacion.jspa?redireccion=57&idGrupo=55");
		Thread.sleep(9000);
		Select mes = new Select(chromeDriver.findElement(By.id("frmPrincipal:mes")));
		mes.selectByValue("8");
		Thread.sleep(8000);
		Select dia = new Select(chromeDriver.findElement(By.id("frmPrincipal:dia")));
		dia.selectByValue("0");
		Thread.sleep(10000);
		chromeDriver.findElement(By.id("btnRecaptcha")).click();
		Thread.sleep(9000);
		List<WebElement> list = chromeDriver.findElements(By.className("ui-dt-c"));
		Iterator<WebElement> iterator = list.iterator();
		List<String> values = new ArrayList<String>();
		while (iterator.hasNext()){
			WebElement element = iterator.next();
			values.add(element.getText());
		}
		System.out.println(values.toString());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}*/

}