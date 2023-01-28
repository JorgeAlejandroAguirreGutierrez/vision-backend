package com.proyecto.sicecuador.datos.cliente;

import com.proyecto.sicecuador.modelos.cliente.OrigenIngreso;
import com.proyecto.sicecuador.repositorios.cliente.IOrigenIngresoRepository;
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
public class OrigenIngresoData implements ApplicationRunner {
    @Autowired
    private IOrigenIngresoRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<OrigenIngreso> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<OrigenIngreso> origenes_ingresos = new ArrayList<>();
            origenes_ingresos.add(new OrigenIngreso("OIN202301000001", "SALARIO", "SLR", "ACTIVO"));
            origenes_ingresos.add(new OrigenIngreso("OIN202301000002", "VENTAS", "VNT", "ACTIVO"));
            origenes_ingresos.add(new OrigenIngreso("OIN202301000003", "INDEPENDIENTE", "INDP", "ACTIVO"));
            rep.saveAll(origenes_ingresos);
        }
    }
}
