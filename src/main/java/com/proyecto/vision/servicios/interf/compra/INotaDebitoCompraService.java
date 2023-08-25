package com.proyecto.vision.servicios.interf.compra;

import com.proyecto.vision.modelos.compra.NotaDebitoCompra;
import com.proyecto.vision.modelos.compra.NotaDebitoCompraLinea;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;

public interface INotaDebitoCompraService extends IGenericoService<NotaDebitoCompra> {
	void validar(NotaDebitoCompra notaCreditoCompra);
	NotaDebitoCompra anular(NotaDebitoCompra notaCreditoCompra);
	List<NotaDebitoCompra> consultarPorProceso(String estado);
	List<NotaDebitoCompra> consultarPorEmpresa(long empresaId);
	List<NotaDebitoCompra> consultarPorEmpresaYProceso(long empresaId, String estado);
	NotaDebitoCompra calcular(NotaDebitoCompra notaDebitoCompra);
	void validarLinea(NotaDebitoCompraLinea notaDebitoCompraLinea);
	NotaDebitoCompraLinea calcularLinea(NotaDebitoCompraLinea notaDebitoCompraLinea);
	NotaDebitoCompra obtenerPorFacturaCompra(long facturaCompraId);
}
