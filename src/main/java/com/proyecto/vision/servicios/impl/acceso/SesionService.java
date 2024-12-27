package com.proyecto.vision.servicios.impl.acceso;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.exception.SesionInvalidaException;
import com.proyecto.vision.modelos.acceso.Empresa;
import com.proyecto.vision.modelos.acceso.Sesion;
import com.proyecto.vision.modelos.acceso.Usuario;
import com.proyecto.vision.repositorios.acceso.ISesionRepository;
import com.proyecto.vision.repositorios.acceso.IUsuarioRepository;
import com.proyecto.vision.servicios.interf.acceso.ISesionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class SesionService implements ISesionService {
    @Autowired
    private ISesionRepository rep;
    
    @Autowired
    private IUsuarioRepository rep_usuario;
    
    @Override
    public Sesion crear(Sesion sesion) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_sesion);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	sesion.setCodigo(codigo.get());
    	Optional<Usuario> usuario = rep_usuario.obtenerPorApodoYContrasenaYEstado(sesion.getUsuario().getApodo(), sesion.getUsuario().getContrasena(), Constantes.estadoActivo);
    	if(usuario.isPresent()) {
    	    Empresa empresa = sesion.getUsuario().getEstacion().getEstablecimiento().getEmpresa();
    		sesion.setUsuario(usuario.get());
    		if(sesion.getUsuario().getPerfil().getMultiempresa().equals(Constantes.si)){
    		    sesion.getUsuario().getEstacion().getEstablecimiento().setEmpresa(empresa);
            }
            sesion.setFechaApertura(new Date());
            sesion.setEstado(Constantes.estadoActivo);
            return rep.save(sesion);
    	}
    	throw new EntidadNoExistenteException(Constantes.usuario); 
    }

    @Override
    public Sesion actualizar(Sesion sesion) {
        return rep.save(sesion);
    }

    @Override
    public Sesion obtener(long id) {
        Optional<Sesion> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.sesion);
    }

    @Override
    public List<Sesion> consultar() {
        return rep.findAll();
    }

    @Override
    public Optional<Sesion> validar(Sesion _sesion) {
        final Optional<Sesion> sesion= rep.findById(_sesion.getId());
        if (sesion.isPresent()) {
        	long startTime = sesion.get().getFechaApertura().getTime();
			long endTime = new Date().getTime();
			long diffTime = endTime - startTime;
			long diffDays = diffTime / (1000 * 60 * 60 * 24);
			if(diffDays<1) {
				return sesion;
			}
			throw new SesionInvalidaException();
        }
        throw new EntidadNoExistenteException(Constantes.sesion);
    }
    
    @Override
    public Optional<Sesion> cerrar(Sesion sesion) {
        Sesion _sesion = rep.findById(sesion.getId()).get();
        sesion.setFechaCierre(new Date());
        sesion.setEstado(Constantes.estadoActivo);
        _sesion = rep.save(_sesion);
        return Optional.of(_sesion);
    }
}
