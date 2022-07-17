package com.proyecto.sicecuador.controladoras.recaudacion;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathCuentaPropia;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.recaudacion.CuentaPropia;
import com.proyecto.sicecuador.servicios.interf.recaudacion.ICuentaPropiaService;
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
@RequestMapping(contexto+pathCuentaPropia)
public class CuentaPropiaController implements GenericoController<CuentaPropia> {
    @Autowired
    private ICuentaPropiaService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<CuentaPropia> cuentas_propias=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, cuentas_propias);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<CuentaPropia> cuentas_propias = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, cuentas_propias);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Optional<CuentaPropia> cuenta_propia=servicio.obtener(new CuentaPropia(id));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, cuenta_propia);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid CuentaPropia _cuenta_propia) {
        CuentaPropia cuenta_propia=servicio.crear(_cuenta_propia);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, cuenta_propia);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody CuentaPropia _cuenta_propia) {
        CuentaPropia cuenta_propia=servicio.actualizar(_cuenta_propia);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, cuenta_propia);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        CuentaPropia cuenta_propia=servicio.eliminar(new CuentaPropia(id));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, cuenta_propia);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> importar(MultipartFile file) {
        return null;
    }
}
