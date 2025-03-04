package com.proyecto.vision.controladoras.configuracion;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.controladoras.GenericoController;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.acceso.Empresa;
import com.proyecto.vision.modelos.configuracion.Modelo;
import com.proyecto.vision.modelos.configuracion.Sincronizacion;
import com.proyecto.vision.servicios.interf.configuracion.ISincronizacionService;
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
import java.io.IOException;
import java.util.List;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathSincronizacion;


@RestController
@RequestMapping(contexto+pathSincronizacion)
public class SincronizacionController implements GenericoController<Sincronizacion> {
    @Autowired
    private ISincronizacionService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<Sincronizacion> sincronizaciones = servicio.consultar();
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, sincronizaciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
	    List<Sincronizacion> sincronizaciones = servicio.consultarPorEstado(estado);
	    Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, sincronizaciones);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Sincronizacion sincronizacion = servicio.obtener(id);
        Respuesta respuesta = new Respuesta(true,Constantes.mensaje_obtener_exitoso, sincronizacion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid Sincronizacion _sincronizacion) {
        Sincronizacion sincronizacion = servicio.crear(_sincronizacion);
        Respuesta respuesta = new Respuesta(true,Constantes.mensaje_crear_exitoso, sincronizacion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Sincronizacion _sincronizacion) {
        Sincronizacion sincronizacion = servicio.actualizar(_sincronizacion);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, sincronizacion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/procesar/{sincronizacionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> procesar(@PathVariable("sincronizacionId") long sincronizacionId) {
        List<Modelo> modelos = servicio.procesar(sincronizacionId);
        Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, modelos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/crearModelos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crearModelos(@RequestBody List<Modelo> _modelos) {
        servicio.crearModelos(_modelos);
        Respuesta respuesta = new Respuesta(true,Constantes.mensaje_crear_exitoso, null);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/cargarArchivo/{sincronizacionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cargarArchivo(@PathVariable("sincronizacionId") long sincronizacionId, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        Sincronizacion sincronizacion = servicio.cargarArchivo(sincronizacionId, multipartFile);
        Respuesta respuesta = new Respuesta(true,Constantes.mensaje_crear_exitoso, sincronizacion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
