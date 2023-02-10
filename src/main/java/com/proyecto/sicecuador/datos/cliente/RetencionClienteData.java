package com.proyecto.sicecuador.datos.cliente;

import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.cliente.RetencionCliente;
import com.proyecto.sicecuador.modelos.configuracion.TipoRetencion;
import com.proyecto.sicecuador.repositorios.cliente.IRetencionClienteRepository;
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
@Order(21)
@Profile({"dev","prod"})
public class RetencionClienteData implements ApplicationRunner {
    @Autowired
    private IRetencionClienteRepository rep;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<RetencionCliente> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<RetencionCliente> retenciones_clientes = new ArrayList<>();
            retenciones_clientes.add(new RetencionCliente("RCL011907000001", new TipoRetencion(1), new Cliente(1)));
            retenciones_clientes.add(new RetencionCliente("RCL011907000002", new TipoRetencion(9), new Cliente(1)));
            retenciones_clientes.add(new RetencionCliente("RCL011907000003", new TipoRetencion(2), new Cliente(1)));
            retenciones_clientes.add(new RetencionCliente("RCL011907000004", new TipoRetencion(13), new Cliente(1)));
            retenciones_clientes.add(new RetencionCliente("RCL011907000005", new TipoRetencion(1), new Cliente(2)));
            retenciones_clientes.add(new RetencionCliente("RCL011908000006", new TipoRetencion(9), new Cliente(2)));
            retenciones_clientes.add(new RetencionCliente("RCL011908000007", new TipoRetencion(2), new Cliente(2)));
            retenciones_clientes.add(new RetencionCliente("RCL011908000008", new TipoRetencion(13), new Cliente(2)));
            retenciones_clientes.add(new RetencionCliente("RCL011908000009", new TipoRetencion(1), new Cliente(3)));
            retenciones_clientes.add(new RetencionCliente("RCL011908000010", new TipoRetencion(9), new Cliente(3)));
            retenciones_clientes.add(new RetencionCliente("RCL011908000011", new TipoRetencion(2), new Cliente(3)));
            retenciones_clientes.add(new RetencionCliente("RCL011908000012", new TipoRetencion(13), new Cliente(3)));
            rep.saveAll(retenciones_clientes);
        }
    }
}
