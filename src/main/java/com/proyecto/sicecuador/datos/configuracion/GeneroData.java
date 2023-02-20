package com.proyecto.sicecuador.datos.configuracion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.configuracion.Genero;
import com.proyecto.sicecuador.repositorios.configuracion.IGeneroRepository;
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
            generos.add(new Genero("GEN011907000001", "MASCULINO", "M", Constantes.activo));
            generos.add(new Genero("GEN011908000002", "FEMENINO", "F", Constantes.activo));
            rep.saveAll(generos);
        }
    }
}
