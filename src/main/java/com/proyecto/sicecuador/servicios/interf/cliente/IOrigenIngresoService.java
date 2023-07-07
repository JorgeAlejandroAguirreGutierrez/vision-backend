package com.proyecto.vision.servicios.interf.cliente;

import java.util.List;

import com.proyecto.vision.modelos.cliente.OrigenIngreso;
import com.proyecto.vision.servicios.interf.IGenericoService;

public interface IOrigenIngresoService extends IGenericoService<OrigenIngreso> {
	void validar(OrigenIngreso origenIngreso);
	List<OrigenIngreso> consultarPorEstado(String estado);
	OrigenIngreso activar(OrigenIngreso origenIngreso);
	OrigenIngreso inactivar(OrigenIngreso origenIngreso);
	List<OrigenIngreso> buscar(OrigenIngreso origenIngreso);
}
