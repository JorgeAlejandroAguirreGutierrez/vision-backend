package com.proyecto.sicecuador.controladoras.compra;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.compra.GrupoProveedor;
import com.proyecto.sicecuador.servicios.interf.compra.IGrupoProveedorService;
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

import static com.proyecto.sicecuador.controladoras.Endpoints.*;

@RestController
@RequestMapping(contexto+pathGrupoProveedor)
public class GrupoProveedorController implements GenericoController<GrupoProveedor> {
    @Autowired
    private IGrupoProveedorService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<GrupoProveedor> grupos_proveedores = servicio.consultar();
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, grupos_proveedores);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/consultarActivos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarActivos() {
	    List<GrupoProveedor> gruposProveedores = servicio.consultarActivos();
	    Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, gruposProveedores);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<GrupoProveedor> gruposproveedores = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, gruposproveedores);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        GrupoProveedor grupo_proveedor=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, grupo_proveedor);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid GrupoProveedor _grupoProveedor) {
        GrupoProveedor grupo_Proveedor=servicio.crear(_grupoProveedor);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, grupo_Proveedor);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody GrupoProveedor _grupoProveedor) {
        GrupoProveedor grupo_proveedor=servicio.actualizar(_grupoProveedor);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, grupo_proveedor);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody GrupoProveedor _grupoProveedor) {
    	GrupoProveedor grupoProveedor=servicio.activar(_grupoProveedor);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_activar_exitoso, grupoProveedor);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody GrupoProveedor _grupoProveedor) {
    	GrupoProveedor grupoProveedor=servicio.inactivar(_grupoProveedor);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_inactivar_exitoso, grupoProveedor);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PostMapping(value = "/buscar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscar(@RequestBody GrupoProveedor _grupo_proveedor) {
    	List<GrupoProveedor> grupos_proveedores=servicio.buscar(_grupo_proveedor);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, grupos_proveedores);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
