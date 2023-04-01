package com.proyecto.sicecuador.repositorios.usuario;

import com.proyecto.sicecuador.modelos.configuracion.Regimen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRegimenRepository extends JpaRepository<Regimen, Long>, JpaSpecificationExecutor<Regimen> {
	@Query(value = "select rg from Regimen rg order by rg.codigo asc")
	List<Regimen> consultar();
	@Query(value = "select rg from Regimen rg where rg.estado=:estado order by rg.codigo asc")
    List<Regimen> consultarPorEstado(String estado);
	@Query(value = "select rg from Regimen rg where rg.codigo like '%'||:codigo||'%' and rg.descripcion like '%'||:descripcion||'%' and rg.abreviatura like '%'||:abreviatura||'%' order by rg.codigo asc")
	List<Regimen> buscar(String codigo, String descripcion, String abreviatura);
}
