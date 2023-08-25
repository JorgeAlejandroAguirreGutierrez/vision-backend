package com.proyecto.vision.servicios.interf.compra;

import com.proyecto.vision.modelos.compra.NotaCreditoCompra;
import com.proyecto.vision.modelos.compra.NotaCreditoCompraLinea;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;

public interface INotaCreditoCompraService extends IGenericoService<NotaCreditoCompra> {
	void validar(NotaCreditoCompra notaCreditoCompra);
	NotaCreditoCompra anular(NotaCreditoCompra notaCreditoCompra);
	List<NotaCreditoCompra> consultarPorProceso(String proceso);
	List<NotaCreditoCompra> consultarPorEmpresa(long empresaId);
	List<NotaCreditoCompra> consultarPorEmpresaYProceso(long empresaId, String proceso);
	NotaCreditoCompra calcular(NotaCreditoCompra notaCreditoCompra);
	void validarLinea(NotaCreditoCompraLinea notaCreditoCompraLinea);
	NotaCreditoCompra obtenerPorFacturaCompra(long facturaCompraId);
}
