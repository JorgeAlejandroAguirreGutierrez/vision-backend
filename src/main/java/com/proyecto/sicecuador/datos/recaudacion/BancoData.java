package com.proyecto.sicecuador.datos.recaudacion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.recaudacion.Banco;
import com.proyecto.sicecuador.servicios.interf.comprobante.recaudacion.IBancoRepository;
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
@Order(25)
@Profile({"dev","prod"})
public class BancoData implements ApplicationRunner {
    @Autowired
    private IBancoRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Banco> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Banco> bancos = new ArrayList<>();
            bancos.add(new Banco("BA1", "BANCO", "BANCO PRODUBANCO", "PB", Constantes.activo));
            bancos.add(new Banco("BA2", "BANCO", "BANCO PICHINCHA", "BPI", Constantes.activo));
            bancos.add(new Banco("BA3", "BANCO", "BANCO PACIFICO", "BP", Constantes.activo));
            bancos.add(new Banco("BA4", "COOPERATIVA", "COOPROGRESO", "CP", Constantes.activo));
            rep.saveAll(bancos);
        }
    }
}
