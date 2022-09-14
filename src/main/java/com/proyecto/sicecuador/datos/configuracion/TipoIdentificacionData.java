package com.proyecto.sicecuador.datos.configuracion;

import com.proyecto.sicecuador.modelos.configuracion.TipoIdentificacion;
import com.proyecto.sicecuador.repositorios.configuracion.ITipoIdentificacionRepository;
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
@Order(1)
@Profile({"dev","prod"})
public class TipoIdentificacionData implements ApplicationRunner {
    @Autowired
    private ITipoIdentificacionRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<TipoIdentificacion> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<TipoIdentificacion> tiposidentificaciones = new ArrayList<>();
            tiposidentificaciones.add(new TipoIdentificacion("TID011912001", "04", "RUC", "activo"));
            tiposidentificaciones.add(new TipoIdentificacion("TID011912002", "05", "CÉDULA", "activo"));
            tiposidentificaciones.add(new TipoIdentificacion("TID011912003", "06", "PASAPORTE", "activo"));
            tiposidentificaciones.add(new TipoIdentificacion("TID011912004", "07", "CONSUMIDOR FINAL", "activo"));
            tiposidentificaciones.add(new TipoIdentificacion("TID011912005", "08", "IDENTIFICACIÓN DEL EXTERIOR", "activo"));
            
            rep.saveAll(tiposidentificaciones);
        }
    }
}
