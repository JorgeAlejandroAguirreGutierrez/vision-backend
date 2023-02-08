package com.proyecto.sicecuador.datos.proveedor;

import com.proyecto.sicecuador.modelos.proveedor.CorreoProveedor;
import com.proyecto.sicecuador.modelos.proveedor.Proveedor;
import com.proyecto.sicecuador.repositorios.proveedor.ICorreoProveedorRepository;
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
public class CorreoProveedorData implements ApplicationRunner {
    @Autowired
    private ICorreoProveedorRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<CorreoProveedor> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<CorreoProveedor> correos = new ArrayList<>();
            correos.add(new CorreoProveedor("COA011908000001", "AUXILIAR1@GMAIL.COM", new Proveedor(1)));
            correos.add(new CorreoProveedor("COA011908000002", "AUXILIAR11@GMAIL.COM", new Proveedor(1)));
            correos.add(new CorreoProveedor("COA011909000003", "AUXILIAR2@GMAIL.COM", new Proveedor(2)));
            correos.add(new CorreoProveedor("COA011909000004", "AUXILIAR3@GMAIL.COM", new Proveedor(2)));
            rep.saveAll(correos);
        }
    }
}
