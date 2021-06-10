package com.proyecto.sicecuador.datos.recaudacion;

import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.modelos.comprobante.TipoComprobante;
import com.proyecto.sicecuador.modelos.recaudacion.*;
import com.proyecto.sicecuador.modelos.usuario.Sesion;
import com.proyecto.sicecuador.repositorios.recaudacion.IRecaudacionRepository;
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
@Order(45)
@Profile({"dev","prod"})
public class RecaudacionData implements ApplicationRunner {
    @Autowired
    private IRecaudacionRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*Optional<Recaudacion> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Recaudacion> recaudaciones = new ArrayList<>();
            recaudaciones.add(new Recaudacion("REC000001", Date.valueOf("2019-08-26"), 100.00, "Recaudación factura 1", 0.00, 60.00, 10.00, 10.00, new TarjetaCredito(1), new TarjetaDebito(1), new Credito(1), new TipoComprobante(1), new Factura(1), new Sesion(1)));
            recaudaciones.add(new Recaudacion("REC000002", Date.valueOf("2019-08-27"), 300.00, "Recaudación factura 2", 100.00, 80.00, 20.00, 0.00, new TarjetaCredito(2), new TarjetaDebito(2), new Credito(2), new TipoComprobante(1), new Factura(1), new Sesion(1)));
            rep.saveAll(recaudaciones);
        }*/
    }
}
