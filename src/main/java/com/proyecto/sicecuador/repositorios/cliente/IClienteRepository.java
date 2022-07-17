package com.proyecto.sicecuador.repositorios.cliente;

import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.repositorios.IGenericoRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteRepository extends IGenericoRepository<Cliente> {
	
	@Query(value = "select c from Cliente c "
            + "WHERE c.identificacion=:identificacion and c.estado=:estado")
    public Optional<Cliente> obtenerPorIdentificacion(String identificacion, String estado);
	
	@Query(value = "select c from Cliente c "
            + "WHERE c.estado=:estado")
    public List<Cliente> consultar(String estado);
}
