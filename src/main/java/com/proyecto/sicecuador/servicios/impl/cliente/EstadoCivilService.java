package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.modelos.cliente.EstadoCivil;
import com.proyecto.sicecuador.repositorios.interf.cliente.IEstadoCivilRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IEstadoCivilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class EstadoCivilService implements IEstadoCivilService {
    @Autowired
    private IEstadoCivilRepository rep;
    @Override
    public EstadoCivil crear(EstadoCivil estado_civil) {
        return rep.save(estado_civil);
    }

    @Override
    public EstadoCivil actualizar(EstadoCivil estado_civil) {
        return rep.save(estado_civil);
    }

    @Override
    public EstadoCivil eliminar(EstadoCivil estado_civil) {
        rep.deleteById(estado_civil.getId());
        return estado_civil;
    }

    @Override
    public Optional<EstadoCivil> obtener(EstadoCivil estado_civil) {
        return rep.findById(estado_civil.getId());
    }

    @Override
    public List<EstadoCivil> consultar() {
        return rep.findAll();
    }
}
