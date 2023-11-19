package com.proyecto.vision.servicios.impl.inventario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.exception.EntidadExistenteException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.inventario.ProductoProveedor;
import com.proyecto.vision.repositorios.inventario.IProductoProveedorRepository;
import com.proyecto.vision.servicios.interf.inventario.IProductoProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoProveedorService implements IProductoProveedorService {
    @Autowired
    private IProductoProveedorRepository rep;

    @Override
    public void validar(ProductoProveedor productoProveedor) {
        if(productoProveedor.getCodigoEquivalente().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.codigoNumerico);
        if(productoProveedor.getProducto().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.producto);
        if(productoProveedor.getProveedor().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.proveedor);
    }
    
    @Override
    public ProductoProveedor crear(ProductoProveedor productoProveedor) {
        validar(productoProveedor);
        //Optional<ProductoProveedor> productoProductoExistentePorCodigoEquivalente = rep.obtenerPorCodigoEquivalenteYEmpresa(productoProveedor.getCodigoEquivalente(), productoProveedor.getProducto().getEmpresa().getId());
        //if(productoProductoExistentePorCodigoEquivalente.isPresent()) {
        //    throw new EntidadExistenteException(Constantes.codigoNumerico);
        //}
        Optional<String>codigo = Util.generarCodigo(Constantes.tabla_producto_proveedor);
        if (codigo.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
        productoProveedor.setCodigo(codigo.get());
        productoProveedor.setEstado(Constantes.estadoActivo);
    	ProductoProveedor res = rep.save(productoProveedor);
        res.normalizar();
        return res;
    }

    @Override
    public ProductoProveedor actualizar(ProductoProveedor productoProveedor) {
    	validar(productoProveedor);
        ProductoProveedor res = rep.save(productoProveedor);
        res.normalizar();
        return res;
    }

    @Override
    public ProductoProveedor activar(ProductoProveedor productoProveedor) {
        validar(productoProveedor);
        productoProveedor.setEstado(Constantes.estadoActivo);
        ProductoProveedor res = rep.save(productoProveedor);
        res.normalizar();
        return res;
    }

    @Override
    public ProductoProveedor inactivar(ProductoProveedor productoProveedor) {
        validar(productoProveedor);
        productoProveedor.setEstado(Constantes.estadoInactivo);
        ProductoProveedor res = rep.save(productoProveedor);
        res.normalizar();
        return res;
    }

    @Override
    public ProductoProveedor obtener(long id) {
        Optional<ProductoProveedor> productoProveedor= rep.findById(id);
        if(productoProveedor.isPresent()) {
        	ProductoProveedor res = productoProveedor.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.producto);
    }

    @Override
    public List<ProductoProveedor> consultar() {
        return rep.consultar();
    }
    
    @Override
    public List<ProductoProveedor> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public List<ProductoProveedor> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    @Override
    public List<ProductoProveedor> consultarPorEmpresaYEstado(long empresaId, String estado){
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }

    @Override
    public Page<ProductoProveedor> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public List<ProductoProveedor> consultarPorProveedorYEstado(long proveedorId, String estado) {
        return  rep.consultarPorProveedorYEstado(proveedorId, estado);
    }

    @Override
    public List<ProductoProveedor> consultarPorProductoYEstado(long productoId, String estado) {
        return rep.consultarPorProductoYEstado(productoId, estado);
    }

    @Override
    public List<ProductoProveedor> buscar(ProductoProveedor productoProveedor) {
        return  rep.findAll((root, criteriaQuery, criteriaBuilder) -> {
		    List<Predicate> predicates = new ArrayList<>();
		    if (!productoProveedor.getProducto().getNombre().equals(Constantes.vacio)) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("nombre"), "%"+productoProveedor.getProducto().getNombre()+"%")));
		    }
		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		});
    }
}
