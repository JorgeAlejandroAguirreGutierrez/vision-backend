package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.modelos.inventario.BodegaProducto;
import com.proyecto.sicecuador.modelos.inventario.Caracteristica;
import com.proyecto.sicecuador.modelos.inventario.Producto;
import com.proyecto.sicecuador.repositorios.interf.inventario.IBodegaProductoRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.IBodegaProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
@Service
public class BodegaProductoService implements IBodegaProductoService {
    @Autowired
    private IBodegaProductoRepository rep;
    @Override
    public BodegaProducto crear(BodegaProducto bodega_producto) {
        return rep.save(bodega_producto);
    }

    @Override
    public BodegaProducto actualizar(BodegaProducto bodega_producto) {
        return rep.save(bodega_producto);
    }

    @Override
    public BodegaProducto eliminar(BodegaProducto bodega_producto) {
        rep.deleteById(bodega_producto.getId());
        return bodega_producto;
    }

    @Override
    public Optional<BodegaProducto> obtener(BodegaProducto bodega_producto) {
        return rep.findById(bodega_producto.getId());
    }

    @Override
    public List<BodegaProducto> consultar() {
        return rep.findAll();
    }

    @Override
    public List<BodegaProducto> consultarProducto(BodegaProducto bodega_producto) {
        return  rep.findAll(new Specification<BodegaProducto>() {
            @Override
            public Predicate toPredicate(Root<BodegaProducto> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (bodega_producto.getProducto().getId()!=0) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("producto").get("id"), bodega_producto.getProducto().getId())));
                    predicates.add(criteriaBuilder.and(criteriaBuilder.isNull(root.join("caracteristicas").get("factura_detalle"))));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }
    @Override
    public Optional<BodegaProducto> obtenerProductoBodega(BodegaProducto bodega_producto) {
        return  rep.findOne(new Specification<BodegaProducto>() {
            @Override
            public Predicate toPredicate(Root<BodegaProducto> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (bodega_producto.getProducto().getId()!=0) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("producto").get("id"), bodega_producto.getProducto().getId())));
                }
                if (bodega_producto.getBodega().getId()!=0) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("bodega").get("id"), bodega_producto.getBodega().getId())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }
}
