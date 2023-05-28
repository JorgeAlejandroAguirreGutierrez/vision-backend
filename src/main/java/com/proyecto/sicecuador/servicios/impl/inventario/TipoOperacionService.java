package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.inventario.TipoOperacion;
import com.proyecto.sicecuador.repositorios.inventario.ITipoOperacionRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.ITipoOperacionService;
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
    	tipoOperacion.setEstado(Constantes.activo);
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
        tipoOperacion.setEstado(Constantes.activo);
        return rep.save(tipoOperacion);
    }

    @Override
    public TipoOperacion inactivar(TipoOperacion tipoOperacion) {
        validar(tipoOperacion);
        tipoOperacion.setEstado(Constantes.inactivo);
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
    public List<TipoOperacion> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<TipoOperacion> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}
