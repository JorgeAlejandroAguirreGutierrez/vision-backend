package com.proyecto.sicecuador.controladoras.reporte;

import com.proyecto.sicecuador.servicios.impl.reporte.ReporteCajaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.text.ParseException;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathReporteCaja;

@RestController
@RequestMapping(contexto+pathReporteCaja)
public class ReporteCajaController {
    @Autowired
    private ReporteCajaService servicio;

    @GetMapping(value = "/pdf/{apodo}/{fechaInicio}/{fechaFinal}/{empresaId}/" +
            "{billete100}/{billete50}/{billete20}/{billete10}/{billete5}/{billete2}/{billete1}/" +
            "{moneda1}/{moneda050}/{moneda025}/{moneda010}/{moneda005}/{moneda001}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> pdf(@PathVariable("apodo") String apodo, @PathVariable("fechaInicio") String fechaInicio, @PathVariable("fechaFinal") String fechaFinal, @PathVariable("empresaId") long empresaId,
        @PathVariable("billete100") double billete100, @PathVariable("billete50") double billete50, @PathVariable("billete20") double billete20, @PathVariable("billete10") double billete10, @PathVariable("billete5") double billete5, @PathVariable("billete2") double billete2, @PathVariable("billete1") double billete1,
        @PathVariable("moneda1") double moneda1, @PathVariable("moneda050") double moneda050, @PathVariable("moneda025") double moneda025, @PathVariable("moneda010") double moneda010, @PathVariable("moneda005") double moneda005, @PathVariable("moneda001") double moneda001) throws ParseException {
        ByteArrayInputStream pdf = servicio.pdf(apodo, fechaInicio, fechaFinal, empresaId,
                billete100, billete50, billete20, billete10, billete5, billete2, billete1,
                moneda1, moneda050, moneda025, moneda010, moneda005, moneda001);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=ReporteVenta.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }
}
