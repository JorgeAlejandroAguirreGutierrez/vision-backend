package com.proyecto.vision.servicios.impl.usuario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.exception.EntidadExistenteException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.usuario.Empresa;
import com.proyecto.vision.repositorios.usuario.IEmpresaRepository;
import com.proyecto.vision.servicios.interf.usuario.IEmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService implements IEmpresaService {
    @Autowired
    private IEmpresaRepository rep;

    private final Path root = Paths.get(Constantes.pathCertificados);

    @Override
    public void validar(Empresa empresa) {
        if(empresa.getIdentificacion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.identificacion);
        if(empresa.getRazonSocial().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.razonSocial);
        if(empresa.getNombreComercial().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.nombreComercial);
        if(empresa.getObligadoContabilidad().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.obligadoContabilidad);
        if(empresa.getDireccion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.direccion);
        if(empresa.getTipoIdentificacion().getId() ==  Constantes.ceroId) throw new DatoInvalidoException(Constantes.tipo_identificacion);
    }

    @Override
    public Empresa crear(Empresa empresa) {
        validar(empresa);
      Optional<Empresa> buscarEmpresa = rep.obtenerPorIdentificacion(empresa.getIdentificacion(), Constantes.estadoActivo);
      if(buscarEmpresa.isPresent()) {
          throw new EntidadExistenteException(Constantes.empresa);
      }
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_empresa);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	empresa.setCodigo(codigo.get());
        byte[] logoBytes = empresa.getLogo64().getBytes(StandardCharsets.UTF_8);
        empresa.setLogo(logoBytes);
    	empresa.setEstado(Constantes.estadoActivo);
    	Empresa res = rep.save(empresa);
        res.normalizar();
        return res;
    }

    @Override
    public Empresa actualizar(Empresa empresa) {
        validar(empresa);
        empresa.setLogo(empresa.getLogo64().getBytes(StandardCharsets.UTF_8));
        Empresa res = rep.save(empresa);
        res.normalizar();
        return res;
    }

    @Override
    public Empresa activar(Empresa empresa) {
        validar(empresa);
        empresa.setEstado(Constantes.estadoActivo);
        Empresa res = rep.save(empresa);
        res.normalizar();
        return res;
    }

    @Override
    public Empresa inactivar(Empresa empresa) {
        validar(empresa);
        empresa.setEstado(Constantes.estadoInactivo);
        Empresa res = rep.save(empresa);
        res.normalizar();
        return res;
    }

    @Override
    public Empresa obtener(long id) {
        Optional<Empresa> empresa = rep.findById(id);
        if(empresa.isPresent()) {
        	Empresa res = empresa.get();
        	if(res.getLogo() != null) {
        	    res.setLogo64(new String(res.getLogo(), StandardCharsets.UTF_8));
        	}
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.empresa);
    }

    @Override
    public List<Empresa> consultar() {
        List<Empresa> empresas = rep.consultar();
        if(!empresas.isEmpty()) {
            for (Empresa empresa : empresas){
                if(empresa.getLogo() != null) {
                    empresa.setLogo64(new String(empresa.getLogo(), StandardCharsets.UTF_8));
                }
            }
            return empresas;
        }
        throw new EntidadNoExistenteException(Constantes.empresa);
    }
    
    @Override
    public List<Empresa> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public Page<Empresa> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public Empresa subirCertificado(long empresaId, MultipartFile file) throws IOException {
        Optional<Empresa> optional = rep.findById(empresaId);
        if(optional.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.empresa);
        }
        Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        Empresa empresa = optional.get();
        empresa.setCertificado(file.getOriginalFilename());
        return rep.save(empresa);
    }

    @Override
    public Resource bajarCertificado(long empresaId) throws MalformedURLException {
        Optional<Empresa> optional = rep.findById(empresaId);
        if(optional.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.empresa);
        }
        Empresa empresa = optional.get();
        Path file = this.root.resolve(empresa.getCertificado());
        Resource resource = new UrlResource(file.toUri());
        return resource;
    }
}
