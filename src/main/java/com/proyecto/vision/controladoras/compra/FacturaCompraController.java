package com.proyecto.vision.controladoras.compra;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.compra.FacturaCompra;
import com.proyecto.vision.modelos.compra.FacturaCompraLinea;
import com.proyecto.vision.modelos.compra.GrupoProveedor;
import com.proyecto.vision.servicios.interf.compra.IFacturaCompraService;
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
import static com.proyecto.vision.controladoras.Endpoints.pathFacturaCompra;

@RestController
@RequestMapping(contexto+pathFacturaCompra)
public class FacturaCompraController {
    @Autowired
    private IFacturaCompraService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<FacturaCompra> facturasCompras = servicio.consultar();
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, facturasCompras);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
        List<FacturaCompra> facturasCompras = servicio.consultarPorEstado(estado);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, facturasCompras);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresa/{empresaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId) {
        List<FacturaCompra> facturasCompras = servicio.consultarPorEmpresa(empresaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, facturasCompras);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresaYEstado/{empresaId}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresaYEstado(@PathVariable("empresaId") long empresaId, @PathVariable("estado") String estado) {
        List<FacturaCompra> facturasCompras = servicio.consultarPorEmpresaYEstado(empresaId, estado);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, facturasCompras);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorProveedorYEmpresaYEstado/{proveedorId}/{empresaId}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorProveedorYEmpresaYEstado(@PathVariable("proveedorId") long proveedorId, @PathVariable("empresaId") long empresaId, @PathVariable("estado") String estado) {
        List<FacturaCompra> facturasCompras = servicio.consultarPorProveedorYEmpresaYEstado(proveedorId, empresaId, estado);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, facturasCompras);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorProveedorYEmpresaYEstadoDiferente/{proveedorId}/{empresaId}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorProveedorYEmpresaYEstadoDiferente(@PathVariable("proveedorId") long proveedorId, @PathVariable("empresaId") long empresaId, @PathVariable("estado") String estado) {
        List<FacturaCompra> facturasCompras = servicio.consultarPorProveedorYEmpresaYEstadoDiferente( proveedorId, empresaId, estado);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, facturasCompras);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        FacturaCompra facturaCompra=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, facturaCompra);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody FacturaCompra _facturaCompra) {
        FacturaCompra facturaCompra=servicio.crear(_facturaCompra);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, facturaCompra);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody FacturaCompra _facturaCompra) {
        FacturaCompra facturaCompra=servicio.actualizar(_facturaCompra);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, facturaCompra);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PatchMapping(value = "/anular", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> anular(@RequestBody FacturaCompra _facturaCompra) {
        FacturaCompra facturaCompra = servicio.anular(_facturaCompra);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_anular_exitoso, facturaCompra);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/calcular", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> calcular(@RequestBody FacturaCompra _facturaCompra) {
        FacturaCompra facturaCompra = servicio.calcular(_facturaCompra);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_calcular_exitoso, facturaCompra);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/calcularLinea", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> calcularLinea(@RequestBody FacturaCompraLinea _facturaCompraLinea) {
        FacturaCompraLinea facturaCompraLinea = servicio.calcularLinea(_facturaCompraLinea);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_calcular_exitoso, facturaCompraLinea);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/pagar/{facturaCompraId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> pagar(@PathVariable("facturaCompraId") long facturaCompraId) {
        FacturaCompra facturasCompra = servicio.pagar(facturaCompraId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_actualizar_exitoso, facturasCompra);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}