package com.proyecto.sicecuador.servicios.interf.cliente;

import com.proyecto.sicecuador.modelos.cliente.GrupoCliente;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;
import java.util.List;

public interface IGrupoClienteService extends IGenericoService<GrupoCliente> {
	void validar(GrupoCliente grupoCliente);
	List<GrupoCliente> consultarPorEstado(String estado);
	List<GrupoCliente> consultarPorEmpresa(long empresaId);
	List<GrupoCliente> consultarPorEmpresaYEstado(long empresaId, String estado);
	GrupoCliente activar(GrupoCliente grupoCliente);
	GrupoCliente inactivar(GrupoCliente grupoCliente);
	List<GrupoCliente> buscar(GrupoCliente grupoCliente);
}
