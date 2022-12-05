package com.proyecto.sicecuador.servicios.interf.inventario;

import com.proyecto.sicecuador.modelos.inventario.EquivalenciaMedida;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface IEquivalenciaMedidaService extends IGenericoService<EquivalenciaMedida> {
	EquivalenciaMedida activar(EquivalenciaMedida equivalenciaMedida);
	EquivalenciaMedida inactivar(EquivalenciaMedida equivalenciaMedida);
	List<EquivalenciaMedida> consultarActivos();
	EquivalenciaMedida obtenerMedida1Medida2(EquivalenciaMedida tabla);
    List<EquivalenciaMedida> buscar(EquivalenciaMedida tem);
    List<EquivalenciaMedida> buscarMedidasEquivalentes(EquivalenciaMedida equivalencias);
}
