package com.proyecto.sicecuador.controladoras.recaudacion;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathRecaudacion;

import com.proyecto.sicecuador.Constantes;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(contexto+pathRecaudacion)
public class RecaudacionController implements GenericoController<Recaudacion> {
    @Autowired
    private IRecaudacionService servicio;
    @Autowired
    private ICreditoService servicio_credito;
    @Autowired
    private IRangoCrediticioService servicio_rango_crediticio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<Recaudacion> recaudaciones=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, recaudaciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Recaudacion recaudacion=servicio.obtener(new Recaudacion(id)).get();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, recaudacion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid Recaudacion _recaudacion) {
        _recaudacion.normalizar();
        double diferencia= _recaudacion.getFactura().getTotalConDescuento()-_recaudacion.getTotal();
        if (diferencia>0){
            _recaudacion.getCredito().setSaldo(diferencia);
            _recaudacion.setTotal(_recaudacion.getTotal()+diferencia);
        }
        RangoCrediticio rango_crediticio=servicio_rango_crediticio.obtenerSaldo(_recaudacion.getCredito().getSaldo()).get();
        _recaudacion.getCredito().setTasaInteresAnual(rango_crediticio.getTasaInteresAnual());
        double tasa_periodo=Math.rint((rango_crediticio.getTasaInteresAnual()/_recaudacion.getCredito().getPeriodicidadTotal())*100d)/100d;
        _recaudacion.getCredito().setTasaPeriodo(tasa_periodo);
        _recaudacion.setCredito(servicio_credito.construir(_recaudacion.getCredito()).get());
        Recaudacion recaudacion=servicio.crear(_recaudacion);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, recaudacion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Recaudacion _recaudacion) {
        Recaudacion recaudacion=servicio.actualizar(_recaudacion);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, recaudacion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        Recaudacion recaudacion=servicio.eliminar(new Recaudacion(id));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, recaudacion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> importar(MultipartFile file) {
        return null;
    }
}
