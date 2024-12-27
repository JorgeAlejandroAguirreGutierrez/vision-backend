package com.proyecto.vision.servicios.impl.acceso;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.*;
import com.proyecto.vision.modelos.cliente.Contribuyente;
import com.proyecto.vision.modelos.configuracion.TipoIdentificacion;
import com.proyecto.vision.modelos.acceso.Empresa;
import com.proyecto.vision.repositorios.cliente.IContribuyenteRepository;
import com.proyecto.vision.repositorios.configuracion.ITipoIdentificacionRepository;
import com.proyecto.vision.repositorios.acceso.IEmpresaRepository;
import com.proyecto.vision.servicios.interf.acceso.IEmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService implements IEmpresaService {
    @Autowired
    private IEmpresaRepository rep;
    @Autowired
    private ITipoIdentificacionRepository repTipoIdentificacion;
    @Autowired
    private IContribuyenteRepository repContribuyente;

    private final Path certificados = Paths.get(Constantes.pathRecursos +  Constantes.pathCertificados);
    private final Path logos = Paths.get(Constantes.pathRecursos +  Constantes.pathLogos);

    @Override
    public void validar(Empresa empresa) {
        if(empresa.getIdentificacion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.identificacion);
        if(empresa.getRazonSocial().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.razonSocial);
        if(empresa.getNombreComercial().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.nombreComercial);
        if(empresa.getObligadoContabilidad().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.obligadoContabilidad);
        if(empresa.getDireccion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.direccion);
        if(empresa.getContrasena().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.contrasena);
        if(empresa.getContrasenaSRI().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.contrasenaSRI);
        if(empresa.getTipoIdentificacion().getId() ==  Constantes.ceroId) throw new DatoInvalidoException(Constantes.tipo_identificacion);
    }

    @Override
    public Empresa crear(Empresa empresa) {
        validar(empresa);
        Optional<Empresa> buscarEmpresa = rep.obtenerPorIdentificacionYEstado(empresa.getIdentificacion(), Constantes.estadoActivo);
        if(buscarEmpresa.isPresent()) {
          throw new EntidadExistenteException(Constantes.empresa);
        }
        Optional<String>codigo=Util.generarCodigo(Constantes.tabla_empresa);
        if (codigo.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        empresa.setCodigo(codigo.get());
        empresa.setEstado(Constantes.estadoActivo);
        Empresa res = rep.save(empresa);
        res.normalizar();
        return res;
    }

    @Override
    public Empresa actualizar(Empresa empresa) {
        validar(empresa);
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
        if(empresa.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.empresa);
        }
        Empresa res = empresa.get();
        res.normalizar();
        return res;
    }

    @Override
    public List<Empresa> consultar() {
        return rep.consultar();
    }

    @Override
    public Empresa validarIdentificacion(String identificacion) {
        if (identificacion!= null) {
            Optional<Empresa> res = rep.obtenerPorIdentificacionYEstado(identificacion, Constantes.estadoActivo);
            if(res.isPresent()) {
                throw new EntidadExistenteException(Constantes.empresa);
            }
            TipoIdentificacion tipoIdentificacion=null;
            if (identificacion.length() != 13) {
                throw new IdentificacionInvalidaException();
            } else if (identificacion.length() == 13 && Integer.parseInt((identificacion.substring(11,13))) == 1) {
                String cedula = identificacion.substring(0,10);
                boolean bandera = Util.verificarCedula(cedula);
                if (bandera) {
                    tipoIdentificacion = repTipoIdentificacion.obtenerPorCodigoSri("04").get();
                    Empresa empresa = new Empresa();
                    empresa.setIdentificacion(identificacion);
                    empresa.setTipoIdentificacion(tipoIdentificacion);
                    Optional<Contribuyente> contribuyente = repContribuyente.obtenerPorIdentificacion(identificacion);
                    if(contribuyente.isPresent()) {
                        empresa.setRazonSocial(contribuyente.get().getRazonSocial());
                        if (contribuyente.get().getNombreComercial()!=null) {
                            empresa.setNombreComercial(contribuyente.get().getNombreComercial());
                        }
                        if (contribuyente.get().getObligadoContabilidad()!=null) {
                            empresa.setObligadoContabilidad(contribuyente.get().getObligadoContabilidad());
                        }
                    }
                    return empresa;
                }
                throw new IdentificacionInvalidaException();
            }
            throw new IdentificacionInvalidaException();
        }
        throw new IdentificacionInvalidaException();
    }

    @Override
    public List<Empresa> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }


    @Override
    public Empresa subirCertificado(long empresaId, MultipartFile file) throws IOException {
        if (file!= null) {
            Optional<Empresa> optional = rep.findById(empresaId);
            if (optional.isEmpty()) {
                throw new EntidadNoExistenteException(Constantes.empresa);
            }
            Empresa empresa = optional.get();
            if (file.getOriginalFilename() != null) {
                Files.copy(file.getInputStream(), this.certificados.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
                empresa.setCertificado(file.getOriginalFilename());
            }
            return rep.save(empresa);
        }
        throw new IdentificacionInvalidaException();
    }

    @Override
    public Resource bajarCertificado(long empresaId) throws MalformedURLException {
        Optional<Empresa> optional = rep.findById(empresaId);
        if(optional.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.empresa);
        }
        Empresa empresa = optional.get();
        Path file = this.certificados.resolve(empresa.getCertificado());
        Resource resource = new UrlResource(file.toUri());
        return resource;
    }

    @Override
    public Resource bajarLogo(long empresaId) throws MalformedURLException {
        Optional<Empresa> optional = rep.findById(empresaId);
        if(optional.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.empresa);
        }
        Empresa empresa = optional.get();
        Path file = this.logos.resolve(empresa.getCertificado());
        Resource resource = new UrlResource(file.toUri());
        return resource;
    }
}
