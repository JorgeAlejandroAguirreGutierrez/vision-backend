package com.proyecto.vision.repositorios.compra;

import com.proyecto.vision.modelos.compra.NotaDebitoCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INotaDebitoCompraRepository extends JpaRepository<NotaDebitoCompra, Long>, JpaSpecificationExecutor<NotaDebitoCompra> {
    @Query(value = "select ndc from NotaDebitoCompra ndc order by ndc.fechaCreacion desc")
    List<NotaDebitoCompra> consultar();
    @Query(value = "select ndc from NotaDebitoCompra ndc where ndc.estado = :estado order by ndc.fechaCreacion desc")
    List<NotaDebitoCompra> consultarPorEstado(String estado);
    @Query(value = "select ndc from NotaDebitoCompra ndc where ndc.empresa.id = :empresaId order by ndc.fechaCreacion desc")
    List<NotaDebitoCompra> consultarPorEmpresa(long empresaId);
    @Query(value = "select ndc from NotaDebitoCompra ndc where ndc.empresa.id = :empresaId and ndc.estado = :estado order by ndc.fechaCreacion desc")
    List<NotaDebitoCompra> consultarPorEmpresaYEstado(long empresaId, String estado);
}