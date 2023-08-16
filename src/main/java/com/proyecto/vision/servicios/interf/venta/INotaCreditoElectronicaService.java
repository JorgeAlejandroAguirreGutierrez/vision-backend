package com.proyecto.vision.servicios.interf.venta;

import com.proyecto.vision.modelos.venta.NotaCredito;

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;

public interface INotaCreditoElectronicaService {
	NotaCredito enviar(long notaCreditoVentaId) throws MalformedURLException;
	ByteArrayInputStream obtenerPDF(long notaCreditoVentaId);
	void enviarPDFYXML(long notaCreditoVentaId);
}
