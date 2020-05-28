package com.proyecto.sicecuador.servicios.impl.comprobante;

import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.repositorios.interf.comprobante.IFacturaRepository;
import com.proyecto.sicecuador.servicios.interf.comprobante.IFacturaService;
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
public class FacturaService implements IFacturaService {
    @Autowired
    private IFacturaRepository rep;
    @Override
    public Factura crear(Factura factura) {
        return rep.save(factura);
    }

    @Override
    public Factura actualizar(Factura factura) {
        return rep.save(factura);
    }

    @Override
    public Factura eliminar(Factura factura) {
        rep.deleteById(factura.getId());
        return factura;
    }

    @Override
    public Optional<Factura> obtener(Factura factura) {
        return rep.findById(factura.getId());
    }

    @Override
    public List<Factura> consultar() {
        return rep.findAll();
    }

    @Override
    public List<Factura> consultarClienteRazonSocial(Factura factura) {
        return  rep.findAll(new Specification<Factura>() {
            @Override
            public Predicate toPredicate(Root<Factura> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (factura.getCliente().getRazon_social()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("cliente").get("razon_social"), "%"+factura.getCliente().getRazon_social()+"%")));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }

    @Override
    public List<Factura> consultarNumero(Factura factura) {
        return  rep.findAll(new Specification<Factura>() {
            @Override
            public Predicate toPredicate(Root<Factura> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (factura.getNumero()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("numero"), "%"+factura.getNumero()+"%")));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }
}
