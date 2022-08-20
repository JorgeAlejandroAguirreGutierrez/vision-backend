package com.proyecto.sicecuador.datos.cliente;

import com.proyecto.sicecuador.modelos.cliente.Genero;
import com.proyecto.sicecuador.repositorios.cliente.IGeneroRepository;
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
            generos.add(new Genero("GEN011907000001", "MASCULINO", "M"));
            generos.add(new Genero("GEN011908000002", "FEMENINO", "F"));
            rep.saveAll(generos);
        }
    }
}
