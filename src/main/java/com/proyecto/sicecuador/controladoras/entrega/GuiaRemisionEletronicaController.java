package com.proyecto.sicecuador.controladoras.entrega;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.entrega.GuiaRemision;
import com.proyecto.sicecuador.servicios.interf.venta.IGuiaRemisionElectronicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathGuiaRemisionElectronica;

@RestController
@RequestMapping(contexto+pathGuiaRemisionElectronica)
public class GuiaRemisionEletronicaController {
    @Autowired
    private IGuiaRemisionElectronicaService servicio;

    @GetMapping(value = "/{guiaRemisionId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> enviar(@PathVariable("guiaRemisionId") long guiaRemisionId) {
        GuiaRemision guiaRemision = servicio.enviar(guiaRemisionId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_crear_nota_debito_electronica_exitosa + Constantes.espacio + guiaRemision.getClaveAcceso(), guiaRemision);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
