package com.proyecto.sicecuador.servicios.interf.inventario;

import com.proyecto.sicecuador.modelos.inventario.Impuesto;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;
import java.util.Optional;

public interface IImpuestoService extends IGenericoService<Impuesto> {
	void validar(Impuesto impuesto);
	Impuesto activar(Impuesto impuesto);
	Impuesto inactivar(Impuesto impuesto);
	List<Impuesto> consultarActivos();
    Optional<Impuesto> obtenerImpuestoPorcentaje(Impuesto impuesto);
}
