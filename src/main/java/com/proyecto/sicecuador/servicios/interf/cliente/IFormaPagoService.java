package com.proyecto.sicecuador.servicios.interf.cliente;

import java.util.List;

import com.proyecto.sicecuador.modelos.cliente.FormaPago;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IFormaPagoService extends IGenericoService<FormaPago> {
	void validar(FormaPago formaPago);
	FormaPago activar(FormaPago formaPago);
	FormaPago inactivar(FormaPago formaPago);
	List<FormaPago> consultarPorEstado(String estado);
	List<FormaPago> buscar(FormaPago forma_pago);
}
