package com.proyecto.vision.controladoras.venta;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.controladoras.GenericoController;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.venta.Factura;
import com.proyecto.vision.modelos.venta.FacturaLinea;
import com.proyecto.vision.servicios.interf.venta.IFacturaService;
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
import static com.proyecto.vision.controladoras.Endpoints.pathFactura;

@RestController
@RequestMapping(contexto+pathFactura)
public class FacturaController implements GenericoController<Factura> {
    @Autowired
    private IFacturaService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<Factura> facturas=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, facturas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody Factura _factura) {
        Factura factura=servicio.crear(_factura);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, factura);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Factura _factura) {
        Factura factura = servicio.actualizar(_factura);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, factura);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PatchMapping(value = "/recaudar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> recaudar(@RequestBody Factura _factura) {
        Factura factura = servicio.recaudar(_factura);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_recaudacion_exitosa, factura);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PatchMapping(value = "/anular", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> anular(@RequestBody Factura _factura) {
        Factura factura = servicio.anular(_factura);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_anular_exitoso, factura);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/calcular", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> calcular(@RequestBody Factura _factura) {
        Factura factura=servicio.calcular(_factura);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_calcular_exitoso, factura);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/calcularLinea", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> calcularLinea(@RequestBody FacturaLinea _facturaLinea) {
        FacturaLinea facturaLinea = servicio.calcularLinea(_facturaLinea);
        Respuesta respuesta = new Respuesta(true,Constantes.mensaje_calcular_exitoso, facturaLinea);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresa/{empresaId}/{cantidad}/{pagina}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId, @PathVariable("cant") int cant, @PathVariable("pagina") int pagina) {
        List<Factura> facturas = servicio.consultarPorEmpresa(empresaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, facturas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEstado/{estado}/{cantidad}/{pagina}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado, @PathVariable("cant") int cant, @PathVariable("pagina") int pagina) {
        List<Factura> facturas= servicio.consultarPorEstado(estado);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, facturas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresaYEstado/{empresaId}/{estado}/{cantidad}/{pagina}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresaYEstado(@PathVariable("empresaId") long empresaId, @PathVariable("estado") String estado,
                                                        @PathVariable("cant") int cant, @PathVariable("pagina") int pagina) {
        List<Factura> facturas = servicio.consultarPorEmpresaYEstado(empresaId, estado);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, facturas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorCliente/{clienteId}/{cantidad}/{pagina}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorCliente(@PathVariable("clienteId") long clienteId, @PathVariable("cant") int cant, @PathVariable("pagina") int pagina) {
        List<Factura> facturas = servicio.consultarPorCliente(clienteId);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, facturas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorClienteYEmpresaYEstado/{clienteId}/{empresaId}/{estado}/{cantidad}/{pagina}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorClienteYEmpresaYEstado(@PathVariable("clienteId") long clienteId, @PathVariable("empresaId") long empresaId, @PathVariable("estado") String estado,
                                                                @PathVariable("cant") int cant, @PathVariable("pagina") int pagina) {
        List<Factura> facturas = servicio.consultarPorClienteYEmpresaYEstado(clienteId, empresaId, estado);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, facturas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorClienteYEstado/{clienteId}/{estado}/{cantidad}/{pagina}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorClienteYEstado(@PathVariable("clienteId") long clienteId, @PathVariable("estado") String estado,
                                                        @PathVariable("cant") int cant, @PathVariable("pagina") int pagina) {
        List<Factura> facturas = servicio.consultarPorClienteYEstado(clienteId, estado);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, facturas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Factura factura=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, factura);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/validarIdentificacion/{identificacion}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> validarIdentificacion(@PathVariable("identificacion") String identificacion) {
        String nombre = servicio.validarIdentificacion(identificacion);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, nombre);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}