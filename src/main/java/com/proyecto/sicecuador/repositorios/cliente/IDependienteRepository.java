package com.proyecto.sicecuador.repositorios.cliente;

import com.proyecto.sicecuador.modelos.cliente.Dependiente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IDependienteRepository extends JpaRepository<Dependiente, Long>, JpaSpecificationExecutor<Dependiente> {	
	@Query(value = "select d from Dependiente d where d.cliente.id = :clienteId order by d.codigo asc")
    List<Dependiente> consultarPorCliente(long clienteId);
	@Query(value = "select d from Dependiente d where d.razonSocial = :razonSocial order by d.codigo asc")
    List<Dependiente> consultarPorRazonSocial(String razonSocial);
}
