package com.proyecto.sicecuador.datos.cliente;

import com.proyecto.sicecuador.modelos.cliente.CalificacionCliente;
import com.proyecto.sicecuador.repositorios.cliente.ICalificacionClienteRepository;
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
@Order(2)
@Profile({"dev","prod"})
public class CalificacionClienteData implements ApplicationRunner {
    @Autowired
    private ICalificacionClienteRepository rep;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<CalificacionCliente> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<CalificacionCliente> calificacion_clientes = new ArrayList<>();
            calificacion_clientes.add(new CalificacionCliente("CCL202301000001", "EXELENTE", "EX", "ACTIVO"));
            calificacion_clientes.add(new CalificacionCliente("CCL202301000002", "MUY BUENO", "MB", "ACTIVO"));
            calificacion_clientes.add(new CalificacionCliente("CCL202301000003", "BUENO", "BU", "ACTIVO"));
            calificacion_clientes.add(new CalificacionCliente("CCL202301000004", "REGULAR", "RE", "ACTIVO"));
            calificacion_clientes.add(new CalificacionCliente("CCL202301000005", "MALO", "MA", "ACTIVO"));
            rep.saveAll(calificacion_clientes);
        }
    }
}
