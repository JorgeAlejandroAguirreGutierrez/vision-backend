package com.proyecto.sicecuador.controladoras.comprobante;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.compra.FacturaCompra;
import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.modelos.comprobante.FacturaLinea;
import com.proyecto.sicecuador.servicios.interf.comprobante.IFacturaService;
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

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathFactura;

@RestController
@RequestMapping(contexto+pathFactura)
public class FacturaController implements GenericoController<Factura> {
    @Autowired
    private IFacturaService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<Factura> facturas=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, facturas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping(value = "/consultarActivos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarActivos() {
	    List<Factura> facturas= servicio.consultarActivos();
	    Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, facturas);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<Factura> facturas = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, facturas);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Factura factura=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, factura);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody Factura _factura) {
        Factura factura=servicio.crear(_factura);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, factura);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PostMapping(value = "/buscar",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buscar(@RequestBody Factura factura) {
        List<Factura> facturas=servicio.buscar(factura);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_consultar_exitoso, facturas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Factura _factura) {
        Factura factura=servicio.actualizar(_factura);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, factura);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody Factura _factura) {
    	Factura factura = servicio.activar(_factura);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_activar_exitoso, factura);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody Factura _factura) {
    	Factura factura = servicio.inactivar(_factura);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_inactivar_exitoso, factura);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PostMapping(value = "/calcular", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> calcular(@RequestBody @Valid Factura _factura) {
        Factura factura=servicio.calcular(_factura);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_calcular_exitoso, factura);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/calcularLinea", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> calcularLinea(@RequestBody FacturaLinea _facturaLinea) {
        FacturaLinea facturaLinea=servicio.calcularLinea(_facturaLinea);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_calcular_exitoso, facturaLinea);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorCliente/{clienteId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorProveedor(@PathVariable("clienteId") long clienteId) {
        List<Factura> facturas = servicio.consultarPorCliente(clienteId);
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, facturas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
