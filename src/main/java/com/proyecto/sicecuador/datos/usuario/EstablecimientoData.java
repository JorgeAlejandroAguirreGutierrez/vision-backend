package com.proyecto.sicecuador.datos.usuario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.configuracion.Regimen;
import com.proyecto.sicecuador.modelos.usuario.*;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.repositorios.usuario.IEstablecimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
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
            List<TelefonoEstablecimiento> telefonosEstablecimientos = new ArrayList<>();
            telefonosEstablecimientos.add(new TelefonoEstablecimiento("TEL011907000001", "032964123", new Establecimiento(1)));
            List<CelularEstablecimiento> celularesEstablecimientos = new ArrayList<>();
            celularesEstablecimientos.add(new CelularEstablecimiento("CEL011907000001", "0999778877", new Establecimiento(1)));
            List<CorreoEstablecimiento> correosEstablecimientos = new ArrayList<>();
            correosEstablecimientos.add(new CorreoEstablecimiento("COR011907000001", "CORREO@HOTMAIL.COM", new Establecimiento(1)));
            establecimientos.add(new Establecimiento("EST202301000001", "001", "ESTABLECIMIENTO 1", "CALLE 10 CARRERA 15 #27", "1", "1", Constantes.activo, new Regimen(1), new Ubicacion(1), new Empresa(1), telefonosEstablecimientos, celularesEstablecimientos, correosEstablecimientos));
            establecimientos.add(new Establecimiento("EST202301000002", "002", "ESTABLECIMIENTO 2", "CALLE 5 CARRERA 60 #50", "1", "1", Constantes.activo, new Regimen(2), new Ubicacion(2), new Empresa(1), Collections.emptyList(), Collections.emptyList(), Collections.emptyList()));
            establecimientos.add(new Establecimiento("EST202301000003", "003", "ESTABLECIMIENTO 3", "CALLE 8 Y LARREA #27", "1", "1", Constantes.activo, new Regimen(3), new Ubicacion(3), new Empresa(1), Collections.emptyList(), Collections.emptyList(), Collections.emptyList()));
            rep.saveAll(establecimientos);
        }
    }
}
