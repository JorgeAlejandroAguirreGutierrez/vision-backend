package com.proyecto.sicecuador.repositorios.datos.cliente;

import com.proyecto.sicecuador.modelos.cliente.*;
import com.proyecto.sicecuador.repositorios.interf.cliente.IEstadoCivilRepository;
import com.proyecto.sicecuador.repositorios.interf.cliente.IFinanciamientoRepository;
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
@Order(14)
@Profile({"dev","prod"})
public class FinanciamientoData implements ApplicationRunner {
    @Autowired
    private IFinanciamientoRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*Optional<Financiamiento> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Financiamiento> financiamientos = new ArrayList<>();
            financiamientos.add(new Financiamiento("FIN011907000001", 100.20, new TipoPago(1), new FormaPago(1), new PlazoCredito(1)));
            financiamientos.add(new Financiamiento("FIN011908000002", 80.20, new TipoPago(2), new FormaPago(2), new PlazoCredito(2)));
            financiamientos.add(new Financiamiento("FIN011909000003", 90.20, new TipoPago(2), new FormaPago(2), new PlazoCredito(3)));
            rep.saveAll(financiamientos);
        }*/
    }
}
