package com.proyecto.sicecuador.servicios.impl.configuracion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.configuracion.Parametro;
import com.proyecto.sicecuador.repositorios.configuracion.IParametroRepository;
import com.proyecto.sicecuador.servicios.interf.configuracion.IParametroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ParametroService implements IParametroService {
    @Autowired
    private IParametroRepository rep;

    @Override
    public void validar(Parametro parametro) {
        if(parametro.getTipo().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.tipo);
        if(parametro.getNombre().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.nombre);
        if(parametro.getTabla().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.tabla);
        if(parametro.getAbreviatura().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.abreviatura);
    }
    
    @Override
    public Parametro crear(Parametro parametro) {
        validar(parametro);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_parametro);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	parametro.setCodigo(codigo.get());
    	parametro.setEstado(Constantes.activo);
    	return rep.save(parametro);
    }

    @Override
    public Parametro actualizar(Parametro parametro) {
        validar(parametro);
        return rep.save(parametro);
    }

    @Override
    public Parametro activar(Parametro parametro) {
        validar(parametro);
        parametro.setEstado(Constantes.activo);
        return rep.save(parametro);
    }

    @Override
    public Parametro inactivar(Parametro parametro) {
        validar(parametro);
        parametro.setEstado(Constantes.inactivo);
        return rep.save(parametro);
    }

    @Override
    public Parametro obtener(long id) {
        Optional<Parametro> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.parametro);
    }

    @Override
    public List<Parametro> consultar() {
        return rep.consultar();
    }
    
    @Override
    public List<Parametro> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<Parametro> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public Parametro obtenerPorTipo(String tipo) {
        Optional<Parametro> res= rep.findByTipo(tipo, Constantes.activo);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.parametro);
    }

    /*@Override
    public Parametro obtenerPorTipoTabla(Parametro parametro) {
        Optional<Parametro> res= rep.findByTablaAndTipo(parametro.getTabla(), parametro.getTipo(), Constantes.activo);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.parametro);
    }*/
    
    @Override
    public List<Parametro> consultarPorTipo(String tipo) {
        return rep.AllByTipo(tipo, Constantes.activo);
    }
}
