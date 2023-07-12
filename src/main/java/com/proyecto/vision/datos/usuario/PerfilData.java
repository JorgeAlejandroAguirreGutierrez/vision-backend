package com.proyecto.vision.datos.usuario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.usuario.Perfil;
import com.proyecto.vision.repositorios.usuario.IPerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
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
            perfiles.add(new Perfil("PEF202301000001", "ADMINISTRADOR", "ADM", Constantes.estadoActivo, Constantes.si, Collections.emptyList()));
            perfiles.add(new Perfil("PEF202301000002", "GERENCIAL", "GER", Constantes.estadoActivo, Constantes.no, Collections.emptyList()));
            perfiles.add(new Perfil("PEF202301000003", "RECAUDADOR", "REC", Constantes.estadoActivo, Constantes.no, Collections.emptyList()));
            perfiles.add(new Perfil("PEF202301000004", "CONTADOR", "CTD", Constantes.estadoActivo, Constantes.no, Collections.emptyList()));
            perfiles.add(new Perfil("PEF202301000005", "CONSULTA", "CON", Constantes.estadoActivo, Constantes.no, Collections.emptyList()));

            rep.saveAll(perfiles);
        }
    }
}
