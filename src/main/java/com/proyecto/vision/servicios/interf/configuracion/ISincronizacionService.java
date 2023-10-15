package com.proyecto.vision.servicios.interf.configuracion;

import com.proyecto.vision.modelos.compra.FacturaCompra;
import com.proyecto.vision.modelos.configuracion.Sincronizacion;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;

public interface ISincronizacionService extends IGenericoService<Sincronizacion> {
	void validar(Sincronizacion sincronizacion);
	List<Sincronizacion> consultarPorEstado(String estado);
	List<FacturaCompra> procesar(long sincronizacionId);
}
