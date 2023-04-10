package com.proyecto.sicecuador.servicios.impl.venta;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.*;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.exception.EstadoInvalidoException;
import com.proyecto.sicecuador.exception.FacturaElectronicaInvalidaException;
import com.proyecto.sicecuador.modelos.venta.Factura;
import com.proyecto.sicecuador.modelos.venta.FacturaLinea;
import com.proyecto.sicecuador.modelos.venta.electronico.factura.*;
import com.proyecto.sicecuador.modelos.recaudacion.Cheque;
import com.proyecto.sicecuador.modelos.recaudacion.Deposito;
import com.proyecto.sicecuador.modelos.recaudacion.TarjetaCredito;
import com.proyecto.sicecuador.modelos.recaudacion.TarjetaDebito;
import com.proyecto.sicecuador.modelos.recaudacion.Transferencia;
import com.proyecto.sicecuador.repositorios.venta.IFacturaRepository;
import com.proyecto.sicecuador.servicios.interf.venta.IFacturaElectronicaService;

import ayungan.com.signature.ConvertFile;
import ayungan.com.signature.SignatureXAdESBES;
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

import java.io.*;
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
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
public class FacturaElectronicaService implements IFacturaElectronicaService{
    @Autowired
    private IFacturaRepository rep;
    
    @Value("${prefijo.url.imagenes}")
    private String imagenes;
    
    @Value("${correo.usuario}")
    private String correoUsuario;
    
    @Value("${correo.contrasena}")
    private String correoContrasena;
    
