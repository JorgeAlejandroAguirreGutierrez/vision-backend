package com.proyecto.sicecuador.servicios.interf.entrega;

import java.util.List;

import com.proyecto.sicecuador.modelos.entrega.Transportista;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface ITransportistaService extends IGenericoService<Transportista> {
	Transportista activar(Transportista transportista);
	Transportista inactivar(Transportista transportista);
	List<Transportista> consultarPorEstado(String estado);
	List<Transportista> consultarPorEmpresa(long empresaId);
	List<Transportista> consultarPorEmpresaYEstado(long empresaId, String estado);
}
