package com.proyecto.vision.controladoras.compra;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathProveedor;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.controladoras.GenericoController;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.cajaBanco.Banco;
import com.proyecto.vision.modelos.cliente.Cliente;
import com.proyecto.vision.modelos.cliente.Segmento;
import com.proyecto.vision.modelos.compra.Proveedor;
import com.proyecto.vision.servicios.interf.compra.IProveedorService;
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
@RequestMapping(contexto+pathProveedor)
public class ProveedorController implements GenericoController<Proveedor> {
    @Autowired
    private IProveedorService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<Proveedor> proveedores = servicio.consultar();
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, proveedores);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
	    List<Proveedor> proveedores = servicio.consultarPorEstado(estado);
	    Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, proveedores);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresa/{empresaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId) {
        List<Proveedor> proveedores = servicio.consultarPorEmpresa(empresaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, proveedores);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresaYEstado/{empresaId}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId, @PathVariable("estado") String estado) {
        List<Proveedor> proveedores = servicio.consultarPorEmpresaYEstado(empresaId, estado);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, proveedores);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Proveedor proveedor = servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, proveedor);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid Proveedor _proveedor) {
        Proveedor proveedor = servicio.crear(_proveedor);
        Respuesta respuesta = new Respuesta(true,Constantes.mensaje_crear_exitoso, proveedor);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Proveedor _proveedor) {
        Proveedor proveedor = servicio.actualizar(_proveedor);
        Respuesta respuesta = new Respuesta(true,Constantes.mensaje_actualizar_exitoso, proveedor);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody Proveedor _proveedor) {
    	Proveedor proveedor = servicio.activar(_proveedor);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_activar_exitoso, proveedor);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody Proveedor _proveedor) {
    	Proveedor proveedor = servicio.inactivar(_proveedor);
        Respuesta respuesta= new Respuesta(true, Constantes.mensaje_inactivar_exitoso, proveedor);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/buscar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscar(@RequestBody Proveedor proveedor) {
    	List<Proveedor> proveedores=servicio.buscar(proveedor);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, proveedores);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/validarIdentificacionPorEmpresa/{identificacion}/{empresaId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> validarIdentificacionPorEmpresa(@PathVariable("identificacion") String identificacion, @PathVariable("empresaId") long empresaId) {
        Proveedor proveedor = servicio.validarIdentificacionPorEmpresa(identificacion, empresaId);
        proveedor.normalizar();
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_obtener_exitoso, proveedor);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
