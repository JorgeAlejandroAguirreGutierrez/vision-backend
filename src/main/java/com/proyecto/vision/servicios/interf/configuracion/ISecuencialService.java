package com.proyecto.vision.servicios.interf.configuracion;

import java.util.List;

import com.proyecto.vision.modelos.configuracion.Secuencial;
import com.proyecto.vision.servicios.interf.IGenericoService;

public interface ISecuencialService extends IGenericoService<Secuencial> {
	void validar(Secuencial secuencial);
	Secuencial activar(Secuencial secuencial);
	Secuencial inactivar(Secuencial secuencial);
	List<Secuencial> consultarPorEstado(String estado);
	Secuencial obtenerPorTipoComprobanteYEstacionYEmpresaYEstado(long tipoComprobanteId, long estacionId, long empresaId, String estado);
}
