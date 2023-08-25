package com.proyecto.vision.repositorios.compra;

import com.proyecto.vision.modelos.compra.NotaCreditoCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INotaCreditoCompraRepository extends JpaRepository<NotaCreditoCompra, Long>, JpaSpecificationExecutor<NotaCreditoCompra> {
    @Query(value = "select ncc from NotaCreditoCompra ncc order by ncc.codigo desc")
    List<NotaCreditoCompra> consultar();
    @Query(value = "select ncc from NotaCreditoCompra ncc where ncc.proceso = :proceso order by ncc.codigo desc")
    List<NotaCreditoCompra> consultarPorProceso(String proceso);
    @Query(value = "select ncc from NotaCreditoCompra ncc where ncc.empresa.id = :empresaId order by ncc.codigo desc")
    List<NotaCreditoCompra> consultarPorEmpresa(long empresaId);
    @Query(value = "select ncc from NotaCreditoCompra ncc where ncc.empresa.id = :empresaId and ncc.proceso = :proceso order by ncc.codigo asc")
    List<NotaCreditoCompra> consultarPorEmpresaYProceso(long empresaId, String proceso);
    @Query(value = "select ncc from NotaCreditoCompra ncc where ncc.facturaCompra.id = :facturaCompraId and ncc.proceso = :proceso order by ncc.codigo desc")
    List<NotaCreditoCompra> consultarPorFacturaCompraYProceso(long facturaCompraId, String proceso);
}
