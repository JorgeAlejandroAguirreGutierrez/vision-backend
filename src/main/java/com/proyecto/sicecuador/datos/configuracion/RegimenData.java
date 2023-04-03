package com.proyecto.sicecuador.datos.configuracion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.configuracion.Regimen;
import com.proyecto.sicecuador.repositorios.configuracion.IRegimenRepository;
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
@Order(10)
@Profile({"dev","prod"})
public class RegimenData implements ApplicationRunner {
    @Autowired
    private IRegimenRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Regimen> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Regimen> regimenes = new ArrayList<>();
            regimenes.add(new Regimen("REG202301000001", "CONTRIBUYENTE NEGOCIO POPULAR RÉGIMEN RIMPE", "RIMPE", Constantes.si,"ACTIVO"));
            regimenes.add(new Regimen("REG202301000002", "CONTRIBUYENTE RÉGIMEN RIMPE EMPRENDEDOR", "EMPRENDEDOR", Constantes.si , "ACTIVO"));
            regimenes.add(new Regimen("REG202301000003", "CONTRIBUYENTE RÉGIMEN GENERAL", "GENERAL", Constantes.no , "ACTIVO"));
            rep.saveAll(regimenes);
        }
    }
}
