package com.proyecto.vision.datos.cliente;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.cliente.CalificacionCliente;
import com.proyecto.vision.modelos.usuario.Empresa;
import com.proyecto.vision.repositorios.cliente.ICalificacionClienteRepository;
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
@Order(3)
@Profile({"dev","prod"})
public class CalificacionClienteData implements ApplicationRunner {
    @Autowired
    private ICalificacionClienteRepository rep;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<CalificacionCliente> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<CalificacionCliente> calificacion_clientes = new ArrayList<>();
            calificacion_clientes.add(new CalificacionCliente("CCL012301000001", "EXELENTE", "EX", Constantes.estadoActivo, new Empresa(1)));
            calificacion_clientes.add(new CalificacionCliente("CCL012301000002", "MUY BUENO", "MB", Constantes.estadoActivo, new Empresa(1)));
            calificacion_clientes.add(new CalificacionCliente("CCL012301000003", "BUENO", "BU", Constantes.estadoActivo, new Empresa(1)));
            calificacion_clientes.add(new CalificacionCliente("CCL012301000004", "REGULAR", "RE", Constantes.estadoActivo, new Empresa(1)));
            calificacion_clientes.add(new CalificacionCliente("CCL012301000005", "MALO", "MA", Constantes.estadoActivo, new Empresa(1)));
            // EMPRESA 2
            calificacion_clientes.add(new CalificacionCliente("CCL022308000001", "EXELENTE", "EX", Constantes.estadoActivo, new Empresa(2)));
            calificacion_clientes.add(new CalificacionCliente("CCL022308000002", "MUY BUENO", "MB", Constantes.estadoActivo, new Empresa(2)));
            calificacion_clientes.add(new CalificacionCliente("CCL022308000003", "BUENO", "BU", Constantes.estadoActivo, new Empresa(2)));
            calificacion_clientes.add(new CalificacionCliente("CCL022308000004", "REGULAR", "RE", Constantes.estadoActivo, new Empresa(2)));
            calificacion_clientes.add(new CalificacionCliente("CCL022308000005", "MALO", "MA", Constantes.estadoActivo, new Empresa(2)));
            // EMPRESA 3
            calificacion_clientes.add(new CalificacionCliente("CCL032309000001", "EXELENTE", "EX", Constantes.estadoActivo, new Empresa(3)));
            calificacion_clientes.add(new CalificacionCliente("CCL032309000002", "MUY BUENO", "MB", Constantes.estadoActivo, new Empresa(3)));
            calificacion_clientes.add(new CalificacionCliente("CCL032309000003", "BUENO", "BU", Constantes.estadoActivo, new Empresa(3)));
            calificacion_clientes.add(new CalificacionCliente("CCL032309000004", "REGULAR", "RE", Constantes.estadoActivo, new Empresa(3)));
            calificacion_clientes.add(new CalificacionCliente("CCL032309000005", "MALO", "MA", Constantes.estadoActivo, new Empresa(3)));
            rep.saveAll(calificacion_clientes);
        }
    }
}
