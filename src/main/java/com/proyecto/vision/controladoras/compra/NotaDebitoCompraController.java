package com.proyecto.vision.controladoras.compra;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.compra.NotaDebitoCompra;
import com.proyecto.vision.modelos.compra.NotaDebitoCompraLinea;
import com.proyecto.vision.servicios.interf.compra.INotaDebitoCompraService;
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
import static com.proyecto.vision.controladoras.Endpoints.pathNotaDebitoCompra;

@RestController
@RequestMapping(contexto+pathNotaDebitoCompra)
public class NotaDebitoCompraController {
    @Autowired
    private INotaDebitoCompraService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<NotaDebitoCompra> notasDebitosCompras=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, notasDebitosCompras);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
        List<NotaDebitoCompra> notasDebitosCompras = servicio.consultarPorEstado(estado);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, notasDebitosCompras);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresa/{empresaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId) {
        List<NotaDebitoCompra> notasDebitosCompras = servicio.consultarPorEmpresa(empresaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, notasDebitosCompras);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        NotaDebitoCompra notaDebitoCompra = servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_obtener_exitoso, notaDebitoCompra);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "obtenerPorFacturaCompra/{facturaCompraId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerPorFacturaCompra(@PathVariable("facturaCompraId") long facturaCompraId) {
        NotaDebitoCompra notaDebitoCompra = servicio.obtenerPorFacturaCompra(facturaCompraId);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_obtener_exitoso, notaDebitoCompra);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody NotaDebitoCompra _notaDebitoCompra) {
        NotaDebitoCompra notaDebitoCompra = servicio.crear(_notaDebitoCompra);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_crear_exitoso, notaDebitoCompra);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody NotaDebitoCompra _notaDebitoCompra) {
        NotaDebitoCompra notaDebitoCompra = servicio.actualizar(_notaDebitoCompra);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_actualizar_exitoso, notaDebitoCompra);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PatchMapping(value = "/anular", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> anular(@RequestBody NotaDebitoCompra _notaDebitoCompra) {
        NotaDebitoCompra notaDebitoCompra = servicio.anular(_notaDebitoCompra);
        Respuesta respuesta= new Respuesta(true, Constantes.mensaje_anular_exitoso, notaDebitoCompra);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/calcular", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> calcular(@RequestBody NotaDebitoCompra _notaDebitoCompra) {
        NotaDebitoCompra notaDebitoCompra = servicio.calcular(_notaDebitoCompra);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_calcular_exitoso, notaDebitoCompra);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @PostMapping(value = "/calcularLinea", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> calcularLinea(@RequestBody NotaDebitoCompraLinea _notaDebitoCompraLinea) {
        NotaDebitoCompraLinea notaDebitoCompraLinea = servicio.calcularLinea(_notaDebitoCompraLinea);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_calcular_exitoso, notaDebitoCompraLinea);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}