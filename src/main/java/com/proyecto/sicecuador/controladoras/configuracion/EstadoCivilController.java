package com.proyecto.sicecuador.controladoras.configuracion;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathEstadoCivil;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.cajaBanco.Banco;
import com.proyecto.sicecuador.modelos.configuracion.EstadoCivil;
import com.proyecto.sicecuador.servicios.interf.configuracion.IEstadoCivilService;
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
        List<EstadoCivil> estados_civiles=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, estados_civiles);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/consultarActivos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarActivos() {
	    List<EstadoCivil> estadosCiviles = servicio.consultarActivos();
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
        EstadoCivil estado_civil=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, estado_civil);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid EstadoCivil _EstadoCivil) {
        EstadoCivil estado_civil=servicio.crear(_EstadoCivil);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, estado_civil);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody EstadoCivil _EstadoCivil) {
        EstadoCivil estado_civil=servicio.actualizar(_EstadoCivil);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, estado_civil);
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
