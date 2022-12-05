package com.proyecto.sicecuador.servicios.interf.configuracion;
import com.proyecto.sicecuador.modelos.configuracion.Parametro;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;
import java.util.Optional;

public interface IParametroService extends IGenericoService<Parametro> {
	Parametro activar(Parametro parametro);
	Parametro inactivar(Parametro parametro);
	List<Parametro> consultarActivos();
    Parametro obtenerTipo(Parametro parametro);
    Parametro obtenerTipoTabla(Parametro parametro);
    List<Parametro> consultarTipo(Parametro parametro);
}
