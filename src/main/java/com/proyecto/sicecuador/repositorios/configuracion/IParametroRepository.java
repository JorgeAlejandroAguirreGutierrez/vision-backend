package com.proyecto.sicecuador.repositorios.configuracion;

import com.proyecto.sicecuador.modelos.configuracion.Parametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface IParametroRepository extends JpaRepository<Parametro, Long>, JpaSpecificationExecutor<Parametro> {

    @Query(value = "select p from Parametro p order by p.codigo asc")
    List<Parametro> consultar();
    @Query(value = "select p from Parametro p where p.estado=:estado order by p.codigo asc")
    List<Parametro> consultarPorEstado(String estado);
    @Query(value = "select count(*) from modelo", nativeQuery = true)
    long findConteoModelo();

    @Query(value = "select p from Parametro p where p.tipo = :tipo and p.estado = :estado order by p.codigo asc")
    Optional<Parametro> findByTipo(String tipo, String estado);
    
    @Query(value = "select p from Parametro p where p.tabla = :tabla and p.tipo = :tipo and p.estado = :estado order by p.codigo asc")
    Optional<Parametro> findByTablaAndTipo(String tabla, String tipo, String estado);
    
    @Query(value = "select p from Parametro p where p.tipo = :tipo and p.estado = :estado order by p.codigo asc")
    List<Parametro> AllByTipo(String tipo, String estado);
}
