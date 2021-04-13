package com.proyecto.sicecuador.servicios.interf.cliente;

import com.proyecto.sicecuador.modelos.cliente.GrupoCliente;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;
import java.util.List;

public interface IGrupoClienteService extends IGenericoService<GrupoCliente> {
    List<GrupoCliente> buscar(GrupoCliente grupo_cliente);
}
