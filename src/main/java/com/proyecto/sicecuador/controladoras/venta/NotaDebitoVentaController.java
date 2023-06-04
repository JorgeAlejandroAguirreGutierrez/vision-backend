package com.proyecto.sicecuador.controladoras.venta;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.inventario.Bodega;
import com.proyecto.sicecuador.modelos.venta.NotaDebitoVenta;
import com.proyecto.sicecuador.modelos.venta.NotaDebitoVentaLinea;
import com.proyecto.sicecuador.servicios.interf.venta.INotaDebitoVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathNotaDebitoVenta;

@RestController
@RequestMapping(contexto+pathNotaDebitoVenta)
public class NotaDebitoVentaController {
    @Autowired
    private INotaDebitoVentaService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<NotaDebitoVenta> notasDebitosVentas = servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, notasDebitosVentas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarActivos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarActivos() {
        List<NotaDebitoVenta> notasDebitosVentas= servicio.consultarActivos();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, notasDebitosVentas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresa/{empresaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId) {
        List<NotaDevitoVenta> notasDebitosVentas = servicio.consultarPorEmpresa(empresaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, notasDebitosVentas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
        Page<NotaDebitoVenta> notasDebitosVentas = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, notasDebitosVentas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        NotaDebitoVenta notaDebitoVenta = servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_obtener_exitoso, notaDebitoVenta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody NotaDebitoVenta _notaDebitoVenta) {
        NotaDebitoVenta notaDebitoVenta = servicio.crear(_notaDebitoVenta);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_crear_exitoso, notaDebitoVenta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody NotaDebitoVenta _notaDebitoVenta) {
        NotaDebitoVenta notaDebitoVenta = servicio.actualizar(_notaDebitoVenta);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_actualizar_exitoso, notaDebitoVenta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody NotaDebitoVenta _notaDebitoVenta) {
        NotaDebitoVenta notaDebitoVenta = servicio.activar(_notaDebitoVenta);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_activar_exitoso, notaDebitoVenta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody NotaDebitoVenta _notaDebitoVenta) {
        NotaDebitoVenta notaDebitoVenta = servicio.inactivar(_notaDebitoVenta);
        Respuesta respuesta= new Respuesta(true, Constantes.mensaje_inactivar_exitoso, notaDebitoVenta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/calcular", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> calcular(@RequestBody NotaDebitoVenta _notaDebitoVenta) {
        NotaDebitoVenta notaDebitoVenta = servicio.calcular(_notaDebitoVenta);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_calcular_exitoso, notaDebitoVenta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @PostMapping(value = "/calcularLinea", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> calcularLinea(@RequestBody NotaDebitoVentaLinea _notaDebitoVentaLinea) {
        NotaDebitoVentaLinea notaDebitoVentaLinea = servicio.calcularLinea(_notaDebitoVentaLinea);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_calcular_exitoso, notaDebitoVentaLinea);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/calcularRecaudacion", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> calcularRecaudacion(@RequestBody NotaDebitoVenta _notaDebitoVenta) {
        NotaDebitoVenta notaDebitoVenta = servicio.calcularRecaudacion(_notaDebitoVenta);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_calcular_exitoso, notaDebitoVenta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "obtenerPorFactura/{facturaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerPorFactura(@PathVariable("facturaId") long facturaId) {
        NotaDebitoVenta notaDebitoVenta = servicio.obtenerPorFactura(facturaId);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_obtener_exitoso, notaDebitoVenta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
