package com.proyecto.vision.controladoras.compra;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.compra.NotaCreditoCompra;
import com.proyecto.vision.servicios.interf.compra.INotaCreditoCompraService;
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
import static com.proyecto.vision.controladoras.Endpoints.pathNotaCreditoCompra;

@RestController
@RequestMapping(contexto+pathNotaCreditoCompra)
public class NotaCreditoCompraController {
    @Autowired
    private INotaCreditoCompraService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<NotaCreditoCompra> notasCreditosCompras=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, notasCreditosCompras);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
        List<NotaCreditoCompra> notasCreditosCompras = servicio.consultarPorEstado(estado);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, notasCreditosCompras);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresa/{empresaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId) {
        List<NotaCreditoCompra> notasCreditosCompras = servicio.consultarPorEmpresa(empresaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, notasCreditosCompras);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
        Page<NotaCreditoCompra> notasCreditosCompras = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, notasCreditosCompras);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        NotaCreditoCompra notaCreditoCompra=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_obtener_exitoso, notaCreditoCompra);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "obtenerPorFacturaCompra/{facturaCompraId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerPorFacturaCompra(@PathVariable("facturaCompraId") long facturaCompraId) {
        NotaCreditoCompra notaCreditoCompra=servicio.obtenerPorFacturaCompra(facturaCompraId);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_obtener_exitoso, notaCreditoCompra);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody NotaCreditoCompra _notaCreditoCompra) {
        NotaCreditoCompra notaCreditoCompra=servicio.crear(_notaCreditoCompra);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_crear_exitoso, notaCreditoCompra);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody NotaCreditoCompra _notaCreditoCompra) {
        NotaCreditoCompra notaCreditoCompra=servicio.actualizar(_notaCreditoCompra);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_actualizar_exitoso, notaCreditoCompra);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PatchMapping(value = "/anular", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> anular(@RequestBody NotaCreditoCompra _notaCreditoCompra) {
        NotaCreditoCompra notaCreditoCompra = servicio.anular(_notaCreditoCompra);
        Respuesta respuesta= new Respuesta(true, Constantes.mensaje_anular_exitoso, notaCreditoCompra);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/calcular", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> calcular(@RequestBody NotaCreditoCompra _notaCreditoCompra) {
        NotaCreditoCompra notaCreditoCompra = servicio.calcular(_notaCreditoCompra);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_calcular_exitoso, notaCreditoCompra);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
