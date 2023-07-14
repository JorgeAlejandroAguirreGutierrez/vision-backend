package com.proyecto.vision.datos.usuario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.usuario.Estacion;
import com.proyecto.vision.modelos.usuario.Perfil;
import com.proyecto.vision.modelos.usuario.Usuario;
import com.proyecto.vision.repositorios.usuario.IUsuarioRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
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
            byte[] avatarBytesMario = IOUtils.toString(new FileInputStream("src/main/resources/static/usuarios/mdelgado.txt"), "UTF-8").getBytes(StandardCharsets.UTF_8);
            byte[] avatarBytesAlejandro = IOUtils.toString(new FileInputStream("src/main/resources/static/usuarios/jalejandro.txt"), "UTF-8").getBytes(StandardCharsets.UTF_8);
            byte[] avatarBytesJorge = IOUtils.toString(new FileInputStream("src/main/resources/static/usuarios/jhidalgo.txt"), "UTF-8").getBytes(StandardCharsets.UTF_8);
            byte[] avatarBytesSantiago = IOUtils.toString(new FileInputStream("src/main/resources/static/usuarios/scantos.txt"), "UTF-8").getBytes(StandardCharsets.UTF_8);
            List<Usuario> usuarios = new ArrayList<>();
            usuarios.add(new Usuario("USU202301000001", "0603467226", "MDELGADO", "MARIO DELGADO", "032948243", "0931248509", "mastermariodelgado@gmail.com", "827ccb0eea8a706c4c34a16891f84e7b", "827ccb0eea8a706c4c34a16891f84e7b", "", avatarBytesMario, Constantes.no, "PRIMERA PREGUNTA", "PRIMERA RESPUESTA", Constantes.estadoActivo, new Perfil(1), new Estacion(1)));
            usuarios.add(new Usuario("USU202301000002", "0123456789", "JALEJANDRO", "JORGE ALEJANDRO AGUIRRE", "032945188", "0931248588", "alejandro@gmail.com", "827ccb0eea8a706c4c34a16891f84e7b", "827ccb0eea8a706c4c34a16891f84e7b", "", avatarBytesAlejandro, Constantes.no, "PRIMERA PREGUNTA", "PRIMERA RESPUESTA", Constantes.estadoActivo, new Perfil(1), new Estacion(4)));
            usuarios.add(new Usuario("USU202301000003", "0502685969", "JHIDALGO", "JORGE HIDALGO", "032945122", "0931248567", "gatosohidalgo@gmail.com", "827ccb0eea8a706c4c34a16891f84e7b", "827ccb0eea8a706c4c34a16891f84e7b", "", avatarBytesJorge, Constantes.no, "PRIMERA PREGUNTA", "PRIMERA RESPUESTA", Constantes.estadoActivo, new Perfil(1), new Estacion(6)));
            usuarios.add(new Usuario("USU202301000004", "0101010101", "SCANTOS", "SANTIAGO CANTOS", "032945101", "0931248501", "santiago_cantos@hotmail.com", "827ccb0eea8a706c4c34a16891f84e7b", "827ccb0eea8a706c4c34a16891f84e7b", "", avatarBytesSantiago, Constantes.no, "PRIMERA PREGUNTA", "PRIMERA RESPUESTA", Constantes.estadoActivo, new Perfil(1), new Estacion(7)));

            usuarios.add(new Usuario("USU202301000005", "0603467226", "GERENTE1", "MARIO DELGADO", "032948243", "0931248509", "mastermariodelgado@gmail.com", "827ccb0eea8a706c4c34a16891f84e7b", "827ccb0eea8a706c4c34a16891f84e7b", "", avatarBytesMario, Constantes.no, "PRIMERA PREGUNTA", "PRIMERA RESPUESTA", Constantes.estadoActivo, new Perfil(2), new Estacion(5)));
            usuarios.add(new Usuario("USU202301000006", "0502685969", "GERENTE2", "JORGE HIDALGO", "032945122", "0931248567", "gatosohidalgo@gmail.com", "827ccb0eea8a706c4c34a16891f84e7b", "827ccb0eea8a706c4c34a16891f84e7b", "", avatarBytesJorge, Constantes.no, "PRIMERA PREGUNTA", "PRIMERA RESPUESTA", Constantes.estadoActivo, new Perfil(2), new Estacion(8)));

            usuarios.add(new Usuario("USU202301000007", "0123456789", "RECAUDADOR1", "JORGE ALEJANDRO AGUIRRE", "032945188", "0931248588", "alejandro@gmail.com", "827ccb0eea8a706c4c34a16891f84e7b", "827ccb0eea8a706c4c34a16891f84e7b", "", avatarBytesAlejandro, Constantes.no, "PRIMERA PREGUNTA", "PRIMERA RESPUESTA", Constantes.estadoActivo, new Perfil(3), new Estacion(2)));
            usuarios.add(new Usuario("USU202301000008", "0101010101", "RECAUDADOR2", "SANTIAGO CANTOS", "032945101", "0931248501", "santiago_cantos@hotmail.com", "827ccb0eea8a706c4c34a16891f84e7b", "827ccb0eea8a706c4c34a16891f84e7b", "", avatarBytesSantiago, Constantes.no, "PRIMERA PREGUNTA", "PRIMERA RESPUESTA", Constantes.estadoActivo, new Perfil(3), new Estacion(9)));

            rep.saveAll(usuarios);
        }
    }
}
