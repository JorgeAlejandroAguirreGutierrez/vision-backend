package com.proyecto.vision.repositorios.entrega;

import com.proyecto.vision.modelos.entrega.GuiaRemision;

import java.util.List;
import java.util.Optional;

import com.proyecto.vision.modelos.venta.NotaCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IGuiaRemisionRepository extends JpaRepository<GuiaRemision, Long>, JpaSpecificationExecutor<GuiaRemision> {
    @Query(value = "select gr from GuiaRemision gr order by gr.codigo desc")
    List<GuiaRemision> consultar();
    @Query(value = "select gr from GuiaRemision gr where gr.estadoSRI= :estadoSRI order by gr.codigo desc")
    List<GuiaRemision> consultarPorEstadoSRI(String estadoSRI);
    @Query(value = "select gr from GuiaRemision gr where gr.empresa.id = :empresaId order by gr.codigo desc")
    List<GuiaRemision> consultarPorEmpresa(long empresaId);
    @Query(value = "select gr from GuiaRemision gr where gr.empresa.id = :empresaId and gr.estadoSRI = :estadoSRI order by gr.codigo asc")
    List<GuiaRemision> consultarPorEmpresaYEstadoSRI(long empresaId, String estadoSRI);
    @Query(value = "select gr from GuiaRemision gr where gr.factura.id != :facturaId and gr.empresa.id = :empresaId and gr.estadoSRI != :estadoSRI order by gr.codigo asc")
    List<GuiaRemision> consultarPorFacturaYEmpresaYNoIgualEstadoSRI(long facturaId, long empresaId, String estadoSRI);
    @Query(value = "select gr from GuiaRemision gr where gr.factura.id = :facturaId order by gr.codigo desc")
    Optional<GuiaRemision> obtenerPorFactura(long facturaId);

}
