package com.proyecto.sicecuador.datos.usuario;

import com.proyecto.sicecuador.modelos.usuario.Establecimiento;
import com.proyecto.sicecuador.modelos.usuario.CorreoEstablecimiento;
import com.proyecto.sicecuador.repositorios.usuario.ICorreoEstablecimientoRepository;
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
@Order(21)
@Profile({"dev","prod"})
public class CorreoEstablecimientoData implements ApplicationRunner {
	@Autowired
	private ICorreoEstablecimientoRepository rep;
	@Override
	public void run(ApplicationArguments args) throws Exception{
		Optional<CorreoEstablecimiento> ant=rep.findById((long) 1);
		if(!ant.isPresent()) {
			List<CorreoEstablecimiento> correosEstablecimientos = new ArrayList<>();
			correosEstablecimientos.add(new CorreoEstablecimiento("COR011907000001", "correo@hotmail.com","ACTIVO", new Establecimiento(1)));
			rep.saveAll(correosEstablecimientos);
		}
	}
}
