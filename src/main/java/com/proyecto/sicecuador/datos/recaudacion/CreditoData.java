package com.proyecto.sicecuador.datos.recaudacion;

import com.proyecto.sicecuador.modelos.cliente.CategoriaCliente;
import com.proyecto.sicecuador.modelos.recaudacion.Credito;
import com.proyecto.sicecuador.repositorios.recaudacion.ICreditoRepository;
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
@Order(26)
@Profile({"dev","prod"})
public class CreditoData implements ApplicationRunner {
    @Autowired
    private ICreditoRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*Optional<Credito> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Credito> creditos = new ArrayList<>();
            creditos.add(new Credito("CRE000001", 500, 12, "MENSUAL", "FRANCESA", 16.00, 16.00, Date.valueOf("2019-10-05"), Date.valueOf("2020-09-05"), 49.06, 44.39, 580.00, "VIGENTE"));
            creditos.add(new Credito("CRE000002", 120, 6, "QUINCENAL", "ALEMANA", 4.00, 16.00, Date.valueOf("2019-10-05"), Date.valueOf("2019-12-20"), 22.03, 4.03, 124.03, "CANCELADO"));
            rep.saveAll(creditos);
        }*/
    }
}
