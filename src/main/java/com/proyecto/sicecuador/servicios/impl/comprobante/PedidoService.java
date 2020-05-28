package com.proyecto.sicecuador.servicios.impl.comprobante;

import com.proyecto.sicecuador.modelos.comprobante.Pedido;
import com.proyecto.sicecuador.repositorios.interf.comprobante.IPedidoRepository;
import com.proyecto.sicecuador.servicios.interf.comprobante.IPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PedidoService implements IPedidoService {
    @Autowired
    private IPedidoRepository rep;
    @Override
    public Pedido crear(Pedido pedido) {
        return rep.save(pedido);
    }

    @Override
    public Pedido actualizar(Pedido pedido) {
        return rep.save(pedido);
    }

    @Override
    public Pedido eliminar(Pedido pedido) {
        rep.deleteById(pedido.getId());
        return pedido;
    }

    @Override
    public Optional<Pedido> obtener(Pedido pedido) {
        return rep.findById(pedido.getId());
    }

    @Override
    public List<Pedido> consultar() {
        return rep.findAll();
    }
}
