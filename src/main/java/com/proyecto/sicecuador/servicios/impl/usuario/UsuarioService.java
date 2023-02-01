package com.proyecto.sicecuador.servicios.impl.usuario;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.ParametroInvalidoException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.usuario.Usuario;
import com.proyecto.sicecuador.repositorios.usuario.IUsuarioRepository;
import com.proyecto.sicecuador.servicios.interf.usuario.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService {
    @Autowired
    private IUsuarioRepository rep;
    
    @Override
    public Usuario crear(Usuario usuario) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_usuario);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	if (!usuario.getContrasena().equals(usuario.getConfirmarContrasena())) {
    		throw new ParametroInvalidoException(Constantes.parametro_contrasena);
    	}
    	String digito = usuario.getTelefono().substring(0, 1);
    	if(usuario.getTelefono().length() != 11 || !digito.equals("0")) {
    		throw new ParametroInvalidoException(Constantes.parametro_telefono);
    	}
    	String digito2 = usuario.getCelular().substring(0, 2);
    	if(usuario.getCelular().length() != 12 || !digito2.equals("09")) {
    		throw new ParametroInvalidoException(Constantes.parametro_celular);
    	}
    	if(!usuario.getCorreo().contains("@")) {
    		throw new ParametroInvalidoException(Constantes.parametro_correo);
    	}

        byte[] avatarBytes = usuario.getAvatar64().getBytes(StandardCharsets.UTF_8);
    	//byte[] avatar = Base64.getDecoder().decode(usuario.getAvatar64());
        usuario.setAvatar(avatarBytes);
    	usuario.setEstado(Constantes.activo);
    	return rep.save(usuario);
    }

    @Override
    public Usuario actualizar(Usuario usuario) {
        return rep.save(usuario);
    }

    @Override
    public Usuario activar(Usuario usuario) {
        usuario.setEstado(Constantes.activo);
        return rep.save(usuario);
    }

    @Override
    public Usuario inactivar(Usuario usuario) {
        usuario.setEstado(Constantes.inactivo);
        return rep.save(usuario);
    }

    @Override
    public Usuario obtener(long id) {
        Optional<Usuario> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.usuario);
    }

    @Override
    public List<Usuario> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<Usuario> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<Usuario> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public List<Usuario> consultarVendedores() {
        return null;
    }

    @Override
    public List<Usuario> consultarCajeros() {
        return null;
    }
    
    public Usuario obtenerPorApodo(String apodo) {
    	Optional<Usuario> res= rep.obtenerPorApodo(apodo, Constantes.activo);
    	if(res.isPresent()) {
    		return res.get();
    	}
    	throw new EntidadNoExistenteException(Constantes.usuario);
    }

    @Override
    public void importar(MultipartFile archivo_temporal) {
        try {
            List<Usuario> usuarios=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal,5);
            for (List<String> datos: info) {
                Usuario usuario = new Usuario(datos);
                usuarios.add(usuario);
            }
            rep.saveAll(usuarios);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
