package com.proyecto.sicecuador.repositorios.datos.recaudacion;

import com.proyecto.sicecuador.modelos.recaudacion.*;
import com.proyecto.sicecuador.repositorios.interf.recaudacion.ITarjetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Order(30)
public class TarjetaData implements ApplicationRunner {
    @Autowired
    private ITarjetaRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*Optional<Tarjeta> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Tarjeta> tarjetas = new ArrayList<>();
            tarjetas.add(new Tarjeta("TAR000001", "CREDITO", "VISA", new Banco(1)));
            tarjetas.add(new Tarjeta("TAR000002", "DEBITO", "VISA", new Banco(1)));
            tarjetas.add(new Tarjeta("TAR000003", "CREDITO", "MASTERCARD", new Banco(2)));
            tarjetas.add(new Tarjeta("TAR000004", "DEBITO", "MASTERCARD", new Banco(2)));
            tarjetas.add(new Tarjeta("TAR000005", "CREDITO", "DINERS CLUB", new Banco(3)));
            tarjetas.add(new Tarjeta("TAR000006", "DEBITO", "VISA", new Banco(4)));
            rep.saveAll(tarjetas);
        }*/
    }
}
