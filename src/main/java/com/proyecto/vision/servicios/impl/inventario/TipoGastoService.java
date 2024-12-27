package com.proyecto.vision.servicios.impl.inventario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.inventario.TipoGasto;
import com.proyecto.vision.repositorios.inventario.ITipoGastoRepository;
import com.proyecto.vision.servicios.interf.inventario.ITipoGastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoGastoService implements ITipoGastoService {
    @Autowired
    private ITipoGastoRepository rep;

    @Override
    public void validar(TipoGasto tipoGasto) {
        if(tipoGasto.getDescripcion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.descripcion);
        if(tipoGasto.getAbreviatura().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.abreviatura);
    }
    
    @Override
    public TipoGasto crear(TipoGasto tipoGasto) {
        validar(tipoGasto);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_tipo_gasto);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	tipoGasto.setCodigo(codigo.get());
    	return rep.save(tipoGasto);
    }

    @Override
    public TipoGasto actualizar(TipoGasto tipoGasto) {
        validar(tipoGasto);
        return rep.save(tipoGasto);
    }

    @Override
    public TipoGasto obtener(long id) {
        Optional<TipoGasto> res=rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.tipo_gasto);
    }

    @Override
    public List<TipoGasto> consultar() {
        return rep.consultar();
    }
}
