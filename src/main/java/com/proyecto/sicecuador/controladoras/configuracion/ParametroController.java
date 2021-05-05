package com.proyecto.sicecuador.controladoras.configuracion;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathParametro;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
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
        List<Parametro> parametros=servicio.consultar();
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
        Parametro parametro=servicio.obtener(new Parametro(id)).get();
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

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        Parametro parametro=servicio.eliminar(new Parametro(id));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, parametro);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> importar(MultipartFile file) {
        return null;
    }

    @GetMapping(value = "/tipo/{tipo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerTipo(@PathVariable("tipo") String tipo) {
        Parametro parametro=servicio.obtenerTipo(new Parametro(tipo)).get();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, parametro);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/consultartipo/{tipo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarTipo(@PathVariable("tipo") String tipo) {
        List<Parametro> parametro=servicio.consultarTipo(new Parametro(tipo));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, parametro);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
