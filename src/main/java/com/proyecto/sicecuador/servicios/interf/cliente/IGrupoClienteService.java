package com.proyecto.sicecuador.servicios.interf.cliente;

import com.proyecto.sicecuador.modelos.cliente.GrupoCliente;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;
import java.util.List;

public interface IGrupoClienteService extends IGenericoService<GrupoCliente> {
	GrupoCliente activar(GrupoCliente grupoCliente);
	GrupoCliente inactivar(GrupoCliente grupoCliente);
	List<GrupoCliente> consultarActivos();
	List<GrupoCliente> buscar(GrupoCliente grupoCliente);
}
