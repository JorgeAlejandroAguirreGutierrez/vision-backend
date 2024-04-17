package com.proyecto.vision.repositorios.cliente;

import com.proyecto.vision.modelos.cliente.OrigenIngreso;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrigenIngresoRepository extends JpaRepository<OrigenIngreso, Long>, JpaSpecificationExecutor<OrigenIngreso> {
	@Query(value = "select oi from OrigenIngreso oi order by oi.id desc")
	List<OrigenIngreso> consultar();
	@Query(value = "select oi from OrigenIngreso oi where oi.estado=:estado order by oi.id desc")
    List<OrigenIngreso> consultarPorEstado(String estado);
	@Query(value = "select oi from OrigenIngreso oi where oi.descripcion = :descripcion and oi.estado = :estado")
	Optional<OrigenIngreso> obtenerPorDescripcionYEstado(String descripcion, String estado);
}
