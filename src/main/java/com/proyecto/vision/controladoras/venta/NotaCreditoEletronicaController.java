package com.proyecto.vision.controladoras.venta;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.venta.NotaCreditoVenta;
import com.proyecto.vision.servicios.interf.venta.INotaCreditoElectronicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathNotaCreditoElectronica;

@RestController
@RequestMapping(contexto+pathNotaCreditoElectronica)
public class NotaCreditoEletronicaController {
    @Autowired
    private INotaCreditoElectronicaService servicio;

    @GetMapping(value = "/{notaCreditoVentaId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> enviar(@PathVariable("notaCreditoVentaId") long notaCreditoVentaId) {
        NotaCreditoVenta notaCreditoVenta = servicio.enviar(notaCreditoVentaId);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_crear_nota_credito_electronica_exitosa + Constantes.espacio + notaCreditoVenta.getClaveAcceso(), notaCreditoVenta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/obtenerPDF/{notaCreditoVentaId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerPDF(@PathVariable("notaCreditoVentaId") long notaCreditoVentaId) {
        ByteArrayInputStream pdf = servicio.obtenerPDF(notaCreditoVentaId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=notaCreditoVenta.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }
    @GetMapping(value = "/enviarPDFYXML/{notaCreditoVentaId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> enviarPDFYXML(@PathVariable("notaCreditoVentaId") long notaCreditoVentaId) {
        servicio.enviarPDFYXML(notaCreditoVentaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_exitoso, null);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

}
