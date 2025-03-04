package com.proyecto.vision.servicios.interf.configuracion;

import com.proyecto.vision.modelos.configuracion.Impuesto;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;

public interface IImpuestoService extends IGenericoService<Impuesto> {
	void validar(Impuesto impuesto);
	Impuesto activar(Impuesto impuesto);
	Impuesto inactivar(Impuesto impuesto);
	List<Impuesto> consultarPorEstado(String estado);
    Impuesto obtenerPorPorcentajeYEstado(double porcentaje, String estado);
	Impuesto obtenerPorCodigoSRIYEstado(String codigoSri, String estado);
}
