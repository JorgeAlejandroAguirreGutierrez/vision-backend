package com.proyecto.vision.controladoras.usuario;

import static com.proyecto.vision.controladoras.Endpoints.contexto;
import static com.proyecto.vision.controladoras.Endpoints.pathEmpresa;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.controladoras.GenericoController;
import com.proyecto.vision.modelos.Respuesta;
import com.proyecto.vision.modelos.compra.Proveedor;
import com.proyecto.vision.modelos.usuario.Empresa;
import com.proyecto.vision.servicios.interf.usuario.IEmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
@RestController
@RequestMapping(contexto+pathEmpresa)
public class EmpresaController implements GenericoController<Empresa> {
    @Autowired
    private IEmpresaService servicio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar() {
        List<Empresa> empresas=servicio.consultar();
        Respuesta respuesta=new Respuesta(true, Constantes.mensaje_consultar_exitoso, empresas);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/consultarPorEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPorEstado(@PathVariable("estado") String estado) {
	    List<Empresa> empresas = servicio.consultarPorEstado(estado);
	    Respuesta respuesta = new Respuesta(true, Constantes.mensaje_consultar_exitoso, empresas);
	    return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/paginas/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultarPagina(@PathVariable("page") int page){
    	Page<Empresa> empresas = servicio.consultarPagina(PageRequest.of(page, Constantes.size, Sort.by(Constantes.order)));
    	Respuesta respuesta = new Respuesta(true,Constantes.mensaje_consultar_exitoso, empresas);
    	return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> obtener(@PathVariable("id") long id) {
        Empresa empresa=servicio.obtener(id);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_obtener_exitoso, empresa);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@RequestBody @Valid Empresa _empresa) {
        Empresa empresa=servicio.crear(_empresa);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_crear_exitoso, empresa);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizar(@RequestBody Empresa _empresa) {
        Empresa empresa=servicio.actualizar(_empresa);
        Respuesta respuesta=new Respuesta(true,Constantes.mensaje_actualizar_exitoso, empresa);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PatchMapping(value = "/activar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activar(@RequestBody Empresa _empresa) {
    	Empresa empresa=servicio.activar(_empresa);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_activar_exitoso, empresa);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
   
    @PatchMapping(value = "/inactivar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inactivar(@RequestBody Empresa _empresa) {
    	Empresa empresa=servicio.inactivar(_empresa);
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_inactivar_exitoso, empresa);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/validarIdentificacion/{identificacion}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> validarIdentificacion(@PathVariable("identificacion") String identificacion) {
        Empresa empresa = servicio.validarIdentificacion(identificacion);
        empresa.normalizar();
        Respuesta respuesta= new Respuesta(true,Constantes.mensaje_obtener_exitoso, empresa);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/subirCertificado/{empresaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> subirCertificado(@PathVariable("empresaId") long empresaId, @RequestParam("file") MultipartFile file) throws IOException {
        Empresa empresa = servicio.subirCertificado(empresaId, file);
        Respuesta respuesta = new Respuesta(true,Constantes.mensaje_crear_exitoso, empresa);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping(value = "/bajarCertificado/{empresaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> bajarCertificado(@PathVariable("empresaId") long empresaId) throws MalformedURLException {
        Resource file = servicio.bajarCertificado(empresaId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping(value = "/bajarLogo/{empresaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> bajarLogo(@PathVariable("empresaId") long empresaId) throws MalformedURLException {
        Resource file = servicio.bajarLogo(empresaId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
