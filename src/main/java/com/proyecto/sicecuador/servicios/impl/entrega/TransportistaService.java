package com.proyecto.sicecuador.servicios.impl.entrega;

import com.proyecto.sicecuador.modelos.entrega.Transportista;
import com.proyecto.sicecuador.repositorios.interf.entrega.IGuiaRemisionRepository;
import com.proyecto.sicecuador.repositorios.interf.entrega.ITransportistaRepository;
import com.proyecto.sicecuador.servicios.interf.entrega.ITransportistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TransportistaService implements ITransportistaService {
    @Autowired
    private ITransportistaRepository rep;
    @Override
    public Transportista crear(Transportista transportista) {
        return rep.save(transportista);
    }

    @Override
    public Transportista actualizar(Transportista transportista) {
        return rep.save(transportista);
    }

    @Override
    public Transportista eliminar(Transportista transportista) {
        rep.deleteById(transportista.getId());
        return transportista;
    }

    @Override
    public Optional<Transportista> obtener(Transportista transportista) {
        return rep.findById(transportista.getId());
    }

    @Override
    public List<Transportista> consultar() {
        return rep.findAll();
    }
}
