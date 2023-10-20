package com.proyecto.vision.repositorios.configuracion;

import com.proyecto.vision.modelos.configuracion.TipoIdentificacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITipoIdentificacionRepository extends JpaRepository<TipoIdentificacion, Long>, JpaSpecificationExecutor<TipoIdentificacion> {
	@Query(value="select * from tipo_identificacion u where u.codigo_sri = :codigoSri", nativeQuery = true)
    Optional<TipoIdentificacion> obtenerPorCodigoSri(String codigoSri);
    @Query(value="select * from tipo_identificacion u where u.abreviatura = :abreviatura", nativeQuery = true)
    Optional<TipoIdentificacion> obtenerPorAbreviatura(String abreviatura);

}
