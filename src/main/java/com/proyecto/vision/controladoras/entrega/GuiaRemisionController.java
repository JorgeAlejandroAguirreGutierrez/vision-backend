package com.proyecto.vision.controladoras.entrega;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathGuiaRemision;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.controladoras.GenericoController;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.entrega.GuiaRemision;
import com.proyecto.vision.servicios.interf.entrega.IGuiaRemisionService;
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
import java.util.Optional;
@RestController
@RequestMapping(contexto+pathGuiaRemision)
public class GuiaRemisionController implements GenericoController<GuiaRemision> {
    @Autowired
    private IGuiaRemisionService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<GuiaRemision> guiasRemisiones = servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, guiasRemisiones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresa/{empresaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId) {
        List<GuiaRemision> guiasRemisiones = servicio.consultarPorEmpresa(empresaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, guiasRemisiones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresaYEstado/{empresaId}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresaYEstado(@PathVariable("empresaId") long empresaId, @PathVariable("estado") String estado) {
        List<GuiaRemision> guiasRemisiones = servicio.consultarPorEmpresaYEstado(empresaId, estado);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, guiasRemisiones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorFacturaYEmpresaYEstado/{facturaId}/{empresaId}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorFacturaYEmpresaYEstado(@PathVariable("facturaId") long facturaId, @PathVariable("empresaId") long empresaId, @PathVariable("estado") String estado) {
        List<GuiaRemision> guiasRemisiones = servicio.consultarPorFacturaYEmpresaYEstado(facturaId, empresaId, estado);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, guiasRemisiones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        GuiaRemision guiaRemision =servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, guiaRemision);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid GuiaRemision _guiaRemision) {
        GuiaRemision guiaRemision = servicio.crear(_guiaRemision);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, guiaRemision);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody GuiaRemision _guiaRemision) {
        GuiaRemision guiaRemision = servicio.actualizar(_guiaRemision);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, guiaRemision);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PatchMapping(value = "/anular", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> anular(@RequestBody GuiaRemision _guiaRemision) {
        GuiaRemision guiaRemision = servicio.anular(_guiaRemision);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_activar_exitoso, guiaRemision);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/obtenerPorFactura/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerPorFactura(@PathVariable("id") long id) {
        Optional<GuiaRemision> entrega=servicio.obtenerPorFactura(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, entrega);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
