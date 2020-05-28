package com.proyecto.sicecuador.controladoras.inventario;

import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.inventario.Bodega;
import com.proyecto.sicecuador.modelos.inventario.BodegaProducto;
import com.proyecto.sicecuador.modelos.inventario.Producto;
import com.proyecto.sicecuador.servicios.interf.inventario.IBodegaProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/sicecuador/bodegaproducto")
public class BodegaProductoController implements GenericoController<BodegaProducto> {
    @Autowired
    private IBodegaProductoService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        try {
            List<BodegaProducto> bodegas=servicio.consultar();
            Respuesta respuesta=new Respuesta(true,"Se consulto las bodegas", bodegas);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        try {
            Optional<BodegaProducto> bodega_producto=servicio.obtener(new BodegaProducto(id));
            Respuesta respuesta=new Respuesta(true,"Se obtuvo un bodega producto", bodega_producto);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody BodegaProducto _bodega_producto) {
        try {
            BodegaProducto bodega_producto=servicio.crear(_bodega_producto);
            Respuesta respuesta=new Respuesta(true,"Se creo un bodega", bodega_producto);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody BodegaProducto _bodega_producto) {
        try {
            BodegaProducto bodega_producto=servicio.actualizar(_bodega_producto);
            Respuesta respuesta=new Respuesta(true,"Se actualizo un bodega", bodega_producto);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        try {
            BodegaProducto bodega_producto=servicio.eliminar(new BodegaProducto(id));
            Respuesta respuesta=new Respuesta(true,"Se elimino un bodega producto", bodega_producto);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/{producto_id}/producto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarProducto(@PathVariable("producto_id") long producto_id) {
        try {
            BodegaProducto _bodega_producto=new BodegaProducto();
            _bodega_producto.setProducto(new Producto(producto_id));
            List<BodegaProducto> bodega=servicio.consultarProducto(_bodega_producto);
            Respuesta respuesta=new Respuesta(true,"Se obtuvo un bodega producto", bodega);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/{producto_id}/producto/{bodega_id}/bodega", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerProductoBodega(@PathVariable("producto_id") long producto_id, @PathVariable("bodega_id") long bodega_id) {
        try {
            BodegaProducto _bodega_producto=new BodegaProducto();
            _bodega_producto.setProducto(new Producto(producto_id));
            _bodega_producto.setBodega(new Bodega(bodega_id));
            Optional<BodegaProducto> bodega=servicio.obtenerProductoBodega(_bodega_producto);
            Respuesta respuesta=new Respuesta(true,"Se obtuvo un bodega producto", bodega);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
