package com.proyecto.sicecuador.controladoras.cliente;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathTelefono;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.cliente.Telefono;
import com.proyecto.sicecuador.servicios.interf.cliente.ITelefonoService;
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
@RequestMapping(contexto+pathTelefono)
public class TelefonoController implements GenericoController<Telefono> {
    @Autowired
    private ITelefonoService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<Telefono> telefonos = servicio.consultar();
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, telefonos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<Telefono> telefonos = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, telefonos);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Telefono telefono=servicio.obtener(new Telefono(id)).get();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, telefono);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid Telefono _telefono) {
        Telefono telefono=servicio.crear(_telefono);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, telefono);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Telefono _Telefono) {
        Telefono telefono=servicio.actualizar(_Telefono);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, telefono);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        Telefono telefono=servicio.eliminar(new Telefono(id));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, telefono);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/importar", headers = "content-type=multipart/*", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> importar(@RequestPart("archivo") MultipartFile archivo) {
	    boolean bandera=servicio.importar(archivo);
	    Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, bandera);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
