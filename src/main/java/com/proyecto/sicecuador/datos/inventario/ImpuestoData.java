package com.proyecto.sicecuador.datos.inventario;

import com.proyecto.sicecuador.Constantes;
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
            impuestos.add(new Impuesto("IMP012001000001", "0", "IVA", "0 %", 0, Constantes.activo));
            impuestos.add(new Impuesto("IMP012001000002", "2", "IVA", "12 %", 12, Constantes.activo));
            impuestos.add(new Impuesto("IMP012001000003", "3", "IVA", "14 %", 14, Constantes.activo));
            impuestos.add(new Impuesto("IMP012001000004", "8", "IVA diferenciado", "8 %", 8, Constantes.activo));
            rep.saveAll(impuestos);
        }
    }
}
