package com.proyecto.sicecuador.datos.inventario;

import com.proyecto.sicecuador.modelos.inventario.Impuesto;
import com.proyecto.sicecuador.repositorios.inventario.IImpuestoRepository;
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
@Order(38)
@Profile({"dev","prod"})
public class ImpuestoData implements ApplicationRunner {
    @Autowired
    private IImpuestoRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Impuesto> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Impuesto> impuestos = new ArrayList<>();
            impuestos.add(new Impuesto("IVA1", "2", "IVA", "0 %", 0, "ACTIVO"));
            impuestos.add(new Impuesto("IVA2", "2", "IVA", "12 %", 12, "ACTIVO"));
            impuestos.add(new Impuesto("IVA3", "2", "IVA", "14 %", 14, "ACTIVO"));
            rep.saveAll(impuestos);
        }
    }
}
