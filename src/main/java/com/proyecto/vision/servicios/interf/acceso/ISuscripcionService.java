package com.proyecto.vision.servicios.interf.acceso;

import com.proyecto.vision.modelos.acceso.Suscripcion;
import com.proyecto.vision.servicios.interf.IGenericoService;
import java.util.List;

public interface ISuscripcionService extends IGenericoService<Suscripcion> {
	void validar(Suscripcion suscripcion);
	Suscripcion activar(Suscripcion suscripcion);
	Suscripcion inactivar(Suscripcion suscripcion);
	boolean verificar(long empresaId);
	Suscripcion aumentarConteo(long empresaId);
	Suscripcion renovar(long empresaId);
	List<Suscripcion> consultarPorEstado(String estado);
	List<Suscripcion> consultarPorEmpresa(long empresaId);
	List<Suscripcion> consultarPorEmpresaYEstado(long empresaId, String estado);
	Suscripcion obtenerUltimoPorEmpresa(long empresaId);
}
