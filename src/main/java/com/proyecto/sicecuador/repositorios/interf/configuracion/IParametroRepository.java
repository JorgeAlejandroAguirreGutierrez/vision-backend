package com.proyecto.sicecuador.repositorios.interf.configuracion;

import com.proyecto.sicecuador.modelos.configuracion.Parametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface IParametroRepository extends JpaRepository<Parametro, Long>, JpaSpecificationExecutor<Parametro> {
	@Query(value = "SELECT count(*) FROM modelo", nativeQuery = true)
    long findConteoModelo();

    @Query(value = "SELECT * FROM parametro u WHERE u.tipo = :tipo", nativeQuery = true)
    Optional<Parametro> findByTipo(@Param("tipo") String tipo);
    @Query(value = "SELECT * FROM parametro u WHERE u.tabla = :tabla and u.tipo = :tipo", nativeQuery = true)
    Optional<Parametro> findByTablaAndTipo(
            @Param("tabla") String tabla,
            @Param("tipo") String tipo);
    @Query(value = "SELECT * FROM parametro u WHERE u.tipo = :tipo", nativeQuery = true)
    List<Parametro> AllByTipo(@Param("tipo") String tipo);
}
