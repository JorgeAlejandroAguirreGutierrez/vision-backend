package com.proyecto.sicecuador.servicios.interf.inventario;

import com.proyecto.sicecuador.modelos.inventario.EquivalenciaMedida;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface IEquivalenciaMedidaService extends IGenericoService<EquivalenciaMedida> {
	void validar(EquivalenciaMedida equivalenciaMedida);
	EquivalenciaMedida activar(EquivalenciaMedida equivalenciaMedida);
	EquivalenciaMedida inactivar(EquivalenciaMedida equivalenciaMedida);
	List<EquivalenciaMedida> consultarActivos();
}
