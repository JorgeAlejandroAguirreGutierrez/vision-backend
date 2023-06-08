package com.proyecto.sicecuador.servicios.interf.configuracion;

import java.util.List;

import com.proyecto.sicecuador.modelos.configuracion.Genero;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IGeneroService extends IGenericoService<Genero> {
	void validar(Genero genero);
	Genero activar (Genero genero);
	Genero inactivar (Genero genero);
	List<Genero> consultarPorEstado(String estado);
	List<Genero> buscar(Genero genero);
}
