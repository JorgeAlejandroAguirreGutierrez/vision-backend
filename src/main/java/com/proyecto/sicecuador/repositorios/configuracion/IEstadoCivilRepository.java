package com.proyecto.sicecuador.repositorios.configuracion;
import com.proyecto.sicecuador.modelos.configuracion.EstadoCivil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IEstadoCivilRepository extends JpaRepository<EstadoCivil, Long>, JpaSpecificationExecutor<EstadoCivil> {
	@Query(value = "select ec from EstadoCivil ec where ec.estado=:estado")
    List<EstadoCivil> consultarPorEstado(String estado);
	@Query(value = "select ec from EstadoCivil ec where ec.codigo like '%'||:codigo||'%' and ec.descripcion like '%'||:descripcion||'%' and ec.abreviatura like '%'||:abreviatura||'%'")
	List<EstadoCivil> buscar(String codigo, String descripcion, String abreviatura);
}
