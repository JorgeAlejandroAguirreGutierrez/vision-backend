package com.proyecto.sicecuador.servicios.interf.recaudacion;

import com.proyecto.sicecuador.modelos.recaudacion.Credito;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.Optional;

public interface ICreditoService extends IGenericoService<Credito> {
    Optional<Credito> construir(Credito credito);
}
