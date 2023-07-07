package com.proyecto.vision.repositorios.configuracion;

import com.proyecto.vision.modelos.configuracion.Impuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface IImpuestoRepository extends JpaRepository<Impuesto, Long>, JpaSpecificationExecutor<Impuesto> {
    @Query(value = "select i from Impuesto i order by i.codigo asc")
    List<Impuesto> consultar();
    @Query(value = "select i from Impuesto i where i.estado=:estado order by i.codigo asc")
    List<Impuesto> consultarPorEstado(String estado);
	@Query(value = "select i from Impuesto i where i.porcentaje = :porcentaje and i.estado = :estado order by i.codigo asc")
    Optional<Impuesto> findByPorcentaje(double porcentaje, String estado);
}
