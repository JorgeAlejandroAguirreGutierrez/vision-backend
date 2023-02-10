package com.proyecto.sicecuador.datos.usuario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.usuario.Estacion;
import com.proyecto.sicecuador.modelos.usuario.Perfil;
import com.proyecto.sicecuador.modelos.usuario.Usuario;
import com.proyecto.sicecuador.repositorios.usuario.IUsuarioRepository;
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
@Order(24)
@Profile({"dev","prod"})
public class UsuarioData implements ApplicationRunner {
    @Autowired
    private IUsuarioRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Usuario> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Usuario> usuarios = new ArrayList<>();
            usuarios.add(new Usuario("USU202301000001", "0603467226", "MDELGADO", "MARIO DELGADO", "032948243", "0931248509", "mastermariodelgado@gmail.com", "827ccb0eea8a706c4c34a16891f84e7b", "827ccb0eea8a706c4c34a16891f84e7b", "", null, Constantes.no, "PRIMERA PREGUNTA", "PRIMERA RESPUESTA", Constantes.activo, new Perfil(1), new Estacion(1)));
            usuarios.add(new Usuario("USU202301000002", "0123456789", "JALEJANDRO", "JORGE ALEJANDRO", "032945188", "0931248588", "alejandro@gmail.com", "827ccb0eea8a706c4c34a16891f84e7b", "827ccb0eea8a706c4c34a16891f84e7b", "", null, Constantes.no, "PRIMERA PREGUNTA", "PRIMERA RESPUESTA", Constantes.activo, new Perfil(1), new Estacion(2)));
            usuarios.add(new Usuario("USU202301000003", "0502685969", "JHIDALGO", "JORGE HIDALGO", "032945122", "09312485677", "gatosohidalgo@gmail.com", "827ccb0eea8a706c4c34a16891f84e7b", "827ccb0eea8a706c4c34a16891f84e7b", "", null, Constantes.no, "PRIMERA PREGUNTA", "PRIMERA RESPUESTA", Constantes.activo, new Perfil(1), new Estacion(3)));
            usuarios.add(new Usuario("USU202301000004", "0101010101", "SCANTOS", "SANTIAGO CANTOS", "032945101", "0931248501", "santiago_cantos@hotmail.com", "827ccb0eea8a706c4c34a16891f84e7b", "827ccb0eea8a706c4c34a16891f84e7b", "", null, Constantes.no, "PRIMERA PREGUNTA", "PRIMERA RESPUESTA", Constantes.activo, new Perfil(1), new Estacion(4)));
            rep.saveAll(usuarios);
        }
    }
}
