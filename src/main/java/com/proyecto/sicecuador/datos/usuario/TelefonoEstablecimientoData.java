package com.proyecto.sicecuador.datos.usuario;

import com.proyecto.sicecuador.modelos.usuario.Establecimiento;
import com.proyecto.sicecuador.modelos.usuario.TelefonoEstablecimiento;
import com.proyecto.sicecuador.repositorios.usuario.ITelefonoEstablecimientoRepository;
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
@Order(20)
@Profile({"dev","prod"})
public class TelefonoEstablecimientoData implements ApplicationRunner {
	@Autowired
	private ITelefonoEstablecimientoRepository rep;
	@Override
	public void run(ApplicationArguments args) throws Exception{
		Optional<TelefonoEstablecimiento> ant=rep.findById((long) 1);
		if(!ant.isPresent()) {
			List<TelefonoEstablecimiento> telefonosEstablecimientos = new ArrayList<>();
			telefonosEstablecimientos.add(new TelefonoEstablecimiento("TEL011907000001", "032964123","ACTIVO", new Establecimiento(1)));
			rep.saveAll(telefonosEstablecimientos);
		}
	}
}
