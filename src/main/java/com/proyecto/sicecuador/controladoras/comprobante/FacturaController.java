package com.proyecto.sicecuador.controladoras.comprobante;

import static com.proyecto.sicecuador.controladoras.Endpoints.contexto;
import static com.proyecto.sicecuador.controladoras.Endpoints.factura;
import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.controladoras.GenericoController;
import com.proyecto.sicecuador.modelos.Respuesta;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.servicios.interf.comprobante.IFacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(contexto+factura)
public class FacturaController implements GenericoController<Factura> {
    @Autowired
    private IFacturaService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        try {
            List<Factura> facturas=servicio.consultar();
            Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, facturas);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        try {
            Factura factura=servicio.obtener(new Factura(id)).get();
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, factura);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid Factura _factura) {
        try {
            _factura.normalizar();
            Factura factura=servicio.crear(_factura);
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, factura);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Factura _factura) {
        try {
            _factura.normalizar();
            Factura factura=servicio.actualizar(_factura);
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, factura);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable("id") long id)  {
        try {
            Factura factura=servicio.eliminar(new Factura(id));
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_eliminar_exitoso, factura);
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

    @GetMapping(value = "/buscar/numero/{numero}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarNumero(@PathVariable("numero") String numero) {
        try {
            List<Factura> factura=servicio.consultarNumero(new Factura(numero));
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, factura);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/buscar/cliente/razonsocial/{razon_social}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarClienteRazonSocial(@PathVariable("razon_social") String razon_social) {
        try {
            Factura factura=new Factura();
            factura.setCliente(new Cliente(razon_social));
            List<Factura> facturas=servicio.consultarClienteRazonSocial(factura);
            Respuesta respuesta=new Respuesta(true,Constantes.mensaje_consultar_exitoso, facturas);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/generar/pdf/{factura_id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> generarPDF(@PathVariable("factura_id") long factura_id) {
        try {
            Optional<Factura> factura=servicio.obtener(new Factura(factura_id));
            if (factura.isPresent()){
                ByteArrayInputStream pdf = servicio.generarPDF(factura.get());
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Disposition", "inline; filename=factura.pdf");
                return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
                        .body(new InputStreamResource(pdf));
            }
            Respuesta respuesta = new Respuesta(false, Constantes.mensaje_obtener_fallido, null);
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        }catch(Exception e){
            Respuesta respuesta = new Respuesta(false, e.getMessage(), null);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
