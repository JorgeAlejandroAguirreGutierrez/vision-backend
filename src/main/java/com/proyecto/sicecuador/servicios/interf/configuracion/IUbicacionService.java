package com.proyecto.sicecuador.servicios.interf.configuracion;

import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;
import java.util.Optional;

public interface IUbicacionService extends IGenericoService<Ubicacion> {
    List<Ubicacion> consultarProvincias();
    List<Ubicacion> consultarCantones(Ubicacion ubicacion);
    List<Ubicacion> consultarParroquias(Ubicacion ubicacion);
    Optional<Ubicacion> obtenerUbicacionID(Ubicacion ubicacion);
    List<Ubicacion> buscar(Ubicacion ubicacion);
}
