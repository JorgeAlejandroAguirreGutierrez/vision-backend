package com.proyecto.sicecuador.servicios.interf.comprobante;

import com.proyecto.sicecuador.modelos.comprobante.Factura;

import java.io.ByteArrayInputStream;

public interface IFacturaElectronicaService {
	Factura enviar(long facturaId);
	ByteArrayInputStream obtenerPDF(long facturaId);
}
