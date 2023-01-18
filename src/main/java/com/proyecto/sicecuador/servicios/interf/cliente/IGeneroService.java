package com.proyecto.sicecuador.servicios.interf.cliente;

import java.util.List;

import com.proyecto.sicecuador.modelos.cliente.Genero;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IGeneroService extends IGenericoService<Genero> {
	Genero activar (Genero genero);
	Genero inactivar (Genero genero);
	List<Genero> consultarActivos();
	List<Genero> buscar(Genero genero);
}
