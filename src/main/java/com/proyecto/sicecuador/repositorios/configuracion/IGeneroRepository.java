package com.proyecto.sicecuador.repositorios.configuracion;

import com.proyecto.sicecuador.modelos.configuracion.Genero;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IGeneroRepository extends JpaRepository<Genero, Long>, JpaSpecificationExecutor<Genero> {

    @Query(value = "select g from Genero g order by g.codigo asc")
    List<Genero> consultar();
    @Query(value = "select g from Genero g where g.estado=:estado order by g.codigo asc")
    List<Genero> consultarPorEstado(String estado);
	@Query(value = "select g from Genero g where g.codigo like '%'||:codigo||'%' and g.descripcion like '%'||:descripcion||'%' and g.abreviatura like '%'||:abreviatura||'%' order by g.codigo asc")
    List<Genero> buscar(String codigo, String descripcion, String abreviatura);
}
