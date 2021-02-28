package com.proyecto.sicecuador.controladoras.cliente;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathGrupoCliente;
import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.cliente.GrupoCliente;
import com.proyecto.sicecuador.servicios.interf.cliente.IGrupoClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping(contexto+pathGrupoCliente)
public class GrupoClienteController implements GenericoController<GrupoCliente> {
    @Autowired
    private IGrupoClienteService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<GrupoCliente> grupos_clientes = servicio.consultar();
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, grupos_clientes);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        GrupoCliente grupo_cliente=servicio.obtener(new GrupoCliente(id)).get();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, grupo_cliente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid GrupoCliente _grupoCliente) {
        GrupoCliente grupo_cliente=servicio.crear(_grupoCliente);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, grupo_cliente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody GrupoCliente _grupoCliente) {
        GrupoCliente grupo_cliente=servicio.actualizar(_grupoCliente);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, grupo_cliente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        GrupoCliente grupo_cliente=servicio.eliminar(new GrupoCliente(id));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, grupo_cliente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/importar", headers = "content-type=multipart/*", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> importar(@RequestPart("archivo") MultipartFile archivo) {
        boolean bandera=servicio.importar(archivo);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, bandera);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PostMapping(value = "/buscar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscar(@RequestBody GrupoCliente _grupo_cliente) {
    	List<GrupoCliente> grupos_clientes=servicio.buscar(_grupo_cliente);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, grupos_clientes);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
