package com.proyecto.vision.controladoras.acceso;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.controladoras.GenericoController;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.acceso.Paquete;
import com.proyecto.vision.servicios.interf.acceso.IPaqueteService;
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
import static com.proyecto.vision.controladoras.Endpoints.pathPaquete;

@RestController
@RequestMapping(contexto+pathPaquete)
public class PaqueteController implements GenericoController<Paquete> {
    @Autowired
    private IPaqueteService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<Paquete> paquetes = servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, paquetes);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
	    List<Paquete> paquetes = servicio.consultarPorEstado(estado);
	    Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, paquetes);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Paquete paquete = servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_obtener_exitoso, paquete);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid Paquete _paquete) {
        Paquete paquete = servicio.crear(_paquete);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_crear_exitoso, paquete);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Paquete _paquete) {
        Paquete paquete = servicio.actualizar(_paquete);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_actualizar_exitoso, paquete);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/calcular", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> calcular(@RequestBody @Valid Paquete _paquete) {
        Paquete paquete = servicio.calcular(_paquete);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_crear_exitoso, paquete);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody Paquete _paquete) {
    	Paquete paquete = servicio.activar(_paquete);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_activar_exitoso, paquete);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody Paquete _paquete) {
        Paquete paquete = servicio.inactivar(_paquete);
        Respuesta respuesta= new Respuesta(true, Constantes.mensaje_inactivar_exitoso, paquete);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
