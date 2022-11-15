package com.proyecto.sicecuador.datos.usuario;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.proyecto.sicecuador.modelos.configuracion.Empresa;
import com.proyecto.sicecuador.modelos.usuario.Establecimiento;
import com.proyecto.sicecuador.modelos.usuario.Estacion;
import com.proyecto.sicecuador.modelos.usuario.Sesion;
import com.proyecto.sicecuador.modelos.usuario.Usuario;
import com.proyecto.sicecuador.repositorios.usuario.IEstacionRepository;


@Component
@Order(22)
@Profile({"dev","prod"})
public class EstacionData implements ApplicationRunner {
    @Autowired
    private IEstacionRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
    	Optional<Estacion> ant=rep.findById((long) 1);
    	if (!ant.isPresent()){
    		List<Estacion> estaciones = new ArrayList<>();
    		estaciones.add(new Estacion("EST00001", "DESCRIPCION ESTACION","PC1", "1.1.1.1", "ABREVIATURA","ACTIVO",new Establecimiento(1)));
    		rep.saveAll(estaciones);
    	}
    }
}
