package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.inventario.EquivalenciaMedida;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.repositorios.inventario.ITablaEquivalenciaMedidaRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.ITablaEquivalenciaMedidaService;
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
public class TablaEquivalenciaMedidaService implements ITablaEquivalenciaMedidaService {
    @Autowired
    private ITablaEquivalenciaMedidaRepository rep;
    
    @Override
    public EquivalenciaMedida crear(EquivalenciaMedida tabla) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_equivalencia_medida);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	tabla.setCodigo(codigo.get());
    	Optional<EquivalenciaMedida> tem= this.obtenerMedida1Medida2(tabla);
    	if(tem.isPresent()) {
    		throw new EntidadExistenteException(Constantes.equivalencia_medida);
    	}
        return rep.save(tabla);
    }

    @Override
    public EquivalenciaMedida actualizar(EquivalenciaMedida tabla) {
    	Optional<EquivalenciaMedida> optionalTem= this.obtenerMedida1Medida2(tabla);
    	if(optionalTem.isPresent()) {
    		EquivalenciaMedida tem=optionalTem.get();
    		tem.setEquivalencia(tabla.getEquivalencia());
    		return rep.save(tem);
    	}
    	throw new EntidadNoExistenteException(Constantes.equivalencia_medida);
    }

    @Override
    public EquivalenciaMedida eliminar(EquivalenciaMedida tabla) {
        rep.deleteById(tabla.getId());
        return tabla;
    }

    @Override
    public Optional<EquivalenciaMedida> obtener(EquivalenciaMedida tabla) {
        return rep.findById(tabla.getId());
    }

    @Override
    public List<EquivalenciaMedida> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<EquivalenciaMedida> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public Optional<EquivalenciaMedida> obtenerMedida1Medida2(EquivalenciaMedida _tabla){
        Optional<EquivalenciaMedida> tabla =  rep.findOne((root, criteriaQuery, criteriaBuilder) -> {
		    List<Predicate> predicates = new ArrayList<>();
		    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("medida1").get("descripcion"), _tabla.getMedida1().getDescripcion())));
		    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("medida2").get("descripcion"), _tabla.getMedida2().getDescripcion())));
		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));                
		});
        return tabla;
    }

    @Override
    public List<EquivalenciaMedida> buscarMedidasEquivalentes(EquivalenciaMedida _tabla){
        	List<EquivalenciaMedida> equivalencias =  rep.findAll((root, criteriaQuery, criteriaBuilder) -> {
		    List<Predicate> predicates = new ArrayList<>();
		    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("medida1").get("id"), _tabla.getMedida1().getId())));
		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));                
		});
        return equivalencias;
    }

    
    @Override
    public List<EquivalenciaMedida> buscar(EquivalenciaMedida tem) {
        return  rep.findAll((root, criteriaQuery, criteriaBuilder) -> {
		    List<Predicate> predicates = new ArrayList<>();
		    if (!tem.getCodigo().equals(Constantes.vacio)) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("codigo").get("codigo"), "%"+tem.getCodigo()+"%")));
		    }
		    if (!tem.getMedida1().getDescripcion().equals(Constantes.vacio)) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("medida1").get("descripcion"), "%"+tem.getMedida1().getDescripcion()+"%")));
		    }
		    if (!tem.getMedida2().getDescripcion().equals(Constantes.vacio)) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("medida2").get("descripcion"), "%"+tem.getMedida2().getDescripcion()+"%")));
		    }
		    if (tem.getEquivalencia()!=0) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("equivalencia"), "%"+tem.getEquivalencia()+"%")));
		    }
		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		});
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<EquivalenciaMedida> tablas=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal,0);
            for (List<String> datos: info) {
                EquivalenciaMedida tabla = new EquivalenciaMedida(datos);
                tablas.add(tabla);
            }
            if(tablas.isEmpty()){
                return false;
            }
            rep.saveAll(tablas);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
