package com.proyecto.sicecuador.servicios.interf.compra;

import com.proyecto.sicecuador.modelos.compra.GrupoProveedor;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface IGrupoProveedorService extends IGenericoService<GrupoProveedor> {
	void validar(GrupoProveedor grupoProveedor);
	GrupoProveedor activar(GrupoProveedor grupoProveedor);
	GrupoProveedor inactivar(GrupoProveedor grupoProveedor);
	List<GrupoProveedor> consultarActivos();
	List<GrupoProveedor> buscar(GrupoProveedor grupoProveedor);
}
