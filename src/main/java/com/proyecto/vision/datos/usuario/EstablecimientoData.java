package com.proyecto.vision.datos.usuario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.configuracion.Regimen;
import com.proyecto.vision.modelos.usuario.*;
import com.proyecto.vision.modelos.configuracion.Ubicacion;
import com.proyecto.vision.repositorios.usuario.IEstablecimientoRepository;
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
            //ESTABLECIMIENTOS EMPRESA 1
            List<Establecimiento> establecimientos = new ArrayList<>();
            List<TelefonoEstablecimiento> telefonosEstablecimientos = new ArrayList<>();
            telefonosEstablecimientos.add(new TelefonoEstablecimiento("TEE202305000001", "03-296-4123", new Establecimiento(1)));
            List<CelularEstablecimiento> celularesEstablecimientos = new ArrayList<>();
            celularesEstablecimientos.add(new CelularEstablecimiento("CEE202305000001", "09-9977-8877", new Establecimiento(1)));
            List<CorreoEstablecimiento> correosEstablecimientos = new ArrayList<>();
            correosEstablecimientos.add(new CorreoEstablecimiento("COE202305000001", "correo@hotmail.com", new Establecimiento(1)));

            establecimientos.add(new Establecimiento("EST012301000001", "001", "ESTABLECIMIENTO 1", "CALLE 10 CARRERA 15 #27", -1.6719601146175827, -78.65041698970857, Constantes.estadoActivo, new Regimen(1), new Ubicacion(1), new Empresa(1), telefonosEstablecimientos, celularesEstablecimientos, correosEstablecimientos));
            establecimientos.add(new Establecimiento("EST012301000002", "002", "ESTABLECIMIENTO 2", "CALLE 5 CARRERA 60 #50", -1.6719601146175827, -78.65041698970857, Constantes.estadoActivo, new Regimen(2), new Ubicacion(2), new Empresa(1), Collections.emptyList(), Collections.emptyList(), Collections.emptyList()));
            establecimientos.add(new Establecimiento("EST012301000003", "003", "ESTABLECIMIENTO 3", "CALLE 8 Y LARREA #27", -1.6719601146175827, -78.65041698970857, Constantes.estadoActivo, new Regimen(3), new Ubicacion(3), new Empresa(1), Collections.emptyList(), Collections.emptyList(), Collections.emptyList()));

            //ESTABLECIMIENTOS EMPRESA 2
            List<TelefonoEstablecimiento> telefonosEstablecimientos2 = new ArrayList<>();
            telefonosEstablecimientos2.add(new TelefonoEstablecimiento("TEE202308000001", "03-296-4123", new Establecimiento(4)));
            List<CelularEstablecimiento> celularesEstablecimientos2 = new ArrayList<>();
            celularesEstablecimientos2.add(new CelularEstablecimiento("CEE202308000001", "0994657138", new Establecimiento(4)));
            List<CorreoEstablecimiento> correosEstablecimientos2 = new ArrayList<>();
            correosEstablecimientos2.add(new CorreoEstablecimiento("COE202308000001", "vallauca@gmail.com", new Establecimiento(4)));

            establecimientos.add(new Establecimiento("EST022308000001", "001", "VILLA MARIA", "LOJA 21-27 Y OROZCO", -1.6719601146175827, -78.65041698970857, Constantes.estadoActivo, new Regimen(1), new Ubicacion(248), new Empresa(2), Collections.emptyList(), celularesEstablecimientos2, correosEstablecimientos2));
            establecimientos.add(new Establecimiento("EST022308000002", "004", "VILLA MARIA 2", "OROZCO SN Y LOJA", -1.6719601146175827, -78.65041698970857, Constantes.estadoActivo, new Regimen(1), new Ubicacion(248), new Empresa(2), Collections.emptyList(), celularesEstablecimientos2, correosEstablecimientos2));

            rep.saveAll(establecimientos);
        }
    }
}
