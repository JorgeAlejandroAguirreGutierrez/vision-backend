package com.proyecto.vision.repositorios.venta;

import com.proyecto.vision.modelos.venta.NotaDebito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INotaDebitoRepository extends JpaRepository<NotaDebito, Long>, JpaSpecificationExecutor<NotaDebito> {
    @Query(value = "select nd from NotaDebito nd order by nd.codigo desc")
    List<NotaDebito> consultar();
    @Query(value = "select nd from NotaDebito nd where nd.estado = :estado order by nd.codigo desc")
    List<NotaDebito> consultarPorEstado(String estado);
    @Query(value = "select nd from NotaDebito nd where nd.empresa.id = :empresaId order by nd.codigo desc")
    List<NotaDebito> consultarPorEmpresa(long empresaId);
    @Query(value = "select nd from NotaDebito nd where nd.empresa.id = :empresaId and nd.estado = :estado order by nd.codigo asc")
    List<NotaDebito> consultarPorEmpresaYEstado(long empresaId, String estado);
    @Query(value = "select nd from NotaDebito nd where nd.factura.id = :facturaId and nd.empresa.id = :empresaId and nd.estado != :estado order by nd.codigo asc")
    List<NotaDebito> consultarPorFacturaYEmpresaYEstadoDiferente(long facturaId, long empresaId, String estado);

}