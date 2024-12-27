package com.proyecto.vision.servicios.impl.inventario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.inventario.TipoOperacion;
import com.proyecto.vision.repositorios.inventario.ITipoOperacionRepository;
import com.proyecto.vision.servicios.interf.inventario.ITipoOperacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoOperacionService implements ITipoOperacionService {
    @Autowired
    private ITipoOperacionRepository rep;

    @Override
    public void validar(TipoOperacion tipoOperacion) {
        if(tipoOperacion.getDescripcion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.descripcion);
        if(tipoOperacion.getAbreviatura().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.abreviatura);
    }
    
    @Override
    public TipoOperacion crear(TipoOperacion tipoOperacion) {
        validar(tipoOperacion);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_tipo_operacion);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	tipoOperacion.setCodigo(codigo.get());
    	tipoOperacion.setEstado(Constantes.estadoActivo);
    	return rep.save(tipoOperacion);
    }

    @Override
    public TipoOperacion actualizar(TipoOperacion tipoOperacion) {
        validar(tipoOperacion);
        return rep.save(tipoOperacion);
    }

    @Override
    public TipoOperacion activar(TipoOperacion tipoOperacion) {
        validar(tipoOperacion);
        tipoOperacion.setEstado(Constantes.estadoActivo);
        return rep.save(tipoOperacion);
    }

    @Override
    public TipoOperacion inactivar(TipoOperacion tipoOperacion) {
        validar(tipoOperacion);
        tipoOperacion.setEstado(Constantes.estadoInactivo);
        return rep.save(tipoOperacion);
    }

    @Override
    public TipoOperacion obtener(long id) {
        Optional<TipoOperacion> res=rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.tipo_operacion);
    }

    @Override
    public List<TipoOperacion> consultar() {
        return rep.consultar();
    }
    
    @Override
    public List<TipoOperacion> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public TipoOperacion obtenerPorAbreviaturaYEstado(String abreviatura, String estado){
        Optional<TipoOperacion> res = rep.obtenerPorAbreviaturaYEstado(abreviatura, estado);
        if(res.isPresent()) {
            return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.tipo_operacion);
    }
}
