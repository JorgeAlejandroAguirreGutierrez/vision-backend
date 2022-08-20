package com.proyecto.sicecuador.datos.cliente;

import com.proyecto.sicecuador.modelos.cliente.GrupoCliente;
import com.proyecto.sicecuador.repositorios.cliente.IGrupoClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Order(9)
@Profile({"dev","prod"})
public class GrupoClienteData implements ApplicationRunner {
    @Autowired
    private IGrupoClienteRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<GrupoCliente> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<GrupoCliente> grupos_clientes = new ArrayList<>();
            grupos_clientes.add(new GrupoCliente("GCL011907000001", "CLIENTES NACIONALES", "NAC", "ACTIVO"));
            grupos_clientes.add(new GrupoCliente("GCL011908000002", "CLIENTES INTERNACIONALES", "INTER", "ACTIVO"));
            grupos_clientes.add(new GrupoCliente("GCL011909000003", "HONORARIOS", "HON", "ACTIVO"));
            grupos_clientes.add(new GrupoCliente("GCL011909000004", "ASESORIAS", "ASES", "ACTIVO"));
            grupos_clientes.add(new GrupoCliente("GCL011909000005", "SERVICIOS", "SRV", "ACTIVO"));
            rep.saveAll(grupos_clientes);
        }
    }
}
