package com.proyecto.sicecuador.repositorios.cliente;

import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.repositorios.IGenericoRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface IClienteRepository extends IGenericoRepository<Cliente> {
	
	@Query(value = "select c from Cliente c where c.identificacion=:identificacion and c.estado=:estado")
    Optional<Cliente> obtenerPorIdentificacion(String identificacion, String estado);
	
	@Query(value = "select c from Cliente c where c.razonSocial=:razonSocial and c.estado=:estado")
    Optional<Cliente> obtenerPorRazonSocial(String razonSocial, String estado);
	
	@Query(value = "select c from Cliente c where c.estado=:estado")
    List<Cliente> consultarPorEstado(String estado);
}
