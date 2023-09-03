package com.proyecto.vision.datos.configuracion;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.configuracion.EstadoCivil;
import com.proyecto.vision.repositorios.configuracion.IEstadoCivilRepository;
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
            estados_civiles.add(new EstadoCivil("ECV011907000001", "SOLTERO", "S", Constantes.estadoActivo));
            estados_civiles.add(new EstadoCivil("ECV011907000002", "CASADO", "C", Constantes.estadoActivo));
            estados_civiles.add(new EstadoCivil("ECV011908000003", "VIUDO", "V", Constantes.estadoActivo));
            estados_civiles.add(new EstadoCivil("ECV011908000004", "DIVORCIADO", "D", Constantes.estadoActivo));
            estados_civiles.add(new EstadoCivil("ECV011909000005", "UNION LIBRE", "U", Constantes.estadoActivo));
            rep.saveAll(estados_civiles);
        }
    }
}
