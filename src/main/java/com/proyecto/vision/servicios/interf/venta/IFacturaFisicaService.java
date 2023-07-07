package com.proyecto.vision.servicios.interf.venta;

import java.io.ByteArrayInputStream;

import com.proyecto.vision.modelos.venta.Factura;

public interface IFacturaFisicaService {
	ByteArrayInputStream crearPDF(Factura factura);
}
