package com.proyecto.vision.servicios.interf.venta;

import com.proyecto.vision.modelos.venta.NotaDebito;
import com.proyecto.vision.modelos.venta.NotaDebitoLinea;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;

public interface INotaDebitoService extends IGenericoService<NotaDebito> {
	void validar(NotaDebito notaDebito);
	NotaDebito anular(NotaDebito notaDebito);
	List<NotaDebito> consultarPorEstadoSRI(String estadoSRI);
	List<NotaDebito> consultarPorEmpresa(long empresaId);
	List<NotaDebito> consultarPorEmpresaYEstadoSRI(long empresaId, String estadoSRI);
	List<NotaDebito> consultarPorFacturaYEmpresaYNoIgualEstadoSRI(long facturaId, long empresaId, String estadoSRI);
	NotaDebito calcular(NotaDebito notaDebito);
	void validarLinea(NotaDebitoLinea notaDebitoLinea);
	NotaDebitoLinea calcularLinea(NotaDebitoLinea notaDebitoLinea);
	NotaDebito obtenerPorFactura(long facturaId);
	NotaDebito recaudar(NotaDebito notaDebito);
	String validarIdentificacion(String identificacion);
}
