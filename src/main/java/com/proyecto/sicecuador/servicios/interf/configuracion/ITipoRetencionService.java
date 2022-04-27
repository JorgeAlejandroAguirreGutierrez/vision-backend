package com.proyecto.sicecuador.servicios.interf.configuracion;

import com.proyecto.sicecuador.modelos.configuracion.TipoRetencion;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface ITipoRetencionService extends IGenericoService<TipoRetencion> {
    List<TipoRetencion> consultarIvaBien();
    List<TipoRetencion> consultarIvaServicio();
    List<TipoRetencion> consultarRentaBien();
    List<TipoRetencion> consultarRentaServicio();
}
