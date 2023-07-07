package com.proyecto.vision.datos.cliente;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.cliente.GrupoCliente;
import com.proyecto.vision.modelos.contabilidad.CuentaContable;
import com.proyecto.vision.modelos.usuario.Empresa;
import com.proyecto.vision.repositorios.cliente.IGrupoClienteRepository;
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
            gruposClientes.add(new GrupoCliente("GCL012306000001", "CLIENTES NACIONALES", "NAC", Constantes.activo, new CuentaContable(5), new Empresa(1)));
            gruposClientes.add(new GrupoCliente("GCL012306000002", "CLIENTES INTERNACIONALES", "INTER", Constantes.activo, new CuentaContable(6), new Empresa(1)));
            gruposClientes.add(new GrupoCliente("GCL012306000003", "HONORARIOS", "HON", Constantes.activo, new CuentaContable(8), new Empresa(1)));
            gruposClientes.add(new GrupoCliente("GCL012306000004", "ASESORIAS", "ASES", Constantes.activo, new CuentaContable(11), new Empresa(1)));
            gruposClientes.add(new GrupoCliente("GCL012306000005", "SERVICIOS", "SRV", Constantes.activo, new CuentaContable(13), new Empresa(1)));

            gruposClientes.add(new GrupoCliente("GCL022306000001", "CLIENTES NACIONALES", "NAC", Constantes.activo, new CuentaContable(323), new Empresa(2)));
            gruposClientes.add(new GrupoCliente("GCL022306000002", "CLIENTES INTERNACIONALES", "INTER", Constantes.activo, new CuentaContable(324), new Empresa(2)));

            rep.saveAll(gruposClientes);
        }
    }
}
