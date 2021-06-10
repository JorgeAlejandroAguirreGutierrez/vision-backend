package com.proyecto.sicecuador.datos.usuario;

import com.proyecto.sicecuador.modelos.usuario.*;
import com.proyecto.sicecuador.repositorios.usuario.ISesionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Order(25)
@Profile({"dev","prod"})
public class SesionData implements ApplicationRunner {
    @Autowired
    private ISesionRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Sesion> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Sesion> sesiones = new ArrayList<>();
            sesiones.add(new Sesion("SES00001", Date.valueOf("2019-01-01"), Date.valueOf("2019-01-02"), "JORGE-PC", "10.10.10.10", false, new Usuario(1)));
            rep.saveAll(sesiones);
        }
    }
}
