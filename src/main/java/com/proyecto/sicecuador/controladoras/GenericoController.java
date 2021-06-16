package com.proyecto.sicecuador.controladoras;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface GenericoController<T> {
    ResponseEntity<?> consultar();
    ResponseEntity<?> obtener(long id);
    ResponseEntity<?> crear(T t);
    ResponseEntity<?> actualizar(T t);
    ResponseEntity<?> eliminar(long id);
    ResponseEntity<?> importar(MultipartFile archivo);
}
