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
    @Query(value = "select gr from GuiaRemision gr order by gr.codigo asc")
    List<GuiaRemision> consultar();
    @Query(value = "select gr from GuiaRemision gr where gr.estado= :estado order by gr.codigo asc")
    List<GuiaRemision> consultarPorEstado(String estado);
    @Query(value = "select gr from GuiaRemision gr where gr.factura.id = :facturaId order by gr.codigo asc")
    Optional<GuiaRemision> obtenerPorFactura(long facturaId);
}
