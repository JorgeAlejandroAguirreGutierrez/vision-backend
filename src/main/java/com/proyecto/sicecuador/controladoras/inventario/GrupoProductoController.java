package com.proyecto.sicecuador.controladoras.inventario;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathGrupoProducto;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.inventario.GrupoProducto;
import com.proyecto.sicecuador.servicios.interf.inventario.IGrupoProductoService;
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
@RequestMapping(contexto+pathGrupoProducto)
public class GrupoProductoController implements GenericoController<GrupoProducto> {
    @Autowired
    private IGrupoProductoService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<GrupoProducto> grupos_productos=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, grupos_productos);
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
        Optional<GrupoProducto> grupo_producto=servicio.obtener(new GrupoProducto(id));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, grupo_producto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/consultarGrupos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarGrupos() {
    	List<String> grupos=servicio.consultarGrupos();
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, grupos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/consultarSubgrupos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarSubgrupos(@RequestParam("grupo") String grupo) {
    	List<String> subgrupos=servicio.consultarSubgrupos(grupo);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, subgrupos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/consultarSecciones", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarSecciones(@RequestParam("grupo") String grupo, @RequestParam("subgrupo") String subgrupo) {
    	List<String> secciones=servicio.consultarSecciones(grupo, subgrupo);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, secciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/consultarLineas", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarLineas(@RequestParam("grupo") String grupo, @RequestParam("subgrupo") String subgrupo, @RequestParam("seccion") String seccion) {
    	List<String> lineas=servicio.consultarLineas(grupo, subgrupo, seccion);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, lineas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/consultarSublineas", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarSublineas(@RequestParam("grupo") String grupo, @RequestParam("subgrupo") String subgrupo, @RequestParam("seccion") String seccion, @RequestParam("linea") String linea) {
    	List<String> lineas=servicio.consultarSublineas(grupo, subgrupo, seccion, linea);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, lineas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/consultarPresentaciones", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPresentaciones(@RequestParam("grupo") String grupo, @RequestParam("subgrupo") String subgrupo, @RequestParam("seccion") String seccion, @RequestParam("linea") String linea, @RequestParam("sublinea") String sublinea) {
    	List<String> presentaciones=servicio.consultarPresentaciones(grupo, subgrupo, seccion, linea, sublinea);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, presentaciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/obteneGrupo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerGrupoProducto(@RequestParam("grupo") String grupo, @RequestParam("subgrupo") String subgrupo, @RequestParam("seccion") String seccion, @RequestParam("linea") String linea, @RequestParam("sublinea") String sublinea, @RequestParam("presentacion") String presentacion) {
    	Optional<GrupoProducto> grupoProducto=servicio.obtenerGrupoProducto(grupo, subgrupo, seccion, linea, sublinea, presentacion);
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

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        GrupoProducto grupo_producto=servicio.eliminar(new GrupoProducto(id));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, grupo_producto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> importar(MultipartFile file) {
        return null;
    }

}
