package com.proyecto.sicecuador.servicios.interf.configuracion;

import com.proyecto.sicecuador.modelos.configuracion.Regimen;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface IRegimenService extends IGenericoService<Regimen> {
	void validar(Regimen regimen);
	Regimen activar(Regimen regimen);
	Regimen inactivar(Regimen regimen);
	List<Regimen> consultarPorEstado(String estado);
	List<Regimen> buscar(Regimen regimen);
}
