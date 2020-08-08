package com.proyecto.sicecuador.controladoras.recaudacion;

import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.recaudacion.Amortizacion;
import com.proyecto.sicecuador.modelos.recaudacion.Banco;
import com.proyecto.sicecuador.modelos.recaudacion.Credito;
import com.proyecto.sicecuador.modelos.recaudacion.RangoCrediticio;
import com.proyecto.sicecuador.servicios.interf.recaudacion.ICreditoService;
import com.proyecto.sicecuador.servicios.interf.recaudacion.IRangoCrediticioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sicecuador/credito")
public class CreditoController implements GenericoController<Credito> {
    @Autowired
    private ICreditoService servicio;
    @Autowired
    private IRangoCrediticioService servicio_rango_crediticio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        try {
            List<Credito> creditos=servicio.consultar();
            Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, creditos);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        try {
            Credito credito=servicio.obtener(new Credito(id)).get();
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, credito);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid Credito _credito, BindingResult bindig_result) {
        try {
            Credito credito=servicio.crear(_credito);
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, credito);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Credito _credito) {
        try {
            Credito credito=servicio.actualizar(_credito);
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, credito);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        try {
            Credito credito=servicio.eliminar(new Credito(id));
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, credito);
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

    @GetMapping(value = "/construir", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> construir(@RequestParam double saldo, @RequestParam String periodicidad,
                                       @RequestParam int periodicidad_numero, @RequestParam int periodicidad_total,
                                       @RequestParam long cuotas, @RequestParam Date fecha_primera_cuota, @RequestParam String tipo, @RequestParam boolean sin_intereses ) {
        try {
            RangoCrediticio rango_crediticio=servicio_rango_crediticio.obtenerSaldo(saldo).get();
            double tasa_interes_anual=rango_crediticio.getTasa_interes_anual();
            double tasa_periodo=Math.rint((rango_crediticio.getTasa_interes_anual()/periodicidad_total)*100d)/100d;
            Optional<Credito> credito=servicio.construir(new Credito(null, saldo, tasa_interes_anual, periodicidad_numero, periodicidad,
            periodicidad_total, tasa_periodo, cuotas,fecha_primera_cuota, null,0, tipo, sin_intereses, null));
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, credito);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
