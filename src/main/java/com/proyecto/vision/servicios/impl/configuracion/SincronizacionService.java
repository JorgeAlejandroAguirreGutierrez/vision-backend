package com.proyecto.vision.servicios.impl.configuracion;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.*;
import com.proyecto.vision.modelos.compra.*;
import com.proyecto.vision.modelos.configuracion.*;
import com.proyecto.vision.modelos.inventario.Producto;
import com.proyecto.vision.repositorios.compra.IFacturaCompraRepository;
import com.proyecto.vision.repositorios.compra.IGastoPersonalRepository;
import com.proyecto.vision.repositorios.compra.IProveedorRepository;
import com.proyecto.vision.repositorios.configuracion.IImpuestoRepository;
import com.proyecto.vision.repositorios.configuracion.ISincronizacionRepository;
import com.proyecto.vision.repositorios.configuracion.ITipoComprobanteRepository;
import com.proyecto.vision.repositorios.configuracion.ITipoIdentificacionRepository;
import com.proyecto.vision.repositorios.inventario.IProductoRepository;
import com.proyecto.vision.servicios.interf.configuracion.ISincronizacionService;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;

@Service
public class SincronizacionService implements ISincronizacionService {
	
    @Autowired
    private ISincronizacionRepository rep;

    @Autowired
    private IProveedorRepository proveedorRepository;

    @Autowired
    private ITipoComprobanteRepository tipoComprobanteRepository;

    @Autowired
    private ITipoIdentificacionRepository tipoIdentificacionRepository;

    @Autowired
    private IImpuestoRepository impuestoRepository;

    @Autowired
    private IFacturaCompraRepository facturaCompraRepository;

    @Autowired
    private IProductoRepository productoRepository;

    @Autowired
    private IGastoPersonalRepository gastoPersonalRepository;

    @Value("${sri.produccion}")
    private String sriProduccion;

    @Override
    public void validar(Sincronizacion sincronizacion) {
        if(sincronizacion.getTipo().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.tipo);
        if(sincronizacion.getMes().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.mes);
        if(sincronizacion.getAnio().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.anio);
    }

