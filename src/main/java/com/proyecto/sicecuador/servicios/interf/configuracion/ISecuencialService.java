package com.proyecto.sicecuador.servicios.interf.configuracion;

import java.util.List;

import com.proyecto.sicecuador.modelos.configuracion.Secuencial;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface ISecuencialService extends IGenericoService<Secuencial> {
	void validar(Secuencial secuencial);
	Secuencial activar(Secuencial secuencial);
	Secuencial inactivar(Secuencial secuencial);
	List<Secuencial> consultarActivos();
}
