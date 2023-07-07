package com.proyecto.vision.servicios.interf.venta;

import com.proyecto.vision.modelos.venta.Factura;

import java.io.ByteArrayInputStream;

public interface IFacturaElectronicaService {
	Factura enviar(long facturaId);
	ByteArrayInputStream obtenerPDF(long facturaId);
	void enviarPDFYXML(long facturaId);
}
