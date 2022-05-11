package com.proyecto.sicecuador.servicios.impl.contabilidad;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.contabilidad.AfectacionContable;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadExistenteException;
import com.proyecto.sicecuador.repositorios.contabilidad.IAfectacionContableRepository;
import com.proyecto.sicecuador.servicios.interf.contabilidad.IAfectacionContableService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AfectacionContableService implements IAfectacionContableService {
    @Autowired
    private IAfectacionContableRepository rep;
    
    @Override
    public AfectacionContable crear(AfectacionContable afectacionContable) {
    	//proveedor.normalizar();
/*    	Optional<Proveedor> getProveedor=rep.obtenerPorRazonSocial(proveedor.getRazonSocial());
    	if(getProveedor.isPresent()) {
    		throw new ModeloExistenteException();
    	}*/
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_afectacion_contable);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	afectacionContable.setCodigo(codigo.get());
    	return rep.save(afectacionContable);
    }

    @Override
    public AfectacionContable actualizar(AfectacionContable afectacionContable) {
    	//proveedor.normalizar();
    	return rep.save(afectacionContable);
    }

    @Override
    public AfectacionContable eliminar(AfectacionContable afectacionContable) {
        rep.deleteById(afectacionContable.getId());
        return afectacionContable;
    }

    @Override
    public Optional<AfectacionContable> obtener(AfectacionContable afectacionContable) {
        return rep.findById(afectacionContable.getId());
    }

    @Override
    public List<AfectacionContable> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<AfectacionContable> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }


    @Override
    public List<AfectacionContable> buscar(AfectacionContable afectacionContable) {
        return  rep.findAll((root, criteriaQuery, criteriaBuilder) -> {
		    List<Predicate> predicates = new ArrayList<>();
		    if (!afectacionContable.getDescripcion().equals(Constantes.vacio)) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("descripcion"), "%"+afectacionContable.getDescripcion()+"%")));
		    }
		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		});
    }
    
    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<AfectacionContable> afectaciones_contables=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal,7);
            for (List<String> datos: info) {
                AfectacionContable afectacionContable = new AfectacionContable(datos);
                afectaciones_contables.add(afectacionContable);
            }
            if(afectaciones_contables.isEmpty()){
                return false;
            }
            rep.saveAll(afectaciones_contables);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
