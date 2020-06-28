package com.proyecto.sicecuador.controladoras;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Optional;

public interface GenericoController<T> {
    ResponseEntity<?> consultar();
    ResponseEntity<?> obtener(long id);
    ResponseEntity<?> crear(T t);
    ResponseEntity<?> actualizar(T t);
    ResponseEntity<?> eliminar(long id);
}
