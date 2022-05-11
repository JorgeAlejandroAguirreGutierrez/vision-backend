package com.proyecto.sicecuador.servicios.interf.cliente;

import java.util.List;
import java.util.Optional;

import com.proyecto.sicecuador.modelos.cliente.CalificacionCliente;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface ICalificacionClienteService extends IGenericoService<CalificacionCliente> {
	Optional<CalificacionCliente> eliminarPersonalizado(long id);
	List<CalificacionCliente> buscar(CalificacionCliente calificacion_cliente);
}
