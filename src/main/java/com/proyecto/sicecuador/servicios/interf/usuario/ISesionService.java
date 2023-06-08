package com.proyecto.sicecuador.servicios.interf.usuario;

import com.proyecto.sicecuador.modelos.usuario.Sesion;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;
import java.util.Optional;

public interface ISesionService extends IGenericoService<Sesion> {
    Optional<Sesion> validar(Sesion sesion);
    Optional<Sesion> cerrar(Sesion sesion);
}
