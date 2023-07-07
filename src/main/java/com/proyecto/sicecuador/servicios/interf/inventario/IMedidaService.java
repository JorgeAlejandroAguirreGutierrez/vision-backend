package com.proyecto.vision.servicios.interf.inventario;

import java.util.List;

import com.proyecto.vision.modelos.inventario.Medida;
import com.proyecto.vision.servicios.interf.IGenericoService;

public interface IMedidaService extends IGenericoService<Medida> {
	void validar(Medida medida);
	Medida activar(Medida medida);
	Medida inactivar(Medida medida);
	List<Medida> consultarPorEstado(String estado);
	List<Medida> consultarPorEmpresa(long empresaId);
	List<Medida> consultarPorEmpresaYEstado(long empresaId, String estado);
}
