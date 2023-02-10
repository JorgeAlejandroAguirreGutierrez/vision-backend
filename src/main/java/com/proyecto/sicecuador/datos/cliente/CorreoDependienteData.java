package com.proyecto.sicecuador.datos.cliente;

import com.proyecto.sicecuador.modelos.cliente.Dependiente;
import com.proyecto.sicecuador.modelos.cliente.CorreoDependiente;
import com.proyecto.sicecuador.repositorios.cliente.ICorreoAuxiliarRepository;
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
public class CorreoDependienteData implements ApplicationRunner {
    @Autowired
    private ICorreoAuxiliarRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<CorreoDependiente> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<CorreoDependiente> correos = new ArrayList<>();
            correos.add(new CorreoDependiente("COA011908000001", "AUXILIAR1@GMAIL.COM", new Dependiente(1)));
            correos.add(new CorreoDependiente("COA011908000002", "AUXILIAR11@GMAIL.COM", new Dependiente(1)));
            correos.add(new CorreoDependiente("COA011909000003", "AUXILIAR2@GMAIL.COM", new Dependiente(2)));
            correos.add(new CorreoDependiente("COA011909000004", "AUXILIAR3@GMAIL.COM", new Dependiente(3)));
            correos.add(new CorreoDependiente("COA011909000005", "AUXILIAR4@GMAIL.COM", new Dependiente(4)));
            rep.saveAll(correos);
        }
    }
}
