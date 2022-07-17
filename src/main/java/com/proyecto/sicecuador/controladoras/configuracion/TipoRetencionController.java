package com.proyecto.sicecuador.controladoras.configuracion;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathTipoRetencion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.configuracion.TipoRetencion;
import com.proyecto.sicecuador.servicios.interf.configuracion.ITipoRetencionService;
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

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<TipoRetencion> tiposRetenciones = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, tiposRetenciones);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        TipoRetencion tipoRetencion=servicio.obtener(new TipoRetencion(id)).get();
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

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        TipoRetencion tipoRetencion=servicio.eliminar(new TipoRetencion(id));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, tipoRetencion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/ivaBien", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarIvaBien() {
        List<TipoRetencion> tiposRetenciones=servicio.consultarIvaBien();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, tiposRetenciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/ivaServicio", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarIvaServicio() {
        List<TipoRetencion> tiposRetenciones=servicio.consultarIvaServicio();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, tiposRetenciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/rentaBien", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarRentaBien() {
        List<TipoRetencion> tiposRetenciones=servicio.consultarRentaBien();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, tiposRetenciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/rentaServicio", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarRentaServicio() {
        List<TipoRetencion> tiposRetenciones=servicio.consultarRentaServicio();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, tiposRetenciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @PostMapping(value = "/importar", headers = "content-type=multipart/*", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> importar(MultipartFile archivo) {
        boolean bandera=servicio.importar(archivo);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, bandera);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
