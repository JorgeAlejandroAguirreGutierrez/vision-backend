package com.proyecto.vision.repositorios.compra;

import com.proyecto.vision.modelos.compra.NotaCreditoCompra;
import com.proyecto.vision.modelos.venta.NotaCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INotaCreditoCompraRepository extends JpaRepository<NotaCreditoCompra, Long>, JpaSpecificationExecutor<NotaCreditoCompra> {
    @Query(value = "select ncc from NotaCreditoCompra ncc order by ncc.id desc")
    List<NotaCreditoCompra> consultar();
    @Query(value = "select ncc from NotaCreditoCompra ncc where ncc.estado = :estado order by ncc.id desc")
    List<NotaCreditoCompra> consultarPorEstado(String estado);
    @Query(value = "select ncc from NotaCreditoCompra ncc where ncc.empresa.id = :empresaId order by ncc.id desc")
    List<NotaCreditoCompra> consultarPorEmpresa(long empresaId);
    @Query(value = "select ncc from NotaCreditoCompra ncc where ncc.empresa.id = :empresaId and ncc.estado = :estado order by ncc.id desc")
    List<NotaCreditoCompra> consultarPorEmpresaYEstado(long empresaId, String estado);
    @Query(value = "select ncc from NotaCreditoCompra ncc where ncc.facturaCompra.id = :facturaCompraId and ncc.empresa.id = :empresaId and ncc.estado != :estado order by ncc.id desc")
    List<NotaCreditoCompra> consultarPorFacturaCompraYEmpresaYEstadoDiferente(long facturaCompraId, long empresaId, String estado);
}