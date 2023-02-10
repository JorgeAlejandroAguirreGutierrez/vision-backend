package com.proyecto.sicecuador.servicios.interf.compra;

import com.proyecto.sicecuador.modelos.compra.FacturaCompra;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface IFacturaCompraService extends IGenericoService<FacturaCompra> {
	void validar(FacturaCompra facturaCompra);
	FacturaCompra activar(FacturaCompra facturaCompra);
	FacturaCompra inactivar(FacturaCompra facturaCompra);
	List<FacturaCompra> consultarActivos();
	FacturaCompra calcular(FacturaCompra facturaCompra);
}
