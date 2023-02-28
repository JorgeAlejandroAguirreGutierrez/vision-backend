package com.proyecto.sicecuador.servicios.interf.compra;

import com.proyecto.sicecuador.modelos.compra.Proveedor;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface IProveedorService extends IGenericoService<Proveedor> {
	void validar(Proveedor proveedor);
	Proveedor activar(Proveedor proveedor);
	Proveedor inactivar(Proveedor proveedor);
	List<Proveedor> consultarActivos();
    List<Proveedor> buscar(Proveedor proveedor);
}