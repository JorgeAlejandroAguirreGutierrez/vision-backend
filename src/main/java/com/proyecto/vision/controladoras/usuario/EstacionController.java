package com.proyecto.vision.controladoras.usuario;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathEstacion;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.controladoras.GenericoController;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.usuario.Estacion;
import com.proyecto.vision.servicios.interf.usuario.IEstacionService;
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
@RequestMapping(contexto+pathEstacion)
public class EstacionController implements GenericoController<Estacion> {
    @Autowired
    private IEstacionService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<Estacion> estaciones=servicio.consultar();
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, estaciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<Estacion> estaciones = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, estaciones);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Estacion estacion=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, estacion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid Estacion _estacion) {
        Estacion estacion=servicio.crear(_estacion);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, estacion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Estacion _estacion) {
        Estacion estacion=servicio.actualizar(_estacion);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_actualizar_exitoso, estacion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody Estacion _estacion) {
    	Estacion estacion = servicio.activar(_estacion);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_activar_exitoso, estacion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody Estacion _estacion) {
    	Estacion estacion = servicio.inactivar(_estacion);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_inactivar_exitoso, estacion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarActivos(@PathVariable("estado") String estado) {
        List<Estacion> estaciones = servicio.consultarPorEstado(estado);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, estaciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresa/{empresaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId) {
        List<Estacion> estaciones = servicio.consultarPorEmpresa(empresaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, estaciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/establecimiento/{establecimientoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarEstablecimiento(@PathVariable("establecimientoId") long establecimientoId) {
        List<Estacion> estacion=servicio.consultarPorEstablecimiento(establecimientoId);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, estacion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/estacionesPorEstablecimiento/{establecimientoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarEstacionesPorEstablecimiento(@PathVariable("establecimientoId") long establecimientoId) {
        List<Estacion> estaciones=servicio.consultarEstacionesPorEstablecimiento(establecimientoId);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, estaciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/puntosVentaPorEstablecimiento/{establecimientoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPuntosVentaPorEstablecimiento(@PathVariable("establecimientoId") long establecimientoId) {
        List<Estacion> puntosVenta=servicio.consultarPuntosVentaPorEstablecimiento(establecimientoId);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, puntosVenta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
