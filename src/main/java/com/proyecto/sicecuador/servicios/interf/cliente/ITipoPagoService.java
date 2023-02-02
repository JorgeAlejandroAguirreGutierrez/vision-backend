package com.proyecto.sicecuador.servicios.interf.cliente;

import java.util.List;

import com.proyecto.sicecuador.modelos.cliente.TipoPago;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface ITipoPagoService extends IGenericoService<TipoPago> {
	void validar(TipoPago tipoPago);
	TipoPago activar(TipoPago tipoPago);
	TipoPago inactivar(TipoPago tipoPago);
	List<TipoPago> consultarActivos();
}
