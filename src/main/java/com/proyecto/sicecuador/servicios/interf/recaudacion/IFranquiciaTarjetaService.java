package com.proyecto.sicecuador.servicios.interf.recaudacion;

import java.util.List;

import com.proyecto.sicecuador.modelos.recaudacion.FranquiciaTarjeta;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IFranquiciaTarjetaService extends IGenericoService<FranquiciaTarjeta> {
	void validar(FranquiciaTarjeta franquiciaTarjeta);
	FranquiciaTarjeta activar(FranquiciaTarjeta franquiciaTarjeta);
	FranquiciaTarjeta inactivar(FranquiciaTarjeta franquiciaTarjeta);
	List<FranquiciaTarjeta> consultarActivos();
}
