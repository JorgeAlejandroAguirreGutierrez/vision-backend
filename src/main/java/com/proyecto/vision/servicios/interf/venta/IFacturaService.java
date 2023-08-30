package com.proyecto.vision.servicios.interf.venta;

import com.proyecto.vision.modelos.venta.Factura;
import com.proyecto.vision.modelos.venta.FacturaLinea;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.Date;
import java.util.List;

public interface IFacturaService extends IGenericoService<Factura> {
	void validar(Factura factura);
	Factura recaudar(Factura factura);
	Factura anular(Factura factura);
	List<Factura> consultarPorEstado(String estado);
	List<Factura> consultarPorProcesoSRI(String procesoSRI);
	List<Factura> consultarPorEmpresa(long empresaId);
	List<Factura> consultarPorEmpresaYEstado(long empresaId, String estado);
	Factura calcular(Factura factura);
	void validarLinea(FacturaLinea facturaLinea);
	FacturaLinea calcularLinea(FacturaLinea facturaLinea);
	List<Factura> consultarPorCliente(long clienteId);
	List<Factura> consultarPorClienteYEstado(long clienteId, String estado);
	List<Factura> consultarPorClienteYEmpresaYEstado(long clienteId, long empresaId, String estado);
	String validarIdentificacion(String identificacion);
	List<Factura> consultarPorFechaYEmpresaYEstadoEmitidaYEstadoRecaudada(Date fecha, long empresaId, String estadoEmitida, String recaudada);
}