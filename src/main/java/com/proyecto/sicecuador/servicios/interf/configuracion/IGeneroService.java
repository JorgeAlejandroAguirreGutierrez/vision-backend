package com.proyecto.vision.servicios.interf.configuracion;

import java.util.List;

import com.proyecto.vision.modelos.configuracion.Genero;
import com.proyecto.vision.servicios.interf.IGenericoService;

public interface IGeneroService extends IGenericoService<Genero> {
	void validar(Genero genero);
	Genero activar (Genero genero);
	Genero inactivar (Genero genero);
	List<Genero> consultarPorEstado(String estado);
	List<Genero> buscar(Genero genero);
}
