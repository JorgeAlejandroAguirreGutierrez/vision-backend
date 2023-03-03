package com.proyecto.sicecuador.controladoras.comprobante;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.comprobante.NotaDebitoVenta;
import com.proyecto.sicecuador.servicios.interf.comprobante.INotaDebitoElectronicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathNotaDebitoElectronica;

@RestController
@RequestMapping(contexto+pathNotaDebitoElectronica)
public class NotaDebitoEletronicaController {
    @Autowired
    private INotaDebitoElectronicaService servicio;

    @GetMapping(value = "/{notaDebitoVentaId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> enviar(@PathVariable("notaDebitoVentaId") long notaDebitoVentaId) {
        NotaDebitoVenta notaDebitoVenta = servicio.enviar(notaDebitoVentaId);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_crear_nota_debito_electronica_exitosa + Constantes.espacio + notaDebitoVenta.getClaveAcceso(), notaDebitoVenta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
