package com.proyecto.sicecuador.servicios.interf.cliente;

import java.util.List;
import com.proyecto.sicecuador.modelos.cliente.EstadoCivil;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IEstadoCivilService extends IGenericoService<EstadoCivil> {
	List<EstadoCivil> buscar(EstadoCivil estado_civil);
}
