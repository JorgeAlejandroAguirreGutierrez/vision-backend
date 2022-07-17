package com.proyecto.sicecuador.controladoras.cliente;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathAuxiliar;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.cliente.Auxiliar;
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
public class AuxiliarController implements GenericoController<Auxiliar> {
    @Autowired
    private IAuxiliarService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<Auxiliar> categorias_clientes=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_crear_exitoso, categorias_clientes);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<Auxiliar> auxiliares = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, auxiliares);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Auxiliar auxiliar=servicio.obtener(new Auxiliar(id)).get();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, auxiliar);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid Auxiliar _auxiliar) {
        Auxiliar auxiliar=servicio.crear(_auxiliar);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, auxiliar);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Auxiliar _auxiliar) {
        Auxiliar auxiliar=servicio.actualizar(_auxiliar);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, auxiliar);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        Auxiliar auxiliar=servicio.eliminar(new Auxiliar(id));
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_eliminar_exitoso, auxiliar);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/buscar/razonSocial/{razonSocial}/{clienteId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("razonSocial") String razon_social,
                                     @PathVariable("clienteId") long cliente_id) {
        List<Auxiliar> auxiliar=servicio.consultarRazonSocial(new Auxiliar(razon_social, new Cliente(cliente_id)));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, auxiliar);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/cliente/{clienteId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarClienteID(@PathVariable("clienteId") long clienteId) {
        List<Auxiliar> auxiliar=servicio.consultarClienteID(new Auxiliar(new Cliente(clienteId)));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, auxiliar);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/importar", headers = "content-type=multipart/*", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> importar(@RequestPart("archivo") MultipartFile archivo) {
        boolean bandera=servicio.importar(archivo);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, bandera);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

}
