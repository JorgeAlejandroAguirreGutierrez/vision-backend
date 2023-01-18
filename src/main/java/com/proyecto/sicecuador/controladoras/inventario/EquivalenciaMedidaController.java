package com.proyecto.sicecuador.controladoras.inventario;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathTablaEquivalenciaMedida;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.inventario.Medida;
import com.proyecto.sicecuador.modelos.inventario.EquivalenciaMedida;
import com.proyecto.sicecuador.servicios.interf.inventario.IEquivalenciaMedidaService;
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
@RequestMapping(contexto+pathTablaEquivalenciaMedida)
public class EquivalenciaMedidaController implements GenericoController<EquivalenciaMedida> {
    @Autowired
    private IEquivalenciaMedidaService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<EquivalenciaMedida> tablas=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, tablas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/consultarActivos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarActivos() {
	    List<EquivalenciaMedida> equivalenciasMedidas= servicio.consultarActivos();
	    Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, equivalenciasMedidas);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<EquivalenciaMedida> tablas = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, tablas);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        EquivalenciaMedida tabla=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, tabla);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid EquivalenciaMedida _tabla) {
        EquivalenciaMedida tabla=servicio.crear(_tabla);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, tabla);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody EquivalenciaMedida _tabla) {
        EquivalenciaMedida tabla=servicio.actualizar(_tabla);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, tabla);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody EquivalenciaMedida _equivalenciaMedida) {
    	EquivalenciaMedida equivalenciaMedida = servicio.activar(_equivalenciaMedida);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_activar_exitoso, equivalenciaMedida);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody EquivalenciaMedida _equivalenciaMedida) {
    	EquivalenciaMedida equivalenciaMedida=servicio.inactivar(_equivalenciaMedida);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_inactivar_exitoso, equivalenciaMedida);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PostMapping(value = "/buscar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscar(@RequestBody EquivalenciaMedida tem) {
    	List<EquivalenciaMedida> tems=servicio.buscar(tem);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, tems);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{medidaIniId}/{medidaEquiId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerMedidaIniMedidaEqui(@PathVariable("medidaIniId") long medidaIniId, @PathVariable("medidaEquiId") long medidaEquiId) {
        EquivalenciaMedida tabla=servicio.obtenerMedida1Medida2(new EquivalenciaMedida(new Medida(medidaIniId), new Medida(medidaEquiId)));
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, tabla);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/buscarMedidasEquivalentes/{medidaIniId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarMedidasEquivalentes(@PathVariable("medidaIniId") long medidaIniId) {
    	EquivalenciaMedida parametro = new EquivalenciaMedida();
    	parametro.setMedidaIni(new Medida(medidaIniId));
        List<EquivalenciaMedida> tablas=servicio.buscarMedidasEquivalentes(parametro);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, tablas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }    
    @Override
    public ResponseEntity<?> importar(MultipartFile file) {
        return null;
    }
}
