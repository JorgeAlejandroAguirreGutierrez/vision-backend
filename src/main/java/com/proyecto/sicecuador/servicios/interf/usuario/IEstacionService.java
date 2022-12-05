package com.proyecto.sicecuador.servicios.interf.usuario;

import com.proyecto.sicecuador.modelos.usuario.Establecimiento;
import com.proyecto.sicecuador.modelos.usuario.Estacion;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface IEstacionService extends IGenericoService<Estacion> {
	Estacion activar(Estacion estacion);
	Estacion inactivar(Estacion estacion);
	List<Estacion> consultarActivos();
    List<Estacion> consultarEstablecimiento(Establecimiento establecimiento);
}
