package com.proyecto.sicecuador.controladoras.contabilidad;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.cajaBanco.Banco;
import com.proyecto.sicecuador.modelos.contabilidad.CuentaContable;
import com.proyecto.sicecuador.servicios.interf.contabilidad.ICuentaContableService;
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

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathCuentaContable;

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
    
    @GetMapping(value = "/consultarActivos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarActivos() {
	    List<CuentaContable> cuentasContables = servicio.consultarActivos();
	    Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, cuentasContables);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresa/{empresaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId) {
        List<CuentaContable> cuentasContables = servicio.consultarPorEmpresa(empresaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, cuentasContables);
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
