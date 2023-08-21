package com.proyecto.vision.controladoras.entrega;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.entrega.GuiaRemision;
import com.proyecto.vision.servicios.interf.venta.IGuiaRemisionElectronicaService;
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
import java.net.MalformedURLException;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathGuiaRemisionElectronica;

@RestController
@RequestMapping(contexto+pathGuiaRemisionElectronica)
public class GuiaRemisionEletronicaController {
    @Autowired
    private IGuiaRemisionElectronicaService servicio;

    @GetMapping(value = "/{guiaRemisionId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> enviar(@PathVariable("guiaRemisionId") long guiaRemisionId) throws MalformedURLException {
        GuiaRemision guiaRemision = servicio.enviar(guiaRemisionId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_crear_guia_remision_electronica_exitosa + Constantes.espacio + guiaRemision.getClaveAcceso(), guiaRemision);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/obtenerPDF/{guiaRemisionId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerPDF(@PathVariable("guiaRemisionId") long guiaRemisionId) {
        ByteArrayInputStream pdf = servicio.obtenerPDF(guiaRemisionId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=guiaRemision.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }
    @GetMapping(value = "/enviarPDFYXML/{guiaRemisionId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> enviarPDFYXML(@PathVariable("guiaRemisionId") long guiaRemisionId) {
        servicio.enviarPDFYXML(guiaRemisionId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_exitoso, null);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
