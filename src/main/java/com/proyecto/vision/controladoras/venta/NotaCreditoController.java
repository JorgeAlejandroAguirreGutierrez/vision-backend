package com.proyecto.vision.controladoras.venta;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.venta.NotaCredito;
import com.proyecto.vision.servicios.interf.venta.INotaCreditoService;
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
import static com.proyecto.vision.controladoras.Endpoints.pathNotaCredito;

@RestController
@RequestMapping(contexto+pathNotaCredito)
public class NotaCreditoController {
    @Autowired
    private INotaCreditoService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<NotaCredito> notasCreditos = servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, notasCreditos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
        List<NotaCredito> notasCreditos = servicio.consultarPorEstado(estado);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, notasCreditos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresa/{empresaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId) {
        List<NotaCredito> notasCreditos = servicio.consultarPorEmpresa(empresaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, notasCreditos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresaYEstado/{empresaId}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresaYEstado(@PathVariable("empresaId") long empresaId, @PathVariable("estado") String estado) {
        List<NotaCredito> notasCreditos = servicio.consultarPorEmpresaYEstado(empresaId, estado);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, notasCreditos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        NotaCredito notaCredito = servicio.obtener(id);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_obtener_exitoso, notaCredito);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody NotaCredito _notaCredito) {
        NotaCredito notaCredito = servicio.crear(_notaCredito);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_crear_exitoso, notaCredito);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody NotaCredito _notaCredito) {
        NotaCredito notaCredito = servicio.actualizar(_notaCredito);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_actualizar_exitoso, notaCredito);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PatchMapping(value = "/anular", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> anular(@RequestBody NotaCredito _notaCredito) {
        NotaCredito notaCredito = servicio.anular(_notaCredito);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_anular_exitoso, notaCredito);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/calcularOperacion", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> calcularOperacion(@RequestBody NotaCredito _notaCredito) {
        NotaCredito notaCredito = servicio.calcularOperacion(_notaCredito);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_calcular_exitoso, notaCredito);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/calcular", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> calcular(@RequestBody NotaCredito _notaCredito) {
        NotaCredito notaCredito = servicio.calcular(_notaCredito);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_calcular_exitoso, notaCredito);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "obtenerPorFactura/{facturaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerPorFactura(@PathVariable("facturaId") long facturaId) {
        NotaCredito notaCredito = servicio.obtenerPorFactura(facturaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_obtener_exitoso, notaCredito);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
