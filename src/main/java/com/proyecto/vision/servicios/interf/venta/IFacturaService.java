package com.proyecto.vision.servicios.interf.venta;

import com.proyecto.vision.modelos.venta.Factura;
import com.proyecto.vision.modelos.venta.FacturaLinea;
import com.proyecto.vision.servicios.interf.IGenericoService;
import java.util.List;

public interface IFacturaService extends IGenericoService<Factura> {
	void validar(Factura factura);
	Factura recaudar(Factura factura);
	Factura anular(Factura factura);
	List<Factura> consultarPorEstadoSRI(String estadoSRI);
	List<Factura> consultarPorEmpresa(long empresaId);
	List<Factura> consultarPorEmpresaYEstadoSRI(long empresaId, String estadoSRI);
	Factura calcular(Factura factura);
	void validarLinea(FacturaLinea facturaLinea);
	FacturaLinea calcularLinea(FacturaLinea facturaLinea);
	List<Factura> consultarPorCliente(long clienteId);
	List<Factura> consultarPorClienteYEstadoSRI(long clienteId, String estadoSRI);
	List<Factura> consultarPorClienteYEmpresaYProceso(long clienteId, long empresaId, String proceso);
	List<Factura> consultarPorClienteYEmpresaYEstadoSRI(long clienteId, long empresaId, String estadoSRI);
	String validarIdentificacion(String identificacion);
}
