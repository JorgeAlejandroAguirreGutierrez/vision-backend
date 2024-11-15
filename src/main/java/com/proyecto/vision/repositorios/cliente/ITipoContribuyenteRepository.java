package com.proyecto.vision.repositorios.cliente;

import com.proyecto.vision.modelos.cliente.TipoContribuyente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITipoContribuyenteRepository extends JpaRepository<TipoContribuyente, Long>, JpaSpecificationExecutor<TipoContribuyente> {
    @Query(value="select * from tipo_contribuyente u where u.tipo = :tipo and u.subtipo = :subtipo order by u.id desc", nativeQuery = true)
    Optional<TipoContribuyente> obtenerPorTipoYSubtipo(@Param("tipo") String tipo, @Param("subtipo") String subtipo);
}
