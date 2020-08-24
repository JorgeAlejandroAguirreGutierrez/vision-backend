package com.proyecto.sicecuador.repositorios.datos.recaudacion;

import com.proyecto.sicecuador.modelos.recaudacion.RangoCrediticio;
import com.proyecto.sicecuador.repositorios.interf.recaudacion.IRangoCrediticioRepository;
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
@Order(25)
@Profile({"dev","prod"})
public class RangoCrediticioData implements ApplicationRunner {
    @Autowired
    private IRangoCrediticioRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<RangoCrediticio> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<RangoCrediticio> rangos_crediticios = new ArrayList<>();
            rangos_crediticios.add(new RangoCrediticio("RCR1", 0, 500, 48, 12));
            rangos_crediticios.add(new RangoCrediticio("RCR1", 500.01,999999999, 36, 9));
            rep.saveAll(rangos_crediticios);
        }
    }
}
