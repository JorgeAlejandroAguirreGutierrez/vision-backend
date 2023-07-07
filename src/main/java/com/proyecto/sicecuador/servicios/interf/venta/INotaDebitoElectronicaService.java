package com.proyecto.vision.servicios.interf.venta;

import com.proyecto.vision.modelos.venta.NotaDebitoVenta;

public interface INotaDebitoElectronicaService {
	NotaDebitoVenta enviar(long notaDebitoVentaId);
}
