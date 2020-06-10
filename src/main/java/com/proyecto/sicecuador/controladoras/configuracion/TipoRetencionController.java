package com.proyecto.sicecuador.controladoras.configuracion;

import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.cliente.Auxiliar;
import com.proyecto.sicecuador.modelos.configuracion.TipoRetencion;
import com.proyecto.sicecuador.servicios.interf.configuracion.ITipoRetencionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/sicecuador/tiporetencion")
public class TipoRetencionController implements GenericoController<TipoRetencion> {
    @Autowired
    private ITipoRetencionService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        try {
            List<TipoRetencion> tipo_retencions=servicio.consultar();
            Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, tipo_retencions);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        try {
            TipoRetencion tipo_retencion=servicio.obtener(new TipoRetencion(id)).get();
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, tipo_retencion);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody TipoRetencion _tipo_retencion) {
        try {
            TipoRetencion tipo_retencion=servicio.crear(_tipo_retencion);
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, tipo_retencion);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody TipoRetencion _tipo_retencion) {
        try {
            TipoRetencion tipo_retencion=servicio.actualizar(_tipo_retencion);
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, tipo_retencion);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        try {
            TipoRetencion tipo_retencion=servicio.eliminar(new TipoRetencion(id));
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, tipo_retencion);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/ivabien", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarIvaBien() {
        try {
            List<TipoRetencion> tipos_retenciones=servicio.consultarIvaBien();
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, tipos_retenciones);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/ivaservicio", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarIvaServicio() {
        try {
            List<TipoRetencion> tipos_retenciones=servicio.consultarIvaServicio();
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, tipos_retenciones);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/rentabien", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarRentaBien() {
        try {
            List<TipoRetencion> tipos_retenciones=servicio.consultarRentaBien();
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, tipos_retenciones);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/rentaservicio", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarRentaServicio() {
        try {
            List<TipoRetencion> tipos_retenciones=servicio.consultarRentaServicio();
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, tipos_retenciones);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
