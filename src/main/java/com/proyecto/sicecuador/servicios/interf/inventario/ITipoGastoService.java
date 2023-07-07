package com.proyecto.vision.servicios.interf.inventario;

import java.util.List;

import com.proyecto.vision.modelos.inventario.TipoGasto;
import com.proyecto.vision.servicios.interf.IGenericoService;

public interface ITipoGastoService extends IGenericoService<TipoGasto> {
	void validar(TipoGasto tipoGasto);
}
