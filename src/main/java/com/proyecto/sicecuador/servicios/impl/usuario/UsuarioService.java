package com.proyecto.sicecuador.servicios.impl.usuario;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.usuario.Usuario;
import com.proyecto.sicecuador.repositorios.interf.usuario.IUsuarioRepository;
import com.proyecto.sicecuador.servicios.interf.usuario.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
    	usuario.setCodigo(codigo.get());
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

    @Override
    public Optional<Usuario> activar(Usuario usuario) {
        Usuario _usuario=rep.findById(usuario.getId()).get();
        usuario.setActivo(false);
        _usuario=rep.save(_usuario);
        return Optional.of(usuario);
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<Usuario> usuarios=new ArrayList<>();
            List<List<String>>info= Util.leer_importar(archivo_temporal,5);
            for (List<String> datos: info) {
                Usuario usuario = new Usuario(datos);
                usuarios.add(usuario);
            }
            if(usuarios.isEmpty()){
                return false;
            }
            rep.saveAll(usuarios);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
