package com.proyecto.vision.controladoras.compra;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.controladoras.GenericoController;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.cajaBanco.Banco;
import com.proyecto.vision.modelos.compra.GrupoProveedor;
import com.proyecto.vision.servicios.interf.compra.IGrupoProveedorService;
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

import static com.proyecto.vision.controladoras.Endpoints.*;

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
    
    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
	    List<GrupoProveedor> gruposProveedores = servicio.consultarPorEstado(estado);
	    Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, gruposProveedores);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresa/{empresaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId) {
        List<GrupoProveedor> gruposProveedores = servicio.consultarPorEmpresa(empresaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, gruposProveedores);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresaYEstado/{empresaId}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId, @PathVariable("estado") String estado) {
        List<GrupoProveedor> gruposProveedores = servicio.consultarPorEmpresaYEstado(empresaId, estado);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, gruposProveedores);
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
}
