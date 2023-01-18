package com.proyecto.sicecuador.datos.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.cliente.GrupoCliente;
import com.proyecto.sicecuador.modelos.contabilidad.CuentaContable;
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
            List<GrupoCliente> gruposClientes = new ArrayList<>();
            gruposClientes.add(new GrupoCliente("GCL011907000001", "CLIENTES NACIONALES", "NAC", Constantes.activo, new CuentaContable(1)));
            gruposClientes.add(new GrupoCliente("GCL011908000002", "CLIENTES INTERNACIONALES", "INTER", Constantes.activo, new CuentaContable(2)));
            gruposClientes.add(new GrupoCliente("GCL011909000003", "HONORARIOS", "HON", Constantes.activo, new CuentaContable(3)));
            gruposClientes.add(new GrupoCliente("GCL011909000004", "ASESORIAS", "ASES", Constantes.activo, new CuentaContable(4)));
            gruposClientes.add(new GrupoCliente("GCL011909000005", "SERVICIOS", "SRV", Constantes.activo, new CuentaContable(5)));
            rep.saveAll(gruposClientes);
        }
    }
}
