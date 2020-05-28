package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.modelos.cliente.GrupoCliente;
import com.proyecto.sicecuador.otros.Util;
import com.proyecto.sicecuador.repositorios.interf.cliente.IClienteRepository;
import com.proyecto.sicecuador.repositorios.interf.cliente.IGrupoClienteRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IGrupoClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class GrupoClienteService implements IGrupoClienteService {
    @Autowired
    private IGrupoClienteRepository rep;
    @Override
    public GrupoCliente crear(GrupoCliente grupo_cliente) {
        //grupo_cliente.setCodigo(Util.generarCodigo("grupo_cliente","CREAR",rep.count()));
        return rep.save(grupo_cliente);
    }

    @Override
    public GrupoCliente actualizar(GrupoCliente grupo_cliente) {
        return rep.save(grupo_cliente);
    }

    @Override
    public GrupoCliente eliminar(GrupoCliente grupo_cliente) {
        rep.deleteById(grupo_cliente.getId());
        return grupo_cliente;
    }

    @Override
    public Optional<GrupoCliente> obtener(GrupoCliente grupo_cliente) {
        return rep.findById(grupo_cliente.getId());
    }

    @Override
    public List<GrupoCliente> consultar() {
        return rep.findAll();
    }
}
