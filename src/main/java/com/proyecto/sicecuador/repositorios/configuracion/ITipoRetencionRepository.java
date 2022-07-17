package com.proyecto.sicecuador.repositorios.configuracion;

import com.proyecto.sicecuador.modelos.configuracion.TipoRetencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ITipoRetencionRepository extends JpaRepository<TipoRetencion, Long>, JpaSpecificationExecutor<TipoRetencion> {
    @Query(value = "SELECT * FROM tipo_retencion u WHERE u.impuesto_retencion = :impuesto_retencion and u.tipo_retencion = :tipo_retencion", nativeQuery = true)
    List<TipoRetencion> findByImpuestoAndTipo(
            @Param("impuesto_retencion") String impuesto_retencion,
            @Param("tipo_retencion") String tipo_retencion);
}
