package com.proyecto.sicecuador.controladoras.inventario;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathCaracteristica;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.inventario.Bodega;
import com.proyecto.sicecuador.modelos.inventario.Caracteristica;
import com.proyecto.sicecuador.modelos.inventario.Producto;
import com.proyecto.sicecuador.servicios.interf.inventario.ICaracteristicaService;
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
@RequestMapping(contexto+pathCaracteristica)
public class CaracteristicaController implements GenericoController<Caracteristica> {
    @Autowired
    private ICaracteristicaService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<Caracteristica> caracteristicas=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, caracteristicas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<Caracteristica> caracteristicas = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, caracteristicas);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Caracteristica caracteristica=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, caracteristica);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid Caracteristica _caracteristica) {
        Caracteristica caracteristica=servicio.crear(_caracteristica);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, caracteristica);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Caracteristica _caracteristica) {
        Caracteristica caracteristica=servicio.actualizar(_caracteristica);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, caracteristica);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> importar(MultipartFile file) {
        return null;
    }

    @GetMapping(value = "/tipo/bien/{productoId}/existencias", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarBienExistencias(@PathVariable("productoId") long productoId) {
        List<Caracteristica> caracteristicas=servicio.consultarBienExistencias(new Producto(productoId));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, caracteristicas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/tipo/bien/{productoId}/existencias/{bodegaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarBienExistenciasBodega(@PathVariable("productoId") long productoId, @PathVariable("bodegaId") long bodegaId) {
        List<Caracteristica> caracteristicas=servicio.consultarBienExistenciasBodega(new Producto(productoId), new Bodega(bodegaId));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, caracteristicas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
