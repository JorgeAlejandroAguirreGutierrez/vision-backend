package com.proyecto.sicecuador.datos.recaudacion;

import com.proyecto.sicecuador.repositorios.recaudacion.ITarjetaDebitoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(31)
@Profile({"dev","prod"})
public class TarjetaDebitoData implements ApplicationRunner {
    @Autowired
    private ITarjetaDebitoRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*Optional<TarjetaDebito> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<TarjetaDebito> tarjetas = new ArrayList<>();
            tarjetas.add(new TarjetaDebito("RTD000001", "0502685966", "JORGE AGUIRRE", "12314566", 20.00, new Tarjeta(1), new OperadorTarjeta(1)));
            tarjetas.add(new TarjetaDebito("RTD000002", "0502685966", "JORGE AGUIRRE", "12314566", 30.00, new Tarjeta(2), new OperadorTarjeta(1)));
            tarjetas.add(new TarjetaDebito("RTD000003", "0601234567", "MARIO DELGADO", "12314566", 40.00, new Tarjeta(1), new OperadorTarjeta(2)));
            tarjetas.add(new TarjetaDebito("RTD000004", "0601234567", "MARIO DELGADO", "12314566", 50.00, new Tarjeta(2), new OperadorTarjeta(2)));
            rep.saveAll(tarjetas);
        }*/
    }
}
