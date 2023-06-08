package com.proyecto.sicecuador.servicios.interf.compra;

import com.proyecto.sicecuador.modelos.cliente.Segmento;
import com.proyecto.sicecuador.modelos.compra.Proveedor;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface IProveedorService extends IGenericoService<Proveedor> {
	void validar(Proveedor proveedor);
	Proveedor activar(Proveedor proveedor);
	Proveedor inactivar(Proveedor proveedor);
	List<Proveedor> consultarPorEstado(String estado);
	List<Proveedor> consultarPorEmpresa(long empresaId);
	List<Proveedor> consultarPorEmpresaYEstado(long empresaId, String estado);
	List<Proveedor> buscar(Proveedor proveedor);
}
