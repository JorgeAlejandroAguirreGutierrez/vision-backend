package com.proyecto.sicecuador.repositorios.datos.usuario;

import com.proyecto.sicecuador.modelos.usuario.Establecimiento;
import com.proyecto.sicecuador.modelos.usuario.Permiso;
import com.proyecto.sicecuador.modelos.usuario.PuntoVenta;
import com.proyecto.sicecuador.repositorios.interf.usuario.IPuntoVentaRepository;
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
@Order(19)
@Profile({"dev","prod"})
public class PuntoVentaData implements ApplicationRunner {
    @Autowired
    private IPuntoVentaRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<PuntoVenta> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<PuntoVenta> puntos_ventas = new ArrayList<>();
            puntos_ventas.add(new PuntoVenta("EM1ES1CA001", "CAJA1", new Establecimiento(1)));
            puntos_ventas.add(new PuntoVenta("EM1ES1CA002", "CAJA2", new Establecimiento(1)));
            puntos_ventas.add(new PuntoVenta("EM1ES2CA001", "CAJA1", new Establecimiento(2)));
            rep.saveAll(puntos_ventas);
        }
    }
}
