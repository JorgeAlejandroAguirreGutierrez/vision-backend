package com.proyecto.sicecuador.controladoras.comprobante;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathFacturaElectronica;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.servicios.interf.comprobante.IFacturaElectronicaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(contexto+pathFacturaElectronica)
public class FacturaEletronicaController {
	
    @Autowired
    private IFacturaElectronicaService servicio;


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid Factura _factura) {
        Optional<String> facturaElectronicaRespuesta=servicio.crear(_factura);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_crear_exitoso, facturaElectronicaRespuesta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
