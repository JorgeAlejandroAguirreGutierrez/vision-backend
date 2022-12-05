package com.proyecto.sicecuador.servicios.interf.cliente;

import java.util.List;

import com.proyecto.sicecuador.modelos.cliente.EstadoCivil;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IEstadoCivilService extends IGenericoService<EstadoCivil> {
	EstadoCivil activar(EstadoCivil estadoCivil);
	EstadoCivil inactivar(EstadoCivil estadoCivil);
	List<EstadoCivil> consultarActivos();
	List<EstadoCivil> buscar(EstadoCivil estado_civil);
}
