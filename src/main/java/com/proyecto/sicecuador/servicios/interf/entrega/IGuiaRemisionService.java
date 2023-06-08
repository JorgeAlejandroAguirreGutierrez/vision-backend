package com.proyecto.sicecuador.servicios.interf.entrega;

import java.util.List;
import java.util.Optional;

import com.proyecto.sicecuador.modelos.entrega.GuiaRemision;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IGuiaRemisionService extends IGenericoService<GuiaRemision> {
	Optional<GuiaRemision> obtenerPorFactura(long facturaId);
	void validar(GuiaRemision guiaRemision);
	GuiaRemision activar(GuiaRemision guiaRemision);
	GuiaRemision inactivar(GuiaRemision guiaRemision);
}
