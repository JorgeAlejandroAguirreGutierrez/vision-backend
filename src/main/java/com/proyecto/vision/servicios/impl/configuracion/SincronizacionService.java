package com.proyecto.vision.servicios.impl.configuracion;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.compra.FacturaCompra;
import com.proyecto.vision.modelos.compra.FacturaCompraLinea;
import com.proyecto.vision.modelos.compra.Proveedor;
import com.proyecto.vision.modelos.configuracion.Impuesto;
import com.proyecto.vision.modelos.configuracion.Sincronizacion;
import com.proyecto.vision.modelos.configuracion.TipoComprobante;
import com.proyecto.vision.repositorios.compra.IFacturaCompraRepository;
import com.proyecto.vision.repositorios.compra.IProveedorRepository;
import com.proyecto.vision.repositorios.configuracion.ISincronizacionRepository;
import com.proyecto.vision.servicios.interf.compra.IFacturaCompraService;
import com.proyecto.vision.servicios.interf.compra.IProveedorService;
import com.proyecto.vision.servicios.interf.configuracion.IImpuestoService;
import com.proyecto.vision.servicios.interf.configuracion.ISincronizacionService;
import com.proyecto.vision.servicios.interf.configuracion.ITipoComprobanteService;
import org.apache.commons.math3.analysis.function.Sinc;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.*;

@Service
public class SincronizacionService implements ISincronizacionService {
	
    @Autowired
    private ISincronizacionRepository rep;

    @Autowired
    private IProveedorRepository proveedorRepository;

    @Autowired
    private ITipoComprobanteService tipoComprobanteService;

    @Autowired
    private IImpuestoService impuestoService;

    @Autowired
    private IFacturaCompraRepository facturaCompraRepository;

    @Value("${facturacion.produccion}")
    private String facturacionProduccion;

    @Override
    public void validar(Sincronizacion sincronizacion) {
        if(sincronizacion.getTipo().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.tipo);
        if(sincronizacion.getMes().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.mes);
        if(sincronizacion.getAnio().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.anio);
    }
    
    @Override
    public Sincronizacion crear(Sincronizacion sincronizacion) {
        validar(sincronizacion);
    	Optional<String>codigo = Util.generarCodigo(Constantes.tabla_estado_civil);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
        sincronizacion.setCodigo(codigo.get());
        sincronizacion.setEstado(Constantes.estadoPendiente);
    	return rep.save(sincronizacion);
    }

    @Override
    public Sincronizacion actualizar(Sincronizacion sincronizacion) {
        validar(sincronizacion);
        return rep.save(sincronizacion);
    }

