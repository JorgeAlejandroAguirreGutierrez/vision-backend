package com.proyecto.sicecuador.controladoras.inventario;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathLineaProducto;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.inventario.LineaProducto;
import com.proyecto.sicecuador.servicios.interf.inventario.ILineaProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(contexto+pathLineaProducto)
public class LineaProductoController implements GenericoController<LineaProducto> {
    @Autowired
    private ILineaProductoService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<LineaProducto> lineas_productos=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, lineas_productos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Optional<LineaProducto> linea_producto=servicio.obtener(new LineaProducto(id));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, linea_producto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid LineaProducto _linea_producto) {
        LineaProducto linea_producto=servicio.crear(_linea_producto);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, linea_producto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody LineaProducto _linea_producto) {
        LineaProducto linea_producto=servicio.actualizar(_linea_producto);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, linea_producto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        LineaProducto linea_producto=servicio.eliminar(new LineaProducto(id));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, linea_producto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> importar(MultipartFile file) {
        return null;
    }

}
