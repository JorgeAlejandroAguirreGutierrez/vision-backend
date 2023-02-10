package com.proyecto.sicecuador.datos.cliente;

import com.proyecto.sicecuador.modelos.cliente.Dependiente;
import com.proyecto.sicecuador.modelos.cliente.CelularDependiente;
import com.proyecto.sicecuador.repositorios.cliente.ICelularAuxiliarRepository;
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
@Order(22)
@Profile({"dev","prod"})
public class CelularDependienteData implements ApplicationRunner {
    @Autowired
    private ICelularAuxiliarRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<CelularDependiente> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<CelularDependiente> celulares = new ArrayList<>();
            celulares.add(new CelularDependiente("CEA011908000001", "0987654322", new Dependiente(1)));
            celulares.add(new CelularDependiente("CEA011908000002", "0981234563", new Dependiente(1)));
            celulares.add(new CelularDependiente("CEA011909000003", "0965431235", new Dependiente(2)));
            celulares.add(new CelularDependiente("CEA011909000004", "0965431236", new Dependiente(2)));
            celulares.add(new CelularDependiente("CEA011909000005", "0965431237", new Dependiente(3)));
            celulares.add(new CelularDependiente("CEA011909000006", "0965431238", new Dependiente(4)));
            rep.saveAll(celulares);
        }
    }
}
