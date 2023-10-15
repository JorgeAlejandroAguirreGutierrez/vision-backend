package com.proyecto.vision.servicios.impl.configuracion;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.modelos.configuracion.Genero;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.repositorios.configuracion.IGeneroRepository;
import com.proyecto.vision.servicios.interf.configuracion.IGeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GeneroService implements IGeneroService {
    @Autowired
    private IGeneroRepository rep;

    @Override
    public void validar(Genero genero) {
        if(genero.getDescripcion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.descripcion);
        if(genero.getAbreviatura().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.abreviatura);
    }
    
    @Override
    public Genero crear(Genero genero) {
        validar(genero);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_genero);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	genero.setCodigo(codigo.get());
    	genero.setEstado(Constantes.estadoActivo);
    	return rep.save(genero);
    }

    @Override
    public Genero actualizar(Genero genero) {
        validar(genero);
        return rep.save(genero);
    }

    @Override
    public Genero activar(Genero genero) {
        validar(genero);
        genero.setEstado(Constantes.estadoActivo);
        return rep.save(genero);
    }

    @Override
    public Genero inactivar(Genero genero) {
        validar(genero);
        genero.setEstado(Constantes.estadoInactivo);
        return rep.save(genero);
    }

    @Override
    public Genero obtener(long id) {
        Optional<Genero> res = rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.genero);
    }

    @Override
    public List<Genero> consultar() {
        return rep.consultar();
    }
    
    @Override
    public List<Genero> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public Page<Genero> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}
