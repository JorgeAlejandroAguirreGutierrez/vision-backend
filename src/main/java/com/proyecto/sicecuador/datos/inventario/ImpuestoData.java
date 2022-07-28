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
            impuestos.add(new Impuesto("IVA1","2", "IVA", "IVA_0%", "0", "0 %", 0, "ACTIVO"));
            impuestos.add(new Impuesto("IVA2","2", "IVA","IVA_12%", "2", "12 %", 12, "ACTIVO"));
            impuestos.add(new Impuesto("IVA3","2", "IVA","IVA_14%", "3", "14 %", 14, "ACTIVO"));
            impuestos.add(new Impuesto("IVA4","2", "IVA","IVA_0%", "6", "No objeto de impuesto", 0, "ACTIVO"));
            impuestos.add(new Impuesto("IVA5","2", "IVA","IVA_0%", "7", "Excento de IVA", 0, "ACTIVO"));
            impuestos.add(new Impuesto("IVA6","2", "IVA","IVA_0%", "8", "IVA Diferenciado", 0, "ACTIVO"));
            impuestos.add(new Impuesto("ICE1","3", "ICE","ICE_0,16%", "3011", "ICE Cigarrillos rubios", 0.16, "ACTIVO"));
            impuestos.add(new Impuesto("IRBPNR1","5", "IRBPNR1","IRBPNR_0,16%", "COD1", "IRBPNR 0,16", 0.16, "ACTIVO"));

            rep.saveAll(impuestos);
        }
    }
}
