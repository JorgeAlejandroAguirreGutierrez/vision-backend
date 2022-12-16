package com.proyecto.sicecuador.servicios.interf.configuracion;

import com.proyecto.sicecuador.modelos.configuracion.Parametro;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface IParametroService extends IGenericoService<Parametro> {
	Parametro activar(Parametro parametro);
	Parametro inactivar(Parametro parametro);
	List<Parametro> consultarActivos();
    Parametro obtenerPorTipo(Parametro parametro);
    Parametro obtenerPorTipoTabla(Parametro parametro);
    List<Parametro> consultarPorTipo(Parametro parametro);
}
