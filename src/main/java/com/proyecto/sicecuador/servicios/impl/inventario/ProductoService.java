package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.inventario.Producto;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.repositorios.inventario.IProductoRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.IProductoService;
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
public class ProductoService implements IProductoService {
    @Autowired
    private IProductoRepository rep;
    
    @Override
    public Producto crear(Producto producto) {
    	producto.normalizar();
    	Optional<Producto> getProducto=rep.obtenerPorNombre(producto.getNombre());
    	if(getProducto.isPresent()) {
    		throw new EntidadExistenteException(Constantes.producto);
    	}
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_producto);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	producto.setCodigo(codigo.get());
    	return rep.save(producto);
    }

    @Override
    public Producto actualizar(Producto producto) {
    	producto.normalizar();
    	return rep.save(producto);
    }

    @Override
    public Producto activar(Producto producto) {
        producto.setEstado(Constantes.activo);
        producto=rep.save(producto);
        return producto;
    }

    @Override
    public Producto inactivar(Producto producto) {
        producto.setEstado(Constantes.inactivo);
        producto=rep.save(producto);
        return producto;
    }

    @Override
    public Producto obtener(long id) {
        Optional<Producto> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.producto);
    }

    @Override
    public List<Producto> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<Producto> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<Producto> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public List<Producto> consultarBien() {
        List<Producto> productos=  rep.findAll((root, criteriaQuery, criteriaBuilder) -> {
		    List<Predicate> predicates = new ArrayList<>();
		    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("categoriaProducto").get("descripcion"), Constantes.tipo_producto_bien)));
		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		});
        return productos;
    }

    @Override
    public List<Producto> consultarServicio() {
        return  rep.findAll((root, criteriaQuery, criteriaBuilder) -> {
		    List<Predicate> predicates = new ArrayList<>();
		    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("tipoProducto").get("tipo"), "SERVICIO")));
		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		});
    }

    @Override
    public List<Producto> consultarActivoFijo() {
        return  rep.findAll((root, criteriaQuery, criteriaBuilder) -> {
		    List<Predicate> predicates = new ArrayList<>();
		    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("tipoProducto").get("tipo"), "ACTIVOFIJO")));
		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		});
    }
    @Override
    public List<Producto> consultarBodega() {
        return  rep.findAll((root, criteriaQuery, criteriaBuilder) -> {
		    List<Predicate> predicates = new ArrayList<>();
		    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("tipoProducto").get("tipo"), "ACTIVOFIJO")));
		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		});
    }

    @Override
    public List<Producto> buscar(Producto producto) {
        return  rep.findAll((root, criteriaQuery, criteriaBuilder) -> {
		    List<Predicate> predicates = new ArrayList<>();
		    if (!producto.getNombre().equals(Constantes.vacio)) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("nombre"), "%"+producto.getNombre()+"%")));
		    }
		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		});
    }
    
    @Override
    public void importar(MultipartFile archivo_temporal) {
        try {
            List<Producto> productos=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal,7);
            for (List<String> datos: info) {
                Producto producto = new Producto(datos);
                productos.add(producto);
            }
            rep.saveAll(productos);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

}
