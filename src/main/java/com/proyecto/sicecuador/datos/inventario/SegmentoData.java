package com.proyecto.sicecuador.datos.inventario;

import com.proyecto.sicecuador.modelos.inventario.Segmento;
import com.proyecto.sicecuador.modelos.inventario.TipoGasto;
import com.proyecto.sicecuador.repositorios.inventario.ISegmentoRepository;
import com.proyecto.sicecuador.repositorios.inventario.ITipoGastoRepository;
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
public class SegmentoData implements ApplicationRunner {
    @Autowired
    private ISegmentoRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Segmento> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Segmento> segmentos = new ArrayList<>();
            segmentos.add(new Segmento("SEG011907000001", "CLIENTE FINAL", 6));
            segmentos.add(new Segmento("SEG011907000002", "MAYORISTA", 2));
            segmentos.add(new Segmento("SEG011907000003", "DISTRIBUIDOR", 3));
            segmentos.add(new Segmento("SEG011907000004", "TARJETA DE CREDITO", 10));
            rep.saveAll(segmentos);
        }
    }
}
