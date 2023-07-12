package com.proyecto.vision.datos.configuracion;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.configuracion.Genero;
import com.proyecto.vision.repositorios.configuracion.IGeneroRepository;
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
@Order(8)
@Profile({"dev","prod"})
public class GeneroData implements ApplicationRunner {
    @Autowired
    private IGeneroRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Genero> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Genero> generos = new ArrayList<>();
            generos.add(new Genero("GEN012306000001", "MASCULINO", "M", Constantes.estadoActivo));
            generos.add(new Genero("GEN012306000002", "FEMENINO", "F", Constantes.estadoActivo));
            rep.saveAll(generos);
        }
    }
}
