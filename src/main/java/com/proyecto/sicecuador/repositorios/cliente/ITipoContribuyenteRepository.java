package com.proyecto.sicecuador.repositorios.cliente;

import com.proyecto.sicecuador.modelos.cliente.TipoContribuyente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ITipoContribuyenteRepository extends JpaRepository<TipoContribuyente, Long>, JpaSpecificationExecutor<TipoContribuyente> {
    @Query(value="SELECT * FROM tipo_contribuyente u WHERE u.tipo = :tipo and u.subtipo = :subtipo", nativeQuery = true)
    TipoContribuyente findByTipoAndSubtipo(
            @Param("tipo") String tipo,
            @Param("subtipo") String subtipo);
}
