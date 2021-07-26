package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.Constantes;
//import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.inventario.Proveedor;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.ModeloExistenteException;
import com.proyecto.sicecuador.repositorios.inventario.IProveedorRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.IProveedorService;

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
public class ProveedorService implements IProveedorService {
    @Autowired
    private IProveedorRepository rep;
    
    @Override
    public Proveedor crear(Proveedor proveedor) {
    	//proveedor.normalizar();
/*    	Optional<Proveedor> getProveedor=rep.obtenerPorRazonSocial(proveedor.getRazonSocial());
    	if(getProveedor.isPresent()) {
    		throw new ModeloExistenteException();
    	}*/
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_proveedor);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	proveedor.setCodigo(codigo.get());
    	return rep.save(proveedor);
    }

    @Override
    public Proveedor actualizar(Proveedor proveedor) {
    	//proveedor.normalizar();
    	return rep.save(proveedor);
    }

    @Override
    public Proveedor eliminar(Proveedor proveedor) {
        rep.deleteById(proveedor.getId());
        return proveedor;
    }

    @Override
    public Optional<Proveedor> obtener(Proveedor proveedor) {
        return rep.findById(proveedor.getId());
    }

    @Override
    public List<Proveedor> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<Proveedor> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }


    @Override
    public List<Proveedor> buscar(Proveedor proveedor) {
        return  rep.findAll((root, criteriaQuery, criteriaBuilder) -> {
		    List<Predicate> predicates = new ArrayList<>();
		    if (!proveedor.getRazonSocial().equals(Constantes.vacio)) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("razon_social"), "%"+proveedor.getRazonSocial()+"%")));
		    }
		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		});
    }
    
    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<Proveedor> proveedores=new ArrayList<>();
            List<List<String>>info= Util.leer_importar(archivo_temporal,7);
            for (List<String> datos: info) {
                Proveedor proveedor = new Proveedor(datos);
                proveedores.add(proveedor);
            }
            if(proveedores.isEmpty()){
                return false;
            }
            rep.saveAll(proveedores);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}