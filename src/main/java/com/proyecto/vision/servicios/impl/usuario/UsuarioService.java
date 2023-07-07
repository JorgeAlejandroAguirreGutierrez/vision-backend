package com.proyecto.vision.servicios.impl.usuario;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.exception.EntidadExistenteException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.usuario.Usuario;
import com.proyecto.vision.repositorios.usuario.IUsuarioRepository;
import com.proyecto.vision.servicios.interf.usuario.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService {
    @Autowired
    private IUsuarioRepository rep;

    @Override
    public void validar(Usuario usuario) {
        if(usuario.getIdentificacion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.identificacion);
        if(usuario.getApodo().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.apodo);
        if(usuario.getNombre().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.nombre);
        if(usuario.getCorreo().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.correo);
        if(usuario.getContrasena().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.contrasena);
        if(usuario.getConfirmarContrasena().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.confirmarContrasena);
        if(usuario.getCambiarContrasena().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.cambiarContrasena);
        if(usuario.getPregunta().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.pregunta);
        if(usuario.getEstacion().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.estacion);
        if(usuario.getPerfil().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.perfil);
        if (!usuario.getContrasena().equals(usuario.getConfirmarContrasena())) throw new DatoInvalidoException(Constantes.contrasena);
        if(!usuario.getCorreo().contains(Constantes.arroba)) throw new DatoInvalidoException(Constantes.correo);
    }

    @Override
    public Usuario crear(Usuario usuario) {
        validar(usuario);
        Optional<Usuario>buscarApodo=rep.obtenerPorApodoYEstado(usuario.getApodo(), Constantes.activo);
        if(buscarApodo.isPresent()) {
            throw new EntidadExistenteException(Constantes.usuario);
        }
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_usuario);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	usuario.setAvatar(usuario.getAvatar64().getBytes(StandardCharsets.UTF_8));
    	usuario.setCodigo(codigo.get());
    	usuario.setEstado(Constantes.activo);
    	Usuario res = rep.save(usuario);
        res.normalizar();
        return res;
    }

    @Override
    public Usuario actualizar(Usuario usuario) {
        validar(usuario);
        usuario.setAvatar(usuario.getAvatar64().getBytes(StandardCharsets.UTF_8));
        Usuario res = rep.save(usuario);
        res.normalizar();
        return res;
    }

    @Override
    public Usuario activar(Usuario usuario) {
        validar(usuario);
        usuario.setEstado(Constantes.activo);
        Usuario res = rep.save(usuario);
        res.normalizar();
        return res;
    }

    @Override
    public Usuario inactivar(Usuario usuario) {
        validar(usuario);
        usuario.setEstado(Constantes.inactivo);
        Usuario res = rep.save(usuario);
        res.normalizar();
        return res;
    }

    @Override
    public Usuario obtener(long id) {
        Optional<Usuario> usuario = rep.findById(id);
        if(usuario.isPresent()) {
            Usuario res = usuario.get();
            if(res.getAvatar() != null) {
                res.setAvatar64(new String(res.getAvatar(), StandardCharsets.UTF_8));
            }
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.usuario);
    }

    @Override
    public List<Usuario> consultar() {
        List<Usuario> usuarios = rep.consultar();
        if(!usuarios.isEmpty()) {
            for (Usuario usuario : usuarios){
                if(usuario.getAvatar() != null) {
                    usuario.setAvatar64(new String(usuario.getAvatar(), StandardCharsets.UTF_8));
                }
            }
            return usuarios;
        }
        throw new EntidadNoExistenteException(Constantes.empresa);
    }
    
    @Override
    public List<Usuario> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public Page<Usuario> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public Usuario obtenerPorApodoYEstado(String apodo, String estado) {
    	Optional<Usuario> usuario = rep.obtenerPorApodoYEstado(apodo, estado);
    	if(usuario.isPresent()) {
    		Usuario res = usuario.get();
            if(res.getAvatar() != null) {
                res.setAvatar64(new String(res.getAvatar(), StandardCharsets.UTF_8));
            }
            res.normalizar();
            return res;
    	}
    	throw new EntidadNoExistenteException(Constantes.usuario);
    }
}
