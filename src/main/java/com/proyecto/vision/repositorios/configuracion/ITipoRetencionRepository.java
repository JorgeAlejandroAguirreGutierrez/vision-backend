package com.proyecto.vision.repositorios.configuracion;

import com.proyecto.vision.modelos.configuracion.TipoRetencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ITipoRetencionRepository extends JpaRepository<TipoRetencion, Long>, JpaSpecificationExecutor<TipoRetencion> {
    @Query(value = "select tr from TipoRetencion tr order by tr.id desc")
    List<TipoRetencion> consultar();
    @Query(value = "select tr from TipoRetencion tr where tr.estado = :estado order by tr.id desc")
    List<TipoRetencion> consultarPorEstado(String estado);
	@Query(value = "select tr from TipoRetencion tr where tr.impuestoRetencion = :impuestoRetencion and tr.tipoRetencion = :tipoRetencion and tr.estado = :estado order by tr.id desc")
    List<TipoRetencion> findByImpuestoAndTipo(String impuestoRetencion, String tipoRetencion, String estado);
}
