package com.proyecto.sicecuador.servicios.impl.usuario;

import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.modelos.usuario.Establecimiento;
import com.proyecto.sicecuador.modelos.usuario.PuntoVenta;
import com.proyecto.sicecuador.repositorios.interf.configuracion.IParametroRepository;
import com.proyecto.sicecuador.repositorios.interf.usuario.IPuntoVentaRepository;
import com.proyecto.sicecuador.servicios.interf.usuario.IPuntoVentaService;
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
public class PuntoVentaService implements IPuntoVentaService {
    @Autowired
    private IPuntoVentaRepository rep;
    @Autowired
    private static IParametroRepository parametroRep;
    
    @Override
    public PuntoVenta crear(PuntoVenta punto_venta) {
        return rep.save(punto_venta);
    }

    @Override
    public PuntoVenta actualizar(PuntoVenta punto_venta) {
        return rep.save(punto_venta);
    }

    @Override
    public PuntoVenta eliminar(PuntoVenta punto_venta) {
        rep.deleteById(punto_venta.getId());
        return punto_venta;
    }

    @Override
    public Optional<PuntoVenta> obtener(PuntoVenta punto_venta) {
        return rep.findById(punto_venta.getId());
    }

    @Override
    public List<PuntoVenta> consultar() {
        return rep.findAll();
    }

    @Override
    public List<PuntoVenta> consultarEstablecimiento(Establecimiento establecimiento) {
        return  rep.findAll(new Specification<PuntoVenta>() {
            @Override
            public Predicate toPredicate(Root<PuntoVenta> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (establecimiento.getId()!=0) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("establecimiento").get("id"), establecimiento.getId())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<PuntoVenta> puntos_ventas=new ArrayList<>();
            List<List<String>>info= Util.leer_importar(archivo_temporal,3);
            for (List<String> datos: info) {
                PuntoVenta punto_venta = new PuntoVenta(datos);
                puntos_ventas.add(punto_venta);
            }
            if(puntos_ventas.isEmpty()){
                return false;
            }
            rep.saveAll(puntos_ventas);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
