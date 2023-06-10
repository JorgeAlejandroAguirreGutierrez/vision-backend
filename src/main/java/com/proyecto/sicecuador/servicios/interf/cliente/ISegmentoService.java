package com.proyecto.sicecuador.servicios.interf.cliente;

import java.util.List;

import com.proyecto.sicecuador.modelos.cliente.Segmento;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface ISegmentoService extends IGenericoService<Segmento> {
	void validar(Segmento segmento);
	List<Segmento> consultarPorEmpresa(long empresaId);
	List<Segmento> consultarPorEstado(String estado);
	List<Segmento> consultarPorEmpresaYEstado(long empresaId, String estado);
	List<Segmento> buscar(Segmento segmento);
	Segmento activar(Segmento segmento);
	Segmento inactivar(Segmento segmento);
}
