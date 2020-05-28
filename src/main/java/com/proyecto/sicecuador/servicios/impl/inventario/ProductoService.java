package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.comprobante.FacturaCaracteristica;
import com.proyecto.sicecuador.modelos.inventario.Bodega;
import com.proyecto.sicecuador.modelos.inventario.BodegaProducto;
import com.proyecto.sicecuador.modelos.inventario.Caracteristica;
import com.proyecto.sicecuador.modelos.inventario.Producto;
import com.proyecto.sicecuador.repositorios.interf.inventario.IProductoRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class ProductoService implements IProductoService {
    @Autowired
    private IProductoRepository rep;
    @Override
    public Producto crear(Producto producto) {
        return rep.save(producto);
    }

    @Override
    public Producto actualizar(Producto producto) {
        return rep.save(producto);
    }

    @Override
    public Producto eliminar(Producto producto) {
        rep.deleteById(producto.getId());
        return producto;
    }

    @Override
    public Optional<Producto> obtener(Producto producto) {
        return rep.findById(producto.getId());
    }

    @Override
    public List<Producto> consultar() {
        return rep.findAll();
    }

    @Override
    public List<Producto> consultarBien() {
        List<Producto> productos=  rep.findAll(new Specification<Producto>() {
            @Override
            public Predicate toPredicate(Root<Producto> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("tipo_producto").get("tipo"), "BIEN")));
                //predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.selectCase().when(criteriaBuilder.equal(root.get("habilita_caracteristicas"), true),criteriaBuilder.count(criteriaBuilder.isNull(root.join("bodegas_productos").join("caracteristicas").get("factura_detalle")))).otherwise(1).as(Integer.class), 1)));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
        for (Producto producto: productos){
            for (BodegaProducto bodega_producto: producto.getBodegas_productos()){
                for (Caracteristica caracteristica: bodega_producto.getCaracteristicas()){
                    long cantidad_inexistente=0;
                    for(FacturaCaracteristica factura_caracteristica: caracteristica.getFactura_caracteristicas()){
                        cantidad_inexistente=cantidad_inexistente+factura_caracteristica.getCantidad();
                    }
                    if (caracteristica.getCantidad()-cantidad_inexistente<=0){
                        bodega_producto.getCaracteristicas().remove(caracteristica);
                        if (bodega_producto.getCaracteristicas().isEmpty()){
                            break;
                        }
                    } else {
                        caracteristica.setCantidad(caracteristica.getCantidad()-cantidad_inexistente);
                        producto.setStock_total(producto.getStock_total()+caracteristica.getCantidad());
                    }
                }
            }
        }
        return productos;
    }

    @Override
    public List<Producto> consultarServicio() {
        return  rep.findAll(new Specification<Producto>() {
            @Override
            public Predicate toPredicate(Root<Producto> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("tipo_producto").get("tipo"), "SERVICIO")));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });

    }

    @Override
    public List<Producto> consultarActivoFijo() {
        return  rep.findAll(new Specification<Producto>() {
            @Override
            public Predicate toPredicate(Root<Producto> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("tipo_producto").get("tipo"), "ACTIVOFIJO")));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }
    @Override
    public List<Producto> consultarBodega() {
        return  rep.findAll(new Specification<Producto>() {
            @Override
            public Predicate toPredicate(Root<Producto> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("tipo_producto").get("tipo"), "ACTIVOFIJO")));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }
}
