package com.proyecto.vision.servicios.interf.entrega;

import java.util.List;
import java.util.Optional;

import com.proyecto.vision.modelos.entrega.GuiaRemision;
import com.proyecto.vision.servicios.interf.IGenericoService;

public interface IGuiaRemisionService extends IGenericoService<GuiaRemision> {
	Optional<GuiaRemision> obtenerPorFactura(long facturaId);
	void validar(GuiaRemision guiaRemision);
	GuiaRemision activar(GuiaRemision guiaRemision);
	GuiaRemision inactivar(GuiaRemision guiaRemision);
}
