package com.proyecto.vision.controladoras.reporte;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.reporte.ReporteExistencia;
import com.proyecto.vision.modelos.reporte.ReporteVenta;
import com.proyecto.vision.servicios.impl.reporte.ReporteExistenciaService;
import com.proyecto.vision.servicios.impl.reporte.ReporteVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.text.ParseException;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathReporteExistencia;

@RestController
@RequestMapping(contexto+pathReporteExistencia)
public class ReporteExistenciaController {
    @Autowired
    private ReporteExistenciaService servicio;

    @GetMapping(value = "/pdf/{apodo}/{fechaCorte}/{empresaId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> pdf(@PathVariable("apodo") String apodo, @PathVariable("fechaCorte") String fechaCorte, @PathVariable("empresaId") long empresaId) throws ParseException {
        ByteArrayInputStream pdf = servicio.pdf(apodo, fechaCorte, empresaId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=ReporteExistencia.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }

    @GetMapping(value = "/excel/{apodo}/{fechaCorte}/{empresaId}")
    public ResponseEntity<?> excel(@PathVariable("apodo") String apodo, @PathVariable("fechaCorte") String fechaCorte, @PathVariable("empresaId") long empresaId) throws ParseException {
        ByteArrayInputStream pdf = servicio.excel(apodo, fechaCorte, empresaId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=reporteExistencia.xlsx");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(pdf));
    }

    @GetMapping(value = "/obtener/{apodo}/{fechaCorte}/{empresaId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("apodo") String apodo, @PathVariable("fechaCorte") String fechaCorte, @PathVariable("empresaId") long empresaId) throws ParseException {
        ReporteExistencia reporteExistencia = servicio.obtener(apodo, fechaCorte, empresaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_obtener_exitoso, reporteExistencia);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
