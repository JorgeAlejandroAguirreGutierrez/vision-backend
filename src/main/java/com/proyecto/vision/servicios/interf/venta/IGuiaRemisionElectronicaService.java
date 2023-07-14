package com.proyecto.vision.servicios.interf.venta;

import com.proyecto.vision.modelos.entrega.GuiaRemision;

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;

public interface IGuiaRemisionElectronicaService {
	GuiaRemision enviar(long guiaRemisionId) throws MalformedURLException;
	ByteArrayInputStream obtenerPDF(long facturaId);
	void enviarPDFYXML(long facturaId);
}
