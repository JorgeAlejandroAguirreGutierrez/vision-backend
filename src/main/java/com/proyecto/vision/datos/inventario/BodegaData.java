package com.proyecto.vision.datos.inventario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.inventario.Bodega;
import com.proyecto.vision.modelos.usuario.Empresa;
import com.proyecto.vision.repositorios.inventario.IBodegaRepository;
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
            bodegas.add(new Bodega("BOD012301000001","BODEGA MATRIZ", "BM001",Constantes.estadoActivo, new Empresa(1)));
            bodegas.add(new Bodega("BOD012301000002","BODEGA LOCAL 1", "BL001",Constantes.estadoActivo, new Empresa(1)));
            bodegas.add(new Bodega("BOD012301000003","BODEGA LOCAL 2", "BL002",Constantes.estadoActivo, new Empresa(1)));
            // EMPRESA 2
            bodegas.add(new Bodega("BOD022308000001","BODEGA MATRIZ", "BM001",Constantes.estadoActivo, new Empresa(2)));
            // EMPRESA 3
            bodegas.add(new Bodega("BOD032309000001","BODEGA MATRIZ", "BM001",Constantes.estadoActivo, new Empresa(3)));

            rep.saveAll(bodegas);
        }
    }
}
