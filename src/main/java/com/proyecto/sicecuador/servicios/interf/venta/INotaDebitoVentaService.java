package com.proyecto.vision.servicios.interf.venta;

import com.proyecto.vision.modelos.venta.NotaDebitoVenta;
import com.proyecto.vision.modelos.venta.NotaDebitoVentaLinea;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;

public interface INotaDebitoVentaService extends IGenericoService<NotaDebitoVenta> {
	void validar(NotaDebitoVenta notaDebitoVenta);
	NotaDebitoVenta activar(NotaDebitoVenta notaDebitoVenta);
	NotaDebitoVenta inactivar(NotaDebitoVenta notaDebitoVenta);
	List<NotaDebitoVenta> consultarPorEstado(String estado);
	List<NotaDebitoVenta> consultarPorEmpresa(long empresaId);
	List<NotaDebitoVenta> consultarPorEmpresaYEstado(long empresaId, String estado);
	NotaDebitoVenta calcular(NotaDebitoVenta notaDebitoVenta);
	void validarLinea(NotaDebitoVentaLinea notaDebitoVentaLinea);
	NotaDebitoVentaLinea calcularLinea(NotaDebitoVentaLinea notaDebitoVentaLinea);
	NotaDebitoVenta obtenerPorFactura(long facturaId);
	NotaDebitoVenta calcularRecaudacion(NotaDebitoVenta notaDebitoVenta);
}
