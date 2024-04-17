package com.proyecto.vision.servicios.impl.acceso;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.acceso.Perfil;
import com.proyecto.vision.repositorios.acceso.IPerfilRepository;
import com.proyecto.vision.servicios.interf.acceso.IPerfilService;
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
    }
    
    @Override
    public Perfil crear(Perfil perfil) {
        validar(perfil);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_perfil);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	perfil.setCodigo(codigo.get());
    	perfil.setEstado(Constantes.estadoActivo);
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
        perfil.setEstado(Constantes.estadoActivo);
        Perfil res = rep.save(perfil);
        res.normalizar();
        return res;
    }

    @Override
    public Perfil inactivar(Perfil perfil) {
        validar(perfil);
        perfil.setEstado(Constantes.estadoInactivo);
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
        return rep.consultar();
    }
    
    @Override
    public List<Perfil> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public Page<Perfil> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}
