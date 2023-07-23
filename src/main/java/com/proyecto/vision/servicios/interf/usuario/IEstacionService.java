package com.proyecto.vision.servicios.interf.usuario;

import com.proyecto.vision.modelos.usuario.Estacion;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;

public interface IEstacionService extends IGenericoService<Estacion> {
	void validar(Estacion estacion);
	Estacion activar(Estacion estacion);
	Estacion inactivar(Estacion estacion);
	List<Estacion> consultarPorEstado(String estado);
    List<Estacion> consultarPorEstablecimiento(long establecimientoId);
	List<Estacion> consultarEstacionesPorEstablecimiento(long establecimientoId);
	List<Estacion> consultarPuntosVentaPorEstablecimiento(long establecimientoId);
}
