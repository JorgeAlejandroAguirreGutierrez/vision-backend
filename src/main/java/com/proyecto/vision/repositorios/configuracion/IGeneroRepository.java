package com.proyecto.vision.repositorios.configuracion;

import com.proyecto.vision.modelos.configuracion.Genero;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IGeneroRepository extends JpaRepository<Genero, Long>, JpaSpecificationExecutor<Genero> {
    @Query(value = "select g from Genero g order by g.id desc")
    List<Genero> consultar();
    @Query(value = "select g from Genero g where g.estado = :estado order by g.id desc")
    List<Genero> consultarPorEstado(String estado);
}