    @Override
    public Sincronizacion obtener(long id) {
        Optional<Sincronizacion> res = rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.estado_civil);
    }

    @Override
    public List<Sincronizacion> consultar() {
        return rep.consultar();
    }
    
    @Override
    public List<Sincronizacion> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }
    
    @Override
    public Page<Sincronizacion> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public List<FacturaCompra> procesar(long sincronizacionId){
        Optional<Sincronizacion> res = rep.findById(sincronizacionId);
        if(res.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.sincronizacion);
        }
        Sincronizacion sincronizacion = res.get();
        if(sincronizacion.getEstado().equals(Constantes.estadoProcesado)){
            throw new DatoInvalidoException(Constantes.estado);
        }
        List<FacturaCompra> facturasCompras = new ArrayList<>();
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            Path path = Paths.get(Constantes.pathRecursos + Constantes.pathFacturas + Constantes.slash + sincronizacion.getEmpresa().getIdentificacion()
            + Constantes.slash + sincronizacion.getMes() + Constantes.guion_bajo + sincronizacion.getAnio() + Constantes.guion_bajo + Constantes.compra + Constantes.extensionTxt);
            String ruta = path.toAbsolutePath().toString();
            archivo = new File (ruta);
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);
            String linea;
            ArrayList<String> clavesAccesos = new ArrayList<>();
            while((linea = br.readLine())!=null){
                String[] sub = linea.split("\t");
                clavesAccesos.add(sub[sub.length - 2]);
            }
            clavesAccesos.remove(0);
            for(String claveAcceso: clavesAccesos){
                Document documento = consultarFactura(claveAcceso);
                if(documento != null){
                    FacturaCompra facturaCompra = construirFacturaCompra(documento, sincronizacion);
                    facturasCompras.add(facturaCompra);
                }
            }
            sincronizacion.setEstado(Constantes.estadoProcesado);
            rep.save(sincronizacion);
            return facturasCompras;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private Document consultarFactura(String claveAcceso){
        try {
            String url = Constantes.vacio;
            if(facturacionProduccion.equals(Constantes.si)){
                url = Constantes.urlProduccionConsultaFacturacionEletronicaSri;
            }
            if(facturacionProduccion.equals(Constantes.no)){
                url = Constantes.urlPruebasConsultaFacturacionEletronicaSri;
            }
            String body = Util.soapConsultaFacturacionEletronica(claveAcceso);
            HttpClient httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .uri(URI.create(url))
                    .setHeader(Constantes.contentType, Constantes.contenTypeValor)
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            // print status code
            System.out.println(response.statusCode());
            // print response body
            System.out.println(response.body());
            JSONObject json = Util.convertirXmlJson(response.body());
            double numeroComprobantes = json.getJSONObject("soap:Envelope").getJSONObject("soap:Body").getJSONObject("ns2:autorizacionComprobanteResponse").getJSONObject("RespuestaAutorizacionComprobante")
                    .getDouble("numeroComprobantes");
            if(numeroComprobantes == Constantes.cero){
                return null;
            }
            String cdata = json.getJSONObject("soap:Envelope").getJSONObject("soap:Body").getJSONObject("ns2:autorizacionComprobanteResponse").getJSONObject("RespuestaAutorizacionComprobante")
                    .getJSONObject("autorizaciones").getJSONObject("autorizacion").getString("comprobante");
            String[] split = cdata.split("<ds:Signature");
            String xml = split[0];
            xml = xml + "</factura>";
            SAXBuilder saxBuilder = new SAXBuilder();
            Document documento = saxBuilder.build(new StringReader(xml));
            return documento;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (JDOMException e) {
            e.printStackTrace();
        }
        return null;
    }

    private FacturaCompra construirFacturaCompra(Document documento, Sincronizacion sincronizacion){
        FacturaCompra facturaCompra = new FacturaCompra();
        Element raiz = documento.getRootElement();
        facturaCompra.setEstablecimiento(raiz.getChild("infoTributaria").getChildText("estab"));
        facturaCompra.setPuntoVenta(raiz.getChild("infoTributaria").getChildText("ptoEmi"));
        facturaCompra.setSecuencial(raiz.getChild("infoTributaria").getChildText("secuencial"));
        facturaCompra.setNumeroComprobante(facturaCompra.getEstablecimiento() + Constantes.guion + facturaCompra.getPuntoVenta() + Constantes.guion + facturaCompra.getSecuencial());
        facturaCompra.setFecha(new Date(raiz.getChild("infoFactura").getChildText("fechaEmision")));
        facturaCompra.setEstado(Constantes.estadoRecibida);
        facturaCompra.setValorDistribuidoTotal(Constantes.cero);
        facturaCompra.setValorDescuentoTotal(Constantes.cero);
        facturaCompra.setPorcentajeDescuentoTotal(Constantes.cero);
        facturaCompra.setValorPorcentajeDescuentoTotal(Constantes.cero);
        facturaCompra.setSubtotal(Double.parseDouble(raiz.getChild("infoFactura").getChildText("totalSinImpuestos")));
        facturaCompra.setDescuento(Double.parseDouble(raiz.getChild("infoFactura").getChildText("totalDescuento")));
        facturaCompra.setImporteIvaTotal(Double.parseDouble(raiz.getChild("infoFactura").getChild("totalConImpuestos").getChild("totalImpuesto").getChildText("valor")));
        facturaCompra.setTotal(Double.parseDouble(raiz.getChild("infoFactura").getChildText("importeTotal")));
        facturaCompra.setComentario(Constantes.vacio);
        Optional<Proveedor> proveedor = proveedorRepository.obtenerPorIdentificacionYEmpresaYEstado(raiz.getChild("infoTributaria").getChildText("ruc"), sincronizacion.getEmpresa().getId(), Constantes.estadoActivo);
        if(!proveedor.isEmpty()){
            facturaCompra.setProveedor(proveedor.get());
        }
        if(proveedor.isEmpty()){
            facturaCompra.setProveedor(null);
        }
        facturaCompra.setUsuario(sincronizacion.getUsuario());
        TipoComprobante tipoComprobante = tipoComprobanteService.obtenerPorNombreTabla(Constantes.tabla_factura_compra);
        facturaCompra.setTipoComprobante(tipoComprobante);
        facturaCompra.setEmpresa(sincronizacion.getEmpresa());
        facturaCompra.setFacturaCompraLineas(new ArrayList<>());
        List<Element> lista = raiz.getChild("detalles").getChildren();
        int i = 0;
        for(Element elemento : lista){
            FacturaCompraLinea facturaCompraLinea = new FacturaCompraLinea();
            facturaCompraLinea.setPosicion(i + 1);
            facturaCompraLinea.setNombreProducto(elemento.getChildText("descripcion"));
            facturaCompraLinea.setCantidad(Long.parseLong(elemento.getChildText("cantidad")));
            facturaCompraLinea.setCostoUnitario(Double.parseDouble(elemento.getChildText("precioUnitario")));
            facturaCompraLinea.setValorDescuentoLinea(Double.parseDouble(elemento.getChildText("descuento")));
            Impuesto impuesto = impuestoService.obtenerPorCodigoSRIYEstado(elemento.getChild("impuestos").getChild("impuesto").getChildText("codigoPorcentaje"), Constantes.estadoActivo);
            facturaCompraLinea.setImpuesto(impuesto);
            facturaCompra.getFacturaCompraLineas().add(facturaCompraLinea);
            i++;
        }
        calcular(facturaCompra);
        return facturaCompra;
    }

    private void calcularLinea(FacturaCompraLinea facturaCompraLinea){
        this.distribuirCostoLinea(facturaCompraLinea);

        double subtotalLineaSinDescuento = facturaCompraLinea.getCantidad() * facturaCompraLinea.getCostoUnitario();
        subtotalLineaSinDescuento = Math.round(subtotalLineaSinDescuento * 10000.0) / 10000.0;
        facturaCompraLinea.setSubtotalLineaSinDescuento(subtotalLineaSinDescuento);

        double valorPorcentajeDescuentoLinea = (subtotalLineaSinDescuento * facturaCompraLinea.getPorcentajeDescuentoLinea() / 100);
        valorPorcentajeDescuentoLinea = Math.round(valorPorcentajeDescuentoLinea * 10000.0) / 10000.0;
        facturaCompraLinea.setValorPorcentajeDescuentoLinea(valorPorcentajeDescuentoLinea);

        double subtotalLinea = subtotalLineaSinDescuento - facturaCompraLinea.getValorDescuentoLinea() - facturaCompraLinea.getValorPorcentajeDescuentoLinea() -
                facturaCompraLinea.getValorDescuentoTotalLinea() - facturaCompraLinea.getValorPorcentajeDescuentoTotalLinea();
        subtotalLinea = Math.round(subtotalLinea * 10000.0) / 10000.0;
        facturaCompraLinea.setSubtotalLinea(subtotalLinea);

        double importeIvaLinea = (subtotalLinea * facturaCompraLinea.getImpuesto().getPorcentaje()) / 100;
        importeIvaLinea = Math.round(importeIvaLinea * 10000.0) / 10000.0;
        facturaCompraLinea.setImporteIvaLinea(importeIvaLinea);

        double totalLinea = subtotalLinea + importeIvaLinea;
        totalLinea = Math.round(totalLinea * 10000.0) / 10000.0;
        facturaCompraLinea.setTotalLinea(totalLinea);
    }

    private void distribuirCostoLinea(FacturaCompraLinea facturaCompraLinea) {
        double costoPromedioLinea = facturaCompraLinea.getCostoUnitario() + facturaCompraLinea.getCostoDistribuido();
        facturaCompraLinea.setCostoPromedio(costoPromedioLinea);
    }

    private void calcular(FacturaCompra facturaCompra){
        if (facturaCompra.getValorDescuentoTotal() != Constantes.cero) {
            this.calcularValorDescuentoTotal(facturaCompra);
        }
        for(FacturaCompraLinea facturaCompraLinea: facturaCompra.getFacturaCompraLineas()){
            calcularLinea(facturaCompraLinea);
        }
        this.calcularTotales(facturaCompra);
        if (facturaCompra.getPorcentajeDescuentoTotal() != Constantes.cero) {
            this.calcularPorcentajeDescuentoTotal(facturaCompra);
            for(FacturaCompraLinea facturaCompraLinea: facturaCompra.getFacturaCompraLineas()){
                calcularLinea(facturaCompraLinea);
            }
            this.calcularTotales(facturaCompra);
        }
    }

    private void calcularSubtotal(FacturaCompra facturaCompra) {
        double subtotal = Constantes.cero;
        for (FacturaCompraLinea facturaCompraLinea : facturaCompra.getFacturaCompraLineas()) {
            subtotal += facturaCompraLinea.getSubtotalLineaSinDescuento();
        }
        subtotal = Math.round(subtotal * 100.0) / 100.0;
        facturaCompra.setSubtotal(subtotal);
    }

    private void calcularValorDescuentoTotal(FacturaCompra facturaCompra) {
        for (FacturaCompraLinea facturaCompraLinea : facturaCompra.getFacturaCompraLineas()) {
            double ponderacion = facturaCompraLinea.getSubtotalLineaSinDescuento() / facturaCompra.getSubtotal();

            double valorDescuentoTotalLinea = (facturaCompra.getValorDescuentoTotal() * ponderacion) * 100 / (100 + facturaCompraLinea.getImpuesto().getPorcentaje());
            valorDescuentoTotalLinea = Math.round(valorDescuentoTotalLinea * 100.0) / 100.0;
            facturaCompraLinea.setValorDescuentoTotalLinea(valorDescuentoTotalLinea);
        }
    }

    private void calcularPorcentajeDescuentoTotal(FacturaCompra facturaCompra) {
        double valorTotalPorcentajeDescuentoTotal = (facturaCompra.getTotal() * (facturaCompra.getPorcentajeDescuentoTotal() / 100));
        valorTotalPorcentajeDescuentoTotal = Math.round(valorTotalPorcentajeDescuentoTotal * 100.0) / 100.0;
        facturaCompra.setValorPorcentajeDescuentoTotal(valorTotalPorcentajeDescuentoTotal);

        for (FacturaCompraLinea facturaCompraLinea : facturaCompra.getFacturaCompraLineas()) {
            double ponderacion = facturaCompraLinea.getSubtotalLineaSinDescuento() / facturaCompra.getSubtotal();

            double valorPorcentajeDescuentoTotalLinea = (valorTotalPorcentajeDescuentoTotal * ponderacion) * 100 / (100 + facturaCompraLinea.getImpuesto().getPorcentaje());
            valorPorcentajeDescuentoTotalLinea = Math.round(valorPorcentajeDescuentoTotalLinea * 100.0) / 100.0;
            facturaCompraLinea.setValorPorcentajeDescuentoTotalLinea(valorPorcentajeDescuentoTotalLinea);
        }
    }

    private void calcularTotales(FacturaCompra facturaCompra) {
        double subtotalGravadoConDescuento = Constantes.cero, subtotalNoGravadoConDescuento = Constantes.cero;
        double importeIvaTotal = Constantes.cero, descuentoTotal = Constantes.cero;
        for (FacturaCompraLinea facturaCompraLinea : facturaCompra.getFacturaCompraLineas()) {
            descuentoTotal += facturaCompraLinea.getValorDescuentoLinea() + facturaCompraLinea.getValorPorcentajeDescuentoLinea() +
                    facturaCompraLinea.getValorDescuentoTotalLinea() + facturaCompraLinea.getValorPorcentajeDescuentoTotalLinea();
            if (facturaCompraLinea.getImpuesto().getPorcentaje() != Constantes.cero) {
                subtotalGravadoConDescuento += facturaCompraLinea.getSubtotalLinea();
            } else {
                subtotalNoGravadoConDescuento += facturaCompraLinea.getSubtotalLinea();
            }
            importeIvaTotal += facturaCompraLinea.getImporteIvaLinea();
        }
        descuentoTotal = Math.round(descuentoTotal * 100.0) / 100.0;
        facturaCompra.setDescuento(descuentoTotal);

        subtotalGravadoConDescuento = Math.round(subtotalGravadoConDescuento * 100.0) / 100.0;
        facturaCompra.setSubtotalGravadoConDescuento(subtotalGravadoConDescuento);

        subtotalNoGravadoConDescuento = Math.round(subtotalNoGravadoConDescuento * 100.0) / 100.0;
        facturaCompra.setSubtotalNoGravadoConDescuento(subtotalNoGravadoConDescuento);

        importeIvaTotal = Math.round(importeIvaTotal * 100.0) / 100.0;
        facturaCompra.setImporteIvaTotal(importeIvaTotal);

        double total = subtotalGravadoConDescuento + subtotalNoGravadoConDescuento + importeIvaTotal;
        total = Math.round(total * 100.0) / 100.0;
        facturaCompra.setTotal(total);
    }

}
