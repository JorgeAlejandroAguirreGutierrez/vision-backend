package com.proyecto.vision.controladoras.contabilidad;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.controladoras.GenericoController;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.cajaBanco.Banco;
import com.proyecto.vision.modelos.contabilidad.CuentaContable;
import com.proyecto.vision.modelos.contabilidad.MovimientoContable;
import com.proyecto.vision.servicios.interf.contabilidad.IMovimientoContableService;
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

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathMovimientoContable;

@RestController
@RequestMapping(contexto+pathMovimientoContable)
public class MovimientoContableController implements GenericoController<MovimientoContable> {
    @Autowired
    private IMovimientoContableService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
	    List<MovimientoContable> movimientosContables = servicio.consultar();
	    Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, movimientosContables);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
        List<MovimientoContable> movimientosContables = servicio.consultarPorEstado(estado);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, movimientosContables);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresa/{empresaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId) {
        List<MovimientoContable> movimientosContables = servicio.consultarPorEmpresa(empresaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, movimientosContables);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresaYEstado/{empresaId}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresaYEstado(@PathVariable("empresaId") long empresaId, @PathVariable("estado") String estado) {
        List<MovimientoContable> movimientosContables = servicio.consultarPorEmpresaYEstado(empresaId, estado);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, movimientosContables);
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
        MovimientoContable movimientosContable=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, movimientosContable);
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
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody MovimientoContable _movimientoContable) {
    	MovimientoContable movimientoContable=servicio.activar(_movimientoContable);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_activar_exitoso, movimientoContable);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody MovimientoContable _movimientoContable) {
    	MovimientoContable movimientoContable=servicio.inactivar(_movimientoContable);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_inactivar_exitoso, movimientoContable);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/buscar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscar(@RequestBody MovimientoContable movimientoContable) {
    	List<MovimientoContable> movimientosContables=servicio.buscar(movimientoContable);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, movimientosContables);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
