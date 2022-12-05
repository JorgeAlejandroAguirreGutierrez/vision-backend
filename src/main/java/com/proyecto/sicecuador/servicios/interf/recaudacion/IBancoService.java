package com.proyecto.sicecuador.servicios.interf.recaudacion;

import java.util.List;

import com.proyecto.sicecuador.modelos.recaudacion.Banco;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IBancoService extends IGenericoService<Banco> {
	Banco activar(Banco banco);
	Banco inactivar(Banco banco);
	List<Banco> consultarActivos();

}
