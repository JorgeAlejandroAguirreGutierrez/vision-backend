package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.cliente.CategoriaCliente;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.repositorios.cliente.ICategoriaClienteRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.ICategoriaClienteService;
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
public class CategoriaClienteService implements ICategoriaClienteService {
	@Autowired
    private ICategoriaClienteRepository rep;
    
    @Override
    public CategoriaCliente crear(CategoriaCliente categoria_cliente) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_auxiliar);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	categoria_cliente.setCodigo(codigo.get());
    	return rep.save(categoria_cliente);
    }

    @Override
    public CategoriaCliente actualizar(CategoriaCliente categoria_cliente) {
        return rep.save(categoria_cliente);
    }

    @Override
    public CategoriaCliente eliminar(CategoriaCliente categoria_cliente) {
        rep.deleteById(categoria_cliente.getId());
        return categoria_cliente;
    }

    @Override
    public Optional<CategoriaCliente> obtener(CategoriaCliente categoria_cliente) {
        return rep.findById(categoria_cliente.getId());
    }

    @Override
    public List<CategoriaCliente> consultar() {
        return rep.findAll();
    }
    
    @Override
    public Page<CategoriaCliente> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public List<CategoriaCliente> buscar(CategoriaCliente categoria_cliente) {
        return  rep.findAll(new Specification<CategoriaCliente>() {
            @Override
            public Predicate toPredicate(Root<CategoriaCliente> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!categoria_cliente.getCodigo().equals(Constantes.vacio)) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("codigo"), "%"+categoria_cliente.getCodigo()+"%")));
                }
                if (!categoria_cliente.getDescripcion().equals(Constantes.vacio)) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("descripcion"), "%"+categoria_cliente.getDescripcion()+"%")));
                }
                if (!categoria_cliente.getAbreviatura().equals(Constantes.vacio)) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("abreviatura"), "%"+categoria_cliente.getAbreviatura()+"%")));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<CategoriaCliente> categorias_clientes=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal,1);
            for (List<String> datos: info) {
                CategoriaCliente categoria_cliente = new CategoriaCliente(datos);
                categorias_clientes.add(categoria_cliente);
            }
            if(categorias_clientes.isEmpty()){
                return false;
            }
            rep.saveAll(categorias_clientes);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
