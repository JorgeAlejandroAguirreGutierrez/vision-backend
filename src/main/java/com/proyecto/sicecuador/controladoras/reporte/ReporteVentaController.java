package com.proyecto.sicecuador.controladoras.reporte;
import com.proyecto.sicecuador.servicios.impl.reporte.ReporteVentaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.Date;

import static com.proyecto.sicecuador.controladoras.Endpoints.*;

@RestController
@RequestMapping(contexto+pathReporteVenta)
public class ReporteVentaController {
    @Autowired
    private ReporteVentaService servicio;

    @GetMapping(value = "/pdf/{apodo}/{fechaInicio}/{fechaFin}/{empresaId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> pdf(@PathVariable("apodo") String apodo, @PathVariable("fechaInicio") String fechaInicio, @PathVariable("fechaFin") String fechaFin, @PathVariable("empresaId") long empresaId) {
        ByteArrayInputStream pdf = servicio.pdf(apodo, fechaInicio, fechaFin, empresaId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=ReporteVenta.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }
}
