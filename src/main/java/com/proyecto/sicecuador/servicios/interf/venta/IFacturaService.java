package com.proyecto.sicecuador.servicios.interf.venta;

import com.proyecto.sicecuador.modelos.venta.Factura;
import com.proyecto.sicecuador.modelos.venta.FacturaLinea;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;
import java.util.List;

public interface IFacturaService extends IGenericoService<Factura> {
	void validar(Factura factura);
	Factura recaudar(Factura factura);
	Factura activar(Factura factura);
	Factura inactivar(Factura factura);
	List<Factura> consultarActivos();
	List<Factura> buscar(Factura factura);
	Factura calcular(Factura factura);
	void validarLinea(FacturaLinea facturaLinea);
	FacturaLinea calcularLinea(FacturaLinea facturaLinea);
	List<Factura> consultarPorCliente(long clienteId);
	Factura calcularRecaudacion(Factura factura);
}
