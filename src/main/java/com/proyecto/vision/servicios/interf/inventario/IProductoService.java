package com.proyecto.vision.servicios.interf.inventario;

import com.proyecto.vision.modelos.inventario.Producto;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;

public interface IProductoService extends IGenericoService<Producto> {
	void validar(Producto producto);
    Producto activar(Producto producto);
	Producto inactivar(Producto producto);
	List<Producto> consultarPorEstado(String estado);
    List<Producto> consultarPorEmpresa(long empresaId);
    List<Producto> consultarPorEmpresaYEstado(long empresaId, String estado);
    List<Producto> consultarPorProveedor(long proveedorId);
    List<Producto> consultarPorCategoriaProductoYEstado(String categoriaProducto, String estado);
    List<Producto> consultarPorCategoriaProductoYProveedorYEstado(String categoriaProducto, long proveedorId, String estado);
    List<Producto> consultarPorCategoriaProductoYEmpresaYEstado(String categoriaProducto, long empresaId, String estado);
    List<Producto> consultarPorCategoriaProductoYProveedorYEmpresaYEstado(String categoriaProducto, long empresaId, long proveedorId, String estado);
    List<Producto> buscar(Producto producto);
}
