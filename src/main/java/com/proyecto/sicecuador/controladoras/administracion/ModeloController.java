package com.proyecto.sicecuador.controladoras.administracion;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.modelo;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.administracion.Modelo;
import com.proyecto.sicecuador.servicios.interf.administracion.IModeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping(contexto+modelo)
public class ModeloController {
    @Autowired
    private IModeloService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
	    List<Modelo> modelos=servicio.consultar();
	    Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, modelos);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Modelo modelo=servicio.obtener(new Modelo(id)).get();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, modelo);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid Modelo _modelo, BindingResult bindig_result) {
        Modelo modelo=servicio.crear(_modelo);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, modelo);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Modelo _modelo) {
        Modelo modelo=servicio.actualizar(_modelo);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, modelo);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        Modelo modelo=servicio.eliminar(new Modelo(id));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, modelo);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