    @Override
    public Sincronizacion cargarArchivo(long sincronizacionId, MultipartFile multipartFile) {
        if (multipartFile != null) {
            Optional<Sincronizacion> optional = rep.findById(sincronizacionId);
            if (optional.isEmpty()) {
                throw new EntidadNoExistenteException(Constantes.sincronizacion);
            }
            Sincronizacion sincronizacion = optional.get();
            try {
                InputStream inputStream = multipartFile.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                String linea;
                ArrayList<String> clavesAccesos = new ArrayList<>();
                 while((linea = br.readLine()) != null){
                    String[] sub = linea.split("\t");
                    if(sub[2].equals(Constantes.factura_recibida_sri)){
                        clavesAccesos.add(sub[4]);
                    }
                }
                String lista = Constantes.vacio;
                for(String claveAcceso: clavesAccesos){
                    lista = lista + claveAcceso + Constantes.espacio;
                }
                sincronizacion.setClavesAccesos(lista);
                return rep.save(sincronizacion);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        throw new EntidadNoExistenteException(Constantes.sincronizacion);
    }
    
    @Override
    public Sincronizacion crear(Sincronizacion sincronizacion) {
        validar(sincronizacion);
        Optional<Sincronizacion> sincronizacionExistente = rep.obtenerPorTipoYMesYAnioYEmpresa(sincronizacion.getTipo(), sincronizacion.getMes(), sincronizacion.getAnio(), sincronizacion.getEmpresa().getId());
    	if(sincronizacionExistente.isPresent()){
    	    throw new EntidadExistenteException(Constantes.sincronizacion);
        }
        Optional<String>codigo = Util.generarCodigo(Constantes.tabla_sincronizacion);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
        sincronizacion.setCodigo(codigo.get());
        sincronizacion.setEstado(Constantes.estadoActivo);
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
    public List<Modelo> procesar(long sincronizacionId){
        Optional<Sincronizacion> res = rep.findById(sincronizacionId);
        if(res.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.sincronizacion);
        }
        Sincronizacion sincronizacion = res.get();
        if(sincronizacion.getEstado().equals(Constantes.estadoInactivo)){
            throw new DatoInvalidoException(Constantes.estado);
        }
        List<Modelo> modelos = new ArrayList<>();
        List<String> clavesAccesos = Arrays.asList(sincronizacion.getClavesAccesos().split(Constantes.espacio));
        for(String claveAcceso: clavesAccesos){
            Document documento = consultarFactura(claveAcceso);
            if(documento != null){
                Modelo modelo = construir(documento, sincronizacion);
                modelos.add(modelo);
            }
        }
        return modelos;
    }

    private Document consultarFactura(String claveAcceso){
        try {
            String url = Constantes.vacio;
            if(sriProduccion.equals(Constantes.si)){
                url = Constantes.urlProduccionConsultaFacturacionEletronicaSri;
            }
            if(sriProduccion.equals(Constantes.no)){
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

    private Modelo construir(Document documento, Sincronizacion sincronizacion){
        Element raiz = documento.getRootElement();
        Modelo modelo = new Modelo();
        modelo.setRazonSocial(raiz.getChild("infoTributaria").getChildText("razonSocial"));
        modelo.setNombreComercial(raiz.getChild("infoTributaria").getChildText("nombreComercial"));
        modelo.setRuc(raiz.getChild("infoTributaria").getChildText("ruc"));
        modelo.setCodDoc(raiz.getChild("infoTributaria").getChildText("codDoc"));
        modelo.setClaveAcceso(raiz.getChild("infoTributaria").getChildText("claveAcceso"));
        modelo.setEstablecimiento(raiz.getChild("infoTributaria").getChildText("estab"));
        modelo.setPuntoVenta(raiz.getChild("infoTributaria").getChildText("ptoEmi"));
        modelo.setSecuencial(raiz.getChild("infoTributaria").getChildText("secuencial"));
        modelo.setNumeroComprobante(modelo.getEstablecimiento() + Constantes.guion + modelo.getPuntoVenta() + Constantes.guion + modelo.getSecuencial());
        modelo.setDireccion(raiz.getChild("infoTributaria").getChildText("dirMatriz"));
        modelo.setFecha(raiz.getChild("infoFactura").getChildText("fechaEmision"));
        modelo.setTotalSinImpuestos(raiz.getChild("infoFactura").getChildText("totalSinImpuestos"));
        modelo.setTotalDescuento(raiz.getChild("infoFactura").getChildText("totalDescuento"));
        modelo.setImporteTotal(raiz.getChild("infoFactura").getChildText("importeTotal"));
        modelo.setModeloImpuestos(new ArrayList<>());
        List<Element> totalConImpuestos = raiz.getChild("infoFactura").getChild("totalConImpuestos").getChildren();
        for(Element elemento : totalConImpuestos){
            ModeloImpuesto modeloImpuesto = new ModeloImpuesto();
            modeloImpuesto.setCodigo(elemento.getChildText("codigo"));
            modeloImpuesto.setCodigoPorcentaje(elemento.getChildText("codigoPorcentaje"));
            modeloImpuesto.setBaseImponible(elemento.getChildText("baseImponible"));
            modeloImpuesto.setValor(elemento.getChildText("valor"));
            modelo.getModeloImpuestos().add(modeloImpuesto);
        }
        modelo.setModeloDetalles(new ArrayList<>());
        List<Element> lista = raiz.getChild("detalles").getChildren();
        for(Element elemento : lista){
            ModeloDetalle modeloDetalle = new ModeloDetalle();
            modeloDetalle.setCodigoPrincipal(elemento.getChildText("codigoPrincipal"));
            modeloDetalle.setDescripcion(elemento.getChildText("descripcion"));
            modeloDetalle.setCantidad(elemento.getChildText("cantidad"));
            modeloDetalle.setPrecioUnitario(elemento.getChildText("precioUnitario"));
            modeloDetalle.setDescuento(elemento.getChildText("descuento"));
            modeloDetalle.setPrecioTotalSinImpuesto(elemento.getChildText("precioTotalSinImpuesto"));
            modeloDetalle.setCodigoImpuesto(elemento.getChild("impuestos").getChild("impuesto").getChildText("codigo"));
            modeloDetalle.setCodigoPorcentaje(elemento.getChild("impuestos").getChild("impuesto").getChildText("codigoPorcentaje"));
            modeloDetalle.setTarifa(elemento.getChild("impuestos").getChild("impuesto").getChildText("tarifa"));
            modeloDetalle.setBaseImponible(elemento.getChild("impuestos").getChild("impuesto").getChildText("baseImponible"));
            modeloDetalle.setValor(elemento.getChild("impuestos").getChild("impuesto").getChildText("valor"));
            modelo.getModeloDetalles().add(modeloDetalle);
        }
        modelo.setUsuario(sincronizacion.getUsuario());
        modelo.setEmpresa(sincronizacion.getEmpresa());
        return modelo;
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


    public void crearModelos(List<Modelo> modelos){
        List<FacturaCompra> facturasCompras = new ArrayList<>();
        List<GastoPersonal> gastosPersonales = new ArrayList<>();
        for(Modelo modelo: modelos){
            if(modelo.getTipo().equals(Constantes.tabla_factura_compra)){
                FacturaCompra facturaCompra = crearFacturaCompra(modelo);
                if(facturaCompra != null){
                    facturasCompras.add(facturaCompra);
                }
            }
            if(modelo.getTipo().equals(Constantes.tabla_gasto_personal)){
                GastoPersonal gastoPersonal = crearGastoPersonal(modelo);
                if(gastoPersonal != null){
                    gastosPersonales.add(gastoPersonal);
                }
            }
        }
        for(FacturaCompra facturaCompra: facturasCompras){
            Optional<String> codigo = Util.generarCodigoPorEmpresa(facturaCompra.getFecha(), Constantes.tabla_factura_compra, facturaCompra.getEmpresa().getId());
            if (codigo.isEmpty()) {
                throw new CodigoNoExistenteException();
            }
            facturaCompra.setCodigo(codigo.get());
            facturaCompraRepository.save(facturaCompra);
        }
        for(GastoPersonal gastoPersonal: gastosPersonales){
            Optional<String> codigo = Util.generarCodigoPorEmpresa(gastoPersonal.getFecha(), Constantes.tabla_gasto_personal, gastoPersonal.getEmpresa().getId());
            if (codigo.isEmpty()) {
                throw new CodigoNoExistenteException();
            }
            gastoPersonal.setCodigo(codigo.get());
            gastoPersonalRepository.save(gastoPersonal);
        }
    }

    private FacturaCompra crearFacturaCompra(Modelo modelo){
        Optional<FacturaCompra> facturaCompraExistente = facturaCompraRepository.obtenerPorNumeroComprobanteYEmpresa(modelo.getNumeroComprobante(), modelo.getEmpresa().getId());
        if(facturaCompraExistente.isEmpty()){
            FacturaCompra facturaCompra = new FacturaCompra();
            facturaCompra.setEstablecimiento(modelo.getEstablecimiento());
            facturaCompra.setPuntoVenta(modelo.getPuntoVenta());
            facturaCompra.setSecuencial(modelo.getSecuencial());
            facturaCompra.setNumeroComprobante(modelo.getNumeroComprobante());
            facturaCompra.setFecha(new Date(modelo.getFecha()));
            facturaCompra.setEstado(Constantes.estadoRecibida);
            facturaCompra.setValorDistribuidoTotal(Constantes.cero);
            facturaCompra.setValorDescuentoTotal(Constantes.cero);
            facturaCompra.setPorcentajeDescuentoTotal(Constantes.cero);
            facturaCompra.setValorPorcentajeDescuentoTotal(Constantes.cero);
            facturaCompra.setSubtotal(Double.parseDouble(modelo.getTotalSinImpuestos()));
            facturaCompra.setDescuento(Double.parseDouble(modelo.getTotalDescuento()));
            double subtotalGravadoConDescuento =  Constantes.cero;
            double subtotalNoGravadoConDescuento =  Constantes.cero;
            double importeIvaTotal = Constantes.cero;
            for(ModeloImpuesto modeloImpuesto: modelo.getModeloImpuestos()){
                if(modeloImpuesto.getCodigoPorcentaje().equals(Constantes.iva_8_sri) || modeloImpuesto.getCodigoPorcentaje().equals(Constantes.iva_12_sri) || modeloImpuesto.getCodigoPorcentaje().equals(Constantes.iva_14_sri)){
                    subtotalGravadoConDescuento = subtotalGravadoConDescuento + Double.parseDouble(modeloImpuesto.getBaseImponible());
                    importeIvaTotal = importeIvaTotal + Double.parseDouble(modeloImpuesto.getValor());
                }
                if(modeloImpuesto.getCodigoPorcentaje().equals(Constantes.iva_0_sri)){
                    subtotalNoGravadoConDescuento = subtotalNoGravadoConDescuento + Double.parseDouble(modeloImpuesto.getBaseImponible());
                    importeIvaTotal = importeIvaTotal + Double.parseDouble(modeloImpuesto.getValor());
                }
            }
            facturaCompra.setSubtotalGravadoConDescuento(subtotalGravadoConDescuento);
            facturaCompra.setSubtotalNoGravadoConDescuento(subtotalNoGravadoConDescuento);
            facturaCompra.setImporteIvaTotal(importeIvaTotal);
            facturaCompra.setTotal(Double.parseDouble(modelo.getImporteTotal()));
            facturaCompra.setComentario(Constantes.vacio);
            Optional<Proveedor> proveedorExistente = proveedorRepository.obtenerPorIdentificacionYEmpresa(modelo.getRuc(), modelo.getEmpresa().getId());
            if(proveedorExistente.isPresent()){
                facturaCompra.setProveedor(proveedorExistente.get());
            }
            if(proveedorExistente.isEmpty()){
                Proveedor proveedor = new Proveedor();
                proveedor.setRazonSocial(modelo.getRazonSocial());
                proveedor.setNombreComercial(modelo.getNombreComercial());
                proveedor.setIdentificacion(modelo.getRuc());
                proveedor.setDireccion(modelo.getDireccion());
                proveedor.setEstado(Constantes.estadoRecibida);
                proveedor.setEmpresa(modelo.getEmpresa());
                Optional<TipoIdentificacion> tipoIdentificacion = tipoIdentificacionRepository.obtenerPorAbreviatura(Constantes.ruc_abreviatura);
                proveedor.setTipoIdentificacion(tipoIdentificacion.get());
                proveedor = proveedorRepository.save(proveedor);
                facturaCompra.setProveedor(proveedor);
            }
            facturaCompra.setUsuario(modelo.getUsuario());
            facturaCompra.setEmpresa(modelo.getEmpresa());
            facturaCompra.setFacturaCompraLineas(new ArrayList<>());
            int i = 0;
            for(ModeloDetalle modeloDetalle: modelo.getModeloDetalles()){
                FacturaCompraLinea facturaCompraLinea = new FacturaCompraLinea();
                facturaCompraLinea.setPosicion(i + 1);
                facturaCompraLinea.setNombreProducto(modeloDetalle.getDescripcion());
                facturaCompraLinea.setCantidad(Long.parseLong(modeloDetalle.getCantidad()));
                facturaCompraLinea.setCostoUnitario(Double.parseDouble(modeloDetalle.getPrecioUnitario()));
                facturaCompraLinea.setCostoDistribuido(Constantes.cero);
                facturaCompraLinea.setCostoPromedio(Double.parseDouble(modeloDetalle.getPrecioUnitario()));
                facturaCompraLinea.setValorDescuentoLinea(Constantes.cero);
                facturaCompraLinea.setPorcentajeDescuentoLinea(Constantes.cero);
                facturaCompraLinea.setValorPorcentajeDescuentoLinea(Constantes.cero);
                facturaCompraLinea.setValorPorcentajeDescuentoTotalLinea(Constantes.cero);
                facturaCompraLinea.setValorDescuentoTotalLinea(Double.parseDouble(modeloDetalle.getDescuento()));
                facturaCompraLinea.setSubtotalLineaSinDescuento(Double.parseDouble(modeloDetalle.getBaseImponible()));
                facturaCompraLinea.setSubtotalLinea(Double.parseDouble(modeloDetalle.getBaseImponible()));
                facturaCompraLinea.setImporteIvaLinea(Double.parseDouble(modeloDetalle.getValor()));
                facturaCompraLinea.setTotalLinea(Double.parseDouble(modeloDetalle.getBaseImponible()) + Double.parseDouble(modeloDetalle.getValor()));
                String codigoPorcentaje = modeloDetalle.getCodigoPorcentaje();
                if(Integer.parseInt(codigoPorcentaje) == Constantes.seis || Integer.parseInt(codigoPorcentaje) == Constantes.siete){
                    codigoPorcentaje = Constantes.ceroId+Constantes.vacio;
                }
                Optional<Impuesto> impuesto = impuestoRepository.obtenerPorCodigoSRIYEstado(codigoPorcentaje, Constantes.estadoActivo);
                if(impuesto.isEmpty()){
                    throw new EntidadNoExistenteException(Constantes.impuesto);
                }
                facturaCompraLinea.setImpuesto(impuesto.get());
                Optional<Producto> producto = productoRepository.obtenerPorCodigoPrincipalYEmpresa(modeloDetalle.getCodigoPrincipal(), modelo.getEmpresa().getId());
                if(producto.isEmpty()){
                    throw new EntidadNoExistenteException(Constantes.producto);
                }
                facturaCompraLinea.setBodega(null);
                facturaCompra.getFacturaCompraLineas().add(facturaCompraLinea);
                i++;
            }
            calcular(facturaCompra);
            Optional<TipoComprobante> tipoComprobante = tipoComprobanteRepository.obtenerPorNombreTabla(Constantes.tabla_factura_compra);
            facturaCompra.setTipoComprobante(tipoComprobante.get());
            return facturaCompra;
        }
        return null;
    }

    private GastoPersonal crearGastoPersonal(Modelo modelo){
        Optional<GastoPersonal> gastoPersonalExistente = gastoPersonalRepository.obtenerPorNumeroComprobanteYEmpresa(modelo.getNumeroComprobante(), modelo.getEmpresa().getId());
        if(gastoPersonalExistente.isEmpty()){
            GastoPersonal gastoPersonal = new GastoPersonal();
            gastoPersonal.setEstablecimiento(modelo.getEstablecimiento());
            gastoPersonal.setPuntoVenta(modelo.getPuntoVenta());
            gastoPersonal.setSecuencial(modelo.getSecuencial());
            gastoPersonal.setNumeroComprobante(modelo.getNumeroComprobante());
            gastoPersonal.setFecha(new Date(modelo.getFecha()));
            gastoPersonal.setEstado(Constantes.estadoRecibida);
            gastoPersonal.setDescuento(Double.parseDouble(modelo.getTotalDescuento()));
            gastoPersonal.setSubtotal(Double.parseDouble(modelo.getTotalSinImpuestos()));
            double subtotalGravado = Constantes.cero;
            double subtotalNoGravado = Constantes.cero;
            double importeIvaTotal = Constantes.cero;
            for(ModeloImpuesto modeloImpuesto: modelo.getModeloImpuestos()){
                if(modeloImpuesto.getCodigoPorcentaje().equals(Constantes.iva_8_sri) || modeloImpuesto.getCodigoPorcentaje().equals(Constantes.iva_12_sri) || modeloImpuesto.getCodigoPorcentaje().equals(Constantes.iva_14_sri)){
                    subtotalGravado = subtotalGravado + Double.parseDouble(modeloImpuesto.getBaseImponible());
                    importeIvaTotal = importeIvaTotal + Double.parseDouble(modeloImpuesto.getValor());
                }
                if(modeloImpuesto.getCodigoPorcentaje().equals(Constantes.iva_0_sri)){
                    subtotalNoGravado = subtotalNoGravado + Double.parseDouble(modeloImpuesto.getBaseImponible());
                    importeIvaTotal = importeIvaTotal + Double.parseDouble(modeloImpuesto.getValor());
                }
            }
            gastoPersonal.setSubtotalGravado(subtotalGravado);
            gastoPersonal.setSubtotalNoGravado(subtotalNoGravado);
            gastoPersonal.setImporteIvaTotal(importeIvaTotal);
            gastoPersonal.setTotal(Double.parseDouble(modelo.getImporteTotal()));
            gastoPersonal.setComentario(Constantes.vacio);
            gastoPersonal.setTipoGasto(modelo.getTipoGasto());
            Optional<Proveedor> proveedorExistente = proveedorRepository.obtenerPorIdentificacionYEmpresa(modelo.getRuc(), modelo.getEmpresa().getId());
            if(proveedorExistente.isPresent()){
                gastoPersonal.setProveedor(proveedorExistente.get());
            }
            if(proveedorExistente.isEmpty()){
                Proveedor proveedor = new Proveedor();
                proveedor.setRazonSocial(modelo.getRazonSocial());
                proveedor.setNombreComercial(modelo.getNombreComercial());
                proveedor.setIdentificacion(modelo.getRuc());
                proveedor.setDireccion(modelo.getDireccion());
                proveedor.setEstado(Constantes.estadoActivo);
                proveedor.setEmpresa(modelo.getEmpresa());
                Optional<TipoIdentificacion> tipoIdentificacion = tipoIdentificacionRepository.obtenerPorAbreviatura(Constantes.ruc_abreviatura);
                proveedor.setTipoIdentificacion(tipoIdentificacion.get());
                proveedor = proveedorRepository.save(proveedor);
                gastoPersonal.setProveedor(proveedor);
            }
            gastoPersonal.setUsuario(modelo.getUsuario());
            gastoPersonal.setEmpresa(modelo.getEmpresa());
            gastoPersonal.setGastoPersonalLineas(new ArrayList<>());
            int i = 0;
            for(ModeloDetalle modeloDetalle: modelo.getModeloDetalles()){
                GastoPersonalLinea gastoPersonalLinea = new GastoPersonalLinea();
                gastoPersonalLinea.setPosicion(i + 1);
                gastoPersonalLinea.setNombreProducto(modeloDetalle.getDescripcion());
                gastoPersonalLinea.setCantidad((long)(Double.parseDouble(modeloDetalle.getCantidad())));
                gastoPersonalLinea.setCostoUnitario(Double.parseDouble(modeloDetalle.getPrecioUnitario()));
                gastoPersonalLinea.setValorDescuentoLinea(Double.parseDouble(modeloDetalle.getDescuento()));
                gastoPersonalLinea.setSubtotalLineaSinDescuento(Double.parseDouble(modeloDetalle.getBaseImponible()));
                gastoPersonalLinea.setSubtotalLinea(Double.parseDouble(modeloDetalle.getBaseImponible()));
                gastoPersonalLinea.setImporteIvaLinea(Double.parseDouble(modeloDetalle.getValor()));
                gastoPersonalLinea.setTotalLinea(Double.parseDouble(modeloDetalle.getBaseImponible()) + Double.parseDouble(modeloDetalle.getValor()));
                String codigoPorcentaje = modeloDetalle.getCodigoPorcentaje();
                if(Integer.parseInt(codigoPorcentaje) == Constantes.seis || Integer.parseInt(codigoPorcentaje) == Constantes.siete){
                    codigoPorcentaje = Constantes.ceroId+Constantes.vacio;
                }
                Optional<Impuesto> impuesto = impuestoRepository.obtenerPorCodigoSRIYEstado(codigoPorcentaje, Constantes.estadoActivo);
                if(impuesto.isEmpty()){
                    throw new EntidadNoExistenteException(Constantes.impuesto);
                }
                gastoPersonalLinea.setImpuesto(impuesto.get());
                gastoPersonal.getGastoPersonalLineas().add(gastoPersonalLinea);
                i++;
            }
            return gastoPersonal;
        }
        return null;
    }
}
