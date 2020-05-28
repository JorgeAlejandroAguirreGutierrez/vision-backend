package com.proyecto.sicecuador.servicios.impl.usuario;

import com.proyecto.sicecuador.modelos.usuario.Permiso;
import com.proyecto.sicecuador.repositorios.interf.usuario.IPermisoRepository;
import com.proyecto.sicecuador.servicios.interf.usuario.IPermisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PermisoService implements IPermisoService {
    @Autowired
    private IPermisoRepository rep;
    @Override
    public Permiso crear(Permiso permiso) {
        return rep.save(permiso);
    }

    @Override
    public Permiso actualizar(Permiso permiso) {
        return rep.save(permiso);
    }

    @Override
    public Permiso eliminar(Permiso permiso) {
        rep.deleteById(permiso.getId());
        return permiso;
    }

    @Override
    public Optional<Permiso> obtener(Permiso permiso) {
        return rep.findById(permiso.getId());
    }

    @Override
    public List<Permiso> consultar() {
        return rep.findAll();
    }
}
