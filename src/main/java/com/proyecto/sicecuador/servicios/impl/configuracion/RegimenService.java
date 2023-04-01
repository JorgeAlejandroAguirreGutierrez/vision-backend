package com.proyecto.sicecuador.servicios.impl.configuracion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.configuracion.Regimen;
import com.proyecto.sicecuador.repositorios.usuario.IRegimenRepository;
import com.proyecto.sicecuador.servicios.interf.configuracion.IRegimenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegimenService implements IRegimenService {
    @Autowired
    private IRegimenRepository rep;

    @Override
    public void validar(Regimen regimen) {
        if(regimen.getDescripcion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.descripcion);
        if(regimen.getAbreviatura().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.abreviatura);
    }
    
    @Override
    public Regimen crear(Regimen regimen) {
        validar(regimen);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_regimen);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	regimen.setCodigo(codigo.get());
    	regimen.setEstado(Constantes.activo);
    	return rep.save(regimen);
    }

    @Override
    public Regimen actualizar(Regimen regimen) {
        validar(regimen);
        return rep.save(regimen);
    }

    @Override
    public Regimen activar(Regimen regimen) {
        validar(regimen);
        regimen.setEstado(Constantes.activo);
        return rep.save(regimen);
    }

    @Override
    public Regimen inactivar(Regimen regimen) {
        validar(regimen);
        regimen.setEstado(Constantes.inactivo);
        return rep.save(regimen);
    }

    @Override
    public Regimen obtener(long id) {
        Optional<Regimen> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.regimen);
    }

    @Override
    public List<Regimen> consultar() {
        return rep.consultar();
    }
    
    @Override
    public List<Regimen> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }
    
    @Override
    public Page<Regimen> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public List<Regimen> buscar(Regimen regimen) {
        return  rep.buscar(regimen.getCodigo(), regimen.getDescripcion(), regimen.getAbreviatura());
    }
}
