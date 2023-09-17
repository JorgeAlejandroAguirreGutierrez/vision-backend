package com.proyecto.vision.servicios.interf.usuario;

import com.proyecto.vision.modelos.usuario.Paquete;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;

public interface IPaqueteService extends IGenericoService<Paquete> {
	void validar(Paquete paquete);
	Paquete activar(Paquete paquete);
	Paquete inactivar(Paquete paquete);
	List<Paquete> consultarPorEstado(String estado);
}