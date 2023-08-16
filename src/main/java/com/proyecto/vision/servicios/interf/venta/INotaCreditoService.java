package com.proyecto.vision.servicios.interf.venta;

import com.proyecto.vision.modelos.venta.NotaCredito;
import com.proyecto.vision.modelos.venta.NotaCreditoLinea;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;

public interface INotaCreditoService extends IGenericoService<NotaCredito> {
	void validar(NotaCredito notaCredito);
	NotaCredito activar(NotaCredito notaCredito);
	NotaCredito inactivar(NotaCredito notaCredito);
	List<NotaCredito> consultarPorEstado(String estado);
	List<NotaCredito> consultarPorEmpresa(long empresaId);
	List<NotaCredito> consultarPorEmpresaYEstado(long empresaId, String estado);
	NotaCredito calcular(NotaCredito notaCredito);
	void validarLinea(NotaCreditoLinea notaCreditoLinea);
	NotaCredito obtenerPorFactura(long facturaId);
}