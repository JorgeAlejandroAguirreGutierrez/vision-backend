package com.proyecto.vision.repositorios.configuracion;

import com.proyecto.vision.modelos.configuracion.Genero;

import java.util.List;
import java.util.Optional;

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
    @Query(value = "select g from Genero g where g.descripcion = :descripcion and g.estado = :estado")
    Optional<Genero> obtenerPorDescripcionYEstado(String descripcion, String estado);
}
