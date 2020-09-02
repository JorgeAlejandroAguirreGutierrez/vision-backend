package com.proyecto.sicecuador.controladoras.inventario;

import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.inventario.SaldoInicialInventario;
import com.proyecto.sicecuador.modelos.inventario.Segmento;
import com.proyecto.sicecuador.servicios.interf.inventario.ISaldoInicialInventarioService;
import com.proyecto.sicecuador.servicios.interf.inventario.ISegmentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sicecuador/saldoinicialinventario")
public class SaldoInicialInventarioController implements GenericoController<SaldoInicialInventario> {

    @Autowired
    private ISaldoInicialInventarioService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        try {
            List<SaldoInicialInventario> saldos_iniciales_inventarios=servicio.consultar();
            Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, saldos_iniciales_inventarios);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        try {
            Optional<SaldoInicialInventario> saldo_inicial_inventario=servicio.obtener(new SaldoInicialInventario(id));
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, saldo_inicial_inventario);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody SaldoInicialInventario _saldo_inicial_inventario) {
        try {
            SaldoInicialInventario saldo_inicial_inventario=servicio.actualizar(_saldo_inicial_inventario);
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, saldo_inicial_inventario);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid SaldoInicialInventario _saldo_inicial_inventario, BindingResult bindig_result) {
        try {
            SaldoInicialInventario saldo_inicial_inventario=servicio.crear(_saldo_inicial_inventario);
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, saldo_inicial_inventario);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        try {
            SaldoInicialInventario saldo_inicial_inventario=servicio.eliminar(new SaldoInicialInventario(id));
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, saldo_inicial_inventario);
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
