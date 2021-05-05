package com.proyecto.sicecuador.servicios.impl.configuracion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.repositorios.interf.configuracion.IUbicacionRepository;
import com.proyecto.sicecuador.servicios.interf.configuracion.IUbicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class UbicacionService implements IUbicacionService {
    @Autowired
    private IUbicacionRepository rep;
    
    @Override
    public Ubicacion crear(Ubicacion ubicacion) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_ubicacion);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	ubicacion.setCodigo(codigo.get());
    	return rep.save(ubicacion);
    }

    @Override
    public Ubicacion actualizar(Ubicacion ubicacion) {
        return rep.save(ubicacion);
    }

    @Override
    public Ubicacion eliminar(Ubicacion ubicacion) {
        rep.deleteById(ubicacion.getId());
        return ubicacion;
    }

    @Override
    public Optional<Ubicacion> obtener(Ubicacion ubicacion) {
        return rep.findById(ubicacion.getId());
    }

    @Override
    public List<Ubicacion> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<Ubicacion> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<Ubicacion> ubicaciones=new ArrayList<>();
            List<List<String>>info= Util.leer_importar(archivo_temporal, 3);
            for (List<String> datos: info) {
                Ubicacion ubicacion = new Ubicacion(datos);
                ubicaciones.add(ubicacion);
            }
            if(ubicaciones.isEmpty()){
                return false;
            }
            rep.saveAll(ubicaciones);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<Ubicacion> consultarProvincias() {
        List<String> provincias=rep.findProvincias();
        List<Ubicacion> ubicaciones=new ArrayList<>();
        for (String provincia: provincias) {
            Ubicacion ubicacion=new Ubicacion();
            ubicacion.setProvincia(provincia);
            ubicaciones.add(ubicacion);
        }
        return ubicaciones;
    }

    @Override
    public List<Ubicacion> consultarCantones(Ubicacion ubicacion) {
        List<String> cantones=rep.findCantones(ubicacion.getProvincia());
        List<Ubicacion> ubicaciones=new ArrayList<>();
        for (String canton: cantones) {
            Ubicacion _ubicacion=new Ubicacion();
            _ubicacion.setCanton(canton);
            ubicaciones.add(_ubicacion);
        }
        return ubicaciones;
    }

    @Override
    public List<Ubicacion> consultarParroquias(Ubicacion ubicacion) {
        return rep.findParroquias(ubicacion.getCanton());
    }
    @Override
    public Optional<Ubicacion> obtenerUbicacionID(Ubicacion ubicacion) {
        return rep.findByProvinciaAndCantonAndParroquia(ubicacion.getProvincia(),
                ubicacion.getCanton(), ubicacion.getParroquia());
    }

    @Override
    public List<Ubicacion> buscar(Ubicacion ubicacion) {
        return  rep.findAll(new Specification<Ubicacion>() {
            @Override
            public Predicate toPredicate(Root<Ubicacion> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (ubicacion.getCodigoNorma()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("codigoNorma"), "%"+ubicacion.getCodigoNorma()+"%")));
                }
                if (ubicacion.getProvincia()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("provincia"), "%"+ubicacion.getProvincia()+"%")));
                }
                if (ubicacion.getCanton()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("canton"), "%"+ubicacion.getCanton()+"%")));
                }
                if (ubicacion.getParroquia()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("parroquia"), "%"+ubicacion.getParroquia()+"%")));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }
}
