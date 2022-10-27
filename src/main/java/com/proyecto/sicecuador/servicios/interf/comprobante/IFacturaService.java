package com.proyecto.sicecuador.servicios.interf.comprobante;

import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;
import java.util.List;
import java.util.Optional;

public interface IFacturaService extends IGenericoService<Factura> {
	List<Factura> buscar(Factura factura);
	Optional<Factura> calcular(Factura factura);
}
