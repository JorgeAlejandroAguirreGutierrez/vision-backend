package com.proyecto.vision.datos.cliente;

import com.proyecto.vision.modelos.cliente.PlazoCredito;
import com.proyecto.vision.modelos.usuario.Empresa;
import com.proyecto.vision.repositorios.cliente.IPlazoCreditoRepository;
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
            plazos_creditos.add(new PlazoCredito("PCR012301000001", "CORTO PLAZO", "CPL",30, "ACTIVO", new Empresa(1)));
            plazos_creditos.add(new PlazoCredito("PCR012301000002", "MEDIANO PLAZO", "MPL",45, "ACTIVO", new Empresa(1)));
            plazos_creditos.add(new PlazoCredito("PCR012301000003", "LARGO PLAZO", "LPL",60, "ACTIVO", new Empresa(1)));
            //EMPRESA 2
            plazos_creditos.add(new PlazoCredito("PCR022308000001", "SIN CREDITO", "SCR",0, "ACTIVO", new Empresa(2)));
            //EMPRESA 3
            plazos_creditos.add(new PlazoCredito("PCR032309000001", "SIN CREDITO", "SCR",0, "ACTIVO", new Empresa(3)));

            rep.saveAll(plazos_creditos);
        }
    }
}
