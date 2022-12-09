package com.proyecto.sicecuador.datos.usuario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.usuario.Establecimiento;
import com.proyecto.sicecuador.modelos.usuario.Estacion;
import com.proyecto.sicecuador.repositorios.usuario.IEstacionRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(19)
@Profile({"dev","prod"})
public class EstacionData implements ApplicationRunner {
    @Autowired
    private IEstacionRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Estacion> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Estacion> estaciones = new ArrayList<>();
            estaciones.add(new Estacion("EST001", "001", "CAJA1", Constantes.activo, new Establecimiento(1)));
            estaciones.add(new Estacion("EST002", "002", "CAJA2", Constantes.activo, new Establecimiento(1)));
            estaciones.add(new Estacion("EST003", "003", "CAJA3", Constantes.activo, new Establecimiento(2)));
            estaciones.add(new Estacion("EST004", "004", "PC4", Constantes.activo, new Establecimiento(1)));
            rep.saveAll(estaciones);
        }
    }
}
