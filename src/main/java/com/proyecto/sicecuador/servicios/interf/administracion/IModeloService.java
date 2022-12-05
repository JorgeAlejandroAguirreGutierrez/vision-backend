package com.proyecto.sicecuador.servicios.interf.administracion;

import java.util.List;

import com.proyecto.sicecuador.modelos.administracion.Modelo;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IModeloService extends IGenericoService<Modelo> {
	Modelo activar(Modelo modelo);
	Modelo inactivar(Modelo modelo);
	List<Modelo> consultarActivos();
}
