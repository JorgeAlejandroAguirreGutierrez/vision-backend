package com.proyecto.sicecuador.datos.configuracion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.configuracion.Secuencial;
import com.proyecto.sicecuador.modelos.usuario.Estacion;
import com.proyecto.sicecuador.modelos.venta.TipoComprobante;
import com.proyecto.sicecuador.repositorios.configuracion.ISecuencialRepository;
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
@Order(20)
@Profile({"dev","prod"})
public class SecuencialData implements ApplicationRunner {
    @Autowired
    private ISecuencialRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Secuencial> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Secuencial> secuenciales = new ArrayList<>();
            secuenciales.add(new Secuencial("SEC202304000001", 1, Constantes.activo, new TipoComprobante(1), new Estacion(1)));
            secuenciales.add(new Secuencial("SEC202304000002", 1, Constantes.activo, new TipoComprobante(2), new Estacion(1)));
            rep.saveAll(secuenciales);
        }
    }
}
