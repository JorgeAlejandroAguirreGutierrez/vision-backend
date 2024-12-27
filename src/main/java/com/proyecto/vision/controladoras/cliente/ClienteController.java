package com.proyecto.vision.controladoras.cliente;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathCliente;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.controladoras.GenericoController;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.cliente.Cliente;
import com.proyecto.vision.servicios.interf.cliente.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(contexto+pathCliente)
public class ClienteController implements GenericoController<Cliente> {
    @Autowired
    private IClienteService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<Cliente> clientes = servicio.consultar();
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, clientes);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value="/pagina/{pag}/{cant}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("pag") int pag, @PathVariable("cant") int cant) {
        Page<Cliente> clientes = servicio.consultarPagina(pag, cant);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, clientes);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresa/{empresaId}/{pag}/{cant}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId, @PathVariable("pag") int pag, @PathVariable("cant") int cant) {
        Page<Cliente> clientes = servicio.consultarPorEmpresa(empresaId, pag, cant);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, clientes);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarFiltroPorEmpresa/{filtro}/{empresaId}/{pag}/{cant}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarFiltroPorEmpresa(@PathVariable("filtro") String filtro, @PathVariable("empresaId") long empresaId, @PathVariable("pag") int pag, @PathVariable("cant") int cant) {
        Page<Cliente> clientes = servicio.consultarFiltroPorEmpresa(filtro, empresaId, pag, cant);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, clientes);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado, @PathVariable("pag") int pag, @PathVariable("cant") int cant) {
	    Page<Cliente> clientes=servicio.consultarPorEstado(estado);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, clientes);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresaYEstado/{empresaId}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresaYEstado(@PathVariable("empresaId") long empresaId, @PathVariable("estado") String estado) {
        Page<Cliente> clientes = servicio.consultarPorEmpresaYEstado(empresaId, estado);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, clientes);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Cliente cliente = servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, cliente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid Cliente _cliente) {
        Cliente cliente = servicio.crear(_cliente);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, cliente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Cliente _cliente) {
        Cliente cliente = servicio.actualizar(_cliente);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, cliente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody Cliente _cliente) {
    	Cliente cliente = servicio.activar(_cliente);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_activar_exitoso, cliente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody Cliente _cliente) {
    	Cliente cliente = servicio.inactivar(_cliente);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_inactivar_exitoso, cliente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/obtenerIdentificacionYEmpresaYEstado/{identificacion}/{empresaId}/{estado}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerIdentificacionYEmpresaYEstado(@PathVariable("identificacion") String identificacion, @PathVariable("empresaId") long empresaId, @PathVariable("estado") String estado) {
        Cliente cliente = servicio.obtenerPorIdentificacionYEmpresaYEstado(identificacion, empresaId, estado);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_obtener_exitoso, cliente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/obtenerRazonSocialYEmpresaYEstado/{razonSocial}/{empresaId}/{estado}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerPorRazonSocialYEmpresaYEstado(@PathVariable("razonSocial") String razonSocial, @PathVariable("empresaId") long empresaId, @PathVariable("estado") String estado) {
        Cliente cliente=servicio.obtenerPorRazonSocialYEmpresaYEstado(razonSocial, empresaId, estado);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_obtener_exitoso, cliente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/validarIdentificacionPorEmpresa/{identificacion}/{empresaId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> validarIdentificacionPorEmpresa(@PathVariable("identificacion") String identificacion, @PathVariable("empresaId") long empresaId) {
        Cliente cliente = servicio.validarIdentificacionPorEmpresa(identificacion, empresaId);
        cliente.normalizar();
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_obtener_exitoso, cliente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
