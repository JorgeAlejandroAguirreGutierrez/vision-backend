package com.proyecto.vision.servicios.interf.acceso;

import com.proyecto.vision.modelos.acceso.Paquete;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;

public interface IPaqueteService extends IGenericoService<Paquete> {
	Paquete activar(Paquete paquete);
	Paquete inactivar(Paquete paquete);
	List<Paquete> consultarPorEstado(String estado);
	Paquete calcular(Paquete paquete);
}
