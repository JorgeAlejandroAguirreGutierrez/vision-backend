package com.proyecto.vision.servicios.interf.venta;

import com.proyecto.vision.modelos.venta.NotaCreditoVenta;

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;

public interface INotaCreditoElectronicaService {
	NotaCreditoVenta enviar(long notaCreditoVentaId) throws MalformedURLException;
	ByteArrayInputStream obtenerPDF(long notaCreditoVentaId);
	void enviarPDFYXML(long notaCreditoVentaId);
}
