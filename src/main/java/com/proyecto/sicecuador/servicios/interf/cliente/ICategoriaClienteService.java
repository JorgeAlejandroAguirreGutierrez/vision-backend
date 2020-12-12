package com.proyecto.sicecuador.servicios.interf.cliente;

import java.util.List;

import com.proyecto.sicecuador.modelos.cliente.CategoriaCliente;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface ICategoriaClienteService extends IGenericoService<CategoriaCliente> {
	List<CategoriaCliente> buscar(CategoriaCliente categoria_cliente);
}
