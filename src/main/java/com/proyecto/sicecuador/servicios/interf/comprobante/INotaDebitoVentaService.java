package com.proyecto.sicecuador.servicios.interf.comprobante;

import com.proyecto.sicecuador.modelos.comprobante.NotaDebitoVenta;
import com.proyecto.sicecuador.modelos.comprobante.NotaDebitoVentaLinea;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface INotaDebitoVentaService extends IGenericoService<NotaDebitoVenta> {
	void validar(NotaDebitoVenta notaDebitoVenta);
	NotaDebitoVenta activar(NotaDebitoVenta notaDebitoVenta);
	NotaDebitoVenta inactivar(NotaDebitoVenta notaDebitoVenta);
	List<NotaDebitoVenta> consultarActivos();
	NotaDebitoVenta calcular(NotaDebitoVenta notaDebitoVenta);
	void validarLinea(NotaDebitoVentaLinea notaDebitoVentaLinea);
	NotaDebitoVenta obtenerPorFactura(long facturaId);
}
