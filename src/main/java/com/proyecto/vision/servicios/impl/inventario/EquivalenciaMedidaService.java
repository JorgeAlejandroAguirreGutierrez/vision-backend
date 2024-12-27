package com.proyecto.vision.servicios.impl.inventario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.exception.EntidadExistenteException;
import com.proyecto.vision.exception.EntidadNoExistenteException;

import com.proyecto.vision.modelos.inventario.EquivalenciaMedida;
import com.proyecto.vision.modelos.inventario.Kardex;
import com.proyecto.vision.repositorios.inventario.IEquivalenciaMedidaRepository;
import com.proyecto.vision.servicios.interf.inventario.IEquivalenciaMedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

@Service
public class EquivalenciaMedidaService implements IEquivalenciaMedidaService {
    @Autowired
    private IEquivalenciaMedidaRepository rep;

    @Override
    public void validar(EquivalenciaMedida equivalenciaMedida) {
        if(equivalenciaMedida.getEquivalencia() == Constantes.cero) throw new DatoInvalidoException(Constantes.nombre);
    }
    
    @Override
    public EquivalenciaMedida crear(EquivalenciaMedida equivalenciaMedida) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_equivalencia_medida);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
		equivalenciaMedida.setCodigo(codigo.get());
    	EquivalenciaMedida existe= this.obtenerPorMedidaIniYMedidaFin(equivalenciaMedida.getMedidaIni().getId(), equivalenciaMedida.getMedidaFin().getId());
    	/*if(existe.isEmpty()) {
    		throw new EntidadExistenteException(Constantes.equivalencia_medida);
    	}*/
		equivalenciaMedida.setEstado(Constantes.estadoActivo);
        return rep.save(equivalenciaMedida);
    }

    @Override
    public EquivalenciaMedida actualizar(EquivalenciaMedida equivalenciaMedida) {
		validar(equivalenciaMedida);
		return rep.save(equivalenciaMedida);
    }

	@Override
	public EquivalenciaMedida activar(EquivalenciaMedida equivalenciaMedida) {
		validar(equivalenciaMedida);
		equivalenciaMedida.setEstado(Constantes.estadoActivo);
		return rep.save(equivalenciaMedida);
	}

	@Override
	public EquivalenciaMedida inactivar(EquivalenciaMedida equivalenciaMedida) {
		validar(equivalenciaMedida);
		equivalenciaMedida.setEstado(Constantes.estadoInactivo);
		return rep.save(equivalenciaMedida);
	}

	@Override
	public EquivalenciaMedida obtener(long id) {
		Optional<EquivalenciaMedida> res= rep.findById(id);
		if(res.isPresent()) {
			return res.get();
		}
		throw new EntidadNoExistenteException(Constantes.equivalencia_medida);
	}

    @Override
    public List<EquivalenciaMedida> consultar() {
        return rep.findAll();
    }

	@Override
	public List<EquivalenciaMedida> consultarPorEstado(String estado){
		return rep.consultarPorEstado(estado);
	}

	@Override
	public EquivalenciaMedida obtenerPorMedidaIniYMedidaFin(long medidaIni_id, long medidaFin_id){
		Optional<EquivalenciaMedida> res = rep.obtenerPorMedidaIniYMedidaFin(medidaIni_id, medidaFin_id);
		if(res.isEmpty()){
			return null;
		}
		return res.get();
	}

    @Override
    public List<EquivalenciaMedida> buscarMedidasEquivalentes(EquivalenciaMedida _equivalenciaMedida){
        	List<EquivalenciaMedida> equivalencias =  rep.findAll((root, criteriaQuery, criteriaBuilder) -> {
		    List<Predicate> predicates = new ArrayList<>();
		    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("medidaIni").get("id"), _equivalenciaMedida.getMedidaIni().getId())));
		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));                
		});
        return equivalencias;
    }

    
    @Override
    public List<EquivalenciaMedida> buscar(EquivalenciaMedida equivalenciaMedida) {
        return  rep.findAll((root, criteriaQuery, criteriaBuilder) -> {
		    List<Predicate> predicates = new ArrayList<>();
		    if (!equivalenciaMedida.getCodigo().equals(Constantes.vacio)) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("codigo").get("codigo"), "%"+equivalenciaMedida.getCodigo()+"%")));
		    }
		    if (!equivalenciaMedida.getMedidaIni().getDescripcion().equals(Constantes.vacio)) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("medidaIni").get("descripcion"), "%"+equivalenciaMedida.getMedidaIni().getDescripcion()+"%")));
		    }
		    if (!equivalenciaMedida.getMedidaFin().getDescripcion().equals(Constantes.vacio)) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("medidaFin").get("descripcion"), "%"+equivalenciaMedida.getMedidaFin().getDescripcion()+"%")));
		    }
		    if (equivalenciaMedida.getEquivalencia()!=0) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("equivalencia"), "%"+equivalenciaMedida.getEquivalencia()+"%")));
		    }
		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		});
    }

}
