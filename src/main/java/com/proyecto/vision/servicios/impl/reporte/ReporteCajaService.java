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
import com.proyecto.vision.modelos.recaudacion.*;
import com.proyecto.vision.modelos.reporte.*;
import com.proyecto.vision.modelos.usuario.Usuario;
import com.proyecto.vision.modelos.venta.Factura;
import com.proyecto.vision.repositorios.usuario.IUsuarioRepository;
import com.proyecto.vision.repositorios.venta.IFacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReporteCajaService {
    @Autowired
    private IFacturaRepository facturaRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    public ReporteCaja obtener(String apodo, String fechaInicio, String fechaFinal, long empresaId,
                                double billete100, double billete50, double billete20, double billete10, double billete5, double billete2, double billete1,
                                double moneda1, double moneda050, double moneda025, double moneda010, double moneda005, double moneda001) throws ParseException {
        Date fechaInicioC = new SimpleDateFormat(Constantes.fechaCorta).parse(fechaInicio);
        Date fechaFinalC = new SimpleDateFormat(Constantes.fechaCorta).parse(fechaFinal);
        List<Factura> facturas = facturaRepository.consultarPorFechaInicioYFechaFinal(fechaInicioC, fechaFinalC, empresaId);
        Optional<Usuario> usuario = usuarioRepository.obtenerPorApodoYEstado(apodo, Constantes.estadoActivo);
        if (facturas.isEmpty()) {
            throw new EntidadNoExistenteException(Constantes.factura);
        }
        if (usuario.isEmpty()) {
            throw new EntidadNoExistenteException(Constantes.usuario);
        }
        ReporteCaja reporteCaja = new ReporteCaja();
        reporteCaja.setRazonSocial(usuario.get().getEstacion().getEstablecimiento().getEmpresa().getRazonSocial());
        reporteCaja.setNombreComercial(usuario.get().getEstacion().getEstablecimiento().getEmpresa().getNombreComercial());
        reporteCaja.setNombreReporte(Constantes.nombreReporteCaja);
        reporteCaja.setFechaInicio(fechaInicio);
        reporteCaja.setFechaFinal(fechaFinal);
        //DATOS GENERALES
        DateFormat formatoFechaYHora = new SimpleDateFormat(Constantes.fechaYHora);
        reporteCaja.setFecha(formatoFechaYHora.format(new Date()));
        reporteCaja.setPeriodoReporte(fechaInicio + Constantes.espacio + "A" + Constantes.espacio + fechaFinal);
        reporteCaja.setUsuario(usuario.get().getApodo());
        reporteCaja.setPerfil(usuario.get().getPerfil().getDescripcion());

        double total0 = Constantes.cero;
        double total12 = Constantes.cero;
        double totalIva = Constantes.cero;
        long facturasEmitidas = Constantes.ceroId;
        long facturasAnuladas = Constantes.ceroId;
        double totalEfectivo = Constantes.cero;
        double totalCheque = Constantes.cero;
        double totalTarjetaCredito = Constantes.cero;
        double totalTarjetaDebito = Constantes.cero;
        double totalDeposito = Constantes.cero;
        double totalTransferencia = Constantes.cero;
        double totalCredito = Constantes.cero;
        for (Factura factura : facturas) {
            if (factura.getEstadoInterno().equals(Constantes.estadoInternoAnulada)) {
                facturasAnuladas++;
            } else {
                facturasEmitidas++;
            }
            total12 = total12 + factura.getSubtotalGravadoConDescuento();
            total0 = total0 + factura.getSubtotalNoGravadoConDescuento();
            totalIva = totalIva + factura.getImporteIvaTotal();

            totalEfectivo = totalEfectivo + factura.getEfectivo();
            for (Cheque cheque : factura.getCheques()) {
                totalCheque = totalCheque + cheque.getValor();
            }
            for (TarjetaCredito tarjetaCredito : factura.getTarjetasCreditos()) {
                totalTarjetaCredito = totalTarjetaCredito + tarjetaCredito.getValor();
            }
            for (TarjetaDebito tarjetaDebito : factura.getTarjetasDebitos()) {
                totalTarjetaDebito = totalTarjetaDebito + tarjetaDebito.getValor();
            }
            for (Deposito deposito : factura.getDepositos()) {
                totalDeposito = totalDeposito + deposito.getValor();
            }
            for (Transferencia transferencia : factura.getTransferencias()) {
                totalTransferencia = totalTransferencia + transferencia.getValor();
            }
            if (factura.getCredito() != null) {
                totalCredito = totalCredito + factura.getCredito().getSaldo();
            }
        }
        //COMPROBANTES EMITIDOS
        reporteCaja.setFacturasEmitidas(String.valueOf(facturasEmitidas));
        reporteCaja.setFacturasAnuladas(String.valueOf(facturasAnuladas));
        reporteCaja.setFacturasTotales(String.valueOf(facturasEmitidas + facturasAnuladas));

        //RESUMEN DE VENTAS
        reporteCaja.setTotal0(String.format("%.2f", total0));
        reporteCaja.setTotal12(String.format("%.2f", total12));
        reporteCaja.setTotalIva(String.format("%.2f", totalIva));
        reporteCaja.setTotal(String.format("%.2f", total0 + total12 + totalIva));

        //CIERRE DE CAJA VENTAS AL CONTADO
        reporteCaja.setEfectivo(String.format("%.2f", totalEfectivo));
        reporteCaja.setCheque(String.format("%.2f", totalCheque));
        reporteCaja.setTarjetaCredito(String.format("%.2f", totalTarjetaCredito));
        reporteCaja.setTarjetaDebito(String.format("%.2f", totalTarjetaDebito));
        reporteCaja.setDeposito(String.format("%.2f", totalDeposito));
        reporteCaja.setTransferencia(String.format("%.2f", totalTransferencia));
        reporteCaja.setCredito(String.format("%.2f", totalCredito));

        double totalRecaudacion = totalEfectivo + totalCheque + totalTarjetaCredito + totalTarjetaDebito + totalDeposito + totalTransferencia + totalCredito;
        reporteCaja.setTotalRecaudacion(String.format("%.2f", totalRecaudacion));

        reporteCaja.setCantidadBillete100(String.format("%.2f", billete100));
        reporteCaja.setDenominacionBillete100(String.format("%.2f", Constantes.billete100));
        double totalBillete100 = billete100 * Constantes.billete100;
        reporteCaja.setTotalBillete100(String.format("%.2f", totalBillete100));
        reporteCaja.setCantidadBillete50(String.format("%.2f", billete50));
        reporteCaja.setDenominacionBillete50(String.format("%.2f", Constantes.billete50));
        double totalBillete50 = billete50 * Constantes.billete50;
        reporteCaja.setTotalBillete50(String.format("%.2f", totalBillete50));
        reporteCaja.setCantidadBillete20(String.format("%.2f", billete20));
        reporteCaja.setDenominacionBillete20(String.format("%.2f", Constantes.billete20));
        double totalBillete20 = billete20 * Constantes.billete20;
        reporteCaja.setTotalBillete20(String.format("%.2f", totalBillete20));
        reporteCaja.setCantidadBillete10(String.format("%.2f", billete10));
        reporteCaja.setDenominacionBillete10(String.format("%.2f", Constantes.billete10));
        double totalBillete10 = billete10 * Constantes.billete10;
        reporteCaja.setTotalBillete10(String.format("%.2f", totalBillete10));
        reporteCaja.setCantidadBillete5(String.format("%.2f", billete5));
        reporteCaja.setDenominacionBillete5(String.format("%.2f", Constantes.billete5));
        double totalBillete5 = billete5 * Constantes.billete5;
        reporteCaja.setTotalBillete5(String.format("%.2f", totalBillete5));
        reporteCaja.setCantidadBillete2(String.format("%.2f", billete2));
        reporteCaja.setDenominacionBillete2(String.format("%.2f", Constantes.billete2));
        double totalBillete2 = billete2 * Constantes.billete2;
        reporteCaja.setTotalBillete2(String.format("%.2f", totalBillete2));
        reporteCaja.setCantidadBillete1(String.format("%.2f", billete1));
        reporteCaja.setDenominacionBillete1(String.format("%.2f", Constantes.billete1));
        double totalBillete1 = billete1 * Constantes.billete1;
        reporteCaja.setTotalBillete1(String.format("%.2f", totalBillete1));

        double totalBilletes = totalBillete100 + totalBillete50 + totalBillete20 + totalBillete10 + totalBillete5 + totalBillete2 + totalBillete1;
        reporteCaja.setTotalBilletes(String.format("%.2f", totalBilletes));

        reporteCaja.setCantidadMoneda1(String.format("%.2f", moneda1));
        reporteCaja.setDenominacionMoneda1(String.format("%.2f", Constantes.moneda1));
        double totalMoneda1 = moneda1 * Constantes.moneda1;
        reporteCaja.setTotalMoneda1(String.format("%.2f", totalMoneda1));
        reporteCaja.setCantidadMoneda050(String.format("%.2f", moneda050));
        reporteCaja.setDenominacionMoneda050(String.format("%.2f", Constantes.moneda050));
        double totalMoneda050 = moneda050 * Constantes.moneda050;
        reporteCaja.setTotalMoneda050(String.format("%.2f", totalMoneda050));
        reporteCaja.setCantidadMoneda025(String.format("%.2f", moneda025));
        reporteCaja.setDenominacionMoneda025(String.format("%.2f", Constantes.moneda025));
        double totalMoneda025 = moneda025 * Constantes.moneda025;
        reporteCaja.setTotalMoneda025(String.format("%.2f", totalMoneda025));
        reporteCaja.setCantidadMoneda010(String.format("%.2f", moneda010));
        reporteCaja.setDenominacionMoneda010(String.format("%.2f", Constantes.moneda010));
        double totalMoneda010 = moneda010 * Constantes.moneda010;
        reporteCaja.setTotalMoneda010(String.format("%.2f", totalMoneda010));
        reporteCaja.setCantidadMoneda005(String.format("%.2f", moneda005));
        reporteCaja.setDenominacionMoneda005(String.format("%.2f", Constantes.moneda005));
        double totalMoneda005 = moneda005 * Constantes.moneda005;
        reporteCaja.setTotalMoneda005(String.format("%.2f", totalMoneda005));
        reporteCaja.setCantidadMoneda001(String.format("%.2f", moneda001));
        reporteCaja.setDenominacionMoneda001(String.format("%.2f", Constantes.moneda001));
        double totalMoneda001 = moneda001 * Constantes.moneda001;
        reporteCaja.setTotalMoneda001(String.format("%.2f", totalMoneda001));

        double totalMonedas = totalMoneda1 + totalMoneda050 + totalMoneda025 + totalMoneda010 + totalMoneda005 + totalMoneda001;
        reporteCaja.setTotalMonedas(String.format("%.2f", totalMonedas));

        double totalCaja = totalBilletes + totalMonedas;
        reporteCaja.setTotalCaja(String.format("%.2f", totalCaja));

        double diferencia = totalEfectivo - totalCaja;
        if(diferencia > 0){
            reporteCaja.setFaltante(String.format("%.2f", diferencia));
            reporteCaja.setSobrante(String.format("%.2f", Constantes.cero));
        } else {
            reporteCaja.setFaltante(String.format("%.2f", Constantes.cero));
            reporteCaja.setSobrante(String.format("%.2f", diferencia * -1));
        }

        //FIRMAS DE RESPONSABILIDAD
        reporteCaja.setNombreRepresentanteLegal(usuario.get().getEstacion().getEstablecimiento().getEmpresa().getRepresentanteLegal());
        reporteCaja.setCargoRepresentanteLegal(usuario.get().getEstacion().getEstablecimiento().getEmpresa().getCargoRepresentanteLegal());
        reporteCaja.setEmpresaRepresentanteLegal(usuario.get().getEstacion().getEstablecimiento().getEmpresa().getNombreComercial());
        reporteCaja.setNombreUsuario(usuario.get().getNombre());
        reporteCaja.setCargoUsuario(usuario.get().getPerfil().getDescripcion());
        reporteCaja.setEmpresaUsuario(usuario.get().getEstacion().getEstablecimiento().getEmpresa().getNombreComercial());
        return reporteCaja;
    }

    public ByteArrayInputStream pdf(String apodo, String fechaInicio, String fechaFinal, long empresaId,
                                    double billete100, double billete50, double billete20, double billete10, double billete5, double billete2, double billete1,
                                    double moneda1, double moneda050, double moneda025, double moneda010, double moneda005, double moneda001) throws ParseException {
        ReporteCaja reporteCaja = obtener(apodo, fechaInicio, fechaFinal, empresaId,
                billete100, billete50, billete20, billete10, billete5, billete2, billete1,
                moneda1, moneda050, moneda025, moneda010, moneda005, moneda001);
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
            float[] columnasEmpresa = {600F};
            Table tablaEmpresa = new Table(columnasEmpresa);
            tablaEmpresa.addCell(getCellEmpresa(reporteCaja.getRazonSocial() + "\n" +
                    reporteCaja.getNombreComercial() + "\n" +
                    reporteCaja.getNombreReporte() + "\n" +
                    "VENTAS DESDE: " + reporteCaja.getFechaInicio() + " HASTA " + reporteCaja.getFechaFinal() + "\n", TextAlignment.CENTER));
            documento.add(tablaEmpresa);
            documento.add(new Paragraph("\n"));
            float[] columnas = {200F, 200F, 200F};
            Table tabla = new Table(columnas);
            float[] columnasDatoGeneral = {600F};
            Table tablaDatoGeneral = new Table(columnasDatoGeneral);
            tablaDatoGeneral.addCell(getCellSinBorde("DATOS GENERALES"));
            tablaDatoGeneral.addCell(getCellDatoGeneral("PERIODO DEL REPORTE: " + reporteCaja.getPeriodoReporte()));
            tablaDatoGeneral.addCell(getCellDatoGeneral("USUARIO: " + reporteCaja.getUsuario()));
            tablaDatoGeneral.addCell(getCellDatoGeneral("CARGO: " + reporteCaja.getPerfil()));

            float[] columnasComprobanteEmitido = {600F};
            Table tablaComprobanteEmitido = new Table(columnasComprobanteEmitido);
            tablaComprobanteEmitido.addCell(getCellSinBorde("COMPROBANTES EMITIDOS"));
            tablaComprobanteEmitido.addCell(getCellComprobanteEmitido("FACTURAS EMITIDAS: " + reporteCaja.getFacturasEmitidas()));
            tablaComprobanteEmitido.addCell(getCellComprobanteEmitido("FACTURAS ANULADAS: " + reporteCaja.getFacturasAnuladas()));
            tablaComprobanteEmitido.addCell(getCellComprobanteEmitido("TOTAL: " + reporteCaja.getFacturasTotales()));

            float[] columnasResumenVenta = {600F};
            Table tablaResumenVenta = new Table(columnasResumenVenta);
            tablaResumenVenta.addCell(getCellSinBorde("RESUMEN DE VENTAS"));
            tablaResumenVenta.addCell(getCellResumenVenta("VENTAS GRAVADAS CON 12%: " + reporteCaja.getTotal12()));
            tablaResumenVenta.addCell(getCellResumenVenta("VENTAS GRAVADAS CON 0%: " + reporteCaja.getTotal0()));
            tablaResumenVenta.addCell(getCellResumenVenta("IVA TARIAFA 12%: " + reporteCaja.getTotalIva()));
            tablaResumenVenta.addCell(getCellResumenVenta("TOTAL: " + reporteCaja.getTotal()));

            tabla.addCell(getCell(tablaDatoGeneral));
            tabla.addCell(getCell(tablaComprobanteEmitido));
            tabla.addCell(getCell(tablaResumenVenta));
            documento.add(tabla);

            documento.add(new Paragraph("\n"));
            documento.add(new Paragraph("CIERRE DE VENTAS AL CONTADO"));
            float[] columnasCajaContado = {75F, 75F, 75F, 75F, 75F, 75F, 75F, 75F};
            Table tablaCajaContado = new Table(columnasCajaContado);
            tablaCajaContado.addCell(getCellCajaContado("EFECTIVO"));
            tablaCajaContado.addCell(getCellCajaContado("CHEQUE"));
            tablaCajaContado.addCell(getCellCajaContado("T. CREDITO"));
            tablaCajaContado.addCell(getCellCajaContado("T. DEBITO"));
            tablaCajaContado.addCell(getCellCajaContado("TRANSFERENCIAS"));
            tablaCajaContado.addCell(getCellCajaContado("DEPOSITOS"));
            tablaCajaContado.addCell(getCellCajaContado("CREDITOS"));
            tablaCajaContado.addCell(getCellCajaContado("RETENCION"));

            tablaCajaContado.addCell(getCellCajaContado(Constantes.signoDolar + reporteCaja.getEfectivo()));
            tablaCajaContado.addCell(getCellCajaContado(Constantes.signoDolar + reporteCaja.getCheque()));
            tablaCajaContado.addCell(getCellCajaContado(Constantes.signoDolar + reporteCaja.getTarjetaCredito()));
            tablaCajaContado.addCell(getCellCajaContado(Constantes.signoDolar + reporteCaja.getTarjetaDebito()));
            tablaCajaContado.addCell(getCellCajaContado(Constantes.signoDolar + reporteCaja.getTransferencia()));
            tablaCajaContado.addCell(getCellCajaContado(Constantes.signoDolar + reporteCaja.getTransferencia()));
            tablaCajaContado.addCell(getCellCajaContado(Constantes.signoDolar + reporteCaja.getCredito()));
            tablaCajaContado.addCell(getCellCajaContado(Constantes.vacio));

            documento.add(tablaCajaContado);

            documento.add(new Paragraph("\n"));

            float[] columnasCajaDiaria = {300F, 300F};
            Table tablaCajaDiaria = new Table(columnasCajaDiaria);
            tablaCajaDiaria.addCell(getCellCajaDiaria("TOTAL CAJA DIARIA"));
            tablaCajaDiaria.addCell(getCellCajaDiaria(Constantes.signoDolar + reporteCaja.getTotalRecaudacion()));
            tablaCajaDiaria.addCell(getCellCajaDiaria("TOTAL EFECTIVO"));
            tablaCajaDiaria.addCell(getCellCajaDiaria(Constantes.signoDolar + reporteCaja.getEfectivo()));
            tablaCajaDiaria.addCell(getCellCajaDiaria("TOTAL CHEQUES"));
            tablaCajaDiaria.addCell(getCellCajaDiaria(Constantes.signoDolar + reporteCaja.getCheque()));
            tablaCajaDiaria.addCell(getCellCajaDiaria("TOTAL TARJETAS DE CREDITO"));
            tablaCajaDiaria.addCell(getCellCajaDiaria(Constantes.signoDolar + reporteCaja.getTarjetaCredito()));
            tablaCajaDiaria.addCell(getCellCajaDiaria("TOTAL TARJETAS DE DEBITO"));
            tablaCajaDiaria.addCell(getCellCajaDiaria(Constantes.signoDolar + reporteCaja.getTarjetaDebito()));
            tablaCajaDiaria.addCell(getCellCajaDiaria("TOTAL TRANSFERENCIAS"));
            tablaCajaDiaria.addCell(getCellCajaDiaria(Constantes.signoDolar + reporteCaja.getTransferencia()));
            tablaCajaDiaria.addCell(getCellCajaDiaria("TOTAL DEPOSITOS"));
            tablaCajaDiaria.addCell(getCellCajaDiaria(Constantes.signoDolar + reporteCaja.getDeposito()));
            tablaCajaDiaria.addCell(getCellCajaDiaria("TOTAL CREDITO"));
            tablaCajaDiaria.addCell(getCellCajaDiaria(Constantes.signoDolar + reporteCaja.getCredito()));

            documento.add(tablaCajaDiaria);

            documento.add(new Paragraph("\n"));
            documento.add(new Paragraph("DINERO RECAUDADO"));

            documento.add(new Paragraph("\n"));
            documento.add(new Paragraph("BILLETES"));
            float[] columnasBillete = {200F, 200F, 200F};
            Table tablaBillete = new Table(columnasBillete);
            tablaBillete.addCell(getCellBillete("CANTIDAD"));
            tablaBillete.addCell(getCellBillete("DENOMINACION"));
            tablaBillete.addCell(getCellBillete("TOTAL"));
            tablaBillete.addCell(getCellBillete(reporteCaja.getCantidadBillete100()));
            tablaBillete.addCell(getCellBillete(Constantes.signoDolar + reporteCaja.getDenominacionBillete100()));
            tablaBillete.addCell(getCellBillete(Constantes.signoDolar + reporteCaja.getTotalBillete100()));
            tablaBillete.addCell(getCellBillete(reporteCaja.getCantidadBillete50()));
            tablaBillete.addCell(getCellBillete(Constantes.signoDolar + reporteCaja.getDenominacionBillete50()));
            tablaBillete.addCell(getCellBillete(Constantes.signoDolar + reporteCaja.getTotalBillete50()));
            tablaBillete.addCell(getCellBillete(reporteCaja.getCantidadBillete20()));
            tablaBillete.addCell(getCellBillete(Constantes.signoDolar + reporteCaja.getDenominacionBillete20()));
            tablaBillete.addCell(getCellBillete(Constantes.signoDolar + reporteCaja.getTotalBillete20()));
            tablaBillete.addCell(getCellBillete(reporteCaja.getCantidadBillete10()));
            tablaBillete.addCell(getCellBillete(Constantes.signoDolar + reporteCaja.getDenominacionBillete10()));
            tablaBillete.addCell(getCellBillete(Constantes.signoDolar + reporteCaja.getTotalBillete10()));
            tablaBillete.addCell(getCellBillete(reporteCaja.getCantidadBillete5()));
            tablaBillete.addCell(getCellBillete(Constantes.signoDolar + reporteCaja.getDenominacionBillete5()));
            tablaBillete.addCell(getCellBillete(Constantes.signoDolar + reporteCaja.getTotalBillete5()));
            tablaBillete.addCell(getCellBillete(reporteCaja.getCantidadBillete2()));
            tablaBillete.addCell(getCellBillete(Constantes.signoDolar + reporteCaja.getDenominacionBillete2()));
            tablaBillete.addCell(getCellBillete(Constantes.signoDolar + reporteCaja.getTotalBillete2()));
            tablaBillete.addCell(getCellBillete(reporteCaja.getCantidadBillete1()));
            tablaBillete.addCell(getCellBillete(Constantes.signoDolar + reporteCaja.getDenominacionBillete1()));
            tablaBillete.addCell(getCellBillete(Constantes.signoDolar + reporteCaja.getTotalBillete1()));
            tablaBillete.addCell(getCellSinBorde(Constantes.vacio));
            tablaBillete.addCell(getCellBillete("TOTAL BILLETES"));
            tablaBillete.addCell(getCellBillete(Constantes.signoDolar + reporteCaja.getTotalBilletes()));
            documento.add(tablaBillete);


            documento.add(new Paragraph("\n"));
            documento.add(new Paragraph("MONEDAS"));
            float[] columnasMoneda = {200F, 200F, 200F};
            Table tablaMoneda = new Table(columnasMoneda);
            tablaMoneda.addCell(getCellMoneda("CANTIDAD"));
            tablaMoneda.addCell(getCellMoneda("DENOMINACION"));
            tablaMoneda.addCell(getCellMoneda("TOTAL"));
            tablaMoneda.addCell(getCellMoneda(reporteCaja.getCantidadMoneda1()));
            tablaMoneda.addCell(getCellMoneda(Constantes.signoDolar + reporteCaja.getDenominacionMoneda1()));
            tablaMoneda.addCell(getCellMoneda(Constantes.signoDolar + reporteCaja.getTotalMoneda1()));
            tablaMoneda.addCell(getCellMoneda(reporteCaja.getCantidadMoneda050()));
            tablaMoneda.addCell(getCellMoneda(Constantes.signoDolar + reporteCaja.getDenominacionMoneda050()));
            tablaMoneda.addCell(getCellMoneda(Constantes.signoDolar + reporteCaja.getTotalMoneda050()));
            tablaMoneda.addCell(getCellMoneda(reporteCaja.getCantidadMoneda025()));
            tablaMoneda.addCell(getCellMoneda(Constantes.signoDolar + reporteCaja.getDenominacionMoneda025()));
            tablaMoneda.addCell(getCellMoneda(Constantes.signoDolar + reporteCaja.getTotalMoneda025()));
            tablaMoneda.addCell(getCellMoneda(reporteCaja.getCantidadMoneda010()));
            tablaMoneda.addCell(getCellMoneda(Constantes.signoDolar + reporteCaja.getDenominacionMoneda010()));
            tablaMoneda.addCell(getCellMoneda(Constantes.signoDolar + reporteCaja.getTotalMoneda010()));
            tablaMoneda.addCell(getCellMoneda(reporteCaja.getCantidadMoneda005()));
            tablaMoneda.addCell(getCellMoneda(Constantes.signoDolar + reporteCaja.getDenominacionMoneda005()));
            tablaMoneda.addCell(getCellMoneda(Constantes.signoDolar + reporteCaja.getTotalMoneda005()));
            tablaMoneda.addCell(getCellMoneda(reporteCaja.getCantidadMoneda001()));
            tablaMoneda.addCell(getCellMoneda(Constantes.signoDolar + reporteCaja.getDenominacionMoneda001()));
            tablaMoneda.addCell(getCellMoneda(Constantes.signoDolar + reporteCaja.getTotalMoneda001()));
            tablaMoneda.addCell(getCellSinBorde(Constantes.vacio));
            tablaMoneda.addCell(getCellMoneda("TOTAL MONEDAS"));
            tablaMoneda.addCell(getCellMoneda(Constantes.signoDolar + reporteCaja.getTotalMonedas()));
            documento.add(tablaMoneda);

            documento.add(new Paragraph("\n"));
            documento.add(new Paragraph("TOTAL DINERO RECAUDADO: " + Constantes.signoDolar + reporteCaja.getTotalCaja()));

            documento.add(new Paragraph("\n"));
            documento.add(new Paragraph("DIFERENCIAS"));
            float[] columnasDiferencia = {300F, 300F};
            Table tablaDiferencia = new Table(columnasDiferencia);
            tablaDiferencia.addCell(getCellDiferencia("FALTANTE DE CAJA"));
            tablaDiferencia.addCell(getCellDiferencia(Constantes.signoDolar + reporteCaja.getFaltante()));
            tablaDiferencia.addCell(getCellDiferencia("SOBRANTE DE CAJA"));
            tablaDiferencia.addCell(getCellDiferencia(Constantes.signoDolar + reporteCaja.getSobrante()));
            documento.add(tablaDiferencia);

            documento.add(new Paragraph("\n"));
            documento.add(new Paragraph("FIRMAS DE RESPONSABILIDAD"));
            documento.add(new Paragraph("\n"));
            float [] columnasFirma = {300F, 300F};
            Table tablaFirma = new Table(columnasFirma);
            float [] columnasFirmaGerente = {600F};
            Table tablaFirmaGerente = new Table(columnasFirmaGerente);
            tablaFirmaGerente.addCell(getCellFirmaGerente("_____________________________"));
            tablaFirmaGerente.addCell(getCellFirmaGerente(reporteCaja.getNombreRepresentanteLegal()));
            tablaFirmaGerente.addCell(getCellFirmaGerente(reporteCaja.getCargoRepresentanteLegal()));
            tablaFirmaGerente.addCell(getCellFirmaGerente(reporteCaja.getEmpresaRepresentanteLegal()));
            float [] columnasFirmaUsuario = {600F};
            Table tablaFirmaUsuario = new Table(columnasFirmaUsuario);
            tablaFirmaUsuario.addCell(getCellFirmaUsuario("_____________________________"));
            tablaFirmaUsuario.addCell(getCellFirmaUsuario(reporteCaja.getNombreUsuario()));
            tablaFirmaUsuario.addCell(getCellFirmaUsuario(reporteCaja.getCargoUsuario()));
            tablaFirmaUsuario.addCell(getCellFirmaUsuario(reporteCaja.getEmpresaUsuario()));

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

    private Cell getCell(Table tabla) {
        Cell cell = new Cell();
        cell.add(tabla);
        cell.setBorder(Border.NO_BORDER);
        cell.setFontSize(Constantes.fontSize10);
        return cell;
    }

    private Cell getCellDatoGeneral(String text) {
        Cell cell = new Cell().add(new Paragraph(text));
        cell.setFontSize(Constantes.fontSize10);
        cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK,1));
        cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
        return cell;
    }

    private Cell getCellComprobanteEmitido(String text) {
        Cell cell = new Cell().add(new Paragraph(text));
        cell.setFontSize(Constantes.fontSize10);
        cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK,1));
        cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
        return cell;
    }

    private Cell getCellResumenVenta(String text) {
        Cell cell = new Cell().add(new Paragraph(text));
        cell.setFontSize(Constantes.fontSize10);
        cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK,1));
        cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
        return cell;
    }

    private Cell getCellCajaContado(String text) {
        Cell cell = new Cell().add(new Paragraph(text));
        cell.setFontSize(Constantes.fontSize10);
        cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK,1));
        cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
        return cell;
    }

    private Cell getCellCajaDiaria(String text) {
        Cell cell = new Cell().add(new Paragraph(text));
        cell.setFontSize(Constantes.fontSize10);
        cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK,1));
        cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
        return cell;
    }

    private Cell getCellBillete(String text) {
        Cell cell = new Cell().add(new Paragraph(text));
        cell.setFontSize(Constantes.fontSize10);
        cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK,1));
        cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
        return cell;
    }

    private Cell getCellMoneda(String text) {
        Cell cell = new Cell().add(new Paragraph(text));
        cell.setFontSize(Constantes.fontSize10);
        cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK,1));
        cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
        return cell;
    }

    private Cell getCellDiferencia(String text) {
        Cell cell = new Cell().add(new Paragraph(text));
        cell.setFontSize(Constantes.fontSize10);
        cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK,1));
        cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 1));
        return cell;
    }

    private Cell getCellSinBorde(String text) {
        Paragraph parrafo = new Paragraph(text);
        Cell cell = new Cell();
        cell.add(parrafo);
        cell.setFontSize(Constantes.fontSize10);
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
