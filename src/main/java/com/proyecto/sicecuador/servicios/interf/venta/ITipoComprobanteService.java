package com.proyecto.sicecuador.servicios.interf.venta;

import com.proyecto.sicecuador.modelos.venta.TipoComprobante;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface ITipoComprobanteService extends IGenericoService<TipoComprobante> {

    List<TipoComprobante> consultarActivos();
    List<TipoComprobante> consultarElectronica();
    TipoComprobante obtenerPorNombreTabla(String nombreTabla);
}
