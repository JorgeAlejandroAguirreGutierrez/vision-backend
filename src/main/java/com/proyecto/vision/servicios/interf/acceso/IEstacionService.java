package com.proyecto.vision.servicios.interf.acceso;

import com.proyecto.vision.modelos.acceso.Estacion;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;

public interface IEstacionService extends IGenericoService<Estacion> {
	void validar(Estacion estacion);
	Estacion activar(Estacion estacion);
	Estacion inactivar(Estacion estacion);
	List<Estacion> consultarPorEstado(String estado);
	List<Estacion> consultarPorEmpresa(long empresaId);
    List<Estacion> consultarPorEstablecimiento(long establecimientoId);
	List<Estacion> consultarEstacionesPorEstablecimiento(long establecimientoId);
	List<Estacion> consultarPuntosVentaPorEstablecimiento(long establecimientoId);
}
