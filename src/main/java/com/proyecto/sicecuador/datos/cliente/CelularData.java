package com.proyecto.sicecuador.datos.cliente;

import com.proyecto.sicecuador.modelos.cliente.Celular;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.repositorios.cliente.ICelularRepository;
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
public class CelularData implements ApplicationRunner {
    @Autowired
    private ICelularRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Celular> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Celular> celulares = new ArrayList<>();
            celulares.add(new Celular("CEL011908000001", "0987654321", new Cliente(1)));
            celulares.add(new Celular("CEL011908000002", "0981234567", new Cliente(1)));
            celulares.add(new Celular("CEL011909000003", "0965431234", new Cliente(2)));
            rep.saveAll(celulares);
        }
    }

}
