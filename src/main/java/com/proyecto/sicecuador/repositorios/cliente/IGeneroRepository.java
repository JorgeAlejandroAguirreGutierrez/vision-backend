package com.proyecto.sicecuador.repositorios.cliente;

import com.proyecto.sicecuador.modelos.cliente.Genero;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IGeneroRepository extends JpaRepository<Genero, Long>, JpaSpecificationExecutor<Genero> {
	
	@Query(value = "select g from Genero g where g.estado=:estado")
    List<Genero> consultarPorEstado(String estado);
	
	@Query(value = "select g from Genero g where g.codigo like '%'||:codigo||'%' and g.descripcion like '%'||:descripcion||'%' and g.abreviatura like '%'||:abreviatura||'%'")
    List<Genero> buscar(String codigo, String descripcion, String abreviatura);
}
