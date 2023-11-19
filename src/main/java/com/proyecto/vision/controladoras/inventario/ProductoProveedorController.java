package com.proyecto.vision.controladoras.inventario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.controladoras.GenericoController;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.inventario.ProductoProveedor;
import com.proyecto.vision.servicios.interf.inventario.IProductoProveedorService;
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
import static com.proyecto.vision.controladoras.Endpoints.pathProductoProveedor;

@RestController
@RequestMapping(contexto+pathProductoProveedor)
public class ProductoProveedorController implements GenericoController<ProductoProveedor> {
    @Autowired
    private IProductoProveedorService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
	    List<ProductoProveedor> productoProveedores = servicio.consultar();
	    Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, productoProveedores);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
        List<ProductoProveedor> productoProveedores = servicio.consultarPorEstado(estado);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, productoProveedores);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresa/{empresaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId) {
        List<ProductoProveedor> productoProveedores = servicio.consultarPorEmpresa(empresaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, productoProveedores);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresaYEstado/{empresaId}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresaYEstado(@PathVariable("empresaId") long empresaId, @PathVariable("estado") String estado) {
        List<ProductoProveedor> productoProveedores = servicio.consultarPorEmpresaYEstado(empresaId, estado);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, productoProveedores);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<ProductoProveedor> productoProveedores = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, productoProveedores);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        ProductoProveedor productoProveedor=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, productoProveedor);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody ProductoProveedor _productoProveedor) {
        ProductoProveedor productoProveedor=servicio.crear(_productoProveedor);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, productoProveedor);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody ProductoProveedor _productoProveedor) {
        ProductoProveedor productoProveedor=servicio.actualizar(_productoProveedor);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, productoProveedor);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody ProductoProveedor _productoProveedor) {
        ProductoProveedor productoProveedor = servicio.activar(_productoProveedor);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_activar_exitoso, productoProveedor);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody ProductoProveedor _productoProveedor) {
        ProductoProveedor productoProveedor = servicio.inactivar(_productoProveedor);
        Respuesta respuesta= new Respuesta(true, Constantes.mensaje_inactivar_exitoso, productoProveedor);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorProveedorYEstado/{proveedorId}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorProveedorYEstador(@PathVariable("proveedorId") long proveedorId, @PathVariable("estado") String estado) {
        List<ProductoProveedor> productoProveedores = servicio.consultarPorProveedorYEstado(proveedorId, estado);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, productoProveedores);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorProductoYEstado/{productoId}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorProductoYEstado(@PathVariable("productoId") long productoId, @PathVariable("estado") String estado) {
        List<ProductoProveedor> productoProveedores = servicio.consultarPorProductoYEstado(productoId, estado);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, productoProveedores);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/buscar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscar(@RequestBody ProductoProveedor productoProveedor) {
    	List<ProductoProveedor> productos=servicio.buscar(productoProveedor);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, productos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
