package com.proyecto.sicecuador.controladoras.recaudacion;

import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.recaudacion.CuentaPropia;
import com.proyecto.sicecuador.modelos.recaudacion.OperadorTarjeta;
import com.proyecto.sicecuador.servicios.interf.recaudacion.ICuentaPropiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/sicecuador/cuentapropia")
public class CuentaPropiaController implements GenericoController<CuentaPropia> {
    @Autowired
    private ICuentaPropiaService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        try {
            List<CuentaPropia> cuentas_propias=servicio.consultar();
            Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, cuentas_propias);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        try {
            Optional<CuentaPropia> cuenta_propia=servicio.obtener(new CuentaPropia(id));
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, cuenta_propia);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid CuentaPropia _cuenta_propia, BindingResult bindig_result) {
        try {
            CuentaPropia cuenta_propia=servicio.crear(_cuenta_propia);
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, cuenta_propia);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody CuentaPropia _cuenta_propia) {
        try {
            CuentaPropia cuenta_propia=servicio.actualizar(_cuenta_propia);
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, cuenta_propia);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        try {
            CuentaPropia cuenta_propia=servicio.eliminar(new CuentaPropia(id));
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, cuenta_propia);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
