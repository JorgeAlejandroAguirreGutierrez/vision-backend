package com.proyecto.vision.controladoras.configuracion;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathTipoComprobante;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.controladoras.GenericoController;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.configuracion.TipoComprobante;
import com.proyecto.vision.servicios.interf.configuracion.ITipoComprobanteService;
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
@RequestMapping(contexto+pathTipoComprobante)
public class TipoComprobanteController implements GenericoController<TipoComprobante> {

    @Autowired
    private ITipoComprobanteService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<TipoComprobante> tiposComprobantes = servicio.consultar();
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, tiposComprobantes);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorElectronica", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorElectronica() {
        List<TipoComprobante> tiposComprobantes = servicio.consultarPorElectronica();
        Respuesta respuesta = new Respuesta(true,Constantes.mensaje_obtener_exitoso, tiposComprobantes);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<TipoComprobante> tiposComprobantes = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, tiposComprobantes);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        TipoComprobante tipoComprobante=servicio.obtener(id);
        Respuesta respuesta = new Respuesta(true,Constantes.mensaje_obtener_exitoso, tipoComprobante);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid TipoComprobante _tipo_comprobante) {
        TipoComprobante tipoComprobante=servicio.crear(_tipo_comprobante);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, tipoComprobante);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody TipoComprobante _tipo_comprobante) {
        TipoComprobante tipoComprobante=servicio.actualizar(_tipo_comprobante);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, tipoComprobante);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
