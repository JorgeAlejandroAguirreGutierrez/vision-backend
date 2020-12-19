package com.proyecto.sicecuador.controladoras.comprobante;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pedido;
import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.comprobante.Pedido;
import com.proyecto.sicecuador.servicios.interf.comprobante.IPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping(contexto+pedido)
public class PedidoController implements GenericoController<Pedido> {
    @Autowired
    private IPedidoService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        try {
            List<Pedido> pedidos=servicio.consultar();
            Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, pedidos);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        try {
            Pedido pedido=servicio.obtener(new Pedido(id)).get();
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, pedido);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid Pedido _pedido) {
        try {
            Pedido pedido=servicio.crear(_pedido);
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, pedido);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Pedido _pedido) {
        try {
            Pedido pedido=servicio.actualizar(_pedido);
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, pedido);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        try {
            Pedido pedido=servicio.eliminar(new Pedido(id));
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, pedido);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> importar(MultipartFile file) {
        return null;
    }
}
