package com.proyecto.sicecuador.controladoras.usuario;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathPuntoVenta;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.usuario.Establecimiento;
import com.proyecto.sicecuador.modelos.usuario.PuntoVenta;
import com.proyecto.sicecuador.servicios.interf.usuario.IPuntoVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping(contexto+pathPuntoVenta)
public class PuntoVentaController implements GenericoController<PuntoVenta> {
    @Autowired
    private IPuntoVentaService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<PuntoVenta> punto_ventaes=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, punto_ventaes);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        PuntoVenta punto_venta=servicio.obtener(new PuntoVenta(id)).get();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, punto_venta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid PuntoVenta _punto_venta) {
        PuntoVenta punto_venta=servicio.crear(_punto_venta);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, punto_venta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody PuntoVenta _punto_venta) {
        PuntoVenta punto_venta=servicio.actualizar(_punto_venta);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, punto_venta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        PuntoVenta punto_venta=servicio.eliminar(new PuntoVenta(id));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, punto_venta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/establecimiento/{establecimiento_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarEstablecimiento(@PathVariable("establecimiento_id") long establecimiento_id) {
        List<PuntoVenta> punto_venta=servicio.consultarEstablecimiento(new Establecimiento(establecimiento_id));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, punto_venta);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @PostMapping(value = "/importar", headers = "content-type=multipart/*", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> importar(MultipartFile archivo) {
        boolean bandera=servicio.importar(archivo);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, bandera);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
