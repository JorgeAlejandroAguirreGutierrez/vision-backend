package com.proyecto.sicecuador.servicios.impl.usuario;
import com.proyecto.sicecuador.modelos.usuario.Usuario;
import com.proyecto.sicecuador.repositorios.interf.usuario.IUsuarioRepository;
import com.proyecto.sicecuador.servicios.interf.usuario.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
@Service
public class UsuarioService implements IUsuarioService {
    @Autowired
    private IUsuarioRepository rep;
    @Override
    public Usuario crear(Usuario usuario) {
        return rep.save(usuario);
    }

    @Override
    public Usuario actualizar(Usuario usuario) {
        return rep.save(usuario);
    }

    @Override
    public Usuario eliminar(Usuario usuario) {
        return null;
    }

    @Override
    public Optional<Usuario> obtener(Usuario usuario) {
        return rep.findById(usuario.getId());
    }

    @Override
    public List<Usuario> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile file) {
        return false;
    }

    @Override
    public List<Usuario> consultarVendedores() {
        return null;
    }

    @Override
    public List<Usuario> consultarCajeros() {
        return null;
    }

    @Override
    public Optional<Usuario> activar(Usuario usuario) {
        Usuario _usuario=rep.findById(usuario.getId()).get();
        usuario.setActivo(false);
        _usuario=rep.save(_usuario);
        return Optional.of(usuario);
    }
}
