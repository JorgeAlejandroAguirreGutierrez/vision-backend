package com.proyecto.vision.controladoras.acceso;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.controladoras.GenericoController;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.acceso.Suscripcion;
import com.proyecto.vision.servicios.interf.acceso.ISuscripcionService;
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

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathSuscripcion;

@RestController
@RequestMapping(contexto+pathSuscripcion)
public class SuscripcionController implements GenericoController<Suscripcion> {
    @Autowired
    private ISuscripcionService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<Suscripcion> suscripciones = servicio.consultar();
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, suscripciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
	    List<Suscripcion> suscripciones = servicio.consultarPorEstado(estado);
	    Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, suscripciones);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresa/{empresaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId) {
        List<Suscripcion> suscripciones = servicio.consultarPorEmpresa(empresaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, suscripciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresaYEstado/{empresaId}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("empresaId") long empresaId, @PathVariable("estado") String estado) {
        List<Suscripcion> suscripciones = servicio.consultarPorEmpresaYEstado(empresaId, estado);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, suscripciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Suscripcion suscripcion = servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_obtener_exitoso, suscripcion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/obtenerUltimoPorEmpresa/{empresaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerultimoPorEmpresa(@PathVariable("empresaId") long empresaId) {
        Suscripcion suscripcion = servicio.obtenerUltimoPorEmpresa(empresaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, suscripcion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid Suscripcion _suscripcion) {
        Suscripcion suscripcion = servicio.crear(_suscripcion);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_crear_exitoso, suscripcion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Suscripcion _suscripcion) {
        Suscripcion suscripcion = servicio.actualizar(_suscripcion);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_actualizar_exitoso, suscripcion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody Suscripcion _suscripcion) {
    	Suscripcion suscripcion = servicio.activar(_suscripcion);
        Respuesta respuesta= new Respuesta(true, Constantes.mensaje_activar_exitoso, suscripcion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody Suscripcion _suscripcion) {
        Suscripcion suscripcion = servicio.inactivar(_suscripcion);
        Respuesta respuesta= new Respuesta(true, Constantes.mensaje_inactivar_exitoso, suscripcion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
