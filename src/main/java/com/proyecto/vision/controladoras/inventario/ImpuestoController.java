package com.proyecto.vision.controladoras.inventario;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathImpuesto;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.controladoras.GenericoController;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.configuracion.Impuesto;
import com.proyecto.vision.servicios.interf.configuracion.IImpuestoService;
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
@RequestMapping(contexto+pathImpuesto)
public class ImpuestoController implements GenericoController<Impuesto> {
    @Autowired
    private IImpuestoService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<Impuesto> impuestos=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, impuestos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
	    List<Impuesto> impuestos = servicio.consultarPorEstado(estado);
	    Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, impuestos);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Impuesto impuesto = servicio.obtener(id);
        Respuesta respuesta = new Respuesta(true,Constantes.mensaje_obtener_exitoso, impuesto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid Impuesto _impuesto) {
        Impuesto impuesto = servicio.crear(_impuesto);
        Respuesta respuesta = new Respuesta(true,Constantes.mensaje_crear_exitoso, impuesto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Impuesto _impuesto) {
        Impuesto impuesto = servicio.actualizar(_impuesto);
        Respuesta respuesta = new Respuesta(true,Constantes.mensaje_actualizar_exitoso, impuesto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody Impuesto _impuesto) {
    	Impuesto impuesto = servicio.activar(_impuesto);
        Respuesta respuesta = new Respuesta(true,Constantes.mensaje_activar_exitoso, impuesto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody Impuesto _impuesto) {
    	Impuesto impuesto = servicio.inactivar(_impuesto);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_inactivar_exitoso, impuesto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/obtenerPorPorcentajeYEstado/{porcentaje}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerPorPorcentajeYEstado(@PathVariable("porcentaje") double porcentaje, @PathVariable("estado") String estado) {
        Impuesto impuesto = servicio.obtenerPorPorcentajeYEstado(porcentaje, estado);
        Respuesta respuesta = new Respuesta(true,Constantes.mensaje_obtener_exitoso, impuesto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/obtenerPorCodigoSRIYEstado/{codigoSRI}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerPorPorcentajeYEstado(@PathVariable("codigoSRI") String codigoSRI, @PathVariable("estado") String estado) {
        Impuesto impuesto = servicio.obtenerPorCodigoSRIYEstado(codigoSRI, estado);
        Respuesta respuesta = new Respuesta(true,Constantes.mensaje_obtener_exitoso, impuesto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
