package com.proyecto.sicecuador.controladoras.contabilidad;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.contabilidad.MovimientoContable;
import com.proyecto.sicecuador.servicios.interf.contabilidad.IMovimientoContableService;
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
import java.util.Optional;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathMovimientoContable;

@RestController
@RequestMapping(contexto+pathMovimientoContable)
public class MovimientoContableController implements GenericoController<MovimientoContable> {
    @Autowired
    private IMovimientoContableService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
	    List<MovimientoContable> movimientos_contables=servicio.consultar();
	    Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, movimientos_contables);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<MovimientoContable> movimientos_contables = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, movimientos_contables);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Optional<MovimientoContable> movimientos_contables=servicio.obtener(new MovimientoContable(id));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, movimientos_contables);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid MovimientoContable _movimientoContable) {
        MovimientoContable movimientoContable=servicio.crear(_movimientoContable);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, movimientoContable);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody MovimientoContable _movimientoContable) {
        MovimientoContable movimientoContable=servicio.actualizar(_movimientoContable);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, movimientoContable);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        MovimientoContable movimientoContable=servicio.eliminar(new MovimientoContable(id));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, movimientoContable);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/buscar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscar(@RequestBody MovimientoContable movimientoContable) {
    	List<MovimientoContable> movimientosContables=servicio.buscar(movimientoContable);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, movimientosContables);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> importar(MultipartFile file) {
        return null;
    }
}
