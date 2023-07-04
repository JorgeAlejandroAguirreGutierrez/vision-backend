package com.proyecto.sicecuador.servicios.impl.reporte;

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
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.inventario.Kardex;
import com.proyecto.sicecuador.modelos.inventario.Producto;
import com.proyecto.sicecuador.modelos.reporte.ReporteKardex;
import com.proyecto.sicecuador.modelos.reporte.ReporteKardexLinea;
import com.proyecto.sicecuador.modelos.usuario.Usuario;
import com.proyecto.sicecuador.repositorios.inventario.IKardexRepository;
import com.proyecto.sicecuador.repositorios.inventario.IProductoRepository;
import com.proyecto.sicecuador.repositorios.usuario.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReporteKardexService {

    @Autowired
    private IKardexRepository kardexRepository;

    @Autowired
    private IProductoRepository productoRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    public ReporteKardex obtener(String apodo, String fechaInicio, String fechaFinal, long productoId) throws ParseException {
        Date fechaInicioC = new SimpleDateFormat(Constantes.fechaCorta).parse(fechaInicio);
        Date fechaFinalC = new SimpleDateFormat(Constantes.fechaCorta).parse(fechaFinal);
        Optional<Producto> producto = productoRepository.findById(productoId);
        List<Kardex> kardexs = kardexRepository.consultarPorFechaInicioYFechaFinalYProducto(fechaInicioC, fechaFinalC, productoId);
        Optional<Usuario> usuario = usuarioRepository.obtenerPorApodoYEstado(apodo, Constantes.activo);
        if (producto.isEmpty()) {
            throw new EntidadNoExistenteException(Constantes.producto);
        }
        if (kardexs.isEmpty()) {
            throw new EntidadNoExistenteException(Constantes.kardex);
        }
        if (usuario.isEmpty()) {
            throw new EntidadNoExistenteException(Constantes.usuario);
        }
        //DATOS GENERALES
        ReporteKardex reporteKardex = new ReporteKardex();
        reporteKardex.setRazonSocial(usuario.get().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial());
        reporteKardex.setNombreComercial(usuario.get().getEstacion().getEstablecimiento().getEmpresa().getNombreComercial());
        reporteKardex.setNombreReporte(Constantes.nombreReporteKardex);
        reporteKardex.setFechaInicio(fechaInicio);
        reporteKardex.setFechaFinal(fechaFinal);
        DateFormat formatoFecha = new SimpleDateFormat(Constantes.fechaYHora);
        reporteKardex.setFecha(formatoFecha.format(new Date()));
        reporteKardex.setPeriodoDelReporte(fechaInicio + Constantes.espacio + "A" + Constantes.espacio + fechaFinal);
        reporteKardex.setUsuario(usuario.get().getApodo());
        reporteKardex.setPerfil(usuario.get().getPerfil().getDescripcion());
        //PRODUCTO
        reporteKardex.setNombre(producto.get().getNombre());
        reporteKardex.setModelo(Constantes.vacio);
        reporteKardex.setSerie(Constantes.vacio);
        reporteKardex.setCaducidad(Constantes.vacio);
        //VALORACION
        reporteKardex.setCostoPromedio(Constantes.vacio);
        reporteKardex.setUltimoCosto(Constantes.vacio);
        reporteKardex.setMargenRentabilidadPromedio(Constantes.vacio);
        reporteKardex.setTarifaProducto(Constantes.vacio);
        List<ReporteKardexLinea> reporteKardexLineas = new ArrayList();
        for (Kardex kardex : kardexs) {
            ReporteKardexLinea reporteKardexLinea = new ReporteKardexLinea();
            reporteKardexLinea.setOperacion(kardex.getTipoOperacion().getAbreviatura());
            reporteKardexLinea.setFecha(formatoFecha.format(kardex.getFecha()));
            reporteKardexLinea.setOrg(kardex.getTipoComprobante().getAbreviatura());
            reporteKardexLinea.setBodega(kardex.getBodega().getAbreviatura());
            reporteKardexLinea.setDocumento(kardex.getReferencia());
            reporteKardexLinea.setProvCliente(producto.get().getProveedor().getRazonSocial());
            reporteKardexLinea.setIngresoCantidad(String.valueOf(kardex.getEntrada()));
            reporteKardexLinea.setIngresoCosto(String.valueOf(kardex.getCostoPromedio()));
            reporteKardexLinea.setIngresoCostoTotal(String.valueOf(kardex.getCostoTotal()));
            reporteKardexLinea.setSalidaCantidad(String.valueOf(kardex.getSalida()));
            reporteKardexLinea.setSalidaCosto(String.valueOf(kardex.getCostoPromedio()));
            reporteKardexLinea.setSalidaCostoTotal(String.valueOf(kardex.getCostoTotal()));
            reporteKardexLinea.setExistenciaCantidad(String.valueOf(kardex.getSaldo()));
            reporteKardexLinea.setExistenciaCosto(String.valueOf(kardex.getCostoPromedio()));
            reporteKardexLinea.setExistenciaCostoTotal(String.valueOf(kardex.getCostoTotal()));
            reporteKardexLineas.add(reporteKardexLinea);
        }
        reporteKardex.setReporteKardexLineas(reporteKardexLineas);
        return reporteKardex;
    }

    public ByteArrayInputStream pdf(String apodo, String fechaInicio, String fechaFinal, long productoId) throws ParseException {
        ReporteKardex reporteKardex = obtener(apodo, fechaInicio, fechaFinal, productoId);
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
            tabla.addCell(getCellEmpresa(reporteKardex.getRazonSocial() + "\n" +
                    reporteKardex.getNombreComercial() + "\n" +
                    reporteKardex.getNombreReporte() + "\n" +
                    "REPORTE DESDE: " + reporteKardex.getFechaInicio() + " HASTA " + reporteKardex.getFechaFinal() + "\n", TextAlignment.CENTER));
            documento.add(tabla);
            documento.add(new Paragraph("\n"));
            documento.add(new Paragraph("DATOS GENERALES"));
            float[] columnasDatoGeneral = {600F};
            Table tablaDatoGeneral = new Table(columnasDatoGeneral);
            tablaDatoGeneral.addCell(getCellDatoGeneral(
                    "FECHA DE REPORTE: " + reporteKardex.getFecha() + "\n" +
                    "PERIODO DEL REPORTE: " + reporteKardex.getPeriodoDelReporte() + "\n" +
                    "USUARIO: " + reporteKardex.getUsuario() + "\n" +
                    "CARGO: " + reporteKardex.getPerfil(), TextAlignment.LEFT));
            documento.add(tablaDatoGeneral);
            documento.add(new Paragraph("\n"));
            documento.add(new Paragraph("PRODUCTO"));
            float[] columnasProducto = {600F};
            Table tablaProducto = new Table(columnasProducto);
            tablaProducto.addCell(getCellProducto(
                    "NOMBRE: " + reporteKardex.getNombre() + "\n" +
                    "MODELO: " + reporteKardex.getModelo() + "\n" +
                    "SERIE: " + reporteKardex.getSerie() + "\n" +
                    "CADUCIDAD: " + reporteKardex.getCaducidad(), TextAlignment.LEFT));
            documento.add(tablaProducto);
            documento.add(new Paragraph("\n"));
            documento.add(new Paragraph("VALORACION"));
            float[] columnasValoracion = {600F};
            Table tablaValoracion = new Table(columnasValoracion);
            tablaValoracion.addCell(getCellValoracion(
                    "COSTO PROMEDIO: " + reporteKardex.getCostoPromedio() + "\n" +
                    "ULTIMO COSTO: " + reporteKardex.getUltimoCosto() + "\n" +
                    "MARGEN RENTABILIDAD PROMEDIO: " + reporteKardex.getMargenRentabilidadPromedio() + "\n" +
                    "TARIFA PRODUCTO: " + reporteKardex.getTarifaProducto(), TextAlignment.LEFT));
            documento.add(tablaValoracion);
            float[] columnasTablaKardex = {100F, 100F, 100F, 100F, 100F, 100F};
            Table tablaKardex = new Table(columnasTablaKardex);
            tablaKardex.addCell(getCellColumnaKardex("ID"));
            tablaKardex.addCell(getCellColumnaKardex("FECHA"));
            tablaKardex.addCell(getCellColumnaKardex("ORG"));
            tablaKardex.addCell(getCellColumnaKardex("BOD"));
            tablaKardex.addCell(getCellColumnaKardex("DOC"));
            tablaKardex.addCell(getCellColumnaKardex("PROV/CLI"));

            float[] columnasTablaIngreso = {150F, 150F, 150F, 150F};
            Table tablaIngreso = new Table(columnasTablaIngreso);
            tablaIngreso.addCell(getCellColumnaIngreso("ID"));
            tablaIngreso.addCell(getCellColumnaIngreso("CANTIDAD"));
            tablaIngreso.addCell(getCellColumnaIngreso("COSTO"));
            tablaIngreso.addCell(getCellColumnaIngreso("COSTO TOTAL"));

            float[] columnasTablaSalida = {150F, 150F, 150F, 150F};
            Table tablaSalida = new Table(columnasTablaSalida);
            tablaSalida.addCell(getCellColumnaSalida("ID"));
            tablaSalida.addCell(getCellColumnaSalida("CANTIDAD"));
            tablaSalida.addCell(getCellColumnaSalida("COSTO"));
            tablaSalida.addCell(getCellColumnaSalida("COSTO TOTAL"));

            float[] columnasTablaExistencia = {150F, 150F, 150F, 150F};
            Table tablaExistencia = new Table(columnasTablaExistencia);
            tablaExistencia.addCell(getCellColumnaExistencia("ID"));
            tablaExistencia.addCell(getCellColumnaExistencia("CANTIDAD"));
            tablaExistencia.addCell(getCellColumnaExistencia("COSTO"));
            tablaExistencia.addCell(getCellColumnaExistencia("COSTO TOTAL"));
            int id = 1;
            for (ReporteKardexLinea reporteKardexLinea : reporteKardex.getReporteKardexLineas()) {
                tablaKardex.addCell(getCellFilaKardex(id + Constantes.vacio));
                tablaKardex.addCell(getCellFilaKardex(reporteKardexLinea.getFecha()));
                tablaKardex.addCell(getCellFilaKardex(reporteKardexLinea.getOrg()));
                tablaKardex.addCell(getCellFilaKardex(reporteKardexLinea.getBodega()));
                tablaKardex.addCell(getCellFilaKardex(reporteKardexLinea.getDocumento()));
                tablaKardex.addCell(getCellFilaKardex(reporteKardexLinea.getProvCliente()));
                tablaIngreso.addCell(getCellFilaIngreso(id + Constantes.vacio));
                tablaIngreso.addCell(getCellFilaIngreso(reporteKardexLinea.getIngresoCantidad()));
                tablaIngreso.addCell(getCellFilaIngreso(reporteKardexLinea.getIngresoCosto()));
                tablaIngreso.addCell(getCellFilaIngreso(reporteKardexLinea.getIngresoCostoTotal()));
                tablaSalida.addCell(getCellFilaSalida(id + Constantes.vacio));
                tablaSalida.addCell(getCellFilaSalida(reporteKardexLinea.getSalidaCantidad()));
                tablaSalida.addCell(getCellFilaSalida(reporteKardexLinea.getSalidaCosto()));
                tablaSalida.addCell(getCellFilaSalida(reporteKardexLinea.getSalidaCostoTotal()));
                tablaExistencia.addCell(getCellFilaExistencia(id + Constantes.vacio));
                tablaExistencia.addCell(getCellFilaExistencia(reporteKardexLinea.getExistenciaCantidad()));
                tablaExistencia.addCell(getCellFilaExistencia(reporteKardexLinea.getExistenciaCosto()));
                tablaExistencia.addCell(getCellFilaExistencia(reporteKardexLinea.getExistenciaCostoTotal()));
                id++;
            }
            documento.add(new Paragraph("\n"));
            documento.add(new Paragraph("TARJETA KARDEX"));
            documento.add(tablaKardex);

            documento.add(new Paragraph("\n"));
            documento.add(new Paragraph("INGRESOS"));
            documento.add(tablaIngreso);

            documento.add(new Paragraph("\n"));
            documento.add(new Paragraph("SALIDAS"));
            documento.add(tablaSalida);

            documento.add(new Paragraph("\n"));
            documento.add(new Paragraph("EXISTENCIAS"));
            documento.add(tablaExistencia);

            documento.add(new Paragraph("\n"));
            documento.add(new Paragraph("FIRMAS DE RESPONSABILIDAD"));
            documento.add(new Paragraph("\n"));
            float [] columnasFirma = {300F, 300F};
            Table tablaFirma = new Table(columnasFirma);
            float [] columnasFirmaGerente = {600F};
            Table tablaFirmaGerente = new Table(columnasFirmaGerente);
            tablaFirmaGerente.addCell(getCellFirmaGerente("_____________________________"));
            tablaFirmaGerente.addCell(getCellFirmaGerente(reporteKardex.getNombreRepresentanteLegal()));
            tablaFirmaGerente.addCell(getCellFirmaGerente(reporteKardex.getCargoRepresentanteLegal()));
            tablaFirmaGerente.addCell(getCellFirmaGerente(reporteKardex.getEmpresaRepresentanteLegal()));
            float [] columnasFirmaUsuario = {600F};
            Table tablaFirmaUsuario = new Table(columnasFirmaUsuario);
            tablaFirmaUsuario.addCell(getCellFirmaUsuario("_____________________________"));
            tablaFirmaUsuario.addCell(getCellFirmaUsuario(reporteKardex.getNombreUsuario()));
            tablaFirmaUsuario.addCell(getCellFirmaUsuario(reporteKardex.getCargoUsuario()));
            tablaFirmaUsuario.addCell(getCellFirmaUsuario(reporteKardex.getEmpresaUsuario()));

            tablaFirma.addCell(getCellFirma(tablaFirmaGerente));
            tablaFirma.addCell(getCellFirma(tablaFirmaUsuario));

            documento.add(tablaFirma);
            // 5. Close document
            documento.close();
            return new ByteArrayInputStream(salida.toByteArray());
        } catch (Exception e) {
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

    private Cell getCellProducto(String text, TextAlignment alignment) {
        Cell cell = new Cell().add(new Paragraph(text));
        cell.setTextAlignment(alignment);
        cell.setBorder(Border.NO_BORDER);
        cell.setFontSize(Constantes.fontSize10);
        cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK,1));
        cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
        return cell;
    }

    private Cell getCellValoracion(String text, TextAlignment alignment) {
        Cell cell = new Cell().add(new Paragraph(text));
        cell.setTextAlignment(alignment);
        cell.setBorder(Border.NO_BORDER);
        cell.setFontSize(Constantes.fontSize10);
        cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK,1));
        cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
        return cell;
    }

    private Cell getCellColumnaKardex(String text) {
        Paragraph parrafo = new Paragraph(text);
        Cell cell = new Cell();
        cell.add(parrafo);
        cell.setFontSize(Constantes.fontSize10);
        cell.setBorder(new SolidBorder(ColorConstants.BLACK, 1));
        return cell;
    }

    private Cell getCellColumnaIngreso(String text) {
        Paragraph parrafo = new Paragraph(text);
        Cell cell = new Cell();
        cell.add(parrafo);
        cell.setFontSize(Constantes.fontSize10);
        cell.setBorder(new SolidBorder(ColorConstants.BLACK, 1));
        return cell;
    }

    private Cell getCellColumnaSalida(String text) {
        Paragraph parrafo = new Paragraph(text);
        Cell cell = new Cell();
        cell.add(parrafo);
        cell.setFontSize(Constantes.fontSize10);
        cell.setBorder(new SolidBorder(ColorConstants.BLACK, 1));
        return cell;
    }

    private Cell getCellColumnaExistencia(String text) {
        Paragraph parrafo = new Paragraph(text);
        Cell cell = new Cell();
        cell.add(parrafo);
        cell.setFontSize(Constantes.fontSize10);
        cell.setBorder(new SolidBorder(ColorConstants.BLACK, 1));
        return cell;
    }

    private Cell getCellFilaKardex(String text) {
        Paragraph parrafo = new Paragraph(text);
        Cell cell = new Cell();
        cell.add(parrafo);
        cell.setFontSize(Constantes.fontSize10);
        cell.setBorder(new SolidBorder(ColorConstants.BLACK,1));
        return cell;
    }

    private Cell getCellFilaIngreso(String text) {
        Paragraph parrafo = new Paragraph(text);
        Cell cell = new Cell();
        cell.add(parrafo);
        cell.setFontSize(Constantes.fontSize10);
        cell.setBorder(new SolidBorder(ColorConstants.BLACK,1));
        return cell;
    }
    private Cell getCellFilaSalida(String text) {
        Paragraph parrafo = new Paragraph(text);
        Cell cell = new Cell();
        cell.add(parrafo);
        cell.setFontSize(Constantes.fontSize10);
        cell.setBorder(new SolidBorder(ColorConstants.BLACK,1));
        return cell;
    }
    private Cell getCellFilaExistencia(String text) {
        Paragraph parrafo = new Paragraph(text);
        Cell cell = new Cell();
        cell.add(parrafo);
        cell.setFontSize(Constantes.fontSize10);
        cell.setBorder(new SolidBorder(ColorConstants.BLACK,1));
        return cell;
    }
    private Cell getCellIngreso(Table tabla){
        Cell cell = new Cell();
        cell.add(tabla);
        cell.setBorder(Border.NO_BORDER);
        return cell;
    }
    private Cell getCellSalida(Table tabla){
        Cell cell = new Cell();
        cell.add(tabla);
        cell.setBorder(Border.NO_BORDER);
        return cell;
    }
    private Cell getCellExistencia(Table tabla){
        Cell cell = new Cell();
        cell.add(tabla);
        cell.setBorder(Border.NO_BORDER);
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


}
