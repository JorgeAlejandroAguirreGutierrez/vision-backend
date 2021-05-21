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
import com.itextpdf.layout.property.VerticalAlignment;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.SecuenciaNoExistenteException;
import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.modelos.inventario.Kardex;
import com.proyecto.sicecuador.repositorios.comprobante.IFacturaRepository;
import com.proyecto.sicecuador.servicios.interf.comprobante.IFacturaService;
import com.proyecto.sicecuador.servicios.interf.inventario.IKardexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class FacturaService implements IFacturaService {
    @Autowired
    private IFacturaRepository rep;
    @Autowired
    private IKardexService kardexService;

    @Transactional
    @Override
    public Factura crear(Factura factura) {
        //ACTUALIZACION DE KARDEX
        for(int i=0; i<factura.getFacturaDetalles().size(); i++){
            int cantidad=factura.getFacturaDetalles().get(i).getProducto().getKardexs().size();
            Kardex kardex_actualizar=factura.getFacturaDetalles().get(i).getProducto().getKardexs().get(cantidad-1);
            long salida_actual=kardex_actualizar.getSalida();
            kardex_actualizar.setSalida(salida_actual+factura.getFacturaDetalles().get(i).getCantidad());
            kardexService.actualizar(kardex_actualizar);
        }
        Optional<String>codigo=Util.generarCodigo(Constantes.tabla_factura);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	Optional<String>secuencia=Util.generarSecuencia(Constantes.tabla_factura);
    	if (secuencia.isEmpty()) {
    		throw new SecuenciaNoExistenteException();
    	}
    	factura.setCodigo(codigo.get());
    	factura.setSecuencia(secuencia.get());
        return rep.save(factura);
    }

    @Override
    public Factura actualizar(Factura factura) {
        return rep.save(factura);
    }

    @Override
    public Factura eliminar(Factura factura) {
        rep.deleteById(factura.getId());
        return factura;
    }

    @Override
    public Optional<Factura> obtener(Factura factura) {
        return rep.findById(factura.getId());
    }

    @Override
    public List<Factura> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile file) {
        return false;
    }

    @Override
    public List<Factura> buscar(Factura factura) {
        return  rep.findAll((root, criteriaQuery, criteriaBuilder) -> {
		    List<Predicate> predicates = new ArrayList<>();
		    if (!factura.getSecuencia().equals(Constantes.vacio)) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("codigo"), "%"+factura.getSecuencia()+"%")));
		    }
		    if (!factura.getCliente().getRazonSocial().equals(Constantes.vacio)) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("cliente").get("razonSocial"), "%"+factura.getCliente().getRazonSocial()+"%")));
		    }
		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		});
    }

    @Override
    public ByteArrayInputStream generarPDF(Factura factura) {
        try {
            ByteArrayOutputStream salida = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(salida);
            PdfDocument pdf = new PdfDocument(writer);
            // Initialize document
            Document documento = new Document(pdf, PageSize.A4);
            documento.setMargins(20, 20, 20, 20);
            // 4. Add content
            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            documento.add(new Paragraph("LOGO").setFont(font).setFontSize(30));
            documento.add(new Paragraph(factura.getVendedor().getPuntoVenta().getEstablecimiento().getEmpresa().getRazonSocial()+"\n"+
                    "Direccion: "+factura.getVendedor().getPuntoVenta().getEstablecimiento().getDireccion()).setBorder(new SolidBorder(1)));
            documento.add( new Paragraph("\n"));
            documento.add(new Paragraph("RUC: "+factura.getVendedor().getPuntoVenta().getEstablecimiento().getEmpresa().getIdentificacion()+"\n"+
                    "FACTURA"+"\n"+
                    "No."+factura.getSecuencia()+"\n"+
                    "Fecha: "+factura.getFecha().toString()).setBorder(new SolidBorder(1)));
            documento.add( new Paragraph("\n"));
            documento.add( new Paragraph("Razon Social: "+factura.getCliente().getRazonSocial()+"\n"+
                    "Identificacion: "+factura.getCliente().getIdentificacion()+"\n"+
                    "Fecha: "+factura.getFecha().toString()+"\n"+
                    "Direccion: "+factura.getCliente().getDireccion().getDireccion()).setBorder(new SolidBorder(1)));
            documento.add( new Paragraph("\n"));
            float [] columnas_tabla_factura_detalle = {100F, 100F, 100F,100F, 100F, 100F, 100F, 100F, 100F};
            Table tabla_factura_detalle = new Table(columnas_tabla_factura_detalle);
            tabla_factura_detalle.addCell("Codigo");
            tabla_factura_detalle.addCell("Cantidad");
            tabla_factura_detalle.addCell("Descripcion");
            tabla_factura_detalle.addCell("Series");
            tabla_factura_detalle.addCell("Precio U");
            tabla_factura_detalle.addCell("Subsidio");
            tabla_factura_detalle.addCell("Sin subsidio");
            tabla_factura_detalle.addCell("Descuento");
            tabla_factura_detalle.addCell("Total");
            for (int i = 0; i <factura.getFacturaDetalles().size(); i++)
            {
                tabla_factura_detalle.addCell(factura.getFacturaDetalles().get(i).getProducto().getCodigo());
                tabla_factura_detalle.addCell(factura.getFacturaDetalles().get(i).getCantidad()+"");
                tabla_factura_detalle.addCell(factura.getFacturaDetalles().get(i).getProducto().getNombre());
                String series="";
                if (!factura.getFacturaDetalles().get(i).getProducto().isSerieAutogenerado()){
                    for (int j = 0; j <factura.getFacturaDetalles().get(i).getCaracteristicas().size(); j++){
                        series=series+" "+factura.getFacturaDetalles().get(i).getCaracteristicas().get(j).getSerie();
                    }
                }
                tabla_factura_detalle.addCell(series);
                tabla_factura_detalle.addCell(factura.getFacturaDetalles().get(i).getPrecio().getPrecioVentaPublicoIva()+"");
                tabla_factura_detalle.addCell(factura.getFacturaDetalles().get(i).getSubsidio()+"");
                tabla_factura_detalle.addCell(factura.getFacturaDetalles().get(i).getSinSubsidio()+"");
                tabla_factura_detalle.addCell(factura.getFacturaDetalles().get(i).getValorDescuentoIndividualTotales()+"");
                tabla_factura_detalle.addCell(factura.getFacturaDetalles().get(i).getTotalConDescuento()+"");
            }
            documento.add(tabla_factura_detalle);
            float [] columnas_tabla_factura = {130F, 100F};
            Table tabla_factura = new Table(columnas_tabla_factura);
            tabla_factura.addCell("Subtotal SD 12%");
            tabla_factura.addCell(factura.getSubtotalBase12SinDescuento()+"");
            tabla_factura.addCell("Subtotal CD 12%");
            tabla_factura.addCell(factura.getSubtotalBase12ConDescuento()+"");
            tabla_factura.addCell("Subtotal SD 0%");
            tabla_factura.addCell(factura.getSubtotalBase0SinDescuento()+"");
            tabla_factura.addCell("Subtotal CD 0%");
            tabla_factura.addCell(factura.getSubtotalBase0ConDescuento()+"");
            tabla_factura.addCell("Total SD");
            tabla_factura.addCell(factura.getTotalSinDescuento()+"");
            tabla_factura.addCell("Total CD");
            tabla_factura.addCell(factura.getTotalConDescuento()+"");
            tabla_factura.setHorizontalAlignment(HorizontalAlignment.RIGHT);
            documento.add(tabla_factura);
            String telefono_cliente="";
            String correo_cliente="";
            if (!factura.getCliente().getTelefonos().isEmpty()){
                telefono_cliente=factura.getCliente().getTelefonos().get(0).getNumero();
            }
            if (!factura.getCliente().getCorreos().isEmpty()){
                correo_cliente=factura.getCliente().getCorreos().get(0).getEmail();
            }
            documento.add(new Paragraph("INFORMACION ADICIONAL:"+"\n" +
                    "Direccion del Cliente: "+factura.getCliente().getDireccion().getDireccion()+"\n"+
                    "Telefono del Cliente: "+telefono_cliente+"\n"+
                    "Correo del Cliente: "+correo_cliente+"\n"+
                    "Punto de Partida: "+"\n"+
                    "Vendedor: "+ factura.getVendedor().getNombre()+"\n"+
                    "Entrada: "+"\n"+
                    "Valor a Financiar: "+factura.getTotalConDescuento()+"\n"+
                    "Financiamiento: "+"\n"+
                    "1 Letra: "+"\n"+
                    "2 letra: "+"\n"+
                    "Todo incluido financiamiento: "+factura.getTotalConDescuento()).setBorder(new SolidBorder(1)).setWidth(300).setVerticalAlignment(VerticalAlignment.TOP).setHorizontalAlignment(HorizontalAlignment.LEFT));
            documento.add( new Paragraph("\n"));

            // 5. Close document
            documento.close();
            return new ByteArrayInputStream(salida.toByteArray());
        } catch(Exception e){
            return null;
        }
    }

    /*@Override
    public ByteArrayInputStream generarPDF(Factura factura) {
        try {
            // 1) Load ODT file and set Velocity template engine and cache it to the registry
            InputStream in= new FileInputStream(new File("Prueba.docx"));
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in,TemplateEngineKind.Velocity);
            // 2) Create Java model context
            IContext context = report.createContext();
            context.put("name", "world");
            // 3) Set PDF as format converter
            Options options = Options.getTo(ConverterTypeTo.PDF);
            // 3) Generate report by merging Java model with the ODT and convert it to PDF
            OutputStream out = new FileOutputStream(new File("Prueba.docx"));
            report.convert(context, options, out);
            return out.
        } catch(Exception e){
            return null;
        }
    }*/
}
