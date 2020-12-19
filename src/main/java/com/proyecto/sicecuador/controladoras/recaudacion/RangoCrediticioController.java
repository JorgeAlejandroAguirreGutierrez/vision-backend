package com.proyecto.sicecuador.controladoras.recaudacion;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.rango_crediticio;
import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.recaudacion.RangoCrediticio;
import com.proyecto.sicecuador.servicios.interf.recaudacion.IRangoCrediticioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(contexto+rango_crediticio)
public class RangoCrediticioController implements GenericoController<RangoCrediticio> {
    @Autowired
    private IRangoCrediticioService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        try {
            List<RangoCrediticio> rangos_crediticios=servicio.consultar();
            Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, rangos_crediticios);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        try {
            Optional<RangoCrediticio> rango_crediticio=servicio.obtener(new RangoCrediticio(id));
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, rango_crediticio);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid RangoCrediticio _rango_crediticio) {
        try {
            RangoCrediticio rango_crediticio=servicio.crear(_rango_crediticio);
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, rango_crediticio);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody RangoCrediticio _rango_crediticio) {
        try {
            RangoCrediticio rango_crediticio=servicio.actualizar(_rango_crediticio);
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, rango_crediticio);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        try {
            RangoCrediticio rango_crediticio=servicio.eliminar(new RangoCrediticio(id));
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, rango_crediticio);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> importar(MultipartFile file) {
        return null;
    }

    @GetMapping(value = "/saldo/{saldo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerSaldo(@PathVariable("saldo") double saldo) {
        try {
            Optional<RangoCrediticio> rango_crediticio=servicio.obtenerSaldo(saldo);
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, rango_crediticio);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
