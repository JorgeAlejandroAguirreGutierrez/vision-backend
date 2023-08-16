package com.proyecto.vision.servicios.interf.venta;

import com.proyecto.vision.modelos.venta.NotaDebito;

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;

public interface INotaDebitoElectronicaService {
	NotaDebito enviar(long notaDebitoVentaId) throws MalformedURLException;
	ByteArrayInputStream obtenerPDF(long notaDebitoVentaId);
	void enviarPDFYXML(long notaDebitoVentaId);
}
