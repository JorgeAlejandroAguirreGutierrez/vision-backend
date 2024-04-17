package com.proyecto.vision.servicios.interf.acceso;

import java.util.List;

import com.proyecto.vision.modelos.acceso.Establecimiento;
import com.proyecto.vision.servicios.interf.IGenericoService;

public interface IEstablecimientoService extends IGenericoService<Establecimiento> {
	void validar(Establecimiento establecimiento);
	Establecimiento activar(Establecimiento establecimiento);
	Establecimiento inactivar(Establecimiento establecimiento);
	List<Establecimiento> consultarPorEstado(String estado);
	List<Establecimiento> consultarPorEmpresa(long empresaId);
	List<Establecimiento> consultarPorEmpresaYEstado(long empresaId, String estado);
}
