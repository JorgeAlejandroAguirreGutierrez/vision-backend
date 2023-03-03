package com.proyecto.sicecuador.controladoras.comprobante;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.comprobante.NotaCreditoVenta;
import com.proyecto.sicecuador.servicios.interf.comprobante.INotaCreditoElectronicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathNotaCreditoElectronica;

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
}
