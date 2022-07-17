package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.cliente.CalificacionCliente;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.repositorios.cliente.ICalificacionClienteRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.ICalificacionClienteService;
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
public class CalificacionClienteService implements ICalificacionClienteService {
	@Autowired
    private ICalificacionClienteRepository rep;
    
    @Override
    public CalificacionCliente crear(CalificacionCliente calificacion_cliente) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_calificacion_cliente);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	calificacion_cliente.setCodigo(codigo.get());
    	return rep.save(calificacion_cliente);
    }

    @Override
    public CalificacionCliente actualizar(CalificacionCliente calificacion_cliente) {
        return rep.save(calificacion_cliente);
    }

    @Override
    public CalificacionCliente eliminar(CalificacionCliente calificacion_cliente) {
        rep.deleteById(calificacion_cliente.getId());
        return calificacion_cliente;
    }
    
    @Override
    public Optional<CalificacionCliente> eliminarPersonalizado(long id) {
        if(id!=0) {
        	Optional<CalificacionCliente> calificacionCliente=rep.findById(id);
        	if(calificacionCliente.isPresent()) {
        		calificacionCliente.get().setEstado(Constantes.inactivo);
            	return Optional.of(rep.save(calificacionCliente.get()));
        	}
        }
        throw new EntidadNoExistenteException(Constantes.calificacion_cliente);
    }

    @Override
    public Optional<CalificacionCliente> obtener(CalificacionCliente calificacion_cliente) {
        return rep.findById(calificacion_cliente.getId());
    }

    @Override
    public List<CalificacionCliente> consultar() {
        return rep.findAll();
    }
    
    @Override
    public Page<CalificacionCliente> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public List<CalificacionCliente> buscar(CalificacionCliente calificacion_cliente) {
        return  rep.findAll(new Specification<CalificacionCliente>() {
            @Override
            public Predicate toPredicate(Root<CalificacionCliente> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!calificacion_cliente.getCodigo().equals(Constantes.vacio)) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("codigo"), "%"+calificacion_cliente.getCodigo()+"%")));
                }
                if (!calificacion_cliente.getDescripcion().equals(Constantes.vacio)) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("descripcion"), "%"+calificacion_cliente.getDescripcion()+"%")));
                }
                if (!calificacion_cliente.getAbreviatura().equals(Constantes.vacio)) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("abreviatura"), "%"+calificacion_cliente.getAbreviatura()+"%")));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<CalificacionCliente> calificaciones_clientes=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal,1);
            for (List<String> datos: info) {
                CalificacionCliente calificacion_cliente = new CalificacionCliente(datos);
                calificaciones_clientes.add(calificacion_cliente);
            }
            if(calificaciones_clientes.isEmpty()){
                return false;
            }
            rep.saveAll(calificaciones_clientes);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
