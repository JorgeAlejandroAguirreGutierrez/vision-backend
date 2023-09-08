package com.proyecto.vision.servicios.interf.usuario;

import com.proyecto.vision.modelos.usuario.Usuario;
import com.proyecto.vision.servicios.interf.IGenericoService;
import org.springframework.core.io.Resource;

import java.net.MalformedURLException;
import java.util.List;

public interface IUsuarioService extends IGenericoService<Usuario> {
	void validar(Usuario usuario);
    List<Usuario> consultarPorEmpresa(long empresaId);
    List<Usuario> consultarPorEstado(String estado);
    Usuario activar(Usuario usuario);
    Usuario inactivar(Usuario usuario);
    Usuario obtenerPorApodoYEstado(String apodo, String estado);
    Resource bajarAvatar(long usuarioId) throws MalformedURLException;
}
