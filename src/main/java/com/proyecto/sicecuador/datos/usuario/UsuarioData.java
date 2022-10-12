package com.proyecto.sicecuador.datos.usuario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.configuracion.Empresa;
import com.proyecto.sicecuador.modelos.usuario.Perfil;
import com.proyecto.sicecuador.modelos.usuario.PuntoVenta;
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
            usuarios.add(new Usuario("U1", "JUAN ANTONIO", "juanantonio@gmail.com", "12345", "010225036", "/storage/img/imagen1", Constantes.inactivo, new PuntoVenta(1), new Perfil(1), new Empresa(1)));
            usuarios.add(new Usuario("U2", "CRISTINA ALEJANDRA", "cristinaalejandra@gmail.com", "12345", "030132225", "/storage/img/imagen2", Constantes.activo, new PuntoVenta(1), new Perfil(1), new Empresa(1)));
            usuarios.add(new Usuario("U3", "MARIO DELGADO", "mastermariodelgado@gmail.com", "12345", "0603467226", "/storage/img/imagen3", Constantes.activo, new PuntoVenta(1), new Perfil(1), new Empresa(1)));
            usuarios.add(new Usuario("U4", "JORGE HIDALGO", "gatosohidalgo@gmail.com", "12345", "0502685969", "/storage/img/imagen4", Constantes.activo, new PuntoVenta(1), new Perfil(1), new Empresa(1)));
            usuarios.add(new Usuario("U5", "JORGE ALEJANDRO", "alejandro@gmail.com", "12345", "0123456789", "/storage/img/imagen5", Constantes.activo, new PuntoVenta(1), new Perfil(1), new Empresa(1)));
            usuarios.add(new Usuario("U6", "MARIA JOSE", "mariajose@gmail.com", "12345", "080212685", "/storage/img/imagen6", Constantes.activo, new PuntoVenta(1), new Perfil(2), new Empresa(2)));
            usuarios.add(new Usuario("U7", "MARIA JULIANA", "nariajuliana@gmail.com", "12345", "130735366", "/storage/img/imagen7", Constantes.activo, new PuntoVenta(1), new Perfil(2), new Empresa(2)));
            usuarios.add(new Usuario("U8", "SANTIAGO CANTOS", "santiago_cantos@hotmail.com", "12345", "0101010101", "/storage/img/imagen4", Constantes.activo, new PuntoVenta(1), new Perfil(1), new Empresa(1)));
            rep.saveAll(usuarios);
        }
    }
}
