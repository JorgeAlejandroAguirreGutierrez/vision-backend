package com.proyecto.vision.repositorios.venta;

import com.proyecto.vision.modelos.venta.NotaCredito;
import com.proyecto.vision.modelos.venta.NotaDebito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface INotaCreditoRepository extends JpaRepository<NotaCredito, Long>, JpaSpecificationExecutor<NotaCredito> {
    @Query(value = "select nc from NotaCredito nc order by nc.codigo desc")
    List<NotaCredito> consultar();
    @Query(value = "select nc from NotaCredito nc where nc.estado = :estado order by nc.codigo desc")
    List<NotaCredito> consultarPorEstado(String estado);
    @Query(value = "select nc from NotaCredito nc where nc.empresa.id = :empresaId order by nc.codigo desc")
    List<NotaCredito> consultarPorEmpresa(long empresaId);
    @Query(value = "select nc from NotaCredito nc where nc.empresa.id = :empresaId and nc.estado = :estado order by nc.codigo asc")
    List<NotaCredito> consultarPorEmpresaYEstado(long empresaId, String estado);
    @Query(value = "select nc from NotaCredito nc where nc.factura.id = :facturaId and nc.empresa.id = :empresaId and nc.estado != :estado order by nc.codigo asc")
    List<NotaCredito> consultarPorFacturaYEmpresaYEstadoDiferente(long facturaId, long empresaId, String estado);
    @Query(value = "select nc from NotaCredito nc where nc.factura.id = :facturaId and nc.empresa.id = :empresaId and nc.estado != :estado order by nc.codigo asc")
    Optional<NotaCredito> obtenerPorFacturaYEmpresaYEstadoDiferente(long facturaId, long empresaId, String estado);
}