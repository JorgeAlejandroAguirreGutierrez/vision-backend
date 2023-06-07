package com.proyecto.sicecuador.datos.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.cliente.PlazoCredito;
import com.proyecto.sicecuador.modelos.usuario.Empresa;
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
            List<PlazoCredito> plazosCreditos = new ArrayList<>();
            plazosCreditos.add(new PlazoCredito("PCR202301000001", "CORTO PLAZO", "CPL",30, Constantes.activo, new Empresa(1)));
            plazosCreditos.add(new PlazoCredito("PCR202301000002", "MEDIANO PLAZO", "MPL",45, Constantes.activo, new Empresa(1)));
            plazosCreditos.add(new PlazoCredito("PCR202301000003", "LARGO PLAZO", "LPL",60, Constantes.activo, new Empresa(1)));
            plazosCreditos.add(new PlazoCredito("PCR202301000001", "CORTO PLAZO FLEXIBLE", "CPLF",30, Constantes.activo, new Empresa(2)));
            plazosCreditos.add(new PlazoCredito("PCR202301000002", "MEDIANO PLAZO FLEXIBLE", "MPLF",45, Constantes.activo, new Empresa(2)));
            plazosCreditos.add(new PlazoCredito("PCR202301000003", "LARGO PLAZO FLEXIBLE", "LPLF",60, Constantes.activo, new Empresa(2)));
            rep.saveAll(plazosCreditos);
        }
    }
}
