package com.proyecto.sicecuador.controladoras.cliente;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathCliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.servicios.interf.cliente.IClienteService;
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
    
    @GetMapping(value = "/consultarActivos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarActivos() {
	    List<Cliente> clientes=servicio.consultarActivos();
	    Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, clientes);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<Cliente> clientes = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, clientes);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Cliente cliente=servicio.obtener(id);
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
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody Cliente _cliente) {
    	Cliente cliente=servicio.activar(_cliente);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_activar_exitoso, cliente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody Cliente _cliente) {
    	Cliente cliente=servicio.inactivar(_cliente);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_inactivar_exitoso, cliente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/identificacion/{identificacion}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerIdentificacion(@PathVariable("identificacion") String identificacion) {
        Cliente cliente=new Cliente();
        cliente.setIdentificacion(identificacion);
        Cliente _cliente=servicio.obtenerPorIdentificacion(cliente);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_obtener_exitoso, _cliente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/razonSocial/{razonSocial}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerRazonSocial(@PathVariable("razonSocial") String razonSocial) {
        Cliente cliente=new Cliente();
        cliente.setRazonSocial(razonSocial);
        Cliente _cliente=servicio.obtenerPorRazonSocial(cliente);
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
        Cliente _cliente=servicio.validarIdentificacion(identificacion);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_obtener_exitoso, _cliente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }  
    
    @PostMapping(value = "/importar", headers = "content-type=multipart/*", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> importar(@RequestPart("archivo") MultipartFile archivo) {
	    servicio.importar(archivo);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_importacion_exitoso, null);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
