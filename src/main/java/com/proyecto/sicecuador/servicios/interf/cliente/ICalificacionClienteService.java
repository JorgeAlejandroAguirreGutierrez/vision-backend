package com.proyecto.sicecuador.servicios.interf.cliente;

import java.util.List;

import com.proyecto.sicecuador.modelos.cliente.CalificacionCliente;
import com.proyecto.sicecuador.modelos.cliente.Segmento;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface ICalificacionClienteService extends IGenericoService<CalificacionCliente> {
	void validar(CalificacionCliente calificacionCliente);
	List<CalificacionCliente> consultarPorEstado(String estado);
	List<CalificacionCliente> consultarPorEmpresa(long empresaId);
	List<CalificacionCliente> consultarPorEmpresaYEstado(long empresaId, String estado);
	CalificacionCliente activar(CalificacionCliente calificacionCliente);
	CalificacionCliente inactivar(CalificacionCliente calificacionCliente);
	List<CalificacionCliente> buscar(CalificacionCliente calificacionCliente);
}
