package com.proyecto.sicecuador.controladoras.recaudacion;

import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.recaudacion.RangoCrediticio;
import com.proyecto.sicecuador.modelos.recaudacion.Recaudacion;
import com.proyecto.sicecuador.servicios.interf.recaudacion.ICreditoService;
import com.proyecto.sicecuador.servicios.interf.recaudacion.IRangoCrediticioService;
import com.proyecto.sicecuador.servicios.interf.recaudacion.IRecaudacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sicecuador/recaudacion")
public class RecaudacionController implements GenericoController<Recaudacion> {
    @Autowired
    private IRecaudacionService servicio;
    @Autowired
    private ICreditoService servicio_credito;
    @Autowired
    private IRangoCrediticioService servicio_rango_crediticio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        try {
            List<Recaudacion> recaudaciones=servicio.consultar();
            Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, recaudaciones);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        try {
            Recaudacion recaudacion=servicio.obtener(new Recaudacion(id)).get();
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, recaudacion);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid Recaudacion _recaudacion) {
        try {
            _recaudacion.normalizar();
            double diferencia= _recaudacion.getFactura().getTotal_con_descuento()-_recaudacion.getTotal();
            if (diferencia>0){
                _recaudacion.getCredito().setSaldo(diferencia);
                _recaudacion.setTotal(_recaudacion.getTotal()+diferencia);
            }
            RangoCrediticio rango_crediticio=servicio_rango_crediticio.obtenerSaldo(_recaudacion.getCredito().getSaldo()).get();
            _recaudacion.getCredito().setTasa_interes_anual(rango_crediticio.getTasa_interes_anual());
            double tasa_periodo=Math.rint((rango_crediticio.getTasa_interes_anual()/_recaudacion.getCredito().getPeriodicidad_total())*100d)/100d;
            _recaudacion.getCredito().setTasa_periodo(tasa_periodo);
            _recaudacion.setCredito(servicio_credito.construir(_recaudacion.getCredito()).get());
            Recaudacion recaudacion=servicio.crear(_recaudacion);
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, recaudacion);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Recaudacion _recaudacion) {
        try {
            Recaudacion recaudacion=servicio.actualizar(_recaudacion);
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, recaudacion);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        try {
            Recaudacion recaudacion=servicio.eliminar(new Recaudacion(id));
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, recaudacion);
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
}
