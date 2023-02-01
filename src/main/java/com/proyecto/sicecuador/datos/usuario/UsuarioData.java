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
            usuarios.add(new Usuario("USU202301000001", "MARIO DELGADO", "032948243", "0931248509", "mastermariodelgado@gmail.com", "827ccb0eea8a706c4c34a16891f84e7b", "827ccb0eea8a706c4c34a16891f84e7b", "0603467226", null, "MDELGADO", Constantes.no, "PRIMERA PREGUNTA", "PRIMERA RESPUESTA", Constantes.activo, new Perfil(1), new Estacion(1)));
            usuarios.add(new Usuario("USU202301000002", "JORGE ALEJANDRO", "032945188", "0931248588", "alejandro@gmail.com", "827ccb0eea8a706c4c34a16891f84e7b", "827ccb0eea8a706c4c34a16891f84e7b", "0123456789", null, "JALEJANDRO", Constantes.no, "PRIMERA PREGUNTA", "PRIMERA RESPUESTA", Constantes.activo, new Perfil(1), new Estacion(2)));
            usuarios.add(new Usuario("USU202301000003", "JORGE HIDALGO", "032945122", "09312485677", "gatosohidalgo@gmail.com", "827ccb0eea8a706c4c34a16891f84e7b", "827ccb0eea8a706c4c34a16891f84e7b", "0502685969", null, "JHIDALGO", Constantes.no, "PRIMERA PREGUNTA", "PRIMERA RESPUESTA", Constantes.activo, new Perfil(1), new Estacion(3)));
            usuarios.add(new Usuario("USU202301000004", "SANTIAGO CANTOS", "032945101", "0931248501", "santiago_cantos@hotmail.com", "827ccb0eea8a706c4c34a16891f84e7b", "827ccb0eea8a706c4c34a16891f84e7b", "0101010101", null, "SCANTOS", Constantes.no, "PRIMERA PREGUNTA", "PRIMERA RESPUESTA", Constantes.activo, new Perfil(1), new Estacion(4)));
            usuarios.add(new Usuario("USU202301000005", "MARIA JOSE", "032945111", "0931248511", "mariajose@gmail.com", "827ccb0eea8a706c4c34a16891f84e7b", "827ccb0eea8a706c4c34a16891f84e7b", "080212685", null, "MJOSE", Constantes.no, "PRIMERA PREGUNTA", "PRIMERA RESPUESTA", Constantes.activo, new Perfil(2), new Estacion(6)));
            usuarios.add(new Usuario("USU202301000006", "MARIA JULIANA", "032945155", "0931248555", "nariajuliana@gmail.com", "827ccb0eea8a706c4c34a16891f84e7b", "827ccb0eea8a706c4c34a16891f84e7b", "130735366", null, "MJULIANA", Constantes.no, "PRIMERA PREGUNTA", "PRIMERA RESPUESTA", Constantes.activo, new Perfil(2), new Estacion(7)));
            usuarios.add(new Usuario("USU202301000007", "JUAN PEREZ", "032945143", "0931248569", "juanantonio@gmail.com", "827ccb0eea8a706c4c34a16891f84e7b", "827ccb0eea8a706c4c34a16891f84e7b", "010225036", null, "JPEREZ", Constantes.no, "PRIMERA PREGUNTA", "PRIMERA RESPUESTA", Constantes.inactivo, new Perfil(1), new Estacion(5)));
            rep.saveAll(usuarios);
        }
    }
}
