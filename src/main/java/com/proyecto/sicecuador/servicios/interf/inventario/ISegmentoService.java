package com.proyecto.sicecuador.servicios.interf.inventario;

import java.util.List;

import com.proyecto.sicecuador.modelos.inventario.Segmento;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface ISegmentoService extends IGenericoService<Segmento> {
	List<Segmento> buscar(Segmento segmento);
}
