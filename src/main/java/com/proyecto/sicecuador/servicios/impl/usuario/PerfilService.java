package com.proyecto.sicecuador.servicios.impl.usuario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.usuario.Perfil;
import com.proyecto.sicecuador.repositorios.usuario.IPerfilRepository;
import com.proyecto.sicecuador.servicios.interf.usuario.IPerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class PerfilService implements IPerfilService {
    @Autowired
    private IPerfilRepository rep;

    @Override
    public void validar(Perfil perfil) {
        if(perfil.getDescripcion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.descripcion);
        if(perfil.getAbreviatura().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.abreviatura);
        if(perfil.getMultiempresa().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.multiempresa);
        if(perfil.getPermisos().isEmpty()) throw new DatoInvalidoException(Constantes.permiso);
    }
    
    @Override
    public Perfil crear(Perfil perfil) {
        validar(perfil);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_perfil);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	perfil.setCodigo(codigo.get());
    	perfil.setEstado(Constantes.activo);
    	Perfil res = rep.save(perfil);
        res.normalizar();
        return res;
    }

    @Override
    public Perfil actualizar(Perfil perfil) {
        validar(perfil);
        Perfil res = rep.save(perfil);
        res.normalizar();
        return res;
    }

    @Override
    public Perfil activar(Perfil perfil) {
        validar(perfil);
        perfil.setEstado(Constantes.activo);
        Perfil res = rep.save(perfil);
        res.normalizar();
        return res;
    }

    @Override
    public Perfil inactivar(Perfil perfil) {
        validar(perfil);
        perfil.setEstado(Constantes.inactivo);
        Perfil res = rep.save(perfil);
        res.normalizar();
        return res;
    }

    @Override
    public Perfil obtener(long id) {
        Optional<Perfil> perfil= rep.findById(id);
        if(perfil.isPresent()) {
        	Perfil res = perfil.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.perfil);
    }

    @Override
    public List<Perfil> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<Perfil> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<Perfil> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}
