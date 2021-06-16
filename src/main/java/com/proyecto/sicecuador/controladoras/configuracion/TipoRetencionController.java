package com.proyecto.sicecuador.controladoras.configuracion;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathTipoRetencion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
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
        List<TipoRetencion> tipo_retencions=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, tipo_retencions);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<TipoRetencion> tipo_retencions = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, tipo_retencions);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        TipoRetencion tipo_retencion=servicio.obtener(new TipoRetencion(id)).get();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, tipo_retencion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid TipoRetencion _tipo_retencion) {
        TipoRetencion tipo_retencion=servicio.crear(_tipo_retencion);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, tipo_retencion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody TipoRetencion _tipo_retencion) {
        TipoRetencion tipo_retencion=servicio.actualizar(_tipo_retencion);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, tipo_retencion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        TipoRetencion tipo_retencion=servicio.eliminar(new TipoRetencion(id));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, tipo_retencion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/ivabien", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarIvaBien() {
        List<TipoRetencion> tipos_retenciones=servicio.consultarIvaBien();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, tipos_retenciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/ivaservicio", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarIvaServicio() {
        List<TipoRetencion> tipos_retenciones=servicio.consultarIvaServicio();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, tipos_retenciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/rentabien", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarRentaBien() {
        List<TipoRetencion> tipos_retenciones=servicio.consultarRentaBien();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, tipos_retenciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/rentaservicio", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarRentaServicio() {
        List<TipoRetencion> tipos_retenciones=servicio.consultarRentaServicio();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, tipos_retenciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @PostMapping(value = "/importar", headers = "content-type=multipart/*", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> importar(MultipartFile archivo) {
        boolean bandera=servicio.importar(archivo);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, bandera);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
