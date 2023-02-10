package com.proyecto.sicecuador.controladoras.cliente;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathOrigenIngreso;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.cliente.OrigenIngreso;
import com.proyecto.sicecuador.servicios.interf.cliente.IOrigenIngresoService;
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
    
    @GetMapping(value = "/consultarActivos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarActivos() {
	    List<OrigenIngreso> origenesIngresos = servicio.consultarActivos();
	    Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, origenesIngresos);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<OrigenIngreso> origeningresos = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, origeningresos);
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
    
    @PostMapping(value = "/buscar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscar(@RequestBody OrigenIngreso origenIngreso) {
    	List<OrigenIngreso> origenesIngresos=servicio.buscar(origenIngreso);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, origenesIngresos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
