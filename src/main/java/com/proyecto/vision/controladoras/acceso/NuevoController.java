package com.proyecto.vision.controladoras.acceso;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.controladoras.GenericoController;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.acceso.Nuevo;
import com.proyecto.vision.servicios.interf.acceso.INuevoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathNuevo;

@RestController
@RequestMapping(contexto + pathNuevo)
public class NuevoController implements GenericoController<Nuevo> {
    @Autowired
    private INuevoService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<Nuevo> nuevos = servicio.consultar();
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, nuevos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Nuevo nuevo = servicio.obtener(id);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_obtener_exitoso, nuevo);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody Nuevo _nuevo) {
        Nuevo nuevo = servicio.crear(_nuevo);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_crear_exitoso, nuevo);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Nuevo _nuevo) {
        Nuevo nuevo = servicio.actualizar(_nuevo);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_actualizar_exitoso, nuevo);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(value = "/ejecutar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> ejecutar(@RequestBody Nuevo _nuevo) {
        Nuevo nuevo = servicio.ejecutar(_nuevo);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_actualizar_exitoso, nuevo);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
