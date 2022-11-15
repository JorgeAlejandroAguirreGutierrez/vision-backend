package com.proyecto.sicecuador.datos.usuario;

import com.proyecto.sicecuador.modelos.usuario.Perfil;
import com.proyecto.sicecuador.repositorios.usuario.IPerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Order(21)
public class PerfilData implements ApplicationRunner {
    @Autowired
    private IPerfilRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Perfil> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Perfil> perfiles = new ArrayList<>();
            perfiles.add(new Perfil("PE1", "ADMINISTRADOR", "ADM", "ACTIVO", true));
            perfiles.add(new Perfil("PE2", "RECAUDADOR", "REC", "ACTIVO", false));
            perfiles.add(new Perfil("PE3", "DESPACHADOR", "DES", "ACTIVO", true));
            perfiles.add(new Perfil("PE4", "CONTADOR", "CTD", "ACTIVO", false));
            rep.saveAll(perfiles);
        }
    }
}
