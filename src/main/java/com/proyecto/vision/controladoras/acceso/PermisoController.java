package com.proyecto.vision.controladoras.acceso;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathPermiso;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.controladoras.GenericoController;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.acceso.Permiso;
import com.proyecto.vision.servicios.interf.acceso.IPermisoService;
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

@RestController
@RequestMapping(contexto+pathPermiso)
public class PermisoController implements GenericoController<Permiso> {
    @Autowired
    private IPermisoService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<Permiso> permisoes=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, permisoes);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Permiso permiso=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, permiso);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid Permiso _permiso) {
        Permiso permiso=servicio.crear(_permiso);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, permiso);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Permiso _permiso) {
        Permiso permiso=servicio.actualizar(_permiso);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, permiso);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody Permiso _permiso) {
        Permiso permiso = servicio.activar(_permiso);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_activar_exitoso, permiso);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody Permiso _permiso) {
        Permiso permiso = servicio.inactivar(_permiso);
        Respuesta respuesta= new Respuesta(true, Constantes.mensaje_inactivar_exitoso, permiso);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
        List<Permiso> permisos = servicio.consultarPorEstado(estado);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, permisos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/consultarPorPerfil/{perfilId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorPerfil(@PathVariable("perfilId") long perfilId) {
        List<Permiso> permisos = servicio.consultarPorPerfil(perfilId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, permisos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
