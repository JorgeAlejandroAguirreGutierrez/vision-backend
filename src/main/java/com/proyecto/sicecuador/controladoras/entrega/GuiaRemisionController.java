package com.proyecto.sicecuador.controladoras.entrega;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathGuiaRemision;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.entrega.GuiaRemision;
import com.proyecto.sicecuador.servicios.interf.entrega.IGuiaRemisionService;
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
@RequestMapping(contexto+pathGuiaRemision)
public class GuiaRemisionController implements GenericoController<GuiaRemision> {
    @Autowired
    private IGuiaRemisionService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<GuiaRemision> guias_remisiones=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, guias_remisiones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<GuiaRemision> guias_remisiones = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, guias_remisiones);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        GuiaRemision guia_remision=servicio.obtener(new GuiaRemision(id)).get();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, guia_remision);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid GuiaRemision _guia_remision) {
        GuiaRemision guia_remision=servicio.crear(_guia_remision);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, guia_remision);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody GuiaRemision _guia_remision) {
        GuiaRemision guia_remision=servicio.actualizar(_guia_remision);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, guia_remision);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        GuiaRemision guia_remision=servicio.eliminar(new GuiaRemision(id));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, guia_remision);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/importar", headers = "content-type=multipart/*", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> importar(MultipartFile archivo) {
        boolean bandera=servicio.importar(archivo);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, bandera);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
