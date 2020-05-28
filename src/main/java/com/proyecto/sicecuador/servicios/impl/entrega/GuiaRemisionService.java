package com.proyecto.sicecuador.servicios.impl.entrega;

import com.proyecto.sicecuador.modelos.entrega.GuiaRemision;
import com.proyecto.sicecuador.repositorios.interf.entrega.IGuiaRemisionRepository;
import com.proyecto.sicecuador.servicios.interf.entrega.IGuiaRemisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class GuiaRemisionService implements IGuiaRemisionService {
    @Autowired
    private IGuiaRemisionRepository rep;
    @Override
    public GuiaRemision crear(GuiaRemision guia_remision) {
        return rep.save(guia_remision);
    }

    @Override
    public GuiaRemision actualizar(GuiaRemision guia_remision) {
        return rep.save(guia_remision);
    }

    @Override
    public GuiaRemision eliminar(GuiaRemision guia_remision) {
        rep.deleteById(guia_remision.getId());
        return guia_remision;
    }

    @Override
    public Optional<GuiaRemision> obtener(GuiaRemision guia_remision) {
        return rep.findById(guia_remision.getId());
    }

    @Override
    public List<GuiaRemision> consultar() {
        return rep.findAll();
    }
}
