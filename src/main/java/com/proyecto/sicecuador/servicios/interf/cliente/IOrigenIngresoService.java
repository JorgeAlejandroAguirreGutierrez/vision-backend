package com.proyecto.sicecuador.servicios.interf.cliente;

import java.util.List;

import com.proyecto.sicecuador.modelos.cliente.OrigenIngreso;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IOrigenIngresoService extends IGenericoService<OrigenIngreso> {
	void validar(OrigenIngreso origenIngreso);
	OrigenIngreso activar(OrigenIngreso origenIngreso);
	OrigenIngreso inactivar(OrigenIngreso origenIngreso);
	List<OrigenIngreso> consultarPorEstado(String estado);
	List<OrigenIngreso> buscar(OrigenIngreso origenIngreso);
}
