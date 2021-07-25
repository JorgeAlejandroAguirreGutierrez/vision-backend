package com.proyecto.sicecuador.datos.cliente;

import com.proyecto.sicecuador.modelos.cliente.FormaPago;
import com.proyecto.sicecuador.repositorios.cliente.IFormaPagoRepository;
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
@Order(17)
@Profile({"dev","prod"})
public class FormaPagoData implements ApplicationRunner {
    @Autowired
    private IFormaPagoRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<FormaPago> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<FormaPago> formas_pagos = new ArrayList<>();
            formas_pagos.add(new FormaPago("FPA011907000001", "PREPAGO", "PRE", "ACTIVO"));
            formas_pagos.add(new FormaPago("FPA011908000002", "POSTPAGO", "POS", "ACTIVO"));
            rep.saveAll(formas_pagos);
        }
    }
}
