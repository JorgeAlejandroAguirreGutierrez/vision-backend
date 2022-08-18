package com.proyecto.sicecuador.datos.recaudacion;

import com.proyecto.sicecuador.modelos.recaudacion.Banco;
import com.proyecto.sicecuador.modelos.recaudacion.Cheque;
import com.proyecto.sicecuador.modelos.recaudacion.Recaudacion;
import com.proyecto.sicecuador.repositorios.recaudacion.IChequeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Order(46)
@Profile({"dev","prod"})
public class ChequeData implements ApplicationRunner {
    @Autowired
    private IChequeRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*Optional<Cheque> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Cheque> cheques = new ArrayList<>();
            cheques.add(new Cheque("CHE000001", "123-345-00000123", "A LA VISTA", Date.valueOf("2019-08-23"), Date.valueOf("2019-08-30"), 50.00, new Recaudacion(1), new Banco(2)));
            cheques.add(new Cheque("CHE000002", "123-345-00000002", "POSFECHADO", Date.valueOf("2019-08-24"), Date.valueOf("2019-09-30"), 10.00, new Recaudacion(1), new Banco(1)));
            cheques.add(new Cheque("CHE000003", "123-345-00000003", "A LA VISTA", Date.valueOf("2019-08-25"), Date.valueOf("2019-08-28"), 60.00, new Recaudacion(2), new Banco(3)));
            cheques.add(new Cheque("CHE000004", "123-345-00000004", "POSFECHADO", Date.valueOf("2019-08-26"), Date.valueOf("2019-09-15"), 20.00, new Recaudacion(2), new Banco(2)));
            rep.saveAll(cheques);
        }*/
    }
}
