package com.proyecto.sicecuador.servicios.interf.inventario;

import java.util.List;

import com.proyecto.sicecuador.modelos.inventario.TipoGasto;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface ITipoGastoService extends IGenericoService<TipoGasto> {
	TipoGasto activar(TipoGasto tipoGasto);
	TipoGasto inactivar(TipoGasto tipoGasto);
	List<TipoGasto> consultarActivos();
}
