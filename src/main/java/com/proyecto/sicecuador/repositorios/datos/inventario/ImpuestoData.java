package com.proyecto.sicecuador.repositorios.datos.inventario;

import com.proyecto.sicecuador.modelos.cliente.CategoriaCliente;
import com.proyecto.sicecuador.modelos.inventario.Impuesto;
import com.proyecto.sicecuador.repositorios.interf.inventario.IImpuestoRepository;
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
            impuestos.add(new Impuesto("IVA1", "IVA_12%", 12));
            impuestos.add(new Impuesto("IVA2", "IVA_0%", 0));
            rep.saveAll(impuestos);
        }
    }
}