    private FacturaElectronica crear(Factura factura) {
    	//MAPEO A FACTURA ELECTRONICA
    	FacturaElectronica facturaElectronica=new FacturaElectronica();
    	InfoTributaria infoTributaria = new InfoTributaria();
    	InfoFactura infoFactura = new InfoFactura();	 	
    	  	
    	infoTributaria.setAmbiente(Constantes.pruebas_sri);
    	infoTributaria.setTipoEmision(Constantes.emision_normal_sri);
    	infoTributaria.setRazonSocial(factura.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial());
    	infoTributaria.setNombreComercial(factura.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getNombreComercial());
    	infoTributaria.setRuc(factura.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getIdentificacion());
    	infoTributaria.setClaveAcceso(factura.getClaveAcceso());
    	infoTributaria.setCodDoc(Constantes.factura_sri);
    	infoTributaria.setEstab(factura.getSesion().getUsuario().getEstacion().getEstablecimiento().getCodigoSRI());
    	infoTributaria.setPtoEmi(factura.getSesion().getUsuario().getEstacion().getCodigoSRI());
    	infoTributaria.setSecuencial(factura.getSecuencial());
    	infoTributaria.setDirMatriz(factura.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getDireccion());
    	
    	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
    	String fechaEmision = dateFormat.format(factura.getFecha());
    	infoFactura.setFechaEmision(fechaEmision);
		infoFactura.setDirEstablecimiento(factura.getSesion().getUsuario().getEstacion().getEstablecimiento().getDireccion());
    	infoFactura.setObligadoContabilidad(factura.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getObligadoContabilidad());
    	infoFactura.setTipoIdentificacionComprador(factura.getCliente().getTipoIdentificacion().getCodigoSRI());
    	infoFactura.setRazonSocialComprador(factura.getCliente().getRazonSocial());
    	infoFactura.setIdentificacionComprador(factura.getCliente().getIdentificacion());
    	infoFactura.setDireccionComprador(factura.getCliente().getDireccion());
    	infoFactura.setTotalSinImpuestos(factura.getSubtotalSinDescuento());
    	infoFactura.setTotalDescuento(factura.getDescuentoTotal());
    	infoFactura.setTotalConImpuestos(crearTotalConImpuestos(factura));
    	infoFactura.setPropina(Constantes.cero);
    	infoFactura.setImporteTotal(factura.getTotalConDescuento());
    	infoFactura.setMoneda(Constantes.moneda);
    	infoFactura.setPagos(crearPagos(factura));
    	
    	Detalles detalles=crearDetalles(factura);

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
    	for(int i = 0; i<factura.getFacturaLineas().size(); i++) {
        	TotalImpuesto totalImpuesto = new TotalImpuesto();
    		totalImpuesto.setCodigo(Constantes.iva_sri);
        	totalImpuesto.setCodigoPorcentaje(factura.getFacturaLineas().get(i).getImpuesto().getCodigoSRI());
        	totalImpuesto.setDescuentoAdicional(factura.getFacturaLineas().get(i).getTotalDescuentoLinea());
        	totalImpuesto.setBaseImponible(factura.getFacturaLineas().get(i).getSubtotalConDescuentoLinea());
        	totalImpuesto.setValor(factura.getFacturaLineas().get(i).getIvaConDescuentoLinea());
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
        if(factura.getCredito()!= null && factura.getCredito().getSaldo() > Constantes.cero) {
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
    	Detalles detalles=new Detalles();
    	List<Detalle> detalleLista = new ArrayList<>();
    	for(int i = 0; i<factura.getFacturaLineas().size(); i++) {
    		Detalle detalle = new Detalle();
    		detalle.setCodigoPrincipal(factura.getFacturaLineas().get(i).getProducto().getCodigo());
    		detalle.setDescripcion(factura.getFacturaLineas().get(i).getProducto().getNombre());
    		detalle.setCantidad(factura.getFacturaLineas().get(i).getCantidad());
    		detalle.setPrecioUnitario(factura.getFacturaLineas().get(i).getPrecio().getPrecioVentaPublicoManual());
    		detalle.setDescuento(factura.getFacturaLineas().get(i).getTotalDescuentoLinea());
    		detalle.setPrecioTotalSinImpuesto(factura.getFacturaLineas().get(i).getSubtotalConDescuentoLinea());
    		detalle.setImpuestos(crearImpuestos(factura.getFacturaLineas().get(i)));
    		detalleLista.add(detalle);
    	}
    	detalles.setDetalle(detalleLista);
    	return detalles;
    }
    
    private Impuestos crearImpuestos(FacturaLinea facturaLinea) {
    	Impuestos impuestos=new Impuestos();
    	List<Impuesto> impuestoLista = new ArrayList<>();
    	Impuesto impuesto=new Impuesto();
    	impuesto.setCodigo(Constantes.iva_sri);
    	impuesto.setCodigoPorcentaje(facturaLinea.getImpuesto().getCodigoSRI());
    	impuesto.setTarifa(facturaLinea.getImpuesto().getPorcentaje());
    	impuesto.setBaseImponible(facturaLinea.getSubtotalConDescuentoLinea());
    	impuesto.setValor(facturaLinea.getIvaConDescuentoLinea());
    	impuestoLista.add(impuesto);
    	impuestos.setImpuesto(impuestoLista);
    	return impuestos;
    }

	private InfoAdicional crearInfoAdicional(Factura factura) {
		List<CampoAdicional> camposAdicionales = new ArrayList<>();
		CampoAdicional campoAdicional1 = new CampoAdicional();
		campoAdicional1.setNombre(Constantes.telefono);
		campoAdicional1.setValor(factura.getCliente().getTelefonos().get(0).getNumero());
		CampoAdicional campoAdicional2 = new CampoAdicional();
		campoAdicional2.setNombre(Constantes.celular);
		campoAdicional2.setValor(factura.getCliente().getCelulares().get(0).getNumero());
		CampoAdicional campoAdicional3 = new CampoAdicional();
		campoAdicional3.setNombre(Constantes.correo);
		campoAdicional3.setValor(factura.getCliente().getCorreos().get(0).getEmail());
		CampoAdicional campoAdicional4 = new CampoAdicional();
		campoAdicional4.setNombre(Constantes.direccion);
		campoAdicional4.setValor(factura.getCliente().getDireccion());
		CampoAdicional campoAdicional5 = new CampoAdicional();
		campoAdicional5.setNombre(Constantes.valor);
		campoAdicional5.setValor(factura.getTotalConDescuento() + Constantes.vacio);
		camposAdicionales.add(campoAdicional1);
		camposAdicionales.add(campoAdicional2);
		camposAdicionales.add(campoAdicional3);
		camposAdicionales.add(campoAdicional4);
		camposAdicionales.add(campoAdicional5);
		InfoAdicional infoAdicional = new InfoAdicional();
		infoAdicional.setCampoAdicional(camposAdicionales);
		return infoAdicional;
	}

	@Override
	public Factura enviar(long facturaId) {
		Optional<Factura> opcional= rep.findById(facturaId);
		if(opcional.isEmpty()) {
			throw new EntidadNoExistenteException(Constantes.factura);
		}
		Factura factura = opcional.get();
		if(!factura.getEstado().equals(Constantes.recaudada)){
			throw new EstadoInvalidoException(Constantes.recaudacion);
		}
		FacturaElectronica facturaElectronica = crear(factura);
		if(factura.getEstado().equals(Constantes.recaudada)) {
			String estadoRecepcion = recepcion(facturaElectronica);
			if(estadoRecepcion.equals(Constantes.recibidaSri)) {
				String estadoAutorizacion = autorizacion(facturaElectronica);
				if(estadoAutorizacion.equals(Constantes.autorizadoSri)){
					factura.setEstado(Constantes.estadoFacturada);
					enviarCorreo(factura, facturaElectronica);
					Factura facturada = rep.save(factura);
					facturada.normalizar();
					return facturada;
				}
				throw new FacturaElectronicaInvalidaException(estadoAutorizacion);
			}
			throw new FacturaElectronicaInvalidaException(estadoRecepcion);
		} else if(factura.getEstado().equals(Constantes.estadoFacturada)){
			enviarCorreo(factura, facturaElectronica);
			factura.normalizar();
			return factura;
		}
		throw new FacturaElectronicaInvalidaException(Constantes.noRecaudada);
	}
    
    private String recepcion(FacturaElectronica facturaElectronica) {
    	try {
    		JAXBContext jaxbContext = JAXBContext.newInstance(FacturaElectronica.class);            
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, Constantes.utf8);
            jaxbMarshaller.marshal(facturaElectronica, System.out);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(facturaElectronica, sw);
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

	public String autorizacion(FacturaElectronica facturaElectronica){
		try {
			String body=Util.soapConsultaFacturacionEletronica(facturaElectronica.getInfoTributaria().getClaveAcceso());
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
    
    public ByteArrayInputStream crearPDF(Factura factura) {
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
			float [] columnas = {320F, 280F};
			Table tabla = new Table(columnas);
			tabla.addCell(getCellEmpresa(factura.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial()+"\n"+ "\n"+
					"DIRECCION MATRIZ: " + factura.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getDireccion()+"\n"+ "\n"+
					"OBLIGADO A LLEVAR CONTABILIDAD: " + factura.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getObligadoContabilidad(), TextAlignment.LEFT));
			tabla.addCell(getCellFactura("RUC: "+factura.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getIdentificacion()+"\n"+
					"FACTURA"+"\n"+
					"No. " + factura.getSecuencial() + "\n" +
					"NÚMERO DE AUTORIZACIÓN: " + factura.getClaveAcceso()+ "\n" +
					"FECHA: " + factura.getFecha().toString() + "\n" +
					"AMBIENTE: " + Constantes.facturaFisicaAmbienteValor + "\n" +
					"EMISIÓN: " + Constantes.facturaFisicaEmisionValor, TextAlignment.LEFT));
			tabla.setBorderCollapse(BorderCollapsePropertyValue.SEPARATE);
			tabla.setHorizontalBorderSpacing(3);
			documento.add(tabla);
            documento.add(new Paragraph("\n"));
			float [] columnasCliente = {300F, 300F};
			Table tablaCliente = new Table(columnasCliente);
			tablaCliente.addCell(getCellCliente("RAZÓN SOCIAL: "+factura.getCliente().getRazonSocial()+"\n" + "FECHA EMISIÓN: " + factura.getFecha().toString() +"\n",
					TextAlignment.LEFT));
			tablaCliente.addCell(getCellCliente("IDENTIFICACIÓN: " + factura.getCliente().getIdentificacion() + "\n"+ "GUIA REMISION: " + "\t" + "\t", TextAlignment.RIGHT));
			documento.add(tablaCliente);
			documento.add( new Paragraph("\n"));
            float [] columnasTablaFacturaDetalle = {100F, 40F, 160F, 100F, 100F, 100F};
            Table tablaFacturaDetalle = new Table(columnasTablaFacturaDetalle);
            tablaFacturaDetalle.addCell(getCellColumnaFactura("CÓDIGO"));
            tablaFacturaDetalle.addCell(getCellColumnaFactura("CANT"));
            tablaFacturaDetalle.addCell(getCellColumnaFactura("DESCRIPCION"));
            tablaFacturaDetalle.addCell(getCellColumnaFactura("PRECIO U"));
            tablaFacturaDetalle.addCell(getCellColumnaFactura("DSCTO"));
            tablaFacturaDetalle.addCell(getCellColumnaFactura("TOTAL"));
            for (int i = 0; i <factura.getFacturaLineas().size(); i++)
            {
				String precioSinIva = String.format("%.2f", factura.getFacturaLineas().get(i).getPrecio().getPrecioSinIva());
				String valorDescuentoLinea = String.format("%.2f", factura.getFacturaLineas().get(i).getValorDescuentoLinea());
				String subtotalConDescuentoLinea = String.format("%.2f", factura.getFacturaLineas().get(i).getSubtotalConDescuentoLinea());

				tablaFacturaDetalle.addCell(getCellFilaFactura(factura.getFacturaLineas().get(i).getProducto().getCodigo()));
                tablaFacturaDetalle.addCell(getCellFilaFactura(factura.getFacturaLineas().get(i).getCantidad() + Constantes.vacio));
                tablaFacturaDetalle.addCell(getCellFilaFactura(factura.getFacturaLineas().get(i).getProducto().getNombre()));
                tablaFacturaDetalle.addCell(getCellFilaFactura("$"+precioSinIva));
                tablaFacturaDetalle.addCell(getCellFilaFactura("$"+valorDescuentoLinea));
                tablaFacturaDetalle.addCell(getCellFilaFactura("$"+subtotalConDescuentoLinea));
            }
			documento.add(tablaFacturaDetalle);
			documento.add( new Paragraph("\n"));
			String subtotalBase12SinDescuento = String.format("%.2f", factura.getSubtotalBase12SinDescuento());
			String subtotalBase12ConDescuento = String.format("%.2f", factura.getSubtotalBase12ConDescuento());
			String subtotalBase0SinDescuento = String.format("%.2f", factura.getSubtotalBase0SinDescuento());
			String subtotalBase0ConDescuento = String.format("%.2f", factura.getSubtotalBase0ConDescuento());
			String totalSinDescuento = String.format("%.2f", factura.getTotalSinDescuento());
			String totalConDescuento = String.format("%.2f", factura.getTotalConDescuento());
            float [] columnasTablaFactura = {300F, 300F};
            Table tablaFactura = new Table(columnasTablaFactura);
            tablaFactura.addCell(getCellFilaFactura("SUBTOTAL SD 12%"));
            tablaFactura.addCell(getCellFilaFactura("$" + subtotalBase12SinDescuento));
            tablaFactura.addCell(getCellFilaFactura("SUBTOTAL CD 12%"));
            tablaFactura.addCell(getCellFilaFactura("$" + subtotalBase12ConDescuento));
            tablaFactura.addCell(getCellFilaFactura("SUBTOTAL SD 0%"));
            tablaFactura.addCell(getCellFilaFactura("$" + subtotalBase0SinDescuento));
            tablaFactura.addCell(getCellFilaFactura("SUBTOTAL CD 0%"));
            tablaFactura.addCell(getCellFilaFactura("$" + subtotalBase0ConDescuento));
            tablaFactura.addCell(getCellFilaFactura("TOTAL SD"));
            tablaFactura.addCell(getCellFilaFactura("$" + totalSinDescuento));
            tablaFactura.addCell(getCellFilaFactura("TOTAL CD"));
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
			String direccionCliente = factura.getCliente().getDireccion();
			String comentario = factura.getComentario();
			float [] columnasAdicional = {300F, 300F};
			Table tablaAdicional = new Table(columnasAdicional);
			tablaAdicional.addCell(getCellAdicional("COMENTARIO"));
			tablaAdicional.addCell(getCellAdicional(comentario));
			tablaAdicional.addCell(getCellAdicional("DIRECCION"));
			tablaAdicional.addCell(getCellAdicional(direccionCliente));
			tablaAdicional.addCell(getCellAdicional("TELEFONO"));
			tablaAdicional.addCell(getCellAdicional(telefonoCliente));
			tablaAdicional.addCell(getCellAdicional("CELULAR"));
			tablaAdicional.addCell(getCellAdicional(celularCliente));
			tablaAdicional.addCell(getCellAdicional("CORREO"));
			tablaAdicional.addCell(getCellAdicional(correoCliente));
			float [] columnasAdicionalYFactura = {400F, 200F};
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
		cell.setFontSize(Constantes.fontSize);
		return cell;
	}
	private Cell getCellFactura(String text, TextAlignment alignment) {
		Paragraph parrafo = new Paragraph(text);
		Cell cell = new Cell();
		cell.add(parrafo);
		cell.setTextAlignment(alignment);
		cell.setFontSize(Constantes.fontSize);
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
		cell.setFontSize(Constantes.fontSize);
		cell.setBorderBottom(new SolidBorder(ColorConstants.BLUE,1));
		cell.setBorderTop(new SolidBorder(ColorConstants.BLUE, 1));
		return cell;
	}
	private Cell getCellColumnaFactura(String text) {
		Paragraph parrafo = new Paragraph(text);
		Cell cell = new Cell();
		cell.add(parrafo);
		cell.setFontSize(Constantes.fontSize);
		cell.setBackgroundColor(ColorConstants.BLUE).setFontColor(ColorConstants.WHITE);
		cell.setBorder(new SolidBorder(ColorConstants.BLUE,1));
		return cell;
	}
	private Cell getCellFilaFactura(String text) {
		Paragraph parrafo = new Paragraph(text);
		Cell cell = new Cell();
		cell.add(parrafo);
		cell.setFontSize(Constantes.fontSize);
		cell.setBorder(new SolidBorder(ColorConstants.BLUE,1));
		return cell;
	}
	private Cell getCellAdicional(String text) {
		Paragraph parrafo = new Paragraph(text);
		Cell cell = new Cell();
		cell.add(parrafo);
		cell.setFontSize(Constantes.fontSize);
		cell.setBorder(new SolidBorder(ColorConstants.BLUE,1));
		return cell;
	}
	private Cell getCellAdicionalYFactura(Table tabla){
		Cell cell = new Cell();
		cell.add(tabla);
		cell.setBorder(Border.NO_BORDER);
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
	        parte1.setFileName(Constantes.factura+factura.getSecuencial()+Constantes.extensionPdf);
	        MimeBodyPart parte2 = new MimeBodyPart();
	        parte2.setDataHandler(new DataHandler(xmlData));
	        parte2.setFileName(Constantes.factura+factura.getSecuencial()+Constantes.extensionXml);
	        
	        Multipart multipart = new MimeMultipart();
	        multipart.addBodyPart(parte1);
	        multipart.addBodyPart(parte2);

            message.setFrom(new InternetAddress(correoUsuario));
            message.addRecipients(Message.RecipientType.TO, factura.getCliente().getCorreos().get(0).getEmail());   //Se podrían añadir varios de la misma manera
            message.setSubject(factura.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial()+ Constantes.mensajeCorreo + factura.getCodigo());
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

	public ByteArrayInputStream obtenerPDF(long facturaId){
		Optional<Factura> opcional= rep.findById(facturaId);
		if(opcional.isEmpty()) {
			throw new EntidadNoExistenteException(Constantes.factura);
		}
		Factura factura = opcional.get();
		ByteArrayInputStream pdf= crearPDF(factura);
		return pdf;
	}
}
