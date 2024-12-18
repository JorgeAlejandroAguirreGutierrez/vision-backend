package com.proyecto.vision.repositorios.acceso;

import com.proyecto.vision.modelos.acceso.Estacion;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IEstacionRepository extends JpaRepository<Estacion, Long>, JpaSpecificationExecutor<Estacion> {
    @Query(value = "select e from Estacion e order by e.id desc")
    List<Estacion> consultar();
    @Query(value = "select e from Estacion e where e.estado=:estado order by e.id desc")
    List<Estacion> consultarPorEstado(String estado);
    @Query(value = "select e from Estacion e where e.establecimiento.empresa.id = :empresaId order by e.id desc")
    List<Estacion> consultarPorEmpresa(long empresaId);
    @Query(value = "select e from Estacion e where e.establecimiento.id = :establecimientoId and e.estado = :estado order by e.id desc")
    List<Estacion> consultarPorEstablecimiento(long establecimientoId, String estado);
    @Query(value = "select e from Estacion e where e.establecimiento.id = :establecimientoId and e.estado = :estado order by e.id desc")
    List<Estacion> consultarEstacionesPorEstablecimiento(long establecimientoId, String estado);
    @Query(value = "select e from Estacion e where e.establecimiento.id = :establecimientoId and e.puntoVenta = :puntoVenta and e.estado = :estado order by e.id desc")
    List<Estacion> consultarPuntosVentaPorEstablecimiento(long establecimientoId, String puntoVenta, String estado);
    @Query(value = "select e from Estacion e where e.establecimiento.empresa.id = :empresaId and e.establecimiento.id = :establecimientoId and e.codigoSRI = :codigoSri")
    Optional<Estacion> ObtenerPorEmpresaYEstablecimientoYCodigoSri(long empresaId, long establecimientoId, String codigoSri);
}
