package com.proyecto.vision.controladoras.inventario;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathProducto;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.controladoras.GenericoController;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.cliente.Cliente;
import com.proyecto.vision.modelos.inventario.Bodega;
import com.proyecto.vision.modelos.inventario.Producto;
import com.proyecto.vision.servicios.interf.inventario.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(contexto+pathProducto)
public class ProductoController implements GenericoController<Producto> {
    @Autowired
    private IProductoService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
	    List<Producto> productos = servicio.consultar();
	    Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, productos);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
        List<Producto> productos = servicio.consultarPorEstado(estado);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, productos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresa/{empresaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId) {
        List<Producto> productos = servicio.consultarPorEmpresa(empresaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, productos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresaYEstado/{empresaId}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresaYEstado(@PathVariable("empresaId") long empresaId, @PathVariable("estado") String estado) {
        List<Producto> productos = servicio.consultarPorEmpresaYEstado(empresaId, estado);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, productos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Producto producto=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, producto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody Producto _producto) {
        Producto producto=servicio.crear(_producto);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, producto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Producto _producto) {
        Producto producto=servicio.actualizar(_producto);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, producto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody Producto _producto) {
    	Producto producto = servicio.activar(_producto);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_activar_exitoso, producto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody Producto _producto) {
    	Producto producto = servicio.inactivar(_producto);
        Respuesta respuesta= new Respuesta(true, Constantes.mensaje_inactivar_exitoso, producto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorProveedor/{proveedorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorProveedor(@PathVariable("proveedorId") long proveedorId) {
        List<Producto> productos=servicio.consultarPorProveedor(proveedorId);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, productos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorCategoriaProductoYEstado/{tipoProducto}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorCategoriaProductoYEstado(@PathVariable("categoriaProducto") String categoriaProducto, @PathVariable("estado") String estado) {
        List<Producto> productos = servicio.consultarPorCategoriaProductoYEstado(categoriaProducto, estado);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, productos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorCategoriaProductoYProveedorYEstado/{categoriaProducto}/{proveedorId}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorCategoriaProductoYProveedorYEstado(@PathVariable("categoriaProducto") String categoriaProducto, @PathVariable("proveedorId") long proveedorId, @PathVariable("estado") String estado) {
        List<Producto> productos = servicio.consultarPorCategoriaProductoYProveedorYEstado(categoriaProducto, proveedorId, estado);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, productos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorCategoriaProductoYEmpresaYEstado/{categoriaProducto}/{empresaId}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorCategoriaProductoYEmpresaYEstado(@PathVariable("categoriaProducto") String categoriaProducto, @PathVariable("empresaId") long empresaId, @PathVariable("estado") String estado) {
        List<Producto> productos = servicio.consultarPorCategoriaProductoYEmpresaYEstado(categoriaProducto, empresaId, estado);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, productos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorCategoriaProductoYProveedorYEmpresaYEstado/{categoriaProducto}/{proveedorId}/{empresaId}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorCategoriaProductoYProveedorYEmpresaYEstado(@PathVariable("categoriaProducto") String categoriaProducto, @PathVariable("proveedorId") long proveedorId, @PathVariable("empresaId") long empresaId, @PathVariable("estado") String estado) {
        List<Producto> productos = servicio.consultarPorCategoriaProductoYProveedorYEmpresaYEstado(categoriaProducto, proveedorId, empresaId, estado);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, productos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/buscar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscar(@RequestBody Producto producto) {
    	List<Producto> productos=servicio.buscar(producto);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, productos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
