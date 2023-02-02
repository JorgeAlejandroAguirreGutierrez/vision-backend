package com.proyecto.sicecuador.servicios.interf.recaudacion;

import com.proyecto.sicecuador.modelos.recaudacion.Recaudacion;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IRecaudacionService extends IGenericoService<Recaudacion> {
	void validar(Recaudacion recaudacion);
	Recaudacion obtenerPorFactura(long facturaId);
	Recaudacion calcular(Recaudacion recaudacion);
}
