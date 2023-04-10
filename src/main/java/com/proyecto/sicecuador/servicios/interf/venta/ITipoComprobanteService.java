package com.proyecto.sicecuador.servicios.interf.venta;

import com.proyecto.sicecuador.modelos.venta.TipoComprobante;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface ITipoComprobanteService extends IGenericoService<TipoComprobante> {
    TipoComprobante obtenerPorNombreTabla(String nombreTabla);
}
