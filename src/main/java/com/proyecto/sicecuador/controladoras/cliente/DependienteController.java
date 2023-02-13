package com.proyecto.sicecuador.controladoras.cliente;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathDependiente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.cliente.Dependiente;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.servicios.interf.cliente.IDependienteService;
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
@RequestMapping(contexto+pathDependiente)
public class DependienteController implements GenericoController<Dependiente> {
    @Autowired
    private IDependienteService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<Dependiente> dependientes=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_crear_exitoso, dependientes);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<Dependiente> dependientes = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, dependientes);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Dependiente dependiente=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, dependiente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid Dependiente _dependiente) {
        Dependiente dependiente=servicio.crear(_dependiente);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, dependiente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Dependiente _dependiente) {
        Dependiente dependiente=servicio.actualizar(_dependiente);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, dependiente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorRazonSocial/{razonSocial}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("razonSocial") String razonSocial) {
        List<Dependiente> dependiente=servicio.consultarPorRazonSocial(razonSocial);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, dependiente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/cliente/{clienteId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarClienteID(@PathVariable("clienteId") long clienteId) {
        List<Dependiente> dependiente=servicio.consultarPorCliente(clienteId);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, dependiente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
