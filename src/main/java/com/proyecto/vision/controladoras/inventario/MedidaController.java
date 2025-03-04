package com.proyecto.vision.controladoras.inventario;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathMedida;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.controladoras.GenericoController;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.inventario.Medida;
import com.proyecto.vision.servicios.interf.inventario.IMedidaService;
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
@RequestMapping(contexto+pathMedida)
public class MedidaController implements GenericoController<Medida> {
    @Autowired
    private IMedidaService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<Medida> medidas=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, medidas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
	    List<Medida> medidas = servicio.consultarPorEstado(estado);
	    Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, medidas);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresa/{empresaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId) {
        List<Medida> medidas = servicio.consultarPorEmpresa(empresaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, medidas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresaYEstado/{empresaId}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresaYEstado(@PathVariable("empresaId") long empresaId, @PathVariable("estado") String estado) {
        List<Medida> medidas = servicio.consultarPorEmpresaYEstado(empresaId, estado);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, medidas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Medida medida=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, medida);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid Medida _medida) {
        Medida medida=servicio.crear(_medida);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, medida);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Medida _medida) {
        Medida medida=servicio.actualizar(_medida);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, medida);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody Medida _medida) {
    	Medida medida= servicio.activar(_medida);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_activar_exitoso, medida);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody Medida _medida) {
    	Medida medida = servicio.inactivar(_medida);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_inactivar_exitoso, medida);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
