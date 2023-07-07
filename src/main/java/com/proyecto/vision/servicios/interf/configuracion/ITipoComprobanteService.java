package com.proyecto.vision.servicios.interf.configuracion;

import com.proyecto.vision.modelos.configuracion.TipoComprobante;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;

public interface ITipoComprobanteService extends IGenericoService<TipoComprobante> {
    List<TipoComprobante> consultarPorElectronica();
    TipoComprobante obtenerPorNombreTabla(String nombreTabla);
}
