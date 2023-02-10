package com.proyecto.sicecuador.datos.compra;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.compra.GrupoProveedor;
import com.proyecto.sicecuador.modelos.contabilidad.CuentaContable;
import com.proyecto.sicecuador.repositorios.compra.IGrupoProveedorRepository;
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
@Order(9)
@Profile({"dev","prod"})
public class GrupoProveedorData implements ApplicationRunner {
	@Autowired
	private IGrupoProveedorRepository rep;
	@Override
	public void run(ApplicationArguments args) throws Exception{
		Optional<GrupoProveedor> ant = rep.findById((long) 1);
		if(!ant.isPresent()) {
			List<GrupoProveedor> gruposProveedores = new ArrayList<>();
			gruposProveedores.add(new GrupoProveedor("GPR011907000001", "PROVEEDORES NACIONALES", "NAC", Constantes.activo, new CuentaContable(4)));
			gruposProveedores.add(new GrupoProveedor("GPR011907000002", "PROVEEDORES INTERNACIONALES", "INTER", Constantes.activo, new CuentaContable(5)));
			rep.saveAll(gruposProveedores);
		}
	}
	
}
