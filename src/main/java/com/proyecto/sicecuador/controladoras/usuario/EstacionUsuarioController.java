package com.proyecto.sicecuador.controladoras.usuario;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathEstacionUsuario;
import com.proyecto.sicecuador.controladoras.GenericoController;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.usuario.EstacionUsuario;
import com.proyecto.sicecuador.servicios.interf.usuario.IEstacionUsuarioService;
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
@RequestMapping(contexto+pathEstacionUsuario)
public class EstacionUsuarioController implements GenericoController<EstacionUsuario> {
    @Autowired
    private IEstacionUsuarioService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<EstacionUsuario> estacionesUsuarios = servicio.consultar();
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, estacionesUsuarios);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<EstacionUsuario> estacionesUsuarios = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, estacionesUsuarios);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        EstacionUsuario estacionUsuario=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, estacionUsuario);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid EstacionUsuario _estacionUsuario) {
        EstacionUsuario estacionUsuario=servicio.crear(_estacionUsuario);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, estacionUsuario);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody EstacionUsuario _estacionUsuario) {
        EstacionUsuario estacionUsuario=servicio.actualizar(_estacionUsuario);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, estacionUsuario);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/importar", headers = "content-type=multipart/*", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> importar(@RequestPart("archivo") MultipartFile archivo) {
	    servicio.importar(archivo);
	    Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, null);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }	

}
