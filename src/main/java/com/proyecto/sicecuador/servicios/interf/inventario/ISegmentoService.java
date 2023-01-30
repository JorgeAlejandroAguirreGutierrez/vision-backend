package com.proyecto.sicecuador.servicios.interf.inventario;

import java.util.List;

import com.proyecto.sicecuador.modelos.inventario.Segmento;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface ISegmentoService extends IGenericoService<Segmento> {
	void validar(Segmento segmento);
	Segmento activar(Segmento segmento);
	Segmento inactivar(Segmento segmento);
	List<Segmento> consultarActivos();
	List<Segmento> buscar(Segmento segmento);
}
