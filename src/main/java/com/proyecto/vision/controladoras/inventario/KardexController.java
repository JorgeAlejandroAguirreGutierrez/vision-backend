package com.proyecto.vision.controladoras.inventario;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathKardex;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.controladoras.GenericoController;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.inventario.Bodega;
import com.proyecto.vision.modelos.inventario.Kardex;
import com.proyecto.vision.servicios.interf.inventario.IKardexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(contexto+pathKardex)
public class KardexController implements GenericoController<Kardex> {
    @Autowired
    private IKardexService servicio;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid Kardex _kardex) {
        Kardex kardex=servicio.crear(_kardex);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, kardex);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Kardex _kardex) {
        Kardex kardex=servicio.actualizar(_kardex);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, kardex);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Kardex kardex=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, kardex);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/obtenerUltimoPorProductoYBodega/{productoId}/{bodegaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerUltimoPorProductoYBodega(@PathVariable("productoId") long productoId, @PathVariable("bodegaId") long bodegaId) {
        Kardex kardex = servicio.obtenerUltimoPorProductoYBodegaYEstado(productoId, bodegaId, Constantes.estadoActivo);
        Respuesta respuesta = new Respuesta(true,Constantes.mensaje_obtener_exitoso, kardex);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/obtenerUltimoPorProductoYBodegaYTablaTipoComprobante/{productoId}/{bodegaId}/{tablaTipoComprobante}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerUltimoPorProductoYBodegaYTablaTipoComprobante(@PathVariable("productoId") long productoId, @PathVariable("bodegaId") long bodegaId, @PathVariable("tablaTipoComprobante") String tablaTipoComprobante) {
        Kardex kardex = servicio.obtenerUltimoPorProductoYBodegaYTablaTipoComprobante(productoId, bodegaId, tablaTipoComprobante);
        Respuesta respuesta = new Respuesta(true,Constantes.mensaje_obtener_exitoso, kardex);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<Kardex> kardexs=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, kardexs);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorProducto/{productoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorProducto(@PathVariable("productoId") long productoId) {
        List<Kardex> kardex=servicio.consultarPorProducto(productoId);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, kardex);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorProductoYEstado/{productoId}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorProductoYEstado(@PathVariable("productoId") long productoId, @PathVariable("estado") String estado) {
        List<Kardex> kardex=servicio.consultarPorProductoYEstado(productoId, estado);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, kardex);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
