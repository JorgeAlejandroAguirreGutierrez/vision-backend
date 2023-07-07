package com.proyecto.vision.servicios.interf.configuracion;

import com.proyecto.vision.modelos.configuracion.TipoRetencion;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;

public interface ITipoRetencionService extends IGenericoService<TipoRetencion> {
	void validar(TipoRetencion tipoRetencion);
	TipoRetencion activar(TipoRetencion tipoRetencion);
	TipoRetencion inactivar(TipoRetencion tipoRetencion);
	List<TipoRetencion> consultarPorEstado(String estado);
	List<TipoRetencion> consultarIvaBien();
    List<TipoRetencion> consultarIvaServicio();
    List<TipoRetencion> consultarRentaBien();
    List<TipoRetencion> consultarRentaServicio();
}
