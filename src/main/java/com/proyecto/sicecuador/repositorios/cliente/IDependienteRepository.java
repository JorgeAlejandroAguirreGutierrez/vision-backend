package com.proyecto.sicecuador.repositorios.cliente;

import com.proyecto.sicecuador.modelos.cliente.Dependiente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IDependienteRepository extends JpaRepository<Dependiente, Long>, JpaSpecificationExecutor<Dependiente> {
	@Query(value = "select d from Dependiente d where d.estado=:estado")
    List<Dependiente> consultarPorEstado(String estado);
	
	@Query(value = "select d from Dependiente d where d.cliente.id = :clienteId and d.estado=:estado")
    List<Dependiente> consultarPorCliente(long clienteId, String estado);
	
	@Query(value = "select d from Dependiente d where d.razonSocial = :razonSocial and d.cliente.id = :clienteId and d.estado=:estado")
    List<Dependiente> consultarPorRazonSocial(String razonSocial, String estado);
}
