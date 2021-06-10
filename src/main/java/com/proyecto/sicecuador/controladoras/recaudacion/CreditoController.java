package com.proyecto.sicecuador.controladoras.recaudacion;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathCredito;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.recaudacion.Credito;
import com.proyecto.sicecuador.modelos.recaudacion.RangoCrediticio;
import com.proyecto.sicecuador.servicios.interf.recaudacion.ICreditoService;
import com.proyecto.sicecuador.servicios.interf.recaudacion.IRangoCrediticioService;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(contexto+pathCredito)
public class CreditoController implements GenericoController<Credito> {
    @Autowired
    private ICreditoService servicio;
    @Autowired
    private IRangoCrediticioService servicio_rango_crediticio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<Credito> creditos=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, creditos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<Credito> creditos = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, creditos);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Credito credito=servicio.obtener(new Credito(id)).get();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, credito);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid Credito _credito) {
        Credito credito=servicio.crear(_credito);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, credito);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Credito _credito) {
        Credito credito=servicio.actualizar(_credito);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, credito);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        Credito credito=servicio.eliminar(new Credito(id));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, credito);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> importar(MultipartFile file) {
        return null;
    }

    @GetMapping(value = "/construir", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> construir(@RequestParam double saldo, @RequestParam String periodicidad,
                                       @RequestParam int periodicidad_numero, @RequestParam int periodicidad_total,
                                       @RequestParam long cuotas, @RequestParam Date fecha_primera_cuota, @RequestParam String tipo, @RequestParam boolean sin_intereses ) {
        RangoCrediticio rango_crediticio=servicio_rango_crediticio.obtenerSaldo(saldo).get();
        double tasa_interes_anual=rango_crediticio.getTasaInteresAnual();
        double tasa_periodo=Math.rint((rango_crediticio.getTasaInteresAnual()/periodicidad_total)*100d)/100d;
        Optional<Credito> credito=servicio.construir(new Credito(null, saldo, tasa_interes_anual, periodicidad_numero, periodicidad,
        periodicidad_total, tasa_periodo, cuotas,fecha_primera_cuota, null,0, tipo, sin_intereses, null));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, credito);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);

    }
}
