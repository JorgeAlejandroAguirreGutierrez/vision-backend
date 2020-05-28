package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.modelos.cliente.RetencionCliente;
import com.proyecto.sicecuador.repositorios.interf.cliente.IOrigenIngresoRepository;
import com.proyecto.sicecuador.repositorios.interf.cliente.IRetencionClienteRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IRetencionClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class RetencionClienteService implements IRetencionClienteService {
    @Autowired
    private IRetencionClienteRepository rep;
    @Override
    public RetencionCliente crear(RetencionCliente retencion_cliente) {
        return rep.save(retencion_cliente);
    }

    @Override
    public RetencionCliente actualizar(RetencionCliente retencion_cliente) {
        return rep.save(retencion_cliente);
    }

    @Override
    public RetencionCliente eliminar(RetencionCliente retencion_cliente) {
        rep.deleteById(retencion_cliente.getId());
        return retencion_cliente;
    }

    @Override
    public Optional<RetencionCliente> obtener(RetencionCliente retencion_cliente) {
        return rep.findById(retencion_cliente.getId());
    }

    @Override
    public List<RetencionCliente> consultar() {
        return rep.findAll();
    }
}
