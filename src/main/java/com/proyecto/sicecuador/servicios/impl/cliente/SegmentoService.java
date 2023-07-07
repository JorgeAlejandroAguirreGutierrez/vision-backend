package com.proyecto.vision.servicios.impl.cliente;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.cliente.Segmento;
import com.proyecto.vision.repositorios.cliente.ISegmentoRepository;
import com.proyecto.vision.servicios.interf.cliente.ISegmentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.Predicate;

@Service
public class SegmentoService implements ISegmentoService {
    @Autowired
    private ISegmentoRepository rep;

    @Override
    public void validar(Segmento segmento) {
        if(segmento.getDescripcion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.descripcion);
        if(segmento.getMargenGanancia() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.margenGanancia);
        if(segmento.getAbreviatura().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.abreviatura);
    }
    
    @Override
    public Segmento crear(Segmento segmento) {
        validar(segmento);
    	Optional<String>codigo=Util.generarCodigoPorEmpresa(Constantes.tabla_segmento, segmento.getEmpresa().getId());
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	segmento.setCodigo(codigo.get());
    	segmento.setEstado(Constantes.activo);
    	return rep.save(segmento);
    }

    @Override
    public Segmento actualizar(Segmento segmento) {
        validar(segmento);
        return rep.save(segmento);
    }

    @Override
    public Segmento activar(Segmento segmento) {
        validar(segmento);
        segmento.setEstado(Constantes.activo);
        return rep.save(segmento);
    }

    @Override
    public Segmento inactivar(Segmento segmento) {
        validar(segmento);
        segmento.setEstado(Constantes.inactivo);
        return rep.save(segmento);
    }

    @Override
    public Segmento obtener(long id) {
        Optional<Segmento> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.segmento);
    }

    @Override
    public List<Segmento> consultar() {
        return rep.consultar();
    }
    

    @Override
    public List<Segmento> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    @Override
    public List<Segmento> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public List<Segmento> consultarPorEmpresaYEstado(long empresaId, String estado){
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }

    @Override
    public Page<Segmento> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public List<Segmento> buscar(Segmento segmento) {
        return  rep.findAll((root, criteriaQuery, criteriaBuilder) -> {
		    List<Predicate> predicates = new ArrayList<>();
		    if (!segmento.getCodigo().equals(Constantes.vacio)) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("codigo"), "%"+segmento.getCodigo()+"%")));
		    }
		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		});
    }
}
