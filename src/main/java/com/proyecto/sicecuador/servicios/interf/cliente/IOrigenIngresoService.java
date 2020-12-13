package com.proyecto.sicecuador.servicios.interf.cliente;

import java.util.List;

import com.proyecto.sicecuador.modelos.cliente.OrigenIngreso;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IOrigenIngresoService extends IGenericoService<OrigenIngreso> {
	List<OrigenIngreso> buscar(OrigenIngreso origen_ingreso);
}
