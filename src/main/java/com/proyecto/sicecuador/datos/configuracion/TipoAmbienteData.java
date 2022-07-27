package com.proyecto.sicecuador.datos.configuracion;

import com.proyecto.sicecuador.modelos.configuracion.TipoAmbiente;
import com.proyecto.sicecuador.repositorios.configuracion.ITipoAmbienteRepository;
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
public class TipoAmbienteData implements ApplicationRunner {
    @Autowired
    private ITipoAmbienteRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<TipoAmbiente> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<TipoAmbiente> tiposambientes = new ArrayList<>();
            tiposambientes.add(new TipoAmbiente("TAM011912001", "1", "Pruebas", "activo"));
            tiposambientes.add(new TipoAmbiente("TAM011912002", "2", "Producción", "activo"));
            rep.saveAll(tiposambientes);
        }
    }
}
