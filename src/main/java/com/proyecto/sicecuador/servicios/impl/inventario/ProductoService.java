package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.modelos.compra.FacturaCompra;
import com.proyecto.sicecuador.modelos.compra.FacturaCompraLinea;
import com.proyecto.sicecuador.modelos.inventario.Kardex;
import com.proyecto.sicecuador.modelos.inventario.Precio;
import com.proyecto.sicecuador.modelos.inventario.Producto;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.repositorios.inventario.IProductoRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.IKardexService;
import com.proyecto.sicecuador.servicios.interf.inventario.IProductoService;
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
        if(producto.getCategoriaProducto().getDescripcion().equals(Constantes.servicio)){
            producto.setKardexs(Collections.emptyList());
        }
        if(producto.getCategoriaProducto().getDescripcion().equals(Constantes.activo_fijo)){
            producto.setKardexs(Collections.emptyList());
        }
        for (Precio precio : producto.getPrecios()) {
            if(precio.getPrecioVentaPublicoManual() < precio.getPrecioVentaPublico()){
                throw new DatoInvalidoException(Constantes.precio_venta_publico_manual);
            }
        }
        for(Kardex kardex: producto.getKardexs()){
            if(kardex.getCantidad() <= Constantes.cero) throw new DatoInvalidoException(Constantes.cantidad);
            if(kardex.getCostoUnitario() <= Constantes.cero) throw new DatoInvalidoException(Constantes.costoUnitario);
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
        Optional<String>codigo=Util.generarCodigo(Constantes.tabla_producto);
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
        return rep.consultarPorBien(Constantes.tipo_producto_bien, Constantes.activo);
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
    public List<Producto> consultarPorProveedor(long proveedorId) {
        return  rep.consultarPorProveedor(proveedorId, Constantes.activo);
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
