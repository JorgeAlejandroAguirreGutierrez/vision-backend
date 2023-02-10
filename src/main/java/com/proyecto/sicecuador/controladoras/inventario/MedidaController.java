package com.proyecto.sicecuador.controladoras.inventario;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathMedida;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.inventario.Medida;
import com.proyecto.sicecuador.servicios.interf.inventario.IMedidaService;
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
@RequestMapping(contexto+pathMedida)
public class MedidaController implements GenericoController<Medida> {
    @Autowired
    private IMedidaService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<Medida> medidas=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, medidas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/consultarActivos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarActivos() {
	    List<Medida> medidas= servicio.consultarActivos();
	    Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, medidas);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<Medida> medidas = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, medidas);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Medida medida=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, medida);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid Medida _medida) {
        Medida medida=servicio.crear(_medida);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, medida);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Medida _medida) {
        Medida medida=servicio.actualizar(_medida);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, medida);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody Medida _medida) {
    	Medida medida= servicio.activar(_medida);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_activar_exitoso, medida);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody Medida _medida) {
    	Medida medida = servicio.inactivar(_medida);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_inactivar_exitoso, medida);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
