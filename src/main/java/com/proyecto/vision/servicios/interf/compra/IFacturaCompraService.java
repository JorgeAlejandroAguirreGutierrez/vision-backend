package com.proyecto.vision.servicios.interf.compra;

import com.proyecto.vision.modelos.compra.FacturaCompra;
import com.proyecto.vision.modelos.compra.FacturaCompraLinea;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;

public interface IFacturaCompraService extends IGenericoService<FacturaCompra> {
	void validar(FacturaCompra facturaCompra);
	FacturaCompra activar(FacturaCompra facturaCompra);
	FacturaCompra inactivar(FacturaCompra facturaCompra);
	List<FacturaCompra> consultarPorEstado(String estado);
	List<FacturaCompra> consultarPorEmpresa(long empresaId);
	List<FacturaCompra> consultarPorEmpresaYEstadoInternoYEstado(long empresaId, String estadoInterno, String estado);
	List<FacturaCompra> consultarPorProveedorYEmpresaYEstadoInternoYEstado(long proveedorId, long empresaId, String estadoInterno, String estado);
	FacturaCompra calcular(FacturaCompra facturaCompra);
	void validarLinea(FacturaCompraLinea facturaCompraLinea);
	FacturaCompraLinea calcularLinea(FacturaCompraLinea facturaCompraLinea);
	FacturaCompra pagar(long facturaCompraId);
}
