package com.proyecto.sicecuador.servicios.interf.comprobante;

import com.proyecto.sicecuador.modelos.comprobante.NotaDebitoVenta;

public interface INotaDebitoElectronicaService {
	NotaDebitoVenta enviar(long notaDebitoVentaId);
}
