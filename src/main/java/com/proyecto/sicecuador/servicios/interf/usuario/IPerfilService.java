package com.proyecto.vision.servicios.interf.usuario;

import java.util.List;

import com.proyecto.vision.modelos.usuario.Perfil;
import com.proyecto.vision.servicios.interf.IGenericoService;

public interface IPerfilService extends IGenericoService<Perfil> {
	void validar(Perfil perfil);
	Perfil activar(Perfil perfil);
	Perfil inactivar(Perfil perfil);
	List<Perfil> consultarPorEstado(String estado);
}
