package com.proyecto.sicecuador.servicios.interf.entrega;

import java.util.Optional;

import com.proyecto.sicecuador.modelos.entrega.Entrega;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IEntregaService extends IGenericoService<Entrega> {
	public Optional<Entrega> obtenerPorFactura(long facturaId);
}
