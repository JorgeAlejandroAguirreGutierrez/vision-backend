package com.proyecto.sicecuador.datos.cliente;

import com.proyecto.sicecuador.modelos.cliente.Dependiente;
import com.proyecto.sicecuador.modelos.cliente.TelefonoDependiente;
import com.proyecto.sicecuador.repositorios.cliente.ITelefonoAuxiliarRepository;
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
public class TelefonoAuxiliarData implements ApplicationRunner {
    @Autowired
    private ITelefonoAuxiliarRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<TelefonoDependiente> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<TelefonoDependiente> telefonos = new ArrayList<>();
            telefonos.add(new TelefonoDependiente("TEA011908000001", "032964121", new Dependiente(1)));
            telefonos.add(new TelefonoDependiente("TEA011908000002", "032964122", new Dependiente(1)));
            telefonos.add(new TelefonoDependiente("TEA011909000003", "032964123", new Dependiente(2)));
            telefonos.add(new TelefonoDependiente("TEA011909000004", "032964124", new Dependiente(3)));
            telefonos.add(new TelefonoDependiente("TEA011909000005", "032964125", new Dependiente(4)));
            rep.saveAll(telefonos);
        }
    }
}
