package com.proyecto.sicecuador.servicios.interf.usuario;

import com.proyecto.sicecuador.modelos.usuario.Usuario;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface IUsuarioService extends IGenericoService<Usuario> {
	void validar(Usuario usuario);
    List<Usuario> consultarActivos();
    Usuario activar(Usuario usuario);
    Usuario inactivar(Usuario usuario);
    Usuario obtenerPorApodo(String apodo);
}
