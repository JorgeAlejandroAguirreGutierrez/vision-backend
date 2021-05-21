package com.proyecto.sicecuador.controladoras.cliente;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathCliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.servicios.interf.cliente.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(contexto+pathCliente)
public class ClienteController implements GenericoController<Cliente> {
    @Autowired
    private IClienteService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<Cliente> clientes=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, clientes);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Optional<Cliente> cliente=servicio.obtener(new Cliente(id));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, cliente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid Cliente _cliente) {
        Cliente cliente=servicio.crear(_cliente);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, cliente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Cliente _cliente) {
        _cliente.normalizar();
        Cliente cliente=servicio.actualizar(_cliente);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, cliente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        Cliente cliente=servicio.eliminar(new Cliente(id));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, cliente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/identificacion/{identificacion}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerIdentificacion(@PathVariable("identificacion") String identificacion) {
        Cliente cliente=new Cliente();
        cliente.setIdentificacion(identificacion);
        Optional<Cliente> _cliente=servicio.obtenerIdentificacion(cliente);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_obtener_exitoso, _cliente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/razonsocial/{razonsocial}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerRazonSocial(@PathVariable("razonsocial") String razonSocial) {
        Cliente cliente=new Cliente();
        cliente.setRazonSocial(razonSocial);
        Optional<Cliente> _cliente=servicio.obtenerRazonSocial(cliente);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_obtener_exitoso, _cliente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @PostMapping(value = "/buscar",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscar(@RequestBody Cliente cliente) {
        List<Cliente> clientes=servicio.buscar(cliente);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, clientes);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/identificacion/validar/{identificacion}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> validarIdentificacion(@PathVariable("identificacion") String identificacion) {
        Cliente cliente=new Cliente();
        cliente.setIdentificacion(identificacion);
        Optional<Cliente> _cliente=servicio.validarIdentificacion(cliente);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_obtener_exitoso, _cliente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @PostMapping(value = "/importar", headers = "content-type=multipart/*", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> importar(@RequestPart("archivo") MultipartFile archivo) {
	    boolean bandera=servicio.importar(archivo);
	    if(bandera){
	        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_importacion_exitoso, bandera);
	        return new ResponseEntity<>(respuesta, HttpStatus.OK);
	    }
	    Respuesta respuesta=new Respuesta(true,Constantes.mensaje_importacion_fallido, bandera);
	    return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
