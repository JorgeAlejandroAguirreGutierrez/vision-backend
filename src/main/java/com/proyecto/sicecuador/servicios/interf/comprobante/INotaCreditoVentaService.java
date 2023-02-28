package com.proyecto.sicecuador.servicios.interf.comprobante;

import com.proyecto.sicecuador.modelos.comprobante.NotaCreditoVenta;
import com.proyecto.sicecuador.modelos.comprobante.NotaCreditoVentaLinea;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface INotaCreditoVentaService extends IGenericoService<NotaCreditoVenta> {
	void validar(NotaCreditoVenta notaCreditoVenta);
	NotaCreditoVenta activar(NotaCreditoVenta notaCreditoVenta);
	NotaCreditoVenta inactivar(NotaCreditoVenta notaCreditoVenta);
	List<NotaCreditoVenta> consultarActivos();
	NotaCreditoVenta calcular(NotaCreditoVenta notaCreditoVenta);
	void validarLinea(NotaCreditoVentaLinea notaCreditoVentaLinea);
	NotaCreditoVenta obtenerPorFactura(long facturaId);
}
