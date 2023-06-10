package com.proyecto.sicecuador.controladoras.cajaBanco;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathCuentaPropia;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.cajaBanco.Banco;
import com.proyecto.sicecuador.modelos.cajaBanco.CuentaPropia;
import com.proyecto.sicecuador.modelos.configuracion.MenuOpcion;
import com.proyecto.sicecuador.servicios.interf.cajaBanco.ICuentaPropiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(contexto+pathCuentaPropia)
public class CuentaPropiaController implements GenericoController<CuentaPropia> {
    @Autowired
    private ICuentaPropiaService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<CuentaPropia> cuentasPropias=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, cuentasPropias);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
	    List<CuentaPropia> cuentasPropias = servicio.consultarPorEstado(estado);
	    Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, cuentasPropias);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresa/{empresaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId) {
        List<CuentaPropia> cuentasPropias = servicio.consultarPorEmpresa(empresaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, cuentasPropias);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresaYEstado/{empresaId}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresaYEstado(@PathVariable("empresaId") long empresaId, @PathVariable("estado") String estado) {
        List<CuentaPropia> cuentasPropias = servicio.consultarPorEmpresaYEstado(empresaId, estado);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, cuentasPropias);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<CuentaPropia> cuentasPropias = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, cuentasPropias);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        CuentaPropia cuentaPropia=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, cuentaPropia);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid CuentaPropia _cuenta_propia) {
        CuentaPropia cuentaPropia=servicio.crear(_cuenta_propia);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, cuentaPropia);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody CuentaPropia _cuenta_propia) {
        CuentaPropia cuentaPropia=servicio.actualizar(_cuenta_propia);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, cuentaPropia);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody CuentaPropia _cuentaPropia) {
    	CuentaPropia cuentaPropia = servicio.activar(_cuentaPropia);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_activar_exitoso, cuentaPropia);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody CuentaPropia _cuentaPropia) {
    	CuentaPropia cuentaPropia = servicio.inactivar(_cuentaPropia);
        Respuesta respuesta= new Respuesta(true, Constantes.mensaje_inactivar_exitoso, cuentaPropia);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorBanco", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorBanco (@PathVariable("banco") String banco) {
        List<CuentaPropia> cuentasPropias = servicio.consultarPorBanco(banco);
        Respuesta respuesta = new Respuesta(true,Constantes.mensaje_obtener_exitoso, cuentasPropias);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/consultarPorEstadoDistintoBancoAbreviatura", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstadoDistintoBancoAbreviatura (@PathVariable("estado") String estado) {
        List<String> bancos = servicio.consultarPorEstadoDistintoBancoAbreviatura(estado);
        Respuesta respuesta = new Respuesta(true,Constantes.mensaje_obtener_exitoso, bancos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

}
