package com.proyecto.vision.servicios.impl.configuracion;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.configuracion.Impuesto;
import com.proyecto.vision.repositorios.configuracion.IImpuestoRepository;
import com.proyecto.vision.servicios.interf.configuracion.IImpuestoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ImpuestoService implements IImpuestoService {
    @Autowired
    private IImpuestoRepository rep;

    @Override
    public void validar(Impuesto impuesto) {
        if(impuesto.getCodigoSRI().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.codigoSRI);
        if(impuesto.getDescripcion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.descripcion);
        if(impuesto.getAbreviatura().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.abreviatura);
        if(impuesto.getPorcentaje() == Constantes.cero) throw new DatoInvalidoException(Constantes.porcentaje);
    }
    
    @Override
    public Impuesto crear(Impuesto impuesto) {
        validar(impuesto);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_impuesto);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	impuesto.setCodigo(codigo.get());
    	impuesto.setEstado(Constantes.estadoActivo);
    	return rep.save(impuesto);
    }

    @Override
    public Impuesto actualizar(Impuesto impuesto) {
        validar(impuesto);
        return rep.save(impuesto);
    }

    @Override
    public Impuesto activar(Impuesto impuesto) {
        validar(impuesto);
        impuesto.setEstado(Constantes.estadoActivo);
        return rep.save(impuesto);
    }

    @Override
    public Impuesto inactivar(Impuesto impuesto) {
        validar(impuesto);
        impuesto.setEstado(Constantes.estadoInactivo);
        return rep.save(impuesto);
    }

    @Override
    public Impuesto obtener(long id) {
        Optional<Impuesto> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.impuesto);
    }

    @Override
    public List<Impuesto> consultar() {
        return rep.consultar();
    }
    
    @Override
    public List<Impuesto> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public Page<Impuesto> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public Optional<Impuesto> obtenerImpuestoPorcentaje(double porcentaje) {
        return rep.findByPorcentaje(porcentaje, Constantes.estadoActivo);
    }
}
