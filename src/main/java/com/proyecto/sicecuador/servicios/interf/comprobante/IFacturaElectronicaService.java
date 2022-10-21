package com.proyecto.sicecuador.servicios.interf.comprobante;

import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.modelos.comprobante.facturacionelectronica.factura.FacturaElectronicaRespuesta;

import java.util.Optional;

public interface IFacturaElectronicaService {
	Optional<FacturaElectronicaRespuesta> crear(Factura factura);
}
