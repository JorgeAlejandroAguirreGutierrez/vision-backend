package com.proyecto.sicecuador.controladoras.inventario;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathPresentacionProducto;
import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.inventario.PresentacionProducto;
import com.proyecto.sicecuador.servicios.interf.inventario.IPresentacionProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(contexto+pathPresentacionProducto)
public class PresentacionProductoController implements GenericoController<PresentacionProducto> {
    @Autowired
    private IPresentacionProductoService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<PresentacionProducto> presentaciones_productos=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, presentaciones_productos);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        PresentacionProducto presentacion_producto=servicio.obtener(new PresentacionProducto(id)).get();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, presentacion_producto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid PresentacionProducto _presentacion_producto) {
    	PresentacionProducto presentacion_producto=servicio.crear(_presentacion_producto);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, presentacion_producto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody PresentacionProducto _presentacion_producto) {
        PresentacionProducto presentacion_producto=servicio.actualizar(_presentacion_producto);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, presentacion_producto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        PresentacionProducto presentacion_producto=servicio.eliminar(new PresentacionProducto(id));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, presentacion_producto);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PostMapping(value = "/buscar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscar(@RequestBody PresentacionProducto presentacion_producto) {
        	List<PresentacionProducto> presentaciones_productos=servicio.buscar(presentacion_producto);
            Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, presentaciones_productos);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> importar(MultipartFile file) {
        return null;
    }
}
