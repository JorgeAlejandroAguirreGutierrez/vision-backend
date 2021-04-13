package com.proyecto.sicecuador.repositorios.interf.cliente;

import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.repositorios.interf.IGenericoRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteRepository extends IGenericoRepository<Cliente> {
	
	@Query(value = "select c from Cliente c "
            + "WHERE c.identificacion=:identificacion")
    public Optional<Cliente> obtenerPorIdentificacion(String identificacion);
}
