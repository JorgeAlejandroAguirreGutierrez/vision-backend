package com.proyecto.sicecuador.repositorios.datos.recaudacion;

import com.proyecto.sicecuador.repositorios.interf.recaudacion.ICreditoAmortizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class CreditoAmortizacionData implements ApplicationRunner {
    @Autowired
    private ICreditoAmortizacionRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
