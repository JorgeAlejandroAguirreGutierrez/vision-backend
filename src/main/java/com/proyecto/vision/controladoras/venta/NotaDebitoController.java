package com.proyecto.vision.controladoras.venta;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.venta.NotaDebito;
import com.proyecto.vision.modelos.venta.NotaDebitoLinea;
import com.proyecto.vision.servicios.interf.venta.INotaDebitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathNotaDebito;

@RestController
@RequestMapping(contexto+pathNotaDebito)
public class NotaDebitoController {
    @Autowired
    private INotaDebitoService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<NotaDebito> notasDebitos = servicio.consultar();
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, notasDebitos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEstadoSRI/{estadoSRI}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstadoSRI(@PathVariable("estadoSRI") String estadoSRI) {
        List<NotaDebito> notasDebitos = servicio.consultarPorEstadoSRI(estadoSRI);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, notasDebitos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresa/{empresaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId) {
        List<NotaDebito> notasDebitos = servicio.consultarPorEmpresa(empresaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, notasDebitos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresaYEstadoSRI/{empresaId}/{estadoSRI}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresaYEstadoSRI(@PathVariable("empresaId") long empresaId, @PathVariable("estadoSRI") String estadoSRI) {
        List<NotaDebito> notasDebitos = servicio.consultarPorEmpresaYEstadoSRI(empresaId, estadoSRI);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, notasDebitos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
        Page<NotaDebito> notasDebitos = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, notasDebitos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        NotaDebito notaDebito = servicio.obtener(id);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_obtener_exitoso, notaDebito);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody NotaDebito _notaDebito) {
        NotaDebito notaDebito = servicio.crear(_notaDebito);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_crear_exitoso, notaDebito);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody NotaDebito _notaDebito) {
        NotaDebito notaDebito = servicio.actualizar(_notaDebito);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_actualizar_exitoso, notaDebito);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PatchMapping(value = "/recaudar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> recaudar(@RequestBody NotaDebito _notaDebito) {
        NotaDebito notaDebito = servicio.recaudar(_notaDebito);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_recaudacion_exitosa, notaDebito);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PatchMapping(value = "/anular", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> anular(@RequestBody NotaDebito _notaDebito) {
        NotaDebito notaDebito = servicio.anular(_notaDebito);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_anular_exitoso, notaDebito);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/calcular", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> calcular(@RequestBody NotaDebito _notaDebito) {
        NotaDebito notaDebito = servicio.calcular(_notaDebito);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_calcular_exitoso, notaDebito);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/calcularLinea", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> calcularLinea(@RequestBody NotaDebitoLinea _notaDebitoLinea) {
        NotaDebitoLinea notaDebitoLinea = servicio.calcularLinea(_notaDebitoLinea);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_calcular_exitoso, notaDebitoLinea);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "obtenerPorFactura/{facturaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerPorFactura(@PathVariable("facturaId") long facturaId) {
        NotaDebito notaDebito = servicio.obtenerPorFactura(facturaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_obtener_exitoso, notaDebito);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
