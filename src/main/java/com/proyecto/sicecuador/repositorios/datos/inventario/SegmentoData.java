package com.proyecto.sicecuador.repositorios.datos.inventario;

import com.proyecto.sicecuador.modelos.inventario.Segmento;
import com.proyecto.sicecuador.modelos.inventario.TipoGasto;
import com.proyecto.sicecuador.repositorios.interf.inventario.ISegmentoRepository;
import com.proyecto.sicecuador.repositorios.interf.inventario.ITipoGastoRepository;
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
@Order(33)
@Profile({"dev","prod"})
public class SegmentoData implements ApplicationRunner {
    @Autowired
    private ISegmentoRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Segmento> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Segmento> segmentos = new ArrayList<>();
            segmentos.add(new Segmento("SEG011907000001", "MAYORISTA"));
            segmentos.add(new Segmento("SEG011907000002", "DISTRIBUIDOR"));
            segmentos.add(new Segmento("SEG011907000003", "TARJETA DE CREDITO"));
            segmentos.add(new Segmento("SEG011907000004", "CLIENTE FINAL"));
            rep.saveAll(segmentos);
        }
    }
}
