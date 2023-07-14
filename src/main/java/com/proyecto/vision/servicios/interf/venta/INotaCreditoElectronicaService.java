package com.proyecto.vision.servicios.interf.venta;

import com.proyecto.vision.modelos.venta.NotaCreditoVenta;

import java.io.ByteArrayInputStream;

public interface INotaCreditoElectronicaService {
	NotaCreditoVenta enviar(long notaCreditoVentaId);
	ByteArrayInputStream obtenerPDF(long notaCreditoVentaId);
	void enviarPDFYXML(long notaCreditoVentaId);
}
