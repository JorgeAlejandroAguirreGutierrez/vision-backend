package com.proyecto.sicecuador.servicios.interf.comprobante;

import com.proyecto.sicecuador.modelos.comprobante.Factura;

public interface IFacturaElectronicaService {
	Factura enviar(Factura factura);
}
