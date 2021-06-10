package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.modelos.cliente.Auxiliar;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.configuracion.Parametro;
import com.proyecto.sicecuador.repositorios.cliente.IAuxiliarRepository;
import com.proyecto.sicecuador.repositorios.configuracion.IParametroRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IAuxiliarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class AuxiliarService implements IAuxiliarService {
    @Autowired
    private IAuxiliarRepository rep;
    
    @Override
    public Auxiliar crear(Auxiliar auxiliar) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_auxiliar);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	auxiliar.setCodigo(codigo.get());
    	return rep.save(auxiliar);
    }

    @Override
    public Auxiliar actualizar(Auxiliar auxiliar) {
        return rep.save(auxiliar);
    }

    @Override
    public Auxiliar eliminar(Auxiliar auxiliar) {
        rep.deleteById(auxiliar.getId());
        return auxiliar;
    }

    @Override
    public Optional<Auxiliar> obtener(Auxiliar auxiliar) {
        return rep.findById(auxiliar.getId());
    }

    @Override
    public List<Auxiliar> consultar() {
        return rep.findAll();
    }
    
    @Override
    public Page<Auxiliar> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }


    @Override
    public List<Auxiliar> consultarRazonSocial(Auxiliar auxiliar) {
        return  rep.findAll(new Specification<Auxiliar>() {
            @Override
            public Predicate toPredicate(Root<Auxiliar> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (auxiliar.getRazonSocial()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("razonSocial"), "%"+auxiliar.getRazonSocial()+"%")));
                }
                if (auxiliar.getCliente().getId()!=0) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("cliente").get("id"), auxiliar.getCliente().getId())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });

    }
    @Override
    public List<Auxiliar> consultarClienteID(Auxiliar auxiliar) {
        return  rep.findAll(new Specification<Auxiliar>() {
            @Override
            public Predicate toPredicate(Root<Auxiliar> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (auxiliar.getCliente().getId()!=0) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("cliente").get("id"), auxiliar.getCliente().getId())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<Auxiliar> auxiliares=new ArrayList<>();
            List<List<String>>info=Util.leer_importar(archivo_temporal,0);
            for (List<String> datos: info){
                Auxiliar auxiliar = new Auxiliar(datos);
                auxiliares.add(auxiliar);
            }
            if(auxiliares.isEmpty()){
                return false;
            }
            rep.saveAll(auxiliares);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
