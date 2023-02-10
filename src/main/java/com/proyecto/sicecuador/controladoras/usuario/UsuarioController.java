package com.proyecto.sicecuador.controladoras.usuario;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathUsuario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.usuario.Usuario;
import com.proyecto.sicecuador.servicios.interf.usuario.IUsuarioService;
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
@RequestMapping(contexto+pathUsuario)
public class UsuarioController implements GenericoController<Usuario> {
    @Autowired
    private IUsuarioService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<Usuario> usuarios=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, usuarios);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/consultarActivos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarActivos() {
	    List<Usuario> usuarios= servicio.consultarActivos();
	    Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, usuarios);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<Usuario> usuarios = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, usuarios);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Usuario usuario=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, usuario);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/obtenerPorApodo/{apodo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("apodo") String apodo) {
        Usuario usuario=servicio.obtenerPorApodo(apodo);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, usuario);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody Usuario _usuario) {
        Usuario usuario=servicio.crear(_usuario);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, usuario);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Usuario _usuario) {
        Usuario usuario=servicio.actualizar(_usuario);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, usuario);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody Usuario _usuario) {
    	Usuario usuario = servicio.activar(_usuario);
        Respuesta respuesta= new Respuesta(true, Constantes.mensaje_activar_exitoso, usuario);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody Usuario _usuario) {
    	Usuario usuario = servicio.inactivar(_usuario);
        Respuesta respuesta= new Respuesta(true, Constantes.mensaje_inactivar_exitoso, usuario);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
