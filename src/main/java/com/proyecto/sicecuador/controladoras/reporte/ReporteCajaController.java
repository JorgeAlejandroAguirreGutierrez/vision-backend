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

    @GetMapping(value = "/pdf/{apodo}/{fechaInicio}/{fechaFinal}/{empresaId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> pdf(@PathVariable("apodo") String apodo, @PathVariable("fechaInicio") String fechaInicio, @PathVariable("fechaFinal") String fechaFinal, @PathVariable("empresaId") long empresaId) throws ParseException {
        ByteArrayInputStream pdf = servicio.pdf(apodo, fechaInicio, fechaFinal, empresaId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=ReporteVenta.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }
}
