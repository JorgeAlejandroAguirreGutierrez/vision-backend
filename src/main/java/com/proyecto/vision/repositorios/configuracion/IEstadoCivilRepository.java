package com.proyecto.vision.repositorios.configuracion;
import com.proyecto.vision.modelos.configuracion.EstadoCivil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IEstadoCivilRepository extends JpaRepository<EstadoCivil, Long>, JpaSpecificationExecutor<EstadoCivil> {
	@Query(value = "select ec from EstadoCivil ec order by ec.id desc")
	List<EstadoCivil> consultar();
	@Query(value = "select ec from EstadoCivil ec where ec.estado = :estado order by ec.id desc")
    List<EstadoCivil> consultarPorEstado(String estado);
}
