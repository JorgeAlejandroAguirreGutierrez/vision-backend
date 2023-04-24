package com.proyecto.sicecuador.controladoras.venta;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathTipoComprobante;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.usuario.Empresa;
import com.proyecto.sicecuador.modelos.usuario.Estacion;
import com.proyecto.sicecuador.modelos.venta.TipoComprobante;
import com.proyecto.sicecuador.servicios.interf.venta.ITipoComprobanteService;
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
        List<TipoComprobante> tipos_comprobantes=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, tipos_comprobantes);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarActivos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarActivos() {
        List<TipoComprobante> tipos_comprobantes = servicio.consultarActivos();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, tipos_comprobantes);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/consultarElectronica", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarElectronica() {
        List<TipoComprobante> tipos_comprobantes=servicio.consultarElectronica();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, tipos_comprobantes);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<TipoComprobante> tipos_comprobantes = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, tipos_comprobantes);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        TipoComprobante tipo_comprobante=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, tipo_comprobante);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid TipoComprobante _tipo_comprobante) {
        TipoComprobante tipo_comprobante=servicio.crear(_tipo_comprobante);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, tipo_comprobante);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody TipoComprobante _tipo_comprobante) {
        TipoComprobante tipo_comprobante=servicio.actualizar(_tipo_comprobante);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, tipo_comprobante);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
