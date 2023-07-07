package com.proyecto.sicecuador.controladoras.reporte;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.reporte.ReporteKardex;
import com.proyecto.sicecuador.servicios.impl.reporte.ReporteKardexService;
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

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathReporteKardex;

@RestController
@RequestMapping(contexto+pathReporteKardex)
public class ReporteKardexController {
    @Autowired
    private ReporteKardexService servicio;

    @GetMapping(value = "/obtener/{apodo}/{fechaInicio}/{fechaFinal}/{productoId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("apodo") String apodo, @PathVariable("fechaInicio") String fechaInicio, @PathVariable("fechaFinal") String fechaFinal, @PathVariable("productoId") long productoId) throws ParseException {
        ReporteKardex reporteKardex = servicio.obtener(apodo, fechaInicio, fechaFinal, productoId);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_obtener_exitoso, reporteKardex);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/pdf/{apodo}/{fechaInicio}/{fechaFinal}/{productoId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> pdf(@PathVariable("apodo") String apodo, @PathVariable("fechaInicio") String fechaInicio, @PathVariable("fechaFinal") String fechaFinal, @PathVariable("productoId") long productoId) throws ParseException {
        ByteArrayInputStream pdf = servicio.pdf(apodo, fechaInicio, fechaFinal, productoId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=ReporteVenta.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }
}
