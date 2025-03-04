package com.proyecto.vision.controladoras.cliente;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathOrigenIngreso;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.controladoras.GenericoController;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.cliente.OrigenIngreso;
import com.proyecto.vision.servicios.interf.cliente.IOrigenIngresoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping(contexto+pathOrigenIngreso)
public class OrigenIngresoController implements GenericoController<OrigenIngreso> {
    @Autowired
    private IOrigenIngresoService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<OrigenIngreso> origenes_ingresos = servicio.consultar();
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, origenes_ingresos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
	    List<OrigenIngreso> origenesIngresos = servicio.consultarPorEstado(estado);
	    Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, origenesIngresos);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        OrigenIngreso origenIngreso=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, origenIngreso);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid OrigenIngreso _origenIngreso) {
        OrigenIngreso origenIngreso=servicio.crear(_origenIngreso);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, origenIngreso);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody OrigenIngreso _OrigenIngreso) {
        OrigenIngreso origenIngreso=servicio.actualizar(_OrigenIngreso);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, origenIngreso);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody OrigenIngreso _origenIngreso) {
    	OrigenIngreso origenIngreso=servicio.activar(_origenIngreso);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_activar_exitoso, origenIngreso);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody OrigenIngreso _origenIngreso) {
    	OrigenIngreso origenIngreso=servicio.inactivar(_origenIngreso);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_inactivar_exitoso, origenIngreso);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
