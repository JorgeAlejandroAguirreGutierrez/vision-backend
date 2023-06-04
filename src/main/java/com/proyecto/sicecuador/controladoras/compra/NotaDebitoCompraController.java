package com.proyecto.sicecuador.controladoras.compra;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.cajaBanco.Banco;
import com.proyecto.sicecuador.modelos.compra.NotaDebitoCompra;
import com.proyecto.sicecuador.modelos.compra.NotaDebitoCompraLinea;
import com.proyecto.sicecuador.servicios.interf.compra.INotaDebitoCompraService;
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
import static com.proyecto.sicecuador.controladoras.Endpoints.pathNotaDebitoCompra;

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

    @GetMapping(value = "/consultarActivos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarActivos() {
        List<NotaDebitoCompra> notasDebitosCompras = servicio.consultarActivos();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, notasDebitosCompras);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresa/{empresaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId) {
        List<NotaDebitoCompra> notasDebitosCompras = servicio.consultarPorEmpresa(empresaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, notasDebitosCompras);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
        Page<NotaDebitoCompra> notasDebitosCompras = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
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

    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody NotaDebitoCompra _notaDebitoCompra) {
        NotaDebitoCompra notaDebitoCompra = servicio.activar(_notaDebitoCompra);
        Respuesta respuesta= new Respuesta(true, Constantes.mensaje_activar_exitoso, notaDebitoCompra);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody NotaDebitoCompra _notaDebitoCompra) {
        NotaDebitoCompra notaDebitoCompra = servicio.inactivar(_notaDebitoCompra);
        Respuesta respuesta= new Respuesta(true, Constantes.mensaje_inactivar_exitoso, notaDebitoCompra);
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
