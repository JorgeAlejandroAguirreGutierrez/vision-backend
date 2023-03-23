package com.proyecto.sicecuador.servicios.impl.comprobante;

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
import com.proyecto.sicecuador.exception.EstadoInvalidoException;
import com.proyecto.sicecuador.exception.FacturaElectronicaInvalidaException;
import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.modelos.comprobante.FacturaLinea;
import com.proyecto.sicecuador.modelos.comprobante.electronico.factura.*;
import com.proyecto.sicecuador.modelos.recaudacion.Cheque;
import com.proyecto.sicecuador.modelos.recaudacion.Deposito;
import com.proyecto.sicecuador.modelos.recaudacion.TarjetaCredito;
import com.proyecto.sicecuador.modelos.recaudacion.TarjetaDebito;
import com.proyecto.sicecuador.modelos.recaudacion.Transferencia;
import com.proyecto.sicecuador.repositorios.comprobante.IFacturaRepository;
import com.proyecto.sicecuador.servicios.interf.comprobante.IFacturaElectronicaService;

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
    	infoTributaria.setSecuencial(factura.getSecuencia());
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
            documento.setMargins(20, 20, 20, 20);
            // 4. Add content
            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            //ImageData imageData = ImageDataFactory.create(imagenes+factura.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getLogo());
            //Image image = new Image(imageData);
            //image.setWidth(200);
            //image.setHeight(150);
            //documento.add(image);
            documento.setFont(font);
            
            documento.add(new Paragraph(factura.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial()+"\n"+
                    "DIRECCION MATRIZ: "+factura.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getDireccion()+"\n"+
            		"OBLIGADO A LLEVAR CONTABILIDAD: "+factura.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getObligadoContabilidad()).setBorder(new SolidBorder(1)));
            
            documento.add( new Paragraph("\n"));
            
            documento.add(new Paragraph("RUC: "+factura.getSesion().getUsuario().getEstacion().getEstablecimiento().getEmpresa().getIdentificacion()+"\n"+
                    "FACTURA"+"\n"+
                    "No. " + factura.getSecuencia() + "\n" +
                    "NÚMERO DE AUTORIZACIÓN: " + factura.getClaveAcceso()+ "\n" +
                    "FECHA: " + factura.getFecha().toString() + "\n" +
                    "AMBIENTE: " + Constantes.facturaFisicaAmbienteValor + "\n" +
                    "EMISIÓN: " + Constantes.facturaFisicaEmisionValor).setBorder(new SolidBorder(1)));
            
            documento.add( new Paragraph("\n"));
            
            String telefonoCliente="";
            String correoCliente="";
            if (!factura.getCliente().getTelefonos().isEmpty()){
                telefonoCliente=factura.getCliente().getTelefonos().get(0).getNumero();
            }
            if (!factura.getCliente().getCorreos().isEmpty()){
                correoCliente=factura.getCliente().getCorreos().get(0).getEmail();
            }
            documento.add( new Paragraph("RAZÓN SOCIAL: "+factura.getCliente().getRazonSocial()+"\n"+
                    "IDENTIFICACIÓN: " + factura.getCliente().getIdentificacion()+"\n"+
                    "FECHA EMISIÓN: " + factura.getFecha().toString()+"\n"+
                    "DIRECCIÓN: " + factura.getCliente().getDireccion() + "\n" + 
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
            for (int i = 0; i <factura.getFacturaLineas().size(); i++)
            {
                tablaFacturaDetalle.addCell(factura.getFacturaLineas().get(i).getProducto().getCodigo());
                tablaFacturaDetalle.addCell(factura.getFacturaLineas().get(i).getCantidad()+"");
                tablaFacturaDetalle.addCell(factura.getFacturaLineas().get(i).getProducto().getNombre());
                tablaFacturaDetalle.addCell("$"+factura.getFacturaLineas().get(i).getPrecio().getPrecioSinIva());
                tablaFacturaDetalle.addCell("$"+factura.getFacturaLineas().get(i).getValorDescuentoLinea());
                tablaFacturaDetalle.addCell("$"+factura.getFacturaLineas().get(i).getSubtotalConDescuentoLinea());
            }
            documento.add(tablaFacturaDetalle);
            documento.add( new Paragraph("\n"));
            float [] columnasTablaFactura = {130F, 100F};
            Table tablaFactura = new Table(columnasTablaFactura);
            tablaFactura.addCell("SUBTOTAL SD 12%");
            tablaFactura.addCell("$"+factura.getSubtotalBase12SinDescuento());
            tablaFactura.addCell("SUBTOTAL CD 12%");
            tablaFactura.addCell("$"+factura.getSubtotalBase12ConDescuento());
            tablaFactura.addCell("SUBTOTAL SD 0%");
            tablaFactura.addCell("$"+factura.getSubtotalBase0SinDescuento());
            tablaFactura.addCell("SUBTOTAL CD 0%");
            tablaFactura.addCell("$"+factura.getSubtotalBase0ConDescuento());
            tablaFactura.addCell("TOTAL SD");
            tablaFactura.addCell("$"+factura.getTotalSinDescuento());
            tablaFactura.addCell("TOTAL CD");
            tablaFactura.addCell("$"+factura.getTotalConDescuento());
            tablaFactura.setTextAlignment(TextAlignment.RIGHT);
            tablaFactura.setHorizontalAlignment(HorizontalAlignment.RIGHT);
            documento.add(tablaFactura);
            
            documento.add(new Paragraph("INFORMACION ADICIONAL"+"\n" +
                    "COMENTARIO: " + factura.getComentario()).setBorder(new SolidBorder(1)).setWidth(300).setVerticalAlignment(VerticalAlignment.TOP).setHorizontalAlignment(HorizontalAlignment.LEFT));
            documento.add( new Paragraph("\n"));
            // 5. Close document
            documento.close();
            return new ByteArrayInputStream(salida.toByteArray());
        } catch(Exception e){
            return null;
        }
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
	        parte1.setFileName(Constantes.factura+factura.getSecuencia()+Constantes.extensionPdf);
	        MimeBodyPart parte2 = new MimeBodyPart();
	        parte2.setDataHandler(new DataHandler(xmlData));
	        parte2.setFileName(Constantes.factura+factura.getSecuencia()+Constantes.extensionXml);
	        
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
}
