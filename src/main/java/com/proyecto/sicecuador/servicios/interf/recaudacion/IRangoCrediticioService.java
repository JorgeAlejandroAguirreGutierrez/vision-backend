package com.proyecto.sicecuador.servicios.interf.recaudacion;

import com.proyecto.sicecuador.modelos.recaudacion.RangoCrediticio;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.Optional;

public interface IRangoCrediticioService extends IGenericoService<RangoCrediticio> {
    Optional<RangoCrediticio> obtenerSaldo(double saldo);
}
