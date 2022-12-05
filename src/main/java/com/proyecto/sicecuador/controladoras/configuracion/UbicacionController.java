package com.proyecto.sicecuador.controladoras.configuracion;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathUbicacion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.servicios.interf.configuracion.IUbicacionService;
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
@RequestMapping(contexto+pathUbicacion)
public class UbicacionController implements GenericoController<Ubicacion> {
    @Autowired
    private IUbicacionService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<Ubicacion> ubicaciones=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, ubicaciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/consultarActivos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarActivos() {
	    List<Ubicacion> ubicaciones = servicio.consultarActivos();
	    Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, ubicaciones);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<Ubicacion> ubicaciones = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, ubicaciones);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Ubicacion ubicacion=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, ubicacion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid Ubicacion _ubicacion) {
        Ubicacion ubicacion=servicio.crear(_ubicacion);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, ubicacion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Ubicacion _ubicacion) {
        Ubicacion ubicacion=servicio.actualizar(_ubicacion);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, ubicacion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody Ubicacion _ubicacion) {
    	Ubicacion ubicacion = servicio.activar(_ubicacion);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_activar_exitoso, ubicacion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody Ubicacion _ubicacion) {
    	Ubicacion ubicacion=servicio.inactivar(_ubicacion);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_inactivar_exitoso, ubicacion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/provincia",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarProvincias() {
        List<Ubicacion> ubicaciones=servicio.consultarProvincias();
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, ubicaciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/provincia/{provincia}/canton", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarCantones(@PathVariable("provincia") String provincia) {
        Ubicacion ubicacion=new Ubicacion();
        ubicacion.setProvincia(provincia);
        List<Ubicacion> ubicaciones=servicio.consultarCantones(ubicacion);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, ubicaciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/provincia/canton/{canton}/parroquia", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarParroquias(@PathVariable("canton") String canton) {
        Ubicacion ubicacion=new Ubicacion();
        ubicacion.setCanton(canton);
        List<Ubicacion> ubicaciones=servicio.consultarParroquias(ubicacion);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, ubicaciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/{provincia}/{canton}/{parroquia}/id", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerUbicacionID(@PathVariable("provincia") String provincia,
                                                @PathVariable("canton") String canton,
                                                @PathVariable("parroquia") String parroquia) {
        Ubicacion ubicacion=new Ubicacion(provincia, canton, parroquia);
        Ubicacion _ubicacion=servicio.obtenerUbicacionId(ubicacion);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, _ubicacion);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping(value = "/buscar/{codigoNorma}/{provincia}/{canton}/{parroquia}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscar(@PathVariable("codigoNorma") String codigoNorma, @PathVariable("provincia") String provincia,
                                          @PathVariable("canton") String canton, @PathVariable("parroquia") String parroquia) {
        List<Ubicacion> ubicaciones=servicio.buscar(new Ubicacion(codigoNorma, provincia, canton, parroquia));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, ubicaciones);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @PostMapping(value = "/importar", headers = "content-type=multipart/*", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> importar(@RequestPart("archivo") MultipartFile archivo) {
        servicio.importar(archivo);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, null);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

}
