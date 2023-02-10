package com.proyecto.sicecuador.datos.compra;

import com.proyecto.sicecuador.modelos.compra.CelularProveedor;
import com.proyecto.sicecuador.modelos.compra.Proveedor;
import com.proyecto.sicecuador.repositorios.compra.ICelularProveedorRepository;
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
@Order(41)
@Profile({"dev","prod"})
public class CelularProveedorData implements ApplicationRunner {
    @Autowired
    private ICelularProveedorRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<CelularProveedor> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<CelularProveedor> celulares = new ArrayList<>();
            celulares.add(new CelularProveedor("CPR011908000001", "0987654322", new Proveedor(1)));
            celulares.add(new CelularProveedor("CPR011908000002", "0981234563", new Proveedor(1)));
            celulares.add(new CelularProveedor("CPR011909000003", "0965431235", new Proveedor(2)));
            celulares.add(new CelularProveedor("CPR011909000004", "0965431236", new Proveedor(2)));
            rep.saveAll(celulares);
        }
    }
}
