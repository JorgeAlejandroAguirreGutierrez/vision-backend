package com.proyecto.sicecuador.controladoras.comprobante;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathFacturaElectronica;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.modelos.comprobante.NotaDebitoVenta;
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

    @GetMapping(value = "/{facturaId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> enviar(@PathVariable("facturaId") long facturaId) {
        Factura factura = servicio.enviar(facturaId);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_crear_factura_electronica_exitosa + Constantes.espacio + factura.getClaveAcceso(), factura);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
