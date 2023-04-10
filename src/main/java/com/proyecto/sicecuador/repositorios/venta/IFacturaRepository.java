package com.proyecto.sicecuador.repositorios.venta;

import com.proyecto.sicecuador.modelos.venta.Factura;

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
    @Query(value = "select f from Factura f where f.cliente.id = :clienteId and f.estado=:estado order by f.codigo asc")
    List<Factura> consultarPorCliente(long clienteId, String estado);

}
