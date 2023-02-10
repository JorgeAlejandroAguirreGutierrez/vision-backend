package com.proyecto.sicecuador.datos.compra;

import com.proyecto.sicecuador.modelos.compra.Proveedor;
import com.proyecto.sicecuador.modelos.compra.TelefonoProveedor;
import com.proyecto.sicecuador.repositorios.compra.ITelefonoProveedorRepository;
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
public class TelefonoProveedorData implements ApplicationRunner {
    @Autowired
    private ITelefonoProveedorRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<TelefonoProveedor> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<TelefonoProveedor> telefonos = new ArrayList<>();
            telefonos.add(new TelefonoProveedor("TEA011908000001", "032964121", new Proveedor(1)));
            telefonos.add(new TelefonoProveedor("TEA011908000002", "032964122", new Proveedor(1)));
            telefonos.add(new TelefonoProveedor("TEA011909000003", "032964123", new Proveedor(2)));

            rep.saveAll(telefonos);
        }
    }
}
