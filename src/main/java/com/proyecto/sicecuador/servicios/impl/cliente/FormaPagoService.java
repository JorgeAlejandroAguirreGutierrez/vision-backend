package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.cliente.FormaPago;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.repositorios.cliente.IFormaPagoRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IFormaPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.Predicate;
@Service
public class FormaPagoService implements IFormaPagoService {
    @Autowired
    private IFormaPagoRepository rep;
    
    @Override
    public FormaPago crear(FormaPago forma_pago) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_forma_pago);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	forma_pago.setCodigo(codigo.get());
    	return rep.save(forma_pago);
    }

    @Override
    public FormaPago actualizar(FormaPago forma_pago) {
        return rep.save(forma_pago);
    }

    @Override
    public FormaPago eliminar(FormaPago forma_pago) {
        rep.deleteById(forma_pago.getId());
        return forma_pago;
    }

    @Override
    public Optional<FormaPago> obtener(FormaPago forma_pago) {
        return rep.findById(forma_pago.getId());
    }

    @Override
    public List<FormaPago> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<FormaPago> buscar(FormaPago forma_pago) {
        return  rep.findAll((root, criteriaQuery, criteriaBuilder) -> {
		    List<Predicate> predicates = new ArrayList<>();
		    if (!forma_pago.getCodigo().equals(Constantes.vacio)) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("codigo"), "%"+forma_pago.getCodigo()+"%")));
		    }
		    if (!forma_pago.getDescripcion().equals(Constantes.vacio)) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("descripcion"), "%"+forma_pago.getDescripcion()+"%")));
		    }
		    if (!forma_pago.getAbreviatura().equals(Constantes.vacio)) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("abreviatura"), "%"+forma_pago.getAbreviatura()+"%")));
		    }
		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		});
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<FormaPago> formas_pagos=new ArrayList<>();
            List<List<String>>info= Util.leer_importar(archivo_temporal, 10);
            for (List<String> datos: info) {
                FormaPago forma_pago = new FormaPago(datos);
                formas_pagos.add(forma_pago);
            }
            if(formas_pagos.isEmpty()){
                return false;
            }
            rep.saveAll(formas_pagos);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
