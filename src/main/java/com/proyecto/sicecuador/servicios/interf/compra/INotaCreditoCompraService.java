package com.proyecto.sicecuador.servicios.interf.compra;

import com.proyecto.sicecuador.modelos.compra.NotaCreditoCompra;
import com.proyecto.sicecuador.modelos.compra.NotaCreditoCompraLinea;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface INotaCreditoCompraService extends IGenericoService<NotaCreditoCompra> {
	void validar(NotaCreditoCompra notaCreditoCompra);
	NotaCreditoCompra activar(NotaCreditoCompra notaCreditoCompra);
	NotaCreditoCompra inactivar(NotaCreditoCompra notaCreditoCompra);
	List<NotaCreditoCompra> consultarPorEstado(String estado);
	List<NotaCreditoCompra> consultarPorEmpresa(long empresaId);
	List<NotaCreditoCompra> consultarPorEmpresaYEstado(long empresaId, String estado);
	NotaCreditoCompra calcular(NotaCreditoCompra notaCreditoCompra);
	void validarLinea(NotaCreditoCompraLinea notaCreditoCompraLinea);
	NotaCreditoCompra obtenerPorFacturaCompra(long facturaCompraId);
}
