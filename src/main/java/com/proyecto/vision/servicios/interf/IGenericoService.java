package com.proyecto.vision.servicios.interf;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface IGenericoService<T> {
    T crear(T t);
    T actualizar(T t);
    T obtener(long id);
    List<T> consultar();
    Page<T> consultarPagina(Pageable pageable);
}
