package com.proyecto.sicecuador.controladoras.cliente;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathAuxiliar;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.cliente.Dependiente;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.servicios.interf.cliente.IAuxiliarService;
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
@RequestMapping(contexto+pathAuxiliar)
public class DependienteController implements GenericoController<Dependiente> {
    @Autowired
    private IAuxiliarService servicio;

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
        Dependiente dependiente=servicio.obtener(new Dependiente(id)).get();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, dependiente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid Dependiente _auxiliar) {
        Dependiente dependiente=servicio.crear(_auxiliar);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, dependiente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Dependiente _dependiente) {
        Dependiente dependiente=servicio.actualizar(_dependiente);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, dependiente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        Dependiente dependiente=servicio.eliminar(new Dependiente(id));
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_eliminar_exitoso, dependiente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/buscar/razonSocial/{razonSocial}/{clienteId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("razonSocial") String razonSocial,
                                     @PathVariable("clienteId") long cliente_id) {
        List<Dependiente> auxiliar=servicio.consultarRazonSocial(new Dependiente(razonSocial, new Cliente(cliente_id)));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, auxiliar);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/cliente/{clienteId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarClienteID(@PathVariable("clienteId") long clienteId) {
        List<Dependiente> dependiente=servicio.consultarClienteID(new Dependiente(new Cliente(clienteId)));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, dependiente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/importar", headers = "content-type=multipart/*", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> importar(@RequestPart("archivo") MultipartFile archivo) {
        boolean bandera=servicio.importar(archivo);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, bandera);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

}
