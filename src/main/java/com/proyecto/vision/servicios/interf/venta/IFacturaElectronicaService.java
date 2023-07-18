package com.proyecto.vision.servicios.interf.venta;

import com.proyecto.vision.modelos.venta.Factura;

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;

public interface IFacturaElectronicaService {
	Factura enviar(long facturaId) throws MalformedURLException;
	ByteArrayInputStream obtenerPDF(long facturaId);
	void enviarPDFYXML(long facturaId);
	ByteArrayInputStream obtenerTicket(long facturaId);
}
