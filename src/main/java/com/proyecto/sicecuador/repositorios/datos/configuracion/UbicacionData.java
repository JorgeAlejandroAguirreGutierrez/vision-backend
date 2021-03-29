package com.proyecto.sicecuador.repositorios.datos.configuracion;

import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.repositorios.interf.configuracion.IUbicacionRepository;
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
@Order(11)
@Profile({"dev","prod"})
public class UbicacionData implements ApplicationRunner {
    @Autowired
    private IUbicacionRepository rep;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Ubicacion> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Ubicacion> ubicaciones = new ArrayList<>();
            ubicaciones.add(new Ubicacion("U1", "1", "AZUAY", "CUENCA", "BELLAVISTA"));
            ubicaciones.add(new Ubicacion("U2", "2", "AZUAY", "CUENCA", "CAÑARIBAMBA"));
            ubicaciones.add(new Ubicacion("U3", "3", "CHIMBORAZO", "ALAUSI", "ALAUSI"));
            ubicaciones.add(new Ubicacion("U4", "4", "CHIMBORAZO", "RIOBAMBA", "VELASCO"));
            ubicaciones.add(new Ubicacion("U5", "5", "CHIMBORAZO", "RIOBAMBA", "RIOBAMBA"));
            rep.saveAll(ubicaciones);
        }
    }
}
