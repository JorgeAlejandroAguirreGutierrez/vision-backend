package com.proyecto.vision.controladoras.inventario;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathCategoriaProducto;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.controladoras.GenericoController;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.inventario.Bodega;
import com.proyecto.vision.modelos.inventario.CategoriaProducto;
import com.proyecto.vision.servicios.interf.inventario.ICategoriaProductoService;
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

@RestController
@RequestMapping(contexto+pathCategoriaProducto)
public class CategoriaProductoController implements GenericoController<CategoriaProducto> {
    @Autowired
    private ICategoriaProductoService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<CategoriaProducto> categorias_productos=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, categorias_productos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
	    List<CategoriaProducto> categoriasProductos = servicio.consultarPorEstado(estado);
	    Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, categoriasProductos);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        CategoriaProducto categoriaProducto=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, categoriaProducto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "obtenerPorAbreviatura/{abreviatura}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("abreviatura") String abreviatura) {
        CategoriaProducto categoriaProducto=servicio.obtenerPorAbreviatura(abreviatura);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, categoriaProducto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid CategoriaProducto _categoria_producto) {
        CategoriaProducto categoriaProducto=servicio.crear(_categoria_producto);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, categoriaProducto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody CategoriaProducto _categoria_producto) {
        CategoriaProducto categoria_producto=servicio.actualizar(_categoria_producto);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, categoria_producto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody CategoriaProducto _categoriaProducto) {
    	CategoriaProducto categoriaProducto = servicio.activar(_categoriaProducto);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_activar_exitoso, categoriaProducto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody CategoriaProducto _categoriaProducto) {
    	CategoriaProducto categoriaProducto=servicio.inactivar(_categoriaProducto);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_inactivar_exitoso, categoriaProducto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
