package com.proyecto.sicecuador.repositorios.datos.recaudacion;

import com.proyecto.sicecuador.repositorios.interf.recaudacion.IEfectivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(28)
public class EfectivoData implements ApplicationRunner {
    @Autowired
    private IEfectivoRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*Optional<Efectivo> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Efectivo> efectivos = new ArrayList<>();
            efectivos.add(new Efectivo("EFE000001", 50.00));
            efectivos.add(new Efectivo("EFE000002", 20.00));
            efectivos.add(new Efectivo("EFE000003", 10.00));
            efectivos.add(new Efectivo("EFE000004", 80.00));
            rep.saveAll(efectivos);
        }*/
    }
}
