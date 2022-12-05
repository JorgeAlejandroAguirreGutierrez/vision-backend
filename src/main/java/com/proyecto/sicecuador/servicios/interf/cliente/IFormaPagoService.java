package com.proyecto.sicecuador.servicios.interf.cliente;

import java.util.List;

import com.proyecto.sicecuador.modelos.cliente.FormaPago;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IFormaPagoService extends IGenericoService<FormaPago> {
	FormaPago activar(FormaPago formaPago);
	FormaPago inactivar(FormaPago formaPago);
	List<FormaPago> consultarActivos();
	List<FormaPago> buscar(FormaPago forma_pago);
}
