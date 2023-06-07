package com.proyecto.sicecuador.servicios.interf.inventario;

import com.proyecto.sicecuador.modelos.inventario.TipoOperacion;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface ITipoOperacionService extends IGenericoService<TipoOperacion> {
	void validar(TipoOperacion tipoGasto);
	TipoOperacion activar(TipoOperacion tipoGasto);
	TipoOperacion inactivar(TipoOperacion tipoGasto);
	List<TipoOperacion> consultarPorEstado(String estado);
}
