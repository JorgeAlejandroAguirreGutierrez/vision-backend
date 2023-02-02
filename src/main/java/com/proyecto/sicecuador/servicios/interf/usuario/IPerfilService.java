package com.proyecto.sicecuador.servicios.interf.usuario;

import java.util.List;

import com.proyecto.sicecuador.modelos.usuario.Perfil;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IPerfilService extends IGenericoService<Perfil> {
	void validar(Perfil perfil);
	Perfil activar(Perfil perfil);
	Perfil inactivar(Perfil perfil);
	List<Perfil> consultarActivos();
}
