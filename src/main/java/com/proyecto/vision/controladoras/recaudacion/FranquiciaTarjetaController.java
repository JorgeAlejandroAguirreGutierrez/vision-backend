package com.proyecto.vision.controladoras.recaudacion;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathFranquiciaTarjeta;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.controladoras.GenericoController;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.recaudacion.FranquiciaTarjeta;
import com.proyecto.vision.servicios.interf.recaudacion.IFranquiciaTarjetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping(contexto+pathFranquiciaTarjeta)
public class FranquiciaTarjetaController implements GenericoController<FranquiciaTarjeta> {
    @Autowired
    private IFranquiciaTarjetaService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<FranquiciaTarjeta> franquiciasTarjetas=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, franquiciasTarjetas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
	    List<FranquiciaTarjeta> franquiciasTarjetas = servicio.consultarPorEstado(estado);
	    Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, franquiciasTarjetas);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<FranquiciaTarjeta> franquiciasTarjetas = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, franquiciasTarjetas);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        FranquiciaTarjeta franquiciaTarjeta=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, franquiciaTarjeta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid FranquiciaTarjeta _franquiciaTarjeta) {
        FranquiciaTarjeta franquiciaTarjeta=servicio.crear(_franquiciaTarjeta);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, franquiciaTarjeta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody FranquiciaTarjeta _franquiciaTarjeta) {
        FranquiciaTarjeta franquiciaTarjeta=servicio.actualizar(_franquiciaTarjeta);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, franquiciaTarjeta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody FranquiciaTarjeta _franquiciaTarjeta) {
    	FranquiciaTarjeta franquiciaTarjeta= servicio.activar(_franquiciaTarjeta);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_activar_exitoso, franquiciaTarjeta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody FranquiciaTarjeta _franquiciaTarjeta) {
    	FranquiciaTarjeta franquiciaTarjeta = servicio.inactivar(_franquiciaTarjeta);
        Respuesta respuesta= new Respuesta(true, Constantes.mensaje_inactivar_exitoso, franquiciaTarjeta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
