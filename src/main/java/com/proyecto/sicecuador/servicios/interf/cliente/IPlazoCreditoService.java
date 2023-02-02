package com.proyecto.sicecuador.servicios.interf.cliente;

import java.util.List;

import com.proyecto.sicecuador.modelos.cliente.PlazoCredito;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IPlazoCreditoService extends IGenericoService<PlazoCredito> {
	void validar(PlazoCredito plazoCredito);
	PlazoCredito activar(PlazoCredito plazoCredito);
	PlazoCredito inactivar(PlazoCredito plazoCredito);
	List<PlazoCredito> consultarActivos();
}
