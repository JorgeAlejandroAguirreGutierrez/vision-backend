package com.proyecto.sicecuador.datos.usuario;

import com.proyecto.sicecuador.modelos.configuracion.Empresa;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.modelos.usuario.Establecimiento;
import com.proyecto.sicecuador.repositorios.usuario.IEstablecimientoRepository;
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
@Order(18)
@Profile({"dev","prod"})
public class EstablecimientoData implements ApplicationRunner {
    @Autowired
    private IEstablecimientoRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Establecimiento> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Establecimiento> establecimientos = new ArrayList<>();
            establecimientos.add(new Establecimiento("EST001", "001", "CALLE 10 CARRERA 15 #27", "ACTIVO", new Empresa(1), new Ubicacion(1)));
            establecimientos.add(new Establecimiento("EST002", "002", "CALLE 5 CARRERA 60 #50", "ACTIVO", new Empresa(1), new Ubicacion(2)));
            establecimientos.add(new Establecimiento("EST003", "003", "CALLE 8 Y LARREA #27", "ACTIVO", new Empresa(1), new Ubicacion(3)));
            establecimientos.add(new Establecimiento("EST004", "001", "CALLE 19 Y OLMEDO #50", "ACTIVO", new Empresa(2), new Ubicacion(4)));
            rep.saveAll(establecimientos);
        }
    }
}
