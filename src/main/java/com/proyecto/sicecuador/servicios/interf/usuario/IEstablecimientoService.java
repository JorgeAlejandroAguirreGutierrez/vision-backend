package com.proyecto.sicecuador.servicios.interf.usuario;

import java.util.List;

import com.proyecto.sicecuador.modelos.usuario.Establecimiento;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IEstablecimientoService extends IGenericoService<Establecimiento> {
	void validar(Establecimiento establecimiento);
	Establecimiento activar(Establecimiento establecimiento);
	Establecimiento inactivar(Establecimiento establecimiento);
	List<Establecimiento> consultarActivos();
	List<Establecimiento> consultarPorEmpresa(long empresaId);
}
