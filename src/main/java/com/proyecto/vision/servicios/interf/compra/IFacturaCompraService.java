package com.proyecto.vision.servicios.interf.compra;

import com.proyecto.vision.modelos.compra.FacturaCompra;
import com.proyecto.vision.modelos.compra.FacturaCompraLinea;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;

public interface IFacturaCompraService extends IGenericoService<FacturaCompra> {
	void validar(FacturaCompra facturaCompra);
	FacturaCompra anular(FacturaCompra facturaCompra);
	List<FacturaCompra> consultarPorProceso(String estado);
	List<FacturaCompra> consultarPorEmpresa(long empresaId);
	List<FacturaCompra> consultarPorEmpresaYProceso(long empresaId, String proceso);
	List<FacturaCompra> consultarPorEmpresaYProveedorYProceso(long empresaId, long proveedorId, String proceso);
	FacturaCompra calcular(FacturaCompra facturaCompra);
	void validarLinea(FacturaCompraLinea facturaCompraLinea);
	FacturaCompraLinea calcularLinea(FacturaCompraLinea facturaCompraLinea);
	FacturaCompra pagar(long facturaCompraId);
}