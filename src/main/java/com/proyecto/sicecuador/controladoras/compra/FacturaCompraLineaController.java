package com.proyecto.sicecuador.controladoras.compra;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.compra.FacturaCompraLinea;
import com.proyecto.sicecuador.servicios.interf.compra.IFacturaCompraLineaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.pathFacturaCompraLinea;

@RestController
@RequestMapping(contexto+pathFacturaCompraLinea)
public class FacturaCompraLineaController {
    @Autowired
    private IFacturaCompraLineaService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<FacturaCompraLinea> facturasComprasLineas=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, facturasComprasLineas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
        Page<FacturaCompraLinea> facturasComprasLineas = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
        Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, facturasComprasLineas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        FacturaCompraLinea facturaCompraLinea=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, facturaCompraLinea);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody FacturaCompraLinea _facturaCompraLinea) {
        FacturaCompraLinea facturaCompraLinea=servicio.crear(_facturaCompraLinea);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, facturaCompraLinea);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody FacturaCompraLinea _facturaCompraLinea) {
        FacturaCompraLinea facturaCompraLinea=servicio.actualizar(_facturaCompraLinea);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, facturaCompraLinea);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/calcularLinea", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> calcular(@RequestBody FacturaCompraLinea _facturaCompraLinea) {
        FacturaCompraLinea facturaCompraLinea = servicio.calcularLinea(_facturaCompraLinea);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_calcular_exitoso, facturaCompraLinea);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
