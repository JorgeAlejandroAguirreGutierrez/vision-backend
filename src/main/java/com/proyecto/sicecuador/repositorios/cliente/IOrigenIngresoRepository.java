package com.proyecto.sicecuador.repositorios.cliente;

import com.proyecto.sicecuador.modelos.cliente.OrigenIngreso;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrigenIngresoRepository extends JpaRepository<OrigenIngreso, Long>, JpaSpecificationExecutor<OrigenIngreso> {
	@Query(value = "select oi from OrigenIngreso oi where oi.estado=:estado")
    List<OrigenIngreso> consultarPorEstado(String estado);
	
	@Query(value = "select oi from OrigenIngreso oi where oi.codigo like '%'||:codigo||'%' and oi.descripcion like '%'||:descripcion||'%' and oi.abreviatura like '%'||:abreviatura||'%'")
	List<OrigenIngreso> buscar(String codigo, String descripcion, String abreviatura);
}
