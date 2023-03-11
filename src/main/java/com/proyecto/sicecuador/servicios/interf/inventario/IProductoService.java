package com.proyecto.sicecuador.servicios.interf.inventario;

import com.proyecto.sicecuador.modelos.inventario.Producto;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface IProductoService extends IGenericoService<Producto> {
	void validar(Producto producto);
    Producto activar(Producto producto);
	Producto inactivar(Producto producto);
	List<Producto> consultarActivos();
    List<Producto> consultarBien();
    List<Producto> consultarServicio();
    List<Producto> consultarActivoFijo();
    List<Producto> consultarPorProveedor(long proveedorId);
    List<Producto> consultarBienPorProveedor(long proveedorId);
    List<Producto> buscar(Producto producto);
}
