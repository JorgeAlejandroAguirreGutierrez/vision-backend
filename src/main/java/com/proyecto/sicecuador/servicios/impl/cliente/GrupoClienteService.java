package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.cliente.GrupoCliente;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.repositorios.cliente.IGrupoClienteRepository;
import com.proyecto.sicecuador.repositorios.configuracion.IParametroRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IGrupoClienteService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class GrupoClienteService implements IGrupoClienteService {
    @Autowired
    private IGrupoClienteRepository rep;
    
    @Override
    public GrupoCliente crear(GrupoCliente grupo_cliente) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_grupo_cliente);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	grupo_cliente.setCodigo(codigo.get());
    	return rep.save(grupo_cliente);
    }

    @Override
    public GrupoCliente actualizar(GrupoCliente grupo_cliente) {
        return rep.save(grupo_cliente);
    }

    @Override
    public GrupoCliente eliminar(GrupoCliente grupo_cliente) {
        rep.deleteById(grupo_cliente.getId());
        return grupo_cliente;
    }

    @Override
    public Optional<GrupoCliente> obtener(GrupoCliente grupo_cliente) {
        return rep.findById(grupo_cliente.getId());
    }

    @Override
    public List<GrupoCliente> consultar() {
        return rep.findAll();
    }

    @Override
    public List<GrupoCliente> buscar(GrupoCliente grupo_cliente) {
        return  rep.findAll(new Specification<GrupoCliente>() {
            @Override
            public Predicate toPredicate(Root<GrupoCliente> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!grupo_cliente.getCodigo().equals(Constantes.vacio)) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("codigo"), "%"+grupo_cliente.getCodigo()+"%")));
                }
                if (!grupo_cliente.getDescripcion().equals(Constantes.vacio)) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("descripcion"), "%"+grupo_cliente.getDescripcion()+"%")));
                }
                if (!grupo_cliente.getAbreviatura().equals(Constantes.vacio)) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("abreviatura"), "%"+grupo_cliente.getAbreviatura()+"%")));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<GrupoCliente> grupos_clientes=new ArrayList<>();
            List<List<String>>info= Util.leer_importar(archivo_temporal, 12);
            for (List<String> datos: info) {
                GrupoCliente grupo_cliente = new GrupoCliente(datos);
                grupos_clientes.add(grupo_cliente);
            }
            if(grupos_clientes.isEmpty()){
                return false;
            }
            rep.saveAll(grupos_clientes);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
