package com.proyecto.vision.datos.recaudacion;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.recaudacion.OperadorTarjeta;
import com.proyecto.vision.repositorios.recaudacion.IOperadorTarjetaRepository;
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
@Order(29)
@Profile({"dev","prod"})
public class OperadorTarjetaData implements ApplicationRunner {
    @Autowired
    private IOperadorTarjetaRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<OperadorTarjeta> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<OperadorTarjeta> operadoresTarjetas = new ArrayList<>();
            operadoresTarjetas.add(new OperadorTarjeta("OTA000001", "CREDITO", "DATAFAST", "DF", Constantes.estadoActivo));
            operadoresTarjetas.add(new OperadorTarjeta("OTA000002", "DEBITO", "DATAFAST", "DF", Constantes.estadoActivo));
            operadoresTarjetas.add(new OperadorTarjeta("OTA000003", "CREDITO", "MEGADATOS", "MD", Constantes.estadoActivo));
            operadoresTarjetas.add(new OperadorTarjeta("OTA000004", "DEBITO", "MEGADATOS", "MD", Constantes.estadoActivo));
            rep.saveAll(operadoresTarjetas);
        }
    }
}
