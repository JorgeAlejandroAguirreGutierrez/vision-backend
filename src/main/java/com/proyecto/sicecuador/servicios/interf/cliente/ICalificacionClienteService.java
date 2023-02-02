package com.proyecto.sicecuador.servicios.interf.cliente;

import java.util.List;

import com.proyecto.sicecuador.modelos.cliente.CalificacionCliente;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface ICalificacionClienteService extends IGenericoService<CalificacionCliente> {
	void validar(CalificacionCliente calificacionCliente);
	List<CalificacionCliente> consultarActivos();
	CalificacionCliente activar(CalificacionCliente calificacionCliente);
	CalificacionCliente inactivar(CalificacionCliente calificacionCliente);
	List<CalificacionCliente> buscar(CalificacionCliente calificacionCliente);
}
