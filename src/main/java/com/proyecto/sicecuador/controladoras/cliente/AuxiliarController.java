package com.proyecto.sicecuador.controladoras.cliente;

import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.cliente.Auxiliar;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.servicios.interf.cliente.IAuxiliarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/sicecuador/auxiliar")
public class AuxiliarController implements GenericoController<Auxiliar> {
    @Autowired
    private IAuxiliarService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        try {
            List<Auxiliar> categorias_clientes=servicio.consultar();
            Respuesta respuesta=new Respuesta(true,"Se consulto los auxiliares de clientes", categorias_clientes);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        try {
            Auxiliar auxiliar=servicio.obtener(new Auxiliar(id)).get();
            Respuesta respuesta=new Respuesta(true,"Se obtuvo el auxiliar del cliente", auxiliar);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody Auxiliar _auxiliar) {
        try {
            Auxiliar auxiliar=servicio.crear(_auxiliar);
            Respuesta respuesta=new Respuesta(true,"Se creo el auxiliar del cliente", auxiliar);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Auxiliar _auxiliar) {
        try {
            Auxiliar auxiliar=servicio.actualizar(_auxiliar);
            Respuesta respuesta=new Respuesta(true,"Se actualizo el auxiliar del cliente", auxiliar);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        try {
            Auxiliar auxiliar=servicio.eliminar(new Auxiliar(id));
            Respuesta respuesta=new Respuesta(true,"Se elimino el auxiliar del cliente", auxiliar);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/buscar/razonsocial/{razon_social}/{cliente_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("razon_social") String razon_social,
                                     @PathVariable("cliente_id") long cliente_id) {
        try {
            List<Auxiliar> auxiliar=servicio.consultarRazonSocial(new Auxiliar(razon_social, new Cliente(cliente_id)));
            Respuesta respuesta=new Respuesta(true,"Se consulto los auxiliares del cliente", auxiliar);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/cliente/{cliente_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarClienteID(@PathVariable("cliente_id") long cliente_id) {
        try {
            List<Auxiliar> auxiliar=servicio.consultarClienteID(new Auxiliar(new Cliente(cliente_id)));
            Respuesta respuesta=new Respuesta(true,"Se consulto los auxiliares del cliente", auxiliar);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
