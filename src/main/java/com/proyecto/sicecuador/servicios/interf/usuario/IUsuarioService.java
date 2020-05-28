package com.proyecto.sicecuador.servicios.interf.usuario;

import com.proyecto.sicecuador.modelos.usuario.Usuario;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService extends IGenericoService<Usuario> {
    List<Usuario> consultarVendedores();
    List<Usuario> consultarCajeros();
    Optional<Usuario> activar(Usuario usuario);
}
