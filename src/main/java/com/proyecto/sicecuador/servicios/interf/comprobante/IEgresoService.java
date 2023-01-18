package com.proyecto.sicecuador.servicios.interf.comprobante;

import java.util.List;

import com.proyecto.sicecuador.modelos.comprobante.Egreso;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IEgresoService extends IGenericoService<Egreso> {
	Egreso activar(Egreso egreso);
	Egreso inactivar(Egreso egreso);
	List<Egreso> consultarActivos();
}
