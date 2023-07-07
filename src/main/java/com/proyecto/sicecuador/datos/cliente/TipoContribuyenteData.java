package com.proyecto.vision.datos.cliente;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.cliente.TipoContribuyente;
import com.proyecto.vision.repositorios.cliente.ITipoContribuyenteRepository;
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
            List<TipoContribuyente> tiposContribuyentes = new ArrayList<>();
            tiposContribuyentes.add(new TipoContribuyente("TCO011907000001", "NATURAL", "NATURAL", Constantes.no));
            tiposContribuyentes.add(new TipoContribuyente("TCO011908000002", "JURIDICA", "PUBLICA", Constantes.si));
            tiposContribuyentes.add(new TipoContribuyente("TCO011909000003", "JURIDICA", "PRIVADA", Constantes.si));
            rep.saveAll(tiposContribuyentes);
        }
    }
}
