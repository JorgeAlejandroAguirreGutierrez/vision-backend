package com.proyecto.sicecuador.repositorios.configuracion;

import com.proyecto.sicecuador.modelos.configuracion.TipoIdentificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ITipoIdentificacionRepository extends JpaRepository<TipoIdentificacion, Long>, JpaSpecificationExecutor<TipoIdentificacion> {
	 @Query(value="SELECT * FROM tipo_identificacion u WHERE u.codigo_sri = :codigoSri", nativeQuery = true)
	    TipoIdentificacion findByCodigoSri(
	            @Param("codigoSri") String codigoSri);
}
