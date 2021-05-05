package com.proyecto.sicecuador.controladoras.recaudacion;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathOperadorTarjeta;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.recaudacion.OperadorTarjeta;
import com.proyecto.sicecuador.servicios.interf.recaudacion.IOperadorTarjetaService;
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
@RequestMapping(contexto+pathOperadorTarjeta)
public class OperadorTarjetaController implements GenericoController<OperadorTarjeta> {

    @Autowired
    private IOperadorTarjetaService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<OperadorTarjeta> operadores_tarjetas=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, operadores_tarjetas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<OperadorTarjeta> operadores_tarjetas = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, operadores_tarjetas);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/tipo/{tipo}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar(@PathVariable("tipo") String tipo) {
        List<OperadorTarjeta> operadores_tarjetas=servicio.consultarTipo(new OperadorTarjeta(tipo));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, operadores_tarjetas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        OperadorTarjeta operador_tarjeta=servicio.obtener(new OperadorTarjeta(id)).get();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, operador_tarjeta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid OperadorTarjeta _operador_tarjeta) {
        OperadorTarjeta operador_tarjeta=servicio.crear(_operador_tarjeta);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, operador_tarjeta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody OperadorTarjeta _operador_tarjeta) {
        OperadorTarjeta operador_tarjeta=servicio.actualizar(_operador_tarjeta);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, operador_tarjeta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        OperadorTarjeta operador_tarjeta=servicio.eliminar(new OperadorTarjeta(id));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, operador_tarjeta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> importar(MultipartFile file) {
        return null;
    }
}
