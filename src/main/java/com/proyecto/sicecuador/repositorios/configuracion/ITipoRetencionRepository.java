package com.proyecto.sicecuador.repositorios.configuracion;

import com.proyecto.sicecuador.modelos.configuracion.TipoRetencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ITipoRetencionRepository extends JpaRepository<TipoRetencion, Long>, JpaSpecificationExecutor<TipoRetencion> {
	@Query(value = "select tr from TipoRetencion tr where tr.estado = :estado")
    List<TipoRetencion> consultarPorEstado(String estado);
	
	@Query(value = "SELECT tr FROM TipoRetencion tr where tr.impuestoRetencion = :impuestoRetencion and tr.tipoRetencion = :tipoRetencion and tr.estado = :estado")
    List<TipoRetencion> findByImpuestoAndTipo(String impuestoRetencion, String tipoRetencion, String estado);
}
