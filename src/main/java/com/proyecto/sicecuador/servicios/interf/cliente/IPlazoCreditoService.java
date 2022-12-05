package com.proyecto.sicecuador.servicios.interf.cliente;

import java.util.List;
import java.util.Optional;

import com.proyecto.sicecuador.modelos.cliente.PlazoCredito;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IPlazoCreditoService extends IGenericoService<PlazoCredito> {
	PlazoCredito activar(PlazoCredito plazoCredito);
	PlazoCredito inactivar(PlazoCredito plazoCredito);
	List<PlazoCredito> consultarActivos();
}
