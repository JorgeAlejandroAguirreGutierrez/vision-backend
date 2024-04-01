package com.proyecto.vision.servicios.impl.reporte;

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
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.recaudacion.*;
import com.proyecto.vision.modelos.reporte.ReporteVenta;
import com.proyecto.vision.modelos.reporte.ReporteVentaLinea;
import com.proyecto.vision.modelos.usuario.Usuario;
import com.proyecto.vision.modelos.venta.Factura;
import com.proyecto.vision.repositorios.usuario.IUsuarioRepository;
import com.proyecto.vision.repositorios.venta.IFacturaRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public ReporteVenta obtener(String apodo, String fechaInicio, String fechaFinal, long empresaId) throws ParseException {
        Date fechaInicioC = new SimpleDateFormat(Constantes.fechaCorta).parse(fechaInicio);
        Date fechaFinalC = new SimpleDateFormat(Constantes.fechaCorta).parse(fechaFinal);
        List<Factura> facturas = facturaRepository.consultarPorFechaInicioYFechaFinal(fechaInicioC, fechaFinalC, empresaId);
        Optional<Usuario> usuario = usuarioRepository.obtenerPorApodoYEstado(apodo, Constantes.estadoActivo);
        if(facturas.isEmpty()) {
            throw new EntidadNoExistenteException(Constantes.factura);
        }
        if(usuario.isEmpty()) {
            throw new EntidadNoExistenteException(Constantes.usuario);
        }
        //DATOS GENERALES
        ReporteVenta reporteVenta = new ReporteVenta();
        reporteVenta.setRazonSocial(usuario.get().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial());
        reporteVenta.setNombreComercial(usuario.get().getEstacion().getEstablecimiento().getEmpresa().getNombreComercial());
        reporteVenta.setNombreReporte(Constantes.nombreReporteVenta);
        reporteVenta.setFechaInicio(fechaInicio);
        reporteVenta.setFechaFinal(fechaFinal);
        DateFormat formatoFechaYHora = new SimpleDateFormat(Constantes.fechaYHora);
        reporteVenta.setFecha(formatoFechaYHora.format(new Date()));
        reporteVenta.setPeriodoReporte(fechaInicio + Constantes.espacio + "A" + Constantes.espacio + fechaFinal);
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
        double totalDeposito = Constantes.cero;
        double totalCredito = Constantes.cero;
        DateFormat formatoFecha = new SimpleDateFormat(Constantes.fechaCorta);
        DateFormat formatoHora = new SimpleDateFormat(Constantes.hora);
        for(Factura factura: facturas){
            ReporteVentaLinea reporteVentaLinea = new ReporteVentaLinea();
            reporteVentaLinea.setFecha(formatoFecha.format(factura.getFecha()));
            reporteVentaLinea.setHora(formatoHora.format(factura.getFecha()));
            reporteVentaLinea.setDocumento(factura.getTipoComprobante().getAbreviatura());
            reporteVentaLinea.setEstablecimiento(factura.getUsuario().getEstacion().getEstablecimiento().getCodigoSRI());
            reporteVentaLinea.setEstacion(factura.getUsuario().getEstacion().getCodigoSRI());
            reporteVentaLinea.setSecuencia(factura.getSecuencial());
            reporteVentaLinea.setCliente(factura.getCliente().getRazonSocial());
            reporteVentaLinea.setIdentificacion(factura.getCliente().getIdentificacion());
            reporteVentaLinea.setVendedor(factura.getUsuario().getApodo());
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

            reporteVentaLinea.setSubtotal0(factura.getSubtotalNoGravado());
            reporteVentaLinea.setSubtotal12(factura.getSubtotalGravado());
            reporteVentaLinea.setIva(factura.getImporteIva());
            reporteVentaLinea.setTotal(factura.getTotal());
            reporteVentaLineas.add(reporteVentaLinea);

            total0 = total0 + factura.getSubtotalNoGravado();
            total12 = total12 + factura.getSubtotalGravado();
            totalIva = totalIva + factura.getImporteIva();
            reporteTotal = reporteTotal + factura.getTotal();

            if(factura.getEstado().equals(Constantes.estadoAnulada) || factura.getProcesoSRI().equals(Constantes.procesoSRIAnulada)){
                facturasAnuladas++;
            }
            if(factura.getEstado().equals(Constantes.estadoEmitida) || factura.getEstado().equals(Constantes.estadoRecaudada)) {
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
            for(Deposito deposito: factura.getDepositos()){
                totalDeposito = totalDeposito + deposito.getValor();
            }
            if(factura.getCredito()  != null){
                totalCredito = totalCredito + factura.getCredito().getSaldo();
            }

        }
        reporteVenta.setReporteVentaLineas(reporteVentaLineas);
        //TOTALES
        reporteVenta.setTotal0(total0);
        reporteVenta.setTotal12(total12);
        reporteVenta.setTotalIva(totalIva);
        reporteVenta.setTotal(reporteTotal);

        //INFOMACION DE RESUMEN
        reporteVenta.setFacturasEmitidas(facturasEmitidas);
        reporteVenta.setFacturasAnuladas(facturasAnuladas);
        long facturasTotales = facturasEmitidas + facturasAnuladas;
        reporteVenta.setFacturasTotales(facturasTotales);

        //REPORTE DE COBROS
        reporteVenta.setEfectivo(totalEfectivo);
        reporteVenta.setCheque(totalCheque);
        reporteVenta.setTarjetaCredito(totalTarjetaCredito);
        reporteVenta.setTarjetaDebito(totalTarjetaDebito);
        reporteVenta.setTransferencia(totalTransferencia);
        reporteVenta.setDeposito(totalDeposito);
        reporteVenta.setCredito(totalCredito);
        reporteVenta.setTotalRecaudacion(totalEfectivo + totalCheque + totalTarjetaCredito + totalTarjetaDebito + totalTransferencia + totalDeposito + totalCredito);

        //FIRMAS DE RESPONSABILIDAD
        reporteVenta.setNombreRepresentanteLegal(usuario.get().getEstacion().getEstablecimiento().getEmpresa().getRepresentanteLegal());
        reporteVenta.setCargoRepresentanteLegal(usuario.get().getEstacion().getEstablecimiento().getEmpresa().getCargoRepresentanteLegal());
        reporteVenta.setEmpresaRepresentanteLegal(usuario.get().getEstacion().getEstablecimiento().getEmpresa().getNombreComercial());
        reporteVenta.setNombreUsuario(usuario.get().getNombre());
        reporteVenta.setCargoUsuario(usuario.get().getPerfil().getDescripcion());
        reporteVenta.setEmpresaUsuario(usuario.get().getEstacion().getEstablecimiento().getEmpresa().getNombreComercial());
        return reporteVenta;
    }

    public ByteArrayInputStream pdf(String apodo, String fechaInicio, String fechaFinal, long empresaId) throws ParseException {
        ReporteVenta reporteVenta = obtener(apodo, fechaInicio, fechaFinal, empresaId);
        //GENERACION DEL PDF
        try {
            ByteArrayOutputStream salida = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(salida);
            PdfDocument pdf = new PdfDocument(writer);
            // Initialize document
            Document documento = new Document(pdf, PageSize.A4);
            documento.setMargins(10, 10, 10, 10);
            // 4. Add content
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            documento.setFont(font);
            float[] columnas = {600F};
            Table tabla = new Table(columnas);
            tabla.addCell(getCellEmpresa(reporteVenta.getRazonSocial() + "\n" +
                    reporteVenta.getNombreComercial() + "\n" +
                    reporteVenta.getNombreReporte() + "\n" +
                    "VENTAS DESDE: " + reporteVenta.getFechaInicio() + " HASTA " + reporteVenta.getFechaFinal() + "\n", TextAlignment.CENTER));
            documento.add(tabla);
            documento.add(new Paragraph("\n"));
            documento.add(new Paragraph("DATOS GENERALES"));
            float[] columnasDatoGeneral = {600F};
            Table tablaDatoGeneral = new Table(columnasDatoGeneral);
            tablaDatoGeneral.addCell(getCellDatoGeneral("FECHA DE REPORTE: " + reporteVenta.getFecha() + "\n" +
                    "PERIODO DEL REPORTE: " + reporteVenta.getPeriodoReporte() + "\n" +
                    "USUARIO: " + reporteVenta.getUsuario() + "\n" +
                    "CARGO: " + reporteVenta.getPerfil(), TextAlignment.LEFT));
            documento.add(tablaDatoGeneral);
            documento.add(new Paragraph("\n"));
            documento.add(new Paragraph("DOCUMENTOS EN EL PERIODO"));
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
                tablaDocumento.addCell(getCellFilaDocumento("$" + String.format("%.2f", reporteVentaLinea.getSubtotal0())));
                tablaDocumento.addCell(getCellFilaDocumento("$" + String.format("%.2f", reporteVentaLinea.getSubtotal12())));
                tablaDocumento.addCell(getCellFilaDocumento("$" + String.format("%.2f", reporteVentaLinea.getIva())));
                tablaDocumento.addCell(getCellFilaDocumento("$" + String.format("%.2f", reporteVentaLinea.getTotal())));
            }
            tablaDocumento.addCell(getCellVacio(Constantes.vacio));
            tablaDocumento.addCell(getCellVacio(Constantes.vacio));
            tablaDocumento.addCell(getCellVacio(Constantes.vacio));
            tablaDocumento.addCell(getCellVacio(Constantes.vacio));
            tablaDocumento.addCell(getCellVacio(Constantes.vacio));
            tablaDocumento.addCell(getCellVacio(Constantes.vacio));
            tablaDocumento.addCell(getCellVacio(Constantes.vacio));
            tablaDocumento.addCell(getCellVacio(Constantes.vacio));
            tablaDocumento.addCell(getCellVacio(Constantes.vacio));
            tablaDocumento.addCell(getCellFilaDocumento("TOTALES"));
            tablaDocumento.addCell(getCellFilaDocumento("$" + String.format("%.2f", reporteVenta.getTotal0())));
            tablaDocumento.addCell(getCellFilaDocumento("$" + String.format("%.2f", reporteVenta.getTotal12())));
            tablaDocumento.addCell(getCellFilaDocumento("$" + String.format("%.2f", reporteVenta.getTotalIva())));
            tablaDocumento.addCell(getCellFilaDocumento("$" + String.format("%.2f", reporteVenta.getTotal())));
            documento.add(tablaDocumento);

            documento.add(new Paragraph("\n"));
            documento.add(new Paragraph("INFORMACION RESUMEN"));
            float [] columnasFacturaYResumen = {300F, 300F};
            Table tablaFacturaYResumen = new Table(columnasFacturaYResumen);
            float [] columnasFactura = {450F, 150F};
            Table tablaFactura = new Table(columnasFactura);
            tablaFactura.addCell(getCellFactura("FACTURAS EMITIDAS"));
            tablaFactura.addCell(getCellFactura(reporteVenta.getFacturasEmitidas()+ Constantes.vacio));
            tablaFactura.addCell(getCellFactura("FACTURAS ANULADAS"));
            tablaFactura.addCell(getCellFactura(reporteVenta.getFacturasAnuladas() + Constantes.vacio));
            tablaFactura.addCell(getCellFactura("TOTAL"));
            tablaFactura.addCell(getCellFactura(reporteVenta.getFacturasTotales() + Constantes.vacio));

            float [] columnasResumen = {450F, 150F};
            Table tablaResumen = new Table(columnasResumen);
            tablaResumen.addCell(getCellResumen("VENTAS GRAVADAS CON 12%"));
            tablaResumen.addCell(getCellResumen("$" + String.format("%.2f", reporteVenta.getTotal12())));
            tablaResumen.addCell(getCellResumen("VENTAS GRAVADAS CON 0%"));
            tablaResumen.addCell(getCellResumen("$" + String.format("%.2f", reporteVenta.getTotal0())));
            tablaResumen.addCell(getCellResumen("IVA TARIFA 12%"));
            tablaResumen.addCell(getCellResumen("$" + String.format("%.2f", reporteVenta.getTotalIva())));
            tablaResumen.addCell(getCellResumen("TOTAL"));
            tablaResumen.addCell(getCellResumen("$" + String.format("%.2f", reporteVenta.getTotal())));

            tablaFacturaYResumen.addCell(getCellFacturaYResumen(tablaFactura));
            tablaFacturaYResumen.addCell(getCellFacturaYResumen(tablaResumen));
            documento.add(tablaFacturaYResumen);

            documento.add(new Paragraph("\n"));
            documento.add(new Paragraph("REPORTE DE COBROS"));
            float [] columnasRecaudacion = {300F, 300F};
            Table tablaRecaudacion = new Table(columnasRecaudacion);
            tablaRecaudacion.addCell(getCellRecaudacion("EFECTIVO"));
            tablaRecaudacion.addCell(getCellRecaudacion("$" + String.format("%.2f", reporteVenta.getEfectivo())));
            tablaRecaudacion.addCell(getCellRecaudacion("CHEQUE"));
            tablaRecaudacion.addCell(getCellRecaudacion("$" + String.format("%.2f", reporteVenta.getCheque())));
            tablaRecaudacion.addCell(getCellRecaudacion("TARJETA DE CREDITO"));
            tablaRecaudacion.addCell(getCellRecaudacion("$" + String.format("%.2f", reporteVenta.getTarjetaCredito())));
            tablaRecaudacion.addCell(getCellRecaudacion("TARJETA DE DEBITO"));
            tablaRecaudacion.addCell(getCellRecaudacion("$" + String.format("%.2f", reporteVenta.getTarjetaDebito())));
            tablaRecaudacion.addCell(getCellRecaudacion("TRANSFERENCIA"));
            tablaRecaudacion.addCell(getCellRecaudacion("$" + String.format("%.2f", reporteVenta.getTransferencia())));
            tablaRecaudacion.addCell(getCellRecaudacion("CREDITO"));
            tablaRecaudacion.addCell(getCellRecaudacion("$" + String.format("%.2f", reporteVenta.getCredito())));
            documento.add(tablaRecaudacion);

            documento.add(new Paragraph("\n"));
            documento.add(new Paragraph("FIRMAS DE RESPONSABILIDAD"));
            documento.add(new Paragraph("\n"));
            float [] columnasFirma = {300F, 300F};
            Table tablaFirma = new Table(columnasFirma);
            float [] columnasFirmaGerente = {600F};
            Table tablaFirmaGerente = new Table(columnasFirmaGerente);
            tablaFirmaGerente.addCell(getCellFirmaGerente("_____________________________"));
            tablaFirmaGerente.addCell(getCellFirmaGerente(reporteVenta.getNombreRepresentanteLegal()));
            tablaFirmaGerente.addCell(getCellFirmaGerente(reporteVenta.getCargoRepresentanteLegal()));
            tablaFirmaGerente.addCell(getCellFirmaGerente(reporteVenta.getEmpresaRepresentanteLegal()));
            float [] columnasFirmaUsuario = {600F};
            Table tablaFirmaUsuario = new Table(columnasFirmaUsuario);
            tablaFirmaUsuario.addCell(getCellFirmaUsuario("_____________________________"));
            tablaFirmaUsuario.addCell(getCellFirmaUsuario(reporteVenta.getNombreUsuario()));
            tablaFirmaUsuario.addCell(getCellFirmaUsuario(reporteVenta.getCargoUsuario()));
            tablaFirmaUsuario.addCell(getCellFirmaUsuario(reporteVenta.getEmpresaUsuario()));

            tablaFirma.addCell(getCellFirma(tablaFirmaGerente));
            tablaFirma.addCell(getCellFirma(tablaFirmaUsuario));
            documento.add(tablaFirma);

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
        cell.setBorder(Border.NO_BORDER);
        cell.setFontSize(Constantes.fontSize16);
        cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK,1));
        cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
        return cell;
    }

    private Cell getCellDatoGeneral(String text, TextAlignment alignment) {
        Cell cell = new Cell().add(new Paragraph(text));
        cell.setTextAlignment(alignment);
        cell.setBorder(Border.NO_BORDER);
        cell.setFontSize(Constantes.fontSize10);
        cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK,1));
        cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
        return cell;
    }
    private Cell getCellColumnaDocumento(String text) {
        Paragraph parrafo = new Paragraph(text);
        Cell cell = new Cell();
        cell.add(parrafo);
        cell.setFontSize(Constantes.fontSize7);
        cell.setFontColor(ColorConstants.BLACK);
        cell.setBorder(new SolidBorder(ColorConstants.BLACK,1));
        return cell;
    }

    private Cell getCellFilaDocumento(String text) {
        Paragraph parrafo = new Paragraph(text);
        Cell cell = new Cell();
        cell.add(parrafo);
        cell.setFontSize(Constantes.fontSize7);
        cell.setBorder(new SolidBorder(ColorConstants.BLACK,1));
        return cell;
    }

    private Cell getCellVacio(String text) {
        Paragraph parrafo = new Paragraph(text);
        Cell cell = new Cell();
        cell.add(parrafo);
        cell.setFontSize(Constantes.fontSize7);
        cell.setBorder(Border.NO_BORDER);
        return cell;
    }

    private Cell getCellFactura(String text) {
        Paragraph parrafo = new Paragraph(text);
        Cell cell = new Cell();
        cell.add(parrafo);
        cell.setFontSize(Constantes.fontSize10);
        return cell;
    }

    private Cell getCellResumen(String text) {
        Paragraph parrafo = new Paragraph(text);
        Cell cell = new Cell();
        cell.add(parrafo);
        cell.setFontSize(Constantes.fontSize10);
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
        cell.setFontSize(Constantes.fontSize10);
        return cell;
    }

    private Cell getCellFirma(Table tabla){
        Cell cell = new Cell();
        cell.add(tabla);
        cell.setBorder(Border.NO_BORDER);
        cell.setTextAlignment(TextAlignment.CENTER);
        return cell;
    }

    private Cell getCellFirmaGerente(String text) {
        Paragraph parrafo = new Paragraph(text);
        Cell cell = new Cell();
        cell.add(parrafo);
        cell.setBorder(Border.NO_BORDER);
        cell.setFontSize(Constantes.fontSize10);
        cell.setTextAlignment(TextAlignment.CENTER);
        return cell;
    }

    private Cell getCellFirmaUsuario(String text) {
        Paragraph parrafo = new Paragraph(text);
        Cell cell = new Cell();
        cell.add(parrafo);
        cell.setBorder(Border.NO_BORDER);
        cell.setFontSize(Constantes.fontSize10);
        cell.setTextAlignment(TextAlignment.CENTER);
        return cell;
    }

    public ByteArrayInputStream excel(String apodo, String fechaInicio, String fechaFinal, long empresaId) {
        try {
            ReporteVenta reporteVenta = obtener(apodo, fechaInicio, fechaFinal, empresaId);
            // Blank workbook
            XSSFWorkbook workbook = new XSSFWorkbook();
            // Creating a blank Excel sheet
            XSSFSheet sheet = workbook.createSheet("REPORTE VENTA");
            sheet.setColumnWidth(0, 25 * 256);
            sheet.setColumnWidth(1, 25 * 256);
            sheet.setColumnWidth(2, 25 * 256);
            sheet.setColumnWidth(3, 25 * 256);
            sheet.setColumnWidth(4, 25 * 256);
            sheet.setColumnWidth(5, 50 * 256);
            sheet.setColumnWidth(6, 25 * 256);
            sheet.setColumnWidth(7, 25 * 256);
            sheet.setColumnWidth(8, 25 * 256);
            sheet.setColumnWidth(9, 25 * 256);
            sheet.setColumnWidth(10, 25 * 256);
            sheet.setColumnWidth(11, 25 * 256);
            sheet.setColumnWidth(12, 25 * 256);
            int i = 0;
            Row row = sheet.createRow(i);
            row.createCell(6).setCellValue(reporteVenta.getRazonSocial());
            i++;
            row = sheet.createRow(i);
            row.createCell(6).setCellValue(reporteVenta.getNombreComercial());
            i++;
            row = sheet.createRow(i);
            row.createCell(6).setCellValue(reporteVenta.getNombreReporte());
            i++;
            row = sheet.createRow(i);
            row.createCell(6).setCellValue("FECHA: " + reporteVenta.getFecha());
            i++;
            row = sheet.createRow(i);
            row.createCell(6).setCellValue("FECHA INICIO: " + reporteVenta.getFechaInicio());
            i++;
            row = sheet.createRow(i);
            row.createCell(6).setCellValue("FECHA FINAL: " + reporteVenta.getFechaFinal());
            i++;
            row = sheet.createRow(i);
            row.createCell(0).setCellValue("DOCUMENTOS EN EL PERIODO");
            i++;
            row = sheet.createRow(i);
            row.createCell(0).setCellValue("FECHA");
            row.createCell(1).setCellValue("HORA");
            row.createCell(2).setCellValue("DOC");
            row.createCell(3).setCellValue("ESTAB");
            row.createCell(4).setCellValue("SECUENCIA");
            row.createCell(5).setCellValue("CLIENTE");
            row.createCell(6).setCellValue("IDENT");
            row.createCell(7).setCellValue("VEND");
            row.createCell(8).setCellValue("TIPO VENTA");
            row.createCell(9).setCellValue("SUBTOTAL 0");
            row.createCell(10).setCellValue("SUBTOTAL IVA");
            row.createCell(11).setCellValue("IVA");
            row.createCell(12).setCellValue("TOTAL");
            i++;
            for(ReporteVentaLinea linea: reporteVenta.getReporteVentaLineas()){
                row = sheet.createRow(i);
                row.createCell(0).setCellValue(linea.getFecha());
                row.createCell(1).setCellValue(linea.getHora());
                row.createCell(2).setCellValue(linea.getDocumento());
                row.createCell(3).setCellValue(linea.getEstablecimiento());
                row.createCell(4).setCellValue(linea.getSecuencia());
                row.createCell(5).setCellValue(linea.getCliente());
                row.createCell(6).setCellValue(linea.getIdentificacion());
                row.createCell(7).setCellValue(linea.getVendedor());
                row.createCell(8).setCellValue(linea.getTipoVenta());
                row.createCell(9).setCellValue(linea.getSubtotal0());
                row.createCell(10).setCellValue(linea.getSubtotal12());
                row.createCell(11).setCellValue(linea.getIva());
                row.createCell(12).setCellValue(linea.getTotal());
                i++;
            }
            row = sheet.createRow(i);
            row.createCell(8).setCellValue("TOTALES");
            row.createCell(9).setCellValue(reporteVenta.getTotal0());
            row.createCell(10).setCellValue(reporteVenta.getTotal12());
            row.createCell(11).setCellValue(reporteVenta.getTotalIva());
            row.createCell(12).setCellValue(reporteVenta.getTotal());
            i++;
            i++;
            row = sheet.createRow(i);
            row.createCell(0).setCellValue("INFORMACION RESUMEN");
            i++;
            row = sheet.createRow(i);
            row.createCell(0).setCellValue("FACTURAS EMITIDAS");
            row.createCell(1).setCellValue(reporteVenta.getFacturasEmitidas());
            i++;
            row = sheet.createRow(i);
            row.createCell(0).setCellValue("FACTURAS ANULADAS");
            row.createCell(1).setCellValue(reporteVenta.getFacturasEmitidas());
            i++;
            row = sheet.createRow(i);
            row.createCell(0).setCellValue("TOTAL");
            row.createCell(1).setCellValue(reporteVenta.getFacturasTotales());
            i++;
            row = sheet.createRow(i);
            row.createCell(0).setCellValue("VENTAS GRAVADAS CON 12%");
            row.createCell(1).setCellValue(reporteVenta.getTotal12());
            i++;
            row = sheet.createRow(i);
            row.createCell(0).setCellValue("VENTAS GRAVADAS CON 0%");
            row.createCell(1).setCellValue(reporteVenta.getTotal0());
            i++;
            row = sheet.createRow(i);
            row.createCell(0).setCellValue("IVA TARIFA 12%");
            row.createCell(1).setCellValue(reporteVenta.getTotalIva());
            i++;
            row = sheet.createRow(i);
            row.createCell(0).setCellValue("TOTAL");
            row.createCell(1).setCellValue(reporteVenta.getTotal());
            i++;
            i++;
            row = sheet.createRow(i);
            row.createCell(0).setCellValue("REPORTE COBROS");
            i++;
            row = sheet.createRow(i);
            row.createCell(0).setCellValue("EFECTIVO");
            row.createCell(1).setCellValue(reporteVenta.getEfectivo());
            i++;
            row = sheet.createRow(i);
            row.createCell(0).setCellValue("CHEQUE");
            row.createCell(1).setCellValue(reporteVenta.getCheque());
            i++;
            row = sheet.createRow(i);
            row.createCell(0).setCellValue("TARJETA DE CREDITO");
            row.createCell(1).setCellValue(reporteVenta.getTarjetaCredito());
            i++;
            row = sheet.createRow(i);
            row.createCell(0).setCellValue("TARJETA DE DEBITO");
            row.createCell(1).setCellValue(reporteVenta.getTarjetaDebito());
            i++;
            row = sheet.createRow(i);
            row.createCell(0).setCellValue("TRANSFERENCIA");
            row.createCell(1).setCellValue(reporteVenta.getTransferencia());
            i++;
            row = sheet.createRow(i);
            row.createCell(0).setCellValue("CREDITO");
            row.createCell(1).setCellValue(reporteVenta.getCredito());

            // Writing the workbook
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            // Closing file output connections
            out.close();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}