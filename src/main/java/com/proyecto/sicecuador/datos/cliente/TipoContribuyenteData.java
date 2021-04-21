package com.proyecto.sicecuador.datos.cliente;

import com.proyecto.sicecuador.modelos.cliente.TipoContribuyente;
import com.proyecto.sicecuador.repositorios.cliente.ITipoContribuyenteRepository;
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
@Order(12)
@Profile({"dev","prod"})
public class TipoContribuyenteData implements ApplicationRunner {
    @Autowired
    private ITipoContribuyenteRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<TipoContribuyente> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<TipoContribuyente> tipos_contribuyentes = new ArrayList<>();
            tipos_contribuyentes.add(new TipoContribuyente("TCO011907000001", "NATURAL", "NATURAL", false));
            tipos_contribuyentes.add(new TipoContribuyente("TCO011908000002", "JURIDICA", "PUBLICA", true));
            tipos_contribuyentes.add(new TipoContribuyente("TCO011909000003", "JURIDICA", "PRIVADA", true));
            rep.saveAll(tipos_contribuyentes);
        }
    }
}
