package com.proyecto.sicecuador.servicios.impl.usuario;

import com.proyecto.sicecuador.modelos.usuario.Sesion;
import com.proyecto.sicecuador.modelos.usuario.Usuario;
import com.proyecto.sicecuador.repositorios.interf.usuario.ISesionRepository;
import com.proyecto.sicecuador.repositorios.interf.usuario.IUsuarioRepository;
import com.proyecto.sicecuador.servicios.interf.usuario.ISesionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class SesionService implements ISesionService {
    @Autowired
    private ISesionRepository rep;
    @Autowired
    private IUsuarioRepository rep_usuario;
    @Override
    public Sesion crear(Sesion sesion) {
        Usuario usuario=rep_usuario.findByIdentificacionContrasena(sesion.getUsuario().getIdentificacion(),sesion.getUsuario().getContrasena());
        sesion.setUsuario(usuario);
        //sesion.setFecha_apertura(new Date());
        //sesion.setActiva(true);
        return rep.save(sesion);
    }

    @Override
    public Sesion actualizar(Sesion sesion) {
        return rep.save(sesion);
    }

    @Override
    public Sesion eliminar(Sesion sesion) {
        rep.deleteById(sesion.getId());
        return sesion;
    }

    @Override
    public Optional<Sesion> obtener(Sesion sesion) {
        return rep.findById(sesion.getId());
    }

    @Override
    public List<Sesion> consultar() {
        return rep.findAll();
    }

    @Override
    public Optional<Sesion> verificar(Sesion sesion) {
        Sesion _sesion= rep.findById(sesion.getId()).get();
        if (_sesion.getFecha_cierre()==null){
            return Optional.of(_sesion);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Sesion> cerrar(Sesion sesion) {
        Sesion _sesion=rep.findById(sesion.getId()).get();
        sesion.setFecha_cierre(new Date());
        sesion.setActiva(false);
        _sesion=rep.save(_sesion);
        return Optional.of(_sesion);
    }
}
