package com.proyecto.sicecuador.servicios.interf.comprobante;

import com.proyecto.sicecuador.modelos.comprobante.TipoComprobante;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface ITipoComprobanteService extends IGenericoService<TipoComprobante> {
    TipoComprobante obtenerPorNombreTabla(String nombreTabla);
}
