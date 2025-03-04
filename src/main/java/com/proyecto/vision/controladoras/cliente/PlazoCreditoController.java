package com.proyecto.vision.controladoras.cliente;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathPlazoCredito;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.controladoras.GenericoController;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.cliente.PlazoCredito;
import com.proyecto.vision.servicios.interf.cliente.IPlazoCreditoService;
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
@RequestMapping(contexto+pathPlazoCredito)
public class PlazoCreditoController implements GenericoController<PlazoCredito> {
    @Autowired
    private IPlazoCreditoService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<PlazoCredito> plazosCreditos = servicio.consultar();
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, plazosCreditos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresa/{empresaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId) {
        List<PlazoCredito> plazosCreditos=servicio.consultarPorEmpresa(empresaId);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, plazosCreditos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
	    List<PlazoCredito> plazosCreditos = servicio.consultarPorEstado(estado);
	    Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, plazosCreditos);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresaYEstado/{empresaId}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId, @PathVariable("estado") String estado) {
        List<PlazoCredito> plazosCreditos = servicio.consultarPorEmpresaYEstado(empresaId, estado);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, plazosCreditos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        PlazoCredito plazo_credito=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, plazo_credito);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid PlazoCredito _plazoCredito) {
        PlazoCredito plazo_credito=servicio.crear(_plazoCredito);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, plazo_credito);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody PlazoCredito _plazoCredito) {
        PlazoCredito plazo_credito=servicio.actualizar(_plazoCredito);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, plazo_credito);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody PlazoCredito _plazoCredito) {
    	PlazoCredito plazoCredito=servicio.activar(_plazoCredito);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_activar_exitoso, plazoCredito);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody PlazoCredito _plazoCredito) {
    	PlazoCredito plazoCredito=servicio.inactivar(_plazoCredito);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_inactivar_exitoso, plazoCredito);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
