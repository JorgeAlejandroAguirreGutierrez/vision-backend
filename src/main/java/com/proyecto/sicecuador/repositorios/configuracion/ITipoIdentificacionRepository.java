package com.proyecto.sicecuador.repositorios.configuracion;

import com.proyecto.sicecuador.modelos.configuracion.TipoIdentificacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ITipoIdentificacionRepository extends JpaRepository<TipoIdentificacion, Long>, JpaSpecificationExecutor<TipoIdentificacion> {
	@Query(value="select * from tipo_identificacion u where u.codigo_sri = :codigoSri order by u.codigo desc", nativeQuery = true)
    TipoIdentificacion findByCodigoSri(String codigoSri);
}
