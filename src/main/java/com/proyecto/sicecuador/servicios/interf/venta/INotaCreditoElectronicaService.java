package com.proyecto.sicecuador.servicios.interf.venta;

import com.proyecto.sicecuador.modelos.venta.NotaCreditoVenta;

public interface INotaCreditoElectronicaService {
	NotaCreditoVenta enviar(long notaCreditoVentaId);
}
