package com.proyecto.sicecuador.servicios.interf.proveedor;

import com.proyecto.sicecuador.modelos.proveedor.Proveedor;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface IProveedorService extends IGenericoService<Proveedor> {
	Proveedor activar(Proveedor proveedor);
	Proveedor inactivar(Proveedor proveedor);
	List<Proveedor> consultarActivos();
    List<Proveedor> buscar(Proveedor proveedor);
}
