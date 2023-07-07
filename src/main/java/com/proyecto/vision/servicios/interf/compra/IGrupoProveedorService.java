package com.proyecto.vision.servicios.interf.compra;

import com.proyecto.vision.modelos.compra.GrupoProveedor;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;

public interface IGrupoProveedorService extends IGenericoService<GrupoProveedor> {
	void validar(GrupoProveedor grupoProveedor);
	GrupoProveedor activar(GrupoProveedor grupoProveedor);
	GrupoProveedor inactivar(GrupoProveedor grupoProveedor);
	List<GrupoProveedor> consultarPorEstado(String estado);
	List<GrupoProveedor> consultarPorEmpresa(long empresaId);
	List<GrupoProveedor> consultarPorEmpresaYEstado(long empresaId, String estado);
	List<GrupoProveedor> buscar(GrupoProveedor grupoProveedor);
}
