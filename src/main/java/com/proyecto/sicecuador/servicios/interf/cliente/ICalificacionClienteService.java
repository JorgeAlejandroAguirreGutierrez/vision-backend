package com.proyecto.sicecuador.servicios.interf.cliente;

import java.util.List;

import com.proyecto.sicecuador.modelos.cliente.CalificacionCliente;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface ICalificacionClienteService extends IGenericoService<CalificacionCliente> {
	List<CalificacionCliente> buscar(CalificacionCliente calificacion_cliente);
}
