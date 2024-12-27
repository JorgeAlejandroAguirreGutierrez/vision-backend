package com.proyecto.vision.controladoras.contabilidad;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathAfectacionContable;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.controladoras.GenericoController;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.cajaBanco.Banco;
import com.proyecto.vision.modelos.contabilidad.AfectacionContable;
import com.proyecto.vision.servicios.interf.contabilidad.IAfectacionContableService;
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
@RequestMapping(contexto+pathAfectacionContable)
public class AfectacionContableController implements GenericoController<AfectacionContable> {
    @Autowired
    private IAfectacionContableService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
	    List<AfectacionContable> afectacionesContables = servicio.consultar();
	    Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, afectacionesContables);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
	    List<AfectacionContable> afectacionesContables = servicio.consultarPorEstado(estado);
	    Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, afectacionesContables);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        AfectacionContable afectacionesContables=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, afectacionesContables);
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
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody AfectacionContable _afectacionContable) {
    	AfectacionContable afectacionContable=servicio.activar(_afectacionContable);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_activar_exitoso, afectacionContable);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody AfectacionContable _afectacionContable) {
    	AfectacionContable afectacionContable = servicio.inactivar(_afectacionContable);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_inactivar_exitoso, afectacionContable);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
