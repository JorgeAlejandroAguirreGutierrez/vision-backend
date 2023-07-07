package com.proyecto.vision.servicios.interf.cliente;

import java.util.List;

import com.proyecto.vision.modelos.cliente.FormaPago;
import com.proyecto.vision.servicios.interf.IGenericoService;

public interface IFormaPagoService extends IGenericoService<FormaPago> {
	void validar(FormaPago formaPago);
	List<FormaPago> consultarPorEstado(String estado);
	FormaPago activar(FormaPago formaPago);
	FormaPago inactivar(FormaPago formaPago);
	List<FormaPago> buscar(FormaPago forma_pago);
}
