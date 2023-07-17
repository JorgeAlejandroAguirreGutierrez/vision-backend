package com.proyecto.vision.controladoras.venta;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathFacturaElectronica;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.venta.Factura;
import com.proyecto.vision.servicios.interf.venta.IFacturaElectronicaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;

@RestController
@RequestMapping(contexto+pathFacturaElectronica)
public class FacturaEletronicaController {
    @Autowired
    private IFacturaElectronicaService servicio;

    @GetMapping(value = "/{facturaId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> enviar(@PathVariable("facturaId") long facturaId) throws MalformedURLException {
        Factura factura = servicio.enviar(facturaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_crear_factura_electronica_exitosa + Constantes.espacio + factura.getClaveAcceso(), factura);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/obtenerPDF/{facturaId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerPDF(@PathVariable("facturaId") long facturaId) {
        ByteArrayInputStream pdf = servicio.obtenerPDF(facturaId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=factura.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }
    @GetMapping(value = "/enviarPDFYXML/{facturaId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> enviarPDFYXML(@PathVariable("facturaId") long facturaId) {
        servicio.enviarPDFYXML(facturaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_exitoso, null);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/obtenerTicket/{facturaId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerTicket(@PathVariable("facturaId") long facturaId) {
        ByteArrayInputStream pdf = servicio.obtenerTicket(facturaId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=ticket.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }
}
