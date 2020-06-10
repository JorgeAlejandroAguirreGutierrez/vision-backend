package com.proyecto.sicecuador.repositorios.datos.recaudacion;

import com.proyecto.sicecuador.modelos.recaudacion.Amortizacion;
import com.proyecto.sicecuador.repositorios.interf.recaudacion.IAmortizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Component
@Order(25)
public class AmortizacionData implements ApplicationRunner {
    @Autowired
    private IAmortizacionRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Amortizacion> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Amortizacion> amortizaciones = new ArrayList<>();
            amortizaciones.add(new Amortizacion("AMO1", "QUINCENAL", 15));
            amortizaciones.add(new Amortizacion("AMO2", "MENSUAL", 30));
            amortizaciones.add(new Amortizacion("AMO3", "TRIMESTRAL", 90));
            rep.saveAll(amortizaciones);
        }
    }
}
