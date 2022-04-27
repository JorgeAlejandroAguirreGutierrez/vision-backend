package com.proyecto.sicecuador.repositorios.entrega;

import com.proyecto.sicecuador.modelos.entrega.Entrega;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IEntregaRepository  extends JpaRepository<Entrega, Long>, JpaSpecificationExecutor<Entrega> {
	@Query(value = "select e from Entrega e "
            + "WHERE e.factura.id=:facturaId")
    public Optional<Entrega> obtenerPorFactura(long facturaId);
}
