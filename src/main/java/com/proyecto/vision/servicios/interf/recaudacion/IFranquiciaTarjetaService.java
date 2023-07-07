package com.proyecto.vision.servicios.interf.recaudacion;

import java.util.List;

import com.proyecto.vision.modelos.recaudacion.FranquiciaTarjeta;
import com.proyecto.vision.servicios.interf.IGenericoService;

public interface IFranquiciaTarjetaService extends IGenericoService<FranquiciaTarjeta> {
	void validar(FranquiciaTarjeta franquiciaTarjeta);
	FranquiciaTarjeta activar(FranquiciaTarjeta franquiciaTarjeta);
	FranquiciaTarjeta inactivar(FranquiciaTarjeta franquiciaTarjeta);
	List<FranquiciaTarjeta> consultarPorEstado(String estado);
}
