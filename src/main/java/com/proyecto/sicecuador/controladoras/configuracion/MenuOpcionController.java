package com.proyecto.sicecuador.controladoras.configuracion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.configuracion.MenuOpcion;
import com.proyecto.sicecuador.servicios.interf.configuracion.IMenuOpcionService;
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

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathMenuOpcion;

@RestController
@RequestMapping(contexto+pathMenuOpcion)
public class MenuOpcionController implements GenericoController<MenuOpcion> {
    @Autowired
    private IMenuOpcionService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<MenuOpcion> opciones=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, opciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
	    List<MenuOpcion> opciones = servicio.consultarPorEstado(estado);
	    Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, opciones);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<MenuOpcion> opciones = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, opciones);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        MenuOpcion menuOpcion=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, menuOpcion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid MenuOpcion _menuOpcion) {
        MenuOpcion menuOpcion=servicio.crear(_menuOpcion);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, menuOpcion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody MenuOpcion _menuOpcion) {
        MenuOpcion menuOpcion=servicio.actualizar(_menuOpcion);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, menuOpcion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody MenuOpcion _menuOpcion) {
        MenuOpcion menuOpcion=servicio.activar(_menuOpcion);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_activar_exitoso, menuOpcion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody MenuOpcion _menuOpcion) {
        MenuOpcion menuOpcion=servicio.inactivar(_menuOpcion);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_inactivar_exitoso, menuOpcion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/obtenerPorOperacion/{operacion}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerOperacion(@PathVariable("operacion") String operacion) {
        MenuOpcion opcion=servicio.obtenerPorOperacion(operacion);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, opcion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarModulos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarModulos() {
        List<String> modulos = servicio.consultarModulos();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, modulos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorModulo/{modulo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorModulo(@PathVariable("modulo") String modulo) {
        List<MenuOpcion> opciones=servicio.consultarPorModulo(modulo);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, opciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/consultarPorOperacion/{operacion}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarOperacion(@PathVariable("operacion") String operacion) {
        List<MenuOpcion> opcion=servicio.consultarPorOperacion(operacion);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, opcion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
