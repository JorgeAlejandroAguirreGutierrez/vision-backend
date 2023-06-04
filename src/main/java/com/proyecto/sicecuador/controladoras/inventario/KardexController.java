package com.proyecto.sicecuador.controladoras.inventario;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathKardex;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.inventario.Bodega;
import com.proyecto.sicecuador.modelos.inventario.Kardex;
import com.proyecto.sicecuador.servicios.interf.inventario.IKardexService;
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
@RequestMapping(contexto+pathKardex)
public class KardexController implements GenericoController<Kardex> {
    @Autowired
    private IKardexService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<Kardex> kardexs=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, kardexs);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresa/{empresaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId) {
        List<Kardex> kardexs = servicio.consultarPorEmpresa(empresaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, kardexs);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<Kardex> kardexs = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, kardexs);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Kardex kardex=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, kardex);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorProducto/{productoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorProducto(@PathVariable("productoId") long productoId) {
        List<Kardex> kardex=servicio.consultarPorProducto(productoId);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, kardex);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/obtenerUltimoPorBodega/{bodegaId}/{productoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerUltimoPorBodega(@PathVariable("bodegaId") long bodegaId, @PathVariable("productoId") long productoId) {
        Kardex kardex=servicio.obtenerUltimoPorBodega(bodegaId, productoId);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, kardex);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid Kardex _kardex) {
        Kardex kardex=servicio.crear(_kardex);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, kardex);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Kardex _kardex) {
        Kardex kardex=servicio.actualizar(_kardex);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, kardex);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
