package com.proyecto.sicecuador.controladoras.cliente;

import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.cliente.Auxiliar;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.servicios.interf.cliente.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sicecuador/cliente")
public class ClienteController implements GenericoController<Cliente> {
    @Autowired
    private IClienteService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        try {
            List<Cliente> clientes=servicio.consultar();
            Respuesta respuesta=new Respuesta(true,"Se consulto los clientes", clientes);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        try {
            Optional<Cliente> cliente=servicio.obtener(new Cliente(id));
            Respuesta respuesta=new Respuesta(true,"Se obtuvo el cliente", cliente);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody Cliente _cliente) {
        try {
            _cliente.normalizar();
            Cliente cliente=servicio.crear(_cliente);
            Respuesta respuesta=new Respuesta(true,"Se creo el cliente", cliente);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Cliente _cliente) {
        try {
            _cliente.normalizar();
            Cliente cliente=servicio.actualizar(_cliente);
            Respuesta respuesta=new Respuesta(true,"Se actualizo el cliente", cliente);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        try {
            Cliente cliente=servicio.eliminar(new Cliente(id));
            Respuesta respuesta=new Respuesta(true,"Se Elimino el cliente", cliente);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/identificacion/{identificacion}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerIdentificacion(@PathVariable("identificacion") String identificacion) {
        try {
            Cliente cliente=new Cliente();
            cliente.setIdentificacion(identificacion);
            Optional<Cliente> _cliente=servicio.obtenerIdentificacion(cliente);
            Respuesta respuesta= new Respuesta(true,"Se obtuvo el cliente", _cliente);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/razonsocial/{razonsocial}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerRazonSocial(@PathVariable("razonsocial") String razon_social) {
        try {
            Cliente cliente=new Cliente();
            cliente.setRazon_social(razon_social);
            Optional<Cliente> _cliente=servicio.obtenerRazonSocial(cliente);
            Respuesta respuesta= new Respuesta(true,"Se obtuvo el cliente", _cliente);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/buscar/razonsocial/{razonsocial}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarRazonSocial(@PathVariable("razonsocial") String razon_social) {
        try {
            Cliente cliente=new Cliente();
            cliente.setRazon_social(razon_social);
            List<Cliente> _cliente=servicio.consultarRazonSocial(cliente);
            Respuesta respuesta= new Respuesta(true,"Se consulto los clientes", _cliente);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/buscar/identificacion/{identificacion}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarIdentificacion(@PathVariable("identificacion") String identificacion) {
        try {
            Cliente cliente=new Cliente();
            cliente.setIdentificacion(identificacion);
            List<Cliente> _cliente=servicio.consultarIdentificacion(cliente);
            Respuesta respuesta= new Respuesta(true,"Se consulto los clientes", _cliente);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/identificacion/validar/{identificacion}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> validarIdentificacion(@PathVariable("identificacion") String identificacion) {
        try {
            Cliente cliente=new Cliente();
            cliente.setIdentificacion(identificacion);
            Optional<Cliente> _cliente=servicio.validarIdentificacion(cliente);
            Respuesta respuesta= new Respuesta(true,"Se valido la identificacion", _cliente);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
