package com.proyecto.vision.servicios.interf.configuracion;

import java.util.List;

import com.proyecto.vision.modelos.configuracion.EstadoCivil;
import com.proyecto.vision.servicios.interf.IGenericoService;

public interface IEstadoCivilService extends IGenericoService<EstadoCivil> {
	void validar(EstadoCivil estadoCivil);
	List<EstadoCivil> consultarPorEstado(String estado);
	EstadoCivil activar(EstadoCivil estadoCivil);
	EstadoCivil inactivar(EstadoCivil estadoCivil);
}
