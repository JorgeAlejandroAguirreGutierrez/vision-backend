package com.proyecto.vision.servicios.interf.compra;

import com.proyecto.vision.modelos.compra.NotaCreditoCompra;
import com.proyecto.vision.modelos.compra.NotaCreditoCompraLinea;
import com.proyecto.vision.servicios.interf.IGenericoService;

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
