package com.proyecto.vision.controladoras.venta;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.venta.NotaDebito;
import com.proyecto.vision.servicios.interf.venta.INotaDebitoElectronicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathNotaDebitoElectronica;

@RestController
@RequestMapping(contexto+pathNotaDebitoElectronica)
public class NotaDebitoEletronicaController {
    @Autowired
    private INotaDebitoElectronicaService servicio;

    @GetMapping(value = "/{notaDebitoVentaId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> enviar(@PathVariable("notaDebitoVentaId") long notaDebitoVentaId) throws MalformedURLException {
        NotaDebito notaDebito = servicio.enviar(notaDebitoVentaId);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_crear_nota_debito_electronica_exitosa + Constantes.espacio + notaDebito.getClaveAcceso(), notaDebito);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/obtenerPDF/{notaDebitoVentaId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerPDF(@PathVariable("notaDebitoVentaId") long notaDebitoVentaId) {
        ByteArrayInputStream pdf = servicio.obtenerPDF(notaDebitoVentaId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=notaDebitoVenta.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }
    @GetMapping(value = "/enviarPDFYXML/{notaDebitoVentaId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> enviarPDFYXML(@PathVariable("notaDebitoVentaId") long notaDebitoVentaId) {
        servicio.enviarPDFYXML(notaDebitoVentaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_exitoso, null);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
