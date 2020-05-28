package com.proyecto.sicecuador.repositorios.datos.comprobante;

import com.proyecto.sicecuador.repositorios.interf.comprobante.IFacturaCaracteristicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(44)
public class FacturaCaracteristicaData implements ApplicationRunner {
    @Autowired
    private IFacturaCaracteristicaRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
    }
}
