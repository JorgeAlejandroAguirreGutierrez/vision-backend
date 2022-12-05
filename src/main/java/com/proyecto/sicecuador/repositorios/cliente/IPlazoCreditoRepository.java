package com.proyecto.sicecuador.repositorios.cliente;

import com.proyecto.sicecuador.modelos.cliente.PlazoCredito;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IPlazoCreditoRepository extends JpaRepository<PlazoCredito, Long>, JpaSpecificationExecutor<PlazoCredito> {
	@Query(value = "select pc from PlazoCredito pc where pc.estado=:estado")
    List<PlazoCredito> consultarPorEstado(String estado);
}
