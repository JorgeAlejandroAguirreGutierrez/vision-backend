package com.proyecto.sicecuador.servicios.interf.comprobante;

import com.proyecto.sicecuador.modelos.comprobante.NotaDebitoVenta;

public interface INotaDebitoVentaElectronicaService {
	NotaDebitoVenta enviar(long notaDebitoVentaId);
}
