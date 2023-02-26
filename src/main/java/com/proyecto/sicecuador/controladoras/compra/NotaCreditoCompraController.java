package com.proyecto.sicecuador.controladoras.compra;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.compra.NotaCreditoCompra;
import com.proyecto.sicecuador.modelos.compra.NotaCreditoCompraLinea;
import com.proyecto.sicecuador.servicios.interf.compra.INotaCreditoCompraService;
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
import static com.proyecto.sicecuador.controladoras.Endpoints.pathNotaCreditoCompra;

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

    @GetMapping(value = "/consultarActivos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarActivos() {
        List<NotaCreditoCompra> notasCreditosCompras= servicio.consultarActivos();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, notasCreditosCompras);
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

    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody NotaCreditoCompra _notaCreditoCompra) {
        NotaCreditoCompra notaCreditoCompra = servicio.activar(_notaCreditoCompra);
        Respuesta respuesta= new Respuesta(true, Constantes.mensaje_activar_exitoso, notaCreditoCompra);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody NotaCreditoCompra _notaCreditoCompra) {
        NotaCreditoCompra notaCreditoCompra = servicio.inactivar(_notaCreditoCompra);
        Respuesta respuesta= new Respuesta(true, Constantes.mensaje_inactivar_exitoso, notaCreditoCompra);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/calcular", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> calcular(@RequestBody NotaCreditoCompra _notaCreditoCompra) {
        NotaCreditoCompra notaCreditoCompra = servicio.calcular(_notaCreditoCompra);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_calcular_exitoso, notaCreditoCompra);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
