package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.inventario.EquivalenciaMedida;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.repositorios.inventario.IEquivalenciaMedidaRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.IEquivalenciaMedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EquivalenciaMedidaService implements IEquivalenciaMedidaService {
    @Autowired
    private IEquivalenciaMedidaRepository rep;
    
    @Override
    public EquivalenciaMedida crear(EquivalenciaMedida _equivalenciaMedida) {
    	EquivalenciaMedida equivalenciaMedida= this.obtenerMedida1Medida2(_equivalenciaMedida);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_equivalencia_medida);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	equivalenciaMedida.setCodigo(codigo.get());
    	equivalenciaMedida.setEstado(Constantes.activo);
        return rep.save(equivalenciaMedida);
    }

    @Override
    public EquivalenciaMedida actualizar(EquivalenciaMedida tabla) {
    	EquivalenciaMedida tem= this.obtenerMedida1Medida2(tabla);
		tem.setEquivalencia(tabla.getEquivalencia());
		return rep.save(tem);
    }

    @Override
    public EquivalenciaMedida activar(EquivalenciaMedida equivalenciaMedida) {
        equivalenciaMedida.setEstado(Constantes.activo);
        return rep.save(equivalenciaMedida);
    }

    @Override
    public EquivalenciaMedida inactivar(EquivalenciaMedida equivalenciaMedida) {
        equivalenciaMedida.setEstado(Constantes.inactivo);
        return rep.save(equivalenciaMedida);
    }

    @Override
    public EquivalenciaMedida obtener(long id) {
        Optional<EquivalenciaMedida> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.equivalencia_medida);
    }

    @Override
    public List<EquivalenciaMedida> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<EquivalenciaMedida> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<EquivalenciaMedida> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public EquivalenciaMedida obtenerMedida1Medida2(EquivalenciaMedida _equivalenciaMedida){
        Optional<EquivalenciaMedida> equivalenciaMedida =  rep.findOne((root, criteriaQuery, criteriaBuilder) -> {
		    List<Predicate> predicates = new ArrayList<>();
		    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("medidaIni").get("descripcion"), _equivalenciaMedida.getMedidaIni().getDescripcion())));
		    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("medidaEqui").get("descripcion"), _equivalenciaMedida.getMedidaEqui().getDescripcion())));
		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));                
		});
        if(equivalenciaMedida.isEmpty()) {
        	return equivalenciaMedida.get();
        }
        throw new EntidadNoExistenteException(Constantes.equivalencia_medida);
        
    }

    @Override
    public List<EquivalenciaMedida> buscarMedidasEquivalentes(EquivalenciaMedida _tabla){
        	List<EquivalenciaMedida> equivalencias =  rep.findAll((root, criteriaQuery, criteriaBuilder) -> {
		    List<Predicate> predicates = new ArrayList<>();
		    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("medidaIni").get("id"), _tabla.getMedidaIni().getId())));
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
		    if (!tem.getMedidaIni().getDescripcion().equals(Constantes.vacio)) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("medidaIni").get("descripcion"), "%"+tem.getMedidaIni().getDescripcion()+"%")));
		    }
		    if (!tem.getMedidaEqui().getDescripcion().equals(Constantes.vacio)) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("medidaEqui").get("descripcion"), "%"+tem.getMedidaEqui().getDescripcion()+"%")));
		    }
		    if (tem.getEquivalencia()!=0) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("equivalencia"), "%"+tem.getEquivalencia()+"%")));
		    }
		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		});
    }

    @Override
    public void importar(MultipartFile archivoTemporal) {
        try {
            List<EquivalenciaMedida> tablas=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivoTemporal,0);
            for (List<String> datos: info) {
                EquivalenciaMedida tabla = new EquivalenciaMedida(datos);
                tablas.add(tabla);
            }
            rep.saveAll(tablas);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
