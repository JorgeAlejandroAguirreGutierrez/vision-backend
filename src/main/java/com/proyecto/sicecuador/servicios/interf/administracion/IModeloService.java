package com.proyecto.vision.servicios.interf.administracion;

import java.util.List;

import com.proyecto.vision.modelos.administracion.Modelo;
import com.proyecto.vision.servicios.interf.IGenericoService;

public interface IModeloService extends IGenericoService<Modelo> {
	Modelo activar(Modelo modelo);
	Modelo inactivar(Modelo modelo);
	List<Modelo> consultarActivos();
}
