package com.proyecto.vision.servicios.impl.acceso;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.exception.EntidadExistenteException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.acceso.Usuario;
import com.proyecto.vision.repositorios.acceso.IUsuarioRepository;
import com.proyecto.vision.servicios.interf.acceso.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService {
    @Autowired
    private IUsuarioRepository rep;

    private final Path avatares = Paths.get(Constantes.pathRecursos +  Constantes.pathAvatares);

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
        Optional<Usuario>buscarApodo=rep.obtenerPorApodoYEstado(usuario.getApodo(), Constantes.estadoActivo);
        if(buscarApodo.isPresent()) {
            throw new EntidadExistenteException(Constantes.usuario);
        }
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_usuario);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	usuario.setCodigo(codigo.get());
    	usuario.setEstado(Constantes.estadoActivo);
    	Usuario res = rep.save(usuario);
        res.normalizar();
        return res;
    }

    @Override
    public Usuario actualizar(Usuario usuario) {
        validar(usuario);
        Usuario res = rep.save(usuario);
        res.normalizar();
        return res;
    }

    @Override
    public Usuario activar(Usuario usuario) {
        validar(usuario);
        usuario.setEstado(Constantes.estadoActivo);
        Usuario res = rep.save(usuario);
        res.normalizar();
        return res;
    }

    @Override
    public Usuario inactivar(Usuario usuario) {
        validar(usuario);
        usuario.setEstado(Constantes.estadoInactivo);
        Usuario res = rep.save(usuario);
        res.normalizar();
        return res;
    }

    @Override
    public Usuario obtener(long id) {
        Optional<Usuario> usuario = rep.findById(id);
        if(usuario.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.usuario);
        }
        Usuario res = usuario.get();
        res.normalizar();
        return res;

    }

    @Override
    public List<Usuario> consultar() {
        return rep.consultar();
    }

    @Override
    public List<Usuario> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    @Override
    public List<Usuario> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public Usuario obtenerPorApodoYEstado(String apodo, String estado) {
    	Optional<Usuario> usuario = rep.obtenerPorApodoYEstado(apodo, estado);
    	if(usuario.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.usuario);
        }
        Usuario res = usuario.get();
        res.normalizar();
        return res;
    }

    @Override
    public Resource bajarAvatar(long usuarioId) throws MalformedURLException {
        Optional<Usuario> optional = rep.findById(usuarioId);
        if(optional.isEmpty()){
            throw new EntidadNoExistenteException(Constantes.empresa);
        }
        Usuario usuario = optional.get();
        Path file = this.avatares.resolve(usuario.getAvatar());
        Resource resource = new UrlResource(file.toUri());
        return resource;
    }
}
