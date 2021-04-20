package com.proyecto.sicecuador.servicios.interf.comprobante;

import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;
import java.io.ByteArrayInputStream;
import java.util.List;

public interface IFacturaService extends IGenericoService<Factura> {
    List<Factura> consultarClienteRazonSocial(Factura factura);
    List<Factura> consultarNumero(Factura factura);
    ByteArrayInputStream generarPDF(Factura factura);
}
