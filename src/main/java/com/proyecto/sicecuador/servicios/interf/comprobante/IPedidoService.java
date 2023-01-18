package com.proyecto.sicecuador.servicios.interf.comprobante;

import java.util.List;

import com.proyecto.sicecuador.modelos.comprobante.Pedido;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IPedidoService extends IGenericoService<Pedido> {
	Pedido activar(Pedido pedido);
	Pedido inactivar(Pedido pedido);
	List<Pedido> consultarActivos();
}
