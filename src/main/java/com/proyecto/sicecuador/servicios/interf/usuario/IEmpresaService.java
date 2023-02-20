package com.proyecto.sicecuador.servicios.interf.usuario;

import java.util.List;

import com.proyecto.sicecuador.modelos.usuario.Empresa;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IEmpresaService extends IGenericoService<Empresa> {
	void validar(Empresa empresa);
	Empresa activar(Empresa empresa);
	Empresa inactivar(Empresa empresa);
	List<Empresa> consultarActivos();
}
