package com.proyecto.sicecuador.servicios.interf.usuario;

import java.util.List;

import com.proyecto.sicecuador.modelos.usuario.Permiso;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IPermisoService extends IGenericoService<Permiso> {
	Permiso activar(Permiso permiso);
	Permiso inactivar(Permiso permiso);
	List<Permiso> consultarActivos();
}
