package com.proyecto.vision.servicios.interf.venta;

import com.proyecto.vision.modelos.venta.Factura;
import com.proyecto.vision.modelos.venta.FacturaLinea;
import com.proyecto.vision.servicios.interf.IGenericoService;
import java.util.List;

public interface IFacturaService extends IGenericoService<Factura> {
	void validar(Factura factura);
	Factura recaudar(Factura factura);
	Factura activar(Factura factura);
	Factura inactivar(Factura factura);
	List<Factura> consultarPorEstado(String estado);
	List<Factura> consultarPorEmpresa(long empresaId);
	List<Factura> consultarPorEmpresaYEstado(long empresaId, String estado);
	List<Factura> buscar(Factura factura);
	Factura calcular(Factura factura);
	void validarLinea(FacturaLinea facturaLinea);
	FacturaLinea calcularLinea(FacturaLinea facturaLinea);
	List<Factura> consultarPorClienteYEstadoSriYEstadoInternoYEstado(long clienteId, String estadoSri, String estadoInterno, String estado);
	String validarIdentificacion(String identificacion);
	Factura calcularRecaudacion(Factura factura);
}
