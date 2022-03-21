package com.proyecto.sicecuador.servicios.interf.recaudacion;

import java.util.Optional;

import com.proyecto.sicecuador.modelos.recaudacion.Recaudacion;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IRecaudacionService extends IGenericoService<Recaudacion> {
	public Optional<Recaudacion> obtenerPorFactura(long facturaId);
	public Optional<Recaudacion> calcular(Recaudacion recaudacion);
}
