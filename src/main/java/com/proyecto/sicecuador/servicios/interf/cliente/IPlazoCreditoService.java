package com.proyecto.sicecuador.servicios.interf.cliente;

import java.util.List;

import com.proyecto.sicecuador.modelos.cliente.GrupoCliente;
import com.proyecto.sicecuador.modelos.cliente.PlazoCredito;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IPlazoCreditoService extends IGenericoService<PlazoCredito> {
	void validar(PlazoCredito plazoCredito);
	List<PlazoCredito> consultarPorEmpresa(long empresaId);
	List<PlazoCredito> consultarPorEstado(String estado);
	List<PlazoCredito> consultarPorEmpresaYEstado(long empresaId, String estado);
	PlazoCredito activar(PlazoCredito plazoCredito);
	PlazoCredito inactivar(PlazoCredito plazoCredito);
}
