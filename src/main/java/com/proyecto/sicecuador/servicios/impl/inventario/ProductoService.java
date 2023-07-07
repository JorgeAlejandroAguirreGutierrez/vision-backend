package com.proyecto.vision.servicios.impl.inventario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.modelos.compra.FacturaCompra;
import com.proyecto.vision.modelos.compra.FacturaCompraLinea;
import com.proyecto.vision.modelos.inventario.Kardex;
import com.proyecto.vision.modelos.inventario.Precio;
import com.proyecto.vision.modelos.inventario.Producto;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.EntidadExistenteException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.repositorios.inventario.IProductoRepository;
import com.proyecto.vision.servicios.interf.inventario.IKardexService;
import com.proyecto.vision.servicios.interf.inventario.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;

import java.util.*;

@Service
public class ProductoService implements IProductoService {
    @Autowired
    private IProductoRepository rep;
    @Autowired
    private IKardexService kardexService;

    @Override
    public void validar(Producto producto) {
        if(producto.getNombre().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.nombre);
        if(producto.getConsignacion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.consignacion);
        if(producto.getCategoriaProducto().getId() == Constantes.cero) throw new DatoInvalidoException(Constantes.categoria_producto);
        if(producto.getGrupoProducto().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.grupo_producto);
        if(producto.getTipoGasto().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.tipo_gasto);
        if(producto.getImpuesto().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.impuesto);
        if(producto.getPrecios().isEmpty()) throw new DatoInvalidoException(Constantes.precio);
        if(!producto.getCategoriaProducto().getDescripcion().equals(Constantes.bien)){
            producto.setKardexs(Collections.emptyList());
        }
        for(Kardex kardex: producto.getKardexs()){
            if(kardex.getSaldo() <= Constantes.cero) throw new DatoInvalidoException(Constantes.cantidad);
            if(kardex.getCostoPromedio() <= Constantes.cero) throw new DatoInvalidoException(Constantes.costoUnitario);
            if(kardex.getBodega().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.bodega);
        }
    }
    
    @Override
    public Producto crear(Producto producto) {
        validar(producto);
        Optional<Producto> getProducto=rep.obtenerPorNombre(producto.getNombre());
        if(getProducto.isPresent()) {
            throw new EntidadExistenteException(Constantes.producto);
        }
        Optional<String>codigo=Util.generarCodigoPorEmpresa(Constantes.tabla_producto, producto.getEmpresa().getId());
        if (codigo.isEmpty()) {
            throw new CodigoNoExistenteException();
        }
    	producto.setCodigo(codigo.get());
    	producto.setEstado(Constantes.activo);
    	Producto res = rep.save(producto);
        res.normalizar();
        return res;
    }

    @Override
    public Producto actualizar(Producto producto) {
    	validar(producto);
        Producto res = rep.save(producto);
        res.normalizar();
        return res;
    }

    @Override
    public Producto activar(Producto producto) {
        validar(producto);
        producto.setEstado(Constantes.activo);
        Producto res = rep.save(producto);
        res.normalizar();
        return res;
    }

    @Override
    public Producto inactivar(Producto producto) {
        validar(producto);
        producto.setEstado(Constantes.inactivo);
        Producto res = rep.save(producto);
        res.normalizar();
        return res;
    }

    @Override
    public Producto obtener(long id) {
        Optional<Producto> producto= rep.findById(id);
        if(producto.isPresent()) {
        	Producto res = producto.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.producto);
    }

    @Override
    public List<Producto> consultar() {
        return rep.consultar();
    }
    
    @Override
    public List<Producto> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public List<Producto> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    @Override
    public List<Producto> consultarPorEmpresaYEstado(long empresaId, String estado){
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }

    @Override
    public Page<Producto> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public List<Producto> consultarPorProveedor(long proveedorId) {
        return  rep.consultarPorProveedor(proveedorId, Constantes.activo);
    }

    @Override
    public List<Producto> consultarPorCategoriaProductoYEstado(String categoriaProducto, String estado) {
        return rep.consultarPorCategoriaProductoYEstado(categoriaProducto, estado);
    }
    @Override
    public List<Producto> consultarPorCategoriaProductoYProveedorYEstado(String categoriaProducto, long proveedorId, String estado) {
        return rep.consultarPorCategoriaProductoYProveedorYEstado(categoriaProducto, proveedorId, estado);
    }
    @Override
    public List<Producto> consultarPorCategoriaProductoYEmpresaYEstado(String categoriaProducto, long empresaId, String estado) {
        return  rep.consultarPorCategoriaProductoYEmpresaYEstado(categoriaProducto, empresaId, estado);
    }

    @Override
    public List<Producto> consultarPorCategoriaProductoYProveedorYEmpresaYEstado(String categoriaProducto, long proveedorId, long empresaId, String estado) {
        return  rep.consultarPorCategoriaProductoYProveedorYEmpresaYEstado(categoriaProducto, proveedorId, empresaId, estado);
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
}
