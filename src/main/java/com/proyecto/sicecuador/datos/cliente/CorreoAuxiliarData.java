package com.proyecto.sicecuador.datos.cliente;

import com.proyecto.sicecuador.modelos.cliente.Auxiliar;
import com.proyecto.sicecuador.modelos.cliente.CorreoAuxiliar;
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
public class CorreoAuxiliarData implements ApplicationRunner {
    @Autowired
    private ICorreoAuxiliarRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<CorreoAuxiliar> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<CorreoAuxiliar> correos = new ArrayList<>();
            correos.add(new CorreoAuxiliar("COA011908000001", "AUXILIAR1@GMAIL.COM", new Auxiliar(1)));
            correos.add(new CorreoAuxiliar("COA011908000002", "AUXILIAR11@GMAIL.COM", new Auxiliar(1)));
            correos.add(new CorreoAuxiliar("COA011909000003", "AUXILIAR2@GMAIL.COM", new Auxiliar(2)));
            correos.add(new CorreoAuxiliar("COA011909000004", "AUXILIAR3@GMAIL.COM", new Auxiliar(3)));
            correos.add(new CorreoAuxiliar("COA011909000005", "AUXILIAR4@GMAIL.COM", new Auxiliar(4)));
            rep.saveAll(correos);
        }
    }
}
