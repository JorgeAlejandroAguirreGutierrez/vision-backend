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
    @Query(value = "select f from Factura f order by f.codigo desc")
    List<Factura> consultar();
    @Query(value = "select f from Factura f where f.proceso = :proceso order by f.codigo desc")
    List<Factura> consultarPorProceso(String proceso);
    @Query(value = "select f from Factura f where f.estadoSRI = :estadoSRI order by f.codigo desc")
    List<Factura> consultarPorEstadoSRI(String estadoSRI);
    @Query(value = "select f from Factura f where f.empresa.id = :empresaId order by f.codigo desc")
    List<Factura> consultarPorEmpresa(long empresaId);
    @Query(value = "select f from Factura f where f.empresa.id = :empresaId and f.proceso = :proceso order by f.codigo asc")
    List<Factura> consultarPorEmpresaYProceso(long empresaId, String proceso);
    @Query(value = "select f from Factura f where f.empresa.id = :empresaId and f.estadoSRI = :estadoSRI order by f.codigo asc")
    List<Factura> consultarPorEmpresaYEstadoSRI(long empresaId, String estadoSRI);
    @Query(value = "select f from Factura f where f.cliente.id = :clienteId order by f.codigo desc")
    List<Factura> consultarPorCliente(long clienteId);
    @Query(value = "select f from Factura f where f.cliente.id = :clienteId and f.estadoSRI = :estadoSRI order by f.codigo desc")
    List<Factura> consultarPorClienteYEstadoSRI(long clienteId, String estadoSRI);
    @Query(value = "select f from Factura f where f.empresa.id = :empresaId and f.cliente.id = :clienteId and f.estadoSRI = :estadoSRI order by f.codigo desc")
    List<Factura> consultarPorClienteYEmpresaYEstadoSRI(long clienteId, long empresaId, String estadoSRI);
    @Query(value = "select f from Factura f where f.cliente.id = :clienteId and f.proceso = :proceso and f.estadoSRI = :estadoSRI order by f.codigo desc")
    List<Factura> consultarPorClienteYProcesoYEstadoSRI(long clienteId, String proceso, String estadoSRI);
    @Query(value = "select f from Factura f where date(f.fecha) between :fechaInicio and :fechaFinal and f.empresa.id = :empresaId order by f.codigo desc")
    List<Factura> consultarPorFechaInicioYFechaFinal(Date fechaInicio, Date fechaFinal, long empresaId);

}
