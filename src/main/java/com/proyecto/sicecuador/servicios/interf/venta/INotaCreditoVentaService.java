package com.proyecto.sicecuador.servicios.interf.venta;

import com.proyecto.sicecuador.modelos.venta.NotaCreditoVenta;
import com.proyecto.sicecuador.modelos.venta.NotaCreditoVentaLinea;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface INotaCreditoVentaService extends IGenericoService<NotaCreditoVenta> {
	void validar(NotaCreditoVenta notaCreditoVenta);
	NotaCreditoVenta activar(NotaCreditoVenta notaCreditoVenta);
	NotaCreditoVenta inactivar(NotaCreditoVenta notaCreditoVenta);
	List<NotaCreditoVenta> consultarPorEstado(String estado);
	List<NotaCreditoVenta> consultarPorEmpresa(long empresaId);
	List<NotaCreditoVenta> consultarPorEmpresaYEstado(long empresaId, String estado);
	NotaCreditoVenta calcular(NotaCreditoVenta notaCreditoVenta);
	void validarLinea(NotaCreditoVentaLinea notaCreditoVentaLinea);
	NotaCreditoVenta obtenerPorFactura(long facturaId);
}
