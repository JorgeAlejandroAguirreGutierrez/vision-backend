package com.proyecto.vision.controladoras.inventario;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathEquivalenciaMedida;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.controladoras.GenericoController;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.inventario.Medida;
import com.proyecto.vision.modelos.inventario.EquivalenciaMedida;
import com.proyecto.vision.servicios.interf.inventario.IEquivalenciaMedidaService;
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
@RequestMapping(contexto+pathEquivalenciaMedida)
public class EquivalenciaMedidaController implements GenericoController<EquivalenciaMedida> {
    @Autowired
    private IEquivalenciaMedidaService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<EquivalenciaMedida> medidasEquivalentes=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, medidasEquivalentes);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
        List<EquivalenciaMedida> medidasEquivalentes = servicio.consultarPorEstado(estado);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, medidasEquivalentes);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        EquivalenciaMedida medidaEquivalente=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, medidaEquivalente);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/obtenerPorMedidaIniYMedidaFin/{medidaIni_id}/{medidaFin_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtenerPorMedidaIniYMedidaFin(@PathVariable("medidaIni_id") long medidaIni_id, @PathVariable("medidaFin_id") long medidaFin_id) {
        EquivalenciaMedida equivalenciaMedida=servicio.obtenerPorMedidaIniYMedidaFin(medidaIni_id, medidaFin_id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, equivalenciaMedida);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid EquivalenciaMedida _equivalenciaMedida) {
        EquivalenciaMedida equivalenciaMedida=servicio.crear(_equivalenciaMedida);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, equivalenciaMedida);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody EquivalenciaMedida _equivalenciaMedida) {
        EquivalenciaMedida equivalenciaMedida=servicio.actualizar(_equivalenciaMedida);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, equivalenciaMedida);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody EquivalenciaMedida _equivalenciaMedida) {
        EquivalenciaMedida equivalenciaMedida= servicio.activar(_equivalenciaMedida);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_activar_exitoso, equivalenciaMedida);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody EquivalenciaMedida _equivalenciaMedida) {
        EquivalenciaMedida equivalenciaMedida = servicio.inactivar(_equivalenciaMedida);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_inactivar_exitoso, equivalenciaMedida);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PostMapping(value = "/buscar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscar(@RequestBody EquivalenciaMedida tem) {
    	List<EquivalenciaMedida> tems=servicio.buscar(tem);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, tems);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/buscarmedidasequivalentes/{medidaIni_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscarMedidasEquivalentes(@PathVariable("medidaIni_id") long medidaIni_id) {
    	EquivalenciaMedida parametro = new EquivalenciaMedida();
    	parametro.setMedidaIni(new Medida(medidaIni_id));
        List<EquivalenciaMedida> tablas=servicio.buscarMedidasEquivalentes(parametro);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, tablas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }    



}
