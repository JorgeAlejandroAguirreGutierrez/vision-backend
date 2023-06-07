package com.proyecto.sicecuador.servicios.interf.configuracion;

import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface IUbicacionService extends IGenericoService<Ubicacion> {
	void validar(Ubicacion ubicacion);
    Ubicacion activar(Ubicacion ubicacion);
	Ubicacion inactivar(Ubicacion ubicacion);
	List<Ubicacion> consultarPorEstado(String estado);
    List<Ubicacion> consultarProvincias();
    List<Ubicacion> consultarCantones(Ubicacion ubicacion);
    List<Ubicacion> consultarParroquias(String canton);
    Ubicacion obtenerUbicacionId(String provincia, String canton, String parroquia);
    List<Ubicacion> buscar(String codigoNorma, String provincia, String canton, String parroquia);
}
