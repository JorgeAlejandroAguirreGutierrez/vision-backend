package com.proyecto.sicecuador.controladoras.inventario;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathMedidaPrecio;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.inventario.MedidaPrecio;
import com.proyecto.sicecuador.servicios.interf.inventario.IMedidaPrecioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping(contexto+pathMedidaPrecio)
public class MedidaPrecioController implements GenericoController<MedidaPrecio> {
    @Autowired
    private IMedidaPrecioService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<MedidaPrecio> medida_precios=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, medida_precios);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        MedidaPrecio medida_precio=servicio.obtener(new MedidaPrecio(id)).get();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, medida_precio);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid MedidaPrecio _medida_precio) {
        MedidaPrecio medida_precio=servicio.crear(_medida_precio);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, medida_precio);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody MedidaPrecio _medida_precio) {
        MedidaPrecio medida_precio=servicio.actualizar(_medida_precio);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, medida_precio);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        MedidaPrecio medida_precio=servicio.eliminar(new MedidaPrecio(id));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, medida_precio);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> importar(MultipartFile file) {
        return null;
    }
}
