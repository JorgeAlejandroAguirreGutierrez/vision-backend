package com.proyecto.sicecuador.servicios.interf.comprobante;

import com.proyecto.sicecuador.modelos.comprobante.NotaCreditoVenta;

public interface INotaCreditoElectronicaService {
	NotaCreditoVenta enviar(long notaCreditoVentaId);
}
