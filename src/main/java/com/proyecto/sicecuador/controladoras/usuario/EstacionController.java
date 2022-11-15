package com.proyecto.sicecuador.controladoras.usuario;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathEstacion;
import com.proyecto.sicecuador.controladoras.GenericoController;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.usuario.Estacion;
import com.proyecto.sicecuador.servicios.interf.usuario.IEstacionService;
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
        List<Estacion> estaciones = servicio.consultar();
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, estaciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<Estacion> estaciones = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, estaciones);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Estacion estacion=servicio.obtener(new Estacion(id)).get();
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
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, estacion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        Estacion estacion=servicio.eliminar(new Estacion(id));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, estacion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/importar", headers = "content-type=multipart/*", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> importar(@RequestPart("archivo") MultipartFile archivo) {
	    boolean bandera=servicio.importar(archivo);
	    Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, bandera);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }	

}
