package com.proyecto.vision.servicios.interf.entrega;

import java.util.List;

import com.proyecto.vision.modelos.entrega.Transportista;
import com.proyecto.vision.servicios.interf.IGenericoService;

public interface ITransportistaService extends IGenericoService<Transportista> {
	Transportista activar(Transportista transportista);
	Transportista inactivar(Transportista transportista);
	List<Transportista> consultarPorEstado(String estado);
	List<Transportista> consultarPorEmpresa(long empresaId);
	List<Transportista> consultarPorEmpresaYEstado(long empresaId, String estado);
}
