package com.proyecto.vision.controladoras.contabilidad;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.controladoras.GenericoController;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.contabilidad.CuentaContable;
import com.proyecto.vision.servicios.interf.contabilidad.ICuentaContableService;
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

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathCuentaContable;

@RestController
@RequestMapping(contexto+pathCuentaContable)
public class CuentaContableController implements GenericoController<CuentaContable> {
    @Autowired
    private ICuentaContableService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
	    List<CuentaContable> cuentas_contables=servicio.consultar();
	    Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, cuentas_contables);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresa/{empresaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId) {
        List<CuentaContable> cuentasContables=servicio.consultarPorEmpresa(empresaId);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, cuentasContables);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
	    List<CuentaContable> cuentasContables = servicio.consultarPorEstado(estado);
	    Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, cuentasContables);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresaYEstado/{empresaId}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresaYEstado(@PathVariable("empresaId") long empresaId, @PathVariable("estado") String estado) {
        List<CuentaContable> cuentasContables=servicio.consultarPorEmpresaYEstado(empresaId, estado);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, cuentasContables);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<CuentaContable> cuentasContables = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, cuentasContables);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        CuentaContable cuentaContable=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, cuentaContable);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid CuentaContable _cuentaContable) {
        CuentaContable cuentaContable=servicio.crear(_cuentaContable);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, cuentaContable);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody CuentaContable _cuentaContable) {
        CuentaContable cuentaContable=servicio.actualizar(_cuentaContable);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, cuentaContable);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody CuentaContable _cuentaContable) {
    	CuentaContable cuentaContable = servicio.activar(_cuentaContable);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_activar_exitoso, cuentaContable);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody CuentaContable _cuentaContable) {
    	CuentaContable cuentaContable = servicio.inactivar(_cuentaContable);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_inactivar_exitoso, cuentaContable);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/buscar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscar(@RequestBody CuentaContable cuentaContable) {
    	List<CuentaContable> cuentasContables=servicio.buscar(cuentaContable);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, cuentasContables);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
