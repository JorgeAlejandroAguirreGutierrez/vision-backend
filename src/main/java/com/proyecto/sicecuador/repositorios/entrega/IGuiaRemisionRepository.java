package com.proyecto.sicecuador.repositorios.entrega;

import com.proyecto.sicecuador.modelos.entrega.GuiaRemision;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IGuiaRemisionRepository extends JpaRepository<GuiaRemision, Long>, JpaSpecificationExecutor<GuiaRemision> {
	@Query(value = "select gr from GuiaRemision gr where gr.factura.id = :facturaId")
    Optional<GuiaRemision> obtenerPorFactura(long facturaId);
}
