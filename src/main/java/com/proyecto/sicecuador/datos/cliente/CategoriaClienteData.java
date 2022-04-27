package com.proyecto.sicecuador.datos.cliente;

import com.proyecto.sicecuador.modelos.cliente.CategoriaCliente;
import com.proyecto.sicecuador.repositorios.cliente.ICategoriaClienteRepository;
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
@Order(2)
@Profile({"dev","prod"})
public class CategoriaClienteData implements ApplicationRunner {
    @Autowired
    private ICategoriaClienteRepository rep;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<CategoriaCliente> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<CategoriaCliente> categoria_clientes = new ArrayList<>();
            categoria_clientes.add(new CategoriaCliente("CCL011909000001", "EXELENTE", "E"));
            categoria_clientes.add(new CategoriaCliente("CCL011909000002", "MUY BUENO", "M"));
            categoria_clientes.add(new CategoriaCliente("CCL011909000003", "BUENO", "B"));
            categoria_clientes.add(new CategoriaCliente("CCL011909000004", "REGULAR", "R"));
            categoria_clientes.add(new CategoriaCliente("CCL011909000005", "MALO", "MA"));
            rep.saveAll(categoria_clientes);
        }
    }
}
