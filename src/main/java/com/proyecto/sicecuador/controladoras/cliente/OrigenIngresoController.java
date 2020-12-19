package com.proyecto.sicecuador.controladoras.cliente;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.origen_ingreso;
import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.cliente.OrigenIngreso;
import com.proyecto.sicecuador.servicios.interf.cliente.IOrigenIngresoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping(contexto+origen_ingreso)
public class OrigenIngresoController implements GenericoController<OrigenIngreso> {
    @Autowired
    private IOrigenIngresoService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        try {
            List<OrigenIngreso> origenes_ingresos = servicio.consultar();
            Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, origenes_ingresos);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        try {
            OrigenIngreso origen_ingreso=servicio.obtener(new OrigenIngreso(id)).get();
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, origen_ingreso);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        } catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid OrigenIngreso _origenIngreso) {
        try {
            OrigenIngreso origen_ingreso=servicio.crear(_origenIngreso);
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, origen_ingreso);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody OrigenIngreso _OrigenIngreso) {
        try {
            OrigenIngreso origen_ingreso=servicio.actualizar(_OrigenIngreso);
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, origen_ingreso);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        try {
            OrigenIngreso origen_ingreso=servicio.eliminar(new OrigenIngreso(id));
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, origen_ingreso);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping(value = "/buscar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscar(@RequestBody OrigenIngreso origen_ingreso) {
        try {
        	List<OrigenIngreso> origenes_ingresos=servicio.buscar(origen_ingreso);
            Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, origenes_ingresos);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/importar", headers = "content-type=multipart/*", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> importar(@RequestPart("archivo") MultipartFile archivo) {
        try {
            boolean bandera=servicio.importar(archivo);
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, bandera);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
