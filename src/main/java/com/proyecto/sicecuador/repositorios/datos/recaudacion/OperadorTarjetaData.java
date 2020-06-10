package com.proyecto.sicecuador.repositorios.datos.recaudacion;

import com.proyecto.sicecuador.modelos.recaudacion.OperadorTarjeta;
import com.proyecto.sicecuador.repositorios.interf.recaudacion.IOperadorTarjetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Order(29)
public class OperadorTarjetaData implements ApplicationRunner {
    @Autowired
    private IOperadorTarjetaRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<OperadorTarjeta> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<OperadorTarjeta> operadores_tarjetas = new ArrayList<>();
            operadores_tarjetas.add(new OperadorTarjeta("OTA000001", "CREDITO", "DATAFAST", "DF"));
            operadores_tarjetas.add(new OperadorTarjeta("OTA000002", "DEBITO", "DATAFAST", "DF"));
            operadores_tarjetas.add(new OperadorTarjeta("OTA000003", "CREDITO", "MEGADATOS", "MD"));
            operadores_tarjetas.add(new OperadorTarjeta("OTA000004", "DEBITO", "MEGADATOS", "MD"));
            rep.saveAll(operadores_tarjetas);
        }
    }
}
