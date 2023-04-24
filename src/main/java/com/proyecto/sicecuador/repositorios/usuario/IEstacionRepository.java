package com.proyecto.sicecuador.repositorios.usuario;

import com.proyecto.sicecuador.modelos.usuario.Estacion;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IEstacionRepository extends JpaRepository<Estacion, Long>, JpaSpecificationExecutor<Estacion> {
    @Query(value = "select e from Estacion e order by e.codigo asc")
    List<Estacion> consultar();
    @Query(value = "select e from Estacion e where e.estado=:estado order by e.codigo asc")
    List<Estacion> consultarPorEstado(String estado);
    @Query(value = "select e from Estacion e where e.establecimiento.id = :establecimientoId and e.estado = :estado order by e.codigo asc")
    List<Estacion> consultarPorEstablecimiento(long establecimientoId, String estado);
    @Query(value = "select e from Estacion e where e.establecimiento.id = :establecimientoId and e.puntoVenta = :puntoVenta and e.estado = :estado order by e.codigo asc")
    List<Estacion> consultarPorEstablecimientoPuntoVenta(long establecimientoId, String puntoVenta, String estado);
}
