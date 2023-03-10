package com.proyecto.sicecuador.servicios.interf.compra;

import com.proyecto.sicecuador.modelos.compra.NotaDebitoCompra;
import com.proyecto.sicecuador.modelos.compra.NotaDebitoCompraLinea;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface INotaDebitoCompraService extends IGenericoService<NotaDebitoCompra> {
	void validar(NotaDebitoCompra notaCreditoCompra);
	NotaDebitoCompra activar(NotaDebitoCompra notaCreditoCompra);
	NotaDebitoCompra inactivar(NotaDebitoCompra notaCreditoCompra);
	List<NotaDebitoCompra> consultarActivos();
	NotaDebitoCompra calcular(NotaDebitoCompra notaDebitoCompra);
	void validarLinea(NotaDebitoCompraLinea notaDebitoCompraLinea);
	NotaDebitoCompra obtenerPorFacturaCompra(long facturaCompraId);
}
