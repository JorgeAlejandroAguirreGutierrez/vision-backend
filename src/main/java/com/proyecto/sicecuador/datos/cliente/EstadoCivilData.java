package com.proyecto.sicecuador.datos.cliente;

import com.proyecto.sicecuador.modelos.cliente.EstadoCivil;
import com.proyecto.sicecuador.repositorios.cliente.IEstadoCivilRepository;
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
@Order(6)
@Profile({"dev","prod"})
public class EstadoCivilData implements ApplicationRunner {
    @Autowired
    private IEstadoCivilRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<EstadoCivil> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<EstadoCivil> estados_civiles = new ArrayList<>();
            estados_civiles.add(new EstadoCivil("ECV011907000001", "SOLTERO", "S"));
            estados_civiles.add(new EstadoCivil("ECV011907000002", "CASADO", "C"));
            estados_civiles.add(new EstadoCivil("ECV011908000003", "VIUDO", "V"));
            estados_civiles.add(new EstadoCivil("ECV011908000004", "DIVORCIADO", "D"));
            estados_civiles.add(new EstadoCivil("ECV01190900005", "UNION LIBRE", "U"));
            rep.saveAll(estados_civiles);
        }
    }
}
