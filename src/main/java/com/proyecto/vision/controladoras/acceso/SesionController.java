package com.proyecto.vision.controladoras.acceso;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathSesion;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.controladoras.GenericoController;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.acceso.Sesion;
import com.proyecto.vision.servicios.interf.acceso.ISesionService;
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
@RequestMapping(contexto+pathSesion)
public class SesionController implements GenericoController<Sesion> {
    @Autowired
    private ISesionService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<Sesion> sesiones = servicio.consultar();
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, sesiones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Sesion sesion=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, sesion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody Sesion _sesion) {
        Sesion sesion=servicio.crear(_sesion);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, sesion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Sesion _sesion) {
        Sesion sesion=servicio.actualizar(_sesion);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, sesion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PostMapping(value = "/validar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> validarSesion(@RequestBody @Valid Sesion _sesion) {
        Optional<Sesion> sesion=servicio.validar(_sesion);
        return new ResponseEntity<>(sesion, HttpStatus.OK);
    }
}
