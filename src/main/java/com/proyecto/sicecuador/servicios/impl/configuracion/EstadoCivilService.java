package com.proyecto.sicecuador.servicios.impl.configuracion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.modelos.configuracion.EstadoCivil;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.repositorios.configuracion.IEstadoCivilRepository;
import com.proyecto.sicecuador.servicios.interf.configuracion.IEstadoCivilService;
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
    	estadoCivil.setEstado(Constantes.activo);
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
        return rep.findAll();
    }
    
    @Override
    public List<EstadoCivil> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }
    
    @Override
    public Page<EstadoCivil> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
    
    @Override
    public EstadoCivil activar(EstadoCivil estadoCivil) {
        validar(estadoCivil);
        estadoCivil.setEstado(Constantes.activo);
        return rep.save(estadoCivil);
    }

    @Override
    public EstadoCivil inactivar(EstadoCivil estadoCivil) {
        validar(estadoCivil);
        estadoCivil.setEstado(Constantes.inactivo);
        return rep.save(estadoCivil);
    }
    
    @Override
    public List<EstadoCivil> buscar(EstadoCivil estadoCivil) {
        return rep.buscar(estadoCivil.getCodigo(), estadoCivil.getDescripcion(), estadoCivil.getAbreviatura());
    }
}