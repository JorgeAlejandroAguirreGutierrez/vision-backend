package com.proyecto.sicecuador.servicios.interf.configuracion;

import java.util.List;

import com.proyecto.sicecuador.modelos.configuracion.EstadoCivil;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IEstadoCivilService extends IGenericoService<EstadoCivil> {
	void validar(EstadoCivil estadoCivil);
	List<EstadoCivil> consultarPorEstado(String estado);
	EstadoCivil activar(EstadoCivil estadoCivil);
	EstadoCivil inactivar(EstadoCivil estadoCivil);
	List<EstadoCivil> buscar(EstadoCivil estado_civil);
}
