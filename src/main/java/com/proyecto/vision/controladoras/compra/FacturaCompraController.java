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

    @GetMapping(value = "/consultarPorProceso/{proceso}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("proceso") String proceso) {
        List<FacturaCompra> facturasCompras = servicio.consultarPorProceso(proceso);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, facturasCompras);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresa/{empresaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId) {
        List<FacturaCompra> facturasCompras = servicio.consultarPorEmpresa(empresaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, facturasCompras);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresaYProceso/{empresaId}/{proceso}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresaYProceso(@PathVariable("empresaId") long empresaId, @PathVariable("proceso") String proceso) {
        List<FacturaCompra> facturasCompras = servicio.consultarPorEmpresaYProceso(empresaId, proceso);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, facturasCompras);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresaYProveedorYProceso/{empresaId}/{proveedorId}/{proceso}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresaYProveedorYProceso(@PathVariable("empresaId") long empresaId, @PathVariable("proveedorId") long proveedorId, @PathVariable("proceso") String proceso) {
        List<FacturaCompra> facturasCompras = servicio.consultarPorEmpresaYProveedorYProceso(empresaId, proveedorId, proceso);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, facturasCompras);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
        Page<FacturaCompra> facturasCompras = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
        Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, facturasCompras);
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
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_activar_exitoso, facturaCompra);
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