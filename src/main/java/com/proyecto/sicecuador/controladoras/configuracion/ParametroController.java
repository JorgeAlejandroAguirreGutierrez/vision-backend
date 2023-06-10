package com.proyecto.sicecuador.controladoras.configuracion;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathParametro;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.configuracion.Parametro;
import com.proyecto.sicecuador.servicios.interf.configuracion.IParametroService;
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
@RequestMapping(contexto+pathParametro)
public class ParametroController implements GenericoController<Parametro> {
    @Autowired
    private IParametroService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<Parametro> parametros = servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, parametros);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
	    List<Parametro> parametros = servicio.consultarPorEstado(estado);
	    Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, parametros);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<Parametro> parametros = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, parametros);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Parametro parametro=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, parametro);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid Parametro _parametro) {
        Parametro parametro=servicio.crear(_parametro);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, parametro);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Parametro _parametro) {
        Parametro parametro=servicio.actualizar(_parametro);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, parametro);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody Parametro _parametro) {
    	Parametro parametro=servicio.activar(_parametro);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_activar_exitoso, parametro);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody Parametro _parametro) {
    	Parametro parametro=servicio.inactivar(_parametro);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_inactivar_exitoso, parametro);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/obtenerPorTipo/{tipo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerTipo(@PathVariable("tipo") String tipo) {
        Parametro parametro=servicio.obtenerPorTipo(tipo);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, parametro);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/consultarPorTipo/{tipo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarTipo(@PathVariable("tipo") String tipo) {
        List<Parametro> parametro=servicio.consultarPorTipo(tipo);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, parametro);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
