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
            usuarios.add(new Usuario("U1", "JUAN ANTONIO", "juanantonio@gmail.com", "12345", "010225036", "/storage/img/imagen1", "JUANANTONIOUSUARIO", Constantes.no, "PRIMERA PREGUNTA", "PRIMERA RESPUESTA", Constantes.inactivo, new Perfil(1), new Estacion(1)));
            usuarios.add(new Usuario("U2", "CRISTINA ALEJANDRA", "cristinaalejandra@gmail.com", "12345", "030132225", "/storage/img/imagen2", "CRISTINAALEJANDRAUSUARIO", Constantes.no, "PRIMERA PREGUNTA", "PRIMERA RESPUESTA", Constantes.activo, new Perfil(1), new Estacion(2)));
            usuarios.add(new Usuario("U3", "MARIO DELGADO", "mastermariodelgado@gmail.com", "12345", "0603467226", "/storage/img/imagen3", "MARIODELGADOUSUARIO", Constantes.no, "PRIMERA PREGUNTA", "PRIMERA RESPUESTA", Constantes.activo, new Perfil(1), new Estacion(3)));
            usuarios.add(new Usuario("U4", "JORGE HIDALGO", "gatosohidalgo@gmail.com", "12345", "0502685969", "/storage/img/imagen4", "JORGEHIDALGOUSUARIO", Constantes.no, "PRIMERA PREGUNTA", "PRIMERA RESPUESTA", Constantes.activo, new Perfil(1), new Estacion(4)));
            usuarios.add(new Usuario("U5", "JORGE ALEJANDRO", "alejandro@gmail.com", "12345", "0123456789", "/storage/img/imagen5", "JORGEALEJANDROUSUARIO", Constantes.no, "PRIMERA PREGUNTA", "PRIMERA RESPUESTA", Constantes.activo, new Perfil(1), new Estacion(5)));
            usuarios.add(new Usuario("U6", "MARIA JOSE", "mariajose@gmail.com", "12345", "080212685", "/storage/img/imagen6", "MARIAJOSEUSUARIO", Constantes.no, "PRIMERA PREGUNTA", "PRIMERA RESPUESTA", Constantes.activo, new Perfil(2), new Estacion(6)));
            usuarios.add(new Usuario("U7", "MARIA JULIANA", "nariajuliana@gmail.com", "12345", "130735366", "/storage/img/imagen7", "MARIAJULIANAUSUARIO", Constantes.no, "PRIMERA PREGUNTA", "PRIMERA RESPUESTA", Constantes.activo, new Perfil(2), new Estacion(7)));
            usuarios.add(new Usuario("U8", "SANTIAGO CANTOS", "santiago_cantos@hotmail.com", "12345", "0101010101", "/storage/img/imagen4", "SANTIAGOCANTOSUSUARIO", Constantes.no, "PRIMERA PREGUNTA", "PRIMERA RESPUESTA", Constantes.activo, new Perfil(1), new Estacion(8)));
            rep.saveAll(usuarios);
        }
    }
}
