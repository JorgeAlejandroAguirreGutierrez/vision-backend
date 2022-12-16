package com.proyecto.sicecuador.servicios.interf.configuracion;

import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface IUbicacionService extends IGenericoService<Ubicacion> {
	Ubicacion activar(Ubicacion ubicacion);
	Ubicacion inactivar(Ubicacion ubicacion);
	List<Ubicacion> consultarActivos();
    List<Ubicacion> consultarProvincias();
    List<Ubicacion> consultarCantones(Ubicacion ubicacion);
    List<Ubicacion> consultarParroquias(Ubicacion ubicacion);
    Ubicacion obtenerUbicacionId(Ubicacion ubicacion);
    List<Ubicacion> buscar(Ubicacion ubicacion);
}
