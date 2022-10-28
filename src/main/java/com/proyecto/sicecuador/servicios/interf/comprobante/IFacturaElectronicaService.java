package com.proyecto.sicecuador.servicios.interf.comprobante;

import com.proyecto.sicecuador.modelos.comprobante.Factura;

import java.util.Optional;

public interface IFacturaElectronicaService {
	Optional<String> enviarSri(Factura factura);
	Optional<String> enviarCorreo(Factura factura);
}
