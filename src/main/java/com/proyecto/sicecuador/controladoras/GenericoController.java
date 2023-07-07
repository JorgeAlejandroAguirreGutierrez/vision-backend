package com.proyecto.vision.controladoras;

import org.springframework.http.ResponseEntity;

public interface GenericoController<T> {
    ResponseEntity<?> consultar();
    ResponseEntity<?> obtener(long id);
    ResponseEntity<?> crear(T t);
    ResponseEntity<?> actualizar(T t);
}
