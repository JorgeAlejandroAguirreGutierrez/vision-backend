package com.proyecto.sicecuador.servicios.interf.comprobante;

import java.io.ByteArrayInputStream;

import com.proyecto.sicecuador.modelos.comprobante.Factura;

public interface IFacturaFisicaService {
	ByteArrayInputStream crearPDF(Factura factura);
}
