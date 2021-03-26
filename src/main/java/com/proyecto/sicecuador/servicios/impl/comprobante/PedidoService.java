package com.proyecto.sicecuador.servicios.impl.comprobante;

import com.proyecto.sicecuador.modelos.comprobante.Pedido;
import com.proyecto.sicecuador.repositorios.interf.comprobante.IPedidoRepository;
import com.proyecto.sicecuador.repositorios.interf.configuracion.IParametroRepository;
import com.proyecto.sicecuador.servicios.interf.comprobante.IPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
@Service
public class PedidoService implements IPedidoService {
    @Autowired
    private IPedidoRepository rep;
    @Autowired
    private static IParametroRepository parametroRep;
    
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

    @Override
    public boolean importar(MultipartFile file) {
        return false;
    }
}
