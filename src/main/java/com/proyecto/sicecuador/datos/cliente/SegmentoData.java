package com.proyecto.sicecuador.datos.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.cliente.Segmento;
import com.proyecto.sicecuador.modelos.usuario.Empresa;
import com.proyecto.sicecuador.repositorios.cliente.ISegmentoRepository;
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
            segmentos.add(new Segmento("SEG012301000001", 6, "CLIENTE FINAL", "CF", Constantes.activo, new Empresa(1)));
            segmentos.add(new Segmento("SEG012301000002", 2, "MAYORISTA" ,"MAY", Constantes.activo, new Empresa(1)));
            segmentos.add(new Segmento("SEG012301000003", 3, "DISTRIBUIDOR", "DIST", Constantes.activo, new Empresa(1)));
            segmentos.add(new Segmento("SEG012301000004", 10, "TARJETA DE CREDITO", "TCR", Constantes.activo, new Empresa(1)));
            segmentos.add(new Segmento("SEG022305000001", 6, "CLIENTE FINAL", "CF", Constantes.activo, new Empresa(2)));
            segmentos.add(new Segmento("SEG032305000002", 2, "MAYORISTA" ,"MAY", Constantes.activo, new Empresa(2)));
            rep.saveAll(segmentos);
        }
    }
}
