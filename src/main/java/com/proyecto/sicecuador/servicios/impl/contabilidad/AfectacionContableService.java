package com.proyecto.vision.servicios.impl.contabilidad;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.modelos.configuracion.Ubicacion;
import com.proyecto.vision.modelos.contabilidad.AfectacionContable;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.repositorios.contabilidad.IAfectacionContableRepository;
import com.proyecto.vision.servicios.interf.contabilidad.IAfectacionContableService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AfectacionContableService implements IAfectacionContableService {
    @Autowired
    private IAfectacionContableRepository rep;

    @Override
    public void validar(AfectacionContable afectacionContable) {
        if(afectacionContable.getDescripcion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.descripcion);
        if(afectacionContable.getAbreviatura().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.abreviatura);
    }
    
    @Override
    public AfectacionContable crear(AfectacionContable afectacionContable) {
        validar(afectacionContable);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_afectacion_contable);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	afectacionContable.setCodigo(codigo.get());
    	afectacionContable.setEstado(Constantes.activo);
    	return rep.save(afectacionContable);
    }

    @Override
    public AfectacionContable actualizar(AfectacionContable afectacionContable) {
    	validar(afectacionContable);
        return rep.save(afectacionContable);
    }

    @Override
    public AfectacionContable activar(AfectacionContable afectacionContable) {
        validar(afectacionContable);
        afectacionContable.setEstado(Constantes.activo);
        return rep.save(afectacionContable);
    }

    @Override
    public AfectacionContable inactivar(AfectacionContable afectacionContable) {
        validar(afectacionContable);
        afectacionContable.setEstado(Constantes.inactivo);
        return rep.save(afectacionContable);
    }

    @Override
    public AfectacionContable obtener(long id) {
        Optional<AfectacionContable> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.afectacion_contable);
    }

    @Override
    public List<AfectacionContable> consultar() {
        return rep.consultar();
    }
    
    @Override
    public List<AfectacionContable> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public Page<AfectacionContable> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public List<AfectacionContable> buscar(AfectacionContable afectacionContable) {
        return  rep.buscar(afectacionContable.getDescripcion(), Constantes.activo);
    }
}
