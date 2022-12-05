package com.proyecto.sicecuador.servicios.interf.inventario;

import java.util.List;

import com.proyecto.sicecuador.modelos.inventario.Medida;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IMedidaService extends IGenericoService<Medida> {
	Medida activar(Medida medida);
	Medida inactivar(Medida medida);
	List<Medida> consultarActivos();
}
