package com.proyecto.vision.repositorios.configuracion;

import com.proyecto.vision.modelos.configuracion.Regimen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRegimenRepository extends JpaRepository<Regimen, Long>, JpaSpecificationExecutor<Regimen> {
	@Query(value = "select rg from Regimen rg order by rg.id desc")
	List<Regimen> consultar();
	@Query(value = "select rg from Regimen rg where rg.estado=:estado order by rg.id desc")
    List<Regimen> consultarPorEstado(String estado);
	@Query(value = "select rg from Regimen rg where rg.descripcion = :descripcion")
	Optional<Regimen> obtenerPorDescripcion(String descripcion);
	@Query(value = "select rg from Regimen rg where rg.abreviatura = :abreviatura")
	Optional<Regimen> obtenerPorAbreviatura(String abreviatura);
}
