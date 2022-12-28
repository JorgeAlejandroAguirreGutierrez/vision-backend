package com.proyecto.sicecuador.datos.usuario;

import com.proyecto.sicecuador.modelos.usuario.Establecimiento;
import com.proyecto.sicecuador.modelos.usuario.CelularEstablecimiento;
import com.proyecto.sicecuador.repositorios.usuario.ICelularEstablecimientoRepository;
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
public class CelularEstablecimientoData implements ApplicationRunner {
	@Autowired
	private ICelularEstablecimientoRepository rep;
	@Override
	public void run(ApplicationArguments args) throws Exception{
		Optional<CelularEstablecimiento> ant=rep.findById((long) 1);
		if(!ant.isPresent()) {
			List<CelularEstablecimiento> telefonosEstablecimientos = new ArrayList<>();
			telefonosEstablecimientos.add(new CelularEstablecimiento("CEL011907000001", "0999778877", new Establecimiento(1)));
			rep.saveAll(telefonosEstablecimientos);
		}
	}
}
