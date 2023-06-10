package com.proyecto.sicecuador.controladoras.entrega;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathGuiaRemision;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.cajaBanco.Banco;
import com.proyecto.sicecuador.modelos.entrega.GuiaRemision;
import com.proyecto.sicecuador.servicios.interf.entrega.IGuiaRemisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping(contexto+pathGuiaRemision)
public class GuiaRemisionController implements GenericoController<GuiaRemision> {
    @Autowired
    private IGuiaRemisionService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<GuiaRemision> guiasRemisiones = servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, guiasRemisiones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<GuiaRemision> entregas = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, entregas);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        GuiaRemision guiaRemision =servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, guiaRemision);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid GuiaRemision _guiaRemision) {
        GuiaRemision guiaRemision = servicio.crear(_guiaRemision);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, guiaRemision);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody GuiaRemision _guiaRemision) {
        GuiaRemision guiaRemision = servicio.actualizar(_guiaRemision);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, guiaRemision);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody GuiaRemision _guiaRemision) {
        GuiaRemision guiaRemision = servicio.activar(_guiaRemision);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_activar_exitoso, guiaRemision);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody GuiaRemision _guiaRemision) {
        GuiaRemision guiaRemision = servicio.inactivar(_guiaRemision);
        Respuesta respuesta= new Respuesta(true, Constantes.mensaje_inactivar_exitoso, guiaRemision);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/factura/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerPorFactura(@PathVariable("id") long id) {
        Optional<GuiaRemision> entrega=servicio.obtenerPorFactura(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, entrega);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
