package com.proyecto.sicecuador.servicios.interf.usuario;

import java.util.List;

import com.proyecto.sicecuador.modelos.usuario.Establecimiento;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IEstablecimientoService extends IGenericoService<Establecimiento> {
	Establecimiento activar(Establecimiento establecimiento);
	Establecimiento inactivar(Establecimiento establecimiento);
	List<Establecimiento> consultarActivos();
}
