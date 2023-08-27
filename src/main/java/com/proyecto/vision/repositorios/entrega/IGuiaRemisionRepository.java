package com.proyecto.vision.repositorios.entrega;

import com.proyecto.vision.modelos.entrega.GuiaRemision;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IGuiaRemisionRepository extends JpaRepository<GuiaRemision, Long>, JpaSpecificationExecutor<GuiaRemision> {
    @Query(value = "select gr from GuiaRemision gr order by gr.codigo desc")
    List<GuiaRemision> consultar();
    @Query(value = "select gr from GuiaRemision gr where gr.estado = :estado order by gr.codigo desc")
    List<GuiaRemision> consultarPorEstado(String estado);
    @Query(value = "select gr from GuiaRemision gr where gr.empresa.id = :empresaId order by gr.codigo desc")
    List<GuiaRemision> consultarPorEmpresa(long empresaId);
    @Query(value = "select gr from GuiaRemision gr where gr.empresa.id = :empresaId and gr.estado = :estado order by gr.codigo desc")
    List<GuiaRemision> consultarPorEmpresaYEstado(long empresaId, String estado);
    @Query(value = "select gr from GuiaRemision gr where gr.factura.id = :facturaId and gr.empresa.id = :empresaId and gr.estado = :estado order by gr.codigo desc")
    List<GuiaRemision> consultarPorFacturaYEmpresaYEstado(long facturaId, long empresaId, String estado);
    @Query(value = "select gr from GuiaRemision gr where gr.factura.id = :facturaId and gr.empresa.id = :empresaId and gr.estado != :estado order by gr.codigo desc")
    List<GuiaRemision> consultarPorFacturaYEmpresaYEstadoDiferente(long facturaId, long empresaId, String estado);
    @Query(value = "select gr from GuiaRemision gr where gr.factura.id = :facturaId order by gr.codigo desc")
    Optional<GuiaRemision> obtenerPorFactura(long facturaId);

}