package com.proyecto.sicecuador.controladoras.cliente;

import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.cliente.Auxiliar;
import com.proyecto.sicecuador.modelos.cliente.GrupoCliente;
import com.proyecto.sicecuador.modelos.cliente.GrupoCliente;
import com.proyecto.sicecuador.servicios.interf.cliente.IGrupoClienteService;
import com.proyecto.sicecuador.servicios.interf.cliente.IGrupoClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/api/sicecuador/grupocliente")
public class GrupoClienteController implements GenericoController<GrupoCliente> {
    @Autowired
    private IGrupoClienteService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        try {
            List<GrupoCliente> grupos_clientes = servicio.consultar();
            Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, grupos_clientes);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        try {
            GrupoCliente grupo_cliente=servicio.obtener(new GrupoCliente(id)).get();
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, grupo_cliente);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        } catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid GrupoCliente _GrupoCliente, BindingResult bindig_result) {
        try {
            GrupoCliente grupo_cliente=servicio.crear(_GrupoCliente);
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, grupo_cliente);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody GrupoCliente _GrupoCliente) {
        try {
            GrupoCliente grupo_cliente=servicio.actualizar(_GrupoCliente);
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, grupo_cliente);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        try {
            GrupoCliente grupo_cliente=servicio.eliminar(new GrupoCliente(id));
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, grupo_cliente);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
