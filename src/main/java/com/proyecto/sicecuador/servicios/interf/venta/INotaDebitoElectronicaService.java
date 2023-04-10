package com.proyecto.sicecuador.servicios.interf.venta;

import com.proyecto.sicecuador.modelos.venta.NotaDebitoVenta;

public interface INotaDebitoElectronicaService {
	NotaDebitoVenta enviar(long notaDebitoVentaId);
}
