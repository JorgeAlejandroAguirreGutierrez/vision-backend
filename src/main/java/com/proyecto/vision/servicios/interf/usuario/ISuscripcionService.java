package com.proyecto.vision.servicios.interf.usuario;

import com.proyecto.vision.modelos.usuario.Suscripcion;
import com.proyecto.vision.servicios.interf.IGenericoService;
import java.util.List;

public interface ISuscripcionService extends IGenericoService<Suscripcion> {
	void validar(Suscripcion suscripcion);
	Suscripcion activar(Suscripcion suscripcion);
	Suscripcion inactivar(Suscripcion suscripcion);
	List<Suscripcion> consultarPorEstado(String estado);
	List<Suscripcion> consultarPorEmpresaYEstado(long empresaId, String estado);
}
