package com.proyecto.vision.datos.configuracion;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.configuracion.TipoIdentificacion;
import com.proyecto.vision.repositorios.configuracion.ITipoIdentificacionRepository;
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
            tiposidentificaciones.add(new TipoIdentificacion("TID011912001", "04", "RUC", "R", Constantes.estadoActivo));
            tiposidentificaciones.add(new TipoIdentificacion("TID011912002", "05", "CÉDULA", "C",Constantes.estadoActivo));
            tiposidentificaciones.add(new TipoIdentificacion("TID011912003", "06", "PASAPORTE", "P",Constantes.estadoActivo));
            tiposidentificaciones.add(new TipoIdentificacion("TID011912004", "07", "CONSUMIDOR FINAL", "CF",Constantes.estadoActivo));
            tiposidentificaciones.add(new TipoIdentificacion("TID011912005", "08", "IDENTIFICACIÓN DEL EXTERIOR", "E",Constantes.estadoActivo));
            
            rep.saveAll(tiposidentificaciones);
        }
    }
}
