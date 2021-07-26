package com.proyecto.sicecuador.controladoras.contabilidad;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathAfectacionContable;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.contabilidad.AfectacionContable;
import com.proyecto.sicecuador.servicios.interf.contabilidad.IAfectacionContableService;
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
import java.util.Optional;

@RestController
@RequestMapping(contexto+pathAfectacionContable)
public class AfectacionContableController implements GenericoController<AfectacionContable> {
    @Autowired
    private IAfectacionContableService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
	    List<AfectacionContable> afectaciones_contables=servicio.consultar();
	    Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, afectaciones_contables);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<AfectacionContable> afectaciones_contables = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, afectaciones_contables);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Optional<AfectacionContable> afectaciones_contables=servicio.obtener(new AfectacionContable(id));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, afectaciones_contables);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid AfectacionContable _afectacionContable) {
        AfectacionContable afectacionContable=servicio.crear(_afectacionContable);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, afectacionContable);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody AfectacionContable _afectacionContable) {
        AfectacionContable afectacionContable=servicio.actualizar(_afectacionContable);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, afectacionContable);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        AfectacionContable afectacionContable=servicio.eliminar(new AfectacionContable(id));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, afectacionContable);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/buscar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscar(@RequestBody AfectacionContable afectacionContable) {
    	List<AfectacionContable> afectaciones_contables=servicio.buscar(afectacionContable);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, afectaciones_contables);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> importar(MultipartFile file) {
        return null;
    }
}