package com.proyecto.sicecuador.datos.usuario;

import com.proyecto.sicecuador.modelos.usuario.EstacionUsuario;
import com.proyecto.sicecuador.repositorios.usuario.IEstacionUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(22)
@Profile({"dev","prod"})
public class EstacionUsuarioData implements ApplicationRunner {
	@Override
	public void run(ApplicationArguments args) throws Exception{
	}
}
