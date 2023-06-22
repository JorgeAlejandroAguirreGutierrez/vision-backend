package com.proyecto.sicecuador.servicios.impl.reporte;

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
import com.itextpdf.layout.property.BorderCollapsePropertyValue;
import com.itextpdf.layout.property.BorderRadius;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.recaudacion.*;
import com.proyecto.sicecuador.modelos.reporte.ReporteVenta;
import com.proyecto.sicecuador.modelos.reporte.ReporteVentaLinea;
import com.proyecto.sicecuador.modelos.usuario.Usuario;
import com.proyecto.sicecuador.modelos.venta.Factura;
import com.proyecto.sicecuador.repositorios.usuario.IUsuarioRepository;
import com.proyecto.sicecuador.repositorios.venta.IFacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReporteVentaService {

    @Autowired
    private IFacturaRepository facturaRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    public ReporteVenta obtener(String apodo, String fechaInicio, String fechaFinal) {
        List<Factura> facturas = facturaRepository.consultarPorFechaInicioYFechaFinal(fechaInicio, fechaFinal);
        Optional<Usuario> usuario = usuarioRepository.obtenerPorApodo(apodo, Constantes.activo);
        if(!facturas.isEmpty()) {
            throw new EntidadNoExistenteException(Constantes.factura);
        }
        if(!usuario.isEmpty()) {
            throw new EntidadNoExistenteException(Constantes.usuario);
        }
        //DATOS GENERALES
        ReporteVenta reporteVenta = new ReporteVenta();
        reporteVenta.setRazonSocial(usuario.get().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial());
        reporteVenta.setNombreComercial(usuario.get().getEstacion().getEstablecimiento().getEmpresa().getNombreComercial());
        reporteVenta.setFechaInicio(fechaInicio);
        reporteVenta.setFechaInicio(fechaFinal);
        reporteVenta.setFecha(new Date().toString());
        reporteVenta.setPeriodoDelReporte(fechaInicio + Constantes.espacio + fechaFinal);
        reporteVenta.setUsuario(usuario.get().getApodo());
        reporteVenta.setPerfil(usuario.get().getPerfil().getDescripcion());
        List<ReporteVentaLinea> reporteVentaLineas = new ArrayList();
        double total0 = Constantes.cero;
        double total12 = Constantes.cero;
        double totalIva = Constantes.cero;
        double reporteTotal = Constantes.cero;
        long facturasEmitidas = Constantes.ceroId;
        long facturasAnuladas = Constantes.ceroId;
        double totalEfectivo = Constantes.cero;
        double totalCheque = Constantes.cero;
        double totalTarjetaCredito = Constantes.cero;
        double totalTarjetaDebito = Constantes.cero;
        double totalTransferencia = Constantes.cero;
        double totalCredito = Constantes.cero;
        for(Factura factura: facturas){
            ReporteVentaLinea reporteVentaLinea = new ReporteVentaLinea();
            reporteVentaLinea.setFecha(factura.getFecha().toString());
            reporteVentaLinea.setHora(factura.getFecha().toString());
            reporteVentaLinea.setDocumento(factura.getTipoComprobante().getAbreviatura());
            reporteVentaLinea.setEstablecimiento(factura.getSesion().getUsuario().getEstacion().getEstablecimiento().getCodigoSRI());
            reporteVentaLinea.setEstacion(factura.getSesion().getUsuario().getEstacion().getCodigoSRI());
            reporteVentaLinea.setSecuencia(factura.getSecuencial());
            reporteVentaLinea.setCliente(factura.getCliente().getRazonSocial());
            reporteVentaLinea.setIdentificacion(factura.getCliente().getIdentificacion());
            reporteVentaLinea.setVendedor(factura.getSesion().getUsuario().getApodo());
            String tipoVenta = Constantes.vacio;
            if(factura.getEfectivo() > Constantes.cero){
                tipoVenta = Constantes.efectivo;
            }
            if(!factura.getCheques().isEmpty()){
                tipoVenta = Constantes.cheque;
            }
            if(!factura.getTarjetasCreditos().isEmpty()){
                tipoVenta = Constantes.tarjeta_credito;
            }
            if(!factura.getTarjetasDebitos().isEmpty()){
                tipoVenta = Constantes.tarjeta_debito;
            }
            if(!factura.getTransferencias().isEmpty()){
                tipoVenta = Constantes.transferencia;
            }
            if(!factura.getTransferencias().isEmpty()){
                tipoVenta = Constantes.transferencia;
            }
            if(factura.getCredito().getSaldo() > Constantes.cero){
                tipoVenta = Constantes.credito;
            }
            reporteVentaLinea.setTipoVenta(tipoVenta);

            String subtotal0 = String.format("%.2f", factura.getSubtotalNoGrabadoConDescuento());
            reporteVentaLinea.setSubtotal0(subtotal0);
            String subtotal12 = String.format("%.2f", factura.getSubtotalGrabadoConDescuento());
            reporteVentaLinea.setSubtotal12(subtotal12);
            String iva = String.format("%.2f", factura.getImporteIvaTotal());
            reporteVentaLinea.setIva(iva);
            String total = String.format("%.2f", factura.getValorTotal());
            reporteVentaLinea.setTotal(total);
            reporteVentaLineas.add(reporteVentaLinea);

            total0 = total0 + factura.getSubtotalNoGrabadoConDescuento();
            total12 = total12 + factura.getSubtotalGrabadoConDescuento();
            totalIva = totalIva + factura.getImporteIvaTotal();
            reporteTotal = reporteTotal + factura.getValorTotal();

            if(factura.getEstado().equals(Constantes.estadoAnulada)){
                facturasAnuladas++;
            } else {
                facturasEmitidas++;
            }

            totalEfectivo = totalEfectivo + factura.getEfectivo();
            for(Cheque cheque: factura.getCheques()){
                totalCheque = totalCheque + cheque.getValor();
            }
            for(TarjetaCredito tarjetaCredito: factura.getTarjetasCreditos()){
                totalTarjetaCredito = totalTarjetaCredito + tarjetaCredito.getValor();
            }
            for(TarjetaDebito tarjetaDebito: factura.getTarjetasDebitos()){
                totalTarjetaDebito = totalTarjetaDebito + tarjetaDebito.getValor();
            }
            for(Transferencia transferencia: factura.getTransferencias()){
                totalTransferencia = totalTransferencia + transferencia.getValor();
            }
            if(factura.getCredito()  != null){
                totalCredito = totalCredito + factura.getCredito().getSaldo();
            }

        }
        reporteVenta.setReporteVentaLineas(reporteVentaLineas);
        //TOTALES
        reporteVenta.setTotal0(String.format("%.2f", total0));
        reporteVenta.setTotal12(String.format("%.2f", total12));
        reporteVenta.setTotalIva(String.format("%.2f", totalIva));
        reporteVenta.setTotal(String.format("%.2f", reporteTotal));

        //INFOMACION DE RESUMEN
        reporteVenta.setFacturasEmitidas(String.valueOf(facturasEmitidas));
        reporteVenta.setFacturasAnuladas(String.valueOf(facturasAnuladas));
        reporteVenta.setFacturasTotales(String.valueOf(facturasEmitidas + facturasAnuladas));

        //REPORTE DE COBROS
        reporteVenta.setEfectivo(String.format("%.2f", totalEfectivo));
        reporteVenta.setCheque(String.format("%.2f", totalCheque));
        reporteVenta.setTarjetaCredito(String.format("%.2f", totalTarjetaCredito));
        reporteVenta.setTarjetaDebito(String.format("%.2f", totalTarjetaDebito));
        reporteVenta.setTransferencia(String.format("%.2f", totalTransferencia));
        reporteVenta.setCredito(String.format("%.2f", totalCredito));
        String totalRecaudacion = String.format("%.2f", totalEfectivo + totalCheque + totalTarjetaCredito + totalTarjetaDebito + totalTransferencia + totalCredito);
        reporteVenta.setTotalRecaudacion(totalRecaudacion);
        return reporteVenta;
    }

    public ByteArrayInputStream pdf(String apodo, String fechaInicio, String fechaFinal){
        ReporteVenta reporteVenta = obtener(apodo, fechaInicio, fechaFinal);
        //GENERACION DEL PDF
        try {
            ByteArrayOutputStream salida = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(salida);
            PdfDocument pdf = new PdfDocument(writer);
            // Initialize document
            Document documento = new Document(pdf, PageSize.A4);
            documento.setMargins(0, 0, 0, 0);
            // 4. Add content
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            documento.setFont(font);
            float[] columnas = {600F};
            Table tabla = new Table(columnas);
            tabla.addCell(getCellEmpresa(reporteVenta.getRazonSocial() + "\n" + "\n" +
                    reporteVenta.getNombreComercial() + "\n" + "\n" +
                    reporteVenta.getNombreReporte() + "\n" + "\n" +
                    "VENTAS DESDE: " + reporteVenta.getFechaInicio() + " HASTA " + reporteVenta.getFechaFinal() + "\n" + "\n", TextAlignment.LEFT));
            documento.add(tabla);
            documento.add(new Paragraph("\n"));
            documento.add(new Paragraph("DATOS GENERALES"));
            documento.add(new Paragraph("\n"));
            float[] columnasDatoGeneral = {600F};
            Table tablaDatoGeneral = new Table(columnasDatoGeneral);
            tablaDatoGeneral.addCell(getCellDatoGeneral("FECHA DE REPORTE: " + reporteVenta.getFecha() + "\n" +
                    "PERIODO DEL REPORTE: " + reporteVenta.getPeriodoDelReporte() + "\n" +
                    "USUARIO: " + reporteVenta.getUsuario() + "\n" +
                    "CARGO: " + reporteVenta.getPerfil(), TextAlignment.LEFT));
            documento.add(tablaDatoGeneral);
            documento.add(new Paragraph("\n"));
            documento.add(new Paragraph("DOCUMENTOS EN EL PERIODO"));
            documento.add(new Paragraph("\n"));
            float[] columnasTablaDocumento = {42F, 42F, 42F, 42F, 42F, 42F, 42F, 42F, 42F, 42F, 42F, 42F, 42F, 42F};
            Table tablaDocumento = new Table(columnasTablaDocumento);
            tablaDocumento.addCell(getCellColumnaDocumento("FECHA"));
            tablaDocumento.addCell(getCellColumnaDocumento("HORA"));
            tablaDocumento.addCell(getCellColumnaDocumento("DOC"));
            tablaDocumento.addCell(getCellColumnaDocumento("ESTAB"));
            tablaDocumento.addCell(getCellColumnaDocumento("ESTC"));
            tablaDocumento.addCell(getCellColumnaDocumento("SECUENCIA"));
            tablaDocumento.addCell(getCellColumnaDocumento("CLIENTE"));
            tablaDocumento.addCell(getCellColumnaDocumento("IDENT"));
            tablaDocumento.addCell(getCellColumnaDocumento("VEND"));
            tablaDocumento.addCell(getCellColumnaDocumento("T.VTA"));
            tablaDocumento.addCell(getCellColumnaDocumento("SUBTOTAL0"));
            tablaDocumento.addCell(getCellColumnaDocumento("SUBTOTAL12"));
            tablaDocumento.addCell(getCellColumnaDocumento("IVA"));
            tablaDocumento.addCell(getCellColumnaDocumento("TOTAL"));
            for (ReporteVentaLinea reporteVentaLinea : reporteVenta.getReporteVentaLineas())
            {
                tablaDocumento.addCell(getCellFilaDocumento(reporteVentaLinea.getFecha()));
                tablaDocumento.addCell(getCellFilaDocumento(reporteVentaLinea.getHora()));
                tablaDocumento.addCell(getCellFilaDocumento(reporteVentaLinea.getDocumento()));
                tablaDocumento.addCell(getCellFilaDocumento(reporteVentaLinea.getEstablecimiento()));
                tablaDocumento.addCell(getCellFilaDocumento(reporteVentaLinea.getEstacion()));
                tablaDocumento.addCell(getCellFilaDocumento(reporteVentaLinea.getSecuencia()));
                tablaDocumento.addCell(getCellFilaDocumento(reporteVentaLinea.getCliente()));
                tablaDocumento.addCell(getCellFilaDocumento(reporteVentaLinea.getIdentificacion()));
                tablaDocumento.addCell(getCellFilaDocumento(reporteVentaLinea.getVendedor()));
                tablaDocumento.addCell(getCellFilaDocumento(reporteVentaLinea.getTipoVenta()));
                tablaDocumento.addCell(getCellFilaDocumento(reporteVentaLinea.getSubtotal0()));
                tablaDocumento.addCell(getCellFilaDocumento(reporteVentaLinea.getSubtotal12()));
                tablaDocumento.addCell(getCellFilaDocumento(reporteVentaLinea.getIva()));
                tablaDocumento.addCell(getCellFilaDocumento(reporteVentaLinea.getTotal()));
            }
            documento.add(tablaDocumento);
            float [] columnasTotal = {120F, 120F, 120F, 120F, 120F};
            Table tablaTotal = new Table(columnasTotal);
            tablaTotal.addCell(getCellTotal("TOTALES"));
            tablaTotal.addCell(getCellTotal("$" + reporteVenta.getTotal0()));
            tablaTotal.addCell(getCellTotal("$" + reporteVenta.getTotal12()));
            tablaTotal.addCell(getCellTotal("$" + reporteVenta.getTotalIva()));
            tablaTotal.addCell(getCellTotal("$" + reporteVenta.getTotal()));
            tablaTotal.setTextAlignment(TextAlignment.RIGHT);

            documento.add(new Paragraph("\n"));
            documento.add(new Paragraph("INFORMACION RESUMEN"));
            documento.add(new Paragraph("\n"));
            float [] columnasFacturaYResumen = {300F, 300F};
            Table tablaFacturaYResumen = new Table(columnasFacturaYResumen);
            float [] columnasFactura = {450F, 150F};
            Table tablaFactura = new Table(columnasFactura);
            tablaFactura.addCell(getCellFactura("FACTURAS EMITIDAS"));
            tablaFactura.addCell(getCellFactura(reporteVenta.getFacturasEmitidas()));
            tablaFactura.addCell(getCellFactura("FACTURAS EMITIDAS"));
            tablaFactura.addCell(getCellFactura(reporteVenta.getFacturasEmitidas()));
            tablaFactura.addCell(getCellFactura("TOTAL"));
            tablaFactura.addCell(getCellFactura(reporteVenta.getFacturasTotales()));

            float [] columnasResumen = {450F, 150F};
            Table tablaResumen = new Table(columnasResumen);
            tablaResumen.addCell(getCellResumen("VENTAS GRAVADAS CON 12%"));
            tablaResumen.addCell(getCellResumen(reporteVenta.getTotal12()));
            tablaResumen.addCell(getCellResumen("VENTAS GRAVADAS CON 0%"));
            tablaResumen.addCell(getCellResumen(reporteVenta.getTotal0()));
            tablaResumen.addCell(getCellResumen("IVA TARIFA 12%"));
            tablaResumen.addCell(getCellResumen(reporteVenta.getTotalIva()));
            tablaResumen.addCell(getCellResumen("TOTAL"));
            tablaResumen.addCell(getCellResumen(reporteVenta.getTotal()));

            tablaFacturaYResumen.addCell(getCellFacturaYResumen(tablaFactura));
            tablaFacturaYResumen.addCell(getCellFacturaYResumen(tablaResumen));
            documento.add(tablaFacturaYResumen);

            documento.add(new Paragraph("\n"));
            documento.add(new Paragraph("REPORTE DE COBROS"));
            documento.add(new Paragraph("\n"));
            float [] columnasRecaudacion = {450F, 150F};
            Table tablaRecaudacion = new Table(columnasRecaudacion);
            tablaRecaudacion.addCell(getCellRecaudacion("EFECTIVO"));
            tablaRecaudacion.addCell(getCellRecaudacion(reporteVenta.getEfectivo()));
            tablaRecaudacion.addCell(getCellRecaudacion("CHEQUE"));
            tablaRecaudacion.addCell(getCellRecaudacion(reporteVenta.getCheque()));
            tablaRecaudacion.addCell(getCellRecaudacion("TARJETA DE CREDITO"));
            tablaRecaudacion.addCell(getCellRecaudacion(reporteVenta.getTarjetaCredito()));
            tablaRecaudacion.addCell(getCellRecaudacion("TARJETA DE DEBITO"));
            tablaRecaudacion.addCell(getCellRecaudacion(reporteVenta.getTarjetaDebito()));
            tablaRecaudacion.addCell(getCellRecaudacion("TRANSFERENCIA"));
            tablaRecaudacion.addCell(getCellRecaudacion(reporteVenta.getTransferencia()));
            tablaRecaudacion.addCell(getCellRecaudacion("CREDITO"));
            tablaRecaudacion.addCell(getCellRecaudacion(reporteVenta.getCredito()));
            documento.add(tablaRecaudacion);

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

    private Cell getCellDatoGeneral(String text, TextAlignment alignment) {
        Cell cell = new Cell().add(new Paragraph(text));
        cell.setTextAlignment(alignment);
        cell.setBorder(Border.NO_BORDER);
        cell.setFontSize(Constantes.fontSize);
        cell.setBorderBottom(new SolidBorder(ColorConstants.BLUE,1));
        cell.setBorderTop(new SolidBorder(ColorConstants.BLUE, 1));
        return cell;
    }
    private Cell getCellColumnaDocumento(String text) {
        Paragraph parrafo = new Paragraph(text);
        Cell cell = new Cell();
        cell.add(parrafo);
        cell.setFontSize(Constantes.fontSize);
        cell.setBackgroundColor(ColorConstants.BLUE).setFontColor(ColorConstants.WHITE);
        cell.setBorder(new SolidBorder(ColorConstants.BLUE,1));
        return cell;
    }

    private Cell getCellFilaDocumento(String text) {
        Paragraph parrafo = new Paragraph(text);
        Cell cell = new Cell();
        cell.add(parrafo);
        cell.setFontSize(Constantes.fontSize);
        cell.setBorder(new SolidBorder(ColorConstants.BLUE,1));
        return cell;
    }

    private Cell getCellTotal(String text) {
        Paragraph parrafo = new Paragraph(text);
        Cell cell = new Cell();
        cell.add(parrafo);
        cell.setFontSize(Constantes.fontSize);
        cell.setBorder(new SolidBorder(ColorConstants.BLUE,1));
        return cell;
    }

    private Cell getCellFactura(String text) {
        Paragraph parrafo = new Paragraph(text);
        Cell cell = new Cell();
        cell.add(parrafo);
        cell.setFontSize(Constantes.fontSize);
        return cell;
    }

    private Cell getCellResumen(String text) {
        Paragraph parrafo = new Paragraph(text);
        Cell cell = new Cell();
        cell.add(parrafo);
        cell.setFontSize(Constantes.fontSize);
        return cell;
    }

    private Cell getCellFacturaYResumen(Table tabla){
        Cell cell = new Cell();
        cell.add(tabla);
        cell.setBorder(Border.NO_BORDER);
        return cell;
    }

    private Cell getCellRecaudacion(String text) {
        Paragraph parrafo = new Paragraph(text);
        Cell cell = new Cell();
        cell.add(parrafo);
        cell.setFontSize(Constantes.fontSize);
        return cell;
    }

}
