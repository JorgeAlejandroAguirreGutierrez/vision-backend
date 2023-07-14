package com.proyecto.vision.servicios.interf.venta;

import com.proyecto.vision.modelos.venta.NotaDebitoVenta;

import java.io.ByteArrayInputStream;

public interface INotaDebitoElectronicaService {
	NotaDebitoVenta enviar(long notaDebitoVentaId);
	ByteArrayInputStream obtenerPDF(long notaDebitoVentaId);
	void enviarPDFYXML(long notaDebitoVentaId);
}
