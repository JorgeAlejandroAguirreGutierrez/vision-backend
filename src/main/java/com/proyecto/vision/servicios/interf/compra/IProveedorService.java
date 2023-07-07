package com.proyecto.vision.servicios.interf.compra;

import com.proyecto.vision.modelos.cliente.Cliente;
import com.proyecto.vision.modelos.cliente.Segmento;
import com.proyecto.vision.modelos.compra.Proveedor;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;

public interface IProveedorService extends IGenericoService<Proveedor> {
	void validar(Proveedor proveedor);
	Proveedor activar(Proveedor proveedor);
	Proveedor inactivar(Proveedor proveedor);
	List<Proveedor> consultarPorEstado(String estado);
	List<Proveedor> consultarPorEmpresa(long empresaId);
	List<Proveedor> consultarPorEmpresaYEstado(long empresaId, String estado);
	List<Proveedor> buscar(Proveedor proveedor);
	Proveedor buscarClienteBase(Proveedor proveedor);
	Proveedor buscarContribuyente(Proveedor proveedor);
	Proveedor validarIdentificacionPorEmpresa(long empresaId, String identificacion);
}
