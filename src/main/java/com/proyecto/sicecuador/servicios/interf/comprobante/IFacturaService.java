package com.proyecto.sicecuador.servicios.interf.comprobante;

import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.modelos.comprobante.FacturaDetalle;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

public interface IFacturaService extends IGenericoService<Factura> {
	List<Factura> buscar(Factura factura);
	Optional<Factura> calcular(Factura factura);
	Optional<FacturaDetalle> calcularFacturaDetalleTemp(FacturaDetalle facturaDetalle);
    ByteArrayInputStream generarPDF(Factura factura);
}
