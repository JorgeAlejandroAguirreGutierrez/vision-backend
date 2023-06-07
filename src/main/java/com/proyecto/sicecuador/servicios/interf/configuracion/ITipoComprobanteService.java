package com.proyecto.sicecuador.servicios.interf.configuracion;

import com.proyecto.sicecuador.modelos.configuracion.TipoComprobante;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface ITipoComprobanteService extends IGenericoService<TipoComprobante> {
    List<TipoComprobante> consultarPorEstado(String estado);
    List<TipoComprobante> consultarElectronica();
    TipoComprobante obtenerPorNombreTabla(String nombreTabla);
}
