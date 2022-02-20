package com.proyecto.sicecuador.datos.comprobante;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(45)
@Profile({"dev","prod"})
public class FacturaDetalleData implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
    }
}
