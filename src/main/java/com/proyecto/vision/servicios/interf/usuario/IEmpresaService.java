package com.proyecto.vision.servicios.interf.usuario;

import java.util.List;

import com.proyecto.vision.modelos.usuario.Empresa;
import com.proyecto.vision.servicios.interf.IGenericoService;

public interface IEmpresaService extends IGenericoService<Empresa> {
	void validar(Empresa empresa);
	Empresa activar(Empresa empresa);
	Empresa inactivar(Empresa empresa);
	List<Empresa> consultarPorEstado(String estado);
}
