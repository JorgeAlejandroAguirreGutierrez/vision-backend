package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.cliente.Genero;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.repositorios.interf.cliente.IGeneroRepository;
import com.proyecto.sicecuador.repositorios.interf.configuracion.IParametroRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IGeneroService;
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
public class GeneroService implements IGeneroService {
    @Autowired
    private IGeneroRepository rep;
    
    @Override
    public Genero crear(Genero genero) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_genero);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	genero.setCodigo(codigo.get());
    	return rep.save(genero);
    }

    @Override
    public Genero actualizar(Genero genero) {
        return rep.save(genero);
    }

    @Override
    public Genero eliminar(Genero genero) {
        rep.deleteById(genero.getId());
        return genero;
    }

    @Override
    public Optional<Genero> obtener(Genero genero) {
        return rep.findById(genero.getId());
    }

    @Override
    public List<Genero> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<Genero> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public List<Genero> buscar(Genero genero) {
        return  rep.findAll(new Specification<Genero>() {
            @Override
            public Predicate toPredicate(Root<Genero> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!genero.getCodigo().equals(Constantes.vacio)) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("codigo"), "%"+genero.getCodigo()+"%")));
                }
                if (!genero.getDescripcion().equals(Constantes.vacio)) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("descripcion"), "%"+genero.getDescripcion()+"%")));
                }
                if (!genero.getAbreviatura().equals(Constantes.vacio)) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("abreviatura"), "%"+genero.getAbreviatura()+"%")));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }
    

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<Genero> generos=new ArrayList<>();
            List<List<String>>info= Util.leer_importar(archivo_temporal, 11);
            for (List<String> datos: info) {
                Genero genero = new Genero(datos);
                generos.add(genero);
            }
            if(generos.isEmpty()){
                return false;
            }
            rep.saveAll(generos);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
