package com.proyecto.sicecuador.controladoras.recaudacion;

import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.recaudacion.ModeloTabla;
import com.proyecto.sicecuador.servicios.interf.recaudacion.IModeloTablaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/api/sicecuador/modelotabla")
public class ModeloTablaController implements GenericoController<ModeloTabla> {
    @Autowired
    private IModeloTablaService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        try {
            List<ModeloTabla> modelos_tablas=servicio.consultar();
            Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, modelos_tablas);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        try {
            ModeloTabla modelos_tablas=servicio.obtener(new ModeloTabla(id)).get();
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, modelos_tablas);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody ModeloTabla _modelo_tabla) {
        try {
            ModeloTabla modelo_tabla=servicio.crear(_modelo_tabla);
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, modelo_tabla);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody ModeloTabla _modelo_tabla) {
        try {
            ModeloTabla modelo_tabla=servicio.actualizar(_modelo_tabla);
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, modelo_tabla);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        try {
            ModeloTabla modelo_tabla=servicio.eliminar(new ModeloTabla(id));
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, modelo_tabla);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
