package com.proyecto.sicecuador.servicios.interf;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface IGenericoService<T> {
    T crear(T t);
    T actualizar(T t);
    T eliminar(T t);
    Optional<T> obtener(T t);
    List<T> consultar();
}
