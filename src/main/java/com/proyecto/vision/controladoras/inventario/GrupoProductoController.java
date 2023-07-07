package com.proyecto.vision.controladoras.inventario;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathGrupoProducto;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.controladoras.GenericoController;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.inventario.Bodega;
import com.proyecto.vision.modelos.inventario.GrupoProducto;
import com.proyecto.vision.servicios.interf.inventario.IGrupoProductoService;
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
@RequestMapping(contexto+pathGrupoProducto)
public class GrupoProductoController implements GenericoController<GrupoProducto> {
    @Autowired
    private IGrupoProductoService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<GrupoProducto> grupos_productos = servicio.consultar();
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, grupos_productos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
	    List<GrupoProducto> gruposProductos = servicio.consultarPorEstado(estado);
	    Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, gruposProductos);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresa/{empresaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId) {
        List<GrupoProducto> gruposProductos = servicio.consultarPorEmpresa(empresaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, gruposProductos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresaYEstado/{empresaId}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresaYEstado(@PathVariable("empresaId") long empresaId, @PathVariable("estado") String estado) {
        List<GrupoProducto> gruposProductos = servicio.consultarPorEmpresaYEstado(empresaId, estado);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, gruposProductos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<GrupoProducto> grupos_productos = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, grupos_productos);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        GrupoProducto grupoProducto=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, grupoProducto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/consultarGrupos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarGrupos(@RequestParam("empresaId") long empresaId, @RequestParam("estado") String estado) {
    	List<String> grupos=servicio.consultarGrupos(empresaId, estado);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, grupos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/consultarSubgrupos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarSubgrupos(@RequestParam("empresaId") long empresaId, @RequestParam("estado") String estado, @RequestParam("grupo") String grupo) {
    	List<String> subgrupos=servicio.consultarSubgrupos(empresaId, estado, grupo);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, subgrupos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/consultarSecciones", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarSecciones(@RequestParam("empresaId") long empresaId, @RequestParam("estado") String estado, @RequestParam("grupo") String grupo, @RequestParam("subgrupo") String subgrupo) {
    	List<String> secciones=servicio.consultarSecciones(empresaId, estado, grupo, subgrupo);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, secciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/consultarLineas", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarLineas(@RequestParam("empresaId") long empresaId, @RequestParam("estado") String estado, @RequestParam("grupo") String grupo, @RequestParam("subgrupo") String subgrupo, @RequestParam("seccion") String seccion) {
    	List<String> lineas=servicio.consultarLineas(empresaId, estado, grupo, subgrupo, seccion);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, lineas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/consultarSublineas", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarSublineas(@RequestParam("empresaId") long empresaId, @RequestParam("estado") String estado, @RequestParam("grupo") String grupo, @RequestParam("subgrupo") String subgrupo, @RequestParam("seccion") String seccion, @RequestParam("linea") String linea) {
    	List<String> lineas=servicio.consultarSublineas(empresaId, estado, grupo, subgrupo, seccion, linea);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, lineas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/consultarPresentaciones", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPresentaciones(@RequestParam("empresaId") long empresaId, @RequestParam("estado") String estado, @RequestParam("grupo") String grupo, @RequestParam("subgrupo") String subgrupo, @RequestParam("seccion") String seccion, @RequestParam("linea") String linea, @RequestParam("sublinea") String sublinea) {
    	List<String> presentaciones=servicio.consultarPresentaciones(empresaId, estado, grupo, subgrupo, seccion, linea, sublinea);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, presentaciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/obteneGrupo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerGrupoProducto(@RequestParam("grupo") String grupo, @RequestParam("subgrupo") String subgrupo, @RequestParam("seccion") String seccion, @RequestParam("linea") String linea, @RequestParam("sublinea") String sublinea, @RequestParam("presentacion") String presentacion) {
    	GrupoProducto grupoProducto=servicio.obtenerGrupoProducto(grupo, subgrupo, seccion, linea, sublinea, presentacion);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, grupoProducto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PostMapping(value = "/buscar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscar(@RequestBody GrupoProducto grupo_producto) {
    	List<GrupoProducto> grupos_productos=servicio.buscar(grupo_producto);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, grupos_productos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid GrupoProducto _grupo_producto) {
        GrupoProducto grupo_producto=servicio.crear(_grupo_producto);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, grupo_producto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody GrupoProducto _grupo_producto) {
        GrupoProducto grupo_producto=servicio.actualizar(_grupo_producto);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, grupo_producto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody GrupoProducto _grupoProducto) {
    	GrupoProducto grupoProducto = servicio.activar(_grupoProducto);
        Respuesta respuesta = new Respuesta(true,Constantes.mensaje_activar_exitoso, grupoProducto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody GrupoProducto _grupoProducto) {
    	GrupoProducto grupoProducto = servicio.inactivar(_grupoProducto);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_inactivar_exitoso, grupoProducto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
