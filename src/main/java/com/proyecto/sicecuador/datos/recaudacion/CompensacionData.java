package com.proyecto.sicecuador.datos.recaudacion;

import com.proyecto.sicecuador.repositorios.recaudacion.ICompensacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class CompensacionData implements ApplicationRunner {
    @Autowired
    private ICompensacionRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
