package com.proyecto.vision.servicios.interf.compra;

import com.proyecto.vision.modelos.compra.NotaDebitoCompra;
import com.proyecto.vision.modelos.compra.NotaDebitoCompraLinea;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;

public interface INotaDebitoCompraService extends IGenericoService<NotaDebitoCompra> {
	void validar(NotaDebitoCompra notaCreditoCompra);
	NotaDebitoCompra activar(NotaDebitoCompra notaCreditoCompra);
	NotaDebitoCompra inactivar(NotaDebitoCompra notaCreditoCompra);
	List<NotaDebitoCompra> consultarPorEstado(String estado);
	List<NotaDebitoCompra> consultarPorEmpresa(long empresaId);
	List<NotaDebitoCompra> consultarPorEmpresaYEstado(long empresaId, String estado);
	NotaDebitoCompra calcular(NotaDebitoCompra notaDebitoCompra);
	void validarLinea(NotaDebitoCompraLinea notaDebitoCompraLinea);
	NotaDebitoCompraLinea calcularLinea(NotaDebitoCompraLinea notaDebitoCompraLinea);
	NotaDebitoCompra obtenerPorFacturaCompra(long facturaCompraId);
}
