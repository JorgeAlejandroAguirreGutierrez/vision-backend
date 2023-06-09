package com.proyecto.sicecuador.controladoras.cliente;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathCalificacionCliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.cajaBanco.CuentaPropia;
import com.proyecto.sicecuador.modelos.cliente.CalificacionCliente;
import com.proyecto.sicecuador.modelos.cliente.Segmento;
import com.proyecto.sicecuador.servicios.interf.cliente.ICalificacionClienteService;
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
@RequestMapping(contexto+pathCalificacionCliente)
public class CalificacionClienteController implements GenericoController<CalificacionCliente> {
    @Autowired
    private ICalificacionClienteService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<CalificacionCliente> calificacionesClientes=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, calificacionesClientes);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
	    List<CalificacionCliente> calificacionesClientes = servicio.consultarPorEstado(estado);
	    Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, calificacionesClientes);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresa/{empresaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId) {
        List<CalificacionCliente> calificacionesClientes = servicio.consultarPorEmpresa(empresaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, calificacionesClientes);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresaYEstado/{empresaId}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresaYEstado(@PathVariable("empresaId") long empresaId, @PathVariable("estado") String estado) {
        List<CalificacionCliente> calificacionesClientes = servicio.consultarPorEmpresaYEstado(empresaId, estado);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, calificacionesClientes);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<CalificacionCliente> calificacionesClientes = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, calificacionesClientes);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        CalificacionCliente calificacionCliente=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, calificacionCliente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid CalificacionCliente _calificacionCliente) {
        CalificacionCliente calificacionCliente=servicio.crear(_calificacionCliente);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, calificacionCliente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody CalificacionCliente _calificacionCliente) {
        CalificacionCliente calificacionCliente=servicio.actualizar(_calificacionCliente);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, calificacionCliente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody CalificacionCliente _calificacionCliente) {
    	CalificacionCliente calificacionCliente=servicio.activar(_calificacionCliente);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_activar_exitoso, calificacionCliente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody CalificacionCliente _calificacionCliente) {
    	CalificacionCliente calificacionCliente=servicio.inactivar(_calificacionCliente);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_inactivar_exitoso, calificacionCliente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
