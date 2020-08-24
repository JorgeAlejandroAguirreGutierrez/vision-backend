package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.modelos.cliente.Auxiliar;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.cliente.Direccion;
import com.proyecto.sicecuador.otros.Util;
import com.proyecto.sicecuador.repositorios.interf.cliente.IAuxiliarRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IAuxiliarService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.PostPersist;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class AuxiliarService implements IAuxiliarService {
    @Autowired
    private IAuxiliarRepository rep;
    @Override
    public Auxiliar crear(Auxiliar auxiliar) {
        return rep.save(auxiliar);
    }

    @Override
    public Auxiliar actualizar(Auxiliar auxiliar) {
        return rep.save(auxiliar);
    }

    @Override
    public Auxiliar eliminar(Auxiliar auxiliar) {
        rep.deleteById(auxiliar.getId());
        return auxiliar;
    }

    @Override
    public Optional<Auxiliar> obtener(Auxiliar auxiliar) {
        return rep.findById(auxiliar.getId());
    }

    @Override
    public List<Auxiliar> consultar() {
        return rep.findAll();
    }

    @Override
    public List<Auxiliar> consultarRazonSocial(Auxiliar auxiliar) {
        return  rep.findAll(new Specification<Auxiliar>() {
            @Override
            public Predicate toPredicate(Root<Auxiliar> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (auxiliar.getRazon_social()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("razon_social"), "%"+auxiliar.getRazon_social()+"%")));
                }
                if (auxiliar.getCliente().getId()!=0) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("cliente").get("id"), auxiliar.getCliente().getId())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });

    }
    @Override
    public List<Auxiliar> consultarClienteID(Auxiliar auxiliar) {
        return  rep.findAll(new Specification<Auxiliar>() {
            @Override
            public Predicate toPredicate(Root<Auxiliar> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (auxiliar.getCliente().getId()!=0) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("cliente").get("id"), auxiliar.getCliente().getId())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<Auxiliar> auxiliares=new ArrayList<>();
            List<List<String>>info=Constantes.leer_importar(archivo_temporal,0);
            for (List<String> datos: info){
                Auxiliar auxiliar = new Auxiliar(datos);
                auxiliares.add(auxiliar);
            }
            rep.saveAll(auxiliares);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
