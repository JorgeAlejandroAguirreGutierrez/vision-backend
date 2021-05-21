package com.proyecto.sicecuador.servicios.impl.recaudacion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.modelos.recaudacion.OperadorTarjeta;
import com.proyecto.sicecuador.repositorios.recaudacion.IOperadorTarjetaRepository;
import com.proyecto.sicecuador.servicios.interf.recaudacion.IOperadorTarjetaService;
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
public class OperadorTarjetaService implements IOperadorTarjetaService {
    @Autowired
    private IOperadorTarjetaRepository rep;
    
    @Override
    public OperadorTarjeta crear(OperadorTarjeta operador_tarjeta) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_operador_tarjeta);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	operador_tarjeta.setCodigo(codigo.get());
    	return rep.save(operador_tarjeta);
    }

    @Override
    public OperadorTarjeta actualizar(OperadorTarjeta operador_tarjeta) {
        return rep.save(operador_tarjeta);
    }

    @Override
    public OperadorTarjeta eliminar(OperadorTarjeta operador_tarjeta) {
        rep.deleteById(operador_tarjeta.getId());
        return operador_tarjeta;
    }

    @Override
    public Optional<OperadorTarjeta> obtener(OperadorTarjeta operador_tarjeta) {
        return rep.findById(operador_tarjeta.getId());
    }

    @Override
    public List<OperadorTarjeta> consultar() {
        return rep.findAll();
    }

    @Override
    public List<OperadorTarjeta> consultarTipo(OperadorTarjeta operador_tarjeta) {
        return  rep.findAll(new Specification<OperadorTarjeta>() {
            @Override
            public Predicate toPredicate(Root<OperadorTarjeta> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (operador_tarjeta.getTipo()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("tipo"), operador_tarjeta.getTipo())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }
    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<OperadorTarjeta> operadores_tarjetas=new ArrayList<>();
            List<List<String>>info= Util.leer_importar(archivo_temporal,5);
            for (List<String> datos: info) {
                OperadorTarjeta operador_tarjeta = new OperadorTarjeta(datos);
                operadores_tarjetas.add(operador_tarjeta);
            }
            if(operadores_tarjetas.isEmpty()){
                return false;
            }
            rep.saveAll(operadores_tarjetas);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
