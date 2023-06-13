package com.proyecto.sicecuador.controladoras.entrega;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathVehiculo;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.entrega.Vehiculo;
import com.proyecto.sicecuador.servicios.interf.entrega.IVehiculoService;
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
@RequestMapping(contexto+pathVehiculo)
public class VehiculoController implements GenericoController<Vehiculo> {
    @Autowired
    private IVehiculoService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<Vehiculo> transportistas=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, transportistas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresa/{empresaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId) {
        List<Vehiculo> vehiculosTransportes = servicio.consultarPorEmpresa(empresaId);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, vehiculosTransportes);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
	    List<Vehiculo> vehiculosTransportes = servicio.consultarPorEstado(estado);
	    Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, vehiculosTransportes);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEmpresaYEstado/{empresaId}/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEmpresa(@PathVariable("empresaId") long empresaId, @PathVariable("estado") String estado) {
        List<Vehiculo> vehiculosTransportes = servicio.consultarPorEmpresaYEstado(empresaId, estado);
        Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, vehiculosTransportes);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<Vehiculo> transportistas = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, transportistas);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Vehiculo transportista=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, transportista);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid Vehiculo _transportista) {
        Vehiculo transportista=servicio.crear(_transportista);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, transportista);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Vehiculo _transportista) {
        Vehiculo transportista=servicio.actualizar(_transportista);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, transportista);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody Vehiculo _vehiculo) {
    	Vehiculo vehiculo = servicio.activar(_vehiculo);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_activar_exitoso, vehiculo);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody Vehiculo _vehiculo) {
    	Vehiculo vehiculo =servicio.inactivar(_vehiculo);
        Respuesta respuesta= new Respuesta(true, Constantes.mensaje_inactivar_exitoso, vehiculo);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
