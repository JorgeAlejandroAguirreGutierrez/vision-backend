package com.proyecto.sicecuador.datos.cliente;

import com.proyecto.sicecuador.modelos.cliente.PlazoCredito;
import com.proyecto.sicecuador.repositorios.cliente.IPlazoCreditoRepository;
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
@Order(17)
@Profile({"dev","prod"})
public class PlazoCreditoData implements ApplicationRunner {
    @Autowired
    private IPlazoCreditoRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<PlazoCredito> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<PlazoCredito> plazos_creditos = new ArrayList<>();
            plazos_creditos.add(new PlazoCredito("PCR011908000001", "CORTO PLAZO", 30, "ACTIVO"));
            plazos_creditos.add(new PlazoCredito("PCR011908000002", "MEDIANO PLAZO", 45, "ACTIVO"));
            plazos_creditos.add(new PlazoCredito("PCR011908000003", "LARGO PLAZO", 60, "ACTIVO"));
            rep.saveAll(plazos_creditos);
        }
    }
}
