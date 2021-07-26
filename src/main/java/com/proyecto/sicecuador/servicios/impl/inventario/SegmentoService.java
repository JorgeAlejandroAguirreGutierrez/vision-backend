package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.inventario.Proveedor;
import com.proyecto.sicecuador.modelos.inventario.Segmento;
import com.proyecto.sicecuador.repositorios.inventario.ISegmentoRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.ISegmentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.Predicate;

@Service
public class SegmentoService implements ISegmentoService {
    @Autowired
    private ISegmentoRepository rep;
    
    @Override
    public Segmento crear(Segmento segmento) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_segmento);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	segmento.setCodigo(codigo.get());
    	return rep.save(segmento);
    }

    @Override
    public Segmento actualizar(Segmento segmento) {
        return rep.save(segmento);
    }

    @Override
    public Segmento eliminar(Segmento segmento) {
        rep.deleteById(segmento.getId());
        return segmento;
    }

    @Override
    public Optional<Segmento> obtener(Segmento segmento) {
        return rep.findById(segmento.getId());
    }

    @Override
    public List<Segmento> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<Segmento> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public List<Segmento> buscar(Segmento segmento) {
        return  rep.findAll((root, criteriaQuery, criteriaBuilder) -> {
		    List<Predicate> predicates = new ArrayList<>();
		    if (!segmento.getCodigo().equals(Constantes.vacio)) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("codigo"), "%"+segmento.getCodigo()+"%")));
		    }
		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		});
    }
    
    
    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<Segmento> segmentos=new ArrayList<>();
            List<List<String>>info= Util.leer_importar(archivo_temporal,8);
            for (List<String> datos: info) {
                Segmento segmento = new Segmento(datos);
                segmentos.add(segmento);
            }
            if(segmentos.isEmpty()){
                return false;
            }
            rep.saveAll(segmentos);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
