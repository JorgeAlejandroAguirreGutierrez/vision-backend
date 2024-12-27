package com.proyecto.vision.servicios.interf;

import java.util.List;
public interface IGenericoService<T> {
    T crear(T t);
    T actualizar(T t);
    T obtener(long id);
    List<T> consultar();
}
