package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.cliente.CategoriaCliente;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.cliente.EstadoCivil;
import com.proyecto.sicecuador.modelos.cliente.Financiamiento;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.repositorios.interf.cliente.IEstadoCivilRepository;
import com.proyecto.sicecuador.repositorios.interf.configuracion.IParametroRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IEstadoCivilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
@Service
public class EstadoCivilService implements IEstadoCivilService {
	
    @Autowired
    private IEstadoCivilRepository rep;
    
    @Override
    public EstadoCivil crear(EstadoCivil estado_civil) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_estado_civil);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	estado_civil.setCodigo(codigo.get());
    	return rep.save(estado_civil);
    }

    @Override
    public EstadoCivil actualizar(EstadoCivil estado_civil) {
        return rep.save(estado_civil);
    }

    @Override
    public EstadoCivil eliminar(EstadoCivil estado_civil) {
        rep.deleteById(estado_civil.getId());
        return estado_civil;
    }

    @Override
    public Optional<EstadoCivil> obtener(EstadoCivil estado_civil) {
        return rep.findById(estado_civil.getId());
    }

    @Override
    public List<EstadoCivil> consultar() {
        return rep.findAll();
    }
    
    @Override
    public Page<EstadoCivil> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
    
    @Override
    public List<EstadoCivil> buscar(EstadoCivil estado_civil) {
        return  rep.findAll(new Specification<EstadoCivil>() {
            @Override
            public Predicate toPredicate(Root<EstadoCivil> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!estado_civil.getCodigo().equals(Constantes.vacio)) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("codigo"), "%"+estado_civil.getCodigo()+"%")));
                }
                if (!estado_civil.getDescripcion().equals(Constantes.vacio)) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("descripcion"), "%"+estado_civil.getDescripcion()+"%")));
                }
                if (!estado_civil.getAbreviatura().equals(Constantes.vacio)) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("abreviatura"), "%"+estado_civil.getAbreviatura()+"%")));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<EstadoCivil> estados_civiles=new ArrayList<>();
            List<List<String>>info= Util.leer_importar(archivo_temporal,8);
            for (List<String> datos: info) {
                EstadoCivil estado_civil = new EstadoCivil(datos);
                estados_civiles.add(estado_civil);
            }
            if(estados_civiles.isEmpty()){
                return false;
            }
            rep.saveAll(estados_civiles);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
