package com.proyecto.sicecuador.servicios.impl.usuario;

import com.proyecto.sicecuador.modelos.usuario.Perfil;
import com.proyecto.sicecuador.repositorios.interf.usuario.IEstablecimientoRepository;
import com.proyecto.sicecuador.repositorios.interf.usuario.IPerfilRepository;
import com.proyecto.sicecuador.servicios.interf.usuario.IPerfilService;
import com.proyecto.sicecuador.servicios.interf.usuario.IPuntoVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PerfilService implements IPerfilService {
    @Autowired
    private IPerfilRepository rep;
    @Override
    public Perfil crear(Perfil perfil) {
        return rep.save(perfil);
    }

    @Override
    public Perfil actualizar(Perfil perfil) {
        return rep.save(perfil);
    }

    @Override
    public Perfil eliminar(Perfil perfil) {
        rep.deleteById(perfil.getId());
        return perfil;
    }

    @Override
    public Optional<Perfil> obtener(Perfil perfil) {
        return rep.findById(perfil.getId());
    }

    @Override
    public List<Perfil> consultar() {
        return rep.findAll();
    }
}
