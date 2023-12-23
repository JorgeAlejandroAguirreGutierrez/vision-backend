package com.proyecto.vision.servicios.interf.cliente;

import java.util.List;

import com.proyecto.vision.modelos.cliente.Segmento;
import com.proyecto.vision.servicios.interf.IGenericoService;

public interface ISegmentoService extends IGenericoService<Segmento> {
	void validar(Segmento segmento);
	List<Segmento> consultarPorEmpresa(long empresaId);
	List<Segmento> consultarPorEstado(String estado);
	List<Segmento> consultarPorEmpresaYEstado(long empresaId, String estado);
	Segmento activar(Segmento segmento);
	Segmento inactivar(Segmento segmento);

}
