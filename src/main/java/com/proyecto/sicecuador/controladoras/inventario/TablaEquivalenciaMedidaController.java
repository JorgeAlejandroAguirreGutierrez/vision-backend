package com.proyecto.sicecuador.controladoras.inventario;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathTablaEquivalenciaMedida;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.inventario.Medida;
import com.proyecto.sicecuador.modelos.inventario.TablaEquivalenciaMedida;
import com.proyecto.sicecuador.servicios.interf.inventario.ITablaEquivalenciaMedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(contexto+pathTablaEquivalenciaMedida)
public class TablaEquivalenciaMedidaController implements GenericoController<TablaEquivalenciaMedida> {
    @Autowired
    private ITablaEquivalenciaMedidaService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<TablaEquivalenciaMedida> tablas=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, tablas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        TablaEquivalenciaMedida tabla=servicio.obtener(new TablaEquivalenciaMedida(id)).get();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, tabla);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid TablaEquivalenciaMedida _tabla) {
        TablaEquivalenciaMedida tabla=servicio.crear(_tabla);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, tabla);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody TablaEquivalenciaMedida _tabla) {
        TablaEquivalenciaMedida tabla=servicio.actualizar(_tabla);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, tabla);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        TablaEquivalenciaMedida tabla=servicio.eliminar(new TablaEquivalenciaMedida(id));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, tabla);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PostMapping(value = "/buscar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscar(@RequestBody TablaEquivalenciaMedida tem) {
    	List<TablaEquivalenciaMedida> tems=servicio.buscar(tem);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, tems);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{medida1_id}/{medida2_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerMedida1Medida2(@PathVariable("medida1_id") long medida1_id, @PathVariable("medida2_id") long medida2_id) {
        TablaEquivalenciaMedida tabla=servicio.obtenerMedida1Medida2(new TablaEquivalenciaMedida(new Medida(medida1_id), new Medida(medida2_id))).get();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, tabla);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> importar(MultipartFile file) {
        return null;
    }
}
