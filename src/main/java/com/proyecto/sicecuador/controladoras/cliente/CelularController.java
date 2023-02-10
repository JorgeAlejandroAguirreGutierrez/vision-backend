package com.proyecto.sicecuador.controladoras.cliente;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathCelular;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.cliente.Celular;
import com.proyecto.sicecuador.servicios.interf.cliente.ICelularService;
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
@RequestMapping(contexto+pathCelular)
public class CelularController implements GenericoController<Celular> {
    @Autowired
    private ICelularService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<Celular> celulares=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, celulares);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<Celular> celulares = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, celulares);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Celular celular=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, celular);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid Celular _celular) {
        Celular celular=servicio.crear(_celular);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, celular);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Celular _celular) {
        Celular celular=servicio.actualizar(_celular);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, celular);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
