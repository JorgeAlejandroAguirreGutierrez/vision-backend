package com.proyecto.vision.repositorios.acceso;

import com.proyecto.vision.modelos.acceso.Nuevo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface INuevoRepository extends JpaRepository<Nuevo, Long>, JpaSpecificationExecutor<Nuevo> {
    @Query(value = "select n from Nuevo n order by n.id desc")
    List<Nuevo> consultar();
    @Query(value = "select n from Nuevo n where n.identificacion = :identificacion")
    Optional<Nuevo> obtenerPorIdentificacion(String identificacion);
}
