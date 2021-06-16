package com.proyecto.sicecuador.controladoras.inventario;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathProducto;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.inventario.Producto;
import com.proyecto.sicecuador.servicios.interf.inventario.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(contexto+pathProducto)
public class ProductoController implements GenericoController<Producto> {
    @Autowired
    private IProductoService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
	    List<Producto> productos=servicio.consultar();
	    Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, productos);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<Producto> productos = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, productos);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Optional<Producto> producto=servicio.obtener(new Producto(id));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, producto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid Producto _producto) {
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

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        Producto producto=servicio.eliminar(new Producto(id));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, producto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/tipo/bien", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarBien() {
        List<Producto> productos=servicio.consultarBien();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, productos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/tipo/servicio", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarServicio() {
        List<Producto> productos=servicio.consultarServicio();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, productos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/tipo/activofijo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarActivoFijo() {
        List<Producto> productos=servicio.consultarActivoFijo();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, productos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/buscar/producto/{producto_id}/bodega", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarBodega() {
        List<Producto> productos=servicio.consultarBodega();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, productos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/buscar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscar(@RequestBody Producto producto) {
    	List<Producto> productos=servicio.buscar(producto);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, productos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> importar(MultipartFile file) {
        return null;
    }
}
