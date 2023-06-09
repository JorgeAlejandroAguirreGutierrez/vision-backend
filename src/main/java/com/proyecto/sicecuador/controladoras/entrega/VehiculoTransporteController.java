package com.proyecto.sicecuador.controladoras.entrega;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathVehiculoTransporte;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.cajaBanco.Banco;
import com.proyecto.sicecuador.modelos.entrega.VehiculoTransporte;
import com.proyecto.sicecuador.servicios.interf.entrega.IVehiculoTransporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(contexto+pathVehiculoTransporte)
public class VehiculoTransporteController implements GenericoController<VehiculoTransporte> {
    @Autowired
    private IVehiculoTransporteService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<VehiculoTransporte> transportistas=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, transportistas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
	    List<VehiculoTransporte> vehiculosTransportes = servicio.consultarPorEstado(estado);
	    Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, vehiculosTransportes);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorTransportistaYEstado/{transportistaId}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("transportistaId") long transportistaId, @PathVariable("estado") String estado) {
        List<VehiculoTransporte> vehiculosTransportes = servicio.consultarPorTransportistaYEstado(transportistaId, estado);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, vehiculosTransportes);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<VehiculoTransporte> transportistas = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, transportistas);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        VehiculoTransporte transportista=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, transportista);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid VehiculoTransporte _transportista) {
        VehiculoTransporte transportista=servicio.crear(_transportista);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, transportista);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody VehiculoTransporte _transportista) {
        VehiculoTransporte transportista=servicio.actualizar(_transportista);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, transportista);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody VehiculoTransporte _vehiculoTransporte) {
    	VehiculoTransporte vehiculoTransporte = servicio.activar(_vehiculoTransporte);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_activar_exitoso, vehiculoTransporte);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody VehiculoTransporte _vehiculoTransporte) {
    	VehiculoTransporte vehiculoTransporte=servicio.inactivar(_vehiculoTransporte);
        Respuesta respuesta= new Respuesta(true, Constantes.mensaje_inactivar_exitoso, vehiculoTransporte);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
