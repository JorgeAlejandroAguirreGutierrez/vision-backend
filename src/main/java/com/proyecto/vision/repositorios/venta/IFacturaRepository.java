package com.proyecto.vision.repositorios.venta;

import com.proyecto.vision.modelos.venta.Factura;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IFacturaRepository extends JpaRepository<Factura, Long>, JpaSpecificationExecutor<Factura> {
    @Query(value = "select f from Factura f order by f.codigo asc")
    List<Factura> consultar();
    @Query(value = "select f from Factura f where f.estado=:estado order by f.codigo asc")
    List<Factura> consultarPorEstado(String estado);
    @Query(value = "select f from Factura f where f.empresa.id = :empresaId order by f.codigo asc")
    List<Factura> consultarPorEmpresa(long empresaId);
    @Query(value = "select f from Factura f where f.empresa.id = :empresaId and f.estado = :estado order by f.codigo asc")
    List<Factura> consultarPorEmpresaYEstado(long empresaId, String estado);
    @Query(value = "select f from Factura f where f.cliente.id = :clienteId order by f.codigo asc")
    List<Factura> consultarPorCliente(long clienteId);
    @Query(value = "select f from Factura f where f.cliente.id = :clienteId and f.estado = :estado order by f.codigo asc")
    List<Factura> consultarPorClienteYEstado(long clienteId, String estado);
    @Query(value = "select f from Factura f where f.empresa.id = :empresaId and f.cliente.id = :clienteId and f.estado = :estado order by f.codigo asc")
    List<Factura> consultarPorEmpresaYClienteYEstado(long empresaId, long clienteId, String estado);
    @Query(value = "select f from Factura f where f.cliente.id = :clienteId and f.estado = :estado and f.estadoInterno = :estadoInterno and f.estadoSri = :estadoSri order by f.codigo asc")
    List<Factura> consultarPorClienteYEstadoYEstadoInternoYEstadoSri(long clienteId, String estado, String estadoInterno, String estadoSri);
    @Query(value = "select f from Factura f where date(f.fecha) between :fechaInicio and :fechaFinal and f.empresa.id = :empresaId order by f.codigo asc")
    List<Factura> consultarPorFechaInicioYFechaFinal(Date fechaInicio, Date fechaFinal, long empresaId);

}
