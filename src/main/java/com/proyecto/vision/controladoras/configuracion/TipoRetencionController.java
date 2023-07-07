package com.proyecto.vision.controladoras.configuracion;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathTipoRetencion;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.controladoras.GenericoController;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.configuracion.TipoRetencion;
import com.proyecto.vision.servicios.interf.configuracion.ITipoRetencionService;
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
@RequestMapping(contexto+pathTipoRetencion)
public class TipoRetencionController implements GenericoController<TipoRetencion> {
    @Autowired
    private ITipoRetencionService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<TipoRetencion> tiposRetenciones=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, tiposRetenciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarporEstado(@PathVariable("estado") String estado) {
	    List<TipoRetencion> tiposRetenciones = servicio.consultarPorEstado(estado);
	    Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, tiposRetenciones);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<TipoRetencion> tiposRetenciones = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, tiposRetenciones);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        TipoRetencion tipoRetencion=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, tipoRetencion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid TipoRetencion _tipo_retencion) {
        TipoRetencion tipoRetencion=servicio.crear(_tipo_retencion);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, tipoRetencion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody TipoRetencion _tipo_retencion) {
        TipoRetencion tipoRetencion=servicio.actualizar(_tipo_retencion);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, tipoRetencion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody TipoRetencion _tipoRetencion) {
    	TipoRetencion tipoRetencion=servicio.activar(_tipoRetencion);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_activar_exitoso, tipoRetencion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody TipoRetencion _tipoRetencion) {
    	TipoRetencion tipoRetencion = servicio.inactivar(_tipoRetencion);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_inactivar_exitoso, tipoRetencion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarIvaBien", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarIvaBien() {
        List<TipoRetencion> tiposRetenciones=servicio.consultarIvaBien();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, tiposRetenciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/consultarIvaServicio", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarIvaServicio() {
        List<TipoRetencion> tiposRetenciones=servicio.consultarIvaServicio();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, tiposRetenciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/consultarRentaBien", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarRentaBien() {
        List<TipoRetencion> tiposRetenciones=servicio.consultarRentaBien();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, tiposRetenciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/consultarRentaServicio", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarRentaServicio() {
        List<TipoRetencion> tiposRetenciones=servicio.consultarRentaServicio();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, tiposRetenciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
