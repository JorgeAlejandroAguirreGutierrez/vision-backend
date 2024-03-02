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
import com.itextpdf.layout.property.TextAlignment;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.inventario.Kardex;
import com.proyecto.vision.modelos.inventario.Producto;
import com.proyecto.vision.modelos.reporte.ReporteExistencia;
import com.proyecto.vision.modelos.reporte.ReporteExistenciaLinea;
import com.proyecto.vision.modelos.usuario.Usuario;
import com.proyecto.vision.repositorios.inventario.IKardexRepository;
import com.proyecto.vision.repositorios.inventario.IProductoRepository;
import com.proyecto.vision.repositorios.usuario.IUsuarioRepository;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReporteExistenciaService {

    @Autowired
    private IKardexRepository kardexRepository;

    @Autowired
    private IProductoRepository productoRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    public ReporteExistencia obtener(String apodo, String fechaCorte, long empresaId) throws ParseException {
        Date fechaCorteC = new SimpleDateFormat(Constantes.fechaCorta).parse(fechaCorte);
        List<Producto> productos = productoRepository.consultarPorEmpresaYEstado(empresaId, Constantes.estadoActivo);
        Optional<Usuario> usuario = usuarioRepository.obtenerPorApodoYEstado(apodo, Constantes.estadoActivo);
        if(usuario.isEmpty()) {
            throw new EntidadNoExistenteException(Constantes.usuario);
        }
        //DATOS GENERALES
        ReporteExistencia reporteExistencia = new ReporteExistencia();
        reporteExistencia.setFechaCorte(fechaCorte);
        reporteExistencia.setRazonSocial(usuario.get().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial());
        reporteExistencia.setNombreComercial(usuario.get().getEstacion().getEstablecimiento().getEmpresa().getNombreComercial());
        reporteExistencia.setNombreReporte(Constantes.nombreReporteExistencia);
        DateFormat formatoFechaYHora = new SimpleDateFormat(Constantes.fechaYHora);
        reporteExistencia.setFecha(formatoFechaYHora.format(new Date()));
        reporteExistencia.setUsuario(usuario.get().getApodo());
        reporteExistencia.setPerfil(usuario.get().getPerfil().getDescripcion());
        reporteExistencia.setReporteExistenciaLineas(new ArrayList<>());
        double totalExistencia = Constantes.cero;
        double totalCostoUnitario = Constantes.cero;
        double totalCosto = Constantes.cero;
        for(Producto producto: productos){
            if(producto.getCategoriaProducto().getAbreviatura().equals(Constantes.abreviatura_bien)){
                ReporteExistenciaLinea reporteExistenciaLinea = new ReporteExistenciaLinea();
                reporteExistenciaLinea.setCodigo(producto.getCodigo());
                reporteExistenciaLinea.setNombre(producto.getNombre());
                reporteExistenciaLinea.setIva(producto.getImpuesto().getCodigoSRI());
                if(producto.getKardexs().size() > Constantes.ceroId){
                    Optional<Kardex> kardex = kardexRepository.obtenerUltimoPorProductoYFechaYEstado(producto.getId(), fechaCorteC, Constantes.estadoActivo);
                    if(kardex.isPresent()){
                        reporteExistenciaLinea.setExistencia(kardex.get().getSaldo() + Constantes.vacio);
                        reporteExistenciaLinea.setCostoUnitario(kardex.get().getCostoPromedio() + Constantes.vacio);
                        reporteExistenciaLinea.setCostoTotal(kardex.get().getCostoTotal() + Constantes.vacio);
                        totalExistencia = totalExistencia + kardex.get().getSaldo();
                        totalCostoUnitario = totalCostoUnitario + kardex.get().getCostoPromedio();
                        totalCosto = totalCosto + kardex.get().getCostoTotal();
                    } else{
                        reporteExistenciaLinea.setExistencia(Constantes.cero + Constantes.vacio);
                        reporteExistenciaLinea.setCostoUnitario(Constantes.cero + Constantes.vacio);
                        reporteExistenciaLinea.setCostoTotal(Constantes.cero + Constantes.vacio);
                        totalExistencia = totalExistencia + Constantes.cero;
                        totalCostoUnitario = totalCostoUnitario + Constantes.cero;
                        totalCosto = totalCosto + Constantes.cero;
                    }
                } else {
                    reporteExistenciaLinea.setExistencia(Constantes.cero + Constantes.vacio);
                    reporteExistenciaLinea.setCostoUnitario(Constantes.cero + Constantes.vacio);
                    reporteExistenciaLinea.setCostoTotal(Constantes.cero + Constantes.vacio);
                    totalExistencia = totalExistencia + Constantes.cero;
                    totalCostoUnitario = totalCostoUnitario + Constantes.cero;
                    totalCosto = totalCosto + Constantes.cero;
                }
                reporteExistencia.getReporteExistenciaLineas().add(reporteExistenciaLinea);
            }
        }
        //TOTALES
        reporteExistencia.setTotalExistencia(totalExistencia + Constantes.vacio);
        reporteExistencia.setTotalCostoUnitario(totalCostoUnitario + Constantes.vacio);
        reporteExistencia.setTotalCosto(totalCosto + Constantes.vacio);
        //FIRMAS DE RESPONSABILIDAD
        reporteExistencia.setNombreRepresentanteLegal(usuario.get().getEstacion().getEstablecimiento().getEmpresa().getRepresentanteLegal());
        reporteExistencia.setCargoRepresentanteLegal(usuario.get().getEstacion().getEstablecimiento().getEmpresa().getCargoRepresentanteLegal());
        reporteExistencia.setEmpresaRepresentanteLegal(usuario.get().getEstacion().getEstablecimiento().getEmpresa().getNombreComercial());
        reporteExistencia.setNombreUsuario(usuario.get().getNombre());
        reporteExistencia.setCargoUsuario(usuario.get().getPerfil().getDescripcion());
        reporteExistencia.setEmpresaUsuario(usuario.get().getEstacion().getEstablecimiento().getEmpresa().getNombreComercial());
        return reporteExistencia;
    }

    public ByteArrayInputStream pdf(String apodo, String fechaCorte, long empresaId) throws ParseException {
        ReporteExistencia reporteExistencia = obtener(apodo, fechaCorte, empresaId);
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
            tabla.addCell(getCellEmpresa(reporteExistencia.getRazonSocial() + "\n" +
                    reporteExistencia.getNombreComercial() + "\n" +
                    reporteExistencia.getNombreReporte() + "\n" +
                    "FECHA: " + reporteExistencia.getFecha() + "\n" +
                    "FECHA DE CORTE: " + reporteExistencia.getFechaCorte(), TextAlignment.CENTER));
            documento.add(tabla);
            documento.add(new Paragraph("\n"));
            documento.add(new Paragraph("DATOS GENERALES"));
            float[] columnasDatoGeneral = {600F};
            Table tablaDatoGeneral = new Table(columnasDatoGeneral);
            tablaDatoGeneral.addCell(getCellDatoGeneral(
                    "USUARIO: " + reporteExistencia.getUsuario() + "\n" +
                    "CARGO: " + reporteExistencia.getPerfil(), TextAlignment.LEFT));
            documento.add(tablaDatoGeneral);
            documento.add(new Paragraph("\n"));
            documento.add(new Paragraph("EXISTENCIAS EN EL PERIODO"));
            float[] columnasTablaDocumento = { 120F, 120F, 120F, 120F, 120F };
            Table tablaDocumento = new Table(columnasTablaDocumento);
            tablaDocumento.addCell(getCellColumnaDocumento("CODIGO"));
            tablaDocumento.addCell(getCellColumnaDocumento("NOMBRE"));
            tablaDocumento.addCell(getCellColumnaDocumento("IVA"));
            tablaDocumento.addCell(getCellColumnaDocumento("EXISTENCIA"));
            tablaDocumento.addCell(getCellColumnaDocumento("COSTO TOTAL"));
            for (ReporteExistenciaLinea reporteExistenciaLinea : reporteExistencia.getReporteExistenciaLineas())
            {
                tablaDocumento.addCell(getCellFilaDocumento(reporteExistenciaLinea.getCodigo()));
                tablaDocumento.addCell(getCellFilaDocumento(reporteExistenciaLinea.getNombre()));
                tablaDocumento.addCell(getCellFilaDocumento(reporteExistenciaLinea.getIva()));
                tablaDocumento.addCell(getCellFilaDocumento(reporteExistenciaLinea.getExistencia()));
                tablaDocumento.addCell(getCellFilaDocumento(reporteExistenciaLinea.getCostoTotal()));
            }
            tablaDocumento.addCell(getCellVacio(Constantes.vacio));
            tablaDocumento.addCell(getCellFilaDocumento("TOTALES"));
            tablaDocumento.addCell(getCellFilaDocumento(reporteExistencia.getTotalExistencia()));
            tablaDocumento.addCell(getCellFilaDocumento(reporteExistencia.getTotalCostoUnitario()));
            tablaDocumento.addCell(getCellFilaDocumento("$" + reporteExistencia.getTotalCosto()));
            documento.add(tablaDocumento);

            documento.add(new Paragraph("\n"));
            documento.add(new Paragraph("FIRMAS DE RESPONSABILIDAD"));
            documento.add(new Paragraph("\n"));
            float [] columnasFirma = {300F, 300F};
            Table tablaFirma = new Table(columnasFirma);
            float [] columnasFirmaGerente = {600F};
            Table tablaFirmaGerente = new Table(columnasFirmaGerente);
            tablaFirmaGerente.addCell(getCellFirmaGerente("_____________________________"));
            tablaFirmaGerente.addCell(getCellFirmaGerente(reporteExistencia.getNombreRepresentanteLegal()));
            tablaFirmaGerente.addCell(getCellFirmaGerente(reporteExistencia.getCargoRepresentanteLegal()));
            tablaFirmaGerente.addCell(getCellFirmaGerente(reporteExistencia.getEmpresaRepresentanteLegal()));
            float [] columnasFirmaUsuario = {600F};
            Table tablaFirmaUsuario = new Table(columnasFirmaUsuario);
            tablaFirmaUsuario.addCell(getCellFirmaUsuario("_____________________________"));
            tablaFirmaUsuario.addCell(getCellFirmaUsuario(reporteExistencia.getNombreUsuario()));
            tablaFirmaUsuario.addCell(getCellFirmaUsuario(reporteExistencia.getCargoUsuario()));
            tablaFirmaUsuario.addCell(getCellFirmaUsuario(reporteExistencia.getEmpresaUsuario()));

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

    public ByteArrayInputStream excel(String apodo, String fechaCorte, long empresaId) {
        try {
            ReporteExistencia reporteExistencia = obtener(apodo, fechaCorte, empresaId);
            // Blank workbook
            XSSFWorkbook workbook = new XSSFWorkbook();
            // Creating a blank Excel sheet
            XSSFSheet sheet = workbook.createSheet("REPORTE EXISTENCIA");
            sheet.setColumnWidth(0, 25 * 256);
            sheet.setColumnWidth(1, 50 * 256);
            sheet.setColumnWidth(2, 25 * 256);
            sheet.setColumnWidth(3, 25 * 256);
            sheet.setColumnWidth(4, 25 * 256);
            sheet.setColumnWidth(5, 25 * 256);
            int i = 0;
            Row row = sheet.createRow(i);
            row.createCell(2).setCellValue(reporteExistencia.getRazonSocial());
            i++;
            row = sheet.createRow(i);
            row.createCell(2).setCellValue(reporteExistencia.getNombreComercial());
            i++;
            row = sheet.createRow(i);
            row.createCell(2).setCellValue(reporteExistencia.getNombreReporte());
            i++;
            row = sheet.createRow(i);
            row.createCell(2).setCellValue("FECHA: " + reporteExistencia.getFecha());
            i++;
            row = sheet.createRow(i);
            row.createCell(2).setCellValue("FECHA DE CORTE: " + reporteExistencia.getFechaCorte());
            i++;
            row = sheet.createRow(i);
            row.createCell(0).setCellValue("CODIGO");
            row.createCell(1).setCellValue("NOMBRE");
            row.createCell(2).setCellValue("IVA");
            row.createCell(3).setCellValue("EXISTENCIA");
            row.createCell(4).setCellValue("COSTO UNITARIO");
            row.createCell(5).setCellValue("COSTO TOTAL");
            i++;
            for(ReporteExistenciaLinea linea: reporteExistencia.getReporteExistenciaLineas()){
                row = sheet.createRow(i);
                row.createCell(0).setCellValue(linea.getCodigo());
                row.createCell(1).setCellValue(linea.getNombre());
                row.createCell(2).setCellValue(Integer.parseInt(linea.getIva()));
                row.createCell(3).setCellValue(Double.parseDouble(linea.getExistencia()));
                row.createCell(4).setCellValue(Double.parseDouble(linea.getCostoUnitario()));
                row.createCell(5).setCellValue(Double.parseDouble(linea.getCostoTotal()));
                i++;
            }
            row = sheet.createRow(i);
            row.createCell(2).setCellValue("TOTALES");
            row.createCell(3).setCellValue(Double.parseDouble(reporteExistencia.getTotalExistencia()));
            row.createCell(4).setCellValue(Double.parseDouble(reporteExistencia.getTotalCostoUnitario()));
            row.createCell(5).setCellValue(Double.parseDouble(reporteExistencia.getTotalCosto()));
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