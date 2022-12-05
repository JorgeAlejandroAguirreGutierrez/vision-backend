package com.proyecto.sicecuador.servicios.interf.entrega;

import com.proyecto.sicecuador.modelos.entrega.Entrega;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IEntregaService extends IGenericoService<Entrega> {
	Entrega obtenerPorFactura(long facturaId);
}
