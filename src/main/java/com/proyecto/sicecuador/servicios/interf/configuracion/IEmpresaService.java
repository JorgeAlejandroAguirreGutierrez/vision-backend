package com.proyecto.sicecuador.servicios.interf.configuracion;

import java.util.List;
import java.util.Optional;

import com.proyecto.sicecuador.modelos.configuracion.Empresa;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IEmpresaService extends IGenericoService<Empresa> {
	Empresa activar(Empresa empresa);
	Empresa inactivar(Empresa empresa);
	List<Empresa> consultarActivos();
}
