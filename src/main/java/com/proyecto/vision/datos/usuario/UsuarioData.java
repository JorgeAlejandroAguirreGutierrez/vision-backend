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
            byte[] avatarBytesJorge = IOUtils.toString(new FileInputStream("src/main/resources/static/usuarios/jhidalgo.txt"), "UTF-8").getBytes(StandardCharsets.UTF_8);
            byte[] avatarBytesSantiago = IOUtils.toString(new FileInputStream("src/main/resources/static/usuarios/scantos.txt"), "UTF-8").getBytes(StandardCharsets.UTF_8);
            byte[] avatarBytesManuel = IOUtils.toString(new FileInputStream("src/main/resources/static/usuarios/manuel.txt"), "UTF-8").getBytes(StandardCharsets.UTF_8);
            byte[] avatarBytesRosa = IOUtils.toString(new FileInputStream("src/main/resources/static/usuarios/rosa.txt"), "UTF-8").getBytes(StandardCharsets.UTF_8);
            byte[] avatarBytesAyda = IOUtils.toString(new FileInputStream("src/main/resources/static/usuarios/ayda.txt"), "UTF-8").getBytes(StandardCharsets.UTF_8);
            byte[] avatarBytesDiana = IOUtils.toString(new FileInputStream("src/main/resources/static/usuarios/diana.txt"), "UTF-8").getBytes(StandardCharsets.UTF_8);
            List<Usuario> usuarios = new ArrayList<>();
            //USUARIOS EMPRESA 1
            usuarios.add(new Usuario("USU202301000001", "0603467226", "MDELGADO", "MARIO DELGADO", "032948243", "0931248509", "mastermariodelgado@gmail.com", "827ccb0eea8a706c4c34a16891f84e7b", "827ccb0eea8a706c4c34a16891f84e7b", "", Constantes.no, "PRIMERA PREGUNTA", "PRIMERA RESPUESTA", Constantes.estadoActivo, new Perfil(1), new Estacion(1)));
            usuarios.add(new Usuario("USU202301000002", "0502685969", "JHIDALGO", "JORGE HIDALGO", "032945122", "0931248567", "gatosohidalgo@gmail.com", "827ccb0eea8a706c4c34a16891f84e7b", "827ccb0eea8a706c4c34a16891f84e7b", "", Constantes.no, "PRIMERA PREGUNTA", "PRIMERA RESPUESTA", Constantes.estadoActivo, new Perfil(1), new Estacion(6)));
            usuarios.add(new Usuario("USU202301000003", "0101010101", "SCANTOS", "SANTIAGO CANTOS", "032945101", "0931248501", "santiago_cantos@hotmail.com", "827ccb0eea8a706c4c34a16891f84e7b", "827ccb0eea8a706c4c34a16891f84e7b", "",  Constantes.no, "PRIMERA PREGUNTA", "PRIMERA RESPUESTA", Constantes.estadoActivo, new Perfil(1), new Estacion(7)));
            //USUARIOS EMPRESA 2
            usuarios.add(new Usuario("USU202308000005", "0604383513", "MALQUI", "ALQUI RAMIREZ MANUEL LEONARDO", "032948243", "0931248509", "mastermariodelgado@gmail.com", "827ccb0eea8a706c4c34a16891f84e7b", "827ccb0eea8a706c4c34a16891f84e7b", "", Constantes.no, "¿CUAL ES TU MARCA DE VEHICULO PREFERIDO?", "MAZDA", Constantes.estadoActivo, new Perfil(3), new Estacion(8)));
            usuarios.add(new Usuario("USU202308000006", "1105355059", "RGONZALEZ", "GONZALEZ PINEDA ROSA MARGARITA", "032945122", "0931248567", "gatosohidalgo@gmail.com", "827ccb0eea8a706c4c34a16891f84e7b", "827ccb0eea8a706c4c34a16891f84e7b", "", Constantes.no, "¿CUAL ES TU MARCA DE VEHICULO PREFERIDO?", "MAZDA", Constantes.estadoActivo, new Perfil(3), new Estacion(9)));
            usuarios.add(new Usuario("USU202308000007", "0601308661", "APOMAGUALLI", "POMAGUALLI PIRAY AYDA ROMELIA", "032945101", "0931248501", "vallauca@gmail.com", "827ccb0eea8a706c4c34a16891f84e7b", "827ccb0eea8a706c4c34a16891f84e7b", "", Constantes.no, "¿CUAL ES TU MARCA DE VEHICULO PREFERIDO?", "MAZDA", Constantes.estadoActivo, new Perfil(2), new Estacion(10)));
            //USUARIOS EMPRESA 3
            usuarios.add(new Usuario("USU202309000008", "0603555145", "AMOLINA", "MOLINA ANGEL", "", "", "catydi_30@hotmail.com", "827ccb0eea8a706c4c34a16891f84e7b", "827ccb0eea8a706c4c34a16891f84e7b", "", Constantes.no, "¿CUAL ES TU MARCA DE VEHICULO PREFERIDO?", "MAZDA", Constantes.estadoActivo, new Perfil(3), new Estacion(11)));
            usuarios.add(new Usuario("USU202309000009", "0601308661", "DMOLINA", "MOLINA DIANA", "", "", "catydi_30@hotmail.com", "827ccb0eea8a706c4c34a16891f84e7b", "827ccb0eea8a706c4c34a16891f84e7b", "", Constantes.no, "¿CUAL ES TU MARCA DE VEHICULO PREFERIDO?", "MAZDA", Constantes.estadoActivo, new Perfil(2), new Estacion(12)));

            rep.saveAll(usuarios);
        }
    }
}
