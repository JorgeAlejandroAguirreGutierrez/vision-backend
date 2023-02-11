package com.proyecto.sicecuador.servicios.interf.compra;

import com.proyecto.sicecuador.modelos.compra.FacturaCompraLinea;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IFacturaCompraLineaService extends IGenericoService<FacturaCompraLinea> {
	void validar(FacturaCompraLinea facturaCompraLinea);
	FacturaCompraLinea calcularLinea(FacturaCompraLinea facturaCompraLinea);
}
