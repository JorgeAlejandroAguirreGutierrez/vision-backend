package com.proyecto.vision.servicios.interf.configuracion;

import com.proyecto.vision.modelos.configuracion.Parametro;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;

public interface IParametroService extends IGenericoService<Parametro> {
	void validar(Parametro parametro);
    Parametro activar(Parametro parametro);
	Parametro inactivar(Parametro parametro);
	List<Parametro> consultarPorEstado(String estado);
    Parametro obtenerPorTipo(String tipo);
    List<Parametro> consultarPorTipo(String tipo);
}
