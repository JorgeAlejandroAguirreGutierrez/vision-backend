package com.proyecto.vision.servicios.impl.configuracion;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.modelos.configuracion.EstadoCivil;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.repositorios.configuracion.IEstadoCivilRepository;
import com.proyecto.vision.servicios.interf.configuracion.IEstadoCivilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class EstadoCivilService implements IEstadoCivilService {
	
    @Autowired
    private IEstadoCivilRepository rep;

    @Override
    public void validar(EstadoCivil estadoCivil) {
        if(estadoCivil.getDescripcion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.descripcion);
        if(estadoCivil.getAbreviatura().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.abreviatura);
    }
    
    @Override
    public EstadoCivil crear(EstadoCivil estadoCivil) {
        validar(estadoCivil);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_estado_civil);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	estadoCivil.setCodigo(codigo.get());
    	estadoCivil.setEstado(Constantes.estadoActivo);
    	return rep.save(estadoCivil);
    }

    @Override
    public EstadoCivil actualizar(EstadoCivil estadoCivil) {
        validar(estadoCivil);
        return rep.save(estadoCivil);
    }

    @Override
    public EstadoCivil obtener(long id) {
        Optional<EstadoCivil> res=rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.estado_civil);
    }

    @Override
    public List<EstadoCivil> consultar() {
        return rep.consultar();
    }
    
    @Override
    public List<EstadoCivil> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }
    
    @Override
    public EstadoCivil activar(EstadoCivil estadoCivil) {
        validar(estadoCivil);
        estadoCivil.setEstado(Constantes.estadoActivo);
        return rep.save(estadoCivil);
    }

    @Override
    public EstadoCivil inactivar(EstadoCivil estadoCivil) {
        validar(estadoCivil);
        estadoCivil.setEstado(Constantes.estadoInactivo);
        return rep.save(estadoCivil);
    }
}
