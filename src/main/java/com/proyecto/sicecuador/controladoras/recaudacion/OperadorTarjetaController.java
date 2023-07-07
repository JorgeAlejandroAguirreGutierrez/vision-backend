package com.proyecto.vision.controladoras.recaudacion;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathOperadorTarjeta;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.controladoras.GenericoController;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.recaudacion.OperadorTarjeta;
import com.proyecto.vision.servicios.interf.recaudacion.IOperadorTarjetaService;
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
@RequestMapping(contexto+pathOperadorTarjeta)
public class OperadorTarjetaController implements GenericoController<OperadorTarjeta> {

    @Autowired
    private IOperadorTarjetaService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<OperadorTarjeta> operadoresTarjetas = servicio.consultar();
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, operadoresTarjetas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/consultarPorEstado", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
	    List<OperadorTarjeta> operadoresTarjetas = servicio.consultarPorEstado(estado);
	    Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, operadoresTarjetas);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<OperadorTarjeta> operadoresTarjetas = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, operadoresTarjetas);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorTipo/{tipo}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar(@PathVariable("tipo") String tipo) {
        List<OperadorTarjeta> operadoresTarjetas=servicio.consultarPorTipo(tipo);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, operadoresTarjetas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        OperadorTarjeta operadorTarjeta=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, operadorTarjeta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid OperadorTarjeta _operadorTarjeta) {
        OperadorTarjeta operadorTarjeta=servicio.crear(_operadorTarjeta);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, operadorTarjeta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody OperadorTarjeta _operadorTarjeta) {
        OperadorTarjeta operadorTarjeta=servicio.actualizar(_operadorTarjeta);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, operadorTarjeta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody OperadorTarjeta _operadorTarjeta) {
    	OperadorTarjeta operadorTarjeta = servicio.activar(_operadorTarjeta);
        Respuesta respuesta= new Respuesta(true, Constantes.mensaje_activar_exitoso, operadorTarjeta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody OperadorTarjeta _operadorTarjeta) {
    	OperadorTarjeta operadorTarjeta = servicio.inactivar(_operadorTarjeta);
        Respuesta respuesta= new Respuesta(true, Constantes.mensaje_inactivar_exitoso, operadorTarjeta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
