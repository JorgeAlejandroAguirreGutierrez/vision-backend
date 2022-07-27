package com.proyecto.sicecuador.datos.proveedor;

import com.proyecto.sicecuador.modelos.proveedor.*;
import com.proyecto.sicecuador.repositorios.proveedor.IProveedorRepository;
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
@Order(40)
@Profile({"dev","prod"})
public class ProveedorData implements ApplicationRunner {
    @Autowired
    private IProveedorRepository rep;
        @Override
        public void run(ApplicationArguments args) throws Exception {
            Optional<Proveedor> ant=rep.findById((long) 1);
            if (!ant.isPresent()) {
                List<Proveedor> proveedores = new ArrayList<>();
                proveedores.add(new Proveedor("PRV110721000001", "C", "0101010101", "CORAL CENTRO", "NOMBRE COMERCIAL CORAL", "ACTIVO", false, "CALLE SUCRE Y VEGA MUÑOZ", "072865654", "coralcentro@coral.com.ec"));
                proveedores.add(new Proveedor("PRV110721000002", "C", "0101010102", "SUPERMAXI", "NOMBRE COMERCIAL SUPERMAXI", "ACTIVO", false, "AV HURTADO DE MENDOZA", "072995654", "supermaxi@supermaxi.com.ec"));
                rep.saveAll(proveedores);
            }
    }
}
