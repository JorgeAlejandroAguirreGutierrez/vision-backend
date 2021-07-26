package com.proyecto.sicecuador.datos.cliente;

import com.proyecto.sicecuador.modelos.cliente.Auxiliar;
import com.proyecto.sicecuador.modelos.cliente.CalificacionCliente;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.cliente.Direccion;
import com.proyecto.sicecuador.repositorios.cliente.IAuxiliarRepository;
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
            calificacion_clientes.add(new CalificacionCliente("CCL011909000001", "EXELENTE", "E", "ACTIVO"));
            calificacion_clientes.add(new CalificacionCliente("CCL011909000002", "MUY BUENO", "M", "ACTIVO"));
            calificacion_clientes.add(new CalificacionCliente("CCL011909000003", "BUENO", "B", "ACTIVO"));
            calificacion_clientes.add(new CalificacionCliente("CCL011909000004", "REGULAR", "R", "ACTIVO"));
            calificacion_clientes.add(new CalificacionCliente("CCL011909000005", "MALO", "MA", "ACTIVO"));
            rep.saveAll(calificacion_clientes);
        }
    }
}
