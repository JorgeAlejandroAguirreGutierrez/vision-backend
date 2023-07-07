package com.proyecto.vision.repositorios.cliente;

import com.proyecto.vision.modelos.cliente.TipoContribuyente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ITipoContribuyenteRepository extends JpaRepository<TipoContribuyente, Long>, JpaSpecificationExecutor<TipoContribuyente> {
    @Query(value="select * from tipo_contribuyente u where u.tipo = :tipo and u.subtipo = :subtipo order by u.codigo asc", nativeQuery = true)
    TipoContribuyente findByTipoAndSubtipo(
            @Param("tipo") String tipo,
            @Param("subtipo") String subtipo);
}
