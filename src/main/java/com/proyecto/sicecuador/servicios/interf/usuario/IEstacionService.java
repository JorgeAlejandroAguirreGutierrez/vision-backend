package com.proyecto.sicecuador.servicios.interf.usuario;

import com.proyecto.sicecuador.modelos.usuario.Estacion;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface IEstacionService extends IGenericoService<Estacion> {
	void validar(Estacion estacion);
	Estacion activar(Estacion estacion);
	Estacion inactivar(Estacion estacion);
	List<Estacion> consultarActivos();
    List<Estacion> consultarPorEstablecimiento(long establecimientoId);
	List<Estacion> consultarPorEstablecimientoPuntoVenta(long establecimientoId);
}
