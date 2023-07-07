package com.proyecto.vision.datos.recaudacion;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.recaudacion.FranquiciaTarjeta;
import com.proyecto.vision.repositorios.recaudacion.IFranquiciaTarjetaRepository;
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
public class FranquiciaTarjetaData implements ApplicationRunner {
    @Autowired
    private IFranquiciaTarjetaRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<FranquiciaTarjeta> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<FranquiciaTarjeta> franquiciaTarjetas = new ArrayList<>();
            franquiciaTarjetas.add(new FranquiciaTarjeta("FT1", "", "VISA", "V", Constantes.activo));
            franquiciaTarjetas.add(new FranquiciaTarjeta("FT1", "", "DINERS CLUB", "DC", Constantes.activo));
            franquiciaTarjetas.add(new FranquiciaTarjeta("FT1", "", "MASTERCARD", "M", Constantes.activo));
            rep.saveAll(franquiciaTarjetas);
        }
    }
}
