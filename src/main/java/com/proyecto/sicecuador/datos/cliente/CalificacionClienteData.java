package com.proyecto.sicecuador.datos.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.cliente.CalificacionCliente;
import com.proyecto.sicecuador.modelos.usuario.Empresa;
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
            calificacion_clientes.add(new CalificacionCliente("CCL012301000001", "EXELENTE", "EX", Constantes.activo, new Empresa(1)));
            calificacion_clientes.add(new CalificacionCliente("CCL012301000002", "MUY BUENO", "MB", Constantes.activo, new Empresa(1)));
            calificacion_clientes.add(new CalificacionCliente("CCL012301000003", "BUENO", "BU", Constantes.activo, new Empresa(1)));
            calificacion_clientes.add(new CalificacionCliente("CCL012301000004", "REGULAR", "RE", Constantes.activo, new Empresa(1)));
            calificacion_clientes.add(new CalificacionCliente("CCL012301000005", "MALO", "MA", Constantes.activo, new Empresa(1)));

            calificacion_clientes.add(new CalificacionCliente("CCL022301000001", "EXELENTE", "EX", Constantes.activo, new Empresa(2)));
            calificacion_clientes.add(new CalificacionCliente("CCL022301000002", "MUY BUENO", "MB", Constantes.activo, new Empresa(2)));
            calificacion_clientes.add(new CalificacionCliente("CCL022301000003", "BUENO", "BU", Constantes.activo, new Empresa(2)));
            calificacion_clientes.add(new CalificacionCliente("CCL022301000004", "REGULAR", "RE", Constantes.activo, new Empresa(2)));
            calificacion_clientes.add(new CalificacionCliente("CCL022301000005", "MALO", "MA", Constantes.activo, new Empresa(2)));

            rep.saveAll(calificacion_clientes);
        }
    }
}
