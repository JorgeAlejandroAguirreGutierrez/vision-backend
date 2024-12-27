package com.proyecto.vision.repositorios.venta;

import com.proyecto.vision.modelos.cliente.Cliente;
import com.proyecto.vision.modelos.venta.Factura;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IFacturaRepository extends JpaRepository<Factura, Long>, JpaSpecificationExecutor<Factura> {
    @Query(value = "select f from Factura f order by f.id desc")
    List<Factura> consultar();
    @Query(value = "select f from Factura f order by f.id desc")
    Page<Factura> consultarPagina(Pageable pageable);
    @Query(value = "select f from Factura f where f.empresa.id = :empresaId order by f.id desc")
    Page<Factura> consultarPorEmpresa(long empresaId, Pageable pageable);
    @Query(value = "select f from Factura f where (f.cliente.razonSocial like %:filtro% or f.cliente.identificacion like %:filtro% or f.cliente.direccion like %:filtro%) and f.empresa.id = :empresaId order by c.id desc")
    Page<Factura> consultarFiltroPorEmpresa(String filtro, long empresaId, Pageable pageable);
    @Query(value = "select f from Factura f where f.estado = :estado order by f.id desc")
    List<Factura> consultarPorEstado(String estado);
    @Query(value = "select f from Factura f where f.procesoSRI = :procesoSRI order by f.id desc")
    List<Factura> consultarPorProcesoSRI(String procesoSRI);
    @Query(value = "select f from Factura f where f.empresa.id = :empresaId and f.estado = :estado order by f.id desc")
    List<Factura> consultarPorEmpresaYEstado(long empresaId, String estado);
    @Query(value = "select f from Factura f where f.cliente.id = :clienteId order by f.id desc")
    List<Factura> consultarPorCliente(long clienteId);
    @Query(value = "select f from Factura f where f.cliente.id = :clienteId and f.estado = :estado order by f.id desc")
    List<Factura> consultarPorClienteYEstado(long clienteId, String estado);
    @Query(value = "select f from Factura f where f.cliente.id = :clienteId and f.empresa.id = :empresaId and f.estado = :estado order by f.id desc")
    List<Factura> consultarPorClienteYEmpresaYEstado(long clienteId, long empresaId, String estado);
    @Query(value = "select f from Factura f where f.cliente.id = :clienteId and f.estado = :estado and f.procesoSRI = :procesoSRI order by f.id desc")
    List<Factura> consultarPorClienteYEstadoYProcesoSRI(long clienteId, String estado, String procesoSRI);
    @Query(value = "select f from Factura f where date(f.fecha) between :fechaInicio and :fechaFinal and f.empresa.id = :empresaId order by f.id desc")
    List<Factura> consultarPorFechaInicioYFechaFinal(Date fechaInicio, Date fechaFinal, long empresaId);
    @Query(value = "select f from Factura f where date(f.fecha) = :fecha and f.empresa.id = :empresaId and (f.estado = :estadoEmitida or f.estado = :estadoRecaudada) order by f.id desc")
    List<Factura> consultarPorFechaYEmpresaYEstadoEmitidaYEstadoRecaudada(Date fecha, long empresaId, String estadoEmitida, String estadoRecaudada);

}