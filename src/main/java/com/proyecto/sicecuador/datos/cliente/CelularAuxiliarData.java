package com.proyecto.sicecuador.datos.cliente;

import com.proyecto.sicecuador.modelos.cliente.Auxiliar;
import com.proyecto.sicecuador.modelos.cliente.CelularAuxiliar;
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
public class CelularAuxiliarData implements ApplicationRunner {
    @Autowired
    private ICelularAuxiliarRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<CelularAuxiliar> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<CelularAuxiliar> celulares = new ArrayList<>();
            celulares.add(new CelularAuxiliar("CEA011908000001", "0987654322", new Auxiliar(1)));
            celulares.add(new CelularAuxiliar("CEA011908000002", "0981234563", new Auxiliar(1)));
            celulares.add(new CelularAuxiliar("CEA011909000003", "0965431235", new Auxiliar(2)));
            celulares.add(new CelularAuxiliar("CEA011909000004", "0965431236", new Auxiliar(2)));
            celulares.add(new CelularAuxiliar("CEA011909000005", "0965431237", new Auxiliar(3)));
            celulares.add(new CelularAuxiliar("CEA011909000006", "0965431238", new Auxiliar(4)));
            rep.saveAll(celulares);
        }
    }
}
