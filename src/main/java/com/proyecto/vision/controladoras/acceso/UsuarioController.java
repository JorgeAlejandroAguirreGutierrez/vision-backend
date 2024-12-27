package com.proyecto.vision.controladoras.acceso;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathUsuario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.controladoras.GenericoController;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.acceso.Usuario;
import com.proyecto.vision.servicios.interf.acceso.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping(contexto+pathUsuario)
public class UsuarioController implements GenericoController<Usuario> {
    @Autowired
    private IUsuarioService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<Usuario> usuarios = servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, usuarios);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresa/{empresaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId) {
        List<Usuario> usuarios = servicio.consultarPorEmpresa(empresaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, usuarios);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
	    List<Usuario> usuarios = servicio.consultarPorEstado(estado);
	    Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, usuarios);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Usuario usuario = servicio.obtener(id);
        Respuesta respuesta = new Respuesta(true,Constantes.mensaje_obtener_exitoso, usuario);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/obtenerPorApodoYEstado/{apodo}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerPorApodoYEmpresaYEstado(@PathVariable("apodo") String apodo, @PathVariable("estado") String estado) {
        Usuario usuario = servicio.obtenerPorApodoYEstado(apodo, estado);
        Respuesta respuesta = new Respuesta(true,Constantes.mensaje_obtener_exitoso, usuario);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody Usuario _usuario) {
        Usuario usuario = servicio.crear(_usuario);
        Respuesta respuesta = new Respuesta(true,Constantes.mensaje_crear_exitoso, usuario);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Usuario _usuario) {
        Usuario usuario = servicio.actualizar(_usuario);
        Respuesta respuesta = new Respuesta(true,Constantes.mensaje_actualizar_exitoso, usuario);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody Usuario _usuario) {
    	Usuario usuario = servicio.activar(_usuario);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_activar_exitoso, usuario);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody Usuario _usuario) {
    	Usuario usuario = servicio.inactivar(_usuario);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_inactivar_exitoso, usuario);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/bajarAvatar/{usuarioId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> bajarAvatar(@PathVariable("usuarioId") long usuarioId) throws MalformedURLException {
        Resource file = servicio.bajarAvatar(usuarioId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
