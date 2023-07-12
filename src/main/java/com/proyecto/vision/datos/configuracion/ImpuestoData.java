package com.proyecto.vision.datos.configuracion;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.configuracion.Impuesto;
import com.proyecto.vision.repositorios.configuracion.IImpuestoRepository;
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
            impuestos.add(new Impuesto("IMP012001000001", "0", "IVA", "0 %", 0, Constantes.estadoActivo));
            impuestos.add(new Impuesto("IMP012001000002", "2", "IVA", "12 %", 12, Constantes.estadoActivo));
            impuestos.add(new Impuesto("IMP012001000003", "3", "IVA", "14 %", 14, Constantes.estadoActivo));
            impuestos.add(new Impuesto("IMP012001000004", "8", "IVA diferenciado", "8 %", 8, Constantes.estadoActivo));
            rep.saveAll(impuestos);
        }
    }
}
