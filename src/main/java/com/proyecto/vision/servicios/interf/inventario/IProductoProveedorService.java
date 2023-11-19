package com.proyecto.vision.servicios.interf.inventario;

import com.proyecto.vision.modelos.inventario.ProductoProveedor;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;

public interface IProductoProveedorService extends IGenericoService<ProductoProveedor> {
	void validar(ProductoProveedor productoProveedor);
    ProductoProveedor activar(ProductoProveedor productoProveedor);
	ProductoProveedor inactivar(ProductoProveedor productoProveedor);
	List<ProductoProveedor> consultarPorEstado(String estado);
    List<ProductoProveedor> consultarPorEmpresa(long empresaId);
    List<ProductoProveedor> consultarPorEmpresaYEstado(long empresaId, String estado);
    List<ProductoProveedor> consultarPorProveedorYEstado(long proveedorId, String estado);
    List<ProductoProveedor> consultarPorProductoYEstado(long productoId, String estado);
    List<ProductoProveedor> buscar(ProductoProveedor productoProveedor);
}
