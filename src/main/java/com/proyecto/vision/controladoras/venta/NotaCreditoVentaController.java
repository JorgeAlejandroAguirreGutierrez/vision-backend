package com.proyecto.vision.controladoras.venta;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.inventario.Bodega;
import com.proyecto.vision.modelos.venta.NotaCreditoVenta;
import com.proyecto.vision.servicios.interf.venta.INotaCreditoVentaService;
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
import static com.proyecto.vision.controladoras.Endpoints.pathNotaCreditoVenta;

@RestController
@RequestMapping(contexto+pathNotaCreditoVenta)
public class NotaCreditoVentaController {
    @Autowired
    private INotaCreditoVentaService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<NotaCreditoVenta> notasCreditosVentas=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, notasCreditosVentas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
        List<NotaCreditoVenta> notasCreditosVentas = servicio.consultarPorEstado(estado);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, notasCreditosVentas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresa/{empresaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId) {
        List<NotaCreditoVenta> notasCreditosVentas = servicio.consultarPorEmpresa(empresaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, notasCreditosVentas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresaYEstado/{empresaId}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId, @PathVariable("estado") String estado) {
        List<NotaCreditoVenta> notasCreditosVentas = servicio.consultarPorEmpresaYEstado(empresaId, estado);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, notasCreditosVentas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
        Page<NotaCreditoVenta> notasCreditosVentas = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, notasCreditosVentas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        NotaCreditoVenta notaCreditoVenta = servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_obtener_exitoso, notaCreditoVenta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody NotaCreditoVenta _notaCreditoVenta) {
        NotaCreditoVenta notaCreditoVenta = servicio.crear(_notaCreditoVenta);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_crear_exitoso, notaCreditoVenta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody NotaCreditoVenta _notaCreditoVenta) {
        NotaCreditoVenta notaCreditoVenta = servicio.actualizar(_notaCreditoVenta);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_actualizar_exitoso, notaCreditoVenta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody NotaCreditoVenta _notaCreditoVenta) {
        NotaCreditoVenta notaCreditoVenta = servicio.activar(_notaCreditoVenta);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_activar_exitoso, notaCreditoVenta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody NotaCreditoVenta _notaCreditoVenta) {
        NotaCreditoVenta notaCreditoVenta = servicio.inactivar(_notaCreditoVenta);
        Respuesta respuesta= new Respuesta(true, Constantes.mensaje_inactivar_exitoso, notaCreditoVenta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/calcular", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> calcular(@RequestBody NotaCreditoVenta _notaCreditoVenta) {
        NotaCreditoVenta notaCreditoVenta = servicio.calcular(_notaCreditoVenta);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_calcular_exitoso, notaCreditoVenta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "obtenerPorFactura/{facturaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerPorFactura(@PathVariable("facturaId") long facturaId) {
        NotaCreditoVenta notaCreditoVenta = servicio.obtenerPorFactura(facturaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_obtener_exitoso, notaCreditoVenta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}