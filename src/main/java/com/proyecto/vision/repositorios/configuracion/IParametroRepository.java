package com.proyecto.vision.repositorios.configuracion;

import com.proyecto.vision.modelos.configuracion.Parametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface IParametroRepository extends JpaRepository<Parametro, Long>, JpaSpecificationExecutor<Parametro> {
    @Query(value = "select p from Parametro p order by p.id desc")
    List<Parametro> consultar();
    @Query(value = "select p from Parametro p where p.estado=:estado order by p.id desc")
    List<Parametro> consultarPorEstado(String estado);
    @Query(value = "select p from Parametro p where p.tipo = :tipo and p.estado = :estado order by p.id desc")
    Optional<Parametro> findByTipo(String tipo, String estado);
    @Query(value = "select p from Parametro p where p.tipo = :tipo and p.estado = :estado order by p.id desc")
    List<Parametro> AllByTipo(String tipo, String estado);
}
