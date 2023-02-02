package com.proyecto.sicecuador.servicios.interf.comprobante;

import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;
import java.util.List;

public interface IFacturaService extends IGenericoService<Factura> {
	void validar(Factura factura);
	Factura activar(Factura factura);
	Factura inactivar(Factura factura);
	List<Factura> consultarActivos();
	List<Factura> buscar(Factura factura);
	Factura calcular(Factura factura);
}
