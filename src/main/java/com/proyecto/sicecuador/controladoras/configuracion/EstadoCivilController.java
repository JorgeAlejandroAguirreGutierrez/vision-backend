package com.proyecto.vision.controladoras.configuracion;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathEstadoCivil;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.controladoras.GenericoController;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.configuracion.EstadoCivil;
import com.proyecto.vision.servicios.interf.configuracion.IEstadoCivilService;
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
@RequestMapping(contexto+pathEstadoCivil)
public class EstadoCivilController implements GenericoController<EstadoCivil> {
    @Autowired
    private IEstadoCivilService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<EstadoCivil> estadosCiviles = servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, estadosCiviles);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
	    List<EstadoCivil> estadosCiviles = servicio.consultarPorEstado(estado);
	    Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, estadosCiviles);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }


    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<EstadoCivil> estadosciviles = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, estadosciviles);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        EstadoCivil estadoCivil = servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, estadoCivil);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid EstadoCivil _EstadoCivil) {
        EstadoCivil estadoCivil = servicio.crear(_EstadoCivil);
        Respuesta respuesta = new Respuesta(true,Constantes.mensaje_crear_exitoso, estadoCivil);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody EstadoCivil _EstadoCivil) {
        EstadoCivil estadoCivil = servicio.actualizar(_EstadoCivil);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, estadoCivil);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody EstadoCivil _estadoCivil) {
    	EstadoCivil estadoCivil=servicio.activar(_estadoCivil);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_activar_exitoso, estadoCivil);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody EstadoCivil _estadoCivil) {
    	EstadoCivil estadoCivil=servicio.inactivar(_estadoCivil);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_inactivar_exitoso, estadoCivil);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PostMapping(value = "/buscar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscar(@RequestBody EstadoCivil estado_civil) {
        	List<EstadoCivil> estados_civiles=servicio.buscar(estado_civil);
            Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, estados_civiles);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
