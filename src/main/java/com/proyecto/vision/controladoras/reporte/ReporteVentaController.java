package com.proyecto.vision.controladoras.reporte;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.reporte.ReporteVenta;
import com.proyecto.vision.servicios.impl.reporte.ReporteVentaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.text.ParseException;

import static com.proyecto.vision.controladoras.Endpoints.*;

@RestController
@RequestMapping(contexto+pathReporteVenta)
public class ReporteVentaController {
    @Autowired
    private ReporteVentaService servicio;

    @GetMapping(value = "/pdf/{apodo}/{fechaInicio}/{fechaFin}/{empresaId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> pdf(@PathVariable("apodo") String apodo, @PathVariable("fechaInicio") String fechaInicio, @PathVariable("fechaFin") String fechaFin, @PathVariable("empresaId") long empresaId) throws ParseException {
        ByteArrayInputStream pdf = servicio.pdf(apodo, fechaInicio, fechaFin, empresaId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=ReporteVenta.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }

    @GetMapping(value = "/excel/{apodo}/{fechaInicio}/{fechaFin}/{empresaId}")
    public ResponseEntity<?> excel(@PathVariable("apodo") String apodo, @PathVariable("fechaInicio") String fechaInicio, @PathVariable("fechaFin") String fechaFin, @PathVariable("empresaId") long empresaId) throws ParseException {
        ByteArrayInputStream pdf = servicio.excel(apodo, fechaInicio, fechaFin, empresaId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=reporteVenta.xlsx");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(pdf));
    }

    @GetMapping(value = "/obtener/{apodo}/{fechaInicio}/{fechaFin}/{empresaId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("apodo") String apodo, @PathVariable("fechaInicio") String fechaInicio, @PathVariable("fechaFin") String fechaFin, @PathVariable("empresaId") long empresaId) throws ParseException {
        ReporteVenta reporteVenta = servicio.obtener(apodo, fechaInicio, fechaFin, empresaId);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_obtener_exitoso, reporteVenta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
