package com.proyecto.sicecuador.servicios.impl.comprobante;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.repositorios.comprobante.IFacturaRepository;
import com.proyecto.sicecuador.servicios.interf.comprobante.IFacturaFisicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Optional;


@Service
public class FacturaFisicaService implements IFacturaFisicaService{
    @Autowired
    private IFacturaRepository rep;
    
    @Value("${prefijo.url.imagenes}")
    private String imagenes;
    
    public ByteArrayInputStream crearPDF(Factura _factura) {
    	Optional<Factura> optionalFactura = rep.findById(_factura.getId());
    	if(optionalFactura.isEmpty()) {
    		throw new EntidadNoExistenteException(Constantes.factura);
    	}
    	Factura factura= optionalFactura.get();
    	try {
            ByteArrayOutputStream salida = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(salida);
            PdfDocument pdf = new PdfDocument(writer);
            // Initialize document
            Document documento = new Document(pdf, PageSize.A4);
            documento.setMargins(20, 20, 20, 20);
            // 4. Add content
            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            ImageData imageData = ImageDataFactory.create(imagenes+factura.getSesion().getUsuario().getPuntoVenta().getEstablecimiento().getEmpresa().getLogo());
            Image image = new Image(imageData);
            image.setWidth(200);
            image.setHeight(150);
            documento.add(image);
            documento.setFont(font);
            
            documento.add(new Paragraph(factura.getSesion().getUsuario().getPuntoVenta().getEstablecimiento().getEmpresa().getRazonSocial()+"\n"+
                    "DIRECCION MATRIZ: "+factura.getSesion().getUsuario().getPuntoVenta().getEstablecimiento().getDireccion()+"\n"+
            		"OBLIGADO A LLEVAR CONTABILIDAD: "+factura.getSesion().getUsuario().getPuntoVenta().getEstablecimiento().getEmpresa().getObligadoContabilidad()).setBorder(new SolidBorder(1)));
            
            documento.add( new Paragraph("\n"));
            
            documento.add(new Paragraph("RUC: "+factura.getSesion().getUsuario().getPuntoVenta().getEstablecimiento().getEmpresa().getIdentificacion()+"\n"+
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
                    "DIRECCIÓN: " + factura.getCliente().getDireccion().getDireccion() + "\n" + 
                    "TELÉFONO: " + telefonoCliente + "\n" +
                    "CORREO: " + correoCliente).setBorder(new SolidBorder(1)));
            documento.add( new Paragraph("\n"));
            float [] columnasTablaFacturaDetalle = {100F, 40F, 160F, 100F, 100F, 100F, 100F};
            Table tablaFacturaDetalle = new Table(columnasTablaFacturaDetalle);
            tablaFacturaDetalle.addCell("CÓDIGO");
            tablaFacturaDetalle.addCell("CANT");
            tablaFacturaDetalle.addCell("DESCRIPCION");
            tablaFacturaDetalle.addCell("SERIES");
            tablaFacturaDetalle.addCell("PRECIO U");
            tablaFacturaDetalle.addCell("DSCTO");
            tablaFacturaDetalle.addCell("TOTAL");
            for (int i = 0; i <factura.getFacturaDetalles().size(); i++)
            {
                tablaFacturaDetalle.addCell(factura.getFacturaDetalles().get(i).getProducto().getCodigo());
                tablaFacturaDetalle.addCell(factura.getFacturaDetalles().get(i).getCantidad()+"");
                tablaFacturaDetalle.addCell(factura.getFacturaDetalles().get(i).getProducto().getNombre());
                String series="";
                if (!factura.getFacturaDetalles().get(i).getProducto().isSerieAutogenerado()){
                    for (int j = 0; j <factura.getFacturaDetalles().get(i).getCaracteristicas().size(); j++){
                        series=series+" "+factura.getFacturaDetalles().get(i).getCaracteristicas().get(j).getSerie();
                    }
                }
                tablaFacturaDetalle.addCell(series);
                tablaFacturaDetalle.addCell("$"+factura.getFacturaDetalles().get(i).getPrecio().getPrecioSinIva());
                tablaFacturaDetalle.addCell("$"+factura.getFacturaDetalles().get(i).getValorDescuentoLinea());
                tablaFacturaDetalle.addCell("$"+factura.getFacturaDetalles().get(i).getSubtotalConDescuentoLinea());
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
}
