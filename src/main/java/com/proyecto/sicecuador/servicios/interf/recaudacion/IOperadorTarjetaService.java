package com.proyecto.sicecuador.servicios.interf.recaudacion;

import com.proyecto.sicecuador.modelos.recaudacion.OperadorTarjeta;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface IOperadorTarjetaService extends IGenericoService<OperadorTarjeta> {
	void validar(OperadorTarjeta operadorTarjeta);
	OperadorTarjeta activar(OperadorTarjeta operadorTarjeta);
	OperadorTarjeta inactivar(OperadorTarjeta operadorTarjeta);
	List<OperadorTarjeta> consultarPorEstado(String estado);
	List<OperadorTarjeta> consultarPorTipo(String tipo);

}
