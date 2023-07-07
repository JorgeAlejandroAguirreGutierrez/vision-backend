package com.proyecto.vision.controladoras.entrega;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.entrega.GuiaRemision;
import com.proyecto.vision.servicios.interf.venta.IGuiaRemisionElectronicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathGuiaRemisionElectronica;

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
