package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.modelos.cliente.PlazoCredito;
import com.proyecto.sicecuador.otros.Util;
import com.proyecto.sicecuador.repositorios.interf.cliente.IOrigenIngresoRepository;
import com.proyecto.sicecuador.repositorios.interf.cliente.IPlazoCreditoRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IPlazoCreditoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PlazoCreditoService implements IPlazoCreditoService {
    @Autowired
    private IPlazoCreditoRepository rep;
    @Override
    public PlazoCredito crear(PlazoCredito plazo_credito) {
        return rep.save(plazo_credito);
    }

    @Override
    public PlazoCredito actualizar(PlazoCredito plazo_credito) {
        return rep.save(plazo_credito);
    }

    @Override
    public PlazoCredito eliminar(PlazoCredito plazo_credito) {
        rep.deleteById(plazo_credito.getId());
        return plazo_credito;
    }

    @Override
    public Optional<PlazoCredito> obtener(PlazoCredito plazo_credito) {
        return rep.findById(plazo_credito.getId());
    }

    @Override
    public List<PlazoCredito> consultar() {
        return rep.findAll();
    }
}
