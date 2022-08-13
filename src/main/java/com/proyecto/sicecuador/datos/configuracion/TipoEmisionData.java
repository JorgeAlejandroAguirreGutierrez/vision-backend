package com.proyecto.sicecuador.datos.configuracion;

import com.proyecto.sicecuador.modelos.configuracion.TipoEmision;
import com.proyecto.sicecuador.repositorios.configuracion.ITipoEmisionRepository;
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
@Order(15)
@Profile({"dev","prod"})
public class TipoEmisionData implements ApplicationRunner {
    @Autowired
    private ITipoEmisionRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<TipoEmision> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<TipoEmision> tiposemisiones = new ArrayList<>();
            tiposemisiones.add(new TipoEmision("TEM011912001", "1", "Normal", "activo"));
            rep.saveAll(tiposemisiones);
        }
    }
}
