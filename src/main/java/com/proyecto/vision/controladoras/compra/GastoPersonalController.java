package com.proyecto.vision.controladoras.compra;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.compra.GastoPersonal;
import com.proyecto.vision.servicios.interf.compra.IGastoPersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathGastoPersonal;

@RestController
@RequestMapping(contexto+pathGastoPersonal)
public class GastoPersonalController {
    @Autowired
    private IGastoPersonalService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<GastoPersonal> gastosPersonales = servicio.consultar();
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, gastosPersonales);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
        List<GastoPersonal> gastosPersonales = servicio.consultarPorEstado(estado);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, gastosPersonales);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresa/{empresaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId) {
        List<GastoPersonal> gastosPersonales = servicio.consultarPorEmpresa(empresaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, gastosPersonales);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresaYEstado/{empresaId}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresaYEstado(@PathVariable("empresaId") long empresaId, @PathVariable("estado") String estado) {
        List<GastoPersonal> gastosPersonales = servicio.consultarPorEmpresaYEstado(empresaId, estado);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, gastosPersonales);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorProveedorYEmpresaYEstado/{proveedorId}/{empresaId}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorProveedorYEmpresaYEstado(@PathVariable("proveedorId") long proveedorId, @PathVariable("empresaId") long empresaId, @PathVariable("estado") String estado) {
        List<GastoPersonal> gastosPersonales = servicio.consultarPorProveedorYEmpresaYEstado(proveedorId, empresaId, estado);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, gastosPersonales);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        GastoPersonal gastoPersonal = servicio.obtener(id);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_obtener_exitoso, gastoPersonal);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody GastoPersonal _gastoPersonal) {
        GastoPersonal gastoPersonal = servicio.crear(_gastoPersonal);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_crear_exitoso, gastoPersonal);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody GastoPersonal _gastoPersonal) {
        GastoPersonal gastoPersonal = servicio.actualizar(_gastoPersonal);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_actualizar_exitoso, gastoPersonal);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PatchMapping(value = "/anular", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> anular(@RequestBody GastoPersonal _gastoPersonal) {
        GastoPersonal gastoPersonal = servicio.anular(_gastoPersonal);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_anular_exitoso, gastoPersonal);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}