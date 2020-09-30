package com.proyecto.sicecuador.repositorios.datos.cliente;

import com.proyecto.sicecuador.modelos.cliente.TipoPago;
import com.proyecto.sicecuador.repositorios.interf.cliente.ITipoPagoRepository;
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
public class TipoPagoData implements ApplicationRunner {
    @Autowired
    private ITipoPagoRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<TipoPago> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<TipoPago> tipos_pagos = new ArrayList<>();
            tipos_pagos.add(new TipoPago("TPA011907000001", "EFECTIVO", "EF"));
            tipos_pagos.add(new TipoPago("TPA011908000002", "CREDITO", "CR"));
            rep.saveAll(tipos_pagos);
        }
    }
}
