package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.modelos.cliente.CategoriaCliente;
import com.proyecto.sicecuador.otros.Util;
import com.proyecto.sicecuador.repositorios.interf.cliente.ICategoriaClienteRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.ICategoriaClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CategoriaClienteService implements ICategoriaClienteService {
    @Autowired
    private ICategoriaClienteRepository rep;
    @Override
    public CategoriaCliente crear(CategoriaCliente categoria_cliente) {
        //categoria_cliente.setCodigo(Util.generarCodigo("categoria_cliente","CREAR",rep.count()));
        return rep.save(categoria_cliente);
    }

    @Override
    public CategoriaCliente actualizar(CategoriaCliente categoria_cliente) {
        return rep.save(categoria_cliente);
    }

    @Override
    public CategoriaCliente eliminar(CategoriaCliente categoria_cliente) {
        rep.deleteById(categoria_cliente.getId());
        return categoria_cliente;
    }

    @Override
    public Optional<CategoriaCliente> obtener(CategoriaCliente categoria_cliente) {
        return rep.findById(categoria_cliente.getId());
    }

    @Override
    public List<CategoriaCliente> consultar() {
        return rep.findAll();
    }
}
