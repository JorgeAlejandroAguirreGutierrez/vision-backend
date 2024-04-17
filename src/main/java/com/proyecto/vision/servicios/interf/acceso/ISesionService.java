package com.proyecto.vision.servicios.interf.acceso;

import com.proyecto.vision.modelos.acceso.Sesion;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.Optional;

public interface ISesionService extends IGenericoService<Sesion> {
    Optional<Sesion> validar(Sesion sesion);
    Optional<Sesion> cerrar(Sesion sesion);
}
