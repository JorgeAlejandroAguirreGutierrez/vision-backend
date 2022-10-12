package com.proyecto.sicecuador.controladoras.contabilidad;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
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
import java.util.Optional;

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

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<CuentaContable> cuentas_contables = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, cuentas_contables);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Optional<CuentaContable> cuentas_contables=servicio.obtener(new CuentaContable(id));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, cuentas_contables);
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

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        CuentaContable cuentaContable=servicio.eliminar(new CuentaContable(id));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, cuentaContable);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/buscar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscar(@RequestBody CuentaContable cuentaContable) {
    	List<CuentaContable> cuentasContables=servicio.buscar(cuentaContable);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, cuentasContables);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> importar(MultipartFile file) {
        return null;
    }
}
