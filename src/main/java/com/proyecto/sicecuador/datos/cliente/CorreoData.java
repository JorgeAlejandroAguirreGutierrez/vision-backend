package com.proyecto.sicecuador.datos.cliente;

import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.cliente.Correo;
import com.proyecto.sicecuador.repositorios.cliente.ICorreoRepository;
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
@Order(47)
@Profile({"dev","prod"})
public class CorreoData implements ApplicationRunner {
    @Autowired
    private ICorreoRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Correo> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Correo> correos = new ArrayList<>();
            correos.add(new Correo("COR011907000001", "je.hidalgob@hotmail.com", new Cliente(1)));
            correos.add(new Correo("COR011908000002", "alejo@gmail.com", new Cliente(2)));
            correos.add(new Correo("COR011909000003", "gatosohidalgo@hotmail.com", new Cliente(3)));
            rep.saveAll(correos);
        }
    }
}
