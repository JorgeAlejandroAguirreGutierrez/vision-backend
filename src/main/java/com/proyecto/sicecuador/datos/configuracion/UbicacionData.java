package com.proyecto.sicecuador.datos.configuracion;

import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.repositorios.configuracion.IUbicacionRepository;
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
            ubicaciones.add(new Ubicacion("U1", "010150", "AZUAY", "CUENCA", "CUENCA"));
            ubicaciones.add(new Ubicacion("U2", "010101", "AZUAY", "CUENCA", "BELLAVISTA"));
            ubicaciones.add(new Ubicacion("U3", "010102", "AZUAY", "CUENCA", "CAÑARIBAMBA"));
            ubicaciones.add(new Ubicacion("U4", "010103", "AZUAY", "CUENCA", "EL BATAN"));
            ubicaciones.add(new Ubicacion("U5", "010104", "AZUAY", "CUENCA", "EL SAGRARIO"));
            ubicaciones.add(new Ubicacion("U5", "010105", "AZUAY", "CUENCA", "EL VECINO"));
            rep.saveAll(ubicaciones);
        }
    }
}
