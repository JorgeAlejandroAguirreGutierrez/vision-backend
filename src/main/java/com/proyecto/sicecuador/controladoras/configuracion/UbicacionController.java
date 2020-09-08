package com.proyecto.sicecuador.controladoras.configuracion;

import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.cliente.Auxiliar;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.modelos.inventario.Producto;
import com.proyecto.sicecuador.servicios.interf.configuracion.IUbicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sicecuador/ubicacion")
public class UbicacionController implements GenericoController<Ubicacion> {
    @Autowired
    private IUbicacionService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        try {
            List<Ubicacion> ubicaciones=servicio.consultar();
            Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, ubicaciones);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        try {
            Ubicacion ubicacion=servicio.obtener(new Ubicacion(id)).get();
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, ubicacion);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid Ubicacion _ubicacion, BindingResult bindig_result) {
        try {
            Ubicacion ubicacion=servicio.crear(_ubicacion);
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, ubicacion);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Ubicacion _ubicacion) {
        try {
            Ubicacion ubicacion=servicio.actualizar(_ubicacion);
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, ubicacion);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        try {
            Ubicacion ubicacion=servicio.eliminar(new Ubicacion(id));
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, ubicacion);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/provincia",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarProvincias() {
        try {
            List<Ubicacion> ubicaciones=servicio.consultarProvincias();
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, ubicaciones);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/provincia/{provincia}/canton", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarCantones(@PathVariable("provincia") String provincia) {
        try {
            Ubicacion ubicacion=new Ubicacion();
            ubicacion.setProvincia(provincia);
            List<Ubicacion> ubicaciones=servicio.consultarCantones(ubicacion);
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, ubicaciones);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/provincia/canton/{canton}/parroquia", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarParroquias(@PathVariable("canton") String canton) {
        try {
            Ubicacion ubicacion=new Ubicacion();
            ubicacion.setCanton(canton);
            List<Ubicacion> ubicaciones=servicio.consultarParroquias(ubicacion);
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, ubicaciones);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/{provincia}/{canton}/{parroquia}/id", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerUbicacionID(@PathVariable("provincia") String provincia,
                                                @PathVariable("canton") String canton,
                                                @PathVariable("parroquia") String parroquia) {
        try {
            Ubicacion ubicacion=new Ubicacion(provincia, canton, parroquia);
            Optional<Ubicacion> _ubicacion=servicio.obtenerUbicacionID(ubicacion);
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, _ubicacion);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/buscar/{codigo_norma}/{provincia}/{canton}/{parroquia}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarNombre(@PathVariable("codigo_norma") String codigo_norma, @PathVariable("provincia") String provincia,
                                          @PathVariable("canton") String canton, @PathVariable("parroquia") String parroquia) {
        try {
            List<Ubicacion> ubicaciones=servicio.buscar(new Ubicacion(codigo_norma, provincia, canton, parroquia));
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, ubicaciones);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping(value = "/importar", headers = "content-type=multipart/*", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> importar(@RequestPart("archivo") MultipartFile archivo) {
        try {
            boolean bandera=servicio.importar(archivo);
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, bandera);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
