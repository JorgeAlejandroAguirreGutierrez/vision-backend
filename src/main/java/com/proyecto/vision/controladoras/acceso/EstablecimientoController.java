package com.proyecto.vision.controladoras.acceso;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathEstablecimiento;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.controladoras.GenericoController;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.acceso.Establecimiento;
import com.proyecto.vision.servicios.interf.acceso.IEstablecimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(contexto+pathEstablecimiento)
public class EstablecimientoController implements GenericoController<Establecimiento> {
    
    @Autowired
    private IEstablecimientoService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<Establecimiento> establecimientos=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, establecimientos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresa/{empresaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId) {
        List<Establecimiento> establecimientos = servicio.consultarPorEmpresa(empresaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, establecimientos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
	    List<Establecimiento> establecimientos = servicio.consultarPorEstado(estado);
	    Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, establecimientos);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresaYEstado/{empresaId}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresaYEstado(@PathVariable("empresaId") long empresaId, @PathVariable("estado") String estado) {
        List<Establecimiento> establecimientos=servicio.consultarPorEmpresaYEstado(empresaId, estado);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, establecimientos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Establecimiento establecimiento=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, establecimiento);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody Establecimiento _establecimiento) {
        Establecimiento establecimiento=servicio.crear(_establecimiento);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, establecimiento);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Establecimiento _establecimiento) {
        Establecimiento establecimiento=servicio.actualizar(_establecimiento);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, establecimiento);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody Establecimiento _establecimiento) {
    	Establecimiento establecimiento = servicio.activar(_establecimiento);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_activar_exitoso, establecimiento);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody Establecimiento _establecimiento) {
    	Establecimiento establecimiento = servicio.inactivar(_establecimiento);
        Respuesta respuesta= new Respuesta(true, Constantes.mensaje_inactivar_exitoso, establecimiento);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
