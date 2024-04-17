package com.proyecto.vision.servicios.interf.acceso;

import com.proyecto.vision.modelos.acceso.Nuevo;
import com.proyecto.vision.servicios.interf.IGenericoService;

public interface INuevoService extends IGenericoService<Nuevo> {
	void validar(Nuevo nuevo);
	Nuevo ejecutar(Nuevo nuevo);
}
