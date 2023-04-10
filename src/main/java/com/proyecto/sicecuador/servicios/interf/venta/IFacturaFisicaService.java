package com.proyecto.sicecuador.servicios.interf.venta;

import java.io.ByteArrayInputStream;

import com.proyecto.sicecuador.modelos.venta.Factura;

public interface IFacturaFisicaService {
	ByteArrayInputStream crearPDF(Factura factura);
}
