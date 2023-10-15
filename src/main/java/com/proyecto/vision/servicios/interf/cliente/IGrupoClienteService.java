package com.proyecto.vision.servicios.interf.cliente;

import com.proyecto.vision.modelos.cliente.Cliente;
import com.proyecto.vision.modelos.cliente.GrupoCliente;
import com.proyecto.vision.servicios.interf.IGenericoService;
import java.util.List;

public interface IGrupoClienteService extends IGenericoService<GrupoCliente> {
	void validar(GrupoCliente grupoCliente);
	List<GrupoCliente> consultarPorEmpresa(long empresaId);
	List<GrupoCliente> consultarPorEstado(String estado);
	List<GrupoCliente> consultarPorEmpresaYEstado(long empresaId, String estado);
	GrupoCliente activar(GrupoCliente grupoCliente);
	GrupoCliente inactivar(GrupoCliente grupoCliente);
}
