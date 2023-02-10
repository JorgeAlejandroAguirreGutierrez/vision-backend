package com.proyecto.sicecuador.datos.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.inventario.Bodega;
import com.proyecto.sicecuador.repositorios.inventario.IBodegaRepository;
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
@Order(36)
@Profile({"dev","prod"})
public class BodegaData implements ApplicationRunner {
    @Autowired
    private IBodegaRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Bodega> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Bodega> bodegas = new ArrayList<>();
            bodegas.add(new Bodega("BOD012001000001","BODEGA MATRIZ", Constantes.activo));
            bodegas.add(new Bodega("BOD012001000002","BODEGA LOCAL 1", Constantes.activo));
            bodegas.add(new Bodega("BOD012001000003","BODEGA LOCAL 2", Constantes.activo));
            rep.saveAll(bodegas);
        }
    }
}
