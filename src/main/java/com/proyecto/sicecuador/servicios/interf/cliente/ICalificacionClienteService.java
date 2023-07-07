package com.proyecto.vision.servicios.interf.cliente;

import java.util.List;

import com.proyecto.vision.modelos.cliente.CalificacionCliente;
import com.proyecto.vision.modelos.cliente.Segmento;
import com.proyecto.vision.servicios.interf.IGenericoService;

public interface ICalificacionClienteService extends IGenericoService<CalificacionCliente> {
	void validar(CalificacionCliente calificacionCliente);
	List<CalificacionCliente> consultarPorEmpresa(long empresaId);
	List<CalificacionCliente> consultarPorEstado(String estado);
	List<CalificacionCliente> consultarPorEmpresaYEstado(long empresaId, String estado);
	CalificacionCliente activar(CalificacionCliente calificacionCliente);
	CalificacionCliente inactivar(CalificacionCliente calificacionCliente);
	List<CalificacionCliente> buscar(CalificacionCliente calificacionCliente);
}
