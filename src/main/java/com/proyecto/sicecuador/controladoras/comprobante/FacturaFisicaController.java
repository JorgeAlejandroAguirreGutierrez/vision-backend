package com.proyecto.sicecuador.controladoras.comprobante;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathFacturaFisica;
import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.servicios.interf.comprobante.IFacturaFisicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;

@RestController
@RequestMapping(contexto+pathFacturaFisica)
public class FacturaFisicaController  {
    @Autowired
    private IFacturaFisicaService servicio;
    
    @PostMapping(produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> crearPDF(@RequestBody @Valid Factura _factura) {
        ByteArrayInputStream pdf = servicio.crearPDF(_factura);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=factura.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }
}
